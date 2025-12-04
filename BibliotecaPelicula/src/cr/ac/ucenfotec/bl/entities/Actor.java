package cr.ac.ucenfotec.bl.entities;

import java.util.Set;

public class Actor {
    private String id;
    private String nombre;
    private Set<Pelicula> filmografia;

    public Actor(String id, String nombre, Set<Pelicula> filmografia) {
        this.id = id;
        this.nombre = nombre;
        this.filmografia = filmografia;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<Pelicula> getFilmografia() {
        return filmografia;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFilmografia(Set<Pelicula> filmografia) {
        this.filmografia = filmografia;
    }
}
