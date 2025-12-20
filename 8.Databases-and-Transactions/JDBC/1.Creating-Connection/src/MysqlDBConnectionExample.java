import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDBConnectionExample {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        try {
            // employee is a database created under oracle mysql database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/person_4", "root", "@Root123$");
            System.out.println(conn);
            System.out.println("Connection Established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
