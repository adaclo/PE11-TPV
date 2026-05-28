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

