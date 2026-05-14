package objects;

public class Pantalon extends Article {
    
    private int waistSize;
    private int legLength;
    
    //EMPTY CONSTRUCTOR
    public Pantalon() {
        super();
        this.family = "Pantalon";
    }

    //FULL CONSTRUCTOR
    public Pantalon(int id, String name, double basePrice, double IVA, int stock, int waistSize, int legLength) {
        super(id, name, "Pantalon", basePrice, IVA, stock);
        this.waistSize = waistSize;
        this.legLength = legLength;
    }

    //GETTERS AND SETTERS
    public int getWaistSize() {
        return waistSize;
    }

    public void setWaistSize(int waistSize) {
        this.waistSize = waistSize;
    }

    public int getLegLength() {
        return legLength;
    }

    public void setLegLength(int legLength) {
        this.legLength = legLength;
    }

    //TO STRING
    @Override
    public String toString() {
        return "Pantalon{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", family='" + getFamily() + '\'' +
                ", basePrice=" + getBasePrice() +
                ", IVA=" + getIVA() +
                ", stock=" + getStock() +
                ", waistSize=" + waistSize +
                ", legLength=" + legLength +
                '}';
    }
}
