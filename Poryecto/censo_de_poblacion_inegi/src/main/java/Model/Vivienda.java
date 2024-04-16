/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Controller.SingletonConexio;
import java.awt.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sergio
 */
public class Vivienda {
    public int id_vivienda = 0;
    public String calle;
    public String numero;
    public String colonia;
    public String codigo_postal;
    public int id_tipo_vivienda;
    public int id_AGEEML;
    public String tipo_vivienda;
    public String localidad;
    
    public  Vivienda(int id_vivienda, String calle, String numero, String colonia,String codigo_postal,int tipo_vivienda,int id_AGEEML){
           this.id_vivienda = id_vivienda;
           this.calle = calle;
           this.numero = numero;
           this.colonia  = colonia;
           this.codigo_postal = codigo_postal;
           this.id_tipo_vivienda = tipo_vivienda;
           this.id_AGEEML = id_AGEEML;
    }
    
    public Vivienda(){
        
    }
    
    
    public static LinkedList<String> getLocalidad() throws SQLException{
          Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetAgeeml()}");
        
        cs.execute();
        ResultSet rs = cs.getResultSet();
        
        
      

        LinkedList<String> localidades = new LinkedList<>();
        while (rs.next()) {
             
            localidades.add(rs.getString("nombre_localidad"));
        }


        return localidades;
        
       
    }
    
    public static LinkedList<String> getTipoVivienda() throws SQLException{
          Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetTipoVivienda()}");
        
        cs.execute();
        ResultSet rs = cs.getResultSet();
        
        
      

        LinkedList<String> tipo_vivienda = new LinkedList<>();
        while (rs.next()) {
             
            tipo_vivienda.add(rs.getString("tipo_vivienda"));
        }


        return tipo_vivienda;
        
       
    }
    
     public static ResultSet getAgeeml() throws SQLException{
          Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetAgeeml()}");
        
        cs.execute();
        ResultSet rs = cs.getResultSet();
        
        return rs;
    } 
     
     public static ResultSet countHabitantesXvivienda() throws SQLException{
          Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetAgeeml()}");
        
        cs.execute();
        ResultSet rs = cs.getResultSet();
        
        return rs;
    }
     


    public static Vivienda[] getViviendas() throws SQLException {
        Connection conexion = SingletonConexio.getInstance();
        CallableStatement cs = conexion.prepareCall("{call spGetViviendas()}");
        cs.execute();
        ResultSet rs = cs.getResultSet();

        LinkedList<Vivienda> viviendasList = new LinkedList<>();
        while (rs.next()) {
            int id_vivienda = rs.getInt("id_vivienda");
            String calle = rs.getString("calle");
            String numero = rs.getString("numero");
            String colonia = rs.getString("colonia");
            String codigo_postal = rs.getString("codigo_postal");
            // Puedes obtener los otros valores según sea necesario

            // Crear un objeto Vivienda y añadirlo a la lista
            Vivienda vivienda = new Vivienda(id_vivienda, calle, numero, colonia, codigo_postal, 0, 0);
            viviendasList.add(vivienda);
        }

        // Convertir la lista a un arreglo de Vivienda
        Vivienda[] viviendas = viviendasList.toArray(new Vivienda[0]);
        return viviendas;
    }
    
    public static void rellenarTableHabitantesXvivienda(JTable tabla, int id_vivienda, String sql) throws SQLException{
        
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        
        // Limpiar la tabla antes de agregar nuevos datos
        model.setRowCount(0);
        
        Connection conexion = SingletonConexio.getInstance();
        CallableStatement cs = conexion.prepareCall(sql);
        cs.setInt(1, id_vivienda);
        
        cs.execute();
        
        ResultSet rs = cs.getResultSet();
        // Iterar sobre los resultados y agregarlos a la tabla
        while (rs.next()) {
           Object[] fila = new Object[rs.getMetaData().getColumnCount()];
           for (int i = 0; i < fila.length; i++) {
               fila[i] = rs.getObject(i + 1);
           }
           model.addRow(fila);
        }
    }
    
    public static ResultSet countHabitantesXvivienda_grafica() throws SQLException{
          Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call spCountHabitantesXVivienda()}");
        
        cs.execute();
        ResultSet rs = cs.getResultSet();
        
        return rs;
    }
}
