import java.util.LinkedList;
import java.util.Queue;

/**
 * Árbol binario que guarda solo la referencia a la raíz.
 * Todo el resto de la estructura se alcanza a través de las
 * referencias izquierda/derecha de cada NodoArbol.
 *
 * Autor: Miguel Ángel Ramírez Pardo
 */
public class ArbolBinario<T> {

    private NodoArbol<T> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    public ArbolBinario(NodoArbol<T> raiz) {
        this.raiz = raiz;
    }

    public NodoArbol<T> getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoArbol<T> raiz) {
        this.raiz = raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    // ------------------------------------------------------------------
    // Impresión
    // ------------------------------------------------------------------

    /**
     * Método pedido por la guía: muestra la raíz y sus dos hijos directos.
     * Cada acceso a un hijo se valida antes para evitar NullPointerException.
     */
    public void imprimirRaizYHijos() {
        if (raiz == null) {
            System.out.println("El árbol está vacío (la raíz es null).");
            return;
        }
        System.out.println("Raíz: " + raiz.getDato());
        System.out.println("Hijo izquierdo: " + textoHijo(raiz.getIzquierdo()));
        System.out.println("Hijo derecho: " + textoHijo(raiz.getDerecho()));
    }

    private String textoHijo(NodoArbol<T> hijo) {
        if (hijo == null) {
            return "null (no hay nodo)";
        }
        return String.valueOf(hijo.getDato());
    }

    /** Imprime todo el árbol con sangría según el nivel (recursivo). */
    public void imprimirArbol() {
        if (raiz == null) {
            System.out.println("El árbol está vacío (la raíz es null).");
            return;
        }
        imprimir(raiz, 0, "RAÍZ");
    }

    private void imprimir(NodoArbol<T> nodo, int nivel, String lado) {
        if (nodo == null) {
            return; // caso base: no hay nada que imprimir
        }
        System.out.println("   ".repeat(nivel) + lado + " -> " + nodo.getDato());
        imprimir(nodo.getIzquierdo(), nivel + 1, "IZQ");
        imprimir(nodo.getDerecho(), nivel + 1, "DER");
    }

    // ------------------------------------------------------------------
    // Propiedades
    // ------------------------------------------------------------------

    public int contarNodos() {
        return contarNodos(raiz);
    }

    private int contarNodos(NodoArbol<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(NodoArbol<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.esHoja()) {
            return 1;
        }
        return contarHojas(nodo.getIzquierdo()) + contarHojas(nodo.getDerecho());
    }

    /** Altura en aristas: árbol vacío = -1, un solo nodo = 0. */
    public int altura() {
        return altura(raiz);
    }

    private int altura(NodoArbol<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    // ------------------------------------------------------------------
    // Clasificación
    // ------------------------------------------------------------------

    /** Lleno: todos los nodos tienen 0 o 2 hijos (nunca exactamente 1). */
    public boolean esLleno() {
        return esLleno(raiz);
    }

    private boolean esLleno(NodoArbol<T> nodo) {
        if (nodo == null) {
            return true;
        }
        if (nodo.grado() == 1) {
            return false;
        }
        return esLleno(nodo.getIzquierdo()) && esLleno(nodo.getDerecho());
    }

    /**
     * Completo: todos los niveles están llenos excepto quizá el último,
     * que se llena de izquierda a derecha. Se revisa por niveles: una vez
     * aparece un hueco (null), ya no puede aparecer ningún nodo después.
     */
    public boolean esCompleto() {
        if (raiz == null) {
            return true;
        }
        Queue<NodoArbol<T>> cola = new LinkedList<>();
        cola.add(raiz);
        boolean huecoVisto = false;
        while (!cola.isEmpty()) {
            NodoArbol<T> actual = cola.poll();
            if (actual == null) {
                huecoVisto = true;
            } else {
                if (huecoVisto) {
                    return false; // nodo después de un hueco
                }
                cola.add(actual.getIzquierdo());
                cola.add(actual.getDerecho());
            }
        }
        return true;
    }

    /** Perfecto: tiene exactamente 2^(h+1) - 1 nodos para su altura h. */
    public boolean esPerfecto() {
        return contarNodos() == (1 << (altura() + 1)) - 1;
    }

    /** Degenerado: ningún nodo tiene dos hijos (forma de lista enlazada). */
    public boolean esDegenerado() {
        return esDegenerado(raiz);
    }

    private boolean esDegenerado(NodoArbol<T> nodo) {
        if (nodo == null) {
            return true;
        }
        if (nodo.grado() == 2) {
            return false;
        }
        return esDegenerado(nodo.getIzquierdo()) && esDegenerado(nodo.getDerecho());
    }
}
