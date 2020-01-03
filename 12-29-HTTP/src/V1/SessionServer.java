package V1;


import business.User;

import java.io.*;
import java.util.UUID;

public class SessionServer {//Session 服务器,用文件保存
    private static String dir = "会话";

    //存用户信息，得到Session-id并返回
    public static String put(User user) throws IOException {
        String sessionId = UUID.randomUUID().toString();//UUID:生成全球唯一随机码
        String filename = dir + "\\" + sessionId;//文件名
        OutputStream os = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(user);//写入信息
        os.close();
        return sessionId;
    }

    // 传入Session-id，返回用户信息
    public static User get(String sessionId) throws IOException {
        String filename = dir + "\\" + sessionId;

        InputStream is = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(is);

        User user = null;
        try {
            user = (User)ois.readObject();//从session-id文件中读出用户信息
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        is.close();

        return user;
    }
}
