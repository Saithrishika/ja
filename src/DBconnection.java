import java.sql.*;

public class DBconnection {
    public static Connection connect() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/exam_db";
        String user = "root";
        String pass = ""; // Your MySQL password
        return DriverManager.getConnection(url, user, pass);
    }
}
