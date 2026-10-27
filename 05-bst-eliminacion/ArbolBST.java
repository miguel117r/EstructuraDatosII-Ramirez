import java.util.ArrayList;
import java.util.List;

/**
 * Árbol Binario de Búsqueda (BST): inserción, búsqueda y ELIMINACIÓN.
 * Parte del ArbolBST de la Guía 4 y agrega eliminar(), minValor(),
 * maxValor() y esBSTValido().
 *
 * Convención de orden:
 *   - claves MENORES que el nodo  -> subárbol izquierdo
 *   - claves MAYORES O IGUALES    -> subárbol derecho
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

    // ==================================================================
    // INSERCIÓN Y BÚSQUEDA (Guía 4)
    // ==================================================================

    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

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

    public boolean buscar(int valor) {
        return buscarNodo(valor) != null;
    }

    /** Devuelve el nodo que contiene el valor, o null si no existe. */
    public NodoArbol<Integer> buscarNodo(int valor) {
        return buscarNodo(raiz, valor);
    }

    private NodoArbol<Integer> buscarNodo(NodoArbol<Integer> nodo, int valor) {
        if (nodo == null || valor == nodo.getDato()) {
            return nodo;
        }
        return valor < nodo.getDato()
                ? buscarNodo(nodo.getIzquierdo(), valor)
                : buscarNodo(nodo.getDerecho(), valor);
    }

    // ==================================================================
    // ELIMINACIÓN (Guía 5)
    // ==================================================================

    /**
     * Elimina un valor del BST usando el SUCESOR inorden cuando el nodo
     * tiene dos hijos. Si el valor no existe, el árbol no cambia.
     *
     * Se reasigna la raíz porque el nodo eliminado puede ser la raíz.
     */
    public void eliminar(int valor) {
        raiz = eliminarRec(raiz, valor);
    }

    /**
     * @return la nueva raíz del subárbol una vez eliminado el valor.
     *         El padre vuelve a enlazar ese resultado: ahí es donde
     *         cambia realmente la referencia.
     */
    private NodoArbol<Integer> eliminarRec(NodoArbol<Integer> nodo, int valor) {

        // Caso base: llegamos a null, el valor no estaba en el árbol.
        if (nodo == null) {
            return null;
        }

        if (valor < nodo.getDato()) {
            nodo.setIzquierdo(eliminarRec(nodo.getIzquierdo(), valor));

        } else if (valor > nodo.getDato()) {
            nodo.setDerecho(eliminarRec(nodo.getDerecho(), valor));

        } else {
            // ---- Se encontró el nodo a eliminar ----

            // CASOS 1 y 2a: sin hijo izquierdo.
            //   Hoja: devuelve el hijo derecho, que es null -> el padre queda en null.
            //   Solo hijo derecho: el padre pasa a apuntar a ese hijo.
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }

            // CASO 2b: sin hijo derecho (solo hijo izquierdo).
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }

            // CASO 3: dos hijos.
            //   1) sucesor inorden = mínimo del subárbol derecho;
            //   2) se copia su valor en este nodo (este nodo NO se mueve);
            //   3) se elimina el sucesor original del subárbol derecho.
            //      El sucesor no tiene hijo izquierdo, así que su eliminación
            //      cae siempre en el caso 1 o en el caso 2a.
            int sucesor = minValor(nodo.getDerecho());
            nodo.setDato(sucesor);
            nodo.setDerecho(eliminarRec(nodo.getDerecho(), sucesor));
        }
        return nodo;
    }

    /**
     * Variante: igual que eliminar(), pero con dos hijos usa el ANTECESOR
     * inorden (máximo del subárbol izquierdo) en lugar del sucesor.
     */
    public void eliminarConAntecesor(int valor) {
        raiz = eliminarRecAntecesor(raiz, valor);
    }

    private NodoArbol<Integer> eliminarRecAntecesor(NodoArbol<Integer> nodo, int valor) {
        if (nodo == null) {
            return null;
        }
        if (valor < nodo.getDato()) {
            nodo.setIzquierdo(eliminarRecAntecesor(nodo.getIzquierdo(), valor));
        } else if (valor > nodo.getDato()) {
            nodo.setDerecho(eliminarRecAntecesor(nodo.getDerecho(), valor));
        } else {
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            }
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }
            int antecesor = maxValor(nodo.getIzquierdo());
            nodo.setDato(antecesor);
            nodo.setIzquierdo(eliminarRecAntecesor(nodo.getIzquierdo(), antecesor));
        }
        return nodo;
    }

    /** Mínimo de un subárbol: se baja siempre por la izquierda. */
    public int minValor(NodoArbol<Integer> nodo) {
        if (nodo == null) {
            throw new IllegalArgumentException("Un subárbol vacío no tiene mínimo.");
        }
        NodoArbol<Integer> actual = nodo;
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual.getDato();
    }

    /** Máximo de un subárbol: se baja siempre por la derecha. */
    public int maxValor(NodoArbol<Integer> nodo) {
        if (nodo == null) {
            throw new IllegalArgumentException("Un subárbol vacío no tiene máximo.");
        }
        NodoArbol<Integer> actual = nodo;
        while (actual.getDerecho() != null) {
            actual = actual.getDerecho();
        }
        return actual.getDato();
    }

    // ==================================================================
    // VALIDACIÓN
    // ==================================================================

    /**
     * Comprueba la propiedad BST en TODO el árbol, no solo entre padre
     * e hijo: cada nodo debe respetar los límites que le imponen todos
     * sus ancestros (menores a la izquierda, mayores o iguales a la derecha).
     */
    public boolean esBSTValido() {
        return validar(raiz, null, null);
    }

    private boolean validar(NodoArbol<Integer> nodo, Integer minimo, Integer maximoExcl) {
        if (nodo == null) {
            return true;
        }
        int v = nodo.getDato();
        if (minimo != null && v < minimo) {
            return false;
        }
        if (maximoExcl != null && v >= maximoExcl) {
            return false;
        }
        return validar(nodo.getIzquierdo(), minimo, v)
                && validar(nodo.getDerecho(), v, maximoExcl);
    }

    // ==================================================================
    // PROPIEDADES Y RECORRIDOS
    // ==================================================================

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

    /** Inorden: en un BST válido sale ordenado de menor a mayor. */
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

    /** Preorden: junto con el inorden identifica la forma exacta del árbol. */
    public List<Integer> preorden() {
        List<Integer> lista = new ArrayList<>();
        preorden(raiz, lista);
        return lista;
    }

    private void preorden(NodoArbol<Integer> nodo, List<Integer> lista) {
        if (nodo == null) {
            return;
        }
        lista.add(nodo.getDato());
        preorden(nodo.getIzquierdo(), lista);
        preorden(nodo.getDerecho(), lista);
    }

    /** Dibujo lateral: arriba el subárbol derecho, abajo el izquierdo. */
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

    // ==================================================================
    // BÚSQUEDA CON EVIDENCIA (Guía 4)
    // ==================================================================

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
}
