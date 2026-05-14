package objects;

public class Camisa extends Article {
    
    //ATRIBUTES 
    private int neckSize;
    private int chestWidth;

    //EMPTY CONSTRUCTOR
    public Camisa() {
        super();
        this.family = "Camisa";
    }

    //FULL CONSTRUCTOR
    public Camisa(int id, String name, double basePrice, double IVA, 
                    int stock, int neckSize, int chestWidth) {

        super(id, name, "Camisa", basePrice, IVA, stock);

        this.neckSize = neckSize;
        this.chestWidth = chestWidth;
    }

    //GETTERS AND SETTERS
    public int getNeckSize() {
        return neckSize;
    }

    public void setNeckSize(int neckSize) {
        this.neckSize = neckSize;
    }

    public int getChestWidth() {
        return chestWidth;
    }

    public void setChestWidth(int chestWidth) {
        this.chestWidth = chestWidth;
    }

    //TOSTRING
    @Override
    public String toString() {
        return "Camisa [id=" + getId()
        + ", name=" + getName()
        + ", family=" + getFamily()
        + ", basePrice=" + getBasePrice()
        + ", IVA=" + getIVA()
        + ", stock=" + getStock()
        +", neckSize=" + neckSize
        + ", chestWidth=" + chestWidth + "]";
    }
}
