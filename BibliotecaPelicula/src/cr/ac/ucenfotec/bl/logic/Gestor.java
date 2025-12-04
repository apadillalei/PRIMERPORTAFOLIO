package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.*;
import cr.ac.ucenfotec.dl.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de lógica de negocio del sistema.
 *
 * Actúa como intermediario entre la capa de datos ({@link Data}) y la capa de presentación (UI / Controller),
 * aplicando las reglas de negocio sobre películas, usuarios, administradores, directores y actores.
 *
 * Además expone una lista polimórfica de {@link Cuenta} para demostrar herencia y polimorfismo.
 */
public class Gestor {

    private Data data;

    /**
     * Constructor por defecto.
     * Inicializa el contenedor de datos en memoria.
     */
    public Gestor() {
        this.data = new Data();
    }

    // ===== LISTAS DE CONSULTA =====

    /**
     * Devuelve la lista completa de películas registradas.
     *
     * @return lista de películas
     */
    public List<Pelicula> listarPeliculas() {
        return data.getPeliculas();
    }

    /**
     * Devuelve la lista completa de usuarios registrados.
     *
     * @return lista de usuarios
     */
    public List<User> listarUsuarios() {
        return data.getUsuarios();
    }

    /**
     * Devuelve la lista completa de administradores registrados.
     *
     * @return lista de administradores
     */
    public List<Administrador> listarAdministradores() {
        return data.getAdministradores();
    }

    /**
     * Devuelve la lista completa de directores registrados.
     *
     * @return lista de directores
     */
    public List<Director> listarDirectores() {
        return data.getDirectores();
    }

    /**
     * Devuelve la lista completa de actores registrados.
     *
     * @return lista de actores
     */
    public List<Actor> listarActores() {
        return data.getActores();
    }

    /**
     * Devuelve una lista polimórfica con todas las cuentas del sistema
     * (usuarios y administradores) vistas como {@link Cuenta}.
     *
     * Esto ejemplifica polimorfismo: tanto {@link User} como {@link Administrador}
     * comparten la misma superclase.
     *
     * @return lista de cuentas
     */
    public List<Cuenta> listarCuentas() {
        return data.getCuentas();
    }

    // ===== ADMINISTRADORES =====

    /**
     * Registra un nuevo administrador en el sistema.
     *
     * @param id       identificador único del administrador
     * @param username nombre de usuario
     * @param email    correo electrónico
     * @return true si se registró correctamente, false si ya existía un admin con ese id
     */
    public boolean registrarAdmin(String id, String username, String email) {
        Administrador nuevo = new Administrador(id, username, email, new ArrayList<>());
        return data.agregarAdmin(nuevo);
    }

    /**
     * Realiza el "login" de un administrador según su id.
     *
     * @param id identificador del administrador
     * @return el administrador encontrado, o null si no existe
     */
    public Administrador loginAdmin(String id) {
        return data.buscarAdminPorId(id);
    }

    // ===== USUARIOS =====

    /**
     * Registra un nuevo usuario normal en el sistema.
     *
     * @param id       identificador único del usuario
     * @param username nombre de usuario
     * @param email    correo electrónico
     * @return true si se registró correctamente, false si ya existía un usuario con ese id
     */
    public boolean registrarUsuario(String id, String username, String email) {
        User user = new User(id, username, email, new ArrayList<>());
        return data.agregarUsuario(user);
    }

    /**
     * Realiza el "login" de un usuario según su id.
     *
     * @param id identificador del usuario
     * @return el usuario encontrado, o null si no existe
     */
    public User loginUsuario(String id) {
        return data.buscarUsuarioPorId(id);
    }

    // ===== DIRECTORES =====

    /**
     * Registra un nuevo director.
     *
     * @param id     identificador del director
     * @param nombre nombre del director
     * @return true si se registró correctamente, false si ya existía un director con ese id
     */
    public boolean registrarDirector(String id, String nombre) {
        if (data.buscarDirectorPorId(id) != null) return false;
        Director d = new Director(id, nombre, new ArrayList<>());
        return data.agregarDirector(d);
    }

    /**
     * Busca un director por su id.
     *
     * @param id identificador del director
     * @return el director encontrado o null si no existe
     */
    public Director buscarDirectorPorId(String id) {
        return data.buscarDirectorPorId(id);
    }

    /**
     * Asocia una película con un director
     *     La película guarda la referencia al director
     *     El director agrega la película a su lista de películas dirigidas (si no estaba

     *
     * @param peli película a asociar
     * @param dir  director responsable
     * @return true si la asociación fue válida, false si alguno de los parámetros es null
     */
    public boolean asociarPeliculaConDirector(Pelicula peli, Director dir) {
        if (peli == null || dir == null) return false;

        peli.setDirector(dir);

        if (dir.getPeliculasDirigidas() == null) {
            dir.setPeliculasDirigidas(new ArrayList<>());
        }
        if (!dir.getPeliculasDirigidas().contains(peli)) {
            dir.getPeliculasDirigidas().add(peli);
        }
        return true;
    }

    // ===== ACTORES =====

    /**
     * Registra un nuevo actor.
     *
     * @param id     identificador del actor
     * @param nombre nombre del actor
     * @return true si se registró correctamente, false si ya existía un actor con ese id
     */
    public boolean registrarActor(String id, String nombre) {
        if (data.buscarActorPorId(id) != null) return false;
        Actor a = new Actor(id, nombre, new ArrayList<>());
        return data.agregarActor(a);
    }

    /**
     * Busca un actor por su id.
     *
     * @param id identificador del actor
     * @return el actor encontrado o null si no existe
     */
    public Actor buscarActorPorId(String id) {
        return data.buscarActorPorId(id);
    }

    /**
     * Asocia un actor con una película:
     * <ul>
     *     <li>La película agrega el actor a su elenco.</li>
     *     <li>El actor agrega la película a su filmografía.</li>
     * </ul>
     *
     * @param actor    actor a asociar
     * @param pelicula película en la que participa
     * @return true si la asociación fue correcta, false si algún parámetro es null
     */
    public boolean asociarActorConPelicula(Actor actor, Pelicula pelicula) {
        if (actor == null || pelicula == null) return false;

        if (pelicula.getElenco() == null) {
            pelicula.setElenco(new ArrayList<>());
        }
        if (!pelicula.getElenco().contains(actor)) {
            pelicula.getElenco().add(actor);
        }

        if (actor.getFilmografia() == null) {
            actor.setFilmografia(new ArrayList<>());
        }
        if (!actor.getFilmografia().contains(pelicula)) {
            actor.getFilmografia().add(pelicula);
        }

        return true;
    }

    // ===== PELÍCULAS =====

    /**
     * Registra una nueva película en el sistema.
     *
     * @param p película a agregar
     * @return true si se agregó correctamente, false si ya existía una película con ese id
     */
    public boolean crearPelicula(Pelicula p) {
        return data.agregarPelicula(p);
    }

    /**
     * Busca una película por su id.
     *
     * @param id identificador de la película
     * @return la película encontrada o null si no existe
     */
    public Pelicula buscarPeliculaPorId(String id) {
        return data.buscarPeliculaPorId(id);
    }

    /**
     * Actualiza los datos básicos de una película.
     *
     * @param p                 película a actualizar
     * @param nuevoTitulo       nuevo título
     * @param nuevoAnio         nuevo año
     * @param nuevaDuracion     nueva duración en minutos
     * @param nuevaClasificacion nueva clasificación
     * @return true si se actualizó, false si la película era null
     */
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

    /**
     * Elimina una película por su id.
     *
     * @param id identificador de la película
     * @return true si se eliminó, false si no se encontró
     */
    public boolean eliminarPelicula(String id) {
        return data.eliminarPelicula(id);
    }

    // ===== FAVORITOS DE USUARIO =====

    /**
     * Agrega una película a la lista de favoritas de un usuario.
     *
     * @param user     usuario que marca la película como favorita
     * @param pelicula película a agregar
     * @return true si se agregó, false si ya estaba o si algún parámetro es null
     */
    public boolean agregarFavorito(User user, Pelicula pelicula) {
        if (user == null || pelicula == null) return false;
        return user.agregarFavorito(pelicula);
    }

    /**
     * Elimina una película de la lista de favoritas de un usuario.
     *
     * @param user     usuario dueño de la lista
     * @param pelicula película a eliminar
     * @return true si se eliminó, false si no estaba o si algún parámetro es null
     */
    public boolean eliminarFavorito(User user, Pelicula pelicula) {
        if (user == null || pelicula == null) return false;
        return user.eliminarFavorito(pelicula);
    }

    /**
     * Devuelve una copia de la lista de películas favoritas de un usuario.
     *
     * @param user usuario a consultar
     * @return lista de películas favoritas; lista vacía si user es null
     */
    public List<Pelicula> listarFavoritos(User user) {
        if (user == null) return new ArrayList<>();
        return new ArrayList<>(user.getFavoritos());
    }
}
