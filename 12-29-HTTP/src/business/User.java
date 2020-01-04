package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {// 实现序列化
    public String id;
    public String username;
    public int balance; //账户余额

    public User(String id, String username, int balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    static List<User> users = new ArrayList<>();
    static {
        users.add(new User("1", "马云", 300));
        users.add(new User("2", "马化腾", 200));
        users.add(new User("3", "许家印", 100));
    }

    public static User findUser(String username) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return user;
            }
        }

        return null;
    }
}
