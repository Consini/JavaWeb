import java.io.*;
import java.net.Socket;
import java.util.Scanner;
/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/21 9:15
 **/
public class IODemo {
    /* 从哪读？
     * 1. 可以从文件中读
     * 2. 可以从网络中读（网卡/TCP连接)
     * 3. 可以从内存中读（内存中的一段空间当成输入源）
     * 4. 可以从标准输入读 */
    private static InputStream 获得一个输入流() throws IOException {
        InputStream inputStream;
         //1. 文件中读
//        inputStream = new FileInputStream("test.txt");
         //2.内存中读
//        byte[] bytes = "第一行\r\n第二行\r\n".getBytes("UTF-8");
//        inputStream = new ByteArrayInputStream(bytes);// 字节数组作为输入流
        //3.标准输入读
//        inputStream = System.in;// 标准输入
        //4.网络中读，访问百度
        Socket socket = new Socket("www.baidu.com",80);// 域名+端口
        OutputStream os = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(os,"UTF-8");
        PrintWriter printWriter = new PrintWriter(writer,false);
        printWriter.print("GET / HTTP/1.0\r\n\r\n");
        printWriter.flush();
        inputStream = socket.getInputStream();
        return inputStream;

    }
    /* 1.直接通过字节方式读，然后程序进行字符编码
     * 2.把 Stream 转化为 Reader ，进行字符形式读取
     *   2.1 直接读
     *   2.2 BufferReader  用readLine方法
     * 3. Scanner */
    private static String 从字节流中获得字符数据(InputStream is)throws IOException{
        //1.字节读取。缺点：buffer < 字符长度 || 精确控制字符都比较麻烦
        /*
        byte[] buffer = new byte[1024];// 数据放在 buffer 中
        int len = is.read(buffer);// 实际读到的大小
        String message = new String(buffer,0,len,"UTF-8");// 进行字符编码
        return message;
        */

        //2.进行字符形式直接读取
        /*//2.1 直接读
        Reader reader = new InputStreamReader(is,"UTF-8");
        char[] buffer = new char[6];
        int len;
//      len = reader.read(buffer);//若字符长度大于 buffer 的长度，就不能读全
//      String message = new String(buffer,0,len);
//      return message;
        StringBuilder sb = new StringBuilder();//可以保证读全
        while((len=reader.read(buffer))!=-1){// EOS(End Of Stream),没读到流的末尾-1就一直读
            sb.append(buffer,0,len);
        }
        return sb.toString();
        */

        /*//2.2 通过 BufferReader
        Reader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        StringBuilder sb = new StringBuilder();
        while((line=bufferedReader.readLine()) != null){
            sb.append(line+"\r\n");//readLine会自动去掉换行，所以需要原格式的话需要自己加换行，否则读出的数据就是一行
        }
        return sb.toString();
        */

        // 3.Scanner 读
        // 只读第一行
//        Scanner scanner = new Scanner(is,"UTF-8");
//        return scanner.nextLine();
        //读多行
        Scanner scanner = new Scanner(is,"UTF-8");
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNext()){// 从内存中读时读多行，行数不确定时 需要 ctrl+D 结束,终端上 ctrl+Z 表示结束
            sb.append(scanner.nextLine()+"\r\n");
        }
        return sb.toString();
    }

    private static OutputStream 获得一个输出流() throws  IOException{
        //1.文件中获取
        OutputStream os = new FileOutputStream("本地输出文件.txt");
        //2.从网络中获取
//        Socket socket = new Socket("www.baidu.com", 80);
//        OutputStream os = socket.getOutputStream();
        //3.从内存中
//        OutputStream os = new ByteArrayOutputStream();
        return os;
    }
    private static void 输出一段字符(OutputStream os,String message)throws IOException{
        //1. 直接写
//        byte[] buffer = message.getBytes("UTF-8");//转换成字符数组
//        os.write(buffer);
        //2. Writer写
        Writer writer = new OutputStreamWriter(os, "UTF-8");
//        writer.append(message);
//        writer.flush();
        //3. PrintWriter写
        PrintWriter printWriter = new PrintWriter(writer, false);
        printWriter.printf("%s", message);
        printWriter.flush();
    }
    public static void main(String[] args) throws IOException {
//        InputStream is = 获得一个输入流();
//        String message = 从字节流中获得字符数据(is);
//        System.out.println(message);

        OutputStream os = 获得一个输出流();
        输出一段字符(os, "第一行\r\n第二行\r\n");
    }
}
