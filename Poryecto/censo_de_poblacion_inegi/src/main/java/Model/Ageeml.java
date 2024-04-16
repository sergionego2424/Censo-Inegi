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
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sergio
 */
public class Ageeml {
    public String nombre_estado = "";
    public String nombre_municipio = "";
    public String nombre_localidad = "";
    
    public Ageeml(String nombre_estado, String nombre_municipio, String nombre_localidad){
           this.nombre_estado = nombre_estado;
           this.nombre_municipio = nombre_municipio;
           this.nombre_localidad = nombre_localidad;
    }
    
    public Ageeml(){
        
    }
    
    public void rellenarComboBoxes(JComboBox<String> estado, JComboBox<String> municipio, JComboBox<String> localidad) throws SQLException{
        //estado.removeAllItems();
        //municipio.removeAllItems();
        //localidad.removeAllItems();
        
        
        LinkedList<String> estados = this.getEstados();
        
        for(int i = 0; i < estados.size() ; i++){
            estado.addItem(estados.get(i));
        }
        
        LinkedList<String> municipios = this.getMunicipios();
        for(int i = 0; i < municipios.size() ; i++){
            municipio.addItem(municipios.get(i));
        }
        
        LinkedList<String> localidades = this.getLocalidades();
        for(int i = 0; i < localidades.size() ; i++){
            localidad.addItem(localidades.get(i));
        }
    }
    //spGetAllAGEEML
    
    
    public ResultSet getAgeemlInfo(String nombre_estado_parametro, String nombre_municipio_parametro, String nombre_localidad_parametro) throws SQLException{
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetAllAGEEML(? , ?, ?)}");
        
        cs.setString(1, nombre_estado_parametro);
        cs.setString(2, nombre_municipio_parametro);
        cs.setString(3, nombre_localidad_parametro);
        
        
        System.out.println(this.nombre_localidad);
        cs.execute();
        ResultSet rs = cs.getResultSet();
       
       return rs;
    }
    
    public LinkedList<String> getEstados() throws SQLException{
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	setAllEstados(? , ?)}");
        
        cs.setString(1, this.nombre_municipio);
        cs.setString(2, this.nombre_localidad);
        
        cs.execute();
        ResultSet rs = cs.getResultSet();

        LinkedList<String> localidades = new LinkedList<>();
        while (rs.next()) {
             
            localidades.add(rs.getString("nombre_estado"));
        }
        return localidades;
    }
    
    public LinkedList<String> getMunicipios() throws SQLException{
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetAllMunicipio(? , ?)}");
        
        cs.setString(1, this.nombre_estado);
        cs.setString(2, this.nombre_localidad);
        
        cs.execute();
        ResultSet rs = cs.getResultSet();

        LinkedList<String> localidades = new LinkedList<>();
        while (rs.next()) {
             
            localidades.add(rs.getString("nombre_municipio"));
        }
        return localidades;
    }
    
    public LinkedList<String> getLocalidades() throws SQLException{
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetAllLocalidad(? , ?)}");
        
        cs.setString(1, this.nombre_estado);
        cs.setString(2, this.nombre_municipio);
        
        cs.execute();
        ResultSet rs = cs.getResultSet();

        LinkedList<String> localidades = new LinkedList<>();
        while (rs.next()) {
             
            localidades.add(rs.getString("nombre_localidad"));
        }
        return localidades;
    }
    
    
     public  void rellenarAgeeml(JTable tabla, String nombre_estado_parametro, String nombre_municipio_parametro, String nombre_localidad_parametro) throws SQLException{
        
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        
        // Limpiar la tabla antes de agregar nuevos datos
        model.setRowCount(0);
        
        Connection conexion = SingletonConexio.getInstance();
        CallableStatement cs = conexion.prepareCall("{call 	spGetAllAGEEML(? , ?, ?)}");
        cs.setString(1, nombre_estado_parametro);
        cs.setString(2, nombre_municipio_parametro);
        cs.setString(3, nombre_localidad_parametro);
        
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
    
}
