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

    // Consultar un client per DNI
    public ResultSet consultaClientPerDni(String dni) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM clients WHERE dni = ?");

            ps.setString(1, dni);

            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Comprovar si existeix un client
    public boolean existeixClient(String dni) {
        boolean existeix = false;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT dni FROM clients WHERE dni = ?");

            ps.setString(1, dni);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                existeix = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existeix;
    }

    // Consultar tots els articles de la base de dades
    public ResultSet consultaArticles() {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT articles.id, articles.nom, articles.id_familia, families.nom AS nom_familia, articles.talla_coll, articles.amplada_pit, articles.talla_cintura, articles.llargada_camal, articles.preu_base, articles.iva, articles.stock FROM articles JOIN families ON articles.id_familia = families.id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Consultar un article per id
    public ResultSet consultaArticlePerId(int id) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT articles.id, articles.nom, articles.id_familia, families.nom AS nom_familia, articles.talla_coll, articles.amplada_pit, articles.talla_cintura, articles.llargada_camal, articles.preu_base, articles.iva, articles.stock FROM articles JOIN families ON articles.id_familia = families.id WHERE articles.id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Comprovar si existeix un article
    public boolean existeixArticle(int id) {
        boolean existeix = false;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM articles WHERE id = ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                existeix = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existeix;
    }

    // Consultar totes les camises de la base de dades
    public ResultSet consultaCamises() {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT articles.id, articles.nom, articles.id_familia, families.nom AS nom_familia, articles.talla_coll, articles.amplada_pit, articles.preu_base, articles.iva, articles.stock FROM articles JOIN families ON articles.id_familia = families.id WHERE families.nom = 'camisa'");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Consultar tots els pantalons de la base de dades
    public ResultSet consultaPantalons() {
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT articles.id, articles.nom, articles.id_familia, families.nom AS nom_familia, articles.talla_cintura, articles.llargada_camal, articles.preu_base, articles.iva, articles.stock FROM articles JOIN families ON articles.id_familia = families.id WHERE families.nom = 'pantaló'");
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

    // Consultar l'id d'una familia pel seu nom
    public int consultaIdFamilia(String nom) {
        ResultSet rs = null;
        int id = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM families WHERE nom = ?");

            ps.setString(1, nom);

            rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    // Consultar el nom d'una familia pel seu id
    public String consultaNomFamilia(int id_familia) {
        ResultSet rs = null;
        String nom = "";
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT nom FROM families WHERE id = ?");

            ps.setInt(1, id_familia);

            rs = ps.executeQuery();

            if (rs.next()) {
                nom = rs.getString("nom");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nom;
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

    // Actualitzar clients a la base de dades
    public int actualitzarClient(String dni, String nom, String email, String telefon) {
        String sql = "UPDATE clients SET nom = ?, email = ?, telefon = ? WHERE dni = ?";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nom);
            ps.setString(2, email);
            ps.setString(3, telefon);
            ps.setString(4, dni);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Eliminar clients de la base de dades
    public int eliminarClient(String dni) {
        String sql = "DELETE FROM clients WHERE dni = ?";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, dni);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Insertar articles a la base de dades
    public int inserirArticle(int id, String nom, int id_familia, Integer talla_coll, Integer amplada_pit, Integer talla_cintura, Integer llargada_camal, double preu_base, int iva, int stock) {
        String sql = "INSERT INTO articles (id, nom, id_familia, talla_coll, amplada_pit, talla_cintura, llargada_camal, preu_base, iva, stock) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, nom);
            ps.setInt(3, id_familia);
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

    // Actualitzar articles a la base de dades
    public int actualitzarArticle(int id, String nom, int id_familia, Integer talla_coll, Integer amplada_pit, Integer talla_cintura, Integer llargada_camal, double preu_base, int iva, int stock) {
        String sql = "UPDATE articles SET nom = ?, id_familia = ?, talla_coll = ?, amplada_pit = ?, talla_cintura = ?, llargada_camal = ?, preu_base = ?, iva = ?, stock = ? WHERE id = ?";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, nom);
            ps.setInt(2, id_familia);
            ps.setObject(3, talla_coll);
            ps.setObject(4, amplada_pit);
            ps.setObject(5, talla_cintura);
            ps.setObject(6, llargada_camal);
            ps.setDouble(7, preu_base);
            ps.setInt(8, iva);
            ps.setInt(9, stock);
            ps.setInt(10, id);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Eliminar articles de la base de dades
    public int eliminarArticle(int id) {
        String sql = "DELETE FROM articles WHERE id = ?";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

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

    // Consultar l'ultim tiquet de la base de dades
    public int consultaUltimTiquet() {
        ResultSet rs = null;
        int id = 0;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT MAX(id) AS id FROM tiquets");

            if (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
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

    // Consultar stock d'un article
    public int consultaStockArticle(int id_article) {
        ResultSet rs = null;
        int stock = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT stock FROM articles WHERE id = ?");

            ps.setInt(1, id_article);

            rs = ps.executeQuery();

            if (rs.next()) {
                stock = rs.getInt("stock");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stock;
    }

    // Actualitzar stock d'un article
    public int actualitzarStockArticle(int id_article, int stock) {
        String sql = "UPDATE articles SET stock = ? WHERE id = ?";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, stock);
            ps.setInt(2, id_article);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Restar stock d'un article
    public int restarStockArticle(int id_article, int quantitat) {
        String sql = "UPDATE articles SET stock = stock - ? WHERE id = ? AND stock >= ?";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, quantitat);
            ps.setInt(2, id_article);
            ps.setInt(3, quantitat);

            ps.executeUpdate();
            estat = 1;
        } catch (Exception e) {
            e.printStackTrace();
            estat = 0;
        }
        return estat;
    }

    // Sumar stock d'un article
    public int sumarStockArticle(int id_article, int quantitat) {
        String sql = "UPDATE articles SET stock = stock + ? WHERE id = ?";
        int estat = 0;
        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, quantitat);
            ps.setInt(2, id_article);

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
            PreparedStatement ps = conn.prepareStatement("SELECT clients.dni, clients.nom, COUNT(tiquets.id) AS num_tiquets, COALESCE(SUM(tiquets.total_final), 0) AS total_despesa FROM clients LEFT JOIN tiquets ON tiquets.dni_client = clients.dni WHERE clients.dni = ? GROUP BY clients.dni, clients.nom");

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
            PreparedStatement ps = conn.prepareStatement("SELECT articles.id, articles.nom, COALESCE(SUM(linies_factura.quantitat), 0) AS quantitat_venuda FROM articles LEFT JOIN linies_factura ON linies_factura.id_article = articles.id WHERE articles.id = ? GROUP BY articles.id, articles.nom");

            ps.setInt(1, id_article);

            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Consultar beneficis dels articles
    public ResultSet consultaBeneficisArticles(String ordre) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT articles.id, articles.nom, families.nom AS familia, articles.preu_base, COALESCE(SUM(linies_factura.quantitat), 0) AS quantitat_venuda, CASE WHEN families.nom = 'pantaló' THEN articles.preu_base * 0.30 + articles.llargada_camal * 0.2 WHEN families.nom = 'camisa' THEN articles.preu_base * 0.35 + articles.talla_coll * 0.3 ELSE 0 END AS preu_cost, COALESCE(SUM(linies_factura.preu_final), 0) AS total_vendes, COALESCE(SUM(linies_factura.preu_final), 0) - (COALESCE(SUM(linies_factura.quantitat), 0) * CASE WHEN families.nom = 'pantaló' THEN articles.preu_base * 0.30 + articles.llargada_camal * 0.2 WHEN families.nom = 'camisa' THEN articles.preu_base * 0.35 + articles.talla_coll * 0.3 ELSE 0 END) AS benefici FROM articles JOIN families ON articles.id_familia = families.id LEFT JOIN linies_factura ON linies_factura.id_article = articles.id GROUP BY articles.id, articles.nom, families.nom, articles.preu_base, articles.talla_coll, articles.llargada_camal ORDER BY benefici " + ordre);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    // Consultar articles per sota d'un stock indicat
    public ResultSet consultaArticlesSotaStock(int stock) {
        ResultSet rs = null;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT articles.id, articles.nom, families.nom AS familia, articles.stock FROM articles JOIN families ON articles.id_familia = families.id WHERE articles.stock < ?");

            ps.setInt(1, stock);

            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}