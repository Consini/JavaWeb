package V1.Servlet;

import V1.Request;
import V1.Response;

import java.io.IOException;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/29 11:57
 **/
public class NotFoundServlet extends HttpServlet {
    @Override
    public void doGet(Request req, Response resp) throws IOException {
        resp.setStatus("404 Not Found");
        resp.println("页面不存在");
    }
}
