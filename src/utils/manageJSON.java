package utils;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class manageJSON {
    Object obj = null;
    JSONArray articles = null;
    manageDB db = null;

    // Assignar la base de dades que farem servir
    public manageJSON(manageDB db) {
        this.db = db;
    }

    // Carregar el fitxer JSON d'articles
    public void carregarJSON() {
        JSONParser parser = new JSONParser();
        try {
            obj = parser.parse(new FileReader("json/articles.json"));
            articles = (JSONArray) obj;
            System.out.println("(+) JSON carregat correctament");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Comptar el nombre total d'articles del JSON
    public void comptarArticles() {
        if (articles != null) {
            System.out.println("Hi ha un total de " + articles.size() + " articles");
        } else {
            System.out.println("Primer has de carregar el JSON");
        }
    }

    // Comptar articles per familia
    public void comptarFamilies() {
        int camises = 0;
        int pantalons = 0;
        try {
            for (Object o:articles) {
                JSONObject article = (JSONObject) o;
                String familia = (String) article.get("familia");

                if (familia.equals("camisa")) {
                    camises++;
                }

                if (familia.equals("pantaló")) {
                    pantalons++;
                }
            }

            System.out.println("Camises a carregar: " + camises);
            System.out.println("Pantalons a carregar: " + pantalons);
        } catch (Exception e) {
            System.out.println("Primer has de carregar el JSON");
        }
    }

    // Mostrar tots els articles del JSON
    public void mostrarArticles() {
        try {
            for (Object o:articles) {
                JSONObject article = (JSONObject) o;

                System.out.println("ID: " + article.get("id"));
                System.out.println("Nom: " + article.get("nom"));
                System.out.println("Familia: " + article.get("familia"));
                System.out.println("Preu base: " + article.get("preu_base"));
                System.out.println("IVA: " + article.get("iva"));
                System.out.println("Stock: " + article.get("stock"));
                System.out.println("-----------------------------");
            }
        } catch (Exception e) {
            System.out.println("Primer has de carregar el JSON");
        }
    }

    // Importar articles del JSON a la base de dades
    public void importarArticlesBD() {
        int afegits = 0;
        int actualitzats = 0;
        try {
         if (articles == null) carregarJSON();

            for (Object o : articles) {
            JSONObject article = (JSONObject) o;

            // 1. Extraure dades genèriques
            int id = getInt(article, "id");
            String nom = (String) article.get("nom");
            String nomFamilia = (String) article.get("familia"); // "camisa" o "pantaló"
            
            // 2. Obtenir l'ID de la família des de la DB (perquè el JSON té text)
            int id_familia = db.consultaIdFamilia(nomFamilia);

            // 3. Extraure atributs específics (poden ser nulls segons el tipus)
            Integer talla_coll = getInteger(article, "talla_coll");
            Integer amplada_pit = getInteger(article, "amplada_pit");
            Integer talla_cintura = getInteger(article, "talla_cintura");
            Integer llargada_camal = getInteger(article, "llargada_camal");
            
            double preu_base = getDouble(article, "preu_base");
            int iva = getInt(article, "iva");
            int stock = getInt(article, "stock");

            // 4. Lògica d'inserció o actualització
            if (db.existeixArticle(id)) {
                db.actualitzarArticle(id, nom, id_familia, talla_coll, amplada_pit, talla_cintura, llargada_camal, preu_base, iva, stock);
                actualitzats++;
            } else {
                db.inserirArticle(id, nom, id_familia, talla_coll, amplada_pit, talla_cintura, llargada_camal, preu_base, iva, stock);
                afegits++;
            }
        }

            System.out.println("(+) Procés finalitzat:");
            System.out.println("    - Articles nous: " + afegits);
            System.out.println("    - Articles actualitzats: " + actualitzats);
        
        } catch (Exception e) {
            System.out.println("(!) Error durant la importació a la base de dades.");
            e.printStackTrace();
            }
    }

    // Convertir un camp del JSON a int
    public int getInt(JSONObject article, String camp) {
        int num = 0;
        try {
            num = ((Number) article.get(camp)).intValue();
        } catch (Exception e) {
            num = 0;
        }
        return num;
    }

    // Convertir un camp del JSON a Integer
    public Integer getInteger(JSONObject article, String camp) {
        Integer num = null;
        try {
            num = ((Number) article.get(camp)).intValue();
        } catch (Exception e) {
            num = null;
        }
        return num;
    }

    // Convertir un camp del JSON a double
    public double getDouble(JSONObject article, String camp) {
        double num = 0;
        try {
            num = ((Number) article.get(camp)).doubleValue();
        } catch (Exception e) {
            num = 0;
        }
        return num;
    }

    // Mètode nou per exportar la proposta de recompra a un fitxer JSON
    @SuppressWarnings("unchecked")
    public void guardarPropostaRecompra(java.util.ArrayList<Integer> ids, java.util.ArrayList<String> noms, java.util.ArrayList<Integer> quantitats) {
        JSONArray llistaComanda = new JSONArray();

        for (int i = 0; i < ids.size(); i++) {
            JSONObject articleJson = new JSONObject();
            // Camps exactes demanats: codi article, nom article, quantitat
            articleJson.put("codi_article", ids.get(i));
            articleJson.put("nom_article", noms.get(i));
            articleJson.put("quantitat", quantitats.get(i));
            
            llistaComanda.add(articleJson);
        }

        // Assegurar que la carpeta 'json' existeix
        java.io.File carpeta = new java.io.File("json");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        // Guardar el fitxer JSON afegint un timestamp per fer el nom únic
        String rutaFitxer = "json/proposta_compra_" + System.currentTimeMillis() + ".json";
        try (java.io.FileWriter file = new java.io.FileWriter(rutaFitxer)) {
            file.write(llistaComanda.toJSONString());
            file.flush();
            System.out.println("\n[OK] Fitxer JSON generat correctament a: " + rutaFitxer);
        } catch (Exception e) {
            System.out.println("(!) Error en escriure el fitxer JSON: " + e.getMessage());
        }
    }
}