package App;

import Models.House;
import Models.HouseDAO;
import Models.Wand;
import Models.Wizard;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        HouseDAO houseDAO= new HouseDAO();

        House gryffindor = new House("Gryffindor","Godric Gryffindor");
        House slythering = new House("Slythering","Salazar Slythering");
        House hufflepuff = new House("Hufflepuff ","Helga Hufflepuff");
        House ravenclaw = new House("Ravenclaw","Rowena Ravenclaw");

        houseDAO.create(gryffindor);

        List<House> houses = houseDAO.getAll();
        //forma Olga: houses.forEach(System.out :: println);
        for(House h : houses){
            System.out.println(h.toString());
        }



        Wand harryWand = new Wand("Acebo", "Pluma de fénix", 28.0);
        Wand ronWand = new Wand("Sauce", "Pelo de unicornio", 35.6);
        Wand dracoWand = new Wand("Espino", "Pelo de unicornio", 25.4);
        Wand hermioneWand = new Wand("Vid", "Corazón de dragón", 27.3);

        Wizard harry= new Wizard("Harry Potter", 17);
        Wizard ron= new Wizard("Ron Weasly", 17);
        Wizard hermione= new Wizard("Hermione Granger", 17);
        Wizard draco= new Wizard("Draco Malfoy", 17);

        System.out.println(gryffindor.toString());
        System.out.println(slythering.toString());
        System.out.println(hufflepuff.toString());
        System.out.println(ravenclaw.toString());

        System.out.println(harryWand.toString());
        System.out.println(ronWand.toString());
        System.out.println(hermioneWand.toString());
        System.out.println(dracoWand.toString());

        System.out.println(harry.toString());
        System.out.println(ron.toString());
        System.out.println(draco.toString());
        System.out.println(hermione.toString());


    }

}
