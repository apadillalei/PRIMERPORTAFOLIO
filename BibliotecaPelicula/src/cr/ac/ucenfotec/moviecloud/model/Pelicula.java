package cr.ac.ucenfotec.moviecloud.model;

import java.util.Set;

public class Pelicula {
    private String id;
    private String titulo;
    private int anio;
    private int duracionMinutos;
    private String clasificacion;
    private Set<Genero> generos;
    private Set<Actor> elenco;
    private Director director;

    // ------ CONSTRUCTORES ------
    public Pelicula(String id, String titulo, int anio, int duracionMinutos, String clasificacion,
                    Set<Genero> generos, Set<Actor> elenco, Director director) {
        this.id = id;
        this.titulo = titulo;
        this.anio = anio;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.generos = generos;
        this.elenco = elenco;
        this.director = director;
    }

    public Pelicula(String id, String titulo, int anio, int duracionMinutos, String clasificacion) {
        this(id, titulo, anio, duracionMinutos, clasificacion, null, null, null);
    }

    // ------ MÉTODOS ------
    public void actualizarPelicula(String nuevoTitulo,
                                   int nuevoAnio,
                                   int nuevaDuracion,
                                   String nuevaClasificacion) {
        this.titulo = nuevoTitulo;
        this.anio = nuevoAnio;
        this.duracionMinutos = nuevaDuracion;
        this.clasificacion = nuevaClasificacion;
    }

    public void eliminarPelicula() {
        this.id = null;
        this.titulo = null;
        this.anio = 0;
        this.duracionMinutos = 0;
        this.clasificacion = null;
        this.generos = null;
        this.elenco = null;
        this.director = null;
    }

    public void mostrarInfo() {
        System.out.println("Título: " + titulo +
                ", Año: " + anio +
                ", Duración: " + duracionMinutos + " min" +
                ", Clasificación: " + clasificacion);
    }

    public record Ficha(String sinopsis) {}

    private Ficha ficha;

    // ------ GETTERS & SETTERS ------
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnio() {
        return anio;
    }
    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getClasificacion() {
        return clasificacion;
    }
    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public Set<Genero> getGeneros() {
        return generos;
    }
    public void setGeneros(Set<Genero> generos) {
        this.generos = generos;
    }

    public Set<Actor> getElenco() {
        return elenco;
    }
    public void setElenco(Set<Actor> elenco) {
        this.elenco = elenco;
    }

    public Director getDirector() {
        return director;
    }
    public void setDirector(Director director) {
        this.director = director;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }


}
