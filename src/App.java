import java.sql.ResultSet;
import java.util.Scanner;
import utils.manageDB;
import utils.manageJSON;

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

        int id_familia = 0;

        do {
            id_familia = llegirEnter("ID familia (1 camisa, 2 pantaló): ");
            if (id_familia != 1 && id_familia != 2) {
                System.out.println("(!) ID familia no vàlid. Introdueix 1 per camisa o 2 per pantaló.");
            }
        } while (id_familia != 1 && id_familia != 2);

        Integer talla_coll = null;
        Integer amplada_pit = null;
        Integer talla_cintura = null;
        Integer llargada_camal = null;

        if (id_familia == 1) {
            do {
                talla_coll = llegirEnter("Talla coll: ");
                if (talla_coll < 36 || talla_coll > 52) {
                    System.out.println("(!) Talla coll no vàlida. Ha d'estar entre 36 i 52.");
                }
            } while (talla_coll < 36 || talla_coll > 52);

            do {
                amplada_pit = llegirEnter("Amplada pit: ");
                if (amplada_pit < 10 || amplada_pit > 15) {
                    System.out.println("(!) Amplada pit no vàlida. Ha d'estar entre 10 i 15.");
                }
            } while (amplada_pit < 10 || amplada_pit > 15);
        }

        if (id_familia == 2) {
            do {
                talla_cintura = llegirEnter("Talla cintura: ");
                if (talla_cintura < 24 || talla_cintura > 56) {
                    System.out.println("(!) Talla cintura no vàlida. Ha d'estar entre 24 i 56.");
                }
            } while (talla_cintura < 24 || talla_cintura > 56);

            do {
                llargada_camal = llegirEnter("Llargada camal: ");
                if (llargada_camal < 32 || llargada_camal > 46) {
                    System.out.println("(!) Llargada camal no vàlida. Ha d'estar entre 32 i 46.");
                }
            } while (llargada_camal < 32 || llargada_camal > 46);
        }

        double preu_base = llegirDouble("Preu base: ");

        int iva = 0;

        do {
            iva = llegirEnter("IVA: ");
            if (iva < 4 || iva > 21) {
                System.out.println("(!) IVA no vàlid. Ha d'estar entre 4 i 21.");
            }
        } while (iva < 4 || iva > 21);

        int stock = 0;

        do {
            stock = llegirEnter("Stock: ");
            if (stock < 0) {
                System.out.println("(!) Stock no vàlid. No pot ser negatiu.");
            }
        } while (stock < 0);

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
            
            try {
                ResultSet rs = db.consultaArticlePerId(id);

                if (rs.next()) {
                    String nom = rs.getString("nom");
                    int id_familia = rs.getInt("id_familia");
                    Integer talla_coll = (Integer) rs.getObject("talla_coll");
                    Integer amplada_pit = (Integer) rs.getObject("amplada_pit");
                    Integer talla_cintura = (Integer) rs.getObject("talla_cintura");
                    Integer llargada_camal = (Integer) rs.getObject("llargada_camal");
                    double preu_base = rs.getDouble("preu_base");
                    int iva = rs.getInt("iva");
                    int stock = rs.getInt("stock");

                    int opcio = 0;

                    do {
                        System.out.println("--------------------------------");
                        System.out.println("\n--- DADES ACTUALS ---");
                        consultarArticlePerID(id);
                        System.out.println("\n--- MODIFICAR ARTICLE ID " + id + " ---");
                        System.out.println("1. Modificar nom");
                        System.out.println("2. Modificar familia");
                        System.out.println("3. Modificar talla coll");
                        System.out.println("4. Modificar amplada pit");
                        System.out.println("5. Modificar talla cintura");
                        System.out.println("6. Modificar llargada camal");
                        System.out.println("7. Modificar preu base");
                        System.out.println("8. Modificar IVA");
                        System.out.println("9. Modificar stock");
                        System.out.println("0. Tornar");

                        opcio = llegirEnter("Opció: ");

                        switch (opcio) {

                            case 1:
                                nom = llegirText("Nou nom article: ");
                                break;

                            case 2:
                                do {
                                    id_familia = llegirEnter("Nou ID familia (1 camisa, 2 pantaló): ");
                                    if (id_familia != 1 && id_familia != 2) {
                                        System.out.println("(!) ID familia no vàlid. Introdueix 1 per camisa o 2 per pantaló.");
                                    }
                                } while (id_familia != 1 && id_familia != 2);

                                talla_coll = null;
                                amplada_pit = null;
                                talla_cintura = null;
                                llargada_camal = null;

                                if (id_familia == 1) {
                                    do {
                                        talla_coll = llegirEnter("Nova talla coll: ");
                                        if (talla_coll < 36 || talla_coll > 52) {
                                            System.out.println("(!) Talla coll no vàlida. Ha d'estar entre 36 i 52.");
                                        }
                                    } while (talla_coll < 36 || talla_coll > 52);

                                    do {
                                        amplada_pit = llegirEnter("Nova amplada pit: ");
                                        if (amplada_pit < 10 || amplada_pit > 15) {
                                            System.out.println("(!) Amplada pit no vàlida. Ha d'estar entre 10 i 15.");
                                        }
                                    } while (amplada_pit < 10 || amplada_pit > 15);
                                }

                                if (id_familia == 2) {
                                    do {
                                        talla_cintura = llegirEnter("Nova talla cintura: ");
                                        if (talla_cintura < 24 || talla_cintura > 56) {
                                            System.out.println("(!) Talla cintura no vàlida. Ha d'estar entre 24 i 56.");
                                        }
                                    } while (talla_cintura < 24 || talla_cintura > 56);

                                    do {
                                        llargada_camal = llegirEnter("Nova llargada camal: ");
                                        if (llargada_camal < 32 || llargada_camal > 46) {
                                            System.out.println("(!) Llargada camal no vàlida. Ha d'estar entre 32 i 46.");
                                        }
                                    } while (llargada_camal < 32 || llargada_camal > 46);
                                }
                                break;

                            case 3:
                                if (id_familia == 1) {
                                    do {
                                        talla_coll = llegirEnter("Nova talla coll: ");
                                        if (talla_coll < 36 || talla_coll > 52) {
                                            System.out.println("(!) Talla coll no vàlida. Ha d'estar entre 36 i 52.");
                                        }
                                    } while (talla_coll < 36 || talla_coll > 52);
                                } else {
                                    System.out.println("Aquest camp només és per camises.");
                                }
                                break;

                            case 4:
                                if (id_familia == 1) {
                                    do {
                                        amplada_pit = llegirEnter("Nova amplada pit: ");
                                        if (amplada_pit < 10 || amplada_pit > 15) {
                                            System.out.println("(!) Amplada pit no vàlida. Ha d'estar entre 10 i 15.");
                                        }
                                    } while (amplada_pit < 10 || amplada_pit > 15);
                                } else {
                                    System.out.println("Aquest camp només és per camises.");
                                }
                                break;

                            case 5:
                                if (id_familia == 2) {
                                    do {
                                        talla_cintura = llegirEnter("Nova talla cintura: ");
                                        if (talla_cintura < 24 || talla_cintura > 56) {
                                            System.out.println("(!) Talla cintura no vàlida. Ha d'estar entre 24 i 56.");
                                        }
                                    } while (talla_cintura < 24 || talla_cintura > 56);
                                } else {
                                    System.out.println("Aquest camp només és per pantalons.");
                                }
                                break;

                            case 6:
                                if (id_familia == 2) {
                                    do {
                                        llargada_camal = llegirEnter("Nova llargada camal: ");
                                        if (llargada_camal < 32 || llargada_camal > 46) {
                                            System.out.println("(!) Llargada camal no vàlida. Ha d'estar entre 32 i 46.");
                                        }
                                    } while (llargada_camal < 32 || llargada_camal > 46);
                                } else {
                                    System.out.println("Aquest camp només és per pantalons.");
                                }
                                break;

                            case 7:
                                preu_base = llegirDouble("Nou preu base: ");
                                break;

                            case 8:
                                do {
                                    iva = llegirEnter("Nou IVA: ");
                                    if (iva < 4 || iva > 21) {
                                        System.out.println("(!) IVA no vàlid. Ha d'estar entre 4 i 21.");
                                    }
                                } while (iva < 4 || iva > 21);
                                break;

                            case 9:
                                do {
                                    stock = llegirEnter("Nou stock: ");
                                    if (stock < 0) {
                                        System.out.println("(!) Stock no vàlid. No pot ser negatiu.");
                                    }
                                } while (stock < 0);
                                break;

                            case 0:
                                break;

                            default:
                                System.out.println("Opció no vàlida.");
                        }

                        if (opcio >= 1 && opcio <= 9) {
                            int estat = db.actualitzarArticle(id, nom, id_familia, talla_coll, amplada_pit, talla_cintura, llargada_camal, preu_base, iva, stock);

                            if (estat == 1) {
                                System.out.println("Article modificat correctament.");
                            } else {
                                System.out.println("No s'ha pogut modificar l'article.");
                            }
                        }

                    } while (opcio != 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
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
        System.out.println("----------------");
        consultarArticlePerID(id);
    }

    public void consultarArticlePerID(int id) {
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
    
        String dni;
        while (true) {
            dni = llegirText("DNI (8 números i 1 lletra): ");
            if (dni.matches("^[0-9]{8}[A-Z]$") || dni.equals("000")) break;
            System.out.println("ERROR: El format del DNI no és vàlid (Ex: 12345678Z).");
        }

        if (db.existeixClient(dni)) {
            System.out.println("Error: Ja existeix un client amb aquest DNI.");
            return;
        }

        String nom;
        while (true) {
            nom = llegirText("Nom (màxim 30 caràcters): ");
            if (nom.length() > 0 && nom.length() <= 30) break;
            System.out.println("ERROR: El nom ha de tenir entre 1 i 30 caràcters.");
        }

        String email = llegirText("Email: ");

        String tel;
        while (true) {
            tel = llegirText("Telèfon (9 dígits): ");
        
            if (tel.matches("^[0-9]{9}$")) {
                break;
            } else {
                System.out.println("ERROR: El telèfon ha de tenir exactament 9 números i cap lletra.");
            }
        }
        int estat = db.inserirClient(dni, nom, email, tel);

        if (estat == 1) {System.out.println("Client registrat correctament.");}
        else {System.out.println("Error en registrar el client.");}
    }

    private void modificarClient() {
        System.out.println("\n--- MODIFICAR CLIENT ---");
        String dni = llegirText("Introdueix el DNI del client a modificar: ");
    
        try {
            ResultSet rs = db.consultaClientPerDni(dni);
            if (!rs.next()) {
                System.out.println("Aquest client no existeix.");
                return;
            }

            // Guardem les dades actuals per si no es volen canviar totes
            String nomAct = rs.getString("nom");
            String emailAct = rs.getString("email");
            String telAct = rs.getString("telefon");

            int opcioMod;
            do {
                System.out.println("\nClient: " + nomAct + " [" + dni + "]");
                System.out.println("1. Modificar NOM (Actual: " + nomAct + ")");
                System.out.println("2. Modificar EMAIL (Actual: " + emailAct + ")");
                System.out.println("3. Modificar TELÈFON (Actual: " + telAct + ")");
                System.out.println("4. Modificar-ho TOT");
                System.out.println("0. Tornar / Finalitzar");
                opcioMod = llegirEnter("Què vols fer?: ");

                switch (opcioMod) {
                    case 1:
                        nomAct = llegirText("Nou Nom: ");
                        db.actualitzarClient(dni, nomAct, emailAct, telAct);
                        break;
                    case 2:
                        emailAct = llegirText("Nou Email: ");
                        db.actualitzarClient(dni, nomAct, emailAct, telAct);
                        break;
                    case 3:
                        telAct = llegirText("Nou Telèfon: ");
                        db.actualitzarClient(dni, nomAct, emailAct, telAct);
                        break;
                    case 4:
                        nomAct = llegirText("Nou Nom: ");
                        emailAct = llegirText("Nou Email: ");
                        telAct = llegirText("Nou Telèfon: ");
                        db.actualitzarClient(dni, nomAct, emailAct, telAct);
                        break;
                }
                if (opcioMod >= 1 && opcioMod <= 4) System.out.println("Canvi realitzat.");
                
            } while (opcioMod != 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        int opcio;
            System.out.println("\n--- CONSULTAR CLIENTS ---");
            System.out.println("1. Consultar UN client (per DNI)");
            System.out.println("2. Consultar TOTS els clients");
            System.out.println("0. Tornar");
            opcio = llegirEnter("Opció: ");

            if (opcio == 1) {
                String dni = llegirText("Introdueix DNI: ");
                mostrarTaulaClients(db.consultaClientPerDni(dni));
            } else if (opcio == 2) {
                mostrarTaulaClients(db.consultaClients());
            }
    }

    private void mostrarTaulaClients(ResultSet rs) {
        try {
            System.out.printf("\n%-10s | %-20s | %-25s | %-10s\n", "DNI", "NOM", "EMAIL", "TELÈFON");
            System.out.println("---------------------------------------------------------------------------");
            boolean hihaDades = false;
            while (rs != null && rs.next()) {
                hihaDades = true;
                System.out.printf("%-10s | %-20s | %-25s | %-10s\n", 
                    rs.getString("dni"), 
                    rs.getString("nom"), 
                    rs.getString("email"), 
                    rs.getString("telefon"));
            }
            if (!hihaDades) System.out.println("No s'han trobat resultats.");
        } catch (Exception e) {
            System.out.println("Error al mostrar dades: " + e.getMessage());
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
        System.out.println("\n--- TPV ---");

        String dni_client = llegirText("DNI client: ");

        if (!db.existeixClient(dni_client)) {
            System.out.println("Client no trobat. S'utilitzarà el client genèric 000.");
            dni_client = "000";
        }

        int[] ids_articles = new int[100];
        int[] quantitats = new int[100];
        double[] preus_base = new double[100];
        int[] ives = new int[100];
        double[] preus_finals = new double[100];

        int comptador = 0;
        int id_article = -1;

        double total_base = 0;
        double total_iva = 0;
        double total_final = 0;

        do {
            id_article = llegirEnter("ID article (0 per finalitzar): ");

            if (id_article != 0) {
                if (!db.existeixArticle(id_article)) {
                    System.out.println("No existeix cap article amb aquest ID.");
                } else {
                    int stock_actual = db.consultaStockArticle(id_article);

                    if (stock_actual <= 0) {
                        System.out.println("Aquest article no té stock disponible.");
                    } else {
                        int quantitat = llegirEnter("Quantitat: ");

                        if (quantitat <= 0) {
                            System.out.println("La quantitat ha de ser superior a 0.");
                        } else if (quantitat > stock_actual) {
                            System.out.println("No hi ha prou stock. Stock actual: " + stock_actual);
                        } else {
                            try {
                                ResultSet rs = db.consultaArticlePerId(id_article);

                                if (rs.next()) {
                                    double preu_article = rs.getDouble("preu_base");
                                    int iva_article = rs.getInt("iva");

                                    double linia_base = preu_article * quantitat;
                                    double linia_iva = calcularIva(preu_article, iva_article, quantitat);
                                    double linia_final = calcularPreuFinal(preu_article, iva_article, quantitat);

                                    ids_articles[comptador] = id_article;
                                    quantitats[comptador] = quantitat;
                                    preus_base[comptador] = linia_base;
                                    ives[comptador] = iva_article;
                                    preus_finals[comptador] = linia_final;

                                    total_base = total_base + linia_base;
                                    total_iva = total_iva + linia_iva;
                                    total_final = total_final + linia_final;

                                    comptador++;

                                    System.out.println("Article afegit a la venda.");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        } while (id_article != 0);

        if (comptador == 0) {
            System.out.println("No s'ha registrat cap venda.");
        } else {
            int confirmar = llegirEnter("Confirmar venda? (1 sí, 0 no): ");

            if (confirmar == 1) {
                java.time.LocalDate avui = java.time.LocalDate.now();

                int estatTiquet = db.inserirTiquet(avui.toString(), dni_client, total_base, total_iva, total_final);

                if (estatTiquet == 1) {
                    int id_tiquet = db.consultaUltimTiquet();

                    for (int i = 0; i < comptador; i++) {
                        db.inserirLiniaFactura(id_tiquet, ids_articles[i], quantitats[i], preus_base[i], ives[i], preus_finals[i]);
                        db.restarStockArticle(ids_articles[i], quantitats[i]);
                    }

                    System.out.println("Venda registrada correctament.");
                    imprimirTiquet(id_tiquet, dni_client, ids_articles, quantitats, preus_base, ives, preus_finals, comptador, total_base, total_iva, total_final);
                } else {
                    System.out.println("No s'ha pogut registrar el tiquet.");
                }
            } else {
                System.out.println("Venda cancel·lada.");
            }
        }
    }

    public void imprimirTiquet(int id_tiquet, String dni_client, int[] ids_articles, int[] quantitats, double[] preus_base, int[] ives, double[] preus_finals, int comptador, double total_base, double total_iva, double total_final) {
        System.out.println("\n========== TIQUET ==========");
        System.out.println("Tiquet: " + id_tiquet);
        System.out.println("Client: " + dni_client);
        System.out.println("----------------------------");

        for (int i = 0; i < comptador; i++) {
            System.out.println("Article ID: " + ids_articles[i]);
            System.out.println("Quantitat: " + quantitats[i]);
            System.out.println("Base: " + preus_base[i]);
            System.out.println("IVA: " + ives[i] + "%");
            System.out.println("Final: " + preus_finals[i]);
            System.out.println("----------------------------");
        }

        System.out.println("Total base: " + total_base);
        System.out.println("Total IVA: " + total_iva);
        System.out.println("Total final: " + total_final);
        System.out.println("============================\n");
    }

    public double calcularPreuFinal(double preu_base, int iva, int quantitat) {
        double total_base = preu_base * quantitat;
        double total_iva = total_base * iva / 100;
        double total_final = total_base + total_iva;
        return total_final;
    }

    public double calcularIva(double preu_base, int iva, int quantitat) {
        double total_base = preu_base * quantitat;
        double total_iva = total_base * iva / 100;
        return total_iva;
    }

    public void consultaVendesClient() {

    sc.nextLine();

    System.out.println("\n=== CONSULTA VENDES CLIENT ===");

    System.out.print("DNI client: ");
    String dni = sc.nextLine();

    try {

        ResultSet rs = db.consultaVendesClient(dni);

        if (rs.next()) {

            System.out.println("\nDNI: " + rs.getString("dni"));
            System.out.println("Nom: " + rs.getString("nom"));
            System.out.println("Total tiquets: " + rs.getInt("total_tiquets"));
            System.out.println("Total despesa: " + rs.getDouble("total_despesa") + "€");

        } else {

            System.out.println("No hi ha vendes d'aquest client");
        }

    } catch (Exception e) {

        e.printStackTrace();
    }
}

    public void consultaVendesArticle() {
        System.out.println("Consulta vendes article...");
    }

    public void calcularBeneficis() {
        System.out.println("\n--- CÀLCUL DE BENEFICIS ---");

        int opcio = 0;
        String ordre = "DESC";

        do {
            System.out.println("1. Ordenar beneficis de major a menor");
            System.out.println("2. Ordenar beneficis de menor a major");
            opcio = llegirEnter("Opció: ");

            if (opcio != 1 && opcio != 2) {
                System.out.println("Opció no vàlida.");
            }
        } while (opcio != 1 && opcio != 2);

        if (opcio == 1) {
            ordre = "DESC";
        }

        if (opcio == 2) {
            ordre = "ASC";
        }

        try {
            ResultSet rs = db.consultaBeneficisArticles(ordre);

            System.out.println("\n--- INFORME DE BENEFICIS ---");

            while (rs.next()) {
                System.out.println("--------------------------------");
                System.out.println("ID article: " + rs.getInt("id"));
                System.out.println("Nom: " + rs.getString("nom"));
                System.out.println("Família: " + rs.getString("familia"));
                System.out.println("Preu base: " + rs.getDouble("preu_base"));
                System.out.println("Quantitat venuda: " + rs.getInt("quantitat_venuda"));
                System.out.println("Preu cost unitari: " + rs.getDouble("preu_cost"));
                System.out.println("Total vendes sense IVA: " + rs.getDouble("total_vendes"));
                System.out.println("Benefici: " + rs.getDouble("benefici"));
            }

            System.out.println("--------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
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

