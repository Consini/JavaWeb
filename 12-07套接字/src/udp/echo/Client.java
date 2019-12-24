package udp.echo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/7 9:43
 **/
public class Client {
    public static void main(String[] args) throws IOException {
        //新建 DatagramSocket
        DatagramSocket udpClientSocket = new DatagramSocket();
        // 往对方发数据
        String message = "0_0";
        byte[] sendBuffer = message.getBytes("UTF-8");
        //得到服务器IP地址
        //127.0.0.1 本机地址
        byte[] serverIP = new byte[4];
        serverIP[0] = (byte)127;
        serverIP[1] = (byte)0;
        serverIP[2] = (byte)0;
        serverIP[3] = (byte)1;
        InetAddress serverAddress = InetAddress.getByAddress(serverIP);
        DatagramPacket sendPacket = new DatagramPacket(
                sendBuffer,
                sendBuffer.length,
                serverAddress,
                9898
        );
        udpClientSocket.send(sendPacket);
        // 显示收到的消息
        byte[] receiveBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(
                receiveBuffer, receiveBuffer.length);
        udpClientSocket.receive(receivePacket);
        String responseMessage = new String(
                receivePacket.getData(),
                0,
                receivePacket.getLength(),
                "UTF-8"
        );
        System.out.println(responseMessage);
        udpClientSocket.close();
    }
}
