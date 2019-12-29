/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/27 18:56
 **/
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHTTPServer {
    public static void main(String[] args) throws IOException {
        //建立监听 socket,监听端口为 80 端口（TCP层决定把数据交给谁，通过端口）
        // 一个端口只能属于一个进程
        ServerSocket serverSocket = new ServerSocket(80);
        //创建线程池，负责处理工作线程
        ExecutorService pool = Executors.newFixedThreadPool(10);
        /*主线程只负责前台任务
        对接客户全部交给工作线程处理
         */
        while (true) {
            Socket socket = serverSocket.accept();//没有人来之前不会返回，返回的是一个通信 socket
            pool.execute(new Task(socket));//在Task中进行
        }
    }
}
