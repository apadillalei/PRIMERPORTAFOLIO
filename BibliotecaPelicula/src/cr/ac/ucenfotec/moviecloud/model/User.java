package cr.ac.ucenfotec.moviecloud.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private String id;
    private String username;
    private String email;
    private Set<Pelicula> favoritos;

    // ------ CONSTRUCTOR COMPLETO ------
    public User(String id, String username, String email, Set<Pelicula> favoritos) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.favoritos = (favoritos != null) ? favoritos : new LinkedHashSet<>();
    }

    // ------ CONSTRUCTOR ------
    public User(String id, String username, String email) {
        this(id, username, email, new LinkedHashSet<>());
    }

    // ------ MÉTODOS DE COMPORTAMIENTO ------
    public boolean agregarFavorito(Pelicula pelicula) {
        return pelicula != null && favoritos.add(pelicula);
    }

    public boolean eliminarFavorito(Pelicula pelicula) {
        return pelicula != null && favoritos.remove(pelicula);
    }


    public boolean esFavorito(Pelicula pelicula) {
        return pelicula != null && favoritos.contains(pelicula);
    }

    public void limpiarFavoritos() {
        favoritos.clear();
    }

    // ------ GETTERS & SETTERS ------
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<Pelicula> getFavoritos() { return favoritos; }
    public void setFavoritos(Set<Pelicula> favoritos) {
        this.favoritos = (favoritos != null) ? favoritos : new LinkedHashSet<>();
    }

    // ------ MÉTODOS  ------
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", favoritos=" + favoritos.size() +
                '}';
    }

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
