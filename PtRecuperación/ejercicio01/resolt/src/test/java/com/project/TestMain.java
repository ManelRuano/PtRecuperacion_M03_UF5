package com.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.stefanbirkner.systemlambda.SystemLambda;

import java.io.File;
import java.sql.*;
import java.util.Locale;

public class TestMain {

    @Test
    public void testMainOutputA() throws Exception {
        System.setProperty("environment", "test");

        String text = SystemLambda.tapSystemOut(() -> {
            String[] args = {}; 
            MainA.main(args);
        });
        text = text.replace("\r\n", "\n");

        String expectedOutput = """
            Agregando jugadores...
            id: 1, nom: Messi, pais: Argentina, goles: 870
            id: 2, nom: Cristiano Ronaldo, pais: Portugal, goles: 920

            Agregando equipos...
            id: 1, nom: Al Nassr, credits: 1955
            id: 2, nom: Inter Miami, credits: 2018

            Agregando pichichi...
            id jugador: 1, id equipo: 2, temporada: 2009
            id jugador: 2, id equipo: 1, temporada: 2014

            Actualizando jugador...
            id: 1, nom: Messi, pais: Argentina, goles: 870
            id: 2, nom: Cristiano, pais: Portugal, goles: 920

            Eliminando un jugador...
            id: 1, nom: Messi, pais: Argentina, goles: 870
            """.replace("\r\n", "\n").replace("            ","");

        String diff = TestStringUtils.findFirstDifference(text, expectedOutput);
        assertTrue(diff.compareTo("identical") == 0, 
            "\n>>>>>>>>>> >>>>>>>>>>\n" +
            diff +
            "<<<<<<<<<< <<<<<<<<<<\n");
    }

    // Additional test methods can be modeled after testMainOutputA.

    @Test
    public void testMainTablesA() throws SQLException {
        System.setProperty("environment", "test");
        String url = "jdbc:sqlite:dades.sqlite";

        File dbFile = new File("dades.sqlite");
        assertTrue(dbFile.exists(), "The database file does not exist.");

        try (Connection conn = DriverManager.getConnection(url)) {
            DatabaseMetaData dbMetaData = conn.getMetaData();

            // Check for existence of Estudiants table
            checkTableExists(dbMetaData, "Jugadores", "id", "nom", "pais", "goles");

            // Check for existence of Assignatures table
            checkTableExists(dbMetaData, "Equipos", "id", "nom", "año");

            // Check for existence of Matriculacions table
            checkTableExists(dbMetaData, "Pichichi", "jugadorId", "equipoId", "temporada");
        }
    }

    private void checkTableExists(DatabaseMetaData metaData, String tableName, String... columnNames) throws SQLException {
        try (ResultSet rs = metaData.getTables(null, null, tableName, null)) {
            assertTrue(rs.next(), "Table " + tableName + " does not exist.");
        }

        for (String columnName : columnNames) {
            try (ResultSet rs = metaData.getColumns(null, null, tableName, columnName)) {
                assertTrue(rs.next(), "Column " + columnName + " does not exist in table " + tableName);
            }
        }
    }

}
