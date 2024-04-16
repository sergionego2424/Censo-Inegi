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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Sergio
 */
public class HabitanteDecorator extends Habitante{
    
    public Habitante habitante;
    public Vivienda vivienda;
    public HabitanteDecorator(Habitante habitante, Vivienda vivienda){
        this.habitante = habitante;
        this.vivienda = vivienda;
    }
    
    public HabitanteDecorator(){
        
    }
    
    
    @Override
    public void getInfoHabitante() {
    
     
        
        
        
    }

    @Override
    public void insertHabitante() {
        
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs;
        
        try {
            cs = conexion.prepareCall("{call 	spInsertHabitante_Data(?, ? ,? , ? , ? ,?, ? ,? ,? ,? ,? ,?, ?, ?)}");
            cs.setString(1, this.habitante.CURP);
            cs.setString(2, this.habitante.nombre);
            cs.setString(3, this.habitante.paterno);
            cs.setString(4, this.habitante.materno);
            cs.setString(5, this.habitante.fecha_nacimiento);
            cs.setString(6, this.habitante.sexo);
            cs.setString(7, this.habitante.casado);
            cs.setInt(8, this.vivienda.id_vivienda);
            cs.setString(9, this.vivienda.calle);
            cs.setString(10, this.vivienda.numero);
            cs.setString(11, this.vivienda.colonia);
            cs.setString(12, this.vivienda.codigo_postal);
            cs.setInt(13, this.vivienda.id_tipo_vivienda);
            cs.setInt(14, this.vivienda.id_AGEEML);

            cs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HabitanteDecorator.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        
    }

    @Override
    public HabitanteDecorator getHabitante(String CURP) {
        HabitanteDecorator habitante_return = new HabitanteDecorator();
        
        Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs;
        try {
            cs = conexion.prepareCall("{call 	spGetHabitanteByCURP(?)}");
            
            cs.setString(1, CURP);
            
            
            cs.execute();
        
            ResultSet rs = cs.getResultSet();


            if (rs.next()) {
                // Asignar valores a las propiedades del objeto habitante_return si rs no es nulo
                habitante_return.nombre = rs.getString("nombre");
                habitante_return.paterno = rs.getString("paterno");
                habitante_return.materno = rs.getString("materno");
                habitante_return.fecha_nacimiento = rs.getString("fecha_nacimiento");
                habitante_return.sexo = rs.getString("sexo");
                habitante_return.casado = rs.getString("casado");

                // Crear una nueva Vivienda y asignarla al habitante
                habitante_return.vivienda = new Vivienda(
                    rs.getInt("id_vivienda"), 
                    rs.getString("calle"),
                    rs.getString("numero"),
                    rs.getString("colonia"),
                    rs.getString("codigo_postal"),
                    rs.getInt("id_tipo_vivienda"),
                    rs.getInt("id_AGEEML")
                );
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro este habitante");
            }

        } catch (SQLException ex) {
            Logger.getLogger(HabitanteDecorator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return habitante_return;
        
    }

    @Override
    public void deleteHabitante(String CURP) {
        Connection conexion = SingletonConexio.getInstance();
        CallableStatement cs;
        try {
            cs = conexion.prepareCall("{call spDeleteHabitante(?)}");
            cs.setString(1, CURP);
            cs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HabitanteDecorator.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }

    @Override
    public void updateHabitante() {
     Connection conexion = SingletonConexio.getInstance();
        
        CallableStatement cs;
        
        try {
            cs = conexion.prepareCall("{call spUpdateHabitante_Data(?, ? ,? , ? , ? ,?, ? , ? , ? , ? , ?)}");
            cs.setString(1, this.habitante.CURP);
            cs.setString(2, this.habitante.nombre);
            cs.setString(3, this.habitante.paterno);
            cs.setString(4, this.habitante.materno);
            cs.setString(5, this.habitante.fecha_nacimiento);
            cs.setString(6, this.habitante.sexo);
            cs.setString(7, this.habitante.casado);
            cs.setString(8, this.vivienda.calle);
            cs.setString(9, this.vivienda.numero);
            cs.setString(10, this.vivienda.colonia);
            cs.setString(11, this.vivienda.codigo_postal);

            cs.execute();
        } catch (SQLException ex) {
            Logger.getLogger(HabitanteDecorator.class.getName()).log(Level.SEVERE, null, ex);
        }

    
    }
    
}
