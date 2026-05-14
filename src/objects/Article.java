package objects;

public abstract class Article {
    
    //ATRIBUTES
    private int id;
    private String name;
    protected String family;
    private double basePrice;
    private double IVA;
    private int stock;


    //EMPTY CONSTRUCTOR
    public Article() {

    }

    //FULL CONSTRUCTOR
    public Article(int id, String name, String family, double basePrice, double IVA, int stock) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.basePrice = basePrice;
        this.IVA = IVA;
        this.stock = stock;
    }

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }      

    public String getName() {
        return name;
    }       

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getIVA() {
        return IVA;
    }

    public void setIVA(double IVA) {
        this.IVA = IVA;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    //TO STRING
    @Override
    public String toString() {
        return "Article [id=" + id
        + ", name=" + name
        + ", family=" + family
        + ", basePrice=" + basePrice
        + ", IVA="+ IVA
        + ", stock=" + stock + "]";
    }

}
