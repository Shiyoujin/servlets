package servlets;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DemoServletA extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String a = "哈哈";
        String username = request.getParameter("username"); //获得参数username

        response.setContentType("application/json;charset=utf-8"); //指定返回的格式为JSON格式
        response.setCharacterEncoding("UTF-8");
        String jsonback =a;
        PrintWriter out = response.getWriter();
        out.write(jsonback);
        out.close();
    }


}

