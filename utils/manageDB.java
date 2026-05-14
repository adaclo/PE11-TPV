package utils;
import java.sql.*;

public class manageDB {
    static String URL;
    static final String USER = "root";
    static final String PASSWORD = "";
    public static Connection conn;

    // Assignar la url de la base de dades
    public manageDB(String nomBD) {
        URL = "jdbc:mysql://127.0.0.1:3306/" + nomBD + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
    }

    // Connectar a la base de dades
    public void establirConexio() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Consultar tots els clients de la base de dades
    public ResultSet consultaClients() {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM clients");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Consultar tots els articles de la base de dades
    public ResultSet consultaArticles() {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM articles");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Consultar totes les families de la base de dades
    public ResultSet consultaFamilies() {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM families");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Consultar tots els tiquets de la base de dades
    public ResultSet consultaTiquets() {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM tiquets");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Consultar totes les linies de factura de la base de dades
    public ResultSet consultaLiniesFactura() {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM linies_factura");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Insertar families a la base de dades
    public int inserirFamilia(String nom) {
        String sql = "INSERT IGNORE INTO families (nom) VALUES (?)";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nom);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Insertar clients a la base de dades
    public int inserirClient(String dni, String nom, String email, String telefon) {
        String sql = "INSERT INTO clients (dni, nom, email, telefon) VALUES (?, ?, ?, ?)";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, dni);
            ps.setString(2, nom);
            ps.setString(3, email);
            ps.setString(4, telefon);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Insertar articles a la base de dades
    public int inserirArticle(int id, String nom, String familia, Integer talla_coll, Integer amplada_pit, Integer talla_cintura, Integer llargada_camal, double preu_base, int iva, int stock) {
        String sql = "INSERT INTO articles (id, nom, familia, talla_coll, amplada_pit, talla_cintura, llargada_camal, preu_base, iva, stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, nom);
            ps.setString(3, familia);
            ps.setObject(4, talla_coll);
            ps.setObject(5, amplada_pit);
            ps.setObject(6, talla_cintura);
            ps.setObject(7, llargada_camal);
            ps.setDouble(8, preu_base);
            ps.setInt(9, iva);
            ps.setInt(10, stock);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Insertar tiquets a la base de dades
    public int inserirTiquet(String data_compra, String dni_client, double total_base, double total_iva, double total_final) {
        String sql = "INSERT INTO tiquets (data_compra, dni_client, total_base, total_iva, total_final) VALUES (?, ?, ?, ?, ?)";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, data_compra);
            ps.setString(2, dni_client);
            ps.setDouble(3, total_base);
            ps.setDouble(4, total_iva);
            ps.setDouble(5, total_final);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Insertar linies de factura a la base de dades
    public int inserirLiniaFactura(int id_tiquet, int id_article, int quantitat, double preu_base, int iva, double preu_final) {
        String sql = "INSERT INTO linies_factura (id_tiquet, id_article, quantitat, preu_base, iva, preu_final) VALUES (?, ?, ?, ?, ?, ?)";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id_tiquet);
            ps.setInt(2, id_article);
            ps.setInt(3, quantitat);
            ps.setDouble(4, preu_base);
            ps.setInt(5, iva);
            ps.setDouble(6, preu_final);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Consultar els clients de la base de dades amb vendes
    public ResultSet consultaVendesClient(String dni) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT clients.dni, clients.nom, COUNT(tiquets.id), SUM(tiquets.total_final) FROM clients INNER JOIN tiquets ON tiquets.dni_client = clients.dni WHERE clients.dni = ? GROUP BY clients.dni, clients.nom");

            ps.setString(1, dni);

            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Consultar els productes de la base de dades
    public ResultSet consultaVendesArticle(int id_article) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT articles.id, articles.nom, SUM(linies_factura.quantitat) FROM articles INNER JOIN linies_factura ON linies_factura.id_article = articles.id WHERE articles.id = ? GROUP BY articles.id, articles.nom");

            ps.setInt(1, id_article);

            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}