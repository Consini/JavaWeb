package V1.Servlet;

import V1.Request;
import V1.Response;
import V1.SessionServer;
import business.User;

import java.io.IOException;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/29 11:42
 **/
public class LoginServlet extends HttpServlet {
    @Override
    public void doGet(Request req, Response resp) throws IOException {
        String username = req.parameters.get("username");
        if(username == null){
            resp.setStatus("401 Unautorized");
            resp.println("<h1>请登录</h1>");
            return;
        }

        User currentUser = User.findUser(username);// 查找当前用户是否存在
        if (currentUser == null) {
            resp.setStatus("401 Unauthorized");
            resp.println("<h1>该用户不存在</h1>");
            return;
        }

        String sessionId = SessionServer.put(currentUser);

        //种Cookie，将session-id存入
        resp.setHeader("Set-Cookie", "session-id=" + sessionId + "; " +
                "expires=Tue, 07-Apr-2020 08:46:16 GMT; Max-Age=8640000");

//        //resp.setHeader("Set-Cookie","username= "+username);
//        // 设置 Cookie 时间
//        resp.setHeader("Set-Cookie","username= "+username+";expires=Tue, 07-Apr-2020 08:46:16 GMT; Max-Age=8640000");
        resp.println("<h1>欢迎 "+username+" 登录</h1>");
    }
}
