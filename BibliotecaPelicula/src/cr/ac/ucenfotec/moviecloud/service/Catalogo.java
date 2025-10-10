package cr.ac.ucenfotec.moviecloud.service;

import cr.ac.ucenfotec.moviecloud.model.Director;
import cr.ac.ucenfotec.moviecloud.model.Genero;
import cr.ac.ucenfotec.moviecloud.model.Pelicula;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Catalogo {
    private final Pelicula[] peliculas;
    private int total = 0;

    public Catalogo(int max) {
        this.peliculas = new Pelicula[max];
    }

    public boolean add(Pelicula p) {
        if (total >= peliculas.length) return false;
        if (findIndex(p.getId()) != -1) return false;
        peliculas[total++] = p;
        return true;
    }

    public int size() { return total; }
    public Pelicula at(int i) { return (i>=0 && i<total) ? peliculas[i] : null; }

    public int findIndex(String idRaw) {
        if (idRaw == null) return -1;
        String id = idRaw.toUpperCase();
        for (int i=0;i<total;i++) {
            Pelicula p = peliculas[i];
            if (p != null && p.getId() != null && p.getId().equalsIgnoreCase(id)) return i;
        }
        return -1;
    }

    public Pelicula getById(String id) {
        int i = findIndex(id);
        return (i == -1) ? null : peliculas[i];
    }

    public boolean remove(String id) {
        int idx = findIndex(id);
        if (idx == -1) return false;
        peliculas[idx] = peliculas[total - 1];
        peliculas[total - 1] = null;
        total--;
        return true;
    }

    public boolean updateCore(String id, String titulo, Integer anio, Integer dur, String clas) {
        Pelicula p = getById(id);
        if (p == null) return false;
        if (titulo != null) p.setTitulo(titulo);
        if (anio != null) p.setAnio(anio);
        if (dur != null) p.setDuracionMinutos(dur);
        if (clas != null) p.setClasificacion(clas);
        return true;
    }

    public void replaceGeneros(Pelicula p, Set<Genero> nuevos) { p.setGeneros(nuevos); }
    public void addGeneros(Pelicula p, Set<Genero> gset) {
        if (p.getGeneros() == null) p.setGeneros(new HashSet<>());
        for (Genero g: gset) {
            boolean exists = p.getGeneros().stream().anyMatch(x -> x.getNombre().equalsIgnoreCase(g.getNombre()));
            if (!exists) p.getGeneros().add(g);
        }
    }
    public boolean removeGeneroByName(Pelicula p, String nombre) {
        if (p.getGeneros() == null) return false;
        Genero target = null;
        for (Genero g : p.getGeneros()) {
            if (g.getNombre().equalsIgnoreCase(nombre)) { target = g; break; }
        }
        if (target == null) return false;
        p.getGeneros().remove(target);
        return true;
    }

    // small render helper
    public String renderLine(Pelicula p) {
        String dir = (p.getDirector() == null) ? "N/A" : p.getDirector().getNombre();
        String gs = (p.getGeneros() == null || p.getGeneros().isEmpty())
                ? "Sin géneros"
                : p.getGeneros().stream().map(Genero::getNombre).collect(Collectors.joining(", "));
        return String.format("%s | %s (%d) | %d min | %s | Dir: %s | %s",
                p.getId(), p.getTitulo(), p.getAnio(), p.getDuracionMinutos(), p.getClasificacion(), dir, gs);
    }

    public void setDirector(Pelicula p, Director d) {
        p.setDirector(d);
        if (d.getPeliculasDirigidas() == null) d.setPeliculasDirigidas(new HashSet<>());
        d.getPeliculasDirigidas().add(p);
    }
}
