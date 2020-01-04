package V1;
import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/29 9:23
 **/
public class Request {
    public String method;
    public String path;
    public String version;

    public Map<String,String> headers = new TreeMap<>();
    public Map<String,String> parameters = new HashMap<>();//请求参数query_string

    static Request parse(InputStream is)throws IOException{
        Request request = new Request();
        Scanner sc = new Scanner(is,"UTF-8");

        parseRequestLine(request,sc);
        parseRequestHeader(request,sc);

        return request;
    }

    // 解析请求行
    private static void parseRequestLine(Request request, Scanner sc) throws IOException{
        String[] group = sc.nextLine().split(" ");
        request.method = group[0];// 方法
        parseURL(request,group[1]);//解析路径： URL
        request.version = group[2];// 版本
    }
    //解析 URL:拆分路径 path 和查询字符串 query_string
    private static void parseURL(Request request, String s) throws UnsupportedEncodingException {
        String[] group = s.split("\\?");
        request.path = URLDecoder.decode(group[0], "UTF-8");
        if (group.length == 2) {
            String[] segment = group[1].split("&");
            for (String kvString : segment) {
                String[] kv = kvString.split("=");
                String key = URLDecoder.decode(kv[0], "UTF-8");
                String value = "";
                if (kv.length == 2) {
                    value = URLDecoder.decode(kv[1], "UTF-8");
                }
                request.parameters.put(key, value);
            }
        }
    }

    // 解析请求头
    private static void parseRequestHeader(Request request, Scanner sc) {
        String line;
        while (!(line = sc.nextLine()).isEmpty()) {
            String[] kv = line.split(":");
            String key = kv[0].trim();
            String value = kv[1].trim();
            request.headers.put(key, value);
        }
    }

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                '}';
    }
}
