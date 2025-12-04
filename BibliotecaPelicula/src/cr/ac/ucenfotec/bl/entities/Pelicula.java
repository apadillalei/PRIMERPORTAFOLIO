package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;

public class Pelicula {
    private String id;
    private String titulo;
    private int anio;
    private int duracionMinutos;
    private String clasificacion;

    private ArrayList<Genero> generos;
    private ArrayList<Actor> elenco;
    private Director director;

    public record Ficha(String sinopsis) {}
    private Ficha ficha;

    // Constructor por defecto
    public Pelicula() {
        this.generos = new ArrayList<>();
        this.elenco = new ArrayList<>();
    }

    public Pelicula(String id, String titulo, int anio, int duracionMinutos, String clasificacion,
                    ArrayList<Genero> generos, ArrayList<Actor> elenco, Director director) {

        this.id = id;
        this.titulo = titulo;
        this.anio = anio;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;

        this.generos = (generos != null) ? generos : new ArrayList<>();
        this.elenco = (elenco != null) ? elenco : new ArrayList<>();
        this.director = director;
    }

    public Pelicula(String id, String titulo, int anio, int duracionMinutos, String clasificacion) {
        this(id, titulo, anio, duracionMinutos, clasificacion, new ArrayList<>(), new ArrayList<>(), null);
    }

    // ===== GETTERS & SETTERS =====
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }

    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }

    public ArrayList<Genero> getGeneros() { return generos; }
    public void setGeneros(ArrayList<Genero> generos) {
        this.generos = (generos != null) ? generos : new ArrayList<>();
    }

    public ArrayList<Actor> getElenco() { return elenco; }
    public void setElenco(ArrayList<Actor> elenco) {
        this.elenco = (elenco != null) ? elenco : new ArrayList<>();
    }

    public Director getDirector() { return director; }
    public void setDirector(Director director) { this.director = director; }

    public Ficha getFicha() { return ficha; }
    public void setFicha(Ficha ficha) { this.ficha = ficha; }
}
