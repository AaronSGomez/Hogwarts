package Models;

public class Wizard {

    private int id;
    private String name;
    private int age, houseId, wandId;

    //constructores

    public Wizard(int id, String name, int age, int houseId, int wandId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.houseId = houseId;
        this.wandId = wandId;
    }

    public Wizard() {}

    public Wizard(String name, int age, int houseId, int wandId) {
        this.name = name;
        this.houseId = houseId;
        this.age = age;
        this.wandId = wandId;
    }

    public Wizard(String name, int age) {
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
                ", age=" + age +
                ", houseId=" + houseId +
                ", wandId=" + wandId +
                ']';
    }
}
