package cr.ac.ucenfotec.bl.entities;

import java.util.ArrayList;

public class User extends Cuenta {

    private ArrayList<Pelicula> favoritos;

    // Constructor por defecto
    public User() {
        super();
        this.favoritos = new ArrayList<>();
    }

    // Constructor completo
    public User(String id, String username, String email, ArrayList<Pelicula> favoritos) {
        super(id, username, email);
        this.favoritos = (favoritos != null) ? favoritos : new ArrayList<>();
    }

    // Constructor simplificado
    public User(String id, String username, String email) {
        this(id, username, email, new ArrayList<>());
    }

    // ===== FAVORITOS =====
    public boolean agregarFavorito(Pelicula p) {
        if (p == null) return false;
        if (!favoritos.contains(p)) return favoritos.add(p);
        return false;
    }

    public boolean eliminarFavorito(Pelicula p) {
        return favoritos.remove(p);
    }

    public ArrayList<Pelicula> getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(ArrayList<Pelicula> favoritos) {
        this.favoritos = (favoritos != null) ? favoritos : new ArrayList<>();
    }
}
