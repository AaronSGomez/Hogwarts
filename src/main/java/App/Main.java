package App;

import Models.*;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        HouseDAO houseDAO= new HouseDAO();
        WandDAO wandDAO= new WandDAO();
        WizardDAO wizardDAO= new WizardDAO();

        /*
        House gryffindor = new House("Gryffindor","Godric Gryffindor");
        House slythering = new House("Slythering","Salazar Slythering");
        House hufflepuff = new House("Hufflepuff ","Helga Hufflepuff");
        House ravenclaw = new House("Ravenclaw","Rowena Ravenclaw");
        */
        /*
        houseDAO.create(gryffindor);
        houseDAO.create(slythering);
        houseDAO.create(hufflepuff);
        houseDAO.create(ravenclaw);
        */

        List<House> houses = houseDAO.getAll();
        //forma Olga: houses.forEach(System.out :: println);
        for(House h : houses){
            System.out.println(h.toString());
        }

        /*
        Wand harryWand = new Wand("Acebo", "Pluma de fénix", 28.0);
        Wand ronWand = new Wand("Sauce", "Pelo de unicornio", 35.6);
        Wand dracoWand = new Wand("Espino", "Pelo de unicornio", 25.4);
        Wand hermioneWand = new Wand("Vid", "Corazón de dragón", 27.3);*/
        /*
        wandDAO.create(harryWand);
        wandDAO.create(dracoWand);
        wandDAO.create(hermioneWand);
        wandDAO.create(dracoWand);
        */

       List<Wand> wands = wandDAO.getAll();
        for(Wand wand : wands){
            System.out.println(wand.toString());
        }

        /*
        Wizard harry= new Wizard("Harry Potter", 17,2,1);
        Wizard ron= new Wizard("Ron Weasly", 17,2,2);
        Wizard hermione= new Wizard("Hermione Granger", 17,2,4);
        Wizard draco= new Wizard("Draco Malfoy", 17,2,3);
        wizardDAO.create(harry);
        wizardDAO.create(ron);
        wizardDAO.create(hermione);
        wizardDAO.create(draco);
        */
        wizardDAO.delete(4);

        List<Wizard> w = wizardDAO.getAll();
        for(Wizard wiz : w){
            System.out.println(wiz.toString());
        }

        wizardDAO.delete(4);


    }

}
