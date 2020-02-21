package dao;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

/**
 * @Description TODO
 * @Author K
 * @Date 2020/2/18 17:08
 **/
public class DBUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/java_image_server?characterEncoding=utf8&useSSL=true";
    private static final String USERNAMA = "root";
    private static final String PASSWORD = "";

    private static volatile DataSource dataSource = null;
    public static DataSource getDataSource(){
        //通过这个方法创建 DataSource 的实例
        if(dataSource == null){
            synchronized (DBUtil.class){
                if(dataSource == null){
                    dataSource = new MysqlDataSource();
                }
                MysqlDataSource mysqlDataSource = (MysqlDataSource)dataSource;
                mysqlDataSource.setURL(URL);
                mysqlDataSource.setUser(USERNAMA);
                mysqlDataSource.setPassword(PASSWORD);
                mysqlDataSource.setCharacterEncoding("UTF8");
            }
        }
        return dataSource;
    }

    // 获取连接
    public static Connection getConnection(){
        try {
            return getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭连接
    public static void close(Connection connection, PreparedStatement statement, ResultSet resultSet){
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
