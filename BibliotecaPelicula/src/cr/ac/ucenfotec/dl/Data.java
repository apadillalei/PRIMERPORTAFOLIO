package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.Administrador;
import cr.ac.ucenfotec.bl.entities.Pelicula;
import cr.ac.ucenfotec.bl.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private final List<Pelicula> peliculas;
    private final List<User> usuarios;
    private final List<Administrador> administradores;

    public Data() {
        this.peliculas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.administradores = new ArrayList<>();
    }

    // ===== PELÍCULAS =====
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public boolean agregarPelicula(Pelicula p) {
        if (buscarPeliculaPorId(p.getId()) != null) return false;
        return peliculas.add(p);
    }

    public Pelicula buscarPeliculaPorId(String id) {
        if (id == null) return null;
        for (Pelicula p : peliculas) {
            if (p.getId() != null && p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean eliminarPelicula(String id) {
        Pelicula p = buscarPeliculaPorId(id);
        if (p == null) return false;
        return peliculas.remove(p);
    }

    // ===== USUARIOS =====
    public List<User> getUsuarios() {
        return usuarios;
    }

    public boolean agregarUsuario(User u) {
        if (buscarUsuarioPorId(u.getId()) != null) return false;
        return usuarios.add(u);
    }

    public User buscarUsuarioPorId(String id) {
        if (id == null) return null;
        for (User u : usuarios) {
            if (u.getId() != null && u.getId().equalsIgnoreCase(id)) {
                return u;
            }
        }
        return null;
    }

    // ===== ADMINISTRADORES =====
    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public boolean agregarAdmin(Administrador a) {
        if (buscarAdminPorId(a.getId()) != null) return false;
        return administradores.add(a);
    }

    public Administrador buscarAdminPorId(String id) {
        if (id == null) return null;
        for (Administrador a : administradores) {
            if (a.getId() != null && a.getId().equalsIgnoreCase(id)) {
                return a;
            }
        }
        return null;
    }
}
