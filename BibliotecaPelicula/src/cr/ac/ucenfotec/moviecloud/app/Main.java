package cr.ac.ucenfotec.moviecloud.app;

import cr.ac.ucenfotec.moviecloud.model.Administrador;
import cr.ac.ucenfotec.moviecloud.model.Director;
import cr.ac.ucenfotec.moviecloud.model.Genero;
import cr.ac.ucenfotec.moviecloud.model.Pelicula;
import cr.ac.ucenfotec.moviecloud.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    // ===== IO =====
    static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // ===== CATÁLOGO =====
    static final int MAX = 100;
    static Pelicula[] peliculas = new Pelicula[MAX];
    static int totalPeliculas = 0;

    // ===== ADMINS =====
    static final int MAX_ADM = 20;
    static Administrador[] admins = new Administrador[MAX_ADM];
    static int totalAdmins = 0;
    static Administrador adminActual = null; // sesión admin

    // ===== USUARIOS =====
    static final int MAX_USERS = 100;
    static User[] usuarios = new User[MAX_USERS];
    static int totalUsuarios = 0;
    static User usuarioActual = null; // sesión usuario

    // Favoritas por usuario
    static String[][] favoritas = new String[MAX_USERS][MAX];
    static int[] totalFavs = new int[MAX_USERS];

    public static void main(String[] args) throws IOException {
        int op;
        do {
            op = Menu.menuPrincipal(
                    (adminActual == null ? null : adminActual.getUsername()),
                    (usuarioActual == null ? null : usuarioActual.getUsername())
            );
            switch (op) {
                case 1 -> registrarAdmin();
                case 2 -> registrarUsuario();
                case 3 -> iniciarSesionAdmin();
                case 4 -> iniciarSesionUsuario();
                case 5 -> seleccionarRol();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    // ===== Menus =====
    static void seleccionarRol() throws IOException {
        int r = Menu.menuRol();
        switch (r) {
            case 1 -> loopAdmin();
            case 2 -> loopUsuario();
            case 0 -> {}
            default -> System.out.println("Opción inválida.");
        }
    }

    static void loopAdmin() throws IOException {
        if (adminActual == null) {
            System.out.println("Debe iniciar sesión como administrador.");
            return;
        }
        int op;
        do {
            op = Menu.menuAdmin(adminActual.getUsername());
            switch (op) {
                case 1 -> crearPelicula();
                case 2 -> listarPeliculas();
                case 3 -> buscarPorId();
                case 4 -> actualizarPelicula();
                case 5 -> eliminarPelicula();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    static void loopUsuario() throws IOException {
        if (usuarioActual == null) {
            System.out.println("Debe iniciar sesión como usuario.");
            return;
        }
        int op;
        do {
            op = Menu.menuUsuario(usuarioActual.getUsername());
            switch (op) {
                case 1 -> listarPeliculas();
                case 2 -> guardarFavorita();
                case 3 -> verFavoritas();
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    // ===== Autenticación =====
    static void registrarAdmin() throws IOException {
        if (totalAdmins >= MAX_ADM) {
            System.out.println("Capacidad de administradores llena.");
            return;
        }
        String id = leerId("ID admin (alfanumérico): ");
        if (indiceAdmin(id) != -1) {
            System.out.println("ID ya registrado.");
            return;
        }
        String user = leerNoVacio("Username: ");
        String email = leerEmail("Email: ");
        admins[totalAdmins++] = new Administrador(id, user, email, null);
        System.out.println("Administrador registrado.");
    }

    static void registrarUsuario() throws IOException {
        if (totalUsuarios >= MAX_USERS) {
            System.out.println("Capacidad de usuarios llena.");
            return;
        }
        String id = leerId("ID usuario (alfanumérico): ");
        if (indiceUsuario(id) != -1) {
            System.out.println("ID ya registrado.");
            return;
        }
        String user = leerNoVacio("Username: ");
        String email = leerEmail("Email: ");
        usuarios[totalUsuarios] = new User(id, user, email);
        favoritas[totalUsuarios] = new String[MAX];
        totalFavs[totalUsuarios] = 0;
        totalUsuarios++;
        System.out.println("Usuario registrado.");
    }

    static void iniciarSesionAdmin() throws IOException {
        if (totalAdmins == 0) {
            System.out.println("No hay administradores registrados.");
            return;
        }
        String id = leerId("ID admin: ");
        int idx = indiceAdmin(id);
        if (idx == -1) {
            System.out.println("Admin no encontrado.");
        } else {
            adminActual = admins[idx];
            System.out.println("Sesión admin iniciada: " + adminActual.getUsername());
        }
    }

    static void iniciarSesionUsuario() throws IOException {
        if (totalUsuarios == 0) {
            System.out.println("No hay usuarios registrados.");
            return;
        }
        String id = leerId("ID usuario: ");
        int idx = indiceUsuario(id);
        if (idx == -1) {
            System.out.println("Usuario no encontrado.");
        } else {
            usuarioActual = usuarios[idx];
            System.out.println("Sesión usuario iniciada: " + usuarioActual.getUsername());
        }
    }

    // ===== CRUD Películas (Admin) =====
    static void crearPelicula() throws IOException {
        if (totalPeliculas >= MAX) {
            System.out.println("Capacidad de catálogo llena.");
            return;
        }

        String id = leerIdNuevoPelicula();
        String titulo = leerNoVacio("Título: ");
        int anio = leerEnteroRango("Año (>=1888): ", 1888, 3000);
        int duracion = leerEnteroRango("Duración (min > 0): ", 1, 10000);
        String clas = leerClasificacion();

        Director d = new Director();
        d.setId(leerId("ID director: "));
        d.setNombre(leerNombre("Nombre del director: "));
        d.setPeliculasDirigidas(new HashSet<>());

        System.out.println("Géneros separados por coma (ej: Acción, Drama). Debe haber al menos uno.");
        Set<Genero> generos = leerGenerosObligatorio();

        Pelicula p = new Pelicula(id, titulo, anio, duracion, clas, generos, null, d);
        d.getPeliculasDirigidas().add(p);
        peliculas[totalPeliculas++] = p;

        // Sinopsis - Ficha
        System.out.print("¿Agregar sinopsis? (si/no): ");
        String r = in.readLine();
        if ("si".equalsIgnoreCase(r) || "sí".equalsIgnoreCase(r)) {
            String sinopsis = leerNoVacio("Sinopsis: ");
            p.setFicha(new Pelicula.Ficha(sinopsis));
        }

        System.out.println("Película creada.");
    }

    static void listarPeliculas() {
        if (totalPeliculas == 0) {
            System.out.println("No hay películas registradas.");
            return;
        }
        System.out.println("\n--- LISTA DE PELÍCULAS ---");
        for (int i = 0; i < totalPeliculas; i++) {
            Pelicula p = peliculas[i];
            String dir = (p.getDirector() == null) ? "N/A" : p.getDirector().getNombre();
            String gs = (p.getGeneros() == null || p.getGeneros().isEmpty())
                    ? "Sin géneros"
                    : p.getGeneros().stream().map(Genero::getNombre).collect(Collectors.joining(", "));
            System.out.printf("%d) %s | %s (%d) | %d min | %s | Dir: %s | %s%n",
                    i + 1, p.getId(), p.getTitulo(), p.getAnio(),
                    p.getDuracionMinutos(), p.getClasificacion(), dir, gs);

            if (p.getFicha() != null) {
                System.out.println("   Sinopsis: " + p.getFicha().sinopsis());
            }
        }
    }

    static void buscarPorId() throws IOException {
        if (totalPeliculas == 0) {
            System.out.println("No hay películas.");
            return;
        }
        String id = leerIdExistentePelicula("ID a buscar: ");
        int idx = indicePelicula(id);
        Pelicula p = peliculas[idx];

        System.out.println("\n--- Detalle ---");
        System.out.println("ID: " + p.getId());
        System.out.println("Título: " + p.getTitulo());
        System.out.println("Año: " + p.getAnio());
        System.out.println("Duración: " + p.getDuracionMinutos() + " min");
        System.out.println("Clasificación: " + p.getClasificacion());
        if (p.getDirector() != null) System.out.println("Director: " + p.getDirector().getNombre());
        if (p.getGeneros() != null && !p.getGeneros().isEmpty()) {
            String gs = p.getGeneros().stream().map(Genero::getNombre).collect(Collectors.joining(", "));
            System.out.println("Géneros: " + gs);
        }
        if (p.getFicha() != null)
            System.out.println("Sinopsis: " + p.getFicha().sinopsis());
    }

    // ===== Actualizar Pelicula =======
    static void actualizarPelicula() throws IOException {
        if (totalPeliculas == 0) {
            System.out.println("No hay películas.");
            return;
        }
        String id = leerIdExistentePelicula("ID a actualizar: ");
        int idx = indicePelicula(id);
        Pelicula p = peliculas[idx];

        // Datos base
        String nuevoTitulo = leerConDefault("Nuevo título (enter = mantener): ", p.getTitulo());
        Integer nuevoAnio   = leerEnteroConDefault("Nuevo año (enter = mantener): ", p.getAnio(), 1888, 3000);
        Integer nuevaDur    = leerEnteroConDefault("Nueva duración (min, enter = mantener): ",
                p.getDuracionMinutos(), 1, 10000);

        System.out.print("Nueva clasificación (enter = mantener): ");
        String s = in.readLine();
        String nuevaClas = (s == null || s.trim().isEmpty()) ? p.getClasificacion() : validarClasificacion(s.trim());

        p.setTitulo(nuevoTitulo);
        p.setAnio(nuevoAnio);
        p.setDuracionMinutos(nuevaDur);
        p.setClasificacion(nuevaClas);

        // Director (inline)
        System.out.println("\n-- Director --");
        String dirIdActual  = (p.getDirector() != null && p.getDirector().getId() != null) ? p.getDirector().getId() : "";
        String dirNomActual = (p.getDirector() != null && p.getDirector().getNombre() != null) ? p.getDirector().getNombre() : "";
        String nuevoDirId   = leerConDefault("Nuevo ID director (enter = mantener): ", dirIdActual);
        String nuevoDirNom  = leerConDefault("Nuevo nombre director (enter = mantener): ", dirNomActual);

        if (p.getDirector() == null) p.setDirector(new Director());
        if (!nuevoDirId.isBlank())  p.getDirector().setId(nuevoDirId);
        if (!nuevoDirNom.isBlank()) p.getDirector().setNombre(nuevoDirNom);
        if (p.getDirector().getPeliculasDirigidas() == null) {
            p.getDirector().setPeliculasDirigidas(new HashSet<>());
        }
        p.getDirector().getPeliculasDirigidas().add(p);

        // Géneros
        System.out.println("\n-- Géneros --");
        String actuales = (p.getGeneros() == null || p.getGeneros().isEmpty())
                ? "(ninguno)"
                : p.getGeneros().stream().map(Genero::getNombre).collect(Collectors.joining(", "));
        System.out.println("Actuales: " + actuales);
        System.out.println("Ingrese nuevos géneros separados por coma para REEMPLAZAR.");
        System.out.println("Presione ENTER para mantener los actuales.");
        Set<Genero> nuevos = leerGenerosOpcional(); // null => mantener
        if (nuevos != null) {
            p.setGeneros(nuevos);
            System.out.println("Géneros reemplazados.");
        } else {
            System.out.println("Géneros se mantienen.");
        }

        // Sinopsis - Ficha
        System.out.println("\n-- Sinopsis --");
        String sinopsisActual = (p.getFicha() != null) ? p.getFicha().sinopsis() : "(sin ficha)";
        System.out.println("Actual: " + sinopsisActual);
        System.out.print("Nueva sinopsis (ENTER = mantener, '-' = borrar): ");
        String sin = in.readLine();
        if (sin != null) {
            sin = sin.trim();
            if (sin.equals("-")) {
                p.setFicha(null);
                System.out.println("Sinopsis eliminada.");
            } else if (!sin.isEmpty()) {
                p.setFicha(new Pelicula.Ficha(sin));
                System.out.println("Sinopsis actualizada.");
            } else {
                System.out.println("Sinopsis se mantiene.");
            }
        }

        System.out.println("\nPelícula actualizada.");
    }

    static void eliminarPelicula() throws IOException {
        if (totalPeliculas == 0) {
            System.out.println("No hay películas.");
            return;
        }
        String id = leerIdExistentePelicula("ID a eliminar: ");
        int idx = indicePelicula(id);

        String conf = leerConDefault("Confirmar (si/no): ", "no");
        if (!conf.equalsIgnoreCase("si") && !conf.equalsIgnoreCase("sí")) {
            System.out.println("Cancelado.");
            return;
        }

        peliculas[idx] = peliculas[totalPeliculas - 1];
        peliculas[totalPeliculas - 1] = null;
        totalPeliculas--;
        System.out.println("Película eliminada.");
    }

    static void editarDirector(Pelicula p) throws IOException {
        if (p.getDirector() == null) p.setDirector(new Director());
        p.getDirector().setId(leerId("Nuevo ID director: "));
        p.getDirector().setNombre(leerNombre("Nuevo nombre director: "));
        if (p.getDirector().getPeliculasDirigidas() == null) {
            p.getDirector().setPeliculasDirigidas(new HashSet<>());
        }
        p.getDirector().getPeliculasDirigidas().add(p);
        System.out.println("Director actualizado.");
    }

    static void editarGeneros(Pelicula p) throws IOException {
        if (p.getGeneros() == null) p.setGeneros(new HashSet<>());
        int op;
        do {
            System.out.println("Géneros actuales: " + (p.getGeneros().isEmpty()
                    ? "(ninguno)"
                    : p.getGeneros().stream().map(Genero::getNombre).collect(Collectors.joining(", "))));
            System.out.println("1. Reemplazar todos");
            System.out.println("2. Agregar");
            System.out.println("3. Quitar por nombre");
            System.out.println("0. Volver");
            op = leerEntero("Opción: ");
            switch (op) {
                case 1 -> {
                    System.out.println("Géneros separados por coma:");
                    Set<Genero> nuevos = leerGenerosObligatorio();
                    p.setGeneros(nuevos);
                    System.out.println("Géneros reemplazados.");
                }
                case 2 -> {
                    System.out.println("Géneros a agregar por coma:");
                    Set<Genero> aAgregar = leerGenerosOpcional();
                    int count = 0;
                    if (aAgregar != null) {
                        for (Genero g : aAgregar) {
                            if (!contieneGenero(p.getGeneros(), g.getNombre())) {
                                p.getGeneros().add(g);
                                count++;
                            }
                        }
                    }
                    System.out.println(count + " género(s) agregado(s).");
                }
                case 3 -> {
                    String nombre = leerNombre("Nombre del género a quitar: ");
                    Genero target = null;
                    for (Genero g : p.getGeneros()) {
                        if (g.getNombre().equalsIgnoreCase(nombre)) { target = g; break; }
                    }
                    if (target == null) System.out.println("No está asignado.");
                    else { p.getGeneros().remove(target); System.out.println("Eliminado."); }
                }
                case 0 -> {}
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }


    static void guardarFavorita() throws IOException {
        if (totalPeliculas == 0) {
            System.out.println("No hay películas en el catálogo.");
            return;
        }
        listarPeliculas();
        String id = leerIdExistentePelicula("ID a guardar en favoritas: ");
        int u = indiceUsuario(usuarioActual.getId());
        if (u == -1) { System.out.println("Error de sesión."); return; }
        if (yaFavorita(u, id)) {
            System.out.println("Ya está en favoritas.");
            return;
        }
        if (totalFavs[u] >= MAX) {
            System.out.println("Lista de favoritas llena.");
            return;
        }
        favoritas[u][totalFavs[u]++] = id;
        System.out.println("Guardada en favoritas.");
    }

    static void verFavoritas() {
        int u = indiceUsuario(usuarioActual.getId());
        if (u == -1) { System.out.println("Error de sesión."); return; }
        int n = totalFavs[u];
        System.out.println("\n--- Mis favoritas (" + n + ") ---");
        if (n == 0) {
            System.out.println("Sin favoritas.");
            return;
        }
        for (int i = 0; i < n; i++) {
            String id = favoritas[u][i];
            int idx = indicePelicula(id);
            if (idx != -1) {
                Pelicula p = peliculas[idx];
                System.out.printf("%d) %s | %s (%d)%n", i + 1, p.getId(), p.getTitulo(), p.getAnio());
            } else {
                System.out.printf("%d) %s | [ya no existe en catálogo]%n", i + 1, id);
            }
        }
    }

    // ===== validación =====
    static String leerNoVacio(String label) throws IOException {
        String s;
        do {
            System.out.print(label);
            s = in.readLine();
            if (s != null) s = s.trim();
        } while (s == null || s.isEmpty());
        return s;
    }

    static String leerConDefault(String label, String actual) throws IOException {
        System.out.print(label);
        String s = in.readLine();
        if (s == null || s.trim().isEmpty()) return actual;
        return s.trim();
    }

    static int leerEntero(String label) throws IOException {
        while (true) {
            try {
                System.out.print(label);
                return Integer.parseInt(in.readLine().trim());
            } catch (Exception e) {
                System.out.println("Valor inválido. Intente de nuevo.");
            }
        }
    }

    static int leerEnteroRango(String label, int min, int max) throws IOException {
        while (true) {
            int v = leerEntero(label);
            if (v < min || v > max) System.out.println("Fuera de rango [" + min + " - " + max + "].");
            else return v;
        }
    }

    static Integer leerEnteroConDefault(String label, int actual, int min, int max) throws IOException {
        System.out.print(label);
        String s = in.readLine();
        if (s == null || s.trim().isEmpty()) return actual;
        try {
            int v = Integer.parseInt(s.trim());
            if (v < min || v > max) {
                System.out.println("Fuera de rango [" + min + "-" + max + "]. Se mantiene: " + actual);
                return actual;
            }
            return v;
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Se mantiene: " + actual);
            return actual;
        }
    }

    static String leerId(String label) throws IOException {
        while (true) {
            String id = leerNoVacio(label).toUpperCase();
            if (id.matches("[A-Z0-9]+")) return id;
            System.out.println("Solo letras y números.");
        }
    }

    static String leerIdNuevoPelicula() throws IOException {
        while (true) {
            String id = leerId("ID película: ");
            if (indicePelicula(id) == -1) return id;
            System.out.println("ID en uso. Ingrese otro.");
        }
    }

    static String leerIdExistentePelicula(String label) throws IOException {
        while (true) {
            String id = leerId(label);
            if (indicePelicula(id) != -1) return id;
            System.out.println("No existe película con ese ID.");
        }
    }

    static String leerNombre(String label) throws IOException {
        while (true) {
            String n = leerNoVacio(label);
            if (n.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) return n;
            System.out.println("Use solo letras y espacios.");
        }
    }

    static String leerClasificacion() throws IOException {
        while (true) {
            String c = leerNoVacio("Clasificación (ej: G, PG-13, R): ");
            if (c.matches("[A-Za-z0-9+-]+(\\-[A-Za-z0-9+]+)?")) return c;
            System.out.println("Formato inválido.");
        }
    }

    static String validarClasificacion(String raw) {
        return raw.matches("[A-Za-z0-9+-]+(\\-[A-Za-z0-9+]+)?") ? raw : "NR";
    }

    static String leerEmail(String label) throws IOException {
        Pattern p = Pattern.compile("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$");
        while (true) {
            String mail = leerNoVacio(label);
            if (p.matcher(mail).matches()) return mail;
            System.out.println("Email inválido. Intente de nuevo.");
        }
    }

    static Set<Genero> leerGenerosObligatorio() throws IOException {
        while (true) {
            Set<Genero> r = leerGenerosOpcional();
            if (r != null && !r.isEmpty()) return r;
            System.out.println("Debe ingresar al menos un género válido.");
        }
    }

    static Set<Genero> leerGenerosOpcional() throws IOException {
        System.out.print("Géneros: ");
        String linea = in.readLine();
        if (linea == null || linea.trim().isEmpty()) return null;
        Set<Genero> res = new HashSet<>();
        for (String raw : linea.split(",")) {
            String g = raw.trim();
            if (g.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && !contieneGenero(res, g)) {
                res.add(new Genero(g));
            } else if (!g.isEmpty()) {
                System.out.println("Se ignoró género inválido o duplicado: " + g);
            }
        }
        return res.isEmpty() ? null : res;
    }

    static boolean contieneGenero(Set<Genero> set, String nombre) {
        for (Genero g : set) if (g.getNombre().equalsIgnoreCase(nombre)) return true;
        return false;
    }

    // ===== Índices / búsquedas =====
    static int indicePelicula(String idRaw) {
        String id = idRaw.toUpperCase();
        for (int i = 0; i < totalPeliculas; i++) {
            Pelicula p = peliculas[i];
            if (p != null && p.getId() != null && p.getId().equalsIgnoreCase(id)) return i;
        }
        return -1;
    }

    static int indiceAdmin(String idRaw) {
        String id = idRaw.toUpperCase();
        for (int i = 0; i < totalAdmins; i++) {
            Administrador a = admins[i];
            if (a != null && a.getId() != null && a.getId().equalsIgnoreCase(id)) return i;
        }
        return -1;
    }

    static int indiceUsuario(String idRaw) {
        String id = idRaw.toUpperCase();
        for (int i = 0; i < totalUsuarios; i++) {
            User u = usuarios[i];
            if (u != null && u.getId() != null && u.getId().equalsIgnoreCase(id)) return i;
        }
        return -1;
    }

    static boolean yaFavorita(int idxUsuario, String idPeli) {
        int n = totalFavs[idxUsuario];
        for (int i = 0; i < n; i++) {
            if (favoritas[idxUsuario][i].equalsIgnoreCase(idPeli)) return true;
        }
        return false;
    }
}
