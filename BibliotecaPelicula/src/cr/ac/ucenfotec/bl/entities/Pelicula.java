package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa una película dentro del catálogo.
 * Incluye información básica como título, año, duración,
 * clasificación, géneros, elenco, director y una ficha con sinopsis.
 */
public class Pelicula {

    private String id;
    private String titulo;
    private int anio;
    private int duracionMinutos;
    private String clasificacion;

    private ArrayList<Genero> generos;
    private ArrayList<Actor> elenco;
    private Director director;

    /**
     * Ficha técnica resumida de la película.
     * Se implementa como un {@code record} inmutable.
     */
    public record Ficha(String sinopsis) {}

    private Ficha ficha;

    /**
     * Constructor por defecto.
     * Inicializa las listas de géneros y elenco vacías.
     */
    public Pelicula() {
        this.generos = new ArrayList<>();
        this.elenco = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param id              identificador único de la película
     * @param titulo          título de la película
     * @param anio            año de estreno
     * @param duracionMinutos duración en minutos
     * @param clasificacion   clasificación por edad
     * @param generos         lista de géneros; si es null, se inicializa vacía
     * @param elenco          lista de actores; si es null, se inicializa vacía
     * @param director        director asociado a la película (puede ser null)
     */
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

    /**
     * Constructor simplificado que solo recibe los datos básicos.
     * Inicializa las listas de géneros y elenco vacías
     * y deja el director en null.
     */
    public Pelicula(String id, String titulo, int anio, int duracionMinutos, String clasificacion) {
        this(id, titulo, anio, duracionMinutos, clasificacion,
                new ArrayList<>(), new ArrayList<>(), null);
    }

    // GETTERS & SETTERS
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

    /**
     * Asigna la lista de géneros.
     * Si se recibe null, se reemplaza por una lista vacía.
     */
    public void setGeneros(ArrayList<Genero> generos) {
        this.generos = (generos != null) ? generos : new ArrayList<>();
    }

    public ArrayList<Actor> getElenco() { return elenco; }

    /**
     * Asigna la lista de actores (elenco).
     * Si se recibe null, se reemplaza por una lista vacía.
     */
    public void setElenco(ArrayList<Actor> elenco) {
        this.elenco = (elenco != null) ? elenco : new ArrayList<>();
    }

    public Director getDirector() { return director; }

    public void setDirector(Director director) { this.director = director; }

    public Ficha getFicha() { return ficha; }

    public void setFicha(Ficha ficha) { this.ficha = ficha; }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anio +
                ", duracionMinutos=" + duracionMinutos +
                ", clasificacion='" + clasificacion + '\'' +
                '}';
    }

    /**
     * Dos películas se consideran iguales si comparten el mismo id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pelicula)) return false;
        Pelicula pelicula = (Pelicula) o;
        return Objects.equals(id, pelicula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
