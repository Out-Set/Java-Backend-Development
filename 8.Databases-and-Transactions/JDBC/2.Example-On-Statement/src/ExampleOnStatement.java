import java.sql.*;

public class ExampleOnStatement {
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "root");
            stmt = conn.createStatement();
            if(stmt.execute("SELECT * FROM employees")){
                rs = stmt.getResultSet();
            }
            else{
                int updateCount = stmt.getUpdateCount();
                System.out.println(updateCount);
            }
        } catch (Exception ex) {
            System.out.println("SQLException: "+ ex.getMessage());
        }

        finally{
            if(rs != null){
                try {
                    rs.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            if(stmt != null){
                try {
                    stmt.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }
}
