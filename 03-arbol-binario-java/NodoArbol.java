/**
 * Nodo genérico de un árbol binario.
 *
 * Cada nodo guarda un dato de tipo T y dos referencias a otros nodos:
 * una al hijo izquierdo y otra al hijo derecho. Si una referencia vale
 * null, significa que en esa posición no hay ningún nodo.
 *
 * Autor: Miguel Ángel Ramírez Pardo
 */
public class NodoArbol<T> {

    private T dato;
    private NodoArbol<T> izquierdo;
    private NodoArbol<T> derecho;

    public NodoArbol(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public NodoArbol<T> getIzquierdo() {
        return izquierdo;
    }

    public NodoArbol<T> getDerecho() {
        return derecho;
    }

    public void setIzquierdo(NodoArbol<T> n) {
        izquierdo = n;
    }

    public void setDerecho(NodoArbol<T> n) {
        derecho = n;
    }

    /** Un nodo hoja SÍ existe; lo que no tiene son hijos. */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    /** Grado = cantidad de hijos reales (0, 1 o 2 en un árbol binario). */
    public int grado() {
        int hijos = 0;
        if (izquierdo != null) {
            hijos++;
        }
        if (derecho != null) {
            hijos++;
        }
        return hijos;
    }

    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}
