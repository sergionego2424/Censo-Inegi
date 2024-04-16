/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Usuario;

/**
 *
 * @author Sergio
 */
public class SingletonUsuario {
    private static Usuario usuario = null;

    private SingletonUsuario() {
    }

    public static void  instance(int ID, String nombre, String apellido, String username, String tipo_usuario, String password) {
        if (usuario == null) {
            usuario = new Usuario(ID, nombre, apellido, username, tipo_usuario, password);
        }
       
    }
      public static Usuario getInstance() {
        
        return usuario;
    }
      public static void destroyIntance(){
           if (usuario != null) {
                usuario = null;
           }
      }
}
