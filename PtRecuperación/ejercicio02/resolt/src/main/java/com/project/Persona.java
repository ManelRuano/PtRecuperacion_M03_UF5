package main.java.com.project;

public abstract class Persona {
    protected int id;
    protected String nombre;

    public Persona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public abstract String descripccion();

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
