package Controllers;

import Models.House;
import Models.HouseDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HouseController {
    private HouseDAO houseDAO;

    public HouseController() {
        houseDAO = new HouseDAO();
    }

    public void addHouse(String name, String founder){
        //comprobar permisos
        //comprobar parametros (verificacion en servidor)
        //control de transacciones
        try{
            House house=new House(name,founder);
            houseDAO.create(house);
            System.out.println("☑️ Casa agregada con exito");
        }catch(SQLException e){
            System.out.println("❌ Error al agregar casa : " + e.getMessage());
        }
    }

    public void listHouse(){
        try{
            List<House> houses = houseDAO.getAll();
            if(houses.isEmpty()){
                System.out.println("❌ No hay casas");
            }else{
                for(House house:houses){
                    System.out.println(house.toString());
                }
            }

        }catch (SQLException e){
            System.out.println("❌ Error al listar casas "+e.getMessage());
        }
    }

    public void updateHouse(int id,String name, String founder){
        try{
            House house=new House(name,founder);
            house.setId(id);
            houseDAO.update(house);
            System.out.println("☑️  Casa actualizada con exito ");
        }catch (SQLException e){
            System.out.println("❌ Error al actualizar casa "+e.getMessage());
        }

    }

    public void deleteHouse(int id){
        try{
            houseDAO.delete(id);
            System.out.println("☑️ Casa eliminada con exito");
        }catch (SQLException e){
            System.out.println("❌ Error al borrar la casa "+e.getMessage());
        }
    }

    //controller para JSwing

    public List<House> ListHouseJSwing(){
        List<House> houses = new ArrayList<>();;
        try {
            houses = houseDAO.getAll();
        }catch (SQLException e){
            System.out.println("❌ Error al obtener casas"+e.getMessage());
        }
        return  houses;
    }

    public void addHouseSwing(String name,String founder){
        try{
            houseDAO.create(new House(name,founder));
        }catch (SQLException e){
            System.out.println("❌ Error en la creacion de la casa "+e.getMessage());
        }
    }


}
