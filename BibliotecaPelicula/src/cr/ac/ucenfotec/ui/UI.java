package cr.ac.ucenfotec.ui;

import cr.ac.ucenfotec.bl.entities.Genero;
import cr.ac.ucenfotec.bl.entities.Pelicula;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UI {

    private final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // ===== Menú principal =====
    public int mostrarMenuPrincipal(String admin, String user) throws IOException {
        System.out.println("\n===== MOVIECLOUD =====");
        System.out.println("Sesión Admin: " + (admin == null ? "Ninguna" : admin));
        System.out.println("Sesión Usuario: " + (user == null ? "Ninguna" : user));
        System.out.println("----------------------------------");
        System.out.println("1. Registrar administrador");
        System.out.println("2. Registrar usuario");
        System.out.println("3. Iniciar sesión (administrador)");
        System.out.println("4. Iniciar sesión (usuario)");
        System.out.println("5. Operar como administrador");
        System.out.println("6. Operar como usuario");
        System.out.println("0. Salir");
        return leerEntero("Opción: ");
    }

    public int mostrarMenuAdmin(String adminName) throws IOException {
        System.out.println("\n===== Catálogo (Administrador) =====");
        System.out.println("Admin: " + adminName);
        System.out.println("----------------------------------");
        System.out.println("1. Crear película");
        System.out.println("2. Listar películas");
        System.out.println("3. Buscar película por ID");
        System.out.println("4. Actualizar película");
        System.out.println("5. Eliminar película");
        System.out.println("0. Volver");
        return leerEntero("Opción: ");
    }

    public int mostrarMenuUsuario(String userName) throws IOException {
        System.out.println("\n===== Catálogo (Usuario) =====");
        System.out.println("Usuario: " + userName);
        System.out.println("----------------------------------");
        System.out.println("1. Listar películas");
        System.out.println("2. Agregar a favoritas");
        System.out.println("3. Ver mis favoritas");
        System.out.println("0. Volver");
        return leerEntero("Opción: ");
    }

    // ===== IO genérico =====
    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    public String leerTextoObligatorio(String label) throws IOException {
        String s;
        do {
            System.out.print(label);
            s = in.readLine();
            if (s != null) s = s.trim();
        } while (s == null || s.isEmpty());
        return s;
    }

    public int leerEntero(String label) throws IOException {
        while (true) {
            try {
                System.out.print(label);
                return Integer.parseInt(in.readLine().trim());
            } catch (Exception e) {
                System.out.println("Valor inválido. Intente de nuevo.");
            }
        }
    }

    public String leerId(String label) throws IOException {
        while (true) {
            String id = leerTextoObligatorio(label).toUpperCase();
            if (id.matches("[A-Z0-9]+")) return id;
            System.out.println("Solo se permiten letras y números.");
        }
    }

    public String leerEmail(String label) throws IOException {
        while (true) {
            String email = leerTextoObligatorio(label);
            if (email.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) return email;
            System.out.println("Email inválido. Intente de nuevo.");
        }
    }

    public String leerClasificacion() throws IOException {
        while (true) {
            String c = leerTextoObligatorio("Clasificación (ej: G, PG-13, R): ");
            if (c.matches("[A-Za-z0-9+\\-]+")) return c;
            System.out.println("Formato de clasificación inválido.");
        }
    }

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

    // ===== Mostrar películas =====
    public void mostrarPeliculas(List<Pelicula> peliculas) {
        if (peliculas == null || peliculas.isEmpty()) {
            System.out.println("No hay películas registradas.");
            return;
        }
        System.out.println("\n--- LISTA DE PELÍCULAS ---");
        int i = 1;
        for (Pelicula p : peliculas) {
            String dir = (p.getDirector() == null) ? "N/A" : p.getDirector().getNombre();
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
            System.out.printf("%d) %s | %s (%d) | %d min | %s | Dir: %s | %s%n",
                    i++, p.getId(), p.getTitulo(), p.getAnio(),
                    p.getDuracionMinutos(), p.getClasificacion(), dir, gs);
        }
    }

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
        }
        if (p.getGeneros() != null && !p.getGeneros().isEmpty()) {
            System.out.print("Géneros: ");
            for (int i = 0; i < p.getGeneros().size(); i++) {
                System.out.print(p.getGeneros().get(i).getNombre());
                if (i < p.getGeneros().size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
        if (p.getFicha() != null) {
            System.out.println("Sinopsis: " + p.getFicha().sinopsis());
        }
    }
}
