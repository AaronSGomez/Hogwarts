package Controllers;

import Models.Wizard;
import Models.WizardDAO;

import java.sql.SQLException;

public class WizardController {

    private WizardDAO wizardDAO;

    public WizardController(){
        wizardDAO= new WizardDAO();
    }

    public void addWizard(String name,int age,int house_id, int wand_id){
        //comprobar permisos
        //comprobar parametros (verificacion en servidor)
        //control de transacciones
        try{
            Wizard wizard=new Wizard(name,age,house_id,wand_id);
            wizardDAO.create(wizard);
            System.out.println("☑️ Mago agregado con exito");
        }catch(SQLException e){
            System.out.println("❌ Error al agregar mago : " + e.getMessage());
        }

    }


}
