import java.sql.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        
        try {
            String url = "jdbc:mysql://localhost:3306/employee";
            String user = "root";
            String password = "@iSawan";

            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            ResultSet resSet = stmt.executeQuery("select * from employees");

            while(resSet.next()){
                System.out.println("Student full Name: "+resSet.getString("firname")+" "+resSet.getString("lastname"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
