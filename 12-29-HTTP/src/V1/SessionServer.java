package V1;


import business.User;

import java.io.*;
import java.util.UUID;

public class SessionServer {
    private static String dir = "会话";

    public static String put(User user) throws IOException {
        String sessionId = UUID.randomUUID().toString();
        String filename = dir + "\\" + sessionId;
        OutputStream os = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(os);

        oos.writeObject(user);

        os.close();

        return sessionId;
    }

    public static User get(String sessionId) throws IOException {
        String filename = dir + "\\" + sessionId;

        InputStream is = new FileInputStream(filename);
        ObjectInputStream ois = new ObjectInputStream(is);

        User user = null;
        try {
            user = (User)ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        is.close();

        return user;
    }
}
