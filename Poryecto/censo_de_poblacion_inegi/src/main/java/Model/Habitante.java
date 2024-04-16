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

/**
 *
 * @author Sergio
 */
public abstract class Habitante {
    public  String CURP;
    public String nombre;
    public String paterno;
    public String materno;
    public String fecha_nacimiento;
    public String sexo;
    public String casado;
    public Vivienda vivienda;
    
  
    
    public abstract void getInfoHabitante();
    public abstract void insertHabitante();
    public  abstract  Habitante getHabitante(String CURP);
    public abstract void deleteHabitante(String CRUP);
    public abstract void updateHabitante();
}
