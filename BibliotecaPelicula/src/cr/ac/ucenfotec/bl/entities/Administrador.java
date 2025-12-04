package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Administrador extends Cuenta {

    private List<String> privilegios;

    // Constructor por defecto
    public Administrador() {
        super();
        this.privilegios = new ArrayList<>();
    }

    // Constructor completo
    public Administrador(String id, String username, String email, List<String> privilegios) {
        super(id, username, email);
        this.privilegios = (privilegios != null) ? privilegios : new ArrayList<>();
    }

    // ----- MÉTODOS DE COMPORTAMIENTO -----
    public void agregarPrivilegio(String privilegio) {
        if (privilegio != null && !privilegios.contains(privilegio)) {
            privilegios.add(privilegio);
        }
    }

    public boolean removerPrivilegio(String privilegio) {
        return privilegios.remove(privilegio);
    }

    public boolean tienePrivilegio(String privilegio) {
        return privilegios.contains(privilegio);
    }

    // ----- GETTERS & SETTERS -----
    public List<String> getPrivilegios() { return privilegios; }
    public void setPrivilegios(List<String> privilegios) {
        this.privilegios = (privilegios != null) ? privilegios : new ArrayList<>();
    }

    // ----- MÉTODOS UTILITARIOS -----
    @Override
    public String toString() {
        return "Administrador{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", privilegios=" + privilegios +
                '}';
    }

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
