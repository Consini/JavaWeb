/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/27 19:44
 **/
public class ParseRequestDemo {
    public static void main(String[] args) {
        StringBuilder st = new StringBuilder();
        st.append("GET / HTTP/1.1\r\n");//请求行
        st.append("Host: 127.0.0.1\r\n");// 请求报头
        st.append("Accept: text/html\r\n");
        st.append("Accept: text/html\r\n");
        String req = st.toString();
        int i = req.indexOf("\r\n");
        String line=req.substring(0,1);
        String[] group = line.split(" ");
        System.out.println(group[0]);
        System.out.println(group[1]);
        System.out.println(group[2]);
        int j = req.indexOf("\r\n",i+2);
        while(j != i+2){
            String lin = req.substring(i+2,j);
            if(lin.isEmpty()){
                break;
            }
            System.out.println(lin);

            i = j;
        }
    }
}
