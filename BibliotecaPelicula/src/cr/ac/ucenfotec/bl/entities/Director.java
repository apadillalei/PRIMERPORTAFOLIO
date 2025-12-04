package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa a un director de cine dentro del sistema.
 * Mantiene una lista de películas que ha dirigido.
 */
public class Director {

    private String id;
    private String nombre;
    private ArrayList<Pelicula> peliculasDirigidas;

    /**
     * Constructor por defecto.
     * Inicializa la lista de películas dirigidas vacía.
     */
    public Director() {
        this.peliculasDirigidas = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param id                identificador único del director
     * @param nombre            nombre del director
     * @param peliculasDirigidas lista de películas dirigidas; si es null, se inicializa vacía
     */
    public Director(String id, String nombre, ArrayList<Pelicula> peliculasDirigidas) {
        this.id = id;
        this.nombre = nombre;
        this.peliculasDirigidas = (peliculasDirigidas != null)
                ? peliculasDirigidas
                : new ArrayList<>();
    }

    // GETTERS & SETTERS

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public ArrayList<Pelicula> getPeliculasDirigidas() { return peliculasDirigidas; }

    /**
     * Asigna la lista de películas dirigidas.
     * Si se recibe null, se reemplaza por una lista vacía.
     *
     * @param peliculasDirigidas películas asociadas al director
     */
    public void setPeliculasDirigidas(ArrayList<Pelicula> peliculasDirigidas) {
        this.peliculasDirigidas = (peliculasDirigidas != null)
                ? peliculasDirigidas
                : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Director{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", cantidadPeliculas=" + peliculasDirigidas.size() +
                '}';
    }

    /**
     * Dos directores se consideran iguales si comparten el mismo id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Director)) return false;
        Director director = (Director) o;
        return Objects.equals(id, director.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
