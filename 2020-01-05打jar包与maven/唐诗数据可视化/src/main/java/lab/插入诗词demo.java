package lab;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

/**
 * @Description TODO
 * @Author K
 * @Date 2020/1/7 20:01
 **/
public class 插入诗词demo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String dynasty = "唐代";
        String author = "白居易";
        String title = "问刘十九";
        String content = "绿蚁新醅酒，红泥小火炉。晚来天欲雪，能饮一杯无？";

        // 1. 注册 Driver
        // 2. 获取 Connection 通过 DriverManager
//        Class.forName("com.mysql.jdbc.Driver");
//        String url = "jdbc:mysql://127.0.0.1/tangshi0107?useSSL=false&charset=utf8";
//        Connection connection = DriverManager.getConnection(url,"root","mysql");
//        System.out.println(connection);


        //通过 DataSource 获取 Connection
        //DataSource dataSource1 = new MysqlDataSource();// 不带连接池
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();//带有连接池
        dataSource.setServerName("127.0.0.1");
        dataSource.setPort(3306);
        dataSource.setUser("root");
        dataSource.setPassword("mysql");
        dataSource.setDatabaseName("tangshi0107");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("UTF8");

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO tangshi " +
                    "(sha256, dynasty, title, author, " +
                    "content, words) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "sha256");
                statement.setString(2, dynasty);
                statement.setString(3, title);
                statement.setString(4, author);
                statement.setString(5, content);
                statement.setString(6, "");

                statement.executeUpdate();
            }
        }
    }
}
