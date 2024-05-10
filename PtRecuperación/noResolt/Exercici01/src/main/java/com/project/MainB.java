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
        
    }

    public int insertTrofeo(Trofeo trofeo) {
       
    }
    
    public int insertJugador(Jugador jugador) {
        
    }

    public boolean deleteTrofeo(int id) {
        
    }
    
    public boolean deleteJugador(int id) {
        
    }

    public List<Trofeo> getAllTrofeos() {
        
    }

    public List<Jugador> getAllJugadores() {
        
    }

    public boolean updateTrofeo(Trofeo trofeo) {
        
    }

    public boolean updateJugador(Jugador jugador) {
        
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