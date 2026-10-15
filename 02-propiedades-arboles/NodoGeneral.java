import java.util.ArrayList;
import java.util.List;

/**
 * Nodo de un árbol general (cada nodo puede tener cualquier número de hijos).
 * Guarda una referencia a su padre para poder calcular la profundidad.
 */
public class NodoGeneral<T> {
    private final T dato;
    private NodoGeneral<T> padre;
    private final List<NodoGeneral<T>> hijos = new ArrayList<>();

    public NodoGeneral(T dato) {
        this.dato = dato;
    }

    /** Crea un hijo con el dato indicado, lo enlaza a este nodo y lo devuelve. */
    public NodoGeneral<T> agregarHijo(T datoHijo) {
        NodoGeneral<T> hijo = new NodoGeneral<>(datoHijo);
        hijo.padre = this;
        hijos.add(hijo);
        return hijo;
    }

    public T getDato() { return dato; }

    public NodoGeneral<T> getPadre() { return padre; }

    public List<NodoGeneral<T>> getHijos() { return hijos; }

    public boolean esRaiz() { return padre == null; }

    public boolean esHoja() { return hijos.isEmpty(); }

    /** Grado de un nodo = número de hijos. */
    public int grado() { return hijos.size(); }

    /** Profundidad = número de aristas desde la raíz hasta este nodo. */
    public int profundidad() {
        int p = 0;
        NodoGeneral<T> actual = this;
        while (actual.padre != null) {
            p++;
            actual = actual.padre;
        }
        return p;
    }

    @Override
    public String toString() { return String.valueOf(dato); }
}
