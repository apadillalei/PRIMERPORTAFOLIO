package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;

public class Actor {
    private String id;
    private String nombre;
    private ArrayList<Pelicula> filmografia;

    // Constructor por defecto
    public Actor() {
        this.filmografia = new ArrayList<>();
    }

    // Constructor completo
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
    public void setFilmografia(ArrayList<Pelicula> filmografia) {
        this.filmografia = (filmografia != null)
                ? filmografia
                : new ArrayList<>();
    }
}
