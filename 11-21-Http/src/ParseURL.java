import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/21 12:02
 **/
public class ParseURL {
    private static Map<String, Integer> Ports = new HashMap<>();
    static {
        Ports.put("http", 80);
        Ports.put("https", 443);
        Ports.put("jdbc:mysql", 3306);
    }
    //<协议名称>：//<主机名>/<资源路径>?<查询字符串>#<文档片段标识符>
    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "https:" +
                "//www.baidu.com/" +
                "s?" +
                "ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=c%2B%2B&rsv_pq=a4fc69f200076536&rsv_t=2b96NbhzR2Ir%2BdW1z0tx0yYZJG0xZgadx7dwH0Qkvn6IH793SOANRIZNaj0&rqlang=cn&rsv_enter=1&rsv_dl=tb&rsv_sug3=3&rsv_sug1=3&rsv_sug7=100&rsv_sug2=0&prefixsug=c%252B%252B&rsp=2&inputT=1160&rsv_sug4=1161\n";
        int index = url.indexOf("://");
        String schema = url.substring(0,index);// 协议名称
        System.out.println("协议名称:"+schema);//协议

        url = url.substring(index+3);
        index = url.indexOf("/");
        String hostAndPost = url.substring(0,index);//主机名
        System.out.println("主机名："+hostAndPost);
        String host;
        int port;
        if(hostAndPost.contains(":")){
            String[] group = hostAndPost.split(":");
            host = group[0];
            port = Integer.valueOf(group[1]);
        }else{
            host = hostAndPost;
            port = Ports.get(schema);
        }
        System.out.println("主机:"+host);
        System.out.println("端口号："+port);

        url = url.substring(index);
        index = url.indexOf("?");
        String path = url.substring(0,index);
        System.out.println("资源路径："+path);

        url = url.substring(index+1);
        String queryString;//查询字符串
        String segment = "";//文档片段标识符
        if(url.contains("#")){
            String[] group = url.split("#");
            queryString = group[0];
            segment = group[1];
        }else{
            queryString = url;
        }
        String[] group = queryString.split("&");
        for(String s:group){
            System.out.println(URLDecoder.decode(s,"UTF-8"));
        }
        System.out.println("文档片段标识符:"+segment);
    }
}
