package com.project;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainB {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        AppData db = AppData.getInstance();

        // Create football-themed tables
        createTables();

        // Initially add jugadores and list
        System.out.println("Adding initial jugadores...");
        addJugadores("Cristiano Ronaldo", "Portugal", false);
        addJugadores("Messi", "Argentina", true);
        addJugadores("Griezman", "Francia", true);
        listJugadores();

        // Add equipos and list
        System.out.println("\nAdding equipos...");
        addEquipos("Al Hilal", "Arabia");
        addEquipos("Miami FC", "EEUU");
        addEquipos("PSG", "Francia");
        listEquipos();

        // Add trofeos and list
        System.out.println("\nAdding balones de oro...");
        addBalonDeOro(1, 1, "2017");
        addBalonDeOro(2, 2, "2023");
        addBalonDeOro(3, 2, "2018");
        listBalonDeOro();

        // Update jugadores and list
        System.out.println("\nUpdating jugadores 'Cristiano Ronaldo' to 'Cristiano'...");
        MainB.updateJugadores(1, "Cristiano", "Portugal", false);
        MainB.listJugadores();

        // Delete a equipo and list
        System.out.println("\nDeleting equipo 'Al Hilal'...");
        MainB.deleteEquipos(1);
        MainB.listEquipos();

        // Add another jugadores, update a mission, and list both
        System.out.println("\nAdding another jugadores and updating a mission...");
        MainB.addJugadores("Neymar", "Brasil", false);
        MainB.updateBalonDeOro(2, "2019");
        MainB.listJugadores();
        MainB.listBalonDeOro();

        // Finally, delete a jugadores and list
        System.out.println("\nDeleting jugador 'Messi'...");
        MainB.deleteJugador(2);
        MainB.listJugadores();

        // Checking deletion effect on missions
        System.out.println("\nListing missions after deletion of a jugadores...");
        MainB.listBalonDeOro();
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
        
    public static void updateJugadores(int id, String nombre, String pais, boolean zurdo) {
        
    }

    public static void updateBalonDeOro(int Id, String año) {
        
    }

    public static void deleteEquipos(int id) {
        
    }

    public static void deleteJugador(int id) {
        
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
