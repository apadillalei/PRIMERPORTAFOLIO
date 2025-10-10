package cr.ac.ucenfotec.moviecloud.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu {

    private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // ===== Menú principal (autenticación) =====
    public static int menuPrincipal(String admin, String user) throws IOException {
        System.out.println("\n===== MOVIECLOUD =====");
        System.out.println("Sesión Admin: " + (admin == null ? "Ninguna" : admin));
        System.out.println("Sesión Usuario: " + (user == null ? "Ninguna" : user));
        System.out.println("----------------------------------");
        System.out.println("1. Registrar administrador");
        System.out.println("2. Registrar usuario");
        System.out.println("3. Iniciar sesión (administrador)");
        System.out.println("4. Iniciar sesión (usuario)");
        System.out.println("5. Elegir rol y operar");
        System.out.println("0. Salir");
        return leerEntero("Opción: ");
    }

    // ===== Menú de selección de rol =====
    public static int menuRol() throws IOException {
        System.out.println("\n===== Selección de rol =====");
        System.out.println("1. Administrador");
        System.out.println("2. Usuario normal");
        System.out.println("0. Volver");
        return leerEntero("Opción: ");
    }

    // ===== Menú del administrador =====
    public static int menuAdmin(String adminName) throws IOException {
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

    // ===== Menú del usuario normal =====
    public static int menuUsuario(String userName) throws IOException {
        System.out.println("\n===== Catálogo (Usuario) =====");
        System.out.println("Usuario: " + userName);
        System.out.println("----------------------------------");
        System.out.println("1. Listar películas");
        System.out.println("2. Guardar en favoritas");
        System.out.println("3. Ver mis favoritas");
        System.out.println("0. Volver");
        return leerEntero("Opción: ");
    }

    // ===== Metodo auxiliar
    private static int leerEntero(String label) throws IOException {
        while (true) {
            try {
                System.out.print(label);
                return Integer.parseInt(in.readLine().trim());
            } catch (Exception e) {
                System.out.println("Valor inválido. Intente de nuevo.");
            }
        }
    }
}
