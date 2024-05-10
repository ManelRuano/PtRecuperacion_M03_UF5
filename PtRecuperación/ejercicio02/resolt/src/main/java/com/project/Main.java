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
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS Jugadores (" + // Usamos IF NOT EXISTS para evitar errores si la tabla ya existe
                         "jugadorId INTEGER PRIMARY KEY," +
                         "nombre TEXT," + // Cambiamos VARCHAR por TEXT
                         "edad INTEGER," +
                         "posicion TEXT," +
                         "equipoId INTEGER," +
                         "FOREIGN KEY (equipoId) REFERENCES Equipos(equipoId));");

            stmt.execute("CREATE TABLE IF NOT EXISTS Equipos (" + // Usamos IF NOT EXISTS para evitar errores si la tabla ya existe
                         "equipoId INTEGER PRIMARY KEY," +
                         "nombre TEXT," + // Cambiamos VARCHAR por TEXT
                         "pais TEXT);"); // Cambiamos VARCHAR por TEXT
        }
    }

    public static void addJugador(Connection conn, int id, String nombre, int edad, String posicion, int equipoId) throws SQLException {
        String sql = "INSERT INTO Jugadores (jugadorId, nombre, edad, posicion, equipoId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setInt(3, edad);
            pstmt.setString(4, posicion);
            pstmt.setInt(5, equipoId);
            pstmt.executeUpdate();
        }
    }

    public static void addEquipo(Connection conn, int id, String nombre, String pais) throws SQLException {
        String sql = "INSERT INTO Equipos (equipoId, nombre, pais) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, pais);
            pstmt.executeUpdate();
        }
    }

    public static void listJugadores(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Jugadores";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("jugadorId");
                String nombre = rs.getString("nombre");
                int edad = rs.getInt("edad");
                String posicion = rs.getString("posicion");
                int equipoId = rs.getInt("equipoId");
    
                Jugador jugador = new Jugador(id, nombre, edad, posicion, equipoId);
                System.out.println(jugador);
            }
        }
    }
    
    public static void listEquipos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Equipos";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("equipoId");
                String nombre = rs.getString("nombre");
                String pais = rs.getString("pais");
    
                Equipo equipo = new Equipo(id, nombre, pais);
                System.out.println(equipo);
            }
        }
    }

    public static void deleteEquipo(Connection conn, int equipoId) throws SQLException {
        String sql = "DELETE FROM Equipos WHERE equipoId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, equipoId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Equipo eliminado con éxito con ID: " + equipoId);
            } else {
                System.out.println("No se encontró el equipo con ID: " + equipoId);
            }
        }
    }
    

    public static void updateEdadJugador(Connection conn, int jugadorId, int nuevaEdad) throws SQLException {
        String sql = "UPDATE Jugadores SET edad = ? WHERE jugadorId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nuevaEdad);
            pstmt.setInt(2, jugadorId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Edad actualizada para el jugador con ID: " + jugadorId);
            } else {
                System.out.println("No se encontró el jugador con ID: " + jugadorId);
            }
        }
    }
}
