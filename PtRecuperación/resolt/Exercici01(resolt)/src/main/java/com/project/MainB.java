package com.project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainB {

    private Connection conn;

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        
        Date date = new Date(System.currentTimeMillis());
        
        MainB app = new MainB();
        app.connect();

        // Create tables
        app.createTables();

        List<Trofeo> listTrofeo = new ArrayList<>();
        listTrofeo.add(new Trofeo(-1, "bota de oro", 1));
        listTrofeo.add(new Trofeo(-1, "balon de oro", 2));

        List<Jugador> listJugador = new ArrayList<>();
        listJugador.add(new Jugador(-1, "Messi", date, "Argentina"));
        listJugador.add(new Jugador(-1, "Cristiano", date, "Portugal"));

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
        int idToDelete = listTrofeo.get(1).getId();
        boolean deleted = app.deleteTrofeo(idToDelete);
        System.out.println("Deleted trofeo: " + deleted);
        app.commit();

        // Eliminar un element
        int idToDelete = listJugador.get(1).getId();
        boolean deleted = app.deleteJugador(idToDelete);
        System.out.println("Deleted jugador: " + deleted);
        app.commit();

        List<Trofeo> listA = app.getAllTrofeos();
        app.printList(listA);

        List<Jugador> listB = app.getAllJugadores();
        app.printList(listB);

        // Actualitzar un element
        ZHE524 elmZHE = listZHE524.get(0); 
        elmZHE.setZHE302("BLUEY");
        updated = app.updateZHE524(elmZHE);
        System.out.println("Updated ZHE524: " + updated);
        app.commit();

        // Tanca la base de dades
        app.close();
    }

    public void connect() {
        String url = "jdbc:sqlite:dades.sqlite";
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false); 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void commit() {
        try {
            conn.commit();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void createTables() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS trofeos;");
            stmt.execute("DROP TABLE IF EXISTS jugadores;");
            stmt.execute("CREATE TABLE IF NOT EXISTS trofeos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR(255) NOT NULL, id_jugador INT NOT NULL, FOREIGN KEY (id_jugador) REFERENCES jugadores(id));");
            stmt.execute("CREATE TABLE IF NOT EXISTS jugadores (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR(255) NOT NULL, fecha_nacimiento DATE NOT NULL, pais VARCHAR(255) NOT NULL)");
            conn.commit();
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int insertTrofeo(Trofeo trofeo) {
        String sql = "INSERT INTO trofeos (nombre, id_jugador) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, trofeo.getNombre());
            stmt.setInt(2, trofeo.getId_jugador());
            stmt.executeUpdate();
            conn.commit();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return -1;
    }
    
    public int insertJugador(Jugador jugador) {
        String sql = "INSERT INTO jugadores (nombre, fecha_nacimiento, pais) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, jugador.getNombre());
            stmt.setDate(2, new java.sql.Date(jugador.getFecha_nacimiento().getTime()));
            stmt.setString(3, jugador.getPais());
            stmt.executeUpdate();
            conn.commit();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Retorna l'ID generat
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
        return -1;
    }

    public boolean deleteTrofeo(int id) {
        String sql = "DELETE FROM trofeos WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            conn.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }
    
    public boolean deleteJugador(int id) {
        String sql = "DELETE FROM jugadores WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
    
            int affectedRows = stmt.executeUpdate();
            conn.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    public List<Trofeo> getAllTrofeos() {
        List<Trofeo> resultList = new ArrayList<>();
        String sql = "SELECT * FROM trofeos";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:dades.sqlite");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int idJugador = rs.getInt("id_Jugador");
                resultList.add(new Trofeo(id, nombre, idJugador));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    public List<Jugador> getAllJugadores() {
        List<Jugador> resultList = new ArrayList<>();
        String sql = "SELECT * FROM jugadores";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:dades.sqlite");
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Date fecha_nacimiento = rs.getDate("fecha_nacimiento");
                String pais = rs.getString("pais");
                resultList.add(new Jugador(id, nombre, fecha_nacimiento, pais));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resultList;
    }

    public boolean updateTrofeo(Trofeo trofeo) {
        String sql = "UPDATE trofeos SET nombre = ?, id_jugador = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, trofeo.getNombre());
            stmt.setInt(2, trofeo.getId_jugador());
            stmt.setInt(3, trofeo.getId());
            
            int affectedRows = stmt.executeUpdate();
            conn.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    public boolean updateJugador(Jugador jugador) {
        String sql = "UPDATE jugadores SET nombre = ?, fecha_nacimiento = ?, pais = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, jugador.getNombre());
            stmt.setDate(2, new java.sql.Date(jugador.getFecha_nacimiento().getTime()));
            stmt.setString(3, jugador.getPais());
            stmt.setInt(4, jugador.getId());
    
            int affectedRows = stmt.executeUpdate();
            conn.commit();
            return affectedRows > 0;
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }

    private void handleSQLException(SQLException e) {
        System.out.println("SQL Error Code: " + e.getErrorCode());
        System.out.println("SQL State: " + e.getSQLState());
        System.out.println("Error Message: " + e.getMessage());
        e.printStackTrace();
        try {
            conn.rollback();
            System.out.println("Transaction rolled back.");
        } catch (SQLException ex) {
            System.out.println("Error rolling back transaction.");
            ex.printStackTrace();
        }
    }
    
    public <T> void printList(List<T> list) {
        for (T item : list) {
            System.out.println(item.toString());
        }
    }
}