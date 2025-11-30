package Controllers;
import Models.Wand;
import Models.WandDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WandController {

    private WandDAO wandDAO;

    public WandController() {
       wandDAO = new WandDAO();
    }

    public void addWand(String wood, String core, double length){
        //comprobar permisos
        //comprobar parametros (verificacion en servidor)
        //control de transacciones
        try{
            Wand wand=new Wand(wood,core,length);
            wandDAO.create(wand);
            System.out.println("☑️ Barita agregada con exito");
        }catch(SQLException e){
            System.out.println("❌ Error al agregar Barita : " + e.getMessage());
        }
    }

    public void listWands(){
        try{
            List<Wand> wands = wandDAO.getAll();
            if(wands.isEmpty()){
                System.out.println("❌ No hay baritas");
            }else{
                for(Wand wand:wands){
                    System.out.println(wand.toString());
                }
            }

        }catch (SQLException e){
            System.out.println("❌ Error al listar Baritas "+e.getMessage());
        }
    }

    public void updateWand(int id,String wood, String core, double length){
        try{
            Wand wand=new Wand(wood,core,length);
            wand.setId(id);
            wandDAO.update(wand);
            System.out.println("☑️  Barita actualizado con exito ");
        }catch (SQLException e){
            System.out.println("❌ Error al actualizar barita "+e.getMessage());
        }

    }

    public void deleteWand(int id){
        try{
            wandDAO.delete(id);
            System.out.println("☑️ Barita eliminado con exito");
        }catch (SQLException e){
            System.out.println("❌ Error al borrar la Barita "+e.getMessage());
        }
    }

    //controller para JSwing

    public List<Wand> ListWandJSwing(){
        List<Wand> wands = new ArrayList<>();;
        try {
            wands = wandDAO.getAll();
        }catch (SQLException e){
            System.out.println("❌ Error al obtener baritas"+e.getMessage());
        }
        return  wands;
    }

    public void addWandSwing(String wood,String core,double length){
        try{
            wandDAO.create(new Wand(wood,core,length));
        }catch (SQLException e){
            System.out.println("❌ Error en la creacion de la barita "+e.getMessage());
        }
    }

}
