package V1;
import V1.Servlet.*;

import java.io.File;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/29 9:42
 **/
public class Task implements Runnable{
    private final Socket socket;
    private Map<String,HttpServlet> urlmap = new HashMap<>();

    public Task(Socket socket) {
        this.socket = socket;
        urlmap.put("/joke.js",new JokeJSServlet());
        urlmap.put("/login",new LoginServlet());
        urlmap.put("/run",new RunServlet());
        urlmap.put("/banjia",new RedirectServlet());
        urlmap.put("/plain",new PlainTextServlet());
    }

    @Override
    public void run() {
        try{
            // 读取并解析请求
            Request request = Request.parse(socket.getInputStream());
            System.out.println("接收的请求："+request);
            Response response = new Response();

            // 处理业务

//            HttpServlet servlet;
//            if (request.path.equals("/joke.js")) {
//                servlet = new JokeJSServlet();
//            }else if(request.path.equals("/login")){// 登录
//                // login?username=用户名
//                // 发送响应：Header:Set-Cookie:username=用户名
//                servlet = new LoginServlet();
//            }else if(request.path.equals("/run")){ //
//                servlet = new RunServlet();
//            }else if(request.path.equals("/banjia")){// 重定向
//                servlet = new RedirectServlet();
//            }else if(request.path.equals("/plain")){// 裸文本
//                servlet = new PlainTextServlet();
//            }else if(request.path.equals("/hello")){
//                servlet = new HelloServlet();
//            }else{
//                servlet = new NotFoundServlet();
//            }


            //首先根据 URL 找到对应文件，读取文件并把内容写到 response。
            //首先根据 URL 找到对应 servlet，先去 map 中找，
            // 没有再去 docBase 找，也没有返回 404 Not Found
            HttpServlet servlet = urlmap.get(request.path);
            if(servlet == null){
                String filename = "docBase"+request.path;
                File file = new File(filename);
                if(!file.exists()){
                    servlet = new NotFoundServlet();
                }else{
                    servlet = new StaticServlet();
                }
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
