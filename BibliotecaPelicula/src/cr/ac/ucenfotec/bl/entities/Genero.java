package cr.ac.ucenfotec.bl.entities;

import java.util.Objects;

/**
 * Representa un género cinematográfico, por ejemplo:
 * Acción, Drama, Comedia, Terror, Infantil, etc.
 */
public class Genero {

    private String nombre;

    /**
     * Constructor por defecto requerido por el portafolio.
     */
    public Genero() {
    }

    /**
     * Constructor que recibe el nombre del género.
     *
     * @param nombre nombre del género (ej: "Acción", "Drama")
     */
    public Genero(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return "Genero{" +
                "nombre='" + nombre + '\'' +
                '}';
    }

    /**
     * Dos géneros se consideran iguales si tienen el mismo nombre.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genero)) return false;
        Genero genero = (Genero) o;
        return Objects.equals(nombre, genero.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
