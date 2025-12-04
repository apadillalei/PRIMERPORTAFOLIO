package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa a un administrador del sistema.
 * Hereda de {@link Cuenta} y agrega una lista de privilegios especiales.
 */
public class Administrador extends Cuenta {

    private List<String> privilegios;

    /**
     * Constructor por defecto.
     * Inicializa la lista de privilegios vacía.
     */
    public Administrador() {
        super();
        this.privilegios = new ArrayList<>();
    }

    /**
     * Constructor completo.
     *
     * @param id          identificador único de la cuenta
     * @param username    nombre de usuario
     * @param email       correo electrónico
     * @param privilegios lista de privilegios; si es null, se inicializa vacía
     */
    public Administrador(String id, String username, String email, List<String> privilegios) {
        super(id, username, email);
        this.privilegios = (privilegios != null) ? privilegios : new ArrayList<>();
    }

    /**
     * Agrega un privilegio al administrador si no existe ya.
     *
     * @param privilegio nombre del privilegio a agregar
     */
    public void agregarPrivilegio(String privilegio) {
        if (privilegio != null && !privilegios.contains(privilegio)) {
            privilegios.add(privilegio);
        }
    }

    /**
     * Elimina un privilegio del administrador.
     *
     * @param privilegio privilegio a eliminar
     * @return true si se eliminó, false en caso contrario
     */
    public boolean removerPrivilegio(String privilegio) {
        return privilegios.remove(privilegio);
    }

    /**
     * Verifica si el administrador posee un privilegio específico.
     *
     * @param privilegio privilegio a consultar
     * @return true si lo tiene, false en caso contrario
     */
    public boolean tienePrivilegio(String privilegio) {
        return privilegios.contains(privilegio);
    }

    // GETTERS & SETTERS

    public List<String> getPrivilegios() { return privilegios; }

    public void setPrivilegios(List<String> privilegios) {
        this.privilegios = (privilegios != null) ? privilegios : new ArrayList<>();
    }

    // Métodos utilitarios

    @Override
    public String getTipoCuenta() {
        return "Administrador";
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", privilegios=" + privilegios +
                '}';
    }

    /**
     * Dos administradores se consideran iguales si comparten el mismo id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Administrador)) return false;
        Administrador that = (Administrador) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
