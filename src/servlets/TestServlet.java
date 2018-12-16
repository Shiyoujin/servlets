//package servlets;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class TestServlet extends HelloWorld {
//    private TestServlet testServlet = new TestServlet();
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        this.doPost(request, response);
//    }
//
//    @Override
//    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        //http://localhost:8080/web15_visitorServletWithParm3/test.do?username=wgr&pwd=123
//        //接收请求参数  username, pwd
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");  //.trim()  ??
//        //登陆
//        boolean flag = TestService.login(username, password);
//        if (flag) {  //ture 登陆成功
//            //转发到 main.jsp
//            request.getRequestDispatcher("/main.jsp").forward(request, response);
//        } else {//登陆失败
//            //转发到error/jsp
//            request.getRequestDispatcher("/error.jsp").forward(request, response);
//        }
//    }
//}

//  interface  TestService{
//    public boolean login(String username,String password);
//}
//
// abstract class  TestServicenlnf implements TestService{
//    @Override
//     public  boolean login(String username,String password){
//        //如果username和pwd正确返回true，否则返回false
//        if("wgr".equals(username)&&"123".equals(password)){
//            return true;
//        }
//            return false;
//    }
//}