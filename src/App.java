import java.util.Scanner;
import utils.manageJSON;
import utils.manageDB;

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
        this.db = new manageDB("botiga_db"); 
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
                    System.out.println("Alta article...");
                    break;

                case 2:
                    System.out.println("Modificar article...");
                    break;

                case 3:
                    System.out.println("Esborrar article...");
                    break;

                case 4:
                    System.out.println("Consultar articles...");
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opció no vàlida.");
            }

        } while (opcio != 0);
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
                    System.out.println("Alta client...");
                    break;

                case 2:
                    System.out.println("Modificar client...");
                    break;

                case 3:
                    System.out.println("Esborrar client...");
                    break;

                case 4:
                    System.out.println("Consultar clients...");
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opció no vàlida.");
            }

        } while (opcio != 0);
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

