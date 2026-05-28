import java.util.Scanner;
import utils.manageJSON;
import utils.manageDB;
import java.sql.ResultSet;

public class App {

    private Scanner sc = new Scanner(System.in);
    private manageDB db;
    private manageJSON json;

    public static void main(String[] args) {
        App p = new App();
        p.inicialitzar();
        p.programa();
    }

    public void inicialitzar() {
        this.db = new manageDB("tpv_botiga");
        this.db.establirConexio(); 
        this.json = new manageJSON(this.db);
    }

    public void programa() {
        int opcio;

        do {
            mostrarMenu();
            opcio = llegirEnter("Escull una opció: ");

            switch (opcio) {
                case 1: importarArticles(); break;
                case 2: menuGestioArticles(); break;
                case 3: menuGestioClients(); break;
                case 4: tpv(); break;
                case 5: consultaVendesClient(); break;
                case 6: consultaVendesArticle(); break;
                case 7: calcularBeneficis(); break;
                case 8: recompraAutomatica(); break;
                case 0: System.out.println("Sortint..."); break;
                default: System.out.println("Opció no vàlida.");
            }

        } while (opcio != 0); 
    }

    public void mostrarMenu() {
        
        System.out.println("\n========== TPV BOTIGA ==========");
        System.out.println("1. Importació articles");
        System.out.println("2. Gestió d'articles");
        System.out.println("3. Gestió de clients");
        System.out.println("4. TPV");
        System.out.println("5. Consultes vendes per client");
        System.out.println("6. Consultes vendes per article");
        System.out.println("7. Calcula beneficis totals");
        System.out.println("8. Recompra automàtica articles");
        System.out.println("0. Sortir");
        System.out.println("================================");
    }

    public void menuGestioArticles() {

        int opcio;

        do {

            System.out.println("\n--- GESTIÓ ARTICLES ---");
            System.out.println("1. Alta article");
            System.out.println("2. Modificar article");
            System.out.println("3. Esborrar article");
            System.out.println("4. Consultar articles");
            System.out.println("0. Tornar");

            opcio = llegirEnter("Opció: ");

            switch (opcio) {

                case 1:
                    altaArticle();
                    break;

                case 2:
                    modificarArticle();
                    break;

                case 3:
                    esborrarArticle();
                    break;

                case 4:
                    consultarArticle();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opció no vàlida.");
            }

        } while (opcio != 0);
    }

    public void altaArticle() {
        System.out.println("\n--- ALTA ARTICLE ---");

        String nom = llegirText("Nom article: ");
        int id_familia = llegirEnter("ID familia (1 camisa, 2 pantaló): ");

        Integer talla_coll = null;
        Integer amplada_pit = null;
        Integer talla_cintura = null;
        Integer llargada_camal = null;

        if (id_familia == 1) {
            talla_coll = llegirEnter("Talla coll: ");
            amplada_pit = llegirEnter("Amplada pit: ");
        }

        if (id_familia == 2) {
            talla_cintura = llegirEnter("Talla cintura: ");
            llargada_camal = llegirEnter("Llargada camal: ");
        }

        double preu_base = llegirDouble("Preu base: ");
        int iva = llegirEnter("IVA: ");
        int stock = llegirEnter("Stock: ");

        int estat = db.inserirArticleAuto(nom, id_familia, talla_coll, amplada_pit, talla_cintura, llargada_camal, preu_base, iva, stock);

        if (estat == 1) {
            System.out.println("Article inserit correctament.");
        } else {
            System.out.println("No s'ha pogut inserir l'article.");
        }
    }

    public void modificarArticle() {
        System.out.println("\n--- MODIFICAR ARTICLE ---");

        int id = llegirEnter("ID article a modificar: ");

        if (!db.existeixArticle(id)) {
            System.out.println("No existeix cap article amb aquest ID.");
        } else {
            String nom = llegirText("Nou nom article: ");
            int id_familia = llegirEnter("Nou ID familia (1 camisa, 2 pantaló): ");

            Integer talla_coll = null;
            Integer amplada_pit = null;
            Integer talla_cintura = null;
            Integer llargada_camal = null;

            if (id_familia == 1) {
                talla_coll = llegirEnter("Nova talla coll: ");
                amplada_pit = llegirEnter("Nova amplada pit: ");
            }

            if (id_familia == 2) {
                talla_cintura = llegirEnter("Nova talla cintura: ");
                llargada_camal = llegirEnter("Nova llargada camal: ");
            }

            double preu_base = llegirDouble("Nou preu base: ");
            int iva = llegirEnter("Nou IVA: ");
            int stock = llegirEnter("Nou stock: ");

            int estat = db.actualitzarArticle(id, nom, id_familia, talla_coll, amplada_pit, talla_cintura, llargada_camal, preu_base, iva, stock);

            if (estat == 1) {
                System.out.println("Article modificat correctament.");
            } else {
                System.out.println("No s'ha pogut modificar l'article.");
            }
        }
    }

    public void esborrarArticle() {
        System.out.println("\n--- ESBORRAR ARTICLE ---");

        int id = llegirEnter("ID article a esborrar: ");

        if (!db.existeixArticle(id)) {
            System.out.println("No existeix cap article amb aquest ID.");
        } else {
            int estat = db.eliminarArticle(id);

            if (estat == 1) {
                System.out.println("Article esborrat correctament.");
            } else {
                System.out.println("No s'ha pogut esborrar l'article.");
            }
        }
    }

    public void consultarArticle() {
        System.out.println("\n--- CONSULTAR ARTICLE ---");

        int id = llegirEnter("ID article: ");

        try {
            ResultSet rs = db.consultaArticlePerId(id);

            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nom: " + rs.getString("nom"));
                System.out.println("ID familia: " + rs.getInt("id_familia"));
                System.out.println("Familia: " + rs.getString("nom_familia"));
                System.out.println("Talla coll: " + rs.getObject("talla_coll"));
                System.out.println("Amplada pit: " + rs.getObject("amplada_pit"));
                System.out.println("Talla cintura: " + rs.getObject("talla_cintura"));
                System.out.println("Llargada camal: " + rs.getObject("llargada_camal"));
                System.out.println("Preu base: " + rs.getDouble("preu_base"));
                System.out.println("IVA: " + rs.getInt("iva"));
                System.out.println("Stock: " + rs.getInt("stock"));
            } else {
                System.out.println("No existeix cap article amb aquest ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuGestioClients() {

        int opcio;

        do {

            System.out.println("\n--- GESTIÓ CLIENTS ---");
            System.out.println("1. Alta client");
            System.out.println("2. Modificar client");
            System.out.println("3. Esborrar client");
            System.out.println("4. Consultar clients");
            System.out.println("0. Tornar");

            opcio = llegirEnter("Opció: ");

            switch (opcio) {

                case 1:
                    altaClient();
                    break;

                case 2:
                    modificarClient();
                    break;

                case 3:
                    esborrarClient();
                    break;

                case 4:
                    consultarClients();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opció no vàlida.");
            }

        } while (opcio != 0);
    }

    private void altaClient() {
        System.out.println("\n--- ALTA NOU CLIENT ---");
        String dni = llegirText("DNI: ");
        if (db.existeixClient(dni)) {
            System.out.println("Error: Ja existeix un client amb aquest DNI.");
            return;
        }
        String nom = llegirText("Nom: ");
        String email = llegirText("Email: ");
        String tel = llegirText("Telèfon (9 dígits): ");

        int estat = db.inserirClient(dni, nom, email, tel);
        if (estat == 1) System.out.println("Client registrat correctament.");
        else System.out.println("Error en registrar el client.");
    }

    private void modificarClient() {
        System.out.println("\n--- MODIFICAR CLIENT ---");
        String dni = llegirText("Introdueix el DNI del client a modificar: ");
    
        if (!db.existeixClient(dni)) {
            System.out.println("Aquest client no existeix.");
            return;
        }

        String nouNom = llegirText("Nou Nom: ");
        String nouEmail = llegirText("Nou Email: ");
        String nouTel = llegirText("Nou Telèfon: ");

        int estat = db.actualitzarClient(dni, nouNom, nouEmail, nouTel);
        if (estat == 1) System.out.println("Dades actualitzades.");
        else System.out.println("Error en l'actualització.");
    }

    private void esborrarClient() {
        String dni = llegirText("DNI del client a esborrar: ");
        if (dni.equals("000")) {
            System.out.println("No es pot esborrar el client genèric.");
            return;
        }
    
        int estat = db.eliminarClient(dni);
        if (estat == 1) System.out.println("Client eliminat.");
        else System.out.println("Error: Potser el client té tiquets associats.");
    }

    private void consultarClients() {
        System.out.println("\n--- LLISTAT DE CLIENTS ---");
        try (java.sql.ResultSet rs = db.consultaClients()) {
            System.out.printf("%-10s | %-20s | %-25s | %-10s\n", "DNI", "NOM", "EMAIL", "TELÈFON");
            System.out.println("---------------------------------------------------------------------------");
            while (rs != null && rs.next()) {
                System.out.printf("%-10s | %-20s | %-25s | %-10s\n", 
                    rs.getString("dni"), 
                    rs.getString("nom"), 
                    rs.getString("email"), 
                    rs.getString("telefon"));
            }
        } catch (Exception e) {
            System.out.println("Error al llistar: " + e.getMessage());
        }
    }

    public void importarArticles() {
        System.out.println("\n--- PROCÉS D'IMPORTACIÓ ---");
        // Crida als mètodes de manageJSON que ja hem preparat
        json.carregarJSON();
        json.comptarArticles();
        json.importarArticlesBD();
        System.out.println("---------------------------\n");
    }

    public void tpv() {
        System.out.println("TPV...");
    }

    public void consultaVendesClient() {
        System.out.println("Consulta vendes client...");
    }

    public void consultaVendesArticle() {
        System.out.println("Consulta vendes article...");
    }

    public void calcularBeneficis() {
        System.out.println("Calculant beneficis...");
    }

    public void recompraAutomatica() {
        System.out.println("Recompra automàtica...");
    }

    public String llegirText(String missatge) {
        System.out.print(missatge);
        return sc.nextLine();
    }

    public int llegirEnter(String missatge) {
        int numero = 0;
        boolean esValid = false;

        while (!esValid) {
            try {
                System.out.print(missatge);
                String input = sc.nextLine();
                numero = Integer.parseInt(input);
                esValid = true; // Si arribem aquí, el número és correcte
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Has d'introduir un número sencer vàlid.");
            }
        }
        return numero;
    }

    public double llegirDouble(String missatge) {
        double numero = 0;
        boolean esValid = false;

        while (!esValid) {
            try {
                System.out.print(missatge);
                String input = sc.nextLine().replace(',', '.'); // Accepta tant punts com comes
                numero = Double.parseDouble(input);
                esValid = true;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Introdueix un valor decimal correcte (Ex: 10.50).");
            }
        }
        return numero;
    }
}

