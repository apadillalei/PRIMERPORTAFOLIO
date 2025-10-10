package cr.ac.ucenfotec.moviecloud.model;

public class Genero {
    private String nombre; /*Accion, Drama, Comedia, Terror, Infantil...*/

    public Genero(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
