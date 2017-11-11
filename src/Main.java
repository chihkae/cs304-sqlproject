import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.out.println("Cannot find JDBC driver");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return;
        }

        Connection conn = null;

        try {
            conn = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/airport","cs304", "cs304");

        } catch (SQLException ex) {
            System.out.println("Failed to connect to database");
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return;
        }

        if (conn != null) {
            System.out.println("Connected to db!");
        } else {
            System.out.println("Still failed to connect to db");
        }
    }
}
