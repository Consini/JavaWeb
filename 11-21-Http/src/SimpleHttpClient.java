import java.io.*;
import java.net.Socket;
import java.util.Scanner;
/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/21 15:10
 **/
public class SimpleHttpClient {
    public static void main(String[] args) throws IOException {
        String request = "GET / HTTP/1.0\r\nHost: www.baidu.com\r\n\r\n";

        Socket socket = new Socket("www.baidu.com", 80);
        socket.getOutputStream().write(request.getBytes("UTF-8"));
        // 版本   状态码     状态描述
        // 响应头打印
        // 把响应正文保存下来
        byte[] bytes = new byte[4096];
        int len = socket.getInputStream().read(bytes);//第一次读到的数据长度
        // 假设 4096 字节已经包含 响应行 + 所有响应头 + 一部分正文
        int index = -1;
        for (int i = 0; i < len - 3; i++) {
            if (bytes[i] == '\r' && bytes[i+1] == '\n' && bytes[i+2] == '\r' && bytes[i+3] == '\n') {
                index = i;
                break;
            }
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes, 0, index + 4);
        Scanner scanner = new Scanner(byteArrayInputStream, "UTF-8");
        String statusLine = scanner.nextLine();
        System.out.println("状态行: " + statusLine);
        String[] strings = statusLine.split(" ");
        System.out.println("响应版本: "+strings[0]);
        System.out.println("状态码: "+strings[1]);
        System.out.println("状态描述: "+strings[2]);
        String line;
        int contentLength = -1;
        while (!(line = scanner.nextLine()).isEmpty()) {
            String[] kv = line.split(":");
            String key = kv[0].trim();
            String value = kv[1].trim();
            if(key.equalsIgnoreCase("Content-Length")){
                contentLength = Integer.valueOf(value);
            }
            System.out.println("响应头: " + key + " = " + value);
        }
        System.out.println("正文长度： "+contentLength);
        int alreadyRead = len - index - 4;
        int surplus = contentLength - alreadyRead;//还应该读的
        byte[] body = new byte[contentLength];
        System.arraycopy(bytes,index+4,body,0,alreadyRead);
        int realRead = socket.getInputStream().read(body,alreadyRead,surplus);
        System.out.println("已经读了： "+alreadyRead);
        System.out.println("还应该读: "+surplus);
        System.out.println("实际读了： "+realRead);
        FileOutputStream fileOutputStream = new FileOutputStream("百度.html");
        fileOutputStream.write(body);
    }
}
