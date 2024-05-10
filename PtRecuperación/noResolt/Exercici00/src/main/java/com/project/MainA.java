package com.project;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainA {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        AppData db = AppData.getInstance();

        // Create football-themed tables
        createTables();

        // Initially add jugadores and list
        System.out.println("Adding initial jugadores...");
        addJugadores("Cristiano Ronaldo", "Portugal", false);
        addJugadores("Messi", "Argentina", true);
        listJugadores();

        // Add equipos and list
        System.out.println("\nAdding equipos...");
        addEquipos("Al Hilal", "Arabia");
        addEquipos("Miami FC", "EEUU");
        listEquipos();

        // Add trofeos and list
        System.out.println("\nAdding balones de oro...");
        addBalonDeOro(1, 1, "2017");
        addBalonDeOro(2, 2, "2023");
        listBalonDeOro();

        // Close the database connection
        db.close();
    }

    public static void createTables() {
        
    }
    
    public static void addJugadores(String nombre, String pais, boolean zurdo) {
        
    }

    public static void addEquipos(String name, String pais) {

    }
    
    public static void addBalonDeOro(int jugadorId, int equipoId, String año) {

    }

    public static void listJugadores() {
        
    }

    public static void listEquipos() {
        
    }

    public static void listBalonDeOro() {
        
    }
   
    // Mètode auxiliar per escapar cometes simples que podrien rompre la cadena SQL
    private static String escapeSQL(String input) {
        return input.replace("'", "''");
    }   
}
