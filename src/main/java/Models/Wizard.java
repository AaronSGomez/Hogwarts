package Models;

public class Wizard {

    private int id;
    private String name;
    private int edad, houseId, wandId;

    //constructores

    public Wizard(int id, String name, int edad, int houseId, int wandId) {
        this.id = id;
        this.name = name;
        this.edad = edad;
        this.houseId = houseId;
        this.wandId = wandId;
    }

    public Wizard() {}

    public Wizard(String name, int houseId, int edad, int wandId) {
        this.name = name;
        this.houseId = houseId;
        this.edad = edad;
        this.wandId = wandId;
    }

    public Wizard(String name, int edad) {
        this.name = name;
        this.edad = edad;
    }

    //getters y setters
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getWandId() {
        return wandId;
    }

    public void setWandId(int wandId) {
        this.wandId = wandId;
    }

   //toString
    public String toString() {
        return "Wizard[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", edad=" + edad +
                ", houseId=" + houseId +
                ", wandId=" + wandId +
                ']';
    }
}
