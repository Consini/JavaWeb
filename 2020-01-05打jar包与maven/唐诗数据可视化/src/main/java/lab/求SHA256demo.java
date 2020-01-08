package lab;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description TODO
 * @Author K
 * @Date 2020/1/7 20:29
 **/
public class 求SHA256demo {
    // MD5(短)：65396ee4aad0b4f17aacd1c6112ee364
    // SHA256：//beca6335b20ff57ccc47403ef4d9e0b8fccb4442b3151c2e7d50050673d43172
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String s = "你好世界";
        byte[] bytes = s.getBytes("UTF-8");
        messageDigest.update(bytes);//加密
        byte[] result = messageDigest.digest();//运算
        System.out.println(result.length);
        for(byte b : result){
            System.out.printf("%02x",b);
        }
    }
}
