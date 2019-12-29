import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/27 20:42
 **/
public class Task implements Runnable {
        private final Socket socket;// 代表的就是客人

        Task(Socket socket) {
            this.socket = socket;
        }
        /*按照 TCP 服务器处理流程：
        1.读取客户端的输入
        2.理解客户端的输入
        3.处理客人要求
        4.说让客人能理解的话
        5.把话说出去
        6.通知客人可以走了
        */
        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();//获取输入流
                OutputStream os = socket.getOutputStream();//获取输出流
                // 请求解析
                Request request = Request.parse(is);
                System.out.println(request);//打印请求头
                // 处理业务
                if (request.path.equalsIgnoreCase("/")) { //200
                    String body = "alert('必须关掉');";
                    byte[] bodyBuffer = body.getBytes("UTF-8");
                    StringBuilder response = new StringBuilder();
                    response.append("HTTP/1.0 200 OK\r\n");
                    response.append("Content-Type: application/javascript; charset=UTF-8\r\n");
                    response.append("Content-Length: ");
                    response.append(bodyBuffer.length);
                    response.append("\r\n");
                    response.append("\r\n");

                    os.write(response.toString().getBytes("UTF-8"));
                    os.write(bodyBuffer);
                    os.flush();
                } else if (request.path.equalsIgnoreCase("/run")) { //
                    String body = "<script src='/'></script>";
                    byte[] bodyBuffer = body.getBytes("UTF-8");
                    StringBuilder response = new StringBuilder();
                    response.append("HTTP/1.0 200 OK\r\n");// 状态行
                    response.append("Content-Type: text/html; charset=UTF-8\r\n");
                    response.append("Content-Length: ");// 求正文长度
                    response.append(bodyBuffer.length);
                    response.append("\r\n");
                    response.append("\r\n");// 空行后表示正文

                    os.write(response.toString().getBytes("UTF-8"));
                    os.write(bodyBuffer);
                    os.flush();
                }else if (request.path.equalsIgnoreCase("/banjia")) {// 307
                    StringBuilder response = new StringBuilder();
                    response.append("HTTP/1.0 307 Temporary Redirect\r\n");
                    response.append("Location:/run\r\n");
                    response.append("\r\n");
                    os.write(response.toString().getBytes("UTF-8"));
                    os.flush();
                } else {
                    StringBuilder response = new StringBuilder();
                    response.append("HTTP/1.0 404 Not Found\r\n");
                    response.append("\r\n");
                    os.write(response.toString().getBytes("UTF-8"));
                    os.flush();
                }
            /*
            String body = "<h1>一切正常</h1>";
            // 拼接响应
            Response response = Response.build(os);
            // 发送响应
            response.println(body);
            response.flush();
             */
                socket.close();
            } catch (Exception e) {}
        }
}
