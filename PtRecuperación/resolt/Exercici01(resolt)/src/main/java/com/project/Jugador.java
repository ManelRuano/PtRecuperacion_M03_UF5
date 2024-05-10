package com.project;

import java.sql.Date;

class Jugador {
    int id; 
    String nombre; 
    Date fecha_nacimiento;
    String pais; 

    // Constructor
    public Jugador(int id, String nombre, Date fecha_nacimiento, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.pais = pais;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Date getFecha_nacimiento() { return fecha_nacimiento; }
    public String getPais() { return pais; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFecha_nacimiento(Date fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }
    public void setPais(String pais) { this.pais = pais; }

    // toString
    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", fecha_nacimiento='" + fecha_nacimiento + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
    
    
    
    
    
    
    
    
