import java.sql.*;

/**
 * @Description TODO
 * @Author K
 * @Date 2019/12/1 16:49
 **/
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.注册 Driver
        Class.forName("com.mysql.jdbc.Driver");
        //2. 通过 DriverManager（驱动管理）获取数据库连接
        //jdbc:mysql://本地IP:3306（可以不写）/数据库名?useSSl=false,"用户","密码"
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql119?useSSl=false" ,
                "root",
                "mysql");
        System.out.println(connection);//输出com.mysql.jdbc.JDBC4Connection@506c589e
        Statement statement = connection.createStatement();

        // 练习
        int rows = statement.executeUpdate("INSERT INTO xiangrikui VALUES" +
                " (10099,'蜡笔小新')");
        System.out.println(rows);
        // 通过 try 方式就不用手动 close
//        try(Statement statement1 = connection.createStatement()){
//            statement1.executeUpdate("InSERT into xiangrikui VALUES (10002,'戚容')");
//        }

//         //修改数据
//        int rows = statement.executeUpdate("INSERT INTO classes(name) VALUES ('Java11班')");
//        System.out.println(rows);
//        statement.close();//每执行一次语句就要关闭一次 statement，然后重新建立连接
//        statement = connection.createStatement();
//        rows = statement.executeUpdate("UPDATE classes SET name = 'C++60班' WHERE name = 'Java11班'");
//        System.out.println(rows);

        // 删除数据
//        int rows = statement.executeUpdate("DELETE FROM classes");
//        System.out.println(rows);

//        // 插入数据
//        int rows = statement.executeUpdate("INSERT INTO classes(name) VALUES ('Java11班')");
//        System.out.println(rows);

//        ResultSet resultSet = statement.executeQuery("SHOW TABLES");
//          next 必须首先调一次
//        while(resultSet.next()){// 一次读一行
//            // JDBC的列从 1 开始
//            String tableName = resultSet.getString(1);
//            System.out.println(tableName);
//        }
        //每次查询完都要关闭 resultSet
//        resultSet.close();

        // 关闭statement,
        //statement.close();
        // 关闭连接,只需要关闭一次
        connection.close();
    }
}
