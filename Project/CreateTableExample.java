import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableExample {
    private static final String URL = "jdbc:mysql://localhost:3306/grocery_management"; // Database URL
    private static final String USER = "root"; // Default XAMPP username
    private static final String PASSWORD = ""; // Leave blank if no password is set

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

    public static void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS item (" +
                "item_id INT PRIMARY KEY AUTO_INCREMENT, " +
                "item_name VARCHAR(100) NOT NULL, " +
                "category VARCHAR(50), " +
                "price DOUBLE, " +
                "quantity INT" +
                ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            if (conn != null) {
                stmt.executeUpdate(createTableSQL);
                System.out.println("Table 'item' created or already exists.");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Error creating table.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createTable();
    }
}
