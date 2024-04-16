/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.censo_de_poblacion_inegi;

import View.ActividadEconomica;
import View.Analisis;
import View.HabitanteCRUD;
import View.IniciarSesion;
import View.UsuarioCRUD;
import View.Vivienda;

/**
 *
 * @author Sergio
 */
public class Censo_de_poblacion_inegi {
    public static IniciarSesion i = new IniciarSesion();
    public static  UsuarioCRUD u = new UsuarioCRUD();
    public static HabitanteCRUD h = new HabitanteCRUD();
    public static Vivienda v = new Vivienda();
    public static ActividadEconomica a = new ActividadEconomica();
    public static Analisis an = new Analisis();
    public static void main(String[] args) {
        
        i.setVisible(true);
    }
}
