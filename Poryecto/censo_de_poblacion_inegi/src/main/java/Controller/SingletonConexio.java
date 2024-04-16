package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConexio {
    private static Connection conexion = null;

    
    private SingletonConexio() {
    }

    public static Connection getInstance() {
        if (conexion == null) {
            try {
                // Establecer la conexión
                String url = "jdbc:mysql://localhost:3306/censo_poblacion";
                String usuario = "root";
                String contraseña = ""; // Asegúrate de que la contraseña es correcta
                conexion = DriverManager.getConnection(url, usuario, contraseña);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return conexion;
    }

    public static void closeConnection() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null; // Reset the handler to allow reconnection if needed.
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
