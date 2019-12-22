import com.sun.deploy.util.SyncAccess;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/21 9:15
 **/
public class IODemo {
    /* 1. 可以从文件中读
     * 2. 可以从网络中读（网卡/TCP连接)
     * 3. 可以从内存中读（内存中的一段空间当成输入源
     * 4. 可以从标准输入读
    *@Author K
    *@Date 10:01 2019/12/21
    **/
    private static InputStream 获得一个输入流() throws IOException {
        // 1. 文件中读
        /*
        InputStream inputStream;
        inputStream = new FileInputStream("test.txt");
        return inputStream;
        */

        // 2.内存中读与标准输入读
        /*
        InputStream inputStream;
        //byte[] bytes = "lalala\r\noooooooo".getBytes("UTF-8");// 内存中读
        //inputStream = new ByteArrayInputStream(bytes);
        inputStream = System.in;// 标准输入
        return inputStream;
        */


        //网络中读

        InputStream inputStream;
        Socket socket = new Socket("www.baidu.com",80);
        OutputStream os = socket.getOutputStream();
        Writer writer = new OutputStreamWriter(os,"UTF-8");
        PrintWriter printWriter = new PrintWriter(writer,false);
        printWriter.print("GET / HTTP/1.0\r\n\r\n");
        printWriter.flush();
        inputStream = socket.getInputStream();
        return inputStream;

    }
    /*1.直接通过字节方式读，然后程序进行字符编码
      2.把 Stream 转化为 Reader ，进行字符形式读取
        2.1 直接读
        2.2 BufferReader  readLine
      3. Scanner 也可以
    *@Author K
    *@Date 9:24 2019/12/21
    **/
    private static String 从字节流中获得字符数据(InputStream is)throws IOException{
        //第一种方式，字节读取
        //缺点：buffer < 字符长度 || 精确控制字符
        /*
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);
        String message = new String(buffer,0,len,"UTF-8");
        return message;
        */

        //第二种方法，进行字符形式直接读取
        /*
        Reader reader = new InputStreamReader(is,"UTF-8");
        char[] buffer = new char[10];//若字符长度大于buffer，就不能读全
        int len = reader.read(buffer);
        String message = new String(buffer,0,len);
        return message;
        */
        /* 可以保证读全
        StringBuilder stringBuilder = new StringBuilder();
        Reader reader = new InputStreamReader(is,"UTF-8");
        char[] buffer = new char[3];
        int len;
        while((len=reader.read(buffer))!=-1){
            stringBuilder.append(buffer,0,len);
        }
        String message = stringBuilder.toString();
        return message;
        */

        /*
        Reader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        StringBuilder sb = new StringBuilder();
        while((line=bufferedReader.readLine()) != null){
            sb.append(line+"\r\n");//自己加换行，否则readLine会自动去掉换行，读出的数据就是一行
        }
        return sb.toString();
        */

        // 3.Scanner 读
        // 只读第一行

        Scanner scanner = new Scanner(is,"UTF-8");
        return scanner.nextLine();

        //读多行
//        Scanner scanner = new Scanner(is,"UTF-8");
//        StringBuilder sb = new StringBuilder();
//        // 从内存中读时读多行，行数不确定时 需要 ctrl+D 结束,终端上 ctrl+Z 表示结束
//        while(scanner.hasNext()){
//            sb.append(scanner.nextLine()+"\r\n");
//        }
        // 确定行数时读，
//        int a = 4;
//        while(a-- !=0/*scanner.hasNext()*/){
//            sb.append(scanner.nextLine()+"\r\n");
//        }
//        return sb.toString();
    }

    private static OutputStream 获得一个输出流() throws  IOException{
        OutputStream os = new FileOutputStream("本地输出文件.txt");
        //Socket socket = new Socket("www.baidu.com", 80);
        //OutputStream os = socket.getOutputStream();
        //OutputStream os = new ByteArrayOutputStream();
        return os;
    }


    private static void 输出一段字符(OutputStream os,String message)throws IOException{

//        byte[] buffer = message.getBytes("UTF-8");
//        os.write(buffer);


        Writer writer = new OutputStreamWriter(os, "UTF-8");
//        writer.append(message);
//        writer.flush();

        PrintWriter printWriter = new PrintWriter(writer, false);
        printWriter.printf("%s", message);
        printWriter.flush();
    }
    public static void main(String[] args) throws IOException {
        /*
        InputStream is = 获得一个输入流();
        String message = 从字节流中获得字符数据(is);
        System.out.println(message);
        */
        OutputStream os = 获得一个输出流();
        输出一段字符(os, "lalal\r\n1100011\r\n");
    }
}
