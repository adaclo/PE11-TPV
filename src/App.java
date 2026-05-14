import objects.Camisa;
import objects.Pantalon;

public class App {

    public static void main(String[] args) {

        Camisa camisa = new Camisa(
                1,
                "Camisa Blanca",
                25.99,
                21,
                10,
                40,
                12
        );

        Pantalon pantalon = new Pantalon(
                2,
                "Jeans Negros",
                39.99,
                21,
                5,
                42,
                34
        );

        System.out.println("=== ARTICLES ===");
        System.out.println(camisa);
        System.out.println(pantalon);
    }
}