package Views;

import Controllers.WizardController;

import java.util.Scanner;

public class WizardView {

    private WizardController controller;
    private Scanner sc;

    public WizardView(){
        controller = new WizardController();
        sc = new Scanner(System.in);
    }

    public void showMenu(){
        int opcion;
        do{
            System.out.println("\n Bienvenidos a Hogwarts ");
            System.out.println("1. Listar Magos");
            System.out.println("2. Agregar Mago");
            System.out.println("3. Actualizar Mago");
            System.out.println("4. Eliminar Mago");
            System.out.println("5. Salir");

            System.out.print("Ingrese opcion: ");
            opcion= sc.nextInt();

            switch(opcion){
                case 1 -> controller.listWizards();
                case 2 -> addWizard();
                case 3 -> updateWizard();
                case 4 -> deleteWizard();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion incorrecta");
            }

        }while(opcion !=5);
    }

    private void addWizard(){
        sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        System.out.print("Id de casa: ");
        int house_id = sc.nextInt();
        System.out.print("Id de la barita: ");
        int wand_id = sc.nextInt();

        controller.addWizard(nombre,edad,house_id,wand_id);

    }
    private void updateWizard(){
        sc.nextLine();
        System.out.print("Id del mago a actualizar: ");
        int id = sc.nextInt();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Edad: ");
        int edad = sc.nextInt();
        System.out.print("Id de casa: ");
        int house_id = sc.nextInt();
        System.out.print("Id de la barita: ");
        int wand_id = sc.nextInt();

        controller.updateWizard(id,nombre,edad,house_id,wand_id);
    }

    private void deleteWizard(){
        sc.nextLine();
        System.out.print("Id del mago a eliminar: ");
        int id = sc.nextInt();
        controller.deleteWizard(id);
    }

}
