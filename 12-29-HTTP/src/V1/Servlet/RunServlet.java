package V1.Servlet;

import V1.Request;
import V1.Response;

import java.io.IOException;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/29 11:55
 **/
public class RunServlet extends HttpServlet {
    @Override
    public void doGet(Request req, Response resp) throws IOException {
        resp.println("<script src='joke.js'></script>");
    }
}
