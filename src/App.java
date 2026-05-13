import java.util.Scanner;

public class App {

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        App p = new App();
        p.programa();
    }
    public void programa() {
        int opcio;

        do {

            mostrarMenu();
            opcio = llegirEnter("Escull una opció: ");

            switch (opcio) {

                case 1:
                    importarArticles();
                    break;

                case 2:
                    menuGestioArticles();
                    break;

                case 3:
                    menuGestioClients();
                    break;

                case 4:
                    tpv();
                    break;

                case 5:
                    consultaVendesClient();
                    break;

                case 6:
                    consultaVendesArticle();
                    break;

                case 7:
                    calcularBeneficis();
                    break;

                case 8:
                    recompraAutomatica();
                    break;

                case 0:
                    System.out.println("Sortint de l'aplicació...");
                    break;

                default:
                    System.out.println("Opció no vàlida.");
            }

        } while (opcio != 0);
    }

    public static void mostrarMenu() {

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

    public static void menuGestioArticles() {

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

    public static void menuGestioClients() {

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


    public static void importarArticles() {
        System.out.println("Importació d'articles...");
    }

    public static void tpv() {
        System.out.println("TPV...");
    }

    public static void consultaVendesClient() {
        System.out.println("Consulta vendes client...");
    }

    public static void consultaVendesArticle() {
        System.out.println("Consulta vendes article...");
    }

    public static void calcularBeneficis() {
        System.out.println("Calculant beneficis...");
    }

    public static void recompraAutomatica() {
        System.out.println("Recompra automàtica...");
    }

    public static int llegirEnter(String missatge) {

        int numero;

        while (true) {

            try {

                System.out.print(missatge);
                numero = Integer.parseInt(sc.nextLine());
                return numero;

            } catch (NumberFormatException e) {

                System.out.println("Introdueix un número vàlid.");
            }
        }
    }
}

