package Controllers;

import Models.Wizard;
import Models.WizardDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public void listWizards(){
        try{
            List<Wizard> wizards = wizardDAO.getAll();
            if(wizards.isEmpty()){
                System.out.println("❌ No hay magos");
            }else{
                for(Wizard wizard:wizards){
                    System.out.println(wizard.toString());
                }
            }

        }catch (SQLException e){
            System.out.println("❌ Error al listar magos "+e.getMessage());
        }
    }

    public void updateWizard(int id,String name,int age,int house_id,int wand_id){
        try{
            Wizard wizard=new Wizard(name,age,house_id,wand_id);
            wizard.setId(id);
            wizardDAO.update(wizard);
            System.out.println("☑️  Mago actualizado con exito ");
        }catch (SQLException e){
            System.out.println("❌ Error al actualizar mago "+e.getMessage());
        }

    }

    public void deleteWizard(int id){
        try{
            wizardDAO.delete(id);
            System.out.println("☑️ Mago eliminado con exito");
        }catch (SQLException e){
            System.out.println("❌ Error al borrar el mago "+e.getMessage());
        }
    }

    //controller para JSwing

    public List<Wizard> ListWizardsJSwing(){
        List<Wizard> wizards = new ArrayList<>();;
        try {
            wizards  = wizardDAO.getAll();
        }catch (SQLException e){
            System.out.println("❌ Error al obtener magos "+e.getMessage());
        }
        return  wizards;
    }






}
