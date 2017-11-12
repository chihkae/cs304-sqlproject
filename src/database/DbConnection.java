package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DbConnection {
    INSTANCE;

    private Connection conn = null;
    private String dbUrl = "jdbc:mysql://localhost:3306/airport";
    private String dbUser = "cs304";
    private String dbPassword = "cs304";

    DbConnection() {
        try {
            checkIfJdbcDriverExists();
            connectToAirportDb();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void checkIfJdbcDriverExists() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
    }

    private void connectToAirportDb() throws Exception {
        conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // if conn == null, it means no connection
    // if conn != null, it is connected
    public Connection getConnection() {
        return this.conn;
    }
}
