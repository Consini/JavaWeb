package V1.Servlet;

import V1.Request;
import V1.Response;
import V1.SessionServer;
import business.User;

import java.io.IOException;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/29 15:25
 **/
public class HelloServlet extends HttpServlet {
    @Override
    public void doGet(Request req, Response resp) throws IOException {
        // 原来版本
        /*
        String currentUser = "未登录";
        String cookie = req.headers.get("Cookie");
        if(cookie != null){
            //username=peixinchen
            currentUser = cookie.split("=")[1];
        }
        resp.setHeader("Content-Type","text/plain; charset=utf-8");// 设置了纯文本
        resp.setHeader("X-Teacher","peixinchen");
        resp.setHeader("X-Room","201");
        String target = req.parameters.get("target");
        if(target==null){
            resp.setStatus("401 Unautorized");
            resp.println("不存在的");
            return;
        }
        resp.print("你好 "+target+"\r\n");
        //resp.print("<h1>"+target+"</h1>");// 这里不会以 html 形式显示
        resp.print("当前用户是:"+currentUser);*/

        String sessionId = null;
        String cookie = req.headers.get("Cookie");
        if (cookie != null) {
            // "username=陈沛鑫"
            sessionId = cookie.split("=")[1];
        }

        User user = SessionServer.get(sessionId);

        resp.setHeader("Content-Type", "text/plain; charset=UTF-8");
        resp.println("当前用户是 " + user.username);
        resp.println("账户余额是 " + user.balance);
    }
}
