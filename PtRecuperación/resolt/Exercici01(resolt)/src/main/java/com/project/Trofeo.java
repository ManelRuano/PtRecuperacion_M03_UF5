package com.project;

class Trofeo {
    int id; 
    String nombre; 
    int id_jugador; 

    // Constructor
    public Trofeo(int id, String nombre, int id_jugador) {
        this.id = id;
        this.nombre = nombre;
        this.id_jugador = id_jugador;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getId_jugador() { return id_jugador; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setnombre(String nombre) { this.nombre = nombre; }
    public void setId_jugador(int id_jugador) { this.id_jugador = id_jugador; }

    // toString
    @Override
    public String toString() {
        return "Trofeo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", id_jugador=" + id_jugador +
                '}';
    }
}
