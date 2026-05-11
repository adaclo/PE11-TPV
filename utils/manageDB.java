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

    // Consultar els clients de la base de dades amb vendes
    public ResultSet queryUsers(String dni) {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT dni, nom, count(*), sum(total_final) FROM clients inner join tiquets on dni_client = dni WHERE dni = '" + dni + "' group by dni");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}