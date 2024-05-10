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
        listMissions();

        // Close the database connection
        db.close();
    }

    public static void createTables() {
        AppData db = AppData.getInstance();
        db.update("DROP TABLE IF EXISTS jugadores");
        db.update("DROP TABLE IF EXISTS equipos");
        db.update("DROP TABLE IF EXISTS balonDeOro");
        db.update("CREATE TABLE IF NOT EXISTS jugadores (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, pais TEXT NOT NULL, zurdo BOOLEAN NOT NULL)");
        db.update("CREATE TABLE IF NOT EXISTS equipos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT NOT NULL, pais TEXT NOT NULL)");
        db.update("CREATE TABLE IF NOT EXISTS balonDeOro (id INTEGER PRIMARY KEY AUTOINCREMENT, jugador_id INTEGER NOT NULL, equipo_id INTEGER NOT NULL, año TEXT NOT NULL, FOREIGN KEY(jugador_id) REFERENCES jugadores(id), FOREIGN KEY(equipo_id) REFERENCES equipos(id))");
    }
    
    public static void addJugadores(String nombre, String pais, boolean zurdo) {
        String sql = "INSERT INTO jugadores (nombre, pais, zurdo) VALUES ('" + nombre + "', '" + pais + "', " + (zurdo ? "1" : "0") + "')";
        AppData.getInstance().update(sql);
    }

    public static void addEquipos(String name, String pais) {
        String sql = "INSERT INTO equipos (name, pais) VALUES ('" + escapeSQL(name) + "', '" + escapeSQL(pais) + ")";
        AppData.getInstance().update(sql);
    }
    
    public static void addBalonDeOro(int jugadorId, int equipoId, String año) {
        String sql = "INSERT INTO balonDeOro (jugador_id, equipo_id, año) VALUES (" + jugadorId + ", " + equipoId + ", '" + año + "')";
        AppData.getInstance().update(sql);
    }

    public static void listJugadores() {
        String sql = "SELECT * FROM jugadores";
        List<Map<String, Object>> species = AppData.getInstance().query(sql);
        for (Map<String, Object> s : species) {
            System.out.println("ID: " + s.get("id") + ", Nombre: " + s.get("nombre") + ", Pais: " + s.get("pais") + ", Zurdo: " + (Integer.parseInt(s.get("zurdo").toString()) == 1 ? "Yes" : "No"));
        }
    }

    public static void listEquipos() {
        String sql = "SELECT * FROM equipos";
        List<Map<String, Object>> species = AppData.getInstance().query(sql);
        for (Map<String, Object> s : species) {
            System.out.println("ID: " + s.get("id") + ", Nombre: " + s.get("nombre") + ", Pais: " + s.get("pais"));
        }
    }

    public static void listBalonDeOro() {
        String sql = "SELECT * FROM balonDeOro";
        List<Map<String, Object>> species = AppData.getInstance().query(sql);
        for (Map<String, Object> s : species) {
            System.out.println("ID: " + s.get("id") + ", Jugador ID: " + s.get("jugador_id") + ", Equipo ID: " + s.get("equipo_id") + ", Año: " + s.get("año"));
        }
    }
   
    // Mètode auxiliar per escapar cometes simples que podrien rompre la cadena SQL
    private static String escapeSQL(String input) {
        return input.replace("'", "''");
    }   
}
