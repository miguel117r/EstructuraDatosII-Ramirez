/**
 * Nodo genérico de un árbol binario (el mismo de la Guía 3).
 * En el BST se usa como NodoArbol<Integer>.
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

    /** Un nodo hoja existe, pero no tiene hijos. */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}
