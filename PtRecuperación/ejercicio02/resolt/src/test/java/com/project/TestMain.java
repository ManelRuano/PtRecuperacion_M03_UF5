package com.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.stefanbirkner.systemlambda.SystemLambda;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.Locale;

public class TestMain {

    private static final String URL = "jdbc:sqlite:futbol.db"; // Cambiamos la URL de conexión

    @Test
    public void testMainOutput() throws Exception {
        System.setProperty("environment", "test");

        String text = SystemLambda.tapSystemOut(() -> {
            Locale.setDefault(Locale.US);

            try (Connection conn = DriverManager.getConnection(URL)) {
                conn.setAutoCommit(false);

                Main.createTables(conn);
                Main.addJugador(conn, 1, "Lionel Messi", 30, "Delantero", 10);
                Main.addJugador(conn, 2, "Cristiano Ronaldo", 32, "Delantero", 7);
                Main.addEquipo(conn, 1, "FC Barcelona", "España");
                Main.addEquipo(conn, 2, "Juventus FC", "Italia");

                Main.listJugadores(conn);
                Main.listEquipos(conn);

                conn.commit();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        });

        String expectedOutput = """
            [id=1, nombre='Lionel Messi', edad=30, posicion='Delantero', equipoId=10]
            [id=2, nombre='Cristiano Ronaldo', edad=32, posicion='Delantero', equipoId=7]
            [id=1, nombre='FC Barcelona', pais='España']
            [id=2, nombre='Juventus FC', pais='Italia']
            """;

        assertTrue(text.contains(expectedOutput), "Output does not contain expected text.");
    }

    @Test
    public void testMainTables() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL)) {
            DatabaseMetaData dbMetaData = conn.getMetaData();

            checkTableExists(dbMetaData, "Jugadores");
            checkTableExists(dbMetaData, "Equipos");
        }
    }

    private void checkTableExists(DatabaseMetaData metaData, String tableName) throws SQLException {
        try (ResultSet rs = metaData.getTables(null, null, tableName, null)) {
            assertTrue(rs.next(), "Table " + tableName + " does not exist.");
        }
    }

    @Test
    public void testMainCalls() throws NoSuchMethodException {
        Class<Main> clazz = Main.class;

        // Verify that all the expected methods are present and have correct modifiers
        Method createTables = clazz.getDeclaredMethod("createTables", Connection.class);
        assertTrue(Modifier.isStatic(createTables.getModifiers()), "createTables should be static");

        Method addJugador = clazz.getDeclaredMethod("addJugador", Connection.class, int.class, String.class, int.class, String.class, int.class);
        assertTrue(Modifier.isStatic(addJugador.getModifiers()), "addJugador should be static");

        Method addEquipo = clazz.getDeclaredMethod("addEquipo", Connection.class, int.class, String.class, String.class);
        assertTrue(Modifier.isStatic(addEquipo.getModifiers()), "addEquipo should be static");

        Method listJugadores = clazz.getDeclaredMethod("listJugadores", Connection.class);
        assertTrue(Modifier.isStatic(listJugadores.getModifiers()), "listJugadores should be static");

        Method listEquipos = clazz.getDeclaredMethod("listEquipos", Connection.class);
        assertTrue(Modifier.isStatic(listEquipos.getModifiers()), "listEquipos should be static");
    }

    @Test
    public void testUpdateEdadJugador() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.setAutoCommit(false);
            
            Main.createTables(conn);
            Main.addJugador(conn, 3, "Neymar Jr.", 29, "Delantero", 10);
            Main.updateEdadJugador(conn, 3, 30);

            // Verificar el cambio
            String sql = "SELECT edad FROM Jugadores WHERE jugadorId = 3";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                assertTrue(rs.next(), "Debe encontrar el jugador actualizado");
                assertEquals(30, rs.getInt("edad"), "La edad debe haber sido actualizada");
            }

            conn.rollback();  // Revertimos para no afectar otros tests
        }
    }

    @Test
    public void testDeleteEquipo() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL)) {
            conn.setAutoCommit(false);
            
            Main.createTables(conn);
            Main.addEquipo(conn, 3, "Real Madrid", "España");
            Main.deleteEquipo(conn, 3);

            // Verificar si el equipo fue eliminado
            String sql = "SELECT COUNT(*) AS count FROM Equipos WHERE equipoId = 3";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                assertTrue(rs.next(), "Error al verificar el equipo eliminado");
                assertEquals(0, rs.getInt("count"), "El equipo no debe existir en la base de datos");
            }

            conn.rollback();  // Revertimos para no afectar otros tests
        }
    }
}
