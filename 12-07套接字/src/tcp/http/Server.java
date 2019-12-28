package tcp.http;
import java.io.*;
import java.net.*;
import java.util.Scanner;
/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/7 10:54
 **/
public class Server {
    public static void main(String[] args) throws IOException, IOException {
        //监听 8080 端口
        ServerSocket socket = new ServerSocket(8080);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // 等待连接上来
            Socket clientSocket = socket.accept();// 接收消息

            // 打印对方的ip和端口号
//            InetAddress clientAddress = clientSocket.getInetAddress();
//            int clientPort = clientSocket.getPort();
//            System.out.printf("有客户端连接上来 %s:%d%n",clientAddress.getHostAddress(), clientPort);

            // 获取字节流
            InputStream is = clientSocket.getInputStream();
            // 字节流转换为字符流
            InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
            // 字符流转换缓冲字符流
            BufferedReader reader = new BufferedReader(isReader);

            // 获取输出字节流
            OutputStream os = clientSocket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("好友说>: " + line);// 输出客户端发送的消息
                System.out.print("请回复> ");
                String response = scanner.nextLine();// 输入待回复的消息
                out.println(response);// 输出回复的消息
            }
        }
    }
}