package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa a un actor dentro del sistema.
 * Cada actor tiene un identificador, un nombre
 * y una lista de películas en las que ha participado (filmografía).
 */
public class Actor {

    private String id;
    private String nombre;
    private ArrayList<Pelicula> filmografia;

    /**
     * Constructor por defecto.
     * Inicializa la filmografía como una lista vacía.
     */
    public Actor() {
        this.filmografia = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param id          identificador único del actor
     * @param nombre      nombre del actor
     * @param filmografia lista de películas en las que ha participado;
     *                    si es null, se inicializa como lista vacía
     */
    public Actor(String id, String nombre, ArrayList<Pelicula> filmografia) {
        this.id = id;
        this.nombre = nombre;
        this.filmografia = (filmografia != null)
                ? filmografia
                : new ArrayList<>();
    }

    // GETTERS & SETTERS

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public ArrayList<Pelicula> getFilmografia() { return filmografia; }

    /**
     * Asigna la filmografía del actor.
     * Si se recibe null, se reemplaza por una lista vacía para evitar NullPointerException.
     *
     * @param filmografia lista de películas en las que participa el actor
     */
    public void setFilmografia(ArrayList<Pelicula> filmografia) {
        this.filmografia = (filmografia != null)
                ? filmografia
                : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cantidadPeliculas=" + filmografia.size() +
                '}';
    }

    /**
     * Dos actores se consideran iguales si tienen el mismo id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actor)) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
