/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/27 19:15
 **/
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Request {
    // 方法
    String method;
    // 路径
    String path;
    // 版本
    String version;

    // 请求头
    // key - value
    Map<String, String> headers = new HashMap<>();

    public static Request parse(InputStream is) {
        Request request = new Request();
        // is 是字节流，不太容易读一行，用熟悉的 Scanner 处理。字节流转字符流要带字符集编码
        Scanner scanner = new Scanner(is, "UTF-8");
        // 解析请求行
        parseRequestLine(request, scanner.nextLine());
        // 解析请求头直到空行
        String line;
        while (!(line = scanner.nextLine()).isEmpty()) {
            String[] group = line.split(":");
            String key = group[0].trim();
            String value = group[1].trim();
            request.headers.put(key, value);
        }
        // 解析正文（忽略）

        return request;
    }

    private static void parseRequestLine(Request request, String line) {
        String[] group = line.split(" ");
        request.method = group[0];
        request.path = group[1];
        request.version = group[2];
    }

    // 正文(忽略)

    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", version='" + version + '\'' +
                ", headers=" + headers +
                '}';
    }
}
