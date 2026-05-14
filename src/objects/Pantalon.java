package objects;

public class Pantalon extends Article {
    
    private int waistSize;
    private int legLength;
    
    //EMPTY CONSTRUCTOR
    public Pantalon() {
        super(0, "", 2, 0.0, 0, 0); // Assuming 2 represents the family ID for Pants
    }

    //FULL CONSTRUCTOR
    public Pantalon(int id, String name, double basePrice, double IVA, int stock, int waistSize, int legLength) {
        super(id, name, 2, basePrice, IVA, stock); // Assuming 2 represents the family ID for Pants
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
                ", familyId=" + getFamilyId() +
                ", basePrice=" + getBasePrice() +
                ", IVA=" + getIVA() +
                ", stock=" + getStock() +
                ", waistSize=" + waistSize +
                ", legLength=" + legLength +
                '}';
    }
}
