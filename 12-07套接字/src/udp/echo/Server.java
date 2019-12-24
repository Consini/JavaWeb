package udp.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/7 9:22
 **/
public class Server {
    public static void main(String[] args) throws IOException {
        // 1.新建 DatagramSocket
        DatagramSocket udpServerSocket = new DatagramSocket(9898);
        while(true) {
            byte[] receiveBuffer = new byte[1024];//存放收到的消息
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);// 数据报文
            // 2.等客户端来撩
            udpServerSocket.receive(receivePacket);// 接收到后通过 放到receiveBuffer
            InetAddress clientAddress = receivePacket.getAddress();//对方的IP地址
            System.out.printf("从 %s：%d 收到了消息%n", clientAddress.getHostAddress(), receivePacket.getPort());
            System.out.printf("一共收到了 %d 字节的数据%n", receivePacket.getLength());
            String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
            System.out.println(message);
            // 回给对方同样的消息
            byte[] sendBuffer = message.getBytes("UTF-8");
            DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer,
                    sendBuffer.length,
                    clientAddress,
                    receivePacket.getPort()
            );
            udpServerSocket.send(sendPacket);
        }
        // 关闭 DatagramSocket
        //udpServerSocket.close();
    }
}
