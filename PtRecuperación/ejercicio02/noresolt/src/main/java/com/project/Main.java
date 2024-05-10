package com.project;

import java.sql.*;
import java.util.Locale;
import main.java.com.project.Equipo;
import main.java.com.project.Jugador;
import main.java.com.project.Persona;

public class Main {

    private static String URL = "jdbc:sqlite:futbol.db"; // Cambiamos la URL de conexión
    // No necesitamos USER ni PASSWORD para SQLite

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        try (Connection conn = DriverManager.getConnection(URL)) { // Eliminamos USER y PASSWORD
            conn.setAutoCommit(false);
    
            createTables(conn);
    
            // Añadir jugadores y equipos
            addJugador(conn, 1, "Lionel Messi", 30, "Delantero", 10);
            addJugador(conn, 2, "Cristiano Ronaldo", 32, "Delantero", 7);
            addEquipo(conn, 1, "FC Barcelona", "España");
            addEquipo(conn, 2, "Juventus FC", "Italia");
    
            // Actualizar la edad de un jugador
            updateEdadJugador(conn, 1, 31);
    
            // Eliminar un equipo
            deleteEquipo(conn, 2);
    
            listJugadores(conn);
            listEquipos(conn);
    
            conn.commit();
    
            conn.close();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        if (!"test".equals(System.getProperty("environment"))) {
            System.exit(0);
        }
    }

    public static void createTables(Connection conn) throws SQLException {
        
    }

    public static void addJugador(Connection conn, int id, String nombre, int edad, String posicion, int equipoId) throws SQLException {
        
    }

    public static void addEquipo(Connection conn, int id, String nombre, String pais) throws SQLException {
        
    }

    public static void listJugadores(Connection conn) throws SQLException {
        
    }
    
    public static void listEquipos(Connection conn) throws SQLException {
        
    }

    public static void deleteEquipo(Connection conn, int equipoId) throws SQLException {
        
    }
    

    public static void updateEdadJugador(Connection conn, int jugadorId, int nuevaEdad) throws SQLException {
        
    }
}
