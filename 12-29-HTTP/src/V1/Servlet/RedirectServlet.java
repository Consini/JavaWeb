package V1.Servlet;

import V1.Request;
import V1.Response;

import java.io.IOException;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/29 11:56
 **/
public class RedirectServlet extends HttpServlet {
    @Override
    public void doGet(Request req, Response resp) throws IOException {
        resp.setStatus("307 Temporary Redirect");
        resp.setHeader("Location", "https://www.qq.com/");//告诉新地址Location
    }
}
