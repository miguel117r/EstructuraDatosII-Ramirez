import java.util.ArrayList;
import java.util.List;

/**
 * Árbol Binario de Búsqueda (BST) con inserción y búsqueda recursivas.
 *
 * Convención de orden (la de la guía):
 *   - claves MENORES que el nodo  -> subárbol izquierdo
 *   - claves MAYORES O IGUALES    -> subárbol derecho
 *
 * "Comparación" = cada nodo visitado durante una búsqueda, es decir,
 * cada vez que se compara la clave buscada con el dato de un nodo.
 *
 * Autor: Miguel Ángel Ramírez Pardo
 */
public class ArbolBST {

    private NodoArbol<Integer> raiz;

    public NodoArbol<Integer> getRaiz() {
        return raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    // ------------------------------------------------------------------
    // Inserción
    // ------------------------------------------------------------------

    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    /**
     * Caso base: se llegó a una referencia null -> aquí va el nodo nuevo.
     * Caso recursivo: se baja por la izquierda o la derecha según el orden
     * y se vuelve a enlazar el resultado para no perder la estructura.
     */
    private NodoArbol<Integer> insertarRec(NodoArbol<Integer> nodo, int valor) {
        if (nodo == null) {
            return new NodoArbol<>(valor);
        }
        if (valor < nodo.getDato()) {
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), valor));
        } else {
            nodo.setDerecho(insertarRec(nodo.getDerecho(), valor));
        }
        return nodo;
    }

    // ------------------------------------------------------------------
    // Búsqueda
    // ------------------------------------------------------------------

    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    private boolean buscarRec(NodoArbol<Integer> nodo, int valor) {
        if (nodo == null) {
            return false; // se llegó al final del camino sin encontrarlo
        }
        if (valor == nodo.getDato()) {
            return true;
        }
        return valor < nodo.getDato()
                ? buscarRec(nodo.getIzquierdo(), valor)
                : buscarRec(nodo.getDerecho(), valor);
    }

    /** Búsqueda que además registra el camino y las comparaciones. */
    public ResultadoBusqueda buscarConCamino(int valor) {
        return buscarConCamino(raiz, valor, 0, "");
    }

    private ResultadoBusqueda buscarConCamino(NodoArbol<Integer> nodo, int valor,
                                              int visitados, String recorrido) {
        if (nodo == null) {
            return new ResultadoBusqueda(valor, false, visitados, recorrido + "null");
        }
        visitados++;
        int actual = nodo.getDato();
        String ruta = recorrido + actual;

        if (valor == actual) {
            return new ResultadoBusqueda(valor, true, visitados, ruta);
        }
        if (valor < actual) {
            return buscarConCamino(nodo.getIzquierdo(), valor, visitados, ruta + " -> ");
        }
        return buscarConCamino(nodo.getDerecho(), valor, visitados, ruta + " -> ");
    }

    /** Solo cuenta las comparaciones (sin armar el texto del camino). */
    public int comparaciones(int valor) {
        return comparaciones(raiz, valor);
    }

    private int comparaciones(NodoArbol<Integer> nodo, int valor) {
        if (nodo == null) {
            return 0;
        }
        int actual = nodo.getDato();
        if (valor == actual) {
            return 1;
        }
        return 1 + (valor < actual
                ? comparaciones(nodo.getIzquierdo(), valor)
                : comparaciones(nodo.getDerecho(), valor));
    }

    // ------------------------------------------------------------------
    // Propiedades y recorridos
    // ------------------------------------------------------------------

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(NodoArbol<Integer> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    /** Altura en aristas: árbol vacío = -1, un solo nodo = 0. */
    public int altura() {
        return altura(raiz);
    }

    private int altura(NodoArbol<Integer> nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    /** Inorden (izquierda, nodo, derecha): en un BST sale ordenado. */
    public List<Integer> inorden() {
        List<Integer> lista = new ArrayList<>();
        inorden(raiz, lista);
        return lista;
    }

    private void inorden(NodoArbol<Integer> nodo, List<Integer> lista) {
        if (nodo == null) {
            return;
        }
        inorden(nodo.getIzquierdo(), lista);
        lista.add(nodo.getDato());
        inorden(nodo.getDerecho(), lista);
    }

    /**
     * Dibujo lateral: el árbol "girado" 90° a la izquierda.
     * Arriba queda el subárbol derecho, abajo el izquierdo.
     */
    public void imprimirLateral() {
        if (raiz == null) {
            System.out.println("(árbol vacío)");
            return;
        }
        imprimirLateral(raiz, 0);
    }

    private void imprimirLateral(NodoArbol<Integer> nodo, int nivel) {
        if (nodo == null) {
            return;
        }
        imprimirLateral(nodo.getDerecho(), nivel + 1);
        System.out.println("      ".repeat(nivel) + nodo.getDato());
        imprimirLateral(nodo.getIzquierdo(), nivel + 1);
    }
}
