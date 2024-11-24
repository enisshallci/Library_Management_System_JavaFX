package service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:mysql://localhost:3306/library_db";
                String user = "root";
                String password = "12345";
                connection = DriverManager.getConnection(url, user, password);

            }
        } catch(SQLException e) {
            System.out.println(e);
        }
        return connection;
    }
}








