package com.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.stefanbirkner.systemlambda.SystemLambda;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

        // Obté la data actual en el format necessari
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        // Comprova que la sortida conté el text esperat amb la data actual
        String expectedOutput = String.format("""
            Inserted Trofeo with ID: 1
            Inserted Trofeo with ID: 2
            Inserted Jugador with ID: 1
            Inserted Jugador with ID: 2
            Deleted Trofeo: true
            Deleted Jugador: true
            Trofeo{id=1, nombre='bota de oro', id_jugador=1}
            Jugador{id=1, nombre='Messi', fecha_nacimiento='%s', pais='Argentina'}
            """, formattedDate, formattedDate).replace("\r\n", "\n").replace("            ","");

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

        // Obté la data actual en el format necessari
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE);

        // Comprova que la sortida conté el text esperat amb la data actual
        String expectedOutput = String.format("""
            Inserted Trofeo with ID: 1
            Inserted Trofeo with ID: 2
            Inserted Jugador with ID: 1
            Inserted Jugador with ID: 2
            Deleted Trofeo: true
            Deleted Jugador: true
            Trofeo{id=1, nombre='bota de oro', id_jugador=1}
            Jugador{id=1, nombre='Messi', fecha_nacimiento='%s', pais='Argentina'}
            Updated Trofeo: true
            Updated Jugador: true
            """, formattedDate, formattedDate).replace("\r\n", "\n").replace("            ","");

        String diff = TestStringUtils.findFirstDifference(text, expectedOutput);
        assertTrue(diff.compareTo("identical") == 0, 
            "\n>>>>>>>>>> >>>>>>>>>>\n" +
            diff +
            "<<<<<<<<<< <<<<<<<<<<\n");
    }

    @Test
    public void testMainTablesA() throws SQLException {
        // Adjust this URL with your MySQL connection details
        String url = "jdbc:sqlite:dades.sqlite";
        Connection conn = DriverManager.getConnection(url);
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false); 
            
            DatabaseMetaData dbMetaData = conn.getMetaData();

            // Check the existence and columns of each table
            checkTableExistsAndColumns(dbMetaData, "trofeos", new String[]{"id", "nombre", "id_jugador"});
            checkTableExistsAndColumns(dbMetaData, "jugadores", new String[]{"id", "nombre", "fecha_nacimiento", "pais"});

            // Check foreign key relationship
            checkForeignKey(dbMetaData, "id_jugador", "jugadores", "id");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void checkTableExists(DatabaseMetaData dbMetaData, String tableName) throws SQLException {
        try (ResultSet rs = dbMetaData.getTables(null, null, tableName, null)) {
            assertTrue(rs.next(), "La taula " + tableName + " no existeix.");
        }
    }
    
    private void checkTableExistsAndColumns(DatabaseMetaData dbMetaData, String tableName, String[] columnNames) throws SQLException {
        checkTableExists(dbMetaData, tableName);
        try (ResultSet rs = dbMetaData.getColumns(null, null, tableName, null)) {
            for (String columnName : columnNames) {
                assertTrue(rs.next(), "Esperava més columnes a " + tableName + ".");
                assertEquals(columnName, rs.getString("COLUMN_NAME"), "Nom de columna no coincideix en " + tableName + ".");
            }
        }
    }
    
    private void checkForeignKey(DatabaseMetaData dbMetaData, String tableName, String pkTableName, String fkColumnName) throws SQLException {
        try (ResultSet rs = dbMetaData.getImportedKeys(null, null, tableName)) {
            boolean found = false;
            while (rs.next()) {
                if (rs.getString("PKTABLE_NAME").equals(pkTableName) && rs.getString("FKCOLUMN_NAME").equals(fkColumnName)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "No s'ha trobat la clau forana esperada de " + tableName + " a " + pkTableName + " sobre " + fkColumnName + ".");
        }
    }

    @Test
    public void testMainCallsA() throws Exception {
        Class<MainA> clazz = MainA.class;
    
        // Check if the expected methods exist and have the correct modifiers
        assertMethod(clazz, "createTables", false, false, "Error with the definition of the createTables function.");
        assertMethod(clazz, "insertTrofeo", false, false, "Error with the definition of the insertTrofeo function.", Trofeo.class);
        assertMethod(clazz, "insertJugador", false, false, "Error with the definition of the insertJugador function.", Jugador.class); 
        assertMethod(clazz, "deleteTrofeo", false, false, "Error with the definition of the deleteTrofeo function.", int.class); 
        assertMethod(clazz, "deleteJugador", false, false, "Error with the definition of the deleteJugador function.", int.class); 
        assertMethod(clazz, "getAllTrofeo", false, false, "Error with the definition of the getAllTrofeo function.");
        assertMethod(clazz, "getAllJugador", false, false, "Error with the definition of the getAllJugador function.");
    }

    @Test
    public void testMainCallsB() throws Exception {
        Class<MainB> clazz = MainB.class;
    
        // Check if the expected methods exist and have the correct modifiers
        assertMethod(clazz, "updateTrofeo", false, false, "Error with the definition of the updateTrofeo function.", Trofeo.class);
        assertMethod(clazz, "updateJugador", false, false, "Error with the definition of the updateJugador function.", Jugador.class);
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
            Date date = new Date(System.currentTimeMillis());
            
            MainB app = new MainB();
            app.connect();

            // Create tables
            app.createTables();

            date = new Date(0);

            List<Trofeo> listTrofeo = new ArrayList<>();
            listTrofeo.add(new Trofeo(-1, "bota de oro", 1));
            listTrofeo.add(new Trofeo(-1, "balon de oro", 2));
            listTrofeo.add(new Trofeo(-1, "Pichichi", 3));

            List<Jugador> listJugador = new ArrayList<>();
            listJugador.add(new Jugador(-1, "Messi", date, "Argentina"));
            listJugador.add(new Jugador(-1, "Cristiano", date, "Portugal"));
            listJugador.add(new Jugador(-1, "Neymar", date, "Brasil"));

            // Inserir dades a la base de dades
            for (Trofeo trof : listTrofeo) {
                int id = app.insertTrofeo(trof);
                trof.setId(id);
                System.out.println("Inserted trofeos with ID: " + id);
            }

            for (Jugador jug : listJugador) {
                int id = app.insertJugador(jug);
                jug.setId(id);
                System.out.println("Inserted jugadores with ID: " + id);
            }

            // Eliminar un element
            int idToDelete = listTrofeo.get(2).getId();
            boolean deleted = app.deleteTrofeo(idToDelete);
            System.out.println("Deleted Trofeo: " + deleted);
            app.commit();

            // Eliminar un element
            idToDelete = listJugador.get(2).getId();
            deleted = app.deleteJugador(idToDelete);
            System.out.println("Deleted Jugador: " + deleted);
            app.commit();

            List<Trofeo> listA = app.getAllTrofeo();
            app.printList(listA);

            List<Jugador> listB = app.getAllJugador();
            app.printList(listB);

            // Tanca la base de dades
            app.close();
        });

        text = text.replace("\r\n", "\n");

        String expectedOutput = """
            Inserted Trofeo with ID: 1
            Inserted Trofeo with ID: 2
            Inserted Trofeo with ID: 3
            Inserted Jugador with ID: 1
            Inserted Jugador with ID: 2
            Inserted Jugador with ID: 3
            Deleted Trofeo: true
            Deleted Jugador: true
            Trofeo{id=1, nombre='bota de oro', id_jugador=1}
            Trofeo{id=2, nombre='pichichi', id_jugador=3}
            Jugador{id=1, nombre='Messi', fecha_nacimiento='1970-01-01', pais='Argentina'}
            Jugador{id=2, nombre='Neymar', fecha_nacimiento='1970-01-01', pais='Brasil'}
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
            Date date = new Date(System.currentTimeMillis());
            
            MainB app = new MainB();
            app.connect();

            // Create tables
            app.createTables();

            date = new Date(0);

            List<Trofeo> listTrofeo = new ArrayList<>();
            listTrofeo.add(new Trofeo(-1, "bota de oro", 1));
            listTrofeo.add(new Trofeo(-1, "balon de oro", 2));
            listTrofeo.add(new Trofeo(-1, "Pichichi", 3));

            List<Jugador> listJugador = new ArrayList<>();
            listJugador.add(new Jugador(-1, "Messi", date, "Argentina"));
            listJugador.add(new Jugador(-1, "Cristiano", date, "Portugal"));
            listJugador.add(new Jugador(-1, "Neymar", date, "Brasil"));

            // Inserir dades a la base de dades
            for (Trofeo trof : listTrofeo) {
                int id = app.insertTrofeo(trof);
                trof.setId(id);
                System.out.println("Inserted trofeos with ID: " + id);
            }

            for (Jugador jug : listJugador) {
                int id = app.insertJugador(jug);
                jug.setId(id);
                System.out.println("Inserted jugadores with ID: " + id);
            }

            // Actualitzar un element
            Trofeo trofeo = listTrofeo.get(0);
            trofeo.setNombre("HELLO");
            boolean updated = app.updateTrofeo(trofeo);
            System.out.println("Updated Trofeo: " + updated);
            app.commit();

            // Eliminar un element
            int idToDelete = listTrofeo.get(2).getId();
            boolean deleted = app.deleteTrofeo(idToDelete);
            System.out.println("Deleted Trofeo: " + deleted);
            app.commit();

            // Actualitzar un element
            Jugador jugador = listJugador.get(0); 
            jugador.setNombre("SQUAREPANTS");
            updated = app.updateJugador(elmZHE);
            System.out.println("Updated Jugador: " + updated);
            app.commit();

            // Eliminar un element
            idToDelete = listJugador.get(2).getId();
            deleted = app.deleteJugador(idToDelete);
            System.out.println("Deleted Jugador: " + deleted);
            app.commit();

            List<Trofeo> listA = app.getAllTrofeo();
            app.printList(listA);

            List<Jugador> listB = app.getAllJugador();
            app.printList(listB);

            // Tanca la base de dades
            app.close();
        });

        text = text.replace("\r\n", "\n");

        String expectedOutput = """
            Inserted Trofeo with ID: 1
            Inserted Trofeo with ID: 2
            Inserted Trofeo with ID: 3
            Inserted Jugador with ID: 1
            Inserted Jugador with ID: 2
            Inserted Jugador with ID: 3
            Updated Trofeo: true
            Deleted Trofeo: true
            Updated Jugador: true
            Deleted Jugador: true
            Trofeo{id=1, nombre='HELLO', id_jugador=1}
            Trofeo{id=2, nombre='pichichi', id_jugador=3}
            Jugador{id=1, nombre='SQUAREPANTS', fecha_nacimiento='1970-01-01', pais='Argentina'}
            Jugador{id=2, nombre='Neymar', fecha_nacimiento='1970-01-01', pais='Brasil'}
            """.replace("\r\n", "\n").replace("            ","");

        String diff = TestStringUtils.findFirstDifference(text, expectedOutput);
            assertTrue(diff.compareTo("identical") == 0, 
                ">>>>>>>>>> >>>>>>>>>>\n" +
                diff +
                "<<<<<<<<<< <<<<<<<<<<\n");
    }
}
