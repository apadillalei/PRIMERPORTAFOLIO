package cr.ac.ucenfotec.tl;

import cr.ac.ucenfotec.bl.entities.*;
import cr.ac.ucenfotec.bl.logic.Gestor;
import cr.ac.ucenfotec.ui.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final UI ui;
    private final Gestor gestor;

    private Administrador adminActual;
    private User usuarioActual;

    public Controller() {
        this.ui = new UI();
        this.gestor = new Gestor();
    }

    public void start() throws IOException {
        int opcion;
        do {
            opcion = ui.mostrarMenuPrincipal(
                    adminActual == null ? null : adminActual.getUsername(),
                    usuarioActual == null ? null : usuarioActual.getUsername()
            );
            switch (opcion) {
                case 1 -> registrarAdmin();
                case 2 -> registrarUsuario();
                case 3 -> iniciarSesionAdmin();
                case 4 -> iniciarSesionUsuario();
                case 5 -> loopAdmin();
                case 6 -> loopUsuario();
                case 0 -> ui.mostrarMensaje("Saliendo del sistema...");
                default -> ui.mostrarMensaje("Opción inválida.");
            }
        } while (opcion != 0);
    }

    // ===== ADMIN =====
    private void registrarAdmin() throws IOException {
        String id = ui.leerId("ID admin: ");
        String username = ui.leerTextoObligatorio("Nombre de usuario admin: ");
        String email = ui.leerEmail("Email admin: ");

        boolean ok = gestor.registrarAdmin(id, username, email);
        if (ok) ui.mostrarMensaje("Administrador registrado correctamente.");
        else ui.mostrarMensaje("Ya existe un administrador con ese ID.");
    }

    private void iniciarSesionAdmin() throws IOException {
        String id = ui.leerId("ID admin para iniciar sesión: ");
        Administrador a = gestor.loginAdmin(id);
        if (a == null) {
            ui.mostrarMensaje("Administrador no encontrado.");
        } else {
            adminActual = a;
            ui.mostrarMensaje("Sesión de administrador iniciada como: " + adminActual.getUsername());
        }
    }

    // ===== USUARIO =====
    private void registrarUsuario() throws IOException {
        String id = ui.leerId("ID usuario: ");
        String username = ui.leerTextoObligatorio("Nombre de usuario: ");
        String email = ui.leerEmail("Email usuario: ");

        boolean ok = gestor.registrarUsuario(id, username, email);
        if (ok) ui.mostrarMensaje("Usuario registrado correctamente.");
        else ui.mostrarMensaje("Ya existe un usuario con ese ID.");
    }

    private void iniciarSesionUsuario() throws IOException {
        String id = ui.leerId("ID usuario para iniciar sesión: ");
        User u = gestor.loginUsuario(id);
        if (u == null) {
            ui.mostrarMensaje("Usuario no encontrado.");
        } else {
            usuarioActual = u;
            ui.mostrarMensaje("Sesión de usuario iniciada como: " + usuarioActual.getUsername());
        }
    }

    // ===== LOOP ADMIN =====
    private void loopAdmin() throws IOException {
        if (adminActual == null) {
            ui.mostrarMensaje("Debe iniciar sesión como administrador.");
            return;
        }
        int op;
        do {
            op = ui.mostrarMenuAdmin(adminActual.getUsername());
            switch (op) {
                case 1 -> crearPelicula();
                case 2 -> ui.mostrarPeliculas(gestor.listarPeliculas());
                case 3 -> buscarPelicula();
                case 4 -> actualizarPelicula();
                case 5 -> eliminarPelicula();
                case 0 -> ui.mostrarMensaje("Volviendo al menú principal...");
                default -> ui.mostrarMensaje("Opción inválida.");
            }
        } while (op != 0);
    }

    private void crearPelicula() throws IOException {
        String id = ui.leerId("ID película: ");
        String titulo = ui.leerTextoObligatorio("Título: ");
        int anio = ui.leerEntero("Año (>= 1888): ");
        int duracion = ui.leerEntero("Duración en minutos: ");
        String clasificacion = ui.leerClasificacion();

        String dirNombre = ui.leerTextoObligatorio("Nombre del director: ");
        Director d = new Director();
        d.setNombre(dirNombre);
        d.setPeliculasDirigidas(new ArrayList<>());

        List<Genero> generos = ui.leerGeneros();

        Pelicula p = new Pelicula();
        p.setId(id);
        p.setTitulo(titulo);
        p.setAnio(anio);
        p.setDuracionMinutos(duracion);
        p.setClasificacion(clasificacion);
        p.setDirector(d);
        p.setGeneros(new ArrayList<>(generos));

        String desea = ui.leerTextoObligatorio("¿Desea agregar sinopsis? (si/no): ");
        if (desea.equalsIgnoreCase("si") || desea.equalsIgnoreCase("sí")) {
            String sinopsis = ui.leerTextoObligatorio("Sinopsis: ");
            p.setFicha(new Pelicula.Ficha(sinopsis));
        }

        boolean ok = gestor.crearPelicula(p);
        if (ok) {
            d.getPeliculasDirigidas().add(p);
            ui.mostrarMensaje("Película creada correctamente.");
        } else {
            ui.mostrarMensaje("Ya existe una película con ese ID.");
        }
    }

    private void buscarPelicula() throws IOException {
        String id = ui.leerId("ID de la película a buscar: ");
        Pelicula p = gestor.buscarPeliculaPorId(id);
        ui.mostrarDetallePelicula(p);
    }

    private void actualizarPelicula() throws IOException {
        String id = ui.leerId("ID de la película a actualizar: ");
        Pelicula p = gestor.buscarPeliculaPorId(id);
        if (p == null) {
            ui.mostrarMensaje("Película no encontrada.");
            return;
        }

        String nuevoTitulo = ui.leerTextoObligatorio("Nuevo título: ");
        int nuevoAnio = ui.leerEntero("Nuevo año: ");
        int nuevaDur = ui.leerEntero("Nueva duración (min): ");
        String nuevaClas = ui.leerClasificacion();

        boolean ok = gestor.actualizarPelicula(p, nuevoTitulo, nuevoAnio, nuevaDur, nuevaClas);
        if (ok) ui.mostrarMensaje("Película actualizada.");
        else ui.mostrarMensaje("No se pudo actualizar la película.");
    }

    private void eliminarPelicula() throws IOException {
        String id = ui.leerId("ID de la película a eliminar: ");
        boolean ok = gestor.eliminarPelicula(id);
        if (ok) ui.mostrarMensaje("Película eliminada.");
        else ui.mostrarMensaje("No existe una película con ese ID.");
    }

    // ===== LOOP USUARIO =====
    private void loopUsuario() throws IOException {
        if (usuarioActual == null) {
            ui.mostrarMensaje("Debe iniciar sesión como usuario.");
            return;
        }
        int op;
        do {
            op = ui.mostrarMenuUsuario(usuarioActual.getUsername());
            switch (op) {
                case 1 -> ui.mostrarPeliculas(gestor.listarPeliculas());
                case 2 -> agregarFavorito();
                case 3 -> verFavoritos();
                case 0 -> ui.mostrarMensaje("Volviendo al menú principal...");
                default -> ui.mostrarMensaje("Opción inválida.");
            }
        } while (op != 0);
    }

    private void agregarFavorito() throws IOException {
        String id = ui.leerId("ID de la película a agregar a favoritos: ");
        Pelicula p = gestor.buscarPeliculaPorId(id);
        if (p == null) {
            ui.mostrarMensaje("Película no encontrada.");
            return;
        }
        boolean ok = gestor.agregarFavorito(usuarioActual, p);
        if (ok) ui.mostrarMensaje("Película agregada a favoritos.");
        else ui.mostrarMensaje("La película ya estaba en favoritos.");
    }

    private void verFavoritos() {
        List<Pelicula> favs = gestor.listarFavoritos(usuarioActual);
        System.out.println("\n--- MIS FAVORITAS ---");
        ui.mostrarPeliculas(favs);
    }
}
