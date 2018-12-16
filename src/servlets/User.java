package servlets;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class User extends HttpServlet {
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
         //接收请求中的参数
         String username = request.getParameter("username");
         String password = request.getParameter("password");
         response.setContentType("text/html;charset=utf8");
         //提交方式为post
         request.setCharacterEncoding("utf8");

         PrintWriter out = response.getWriter();

         //输入校验
         if(username!=null&&username.length()>5&&password!=null&&password.length()>5){
             //要连接数据库，先从配置表中获取初始化参数传给JDBSUtil的构造函数
             ServletContext ctx = this.getServletContext();
             String url = ctx.getInitParameter("url");
             String dbuser = ctx.getInitParameter("dbuser");
             String dbpass = ctx.getInitParameter("dbpass");
             JDBCUtil util = new JDBCUtil(url, dbuser, dbpass);

             //userdao--->util--->数据库
             //然后将util作为参数传给操作类
             UserDao dao = new UserDao();
             dao.setUtil(util);

             //将获取的的参数存到uer中，调用操作类中的方法保存
             UserA user = new UserA(username,password);
             dao.saveUser(user);

             out.println("<h1>恭喜你！注册成功</h1>");
             out.println("<br><a href='index.jsp'>点此登录</a>");
         }else{

             out.println("<h1>参数有误，注册失败！</h1>");
             out.println("<br><a href='reg.html'>重新注册</a>");

         }
         out.flush();
         out.close();
     }

     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws  IOException {
        // TODO Auto-generated method stub
        doGet(req, resp);
    }
}

class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        //获取请求参数
        req.setCharacterEncoding("utf8");
        String username = req.getParameter("username");
        String pass = req.getParameter("pass");
        resp.setContentType("text/html;charset=utf8");
        PrintWriter out = resp.getWriter();

        //如果用户名和密码不为空，并且长度大于0就执行if语句--->判断查询到的user--->判断密码是否一致
        if(username!=null&&username.length()>0&&pass!=null&&pass.length()>0){

            //要连接数据库，先从配置表中获取初始化参数传给JDBSUtil的构造函数
            ServletContext ctx = this.getServletContext();
            String url = ctx.getInitParameter("url");
            String dbuser = ctx.getInitParameter("dbuser");
            String dbpass = ctx.getInitParameter("dbpass");

            JDBCUtil util = new JDBCUtil(url, dbuser, dbpass);

            //然后将util作为参数传给操作类
            UserDao dao = new UserDao();
            dao.setUtil(util);

            //调用操作类的通过用户名查询用户
            UserA user = dao.getUserByName(username);

            if(user!=null){
                if(((UserA) user).getPassword().equals(pass)){
                    out.println("<h1>恭喜你！登录成功！进入了学生管理系统！</h1>");
                }else{
                    out.println("<h1>登录失败！密码错误！</h1>");
                    out.println("<br><a href='index.jsp'>点此登录</a>");
                }
            }else{
                out.println("<h1>登录失败！用户名错误！</h1>");
                out.println("<br><a href='index.jsp'>点此登录</a>");
            }
        }else{
            out.println("<h1>登录失败！用户名/密码错误！</h1>");
            out.println("<br><a href='index.jsp'>点此登录</a>");
        }
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws  IOException {
        doGet(req, resp);
    }
}

class JDBCUtil{
    private String url;
    private String dbuser;
    private String dbpass;

    public JDBCUtil(String url, String dbuser, String dbpass) {
        super();
        this.url = url;
        this.dbuser = dbuser;
        this.dbpass = dbpass;
    }
    //静态代码块，加载驱动
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //对外提供一个连接数据库的方法
    public Connection getConnection() throws Exception{
        return DriverManager.getConnection(this.url, this.dbuser, this.dbpass);
    }
}

class UserA {
private String username;
private String password;

public UserA() {
        super();
        }
public UserA(String username, String password) {
        super();
        this.username = username;
        this.password = password;
        }

public String getUsername() {
        return username;
        }
public void setUsername(String username) {
        this.username = username;
        }
public String getPassword() {
        return password;
        }
public void setPassword(String pass) {
        this.password = password;
        }
}

//注册的时候保存用户（通过用户的id）
 class UserDao {
    //因为这个操作类就是对数据库进行操作，所有要连接数据库
    private JDBCUtil util;

    public JDBCUtil getUtil() {
        return util;
    }

    public void setUtil(JDBCUtil util) {
        this.util = util;
    }

    public void saveUser(UserA user) {

        Connection conn = null;
        PreparedStatement stat = null;
        try {
            String sql = "insert into member values (null,?,?,?,?,?,?,?,?)";
            conn = util.getConnection();        //创建连接
            stat = conn.prepareStatement(sql);  //创建预处理对象
            //存储数据（有几个问好，就存几个）
            stat.setString(1, user.getUsername());
            stat.setString(2, user.getPassword());
            stat.executeUpdate();//保存
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //登录时，通过用户名找到这个对象，因为查询到的是一个对象，所有返回一个user
    public UserA getUserByName(String uname){
        UserA user = new UserA();
        try {
            String sql = "select * from member where username = ?";
            Connection conn = util.getConnection();
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, uname);
            //把查询到的数据放到结果集中
            ResultSet res = stat.executeQuery();//查询
            //如果查询到，就将结果集中的数据保存到用户中
            if(res.next()){
                user.setUsername(res.getString("username"));
                user.setPassword(res.getString("password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}






