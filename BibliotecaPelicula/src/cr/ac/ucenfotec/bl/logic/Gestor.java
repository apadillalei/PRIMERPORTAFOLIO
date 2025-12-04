package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.*;
import cr.ac.ucenfotec.dl.Data;

import java.util.ArrayList;
import java.util.List;

public class Gestor {

    private final Data data;

    public Gestor() {
        this.data = new Data();
    }

    // Exponer listas
    public List<Pelicula> listarPeliculas() {
        return data.getPeliculas();
    }

    public List<User> listarUsuarios() {
        return data.getUsuarios();
    }

    public List<Administrador> listarAdministradores() {
        return data.getAdministradores();
    }

    // ===== Administradores =====
    public boolean registrarAdmin(String id, String username, String email) {
        Administrador nuevo = new Administrador();
        nuevo.setId(id);
        nuevo.setUsername(username);
        nuevo.setEmail(email);
        return data.agregarAdmin(nuevo);
    }

    public Administrador loginAdmin(String id) {
        return data.buscarAdminPorId(id);
    }

    // ===== Usuarios =====
    public boolean registrarUsuario(String id, String username, String email) {
        User user = new User(id, username, email);
        return data.agregarUsuario(user);
    }

    public User loginUsuario(String id) {
        return data.buscarUsuarioPorId(id);
    }

    // ===== Películas =====
    public boolean crearPelicula(Pelicula p) {
        return data.agregarPelicula(p);
    }

    public Pelicula buscarPeliculaPorId(String id) {
        return data.buscarPeliculaPorId(id);
    }

    public boolean actualizarPelicula(Pelicula p,
                                      String nuevoTitulo,
                                      int nuevoAnio,
                                      int nuevaDuracion,
                                      String nuevaClasificacion) {
        if (p == null) return false;
        p.setTitulo(nuevoTitulo);
        p.setAnio(nuevoAnio);
        p.setDuracionMinutos(nuevaDuracion);
        p.setClasificacion(nuevaClasificacion);
        return true;
    }

    public boolean eliminarPelicula(String id) {
        return data.eliminarPelicula(id);
    }

    // ===== Favoritos Usuario =====
    public boolean agregarFavorito(User user, Pelicula pelicula) {
        if (user == null || pelicula == null) return false;
        return user.agregarFavorito(pelicula);
    }

    public boolean eliminarFavorito(User user, Pelicula pelicula) {
        if (user == null || pelicula == null) return false;
        return user.eliminarFavorito(pelicula);
    }

    public List<Pelicula> listarFavoritos(User user) {
        if (user == null) return new ArrayList<>();
        return new ArrayList<>(user.getFavoritos());
    }
}
