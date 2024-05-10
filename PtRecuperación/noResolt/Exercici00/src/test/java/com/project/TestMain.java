package com.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.stefanbirkner.systemlambda.SystemLambda;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.io.File;
import java.sql.*;
import java.util.Locale;

public class TestMain {

    @Test
    public void testMainOutputA() throws Exception {

        System.setProperty("environment", "test");

        String text = SystemLambda.tapSystemOut(() -> {
            // Executa el main per a provar la seva sortida
            String[] args = {}; // Passa els arguments necessaris si n'hi ha
            MainA.main(args);
        });
        text = text.replace("\r\n", "\n");

        // Comprova que la sortida conté el text esperat
        String expectedOutput = """
            Adding initial jugadores...
            ID: 1, Nombre: Cristiano Ronaldo, Pais: Alpha Centauri, Zurdo: No
            ID: 2, Nombre: Messi, Pais: Argentina, Zurdo: Yes

            Adding equipos...
            ID: 1, Nombre: Al Hilal, Pais: Arabia
            ID: 2, Nombre: Miami FC, Pais: EEUU

            Adding balones de oro...
            ID: 1, Jugador ID: 1, Equipo ID: 1, Año: 2017
            ID: 2, Jugador ID: 2, Equipo ID: 2, Año: 2023
            """.replace("\r\n", "\n").replace("            ","");
            String diff = TestStringUtils.findFirstDifference(text, expectedOutput);
            assertTrue(diff.compareTo("identical") == 0, 
                "\n>>>>>>>>>> >>>>>>>>>>\n" +
                diff +
                "<<<<<<<<<< <<<<<<<<<<\n");
    }

    @Test
    public void testMainOutputB() throws Exception {

        System.setProperty("environment", "test");

        String text = SystemLambda.tapSystemOut(() -> {
            // Executa el main per a provar la seva sortida
            String[] args = {}; // Passa els arguments necessaris si n'hi ha
            MainB.main(args);
        });
        text = text.replace("\r\n", "\n");

        // Comprova que la sortida conté el text esperat
        String expectedOutput = """
            Adding initial jugadores...
            ID: 1, Nombre: Cristiano Ronaldo, Pais: Alpha Centauri, Zurdo: No
            ID: 2, Nombre: Messi, Pais: Argentina, Zurdo: Yes
            ID: 3, Nombre: Griezman, Pais: Francia, Zurdo: Yes

            Adding equipos...
            ID: 1, Nombre: Al Hilal, Pais: Arabia
            ID: 2, Nombre: Miami FC, Pais: EEUU
            ID: 3, Nombre: PSG, Pais: Francia

            Adding balones de oro...
            ID: 1, Jugador ID: 1, Equipo ID: 1, Año: 2017
            ID: 2, Jugador ID: 2, Equipo ID: 2, Año: 2023
            ID: 3, Jugador ID: 3, Equipo ID: 2, Año: 2018

            Updating jugadores 'Cristiano Ronaldo' to 'Cristiano'...
            ID: 1, Nombre: Cristiano, Pais: Alpha Centauri, Zurdo: No
            ID: 2, Nombre: Messi, Pais: Argentina, Zurdo: Yes
            ID: 3, Nombre: Griezman, Pais: Francia, Zurdo: Yes

            Deleting equipo 'Al Hilal'...
            ID: 2, Nombre: Miami FC, Pais: EEUU
            ID: 3, Nombre: PSG, Pais: Francia

            Adding another species and updating a mission...
            ID: 1, Nombre: Cristiano Ronaldo, Pais: Alpha Centauri, Zurdo: No
            ID: 2, Nombre: Messi, Pais: Argentina, Zurdo: Yes
            ID: 3, Nombre: Griezman, Pais: Francia, Zurdo: Yes
            ID: 4, Nombre: Neymar, Pais: Brasil, Zurdo: No
            ID: 1, Jugador ID: 1, Equipo ID: 1, Año: 2017
            ID: 2, Jugador ID: 2, Equipo ID: 2, Año: 2019
            ID: 3, Jugador ID: 3, Equipo ID: 2, Año: 2018

            Deleting jugador 'Messi'...
            ID: 1, Nombre: Cristiano Ronaldo, Pais: Alpha Centauri, Zurdo: No
            ID: 3, Nombre: Griezman, Pais: Francia, Zurdo: Yes
            ID: 4, Nombre: Neymar, Pais: Brasil, Zurdo: No

            """.replace("\r\n", "\n").replace("            ","");
            String diff = TestStringUtils.findFirstDifference(text, expectedOutput);
            assertTrue(diff.compareTo("identical") == 0, 
                "\n>>>>>>>>>> >>>>>>>>>>\n" +
                diff +
                "<<<<<<<<<< <<<<<<<<<<\n");
    }

    @Test
    public void testMainTablesA() throws SQLException {
        System.setProperty("environment", "test");
        String url = "jdbc:sqlite:dades.sqlite";

        File dbFile = new File("dades.sqlite");
        assertTrue(dbFile.exists(), "The database file does not exist.");

        try (Connection conn = DriverManager.getConnection(url)) {
            DatabaseMetaData dbMetaData = conn.getMetaData();

            // Check for existence of species table
            checkTableExists(dbMetaData, "jugadores", "id", "nombre", "pais", "zuerdo");

            // Check for existence of spaceships table
            checkTableExists(dbMetaData, "equipos", "id", "nombre", "pais");

            // Check for existence of missions table
            checkTableExists(dbMetaData, "balonDeOro", "id", "jugador_id", "equipo_id", "año");

            // Check foreign keys for missions table
            checkForeignKey(dbMetaData, "balonDeOro", "jugadores", "jugador_id");
            checkForeignKey(dbMetaData, "balonDeOro", "equipos", "equipo_id");
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

    private void checkForeignKey(DatabaseMetaData metaData, String tableName, String pkTableName, String fkColumnName) throws SQLException {
        try (ResultSet rs = metaData.getImportedKeys(null, null, tableName)) {
            boolean foundFK = false;
            while (rs.next()) {
                if (pkTableName.equals(rs.getString("PKTABLE_NAME")) && fkColumnName.equals(rs.getString("FKCOLUMN_NAME"))) {
                    foundFK = true;
                    break;
                }
            }
            assertTrue(foundFK, "The table " + tableName + " does not have the correct foreign key relation with " + pkTableName);
        }
    }


    @Test
    public void testMainCallsA() throws Exception {
        Class<MainA> clazz = MainA.class;

        // Check for methods related to creating, adding, and listing entries
        assertMethod(clazz, "createTables", true, false, "The createTables method should be defined correctly.");
        assertMethod(clazz, "addJugadores", true, false, "The addJugadores method should be defined correctly.", String.class, String.class, boolean.class);
        assertMethod(clazz, "addEquipos", true, false, "The addEquipos method should be defined correctly.", String.class, String.class);
        assertMethod(clazz, "addBalonDeOro", true, false, "The addEquipos method should be defined correctly.", int.class, int.class, String.class);
        assertMethod(clazz, "listJugadores", true, false, "The listJugadores method should be defined correctly.");
        assertMethod(clazz, "listEquipos", true, false, "The listEquipos method should be defined correctly.");
        assertMethod(clazz, "listBalonDeOro", true, false, "The listBalonDeOro method should be defined correctly.");
    }

    @Test
    public void testMainCallsB() throws Exception {
        Class<MainB> clazz = MainB.class;

        // Check for methods related to creating, adding, and listing entries
        assertMethod(clazz, "updateJugadores", true, false, "The updateJugadores method should be defined correctly.", int.class, String.class, String.class, boolean.class);
        assertMethod(clazz, "updateBalonDeOro", true, false, "The updateBalonDeOro method should be defined correctly.", int.class, String.class);
        assertMethod(clazz, "deleteEquipos", true, false, "The deleteEquipos method should be defined correctly.", int.class);
        assertMethod(clazz, "deleteJugador", true, false, "The deleteJugador method should be defined correctly.", int.class);
    }

    private void assertMethod(Class<?> clazz, String methodName, boolean shouldBeStatic, boolean shouldBePrivate, String message, Class<?>... parameterTypes) throws NoSuchMethodException {
        // Utilitza getDeclaredMethod per accedir a mètodes privats
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
    
        // Comprova si el mètode és estàtic
        boolean isStatic = Modifier.isStatic(method.getModifiers());
        assertEquals(shouldBeStatic, isStatic, message + " El mètode hauria de ser " + (shouldBeStatic ? "static" : "no static") + ".");
    
        // Comprova si el mètode és privat
        boolean isPrivate = Modifier.isPrivate(method.getModifiers());
        assertEquals(shouldBePrivate, isPrivate, message + " El mètode hauria de ser " + (shouldBePrivate ? "privat" : "no privat") + ".");
    }

    @Test
    public void testMainExtraA() throws Exception {
        String text = SystemLambda.tapSystemOut(() -> {

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
            addEquipos("Al Hilal", "España");
            addEquipos("Miami FC", "EEUU");
            listEquipos();
    
            // Add trofeos and list
            System.out.println("\nAdding balones de oro...");
            addBalonDeOro(1, 1, "2017");
            addBalonDeOro(2, 2, "2023");
            listMissions();
    
            // Close the database connection
            db.close();
        });
        text = text.replace("\r\n", "\n");

        String expectedOutput = """
            Adding initial jugadores...
            ID: 1, Nombre: Cristiano Ronaldo, Pais: Alpha Centauri, Zurdo: No
            ID: 2, Nombre: Messi, Pais: Argentina, Zurdo: Yes

            Adding equipos...
            ID: 1, Nombre: Al Hilal, Pais: Arabia
            ID: 2, Nombre: Miami FC, Pais: EEUU

            Adding balones de oro...
            ID: 1, Jugador ID: 1, Equipo ID: 1, Año: 2017
            ID: 2, Jugador ID: 2, Equipo ID: 2, Año: 2023
            """.replace("\r\n", "\n").replace("            ","");

        String diff = TestStringUtils.findFirstDifference(text, expectedOutput);
            assertTrue(diff.compareTo("identical") == 0, 
                ">>>>>>>>>> >>>>>>>>>>\n" +
                diff +
                "<<<<<<<<<< <<<<<<<<<<\n");
    }

    @Test
    public void testMainExtraB() throws Exception {
        String text = SystemLambda.tapSystemOut(() -> {

            Locale.setDefault(Locale.US);

        AppData db = AppData.getInstance();

        // Create extraterrestrial-themed tables
        MainB.createTables();

        // Initially add species and list
        System.out.println("Adding initial jugadores...");
        MainB.addJugadores("Cristiano Ronaldo", "Portugal", false);
        MainB.addJugadores("Messi", "Argentina", true);
        MainB.addJugadores("Griezman", "Francia", true);
        MainB.listJugadores();

        // Add equipos and list
        System.out.println("\nAdding equipos...");
        MainB.addEquipos("Al Hilal", "Arabia");
        MainB.addEquipos("Miami FC", "EEUU");
        MainB.addEquipos("PSG", "Francia");
        MainB.listEquipos();

        // Add trofeos and list
        System.out.println("\nAdding balones de oro...");
        MainB.addBalonDeOro(1, 1, "2017");
        MainB.addBalonDeOro(2, 2, "2023");
        MainB.addBalonDeOro(3, 2, "2018");
        MainB.listBalonDeOro();

        // Update jugadores and list
        System.out.println("\nUpdating jugadores 'Cristiano Ronaldo' to 'Cristiano'...");
        MainB.updateJugadores(1, "Cristiano", "Portugal", false);
        MainB.listJugadores();

        // Delete a equipo and list
        System.out.println("\nDeleting equipo 'Al Hilal'...");
        MainB.deleteSpaceship(1);
        MainB.listSpaceships();

        // Add another species, update a mission, and list both
        System.out.println("\nAdding another species and updating a mission...");
        MainB.addJugadores("Neymar", "Brasil", false);
        MainB.updateMission(2, "2019");
        MainB.listJugadores();
        MainB.listBalonDeOro();

        // Finally, delete a species and list
        System.out.println("\nDeleting jugador 'Messi'...");
        MainB.deleteJugador(2);
        MainB.listJugadores();

        // Checking deletion effect on missions
        System.out.println("\nListing missions after deletion of a species...");
        MainB.listBalonDeOro();
        // Close the database connection
        db.close();
        });

        text = text.replace("\r\n", "\n");

        String expectedOutput = """
            Adding initial jugadores...
            ID: 1, Nombre: Cristiano Ronaldo, Pais: Alpha Centauri, Zurdo: No
            ID: 2, Nombre: Messi, Pais: Argentina, Zurdo: Yes
            ID: 3, Nombre: Griezman, Pais: Francia, Zurdo: Yes

            Adding equipos...
            ID: 1, Nombre: Al Hilal, Pais: Arabia
            ID: 2, Nombre: Miami FC, Pais: EEUU
            ID: 3, Nombre: PSG, Pais: Francia

            Adding balones de oro...
            ID: 1, Jugador ID: 1, Equipo ID: 1, Año: 2017
            ID: 2, Jugador ID: 2, Equipo ID: 2, Año: 2023
            ID: 3, Jugador ID: 3, Equipo ID: 2, Año: 2018

            Updating jugadores 'Cristiano Ronaldo' to 'Cristiano'...
            ID: 1, Nombre: Cristiano, Pais: Alpha Centauri, Zurdo: No
            ID: 2, Nombre: Messi, Pais: Argentina, Zurdo: Yes
            ID: 3, Nombre: Griezman, Pais: Francia, Zurdo: Yes

            Deleting equipo 'Al Hilal'...
            ID: 2, Nombre: Miami FC, Pais: EEUU
            ID: 3, Nombre: PSG, Pais: Francia

            Adding another species and updating a mission...
            ID: 1, Nombre: Cristiano Ronaldo, Pais: Alpha Centauri, Zurdo: No
            ID: 2, Nombre: Messi, Pais: Argentina, Zurdo: Yes
            ID: 3, Nombre: Griezman, Pais: Francia, Zurdo: Yes
            ID: 4, Nombre: Neymar, Pais: Brasil, Zurdo: No
            ID: 1, Jugador ID: 1, Equipo ID: 1, Año: 2017
            ID: 2, Jugador ID: 2, Equipo ID: 2, Año: 2019
            ID: 3, Jugador ID: 3, Equipo ID: 2, Año: 2018

            Deleting jugador 'Messi'...
            ID: 1, Nombre: Cristiano Ronaldo, Pais: Alpha Centauri, Zurdo: No
            ID: 3, Nombre: Griezman, Pais: Francia, Zurdo: Yes
            ID: 4, Nombre: Neymar, Pais: Brasil, Zurdo: No

            Listing missions after deletion of a jugadores...
            ID: 1, Jugador ID: 1, Equipo ID: 1, Año: 2017
            ID: 2, Jugador ID: 2, Equipo ID: 2, Año: 2023
            """.replace("\r\n", "\n").replace("            ","");

        String diff = TestStringUtils.findFirstDifference(text, expectedOutput);
            assertTrue(diff.compareTo("identical") == 0, 
                ">>>>>>>>>> >>>>>>>>>>\n" +
                diff +
                "<<<<<<<<<< <<<<<<<<<<\n");
    }
}
