import java.sql.Connection;
import java.sql.Statement;

public class manageDB {
    static String url;
    static final String user = "root";
    static final String password = "";
    public static Connection conn;

    // Assignar la url de la base de dades
    public manageDB(String nameDB) {
        url = "jdbc:mysql://127.0.0.1:3306/" + nameDB + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    }

    // Connectar a la base de dades
    public void connectDB() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 
    public ResultSet queryUsers(String query) {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM clients");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}