package lab;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description TODO
 * @Author K
 * @Date 2020/1/9 20:25
 **/
public class Java配置 {
    public static void main(String[] args) throws IOException {
        InputStream is = Java配置.class.getClassLoader().getResourceAsStream("some.properties");
        Properties properties = new Properties();
        properties.load(is);
        System.out.println(properties.getProperty("mysql.host"));
    }
}
