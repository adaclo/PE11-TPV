
package objects;

public class Family {
    
    //ATTRIBUTES
    private int id;
    private String name;

    //EMPTY CONSTRUCTOR
    public Family() {
    }

    //FULL CONSTRUCTOR
    public Family(int id, String name) {
        
        this.id = id;
        this.name = name;
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

    //TO STRING
    @Override
    public String toString() {
        return "Family{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
