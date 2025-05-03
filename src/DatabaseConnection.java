import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=CONVERT_TO_NULL";  // Replace with your host and database name
    private static final String USER = "root";  // Replace with your MySQL username
    private static final String PASSWORD = "root";  // Replace with your MySQL password

    public static Connection connect() throws SQLException {
        try {
            // Register JDBC driver (optional, as DriverManager does this automatically)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Database connection error");
        }
    }
}
