import java.util.ArrayList;
import java.util.List;

/**
 * Nodo de un árbol general que representa un elemento del sistema de archivos.
 * Una carpeta puede tener hijos; un archivo es una hoja (no tiene hijos).
 */
public class Nodo {
    private final String nombre;
    private final boolean esCarpeta;
    private final List<Nodo> hijos = new ArrayList<>();

    public Nodo(String nombre, boolean esCarpeta) {
        this.nombre = nombre;
        this.esCarpeta = esCarpeta;
    }

    public void agregar(Nodo hijo) {
        if (!esCarpeta) {
            throw new IllegalStateException("Un archivo no puede contener elementos: " + nombre);
        }
        hijos.add(hijo);
    }

    public String getNombre() { return nombre; }

    public boolean esCarpeta() { return esCarpeta; }

    public List<Nodo> getHijos() { return hijos; }

    public boolean esHoja() { return hijos.isEmpty(); }
}
