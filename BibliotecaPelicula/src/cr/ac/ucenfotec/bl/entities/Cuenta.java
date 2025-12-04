package cr.ac.ucenfotec.bl.entities;

/**
 * Clase base abstracta para las cuentas del sistema
 * (usuarios normales y administradores).
 *
 * Demuestra abstracción y herencia dentro del modelo.
 */
public abstract class Cuenta {

    protected String id;
    protected String username;
    protected String email;

    /**
     * Constructor por defecto requerido por el portafolio.
     */
    public Cuenta() {
    }

    /**
     * Constructor completo.
     *
     * @param id       identificador único de la cuenta
     * @param username nombre de usuario
     * @param email    correo electrónico
     */
    public Cuenta(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    /**
     * Devuelve el tipo de cuenta concreto
     * (por ejemplo, "Usuario" o "Administrador").
     *
     * @return tipo de cuenta en formato texto
     */
    public abstract String getTipoCuenta();

    // GETTERS & SETTERS

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return getTipoCuenta() + "{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
