package cr.ac.ucenfotec.bl.entities;

/**
 * Clase base para cuentas del sistema (usuario normal y administrador).
 * Demuestra abstracción y herencia.
 */
public abstract class Cuenta {
    protected String id;
    protected String username;
    protected String email;

    // Constructor por defecto
    public Cuenta() {
    }

    // Constructor completo
    public Cuenta(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // GETTERS & SETTERS
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
