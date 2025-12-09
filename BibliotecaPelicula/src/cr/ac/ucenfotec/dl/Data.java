package cr.ac.ucenfotec.dl;

import cr.ac.ucenfotec.bl.entities.Administrador;
import cr.ac.ucenfotec.bl.entities.Actor;
import cr.ac.ucenfotec.bl.entities.Cuenta;
import cr.ac.ucenfotec.bl.entities.Director;
import cr.ac.ucenfotec.bl.entities.Pelicula;
import cr.ac.ucenfotec.bl.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa de acceso a datos (Data Layer) del sistema.
 *
 * Esta clase simula la persistencia de información en memoria,
 * utilizando listas para almacenar películas, usuarios, administradores,
 * directores, actores y cuentas.
 *
 * En el portafolio se puede justificar como una implementación sencilla
 * del patrón DAO (Data Access Object) sin base de datos, solo con estructuras
 * en memoria.
 */
public class Data {

    private List<Pelicula> peliculas;
    private List<User> usuarios;
    private List<Administrador> administradores;
    private List<Director> directores;
    private List<Actor> actores;
    private List<Cuenta> cuentas;

    /**
     * Constructor por defecto.
     * Inicializa todas las listas como colecciones vacías.
     */
    public Data() {
        this.peliculas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.directores = new ArrayList<>();
        this.actores = new ArrayList<>();
        this.cuentas = new ArrayList<>();
    }

    // ===== PELÍCULAS =====

    /**
     * Devuelve la lista completa de películas.
     *
     * @return lista de películas
     */
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    /**
     * Agrega una nueva película si no existe otra con el mismo id.
     *
     * @param p película a agregar
     * @return true si se agregó, false si ya existía una con el mismo id o si p es null
     */
    public boolean agregarPelicula(Pelicula p) {
        if (p == null || p.getId() == null) return false;
        if (buscarPeliculaPorId(p.getId()) != null) return false;
        return peliculas.add(p);
    }

    /**
     * Busca una película por su id.
     *
     * @param id identificador de la película
     * @return la película encontrada o null si no existe
     */
    public Pelicula buscarPeliculaPorId(String id) {
        if (id == null) return null;
        for (Pelicula p : peliculas) {
            if (p.getId() != null && p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Elimina una película según su id.
     *
     * @param id identificador de la película
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminarPelicula(String id) {
        Pelicula p = buscarPeliculaPorId(id);
        if (p == null) return false;
        return peliculas.remove(p);
    }

    // ===== USUARIOS =====

    /**
     * Devuelve la lista completa de usuarios.
     *
     * @return lista de usuarios
     */
    public List<User> getUsuarios() {
        return usuarios;
    }

    /**
     * Agrega un nuevo usuario si el id no está repetido.
     * También lo agrega a la lista polimórfica de cuentas.
     *
     * @param u usuario a agregar
     * @return true si se agregó, false si ya existía un usuario con el mismo id o si u es null
     */
    public boolean agregarUsuario(User u) {
        if (u == null || u.getId() == null) return false;
        if (buscarUsuarioPorId(u.getId()) != null) return false;

        boolean agregado = usuarios.add(u);
        if (agregado) {
            cuentas.add(u); // User es una Cuenta (polimorfismo)
        }
        return agregado;
    }

    /**
     * Busca un usuario por su id.
     *
     * @param id identificador del usuario
     * @return el usuario encontrado o null si no existe
     */
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

    /**
     * Devuelve la lista completa de administradores.
     *
     * @return lista de administradores
     */
    public List<Administrador> getAdministradores() {
        return administradores;
    }

    /**
     * Agrega un nuevo administrador si el id no está repetido.
     * También lo agrega a la lista polimórfica de cuentas.
     *
     * @param a administrador a agregar
     * @return true si se agregó, false si ya existía un admin con ese id o si a es null
     */
    public boolean agregarAdmin(Administrador a) {
        if (a == null || a.getId() == null) return false;
        if (buscarAdminPorId(a.getId()) != null) return false;

        boolean agregado = administradores.add(a);
        if (agregado) {
            cuentas.add(a); // Administrador es una Cuenta (polimorfismo)
        }
        return agregado;
    }

    /**
     * Busca un administrador por su id.
     *
     * @param id identificador del administrador
     * @return el administrador encontrado o null si no existe
     */
    public Administrador buscarAdminPorId(String id) {
        if (id == null) return null;
        for (Administrador a : administradores) {
            if (a.getId() != null && a.getId().equalsIgnoreCase(id)) {
                return a;
            }
        }
        return null;
    }

    // ===== DIRECTORES =====

    /**
     * Devuelve la lista completa de directores.
     *
     * @return lista de directores
     */
    public List<Director> getDirectores() {
        return directores;
    }

    /**
     * Agrega un nuevo director si el id no está repetido.
     *
     * @param d director a agregar
     * @return true si se agregó, false si ya existía un director con ese id o si d es null
     */
    public boolean agregarDirector(Director d) {
        if (d == null || d.getId() == null) return false;
        if (buscarDirectorPorId(d.getId()) != null) return false;
        return directores.add(d);
    }

    /**
     * Busca un director por su id.
     *
     * @param id identificador del director
     * @return el director encontrado o null si no existe
     */
    public Director buscarDirectorPorId(String id) {
        if (id == null) return null;
        for (Director d : directores) {
            if (d.getId() != null && d.getId().equalsIgnoreCase(id)) {
                return d;
            }
        }
        return null;
    }

    // ===== ACTORES =====

    /**
     * Devuelve la lista completa de actores.
     *
     * @return lista de actores
     */
    public List<Actor> getActores() {
        return actores;
    }

    /**
     * Agrega un nuevo actor si el id no está repetido.
     *
     * @param a actor a agregar
     * @return true si se agregó, false si ya existía un actor con ese id o si a es null
     */
    public boolean agregarActor(Actor a) {
        if (a == null || a.getId() == null) return false;
        if (buscarActorPorId(a.getId()) != null) return false;
        return actores.add(a);
    }

    /**
     * Busca un actor por su id.
     *
     * @param id identificador del actor
     * @return el actor encontrado o null si no existe
     */
    public Actor buscarActorPorId(String id) {
        if (id == null) return null;
        for (Actor a : actores) {
            if (a.getId() != null && a.getId().equalsIgnoreCase(id)) {
                return a;
            }
        }
        return null;
    }

    // ===== CUENTAS =====

    /**
     * Devuelve la lista polimórfica de cuentas.
     * Aquí conviven tanto usuarios como administradores
     * gracias a que ambos heredan de {@link Cuenta}.
     *
     * @return lista de cuentas
     */
    public List<Cuenta> getCuentas() {
        return cuentas;
    }
}
