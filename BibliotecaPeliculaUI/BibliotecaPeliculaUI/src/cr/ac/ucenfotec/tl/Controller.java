package cr.ac.ucenfotec.tl;

import cr.ac.ucenfotec.bl.entities.*;
import cr.ac.ucenfotec.bl.logic.Gestor;
import cr.ac.ucenfotec.ui.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador principal de la aplicación.
 *
 * Coordina la interacción entre la interfaz de usuario ({@link UI})
 * y la lógica de negocio ({@link Gestor}), manejando:
 * <ul>
 *     <li>Menú de invitado (sin sesión).</li>
 *     <li>Menú de administrador.</li>
 *     <li>Menú de usuario normal.</li>
 * </ul>
 *
 * También gestiona el estado de la sesión (sin sesión, admin, usuario)
 * utilizando el enum {@link EstadoSesion}.
 */
public class Controller {

    /**
     * Representa el estado actual de la sesión en ejecución.
     * SIN_SESION: nadie ha iniciado sesión.
     * ADMIN: sesión iniciada como administrador.
     * USUARIO: sesión iniciada como usuario normal.
     */
    private enum EstadoSesion {
        SIN_SESION,
        ADMIN,
        USUARIO
    }

    private final UI ui = new UI();
    private final Gestor gestor = new Gestor();

    private EstadoSesion estadoSesion = EstadoSesion.SIN_SESION;
    private Administrador adminActual;
    private User usuarioActual;

    /**
     * Método de arranque de la aplicación.
     * Muestra los menús según el estado de la sesión hasta que el usuario decida salir.
     *
     * @throws IOException si ocurre un error al leer desde consola
     */
    public void start() throws IOException {
        boolean salir = false;

        while (!salir) {
            int opcion;

            switch (estadoSesion) {
                case SIN_SESION -> {
                    ui.mostrarMenuInvitado();
                    opcion = ui.leerOpcion();
                    salir = procesarOpcionInvitado(opcion);
                }
                case ADMIN -> {
                    ui.mostrarMenuAdmin(adminActual.getUsername());
                    opcion = ui.leerOpcion();
                    procesarOpcionAdmin(opcion);
                }
                case USUARIO -> {
                    ui.mostrarMenuUsuario(usuarioActual.getUsername());
                    opcion = ui.leerOpcion();
                    procesarOpcionUsuario(opcion);
                }
            }
        }

        ui.mostrarMensaje("Programa finalizado.");
    }

    // ====== MENÚ INVITADO (sin sesión) ======

    /**
     * Procesa la opción seleccionada en el menú de invitado.
     *
     * @param opcion opción elegida por el usuario
     * @return true si se debe terminar el programa, false si se continúa
     * @throws IOException si ocurre un error de lectura
     */
    private boolean procesarOpcionInvitado(int opcion) throws IOException {
        switch (opcion) {
            case 1 -> registrarAdmin();
            case 2 -> registrarUsuario();
            case 3 -> iniciarSesionAdmin();
            case 4 -> iniciarSesionUsuario();
            case 5 -> verCuentas();
            case 0 -> {
                return true; // salir del programa definitivamente
            }
            default -> ui.mostrarMensaje("Opción inválida.");
        }
        return false;
    }

    /**
     * Registra un nuevo administrador solicitando los datos por consola.
     *
     * @throws IOException si ocurre un error al leer la entrada
     */
    private void registrarAdmin() throws IOException {
        ui.mostrarMensaje("\n=== Registrar administrador ===");
        String id = ui.leerId("ID admin:");
        String username = ui.leerTextoObligatorio("Nombre de usuario admin:");
        String email = ui.leerEmail("Email admin:");

        boolean ok = gestor.registrarAdmin(id, username, email);
        if (ok) ui.mostrarMensaje("Administrador registrado correctamente.");
        else ui.mostrarMensaje("Ya existe un administrador con ese ID.");
    }

    /**
     * Registra un nuevo usuario normal solicitando los datos por consola.
     *
     * @throws IOException si ocurre un error al leer la entrada
     */
    private void registrarUsuario() throws IOException {
        ui.mostrarMensaje("\n=== Registrar usuario ===");
        String id = ui.leerId("ID usuario:");
        String username = ui.leerTextoObligatorio("Nombre de usuario:");
        String email = ui.leerEmail("Email usuario:");

        boolean ok = gestor.registrarUsuario(id, username, email);
        if (ok) ui.mostrarMensaje("Usuario registrado correctamente.");
        else ui.mostrarMensaje("Ya existe un usuario con ese ID.");
    }

    /**
     * Inicia sesión como administrador a partir del id.
     *
     * @throws IOException si ocurre un error al leer la entrada
     */
    private void iniciarSesionAdmin() throws IOException {
        ui.mostrarMensaje("\n=== Iniciar sesión (ADMIN) ===");
        String id = ui.leerId("ID admin:");
        Administrador a = gestor.loginAdmin(id);
        if (a == null) {
            ui.mostrarMensaje("Administrador no encontrado.");
        } else {
            adminActual = a;
            usuarioActual = null;
            estadoSesion = EstadoSesion.ADMIN;
            ui.mostrarMensaje("Sesión iniciada como administrador: " + adminActual.getUsername());
        }
    }

    /**
     * Inicia sesión como usuario normal a partir del id.
     *
     * @throws IOException si ocurre un error al leer la entrada
     */
    private void iniciarSesionUsuario() throws IOException {
        ui.mostrarMensaje("\n=== Iniciar sesión (USUARIO) ===");
        String id = ui.leerId("ID usuario:");
        User u = gestor.loginUsuario(id);
        if (u == null) {
            ui.mostrarMensaje("Usuario no encontrado.");
        } else {
            usuarioActual = u;
            adminActual = null;
            estadoSesion = EstadoSesion.USUARIO;
            ui.mostrarMensaje("Sesión iniciada como usuario: " + usuarioActual.getUsername());
        }
    }

    /**
     * Cierra la sesión actual (si la hay) y vuelve al modo invitado.
     */
    private void cerrarSesion() {
        adminActual = null;
        usuarioActual = null;
        estadoSesion = EstadoSesion.SIN_SESION;
        ui.mostrarMensaje("Sesión cerrada. Volviendo al modo invitado.");
    }

    // ====== MENÚ ADMIN ======

    /**
     * Procesa las opciones del menú de administrador.
     *
     * @param opcion opción elegida
     * @throws IOException si ocurre un error de lectura
     */
    private void procesarOpcionAdmin(int opcion) throws IOException {
        switch (opcion) {
            case 1 -> crearPelicula();
            case 2 -> ui.mostrarPeliculas(gestor.listarPeliculas());
            case 3 -> buscarPelicula();
            case 4 -> actualizarPelicula();
            case 5 -> eliminarPelicula();
            case 6 -> registrarDirector();
            case 7 -> listarDirectores();
            case 8 -> registrarActor();
            case 9 -> listarActores();
            case 10 -> asociarActorPelicula();
            case 0 -> cerrarSesion();
            default -> ui.mostrarMensaje("Opción inválida.");
        }
    }

    /**
     * Crea una película leyendo datos desde consola.
     * Permite agregar sinopsis y asociar un director (existente o nuevo).
     *
     * @throws IOException si ocurre un error de lectura
     */
    private void crearPelicula() throws IOException {
        ui.mostrarMensaje("\n=== Crear película ===");
        String id = ui.leerId("ID película:");
        String titulo = ui.leerTextoObligatorio("Título:");
        int anio = ui.leerEntero("Año (>= 1888):");
        int duracion = ui.leerEntero("Duración en minutos:");
        String clasificacion = ui.leerClasificacion();

        List<Genero> generos = ui.leerGeneros();

        Pelicula p = new Pelicula();
        p.setId(id);
        p.setTitulo(titulo);
        p.setAnio(anio);
        p.setDuracionMinutos(duracion);
        p.setClasificacion(clasificacion);
        p.setGeneros(new ArrayList<>(generos));

        String desea = ui.leerTextoObligatorio("¿Desea agregar sinopsis? (si/no):");
        if (desea.equalsIgnoreCase("si") || desea.equalsIgnoreCase("sí")) {
            String sinopsis = ui.leerTextoObligatorio("Sinopsis:");
            p.setFicha(new Pelicula.Ficha(sinopsis));
        }

        String respDir = ui.leerTextoObligatorio("¿Desea asociar un director? (si/no):");
        if (respDir.equalsIgnoreCase("si") || respDir.equalsIgnoreCase("sí")) {
            seleccionarDirectorParaPelicula(p);
        }

        boolean ok = gestor.crearPelicula(p);
        if (ok) {
            ui.mostrarMensaje("Película creada correctamente.");
        } else {
            ui.mostrarMensaje("Ya existe una película con ese ID.");
        }
    }

    /**
     * Busca una película por su id y muestra el detalle.
     *
     * @throws IOException si ocurre un error de lectura
     */
    private void buscarPelicula() throws IOException {
        ui.mostrarMensaje("\n=== Buscar película ===");
        String id = ui.leerId("ID de la película:");
        Pelicula p = gestor.buscarPeliculaPorId(id);
        ui.mostrarDetallePelicula(p);
    }

    /**
     * Actualiza los datos de una película existente.
     * Permite modificar título, año, duración, clasificación, director y sinopsis.
     *
     * @throws IOException si ocurre un error de lectura
     */
    private void actualizarPelicula() throws IOException {
        ui.mostrarMensaje("\n=== Actualizar película ===");
        String id = ui.leerId("ID de la película a actualizar:");
        Pelicula p = gestor.buscarPeliculaPorId(id);
        if (p == null) {
            ui.mostrarMensaje("Película no encontrada.");
            return;
        }

        ui.mostrarMensaje("\nValores actuales:");
        ui.mostrarMensaje("Título: " + p.getTitulo());
        ui.mostrarMensaje("Año: " + p.getAnio());
        ui.mostrarMensaje("Duración: " + p.getDuracionMinutos() + " min");
        ui.mostrarMensaje("Clasificación: " + p.getClasificacion());
        String dirActual = (p.getDirector() != null) ? p.getDirector().getNombre() : "(sin director)";
        ui.mostrarMensaje("Director: " + dirActual);
        String sinopsisActual = (p.getFicha() != null) ? p.getFicha().sinopsis() : "(sin sinopsis)";
        ui.mostrarMensaje("Sinopsis: " + sinopsisActual);
        ui.mostrarMensaje("--------------------------------------");

        String nuevoTitulo = ui.leerTextoConDefault("Nuevo título", p.getTitulo());
        int nuevoAnio = ui.leerEnteroConDefault("Nuevo año", p.getAnio());
        int nuevaDur = ui.leerEnteroConDefault("Nueva duración (min)", p.getDuracionMinutos());
        String nuevaClas = ui.leerClasificacionConDefault(p.getClasificacion());

        boolean ok = gestor.actualizarPelicula(p, nuevoTitulo, nuevoAnio, nuevaDur, nuevaClas);
        if (!ok) {
            ui.mostrarMensaje("No se pudo actualizar la película.");
            return;
        }

        String respDir = ui.leerTextoObligatorio("¿Desea modificar el director? (si/no):");
        if (respDir.equalsIgnoreCase("si") || respDir.equalsIgnoreCase("sí")) {
            seleccionarDirectorParaPelicula(p);
        }

        System.out.print("Nueva sinopsis (ENTER = mantener, '-' = eliminar): ");
        String nuevaSinopsis = new java.io.BufferedReader(
                new java.io.InputStreamReader(System.in)).readLine();
        if (nuevaSinopsis != null) {
            nuevaSinopsis = nuevaSinopsis.trim();
            if (nuevaSinopsis.equals("-")) {
                p.setFicha(null);
            } else if (!nuevaSinopsis.isEmpty()) {
                p.setFicha(new Pelicula.Ficha(nuevaSinopsis));
            }
        }

        ui.mostrarMensaje("Película actualizada correctamente.");
    }

    /**
     * Elimina una película a partir de su id.
     *
     * @throws IOException si ocurre un error de lectura
     */
    private void eliminarPelicula() throws IOException {
        ui.mostrarMensaje("\n=== Eliminar película ===");
        String id = ui.leerId("ID de la película a eliminar:");
        boolean ok = gestor.eliminarPelicula(id);
        if (ok) ui.mostrarMensaje("Película eliminada.");
        else ui.mostrarMensaje("No existe una película con ese ID.");
    }

    /**
     * Permite seleccionar un director para una película, ya sea
     * utilizando uno existente o creando uno nuevo.
     *
     * @param p película a la que se asociará el director
     * @throws IOException si ocurre un error de lectura
     */
    private void seleccionarDirectorParaPelicula(Pelicula p) throws IOException {
        String usarExistente = ui.leerTextoObligatorio("¿Usar director ya registrado? (si/no):");
        Director director = null;

        if (usarExistente.equalsIgnoreCase("si") || usarExistente.equalsIgnoreCase("sí")) {
            List<Director> directores = gestor.listarDirectores();
            if (directores.isEmpty()) {
                ui.mostrarMensaje("No hay directores registrados. Debe crear uno nuevo.");
            } else {
                ui.mostrarDirectoresConPeliculas(directores);
                String idDir = ui.leerId("ID del director a asociar:");
                director = gestor.buscarDirectorPorId(idDir);
                if (director == null) {
                    ui.mostrarMensaje("No se encontró un director con ese ID.");
                }
            }
        }

        if (director == null) {
            ui.mostrarMensaje("\n=== Registrar nuevo director ===");
            String idNuevo = ui.leerId("Nuevo ID de director:");
            String nombreNuevo = ui.leerTextoObligatorio("Nombre del director:");
            boolean creado = gestor.registrarDirector(idNuevo, nombreNuevo);
            if (!creado) {
                ui.mostrarMensaje("Ya existía un director con ese ID, se usará ese.");
            }
            director = gestor.buscarDirectorPorId(idNuevo);
        }

        if (director != null) {
            gestor.asociarPeliculaConDirector(p, director);
            ui.mostrarMensaje("Director asociado correctamente a la película.");
        }
    }

    // ====== DIRECTORES ======

    /**
     * Registra un director desde la consola.
     *
     * @throws IOException si ocurre un error de lectura
     */
    private void registrarDirector() throws IOException {
        ui.mostrarMensaje("\n=== Registrar director ===");
        String id = ui.leerId("ID director:");
        String nombre = ui.leerTextoObligatorio("Nombre del director:");
        boolean ok = gestor.registrarDirector(id, nombre);
        if (ok) ui.mostrarMensaje("Director registrado correctamente.");
        else ui.mostrarMensaje("Ya existe un director con ese ID.");
    }

    /**
     * Muestra la lista de directores con las películas que han dirigido.
     */
    private void listarDirectores() {
        ui.mostrarDirectoresConPeliculas(gestor.listarDirectores());
    }

    // ====== ACTORES ======

    /**
     * Registra un actor desde la consola.
     *
     * @throws IOException si ocurre un error de lectura
     */
    private void registrarActor() throws IOException {
        ui.mostrarMensaje("\n=== Registrar actor ===");
        String id = ui.leerId("ID actor:");
        String nombre = ui.leerTextoObligatorio("Nombre del actor:");
        boolean ok = gestor.registrarActor(id, nombre);
        if (ok) ui.mostrarMensaje("Actor registrado correctamente.");
        else ui.mostrarMensaje("Ya existe un actor con ese ID.");
    }

    /**
     * Muestra la lista de actores con su filmografía.
     */
    private void listarActores() {
        ui.mostrarActoresConPeliculas(gestor.listarActores());
    }

    /**
     * Asocia un actor a una película ya registrada.
     *
     * @throws IOException si ocurre un error de lectura
     */
    private void asociarActorPelicula() throws IOException {
        ui.mostrarMensaje("\n=== Asociar actor a película ===");

        String idActor = ui.leerId("ID del actor:");
        Actor actor = gestor.buscarActorPorId(idActor);
        if (actor == null) {
            ui.mostrarMensaje("Actor no encontrado.");
            return;
        }

        String idPeli = ui.leerId("ID de la película:");
        Pelicula pelicula = gestor.buscarPeliculaPorId(idPeli);
        if (pelicula == null) {
            ui.mostrarMensaje("Película no encontrada.");
            return;
        }

        boolean ok = gestor.asociarActorConPelicula(actor, pelicula);
        if (ok) ui.mostrarMensaje("Actor asociado correctamente a la película.");
        else ui.mostrarMensaje("No se pudo asociar el actor a la película.");
    }

    // ====== CUENTAS (POLIMORFISMO) ======

    /**
     * Muestra todas las cuentas registradas (usuarios y administradores)
     * utilizando polimorfismo sobre la clase base {@link Cuenta}.
     */
    private void verCuentas() {
        List<Cuenta> cuentas = gestor.listarCuentas();
        ui.mostrarCuentas(cuentas);
    }

    // ====== MENÚ USUARIO ======

    /**
     * Procesa las opciones del menú de usuario normal.
     *
     * @param opcion opción elegida
     * @throws IOException si ocurre un error de lectura
     */
    private void procesarOpcionUsuario(int opcion) throws IOException {
        switch (opcion) {
            case 1 -> ui.mostrarPeliculas(gestor.listarPeliculas());
            case 2 -> agregarFavorito();
            case 3 -> verFavoritos();
            case 4 -> listarDirectores();
            case 5 -> listarActores();
            case 0 -> cerrarSesion();
            default -> ui.mostrarMensaje("Opción inválida.");
        }
    }

    /**
     * Agrega una película a la lista de favoritas del usuario en sesión.
     *
     * @throws IOException si ocurre un error de lectura
     */
    private void agregarFavorito() throws IOException {
        ui.mostrarMensaje("\n=== Agregar a favoritos ===");
        String id = ui.leerId("ID de la película:");
        Pelicula p = gestor.buscarPeliculaPorId(id);
        if (p == null) {
            ui.mostrarMensaje("Película no encontrada.");
            return;
        }
        boolean ok = gestor.agregarFavorito(usuarioActual, p);
        if (ok) ui.mostrarMensaje("Película agregada a favoritos.");
        else ui.mostrarMensaje("La película ya estaba en favoritos.");
    }

    /**
     * Muestra las películas marcadas como favoritas por el usuario en sesión.
     */
    private void verFavoritos() {
        ui.mostrarMensaje("\n=== MIS FAVORITAS ===");
        List<Pelicula> favs = gestor.listarFavoritos(usuarioActual);
        ui.mostrarPeliculas(favs);
    }
}
