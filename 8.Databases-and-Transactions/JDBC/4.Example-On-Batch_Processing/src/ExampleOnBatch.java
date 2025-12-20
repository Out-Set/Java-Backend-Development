import java.sql.*;

public class ExampleOnBatch {
    public static void main(String[] args) throws Exception {
        Connection conn =null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "root");
            Statement stmt = conn.createStatement();
            String SQL = "INSERT INTO employees(id, firname, lastname) VALUES(5, 'Henry', 'J')";
            stmt.addBatch(SQL);

            SQL = "INSERT INTO employees(id, firname, lastname) VALUES(6, 'Haider', 'M')";
            stmt.addBatch(SQL);

            SQL = "UPDATE employees SET lastname='Z' WHERE id=5";
            stmt.addBatch(SQL);

            stmt.executeBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
