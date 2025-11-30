package Models;

public class WizardListDTO {
    private int id;
    private String name;
    private int age;
    private String houseName;
    private String wandWood;
    private String wandCore;
    private Double wandLength;


    public WizardListDTO(int id,String name,int age,String houseName,String wandWood,String wandCore,Double wandLength) {
        this.id = id;
        this.name=name;
        this.age=age;
        this.houseName=houseName;
        this.wandWood=wandWood;
        this.wandCore=wandCore;
        this.wandLength=wandLength;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHouseName() {
        return houseName;
    }

    public int getAge() {
        return age;
    }

    public String getWandWood() {
        return wandWood;
    }

    public String getWandCore() {
        return wandCore;
    }

    public Double getWandLength() {
        return wandLength;
    }
}
