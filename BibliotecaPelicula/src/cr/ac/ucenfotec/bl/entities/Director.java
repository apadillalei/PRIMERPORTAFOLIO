package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;

public class Director {
    private String id;
    private String nombre;
    private ArrayList<Pelicula> peliculasDirigidas;

    // Constructor por defecto
    public Director() {
        this.peliculasDirigidas = new ArrayList<>();
    }

    // Constructor completo
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
    public void setPeliculasDirigidas(ArrayList<Pelicula> peliculasDirigidas) {
        this.peliculasDirigidas = (peliculasDirigidas != null)
                ? peliculasDirigidas
                : new ArrayList<>();
    }
}
