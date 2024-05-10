package main.java.com.project;

public class Jugador extends Persona {
    private int edad;
    private String posicion;
    private int equipoId;

    public Jugador(int id, String nombre, int edad, String posicion, int equipoId) {
        super(id, nombre);
        this.edad = edad;
        this.posicion = posicion;
        this.equipoId = equipoId;
    }

    public int getEdad() {
        return edad;
    }

    public String getPosicion() {
        return posicion;
    }

    public int getEquipoId() {
        return equipoId;
    }

    @Override
    public String descripccion() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", posicion='" + posicion + '\'' +
                ", equipoId=" + equipoId +
                '}';
    }

    @Override
    public String toString() {
        return descripccion();
    }
}
