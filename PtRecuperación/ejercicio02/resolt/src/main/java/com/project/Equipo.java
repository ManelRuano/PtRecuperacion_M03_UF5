package main.java.com.project;

public class Equipo {
    private int equipoId;
    private String nombre;
    private String pais;

    public Equipo(int equipoId, String nombre, String pais) {
        this.equipoId = equipoId;
        this.nombre = nombre;
        this.pais = pais;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPais() {
        return pais;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "equipoId=" + equipoId +
                ", nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
