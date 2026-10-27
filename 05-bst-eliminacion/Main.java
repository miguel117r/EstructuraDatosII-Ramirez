import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Guía 5. BST II: eliminación de nodos.
 *
 * Se parte del BST de 15 códigos de producto de la Guía 4 (orden aleatorio)
 * y, sobre una copia nueva del árbol para cada prueba, se eliminan:
 *   1) una hoja,
 *   2) un nodo con un hijo (derecho y también izquierdo),
 *   3) un nodo con dos hijos (con sucesor y con antecesor),
 *   4) la raíz.
 * Después de cada eliminación se valida que el árbol siga ordenado.
 *
 * Autor: Miguel Ángel Ramírez Pardo
 */
public class Main {

    // Los 15 códigos de la Guía 4, en el mismo orden de inserción.
    private static final int[] CODIGOS = {
        4521, 2308, 6745, 1190, 3876,
        8012, 5467, 2951, 7230, 1604,
        9183, 4017, 6392, 3145, 8750
    };

    private static int totalPruebas = 0;
    private static int pruebasFallidas = 0;

    public static void main(String[] args) {

        // ----------------------------------------------------------
        // ÁRBOL BASE
        // ----------------------------------------------------------
        titulo("ÁRBOL BASE (el BST de la Guía 4)");
        ArbolBST base = crearBase();
        base.imprimirLateral();
        System.out.println("\nInorden:  " + base.inorden());
        System.out.println("Preorden: " + base.preorden());
        System.out.println("Nodos: " + base.contarNodos() + " | Altura: " + base.altura());
        verificar("el árbol base es un BST válido", base.esBSTValido());

        // ----------------------------------------------------------
        // CASO 1: HOJA
        // ----------------------------------------------------------
        titulo("CASO 1: ELIMINAR UNA HOJA (3145)");
        ArbolBST a1 = crearBase();
        NodoArbol<Integer> padre1 = a1.buscarNodo(2951);
        mostrarAntes(a1);
        System.out.println("\nReferencia ANTES: hijo derecho de 2951 = " + padre1.getDerecho());
        a1.eliminar(3145);
        mostrarDespues(a1);
        System.out.println("\nReferencia DESPUÉS: hijo derecho de 2951 = " + padre1.getDerecho());
        verificar("3145 ya no existe", !a1.buscar(3145));
        verificar("el padre 2951 quedó con referencia derecha null", padre1.getDerecho() == null);
        verificar("quedan 14 nodos", a1.contarNodos() == 14);
        verificar("el árbol sigue siendo un BST válido", a1.esBSTValido());

        // ----------------------------------------------------------
        // CASO 2: UN HIJO
        // ----------------------------------------------------------
        titulo("CASO 2a: NODO CON UN HIJO DERECHO (5467, hijo: 6392)");
        ArbolBST a2 = crearBase();
        NodoArbol<Integer> padre2 = a2.buscarNodo(6745);
        mostrarAntes(a2);
        System.out.println("\nReferencia ANTES: hijo izquierdo de 6745 = " + padre2.getIzquierdo());
        a2.eliminar(5467);
        mostrarDespues(a2);
        System.out.println("\nReferencia DESPUÉS: hijo izquierdo de 6745 = " + padre2.getIzquierdo());
        verificar("5467 ya no existe", !a2.buscar(5467));
        verificar("6392 sigue existiendo", a2.buscar(6392));
        verificar("6745 ahora apunta directo a 6392", padre2.getIzquierdo().getDato() == 6392);
        verificar("quedan 14 nodos", a2.contarNodos() == 14);
        verificar("el árbol sigue siendo un BST válido", a2.esBSTValido());

        titulo("CASO 2b: NODO CON UN HIJO IZQUIERDO (9183, hijo: 8750)");
        ArbolBST a2b = crearBase();
        NodoArbol<Integer> padre2b = a2b.buscarNodo(8012);
        mostrarAntes(a2b);
        System.out.println("\nReferencia ANTES: hijo derecho de 8012 = " + padre2b.getDerecho());
        a2b.eliminar(9183);
        mostrarDespues(a2b);
        System.out.println("\nReferencia DESPUÉS: hijo derecho de 8012 = " + padre2b.getDerecho());
        verificar("9183 ya no existe", !a2b.buscar(9183));
        verificar("8012 ahora apunta directo a 8750", padre2b.getDerecho().getDato() == 8750);
        verificar("el árbol sigue siendo un BST válido", a2b.esBSTValido());

        // ----------------------------------------------------------
        // CASO 3: DOS HIJOS
        // ----------------------------------------------------------
        titulo("CASO 3: NODO CON DOS HIJOS (2308), CON SUCESOR INORDEN");
        ArbolBST a3 = crearBase();
        NodoArbol<Integer> nodo3 = a3.buscarNodo(2308);
        NodoArbol<Integer> tresMil = a3.buscarNodo(3876);
        int sucesor3 = a3.minValor(nodo3.getDerecho());
        int antecesor3 = a3.maxValor(nodo3.getIzquierdo());
        mostrarAntes(a3);
        System.out.println("\nSucesor inorden de 2308 (mínimo del subárbol derecho): " + sucesor3);
        System.out.println("Antecesor inorden de 2308 (máximo del subárbol izquierdo): " + antecesor3);
        System.out.println("Hijo izquierdo de 3876 ANTES = " + tresMil.getIzquierdo());
        a3.eliminar(2308);
        mostrarDespues(a3);
        System.out.println("\nEl nodo que era 2308 ahora guarda: " + nodo3.getDato()
                + " (hijos: izq=" + nodo3.getIzquierdo() + ", der=" + nodo3.getDerecho() + ")");
        System.out.println("Hijo izquierdo de 3876 DESPUÉS = " + tresMil.getIzquierdo());
        verificar("2308 ya no existe", !a3.buscar(2308));
        verificar("el nodo ahora guarda el sucesor 2951", nodo3.getDato() == 2951);
        verificar("el sucesor no quedó duplicado", contar(a3, 2951) == 1);
        verificar("3876 heredó el hijo 3145 del sucesor", tresMil.getIzquierdo().getDato() == 3145);
        verificar("quedan 14 nodos", a3.contarNodos() == 14);
        verificar("el árbol sigue siendo un BST válido", a3.esBSTValido());

        titulo("CASO 3 (variante): MISMO NODO 2308, CON ANTECESOR INORDEN");
        ArbolBST a3b = crearBase();
        NodoArbol<Integer> nodo3b = a3b.buscarNodo(2308);
        NodoArbol<Integer> n1190 = a3b.buscarNodo(1190);
        System.out.println("Hijo derecho de 1190 ANTES = " + n1190.getDerecho());
        a3b.eliminarConAntecesor(2308);
        mostrarDespues(a3b);
        System.out.println("\nEl nodo que era 2308 ahora guarda: " + nodo3b.getDato());
        System.out.println("Hijo derecho de 1190 DESPUÉS = " + n1190.getDerecho());
        verificar("el nodo ahora guarda el antecesor 1604", nodo3b.getDato() == 1604);
        verificar("1190 quedó sin hijo derecho", n1190.getDerecho() == null);
        verificar("el árbol sigue siendo un BST válido", a3b.esBSTValido());

        // ----------------------------------------------------------
        // EJERCICIO: ELIMINAR LA RAÍZ
        // ----------------------------------------------------------
        titulo("EJERCICIO: ELIMINAR LA RAÍZ (4521)");
        ArbolBST a4 = crearBase();
        NodoArbol<Integer> padre4 = a4.buscarNodo(6745);
        mostrarAntes(a4);
        System.out.println("\nSucesor inorden de la raíz: " + a4.minValor(a4.getRaiz().getDerecho()));
        a4.eliminar(4521);
        mostrarDespues(a4);
        System.out.println("\nNueva raíz: " + a4.getRaiz().getDato()
                + " | Hijo izquierdo de 6745 ahora = " + padre4.getIzquierdo());
        verificar("4521 ya no existe", !a4.buscar(4521));
        verificar("la nueva raíz es el sucesor 5467", a4.getRaiz().getDato() == 5467);
        verificar("6745 ahora apunta a 6392", padre4.getIzquierdo().getDato() == 6392);
        verificar("el inorden sigue ordenado", estaOrdenada(a4.inorden()));
        verificar("el árbol sigue siendo un BST válido", a4.esBSTValido());

        ArbolBST a4b = crearBase();
        a4b.eliminarConAntecesor(4521);
        System.out.println("\nRaíz eliminada con ANTECESOR -> nueva raíz: " + a4b.getRaiz().getDato());
        verificar("con antecesor la nueva raíz es 4017", a4b.getRaiz().getDato() == 4017);
        verificar("con antecesor el árbol también es un BST válido", a4b.esBSTValido());

        // ----------------------------------------------------------
        // PRUEBAS ADICIONALES
        // ----------------------------------------------------------
        titulo("PRUEBAS ADICIONALES");

        ArbolBST inex = crearBase();
        inex.eliminar(9999);
        verificar("eliminar un valor inexistente no cambia el árbol", inex.contarNodos() == 15 && inex.esBSTValido());

        ArbolBST vacio = new ArbolBST();
        vacio.eliminar(10);
        verificar("eliminar en un árbol vacío no falla", vacio.estaVacio());

        ArbolBST todos = crearBase();
        boolean validoSiempre = true;
        for (int i = 0; i < CODIGOS.length; i++) {
            todos.eliminar(CODIGOS[i]);
            if (!todos.esBSTValido() || todos.contarNodos() != CODIGOS.length - i - 1) {
                validoSiempre = false;
            }
        }
        verificar("eliminar los 15 códigos uno por uno deja el árbol vacío y válido", validoSiempre && todos.estaVacio());

        ArbolBST unico = new ArbolBST();
        unico.insertar(7);
        unico.eliminar(7);
        verificar("eliminar el único nodo deja el árbol vacío", unico.estaVacio());

        // 1000 claves, se eliminan 500 al azar (semilla fija).
        List<Integer> claves = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            claves.add(i * 3);
        }
        List<Integer> mezcladas = new ArrayList<>(claves);
        Collections.shuffle(mezcladas, new Random(7));
        ArbolBST grande = new ArbolBST();
        for (int c : mezcladas) {
            grande.insertar(c);
        }
        List<Integer> aEliminar = new ArrayList<>(mezcladas.subList(0, 500));
        List<Integer> restantes = new ArrayList<>(claves);
        restantes.removeAll(aEliminar);
        for (int c : aEliminar) {
            grande.eliminar(c);
        }
        verificar("1000 claves, 500 eliminadas al azar: quedan 500", grande.contarNodos() == 500);
        verificar("... el árbol sigue siendo un BST válido", grande.esBSTValido());
        verificar("... el inorden coincide exactamente con las claves restantes", grande.inorden().equals(restantes));

        // ----------------------------------------------------------
        // RESUMEN
        // ----------------------------------------------------------
        titulo("RESUMEN");
        System.out.println("Pruebas ejecutadas: " + totalPruebas);
        System.out.println("Pruebas superadas:  " + (totalPruebas - pruebasFallidas));
        System.out.println("Pruebas fallidas:   " + pruebasFallidas);
    }

    // ------------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------------

    private static ArbolBST crearBase() {
        ArbolBST arbol = new ArbolBST();
        for (int c : CODIGOS) {
            arbol.insertar(c);
        }
        return arbol;
    }

    private static void titulo(String texto) {
        System.out.println("\n========================================================");
        System.out.println(texto);
        System.out.println("========================================================");
    }

    private static void mostrarAntes(ArbolBST arbol) {
        System.out.println("ANTES:");
        arbol.imprimirLateral();
        System.out.println("Preorden antes:  " + arbol.preorden());
    }

    private static void mostrarDespues(ArbolBST arbol) {
        System.out.println("\nDESPUÉS:");
        arbol.imprimirLateral();
        System.out.println("Inorden después:  " + arbol.inorden());
        System.out.println("Preorden después: " + arbol.preorden());
    }

    private static void verificar(String descripcion, boolean condicion) {
        totalPruebas++;
        if (!condicion) {
            pruebasFallidas++;
        }
        System.out.println((condicion ? "[OK]    " : "[FALLO] ") + descripcion);
    }

    private static int contar(ArbolBST arbol, int valor) {
        int veces = 0;
        for (int v : arbol.inorden()) {
            if (v == valor) {
                veces++;
            }
        }
        return veces;
    }

    private static boolean estaOrdenada(List<Integer> lista) {
        for (int i = 1; i < lista.size(); i++) {
            if (lista.get(i - 1) > lista.get(i)) {
                return false;
            }
        }
        return true;
    }
}
