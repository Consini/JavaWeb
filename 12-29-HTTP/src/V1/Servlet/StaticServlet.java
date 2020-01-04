package V1.Servlet;

import V1.Request;
import V1.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * @Description TODO
 * @Author K
 * @Date 2020/1/3 18:56
 **/
public class StaticServlet extends HttpServlet {
    @Override
    public void doGet(Request req, Response resp) throws IOException {
        // 127.0.0.1/hello.html
        //1.取 URL 的后缀
        //int index = req.path.lastIndexOf('.');
        int index = req.path.indexOf('.');
        String suffix = req.path.substring(index+1);
        if(suffix.equalsIgnoreCase("css")){
            resp.headers.put("Content-type","text/css; charset=UTF-8");
            //resp.setHeader("Content-type","text/css; charset=UTF-8");
        }

        //1.根据 URL 找到本地文件名
        String filename = "docBase" + req.path;
        //2.读取文件内容并写入 response 中
        try{
            InputStream is = new FileInputStream(filename);
            Scanner scanner = new Scanner(is,"UTF-8");
            while(scanner.hasNext()){
                resp.println(scanner.nextLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
