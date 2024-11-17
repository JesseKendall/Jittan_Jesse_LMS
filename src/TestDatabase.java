import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDatabase {
    public static void main(String[] args) throws SQLException {
        // test path to db
        String url = "jdbc:sqlite:C:/sqlite/library.db";

        // test connection
        try(Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connection to SQLite successful.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
