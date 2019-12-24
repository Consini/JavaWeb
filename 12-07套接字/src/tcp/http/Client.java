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
        Socket tcpClientSocket = new Socket();
        byte[] ipv4 = {(byte)192, (byte)168, (byte)0,(byte)196};
        //byte[] ipv4 = {(byte)127, (byte)0, (byte)0,(byte)1};
        InetAddress serverAddress = InetAddress.getByAddress(ipv4);
        SocketAddress serverSocketAddress
                = new InetSocketAddress(serverAddress, 8080);
        tcpClientSocket.connect(serverSocketAddress);

        while (true) {
            System.out.print("请输出> ");
            String request = scanner.nextLine();
            // 通过字节流直接写入请求
            OutputStream os = tcpClientSocket.getOutputStream();
            PrintStream out = new PrintStream(os, true, "UTF-8");
            out.println(request);

            // 通过字节流，直接读取数据
            InputStream is = tcpClientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(is, "UTF-8")
            );
            String response = reader.readLine();
            System.out.println(response);
        }
    }
}

