/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/1 22:41
 **/
import java.sql.*;

public class Lianxi1 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost/sms?useSSL=false",
                "root",
                ""
        );

        // 如果向日葵班已经有了，就不在插入

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "INSERT INTO classes (name) VALUES ('向日葵班')"
            );
        }

        int classId;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(
                    "SELECT id FROM classes WHERE name = '向日葵班'"
            )) {

                resultSet.next();
                classId = resultSet.getInt(1);
            }
        }

        try (Statement statement = connection.createStatement()) {
            String sql = "INSERT INTO students (sn, class_id, name)"
                    + " VALUES ('10086', " + classId + ", '蜡笔小新'),"
                    + " ('10087', " + classId + ", '麦兜'),"
                    + " ('10088', " + classId + ", '孙悟空')";
            System.out.println(sql);
            statement.executeUpdate(sql);
        }

        connection.close();
    }
}

