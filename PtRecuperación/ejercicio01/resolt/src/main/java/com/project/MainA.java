package com.project;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainA {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        AppData db = AppData.getInstance();

        // Crear las tablas relacionadas con jugadores, equipos y jugadores
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
        AppData db = AppData.getInstance();
        db.update("DROP TABLE IF EXISTS Jugadores");
        db.update("DROP TABLE IF EXISTS Equipos");
        db.update("DROP TABLE IF EXISTS Pichichi");
        db.update("CREATE TABLE Jugadores (id INTEGER PRIMARY KEY, nom TEXT, pais TEXT, goles INTEGER)");
        db.update("CREATE TABLE Equipos (id INTEGER PRIMARY KEY, nom TEXT, año INTEGER)");
        db.update("CREATE TABLE Pichichi (jugadorId INTEGER, equipoId INTEGER, temporada INTEGER, FOREIGN KEY (jugadorId) REFERENCES Jugadores(id), FOREIGN KEY (equipoId) REFERENCES Equipos(id))");
    }

    public static void addJugador(String nom, String cognoms, int goles) {
        String sql = "INSERT INTO Jugadores (nom, pais, goles) VALUES ('" + escapeSQL(nom) + "', '" + escapeSQL(cognoms) + "', " + goles + ")";
        AppData.getInstance().update(sql);
    }

    public static void addEquipo(String nom, int credits) {
        String sql = "INSERT INTO Equipos (nom, año) VALUES ('" + escapeSQL(nom) + "', " + credits + ")";
        AppData.getInstance().update(sql);
    }

    public static void addPichchi(int jugadorId, int equipoId, int temporada) {
        String sql = "INSERT INTO Pichichi (jugadorId, equipoId, temporada) VALUES (" + jugadorId + ", " + equipoId + ", " + temporada + ")";
        AppData.getInstance().update(sql);
    }

    public static void listJugadores() {
        String sql = "SELECT * FROM Jugadores";
        List<Map<String, Object>> estudiants = AppData.getInstance().query(sql);
        estudiants.forEach(e -> System.out.println("id: " + e.get("id") + ", nom: " + e.get("nom") + ", pais: " + e.get("pais") + ", goles: " + e.get("goles")));
    }

    public static void listEquipos() {
        String sql = "SELECT * FROM Equipos";
        List<Map<String, Object>> assignatures = AppData.getInstance().query(sql);
        assignatures.forEach(a -> System.out.println("id: " + a.get("id") + ", nom: " + a.get("nom") + ", credits: " + a.get("año")));
    }

    public static void listPichichi() {
        String sql = "SELECT * FROM Pichichi";
        List<Map<String, Object>> matriculacions = AppData.getInstance().query(sql);
        matriculacions.forEach(m -> System.out.println("id jugador: " + m.get("jugadorId") + ", id equipo: " + m.get("equipoId") + ", temporada: " + m.get("temporada")));
    }

    public static void updateJugadores(int id, String nom, String pais, int goles) {
        String sql = "UPDATE Jugadores SET nom = '" + escapeSQL(nom) + "', pais = '" + escapeSQL(pais) + "', goles = " + goles + " WHERE id = " + id;
        AppData.getInstance().update(sql);
    }

    public static void deleteJugadores(int id) {
        String sql = "DELETE FROM Jugadores WHERE id = " + id;
        AppData.getInstance().update(sql);
    }

    private static String escapeSQL(String input) {
        return input.replace("'", "''");
    }
}
