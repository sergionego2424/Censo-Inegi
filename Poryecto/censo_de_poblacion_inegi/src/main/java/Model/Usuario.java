/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Controller.SingletonConexio;
import Controller.SingletonUsuario;
import View.UsuarioCRUD;
import com.mycompany.censo_de_poblacion_inegi.Censo_de_poblacion_inegi;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sergio
 */
public class Usuario {
    private int ID;
    private String nombre;
    private String apellido;
    private String usuario;
    private String tipo_usuario;
    private String password;
    
    public Usuario(){
        
    }
    
    public Usuario(int ID,String nombre,String apellido,String usuario,String tipo_usuario,String password){
        this.ID = ID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.tipo_usuario = tipo_usuario;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipo_usuario() {
        return tipo_usuario;
    }

    public void setTipo_usuario(String tipo_usuario) {
        this.tipo_usuario = tipo_usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String iniciarSesion(String usuario, String password) throws SQLException{
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	iniciar_sesion_admin(?, ?)}");

        cs.setString(1, usuario);
        cs.setString(2, password);

        boolean hasResultSet = cs.execute();
        
        // Procesar el ResultSet
        String rn = "";
        if (hasResultSet) {
            ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                 rn = rs.getString("RN"); 
                
            }
            rs.close(); 
        }
        System.out.println("RN: " + rn);
        if(rn.equals("1")){
            System.out.println("HERE");
            this.createSingleton(usuario, password);
        }else{
           JOptionPane.showMessageDialog(null, "Contraseña o usuario incorrectos");
        }
        
        return rn ;
        
    }
    
    private void createSingleton(String usuario, String password) throws SQLException{
        ResultSet rs = this.getUsarioInfo(usuario, password);
        rs.next();
        SingletonUsuario.instance(
               Integer.parseInt( rs.getString("id_usuario")), 
                rs.getString("nombre"), 
                rs.getString("apellido"),
                rs.getString("usuario"),
                rs.getString("tipo_usuario"), 
                rs.getString("password"));
        System.out.println(SingletonUsuario.getInstance().nombre);
        
        Censo_de_poblacion_inegi.i.setVisible(false);
        
        Censo_de_poblacion_inegi.u.setVisible(true);
    
    }
    private ResultSet getUsarioInfo(String usuario, String password) throws SQLException{
         Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetUsuario(?, ?)}");

        cs.setString(1, usuario);
        cs.setString(2, password);

        
        cs.execute();
        ResultSet rs = cs.getResultSet();
        
        return rs;
        
    }
    
    
    public static void deleteUsario(int id) throws SQLException{
         Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spDeleteUsuario(?)}");
        
        
        cs.setString(1, Integer.toString(id));
        cs.execute();
        ResultSet rs = cs.getResultSet();
        
       
    }
    
    
    public void insertUsuario() throws SQLException{
            Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spInsertUsuario(?, ?, ?, ?, ?)}");
        
        cs.setString(1, this.nombre);
        cs.setString(2, this.apellido);
        cs.setString(3, this.usuario);
        cs.setString(4, this.tipo_usuario);
        cs.setString(5, this.password);
        
        
        cs.execute();
    }
    
    public void updateUsuario() throws SQLException{
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spUpdateUsuario(?, ?, ?, ?, ?, ?)}");
        
        cs.setString(1, Integer.toString(this.ID));
        cs.setString(2, this.nombre);
        cs.setString(3, this.apellido);
        cs.setString(4, this.usuario);
        cs.setString(5, this.tipo_usuario);
        cs.setString(6, this.password);
        
        
        cs.execute();
    }
    
    public static void rellenarUsuarios(JTable tabla) throws SQLException{
        
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        
        // Limpiar la tabla antes de agregar nuevos datos
        model.setRowCount(0);
        
        Connection conexion = SingletonConexio.getInstance();
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM view_usuarios_activos"); 

        // Iterar sobre los resultados y agregarlos a la tabla
        while (rs.next()) {
           Object[] fila = new Object[rs.getMetaData().getColumnCount()];
           for (int i = 0; i < fila.length; i++) {
               fila[i] = rs.getObject(i + 1);
           }
           model.addRow(fila);
        }
    }
    
    public static Usuario getUsuarioById(String id) throws SQLException{
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs = conexion.prepareCall("{call 	spGetUsuarioById(?)}");
        
        cs.setString(1, id);
     
        
        
        cs.execute();
        
        ResultSet rs = cs.getResultSet();
        
        
        rs.next();
        
        //int ID,String nombre,String apellido,String usuario,String tipo_usuario,String password

        Usuario usuario = new Usuario(
                Integer.parseInt(rs.getString("id_usuario")),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("usuario"),
                rs.getString("tipo_usuario"),
                rs.getString("password")
        );
        
        return usuario;
    }
    
}
