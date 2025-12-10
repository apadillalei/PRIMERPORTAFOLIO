package cr.ac.ucenfotec.ui;

import cr.ac.ucenfotec.bl.entities.Actor;
import cr.ac.ucenfotec.bl.entities.Administrador;
import cr.ac.ucenfotec.bl.entities.Cuenta;
import cr.ac.ucenfotec.bl.entities.Director;
import cr.ac.ucenfotec.bl.entities.Genero;
import cr.ac.ucenfotec.bl.entities.Pelicula;
import cr.ac.ucenfotec.bl.entities.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase UI (User Interface).
 * <p>
 * Representa la capa de presentación del sistema Movie Library.
 * Se encarga de:
 * <ul>
 *     <li>Mostrar los menús de invitado, administrador y usuario.</li>
 *     <li>Leer datos desde la consola de forma validada.</li>
 *     <li>Mostrar listados y detalles de películas, directores, actores y cuentas.</li>
 * </ul>
 * No contiene lógica de negocio, solo interacción con el usuario.
 */
public class UI {

    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // ===== MENUS PRINCIPALES =====

    /**
     * Muestra el menú principal cuando no hay sesión iniciada (modo invitado).
     */
    public void mostrarMenuInvitado() {
        System.out.println("\n===== MOVIE LIBRARY =====");
        System.out.println("Modo: Invitado");
        System.out.println("----------------------------------");
        System.out.println("1. Registrar administrador");
        System.out.println("2. Registrar usuario");
        System.out.println("3. Iniciar sesión (administrador)");
        System.out.println("4. Iniciar sesión (usuario)");
        System.out.println("5. Listar cuentas registradas");
        System.out.println("0. Salir");
    }

    /**
     * Lee una opción numérica del usuario, validando que sea un entero.
     *
     * @return número de opción seleccionada
     * @throws IOException si ocurre un error al leer desde consola
     */
    public int leerOpcion() throws IOException {
        while (true) {
            try {
                System.out.print("Opción: ");
                return Integer.parseInt(in.readLine().trim());
            } catch (Exception e) {
                System.out.println("Valor inválido. Intente de nuevo.");
            }
        }
    }

    /**
     * Muestra el menú de un administrador autenticado.
     *
     * @param adminName nombre de usuario del administrador
     */
    public void mostrarMenuAdmin(String adminName) {
        System.out.println("\n════════════════════════════════════");
        System.out.println("     PANEL DE ADMINISTRACIÓN");
        System.out.println("     Sesión: " + adminName);
        System.out.println("════════════════════════════════════");

        // Películas
        System.out.println("  PELÍCULAS ");
        System.out.println("  1) Crear película");
        System.out.println("  2) Listar películas");
        System.out.println("  3) Buscar película por ID");
        System.out.println("  4) Actualizar película");
        System.out.println("  5) Eliminar película");

        System.out.println("────────────────────────────────────");

        // Directores
        System.out.println("  DIRECTORES  ");
        System.out.println("  6) Registrar director");
        System.out.println("  7) Listar directores y sus películas");

        System.out.println("────────────────────────────────────");

        // Actores
        System.out.println("  ACTORES  ");
        System.out.println("  8) Registrar actor");
        System.out.println("  9) Listar actores y su filmografía");
        System.out.println(" 10) Asociar actor a película");

        System.out.println("────────────────────────────────────");
        System.out.println("\n  0) Cerrar sesión y volver al inicio");
        System.out.println("\n════════════════════════════════════");
    }

    /**
     * Muestra el menú para un usuario normal autenticado.
     *
     * @param userName nombre de usuario
     */
    public void mostrarMenuUsuario(String userName) {
        System.out.println("\n════════════════════════════════════");
        System.out.println("       CATÁLOGO - USUARIO");
        System.out.println("       Sesión: " + userName);
        System.out.println("════════════════════════════════════");

        System.out.println("  CATÁLOGO ");
        System.out.println("  1) Listar películas");

        System.out.println("────────────────────────────────────");

        System.out.println("  FAVORITOS ");
        System.out.println("  2) Agregar a favoritas");
        System.out.println("  3) Ver mis favoritas");

        System.out.println("────────────────────────────────────");

        System.out.println("  INFORMACIÓN  ");
        System.out.println("  4) Listar directores y sus películas");
        System.out.println("  5) Listar actores y su filmografía");

        System.out.println("────────────────────────────────────");
        System.out.println("\n  0) Cerrar sesión y volver al inicio");
        System.out.println("════════════════════════════════════");
    }

    // ===== MÉTODOS DE IO =====

    /**
     * Imprime un mensaje simple en consola.
     *
     * @param msg texto a mostrar
     */
    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    /**
     * Lee texto no vacío desde consola, mostrando una etiqueta.
     *
     * @param label texto a mostrar como etiqueta
     * @return texto ingresado sin espacios al inicio ni al final
     * @throws IOException si ocurre un error de lectura
     */
    public String leerTextoObligatorio(String label) throws IOException {
        String s;
        do {
            System.out.print(label + " ");
            s = in.readLine();
            if (s != null) s = s.trim();
        } while (s == null || s.isEmpty());
        return s;
    }

    /**
     * Lee un número entero desde consola, con validación.
     *
     * @param label etiqueta a mostrar
     * @return entero leído
     * @throws IOException si ocurre un error de lectura
     */
    public int leerEntero(String label) throws IOException {
        while (true) {
            try {
                System.out.print(label + " ");
                return Integer.parseInt(in.readLine().trim());
            } catch (Exception e) {
                System.out.println("Valor inválido. Intente de nuevo.");
            }
        }
    }

    /**
     * Lee un identificador alfanumérico (solo letras y números).
     *
     * @param label etiqueta a mostrar
     * @return id en mayúsculas
     * @throws IOException si ocurre un error de lectura
     */
    public String leerId(String label) throws IOException {
        while (true) {
            String id = leerTextoObligatorio(label).toUpperCase();
            if (id.matches("[A-Z0-9]+")) return id;
            System.out.println("Solo se permiten letras y números.");
        }
    }

    /**
     * Lee un correo electrónico con validación de formato básico.
     *
     * @param label etiqueta a mostrar
     * @return email válido
     * @throws IOException si ocurre un error de lectura
     */
    public String leerEmail(String label) throws IOException {
        while (true) {
            String email = leerTextoObligatorio(label);
            if (email.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) return email;
            System.out.println("Email inválido. Intente de nuevo.");
        }
    }

    /**
     * Lee la clasificación de la película (ej.: G, PG-13, R),
     * validando que tenga un formato sencillo alfanumérico.
     *
     * @return cadena con la clasificación
     * @throws IOException si ocurre un error de lectura
     */
    public String leerClasificacion() throws IOException {
        while (true) {
            String c = leerTextoObligatorio("Clasificación (ej: G, PG-13, R):");
            if (c.matches("[A-Za-z0-9+\\-]+")) return c;
            System.out.println("Formato de clasificación inválido.");
        }
    }

    /**
     * Lee una lista de géneros separados por coma.
     *
     * @return lista de objetos {@link Genero} creados a partir del texto ingresado
     * @throws IOException si ocurre un error de lectura
     */
    public List<Genero> leerGeneros() throws IOException {
        System.out.print("Géneros separados por coma (ej: Acción, Drama): ");
        String linea = in.readLine();
        List<Genero> generos = new ArrayList<>();
        if (linea == null || linea.trim().isEmpty()) {
            return generos;
        }
        for (String raw : linea.split(",")) {
            String g = raw.trim();
            if (!g.isEmpty()) {
                generos.add(new Genero(g));
            }
        }
        return generos;
    }

    // ===== LECTURA CON VALOR POR DEFECTO (para actualización) =====

    /**
     * Lee un texto permitiendo mantener el valor actual si el usuario
     * presiona solo ENTER.
     *
     * @param label  etiqueta a mostrar
     * @param actual valor actual del campo
     * @return texto nuevo o el valor actual si el usuario no escribe nada
     * @throws IOException si ocurre un error de lectura
     */
    public String leerTextoConDefault(String label, String actual) throws IOException {
        System.out.print(label + " (actual: " + actual + ") [ENTER para mantener]: ");
        String s = in.readLine();
        if (s == null || s.trim().isEmpty()) return actual;
        return s.trim();
    }

    /**
     * Lee un entero permitiendo mantener el valor actual si el usuario
     * presiona solo ENTER.
     *
     * @param label  etiqueta a mostrar
     * @param actual valor actual
     * @return nuevo valor entero o el actual si la entrada se deja vacía
     * @throws IOException si ocurre un error de lectura
     */
    public int leerEnteroConDefault(String label, int actual) throws IOException {
        System.out.print(label + " (actual: " + actual + ") [ENTER para mantener]: ");
        String s = in.readLine();
        if (s == null || s.trim().isEmpty()) return actual;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Se mantiene el valor: " + actual);
            return actual;
        }
    }

    /**
     * Lee una nueva clasificación permitiendo mantener la existente
     * si el usuario presiona ENTER.
     *
     * @param actual valor actual de la clasificación
     * @return nueva clasificación o la actual si la entrada es vacía
     * @throws IOException si ocurre un error de lectura
     */
    public String leerClasificacionConDefault(String actual) throws IOException {
        System.out.print("Nueva clasificación (actual: " + actual + ") [ENTER para mantener]: ");
        String s = in.readLine();
        if (s == null || s.trim().isEmpty()) return actual;
        if (s.matches("[A-Za-z0-9+\\-]+")) return s;
        System.out.println("Formato inválido. Se mantiene: " + actual);
        return actual;
    }

    // ===== MOSTRAR PELÍCULAS =====

    /**
     * Muestra en forma de lista todas las películas con datos
     * básicos, géneros y actores asociados.
     *
     * @param peliculas lista de películas a mostrar
     */
    public void mostrarPeliculas(List<Pelicula> peliculas) {
        if (peliculas == null || peliculas.isEmpty()) {
            System.out.println("No hay películas registradas.");
            return;
        }

        System.out.println("\n--- LISTA DE PELÍCULAS ---");
        int i = 1;
        for (Pelicula p : peliculas) {
            String dir = (p.getDirector() == null)
                    ? "N/A"
                    : p.getDirector().getNombre();

            // Géneros
            String gs;
            if (p.getGeneros() == null || p.getGeneros().isEmpty()) {
                gs = "Sin géneros";
            } else {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < p.getGeneros().size(); j++) {
                    sb.append(p.getGeneros().get(j).getNombre());
                    if (j < p.getGeneros().size() - 1) sb.append(", ");
                }
                gs = sb.toString();
            }

            // Actores (elenco)
            String elencoStr;
            if (p.getElenco() == null || p.getElenco().isEmpty()) {
                elencoStr = "Sin actores asociados";
            } else {
                StringBuilder sbElenco = new StringBuilder();
                for (int j = 0; j < p.getElenco().size(); j++) {
                    sbElenco.append(p.getElenco().get(j).getNombre());
                    if (j < p.getElenco().size() - 1) sbElenco.append(", ");
                }
                elencoStr = sbElenco.toString();
            }

            System.out.printf(
                    "%d) %s | %s (%d) | %d min | %s | Dir: %s%n",
                    i++,
                    p.getId(),
                    p.getTitulo(),
                    p.getAnio(),
                    p.getDuracionMinutos(),
                    p.getClasificacion(),
                    dir
            );
            System.out.println("   Géneros: " + gs);
            System.out.println("   Actores: " + elencoStr);
            System.out.println("----------------------------------");
        }
    }

    /**
     * Muestra el detalle completo de una película individual:
     * datos básicos, director, géneros, actores y sinopsis.
     *
     * @param p película a mostrar
     */
    public void mostrarDetallePelicula(Pelicula p) {
        if (p == null) {
            System.out.println("Película no encontrada.");
            return;
        }
        System.out.println("\n--- Detalle de película ---");
        System.out.println("ID: " + p.getId());
        System.out.println("Título: " + p.getTitulo());
        System.out.println("Año: " + p.getAnio());
        System.out.println("Duración: " + p.getDuracionMinutos() + " min");
        System.out.println("Clasificación: " + p.getClasificacion());

        if (p.getDirector() != null) {
            System.out.println("Director: " + p.getDirector().getNombre());
        } else {
            System.out.println("Director: (no asignado)");
        }

        // Géneros
        if (p.getGeneros() != null && !p.getGeneros().isEmpty()) {
            System.out.print("Géneros: ");
            for (int i = 0; i < p.getGeneros().size(); i++) {
                System.out.print(p.getGeneros().get(i).getNombre());
                if (i < p.getGeneros().size() - 1) System.out.print(", ");
            }
            System.out.println();
        } else {
            System.out.println("Géneros: (no asignados)");
        }

        // Actores / elenco
        if (p.getElenco() != null && !p.getElenco().isEmpty()) {
            System.out.print("Actores: ");
            for (int i = 0; i < p.getElenco().size(); i++) {
                System.out.print(p.getElenco().get(i).getNombre());
                if (i < p.getElenco().size() - 1) System.out.print(", ");
            }
            System.out.println();
        } else {
            System.out.println("Actores: (no asociados)");
        }

        if (p.getFicha() != null) {
            System.out.println("Sinopsis: " + p.getFicha().sinopsis());
        }
    }

    // ===== MOSTRAR DIRECTORES Y SUS PELiCULAS =====

    /**
     * Muestra todos los directores registrados junto con las películas
     * que han dirigido.
     *
     * @param directores lista de directores
     */
    public void mostrarDirectoresConPeliculas(List<Director> directores) {
        if (directores == null || directores.isEmpty()) {
            System.out.println("No hay directores registrados.");
            return;
        }

        System.out.println("\n=== DIRECTORES REGISTRADOS ===");
        int i = 1;
        for (Director d : directores) {
            System.out.println(i++ + ") " + d.getNombre() + " (ID: " + d.getId() + ")");
            if (d.getPeliculasDirigidas() == null || d.getPeliculasDirigidas().isEmpty()) {
                System.out.println("   - Sin películas registradas.");
            } else {
                System.out.println("   Películas dirigidas:");
                for (Pelicula p : d.getPeliculasDirigidas()) {
                    System.out.println("      • " + p.getId() + " - " + p.getTitulo() + " (" + p.getAnio() + ")");
                }
            }
            System.out.println("----------------------------------");
        }
    }

    // ===== MOSTRAR ACTORES Y SU FILMOGRAFIA =====

    /**
     * Muestra todos los actores registrados con las películas en las que han participado.
     *
     * @param actores lista de actores
     */
    public void mostrarActoresConPeliculas(List<Actor> actores) {
        if (actores == null || actores.isEmpty()) {
            System.out.println("No hay actores registrados.");
            return;
        }

        System.out.println("\n=== ACTORES REGISTRADOS ===");
        int i = 1;
        for (Actor a : actores) {
            System.out.println(i++ + ") " + a.getNombre() + " (ID: " + a.getId() + ")");
            if (a.getFilmografia() == null || a.getFilmografia().isEmpty()) {
                System.out.println("   - Sin películas registradas.");
            } else {
                System.out.println("   Películas en las que participa:");
                for (Pelicula p : a.getFilmografia()) {
                    System.out.println("      • " + p.getId() + " - " + p.getTitulo() + " (" + p.getAnio() + ")");
                }
            }
            System.out.println("----------------------------------");
        }
    }

    // ===== MOSTRAR CUENTAS =====

    /**
     * Muestra todas las cuentas registradas (usuarios y administradores),
     * utilizando polimorfismo sobre la clase base {@link Cuenta}.
     *
     * @param cuentas lista de cuentas (instancias de {@link Administrador} y {@link User})
     */
    public void mostrarCuentas(List<Cuenta> cuentas) {
        if (cuentas == null || cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas.");
            return;
        }

        System.out.println("\n=== CUENTAS REGISTRADAS (polimorfismo) ===");
        for (Cuenta c : cuentas) {
            String tipo;
            if (c instanceof Administrador) tipo = "Administrador";
            else if (c instanceof User) tipo = "Usuario";
            else tipo = "Cuenta";

            System.out.println("- [" + tipo + "] " +
                    c.getUsername() + " (ID: " + c.getId() + ", Email: " + c.getEmail() + ")");
        }
    }
}
