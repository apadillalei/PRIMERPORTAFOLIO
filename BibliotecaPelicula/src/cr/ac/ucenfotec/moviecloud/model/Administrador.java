package cr.ac.ucenfotec.moviecloud.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Administrador {
    private String id;
    private String username;
    private String email;
    private List<String> privilegios;

    // ----- CONSTRUCTOR -----
    public Administrador(String id, String username, String email, List<String> privilegios) {
        this.id = id;
        this.username = username;
        this.email = email;
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
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getPrivilegios() { return privilegios; }
    public void setPrivilegios(List<String> privilegios) { this.privilegios = privilegios; }


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
