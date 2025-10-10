package cr.ac.ucenfotec.moviecloud.model;

import java.util.Set;

public class Director {
    private String id;
    private String nombre;
    private Set<Pelicula> peliculasDirigidas;

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Set<Pelicula> getPeliculasDirigidas() {
        return peliculasDirigidas;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPeliculasDirigidas(Set<Pelicula> peliculasDirigidas) {
        this.peliculasDirigidas = peliculasDirigidas;
    }
}
