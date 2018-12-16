package servlets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class HelloWorld extends HttpServlet {
    private String message;

    @Override
    public void init() throws  ServletException {
        //执行必需的初始化
          message = "Hello world, this message is from servlet!你好";
//        System.out.println("====");
    }

//    public void service(ServletRequest request, ServletResponse response) throws ServletException,IOException{
//        System.out.println("hehe");
//
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException ,IOException{
        //设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
//        response.setCharacterEncoding("UTF-8");

        //设置逻辑实现
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}




















//    @Override
//    public void init() throws ServletException {
//        // TODO Auto-generated method stub
//        super.init();
//    }
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        // TODO Auto-generated method stub
//        super.init(config);
//    }
//
//     @Override
//     protected void service(HttpServletRequest request, HttpServletResponse response)
//             throws ServletException,IOException {
//         // TODO Auto-generated method stub
//        PrintWriter printWriter = response.getWriter();
//        printStream.print("Hello World");
//        printStranm.close();
//    }
//}



//        response.setContentType("text/html,charset=utf-8");
//        PrintWriter out = response.getWriter();
//        String text = request.getParameter("text");
//
//        out.println("<HTML>");
//        out.println("  <HEAD><TITLE>Hello Servlet</TITLE></HEAD>");
//        out.println("<h1>" + text + "!</h1>");
//        out.println("  </BODY>");
//        out.print("</HTML");
//        out.close();
//    }
//}


