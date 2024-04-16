/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Controller.SingletonConexio;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Sergio
 */
public class HabitanteActicidadEconomicaDecorator extends HabitanteDecorator{
    public HabitanteActicidadEconomicaDecorator habitante ;
    public String descripcion_actividad;
    public float remuneracion;
    
    public HabitanteActicidadEconomicaDecorator(HabitanteActicidadEconomicaDecorator habitante){
        this.habitante = habitante;
    }
    
    public HabitanteActicidadEconomicaDecorator(){
        
    }
    public static LinkedList<String> getCURP() throws SQLException{
          Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetCURP()}");
        
        cs.execute();
        ResultSet rs = cs.getResultSet();
        
        LinkedList<String> CURP = new LinkedList<>();
        while (rs.next()) {
             
            CURP.add(rs.getString("CURP"));
        
        }
        return CURP;
       
    }
    
    
    public  void agregarActividadEconomica() throws SQLException{
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call spInsertActividadEconomica(?, ? ,?)}");
        cs.setString(1, this.habitante.CURP);
        cs.setString(2, this.descripcion_actividad);
        cs.setFloat(3, this.remuneracion);
        
        
        cs.execute();
    }
}
