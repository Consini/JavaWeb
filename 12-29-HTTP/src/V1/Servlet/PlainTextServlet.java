package V1.Servlet;

import V1.Request;
import V1.Response;

import java.io.IOException;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/29 13:34
 **/
public class PlainTextServlet extends HttpServlet {
    @Override
    public void doGet(Request req, Response resp) throws IOException {
        resp.setHeader("Content-Type", "text/plain; charset=utf-8");
        resp.println("<h1>我不是 html 元素</h1>");
    }
}
