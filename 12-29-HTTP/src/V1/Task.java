package V1;
import V1.Servlet.*;

import java.net.Socket;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/29 9:42
 **/
public class Task implements Runnable{
    private final Socket socket;

    public Task(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            // 读取并解析请求
            Request request = Request.parse(socket.getInputStream());
            System.out.println("接收的请求："+request);
            Response response = new Response();

            // 处理业务
            HttpServlet servlet;
            if (request.path.equals("/joke.js")) {
                servlet = new JokeJSServlet();
            }else if(request.path.equals("/login")){// 登录
                // login?username=用户名
                // 发送响应：Header:Set-Cookie:username=用户名
                servlet = new LoginServlet();
            }else if(request.path.equals("/run")){ //
                servlet = new RunServlet();
            }else if(request.path.equals("/banjia")){// 重定向
                servlet = new RedirectServlet();
            }else if(request.path.equals("/plain")){// 裸文本
                servlet = new PlainTextServlet();
            }else if(request.path.equals("/hello")){
                servlet = new HelloServlet();
            }else{
                servlet = new NotFoundServlet();
            }
            servlet.doGet(request,response);

            // 组装并发送响应
            response.writeAndFlush(socket.getOutputStream());
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
