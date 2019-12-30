package V1.Servlet;
import V1.Request;
import V1.Response;

import java.io.IOException;

public class JokeJSServlet extends HttpServlet {
    @Override
    public void doGet(Request req, Response resp) throws IOException {
        resp.setHeader("Content-Type", "application/javascript; charset=utf-8");
        resp.println("alert('你好');");
    }
}
