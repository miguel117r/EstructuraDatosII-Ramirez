/**
 * Ejercicio 1 de la guía: árbol de expresiones con operadores y operandos.
 *
 * Los operadores son nodos internos y los números son hojas.
 *
 *            +
 *          /   \
 *         *     /
 *        / \   / \
 *       -   4 6   3
 *      / \
 *     8   2
 *
 * Expresión: (8 - 2) * 4 + 6 / 3
 *
 * Autor: Miguel Ángel Ramírez Pardo
 */
public class ArbolExpresion {

    public static void main(String[] args) {

        NodoArbol<String> suma = new NodoArbol<>("+");
        NodoArbol<String> producto = new NodoArbol<>("*");
        NodoArbol<String> division = new NodoArbol<>("/");
        NodoArbol<String> resta = new NodoArbol<>("-");

        NodoArbol<String> ocho = new NodoArbol<>("8");
        NodoArbol<String> dos = new NodoArbol<>("2");
        NodoArbol<String> cuatro = new NodoArbol<>("4");
        NodoArbol<String> seis = new NodoArbol<>("6");
        NodoArbol<String> tres = new NodoArbol<>("3");

        suma.setIzquierdo(producto);
        suma.setDerecho(division);

        producto.setIzquierdo(resta);
        producto.setDerecho(cuatro);

        resta.setIzquierdo(ocho);
        resta.setDerecho(dos);

        division.setIzquierdo(seis);
        division.setDerecho(tres);

        ArbolBinario<String> arbol = new ArbolBinario<>(suma);

        System.out.println("=== ÁRBOL DE EXPRESIÓN ===");
        arbol.imprimirArbol();

        System.out.println("\nNodos: " + arbol.contarNodos()
                + " | Hojas (operandos): " + arbol.contarHojas()
                + " | Altura: " + arbol.altura());

        System.out.println("\nResultado de (8 - 2) * 4 + 6 / 3 = " + evaluar(suma));
    }

    /**
     * Evalúa el árbol de forma recursiva (recorrido posorden):
     * primero se evalúan los dos hijos y luego se aplica el operador.
     */
    public static int evaluar(NodoArbol<String> nodo) {
        if (nodo == null) {
            throw new IllegalArgumentException("No se puede evaluar una referencia null.");
        }

        // Una hoja es un operando: se convierte a número.
        if (nodo.esHoja()) {
            return Integer.parseInt(nodo.getDato());
        }

        // Un operador necesita sus dos operandos.
        if (nodo.getIzquierdo() == null || nodo.getDerecho() == null) {
            throw new IllegalStateException("El operador '" + nodo.getDato() + "' necesita dos operandos.");
        }

        int izq = evaluar(nodo.getIzquierdo());
        int der = evaluar(nodo.getDerecho());

        switch (nodo.getDato()) {
            case "+":
                return izq + der;
            case "-":
                return izq - der;
            case "*":
                return izq * der;
            case "/":
                if (der == 0) {
                    throw new ArithmeticException("División entre cero.");
                }
                return izq / der;
            default:
                throw new IllegalArgumentException("Operador no válido: " + nodo.getDato());
        }
    }
}
