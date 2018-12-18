//package servlets;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/hello")
//public class DemoA extends HttpServlet {
//    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException{
//        String id = request.getParameter("account");
//        String password = request.getParameter("password");
////        String name = request.getParameter("name");
//        UserService userService = new UserService();
//        boolean flag = userService.loginService(id,password);
//        if (flag )
//        {
//            response.getWriter().write("success!");
//        } else
//            {
//
//            }
//    }
//






//}
//    @Override
//    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException {
//        String str = "Helllo World";
//        resp.getWriter().write(str);
//    }
//}
