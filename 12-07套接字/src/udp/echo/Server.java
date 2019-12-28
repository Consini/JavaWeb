package udp.echo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/7 9:22
 **/
public class Server {
    //简单实现汉英字典
    private static final Map<String,String> map = new HashMap<>();
    static {
        map.put("cat","猫咪");
        map.put("dog","小狗");
        map.put("rabbit","兔子");
        map.put("lion","狮子");
    }
    public static void main(String[] args) throws IOException {
        // 1.新建 DatagramSocket,并将其绑定到指定端口
        DatagramSocket socket = new DatagramSocket(9898);
        while(true) {
            byte[] receiveBuf = new byte[1024];//存放收到的消息
            DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);// 数据报文
            // 2.等客户端来撩
            socket.receive(receivePacket);// 接收到后通过 放到receiveBuffer
            InetAddress clientAddress = receivePacket.getAddress();//对方的IP地址
            System.out.printf("从 %s：%d 收到了消息%n", clientAddress.getHostAddress(), receivePacket.getPort());
            System.out.printf("一共收到了 %d 字节的数据%n", receivePacket.getLength());
            String message = new String(receivePacket.getData(),
                    0, receivePacket.getLength(),
                    "UTF-8");

            System.out.println(message); //打印接收到的消息
            // 在 map 中查找收到的消息
            String responseMassage = map.getOrDefault(message,"不会翻译");

            // 回给对方同样的消息
            //byte[] sendBuffer = message.getBytes("UTF-8");

            // 回给对方对应单词的中文或者找不到该单词
            byte[] sendBuffer = responseMassage.getBytes("UTF-8");
            DatagramPacket sendPacket = new DatagramPacket(
                    sendBuffer,
                    sendBuffer.length,
                    clientAddress,
                    receivePacket.getPort()
            );
            socket.send(sendPacket);
        }
        // 关闭 DatagramSocket
        //socket.close();
    }
}
