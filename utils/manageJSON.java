package utils;
import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class manageJSON {
    Object obj = null;
    JSONArray articles = null;

    // Carregar el fitxer JSON d'articles
    public void carregarJSON() {
        JSONParser parser = new JSONParser();
        try {
            obj = parser.parse(new FileReader("../json/articles.json"));
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
}