import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLJDBC {
    public static void main(String[] args) throws Exception {
        Connection conn = easyJdbcConnection.getConnection();
        System.out.println(conn);
    }
}

class easyJdbcConnection {
    public static Connection getConnection() throws Exception {
        Connection conn = null;

        try {
            InputStream inputStream = new FileInputStream("db.properties");
            Properties p = new Properties();
            p.load(inputStream);
            String user = p.getProperty("user");
            String password = p.getProperty("passwoed");
            String url = p.getProperty("url");

            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}