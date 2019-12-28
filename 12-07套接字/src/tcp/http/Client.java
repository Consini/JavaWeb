package tcp.http;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/7 11:29
 **/
public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket();
        //byte[] ipv4 = {(byte)192, (byte)168, (byte)0,(byte)196};
        byte[] ipv4 = {(byte)127, (byte)0, (byte)0,(byte)1};// 服务器地址是本机
        InetAddress serverAddress = InetAddress.getByAddress(ipv4);// 对方ip地址
        SocketAddress serverSocketAddress = new InetSocketAddress(serverAddress, 8080);//对方端口号
        // 连接
        socket.connect(serverSocketAddress);

        while (true) {
            System.out.print("请输入信息> ");
            String request = scanner.nextLine();// 客户端的输入当作服务器端的输出
            // 通过字节流直接写入请求
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");
            out.println(request);// 打印出客户端的消息

            // 通过字节流，直接读取数据,服务器端的输出当作客户端的输入
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, "UTF-8")
            );
            String response = reader.readLine();
            System.out.print("对方回复您> ");
            System.out.println(response);// 打印出服务器端回复的消息
        }
        // 关闭
        //socket.close();
    }
}

