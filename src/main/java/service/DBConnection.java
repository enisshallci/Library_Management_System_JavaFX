package service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {

        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://localhost:3306/Library_Database";
            String user = "root";
            String password = "12345";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
        }

        return connection;
    }

//    public static void main(String[] args) throws SQLException {
//
//        DBConnection.getConnection();
//    }

}








