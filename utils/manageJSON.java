package utils;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class manageJSON {
    Object obj = null;
    JSONArray articles = null;

    // Carregar el fitxer JSON d'articles
    public void carregarJSON() {
        JSONParser parser = new JSONParser();
        try {
            obj = parser.parse(new FileReader("articles.json"));
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
}