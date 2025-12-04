package cr.ac.ucenfotec.bl.entities;

public class Genero {
    private String nombre; // Acción, Drama, Comedia, Terror, Infantil...

    // Constructor por defecto
    public Genero() {
    }

    // Constructor con parámetro
    public Genero(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
