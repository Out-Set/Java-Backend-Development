import java.sql.*;

public class ConcurrentUpdate {
        private static final String URL = "jdbc:mysql://localhost:3306/employee";
        private static final String USER_NAME = "root";
        private static final String PASSWORD = "root";
    public static void main(String[] args) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM employees";

        try {
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery(sql);

            System.out.println("id\t"+"First-Name\t"+"Last-Name\t");

            while(rs.next()){
                int id = rs.getInt("id");
                String fn = rs.getString("firname");
                String ln = rs.getString("lastname");

                if(id == 3){
                    rs.updateString("firname", "Raj");
                    rs.updateRow();
                }

                System.out.println(id+"\t"+fn+"\t\t"+ln);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
