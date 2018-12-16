package servlets;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;


class Request extends Thread {
    private int end;
    private int start;
    private ArrayList<Integer> error = new ArrayList<>();
    public Request(int start, int end) {
        this.start = start;
        this.end = end;
    }
    @Override
    public void run() {
        for (; start <= end; start++) {
            sendRequest(start);
        }
        while (error.size() > 0) {
            for (Iterator<Integer> integerIterator = error.iterator(); integerIterator.hasNext();) {
                error = new ArrayList<>();
                sendRequest(integerIterator.next());
            }
        }
    }
    private void sendRequest(int start) {
        PrintWriter out = null;
        BufferedReader in = null;
        // 设置 result变量为空，存储网页爬取的 json字符串，该 sendPost最后返回 result
        String result = "";
        try {
            // 1.URL类封装了大量复杂的实现细节，这里将一个字符串构造成一个URL对象
            //这里直接  传入了 所要爬取的网址 json， 也可在前面设置一个 String，再传入设置的变量
            URL url = new URL("http://jwzx.cqupt.edu.cn/data/json_StudentSearch.php?searchKey=" + start);
            // 打开和URL之间的连接，并 转成 HttpURLConnection 对象
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // 设置通用的请求属性
            conn.setReadTimeout(3000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "*/*");
//                conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //调用connect方法连接远程资源,且此方法应于设置后
            conn.connect();
            // 获取HttpURLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //发送请求参数
            //out.print(Param)
//          // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            // 还有下面数据库的链接也要注意
            // 发送请求参数
//            out.print(param);
            //对数据进行访问
            String line = null;
            while ((line = in.readLine()) != null) {
                result += line;
            }
//            System.out.println(result + "   " + start);
            conn.disconnect();
            JSONObject jsObject = JSONObject.fromObject(result);
            JSONArray jsonArray = JSONArray.fromObject(jsObject.get("returnData"));
            int exist = 0;
            if (jsonArray.size() != 0) {
                exist = 1;
            }
            int resultCode = JDBCOperation.startSave(start, exist);
            if (resultCode != 0) {
                error.add(resultCode);
            }
        } catch (Exception e ) {
            error.add(start);
        } finally {// 使用finally块来关闭输出流、输入流
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                System.out.println("关闭流异常");
            }
        }
    }
}
class JDBCOperation {
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    static class Student {
        private int xh;
        private int Yn;

        public Student(int xh, int Yn) {
            this.xh = xh;
            this.Yn = Yn;
        }

        public int getXh() {
            return xh;
        }

        public int getYn() {
            return Yn;
        }
    }

    //创建数据库链接
    private static Connection getConn() throws Exception {
        Connection conn = null;
//        try {
            //加载 JDBC 的驱动
            Class.forName("com.mysql.cj.jdbc.Driver");// String driverName = "com.mysql.cj.jdbc.Driver";    也可以这么分开写
            //Class.forName(driverName); //classLoader,加载对应驱动

            //对外提供一个链接数据库的方法, student 为数据库名，其后为数据库设置了  UTF-8和 时区，不然 中文会乱码
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8", "root", "");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return conn;
    }

    //设置 prepareStatement对象 insert
    public static int insert(JDBCOperation.Student student) {   // insert 是方法不是构造器，这里不是 传入参数，创建了一个 Studenta类的 对象 studenta来使用其中的 东东
        Connection conn = null; //创建链接
        try {
            conn = getConn();
        } catch (Exception e) {
            return student.xh;
        }
        String sql = "insert into nianji (xh,yn)values(?,?)";
//        PreparedStatement pstmt;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);    //创建预处理对象
            pstmt.setInt(1,student.getXh());
            pstmt.setInt(2,student.getYn());
            pstmt.executeUpdate();  //保存
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            return student.xh;
        }
        return 0;
    }

    public static int startSave (int xh, int exist) {
        reentrantLock.lock();
        int xH = insert(new JDBCOperation.Student(xh, exist));
        reentrantLock.unlock();
        return xH;
    }

    public static void main(String[] args) {
        new Request(2011210000, 2011215100).start();
        new Request(2012210000, 2012215100).start();
        new Request(2013210000, 2013215100).start();
        new Request(2014210000, 2014215100).start();
        new Request(2015210000, 2015215100).start();
        new Request(2016210000, 2016215100).start();
        new Request(2017210000, 2017215100).start();
        new Request(2018210000, 2018215100).start();
    }
}

