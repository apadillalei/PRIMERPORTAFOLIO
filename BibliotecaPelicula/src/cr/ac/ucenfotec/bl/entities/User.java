package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Representa a un usuario normal del sistema.
 * Hereda de {@link Cuenta} y mantiene una lista de películas favoritas.
 */
public class User extends Cuenta {

    private ArrayList<Pelicula> favoritos;

    /**
     * Constructor por defecto.
     * Inicializa la lista de favoritos vacía.
     */
    public User() {
        super();
        this.favoritos = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param id        identificador único de la cuenta
     * @param username  nombre de usuario
     * @param email     correo electrónico
     * @param favoritos lista de películas favoritas; si es null, se inicializa vacía
     */
    public User(String id, String username, String email, ArrayList<Pelicula> favoritos) {
        super(id, username, email);
        this.favoritos = (favoritos != null) ? favoritos : new ArrayList<>();
    }

    /**
     * Constructor simplificado que parte de una lista de favoritos vacía.
     */
    public User(String id, String username, String email) {
        this(id, username, email, new ArrayList<>());
    }

    /**
     * Agrega una película a la lista de favoritos del usuario
     * si aún no ha sido agregada.
     *
     * @param p película a marcar como favorita
     * @return true si se agregó, false si ya estaba o si p es null
     */
    public boolean agregarFavorito(Pelicula p) {
        if (p == null) return false;
        if (!favoritos.contains(p)) return favoritos.add(p);
        return false;
    }

    /**
     * Elimina una película de la lista de favoritas.
     *
     * @param p película a eliminar de favoritos
     * @return true si se eliminó, false si no estaba en la lista
     */
    public boolean eliminarFavorito(Pelicula p) {
        return favoritos.remove(p);
    }

    public ArrayList<Pelicula> getFavoritos() {
        return favoritos;
    }

    /**
     * Asigna la lista de favoritas.
     * Si se recibe null, se reemplaza por una lista vacía.
     */
    public void setFavoritos(ArrayList<Pelicula> favoritos) {
        this.favoritos = (favoritos != null) ? favoritos : new ArrayList<>();
    }

    @Override
    public String getTipoCuenta() {
        return "Usuario";
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", favoritos=" + favoritos.size() +
                '}';
    }

    /**
     * Dos usuarios se consideran iguales si comparten el mismo id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
