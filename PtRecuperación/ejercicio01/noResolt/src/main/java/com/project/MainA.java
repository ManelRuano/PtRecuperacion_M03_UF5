package com.project;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainA {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        AppData db = AppData.getInstance();

        // Crear las tablas relacionadas con jugadores, equipos y pichichis
        createTables();

        // Agregar jugadores y listarlos
        System.out.println("Agregando jugadores...");
        addJugador("Messi", "Argentina", 870);
        addJugador("Cristiano Ronaldo", "Portugal", 920);
        listJugadores();

        // Agregar equipos y listarlos
        System.out.println("\nAgregando equipos...");
        addEquipo("Al Nassr", 1955);
        addEquipo("Inter Miami", 2018);
        listEquipos();

        // Agregar pichichis y listarlos
        System.out.println("\nAgregando pichichi...");
        addPichchi(1, 2, 2009);
        addPichchi(2, 1, 2014);
        listPichichi();

        // Actualizar la información de un jugador
        System.out.println("\nActualizando jugador...");
        updateJugadores(2, "Cristiano", "Portugal", 920);
        listJugadores();

        // Eliminar un jugador y listar nuevamente
        System.out.println("\nEliminando un jugador...");
        deleteJugadores(2);
        listJugadores();

        // Cerrar la conexión a la base de datos
        db.close();
    }

    public static void createTables() {
        
    }

    public static void addJugador(String nom, String cognoms, int goles) {
        
    }

    public static void addEquipo(String nom, int credits) {
       
    }

    public static void addPichchi(int jugadorId, int equipoId, int temporada) {
        
    }

    public static void listJugadores() {
       
    }

    public static void listEquipos() {
        
    }

    public static void listPichichi() {
        
    }

    public static void updateJugadores(int id, String nom, String pais, int goles) {
       
    }

    public static void deleteJugadores(int id) {
        
    }

    private static String escapeSQL(String input) {
        return input.replace("'", "''");
    }
}
