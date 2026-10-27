/**
 * Guía 3: construcción manual de un árbol binario de 7 nodos.
 *
 * Árbol de decisión "¿Qué hago hoy?". Convención: el hijo IZQUIERDO es la
 * respuesta "Sí" y el hijo DERECHO es la respuesta "No".
 *
 *                     ¿Llueve?
 *                    /        \
 *      Quedarse en casa        ¿Hay dinero?
 *                              /          \
 *                        Ir al cine     ¿Hay tiempo libre?
 *                                        /             \
 *                                 Ir al parque    Caminar 15 minutos
 *
 * Autor: Miguel Ángel Ramírez Pardo
 */
public class Main {

    public static void main(String[] args) {

        // 1. Crear los siete nodos (todavía sin enlazar).
        NodoArbol<String> llueve = new NodoArbol<>("¿Llueve?");
        NodoArbol<String> casa = new NodoArbol<>("Quedarse en casa");
        NodoArbol<String> dinero = new NodoArbol<>("¿Hay dinero?");
        NodoArbol<String> cine = new NodoArbol<>("Ir al cine");
        NodoArbol<String> tiempo = new NodoArbol<>("¿Hay tiempo libre?");
        NodoArbol<String> parque = new NodoArbol<>("Ir al parque");
        NodoArbol<String> caminar = new NodoArbol<>("Caminar 15 minutos");

        // 2. Enlazar las referencias izquierda/derecha a mano.
        llueve.setIzquierdo(casa);
        llueve.setDerecho(dinero);

        dinero.setIzquierdo(cine);
        dinero.setDerecho(tiempo);

        tiempo.setIzquierdo(parque);
        tiempo.setDerecho(caminar);

        // 3. El árbol solo necesita conocer la raíz.
        ArbolBinario<String> arbol = new ArbolBinario<>(llueve);

        // 4. Método pedido por la guía.
        System.out.println("=== RAÍZ E HIJOS ===");
        arbol.imprimirRaizYHijos();

        // 5. Estructura completa.
        System.out.println("\n=== ÁRBOL COMPLETO ===");
        arbol.imprimirArbol();

        // 6. Propiedades y clasificación.
        System.out.println("\n=== PROPIEDADES ===");
        System.out.println("Nodos: " + arbol.contarNodos());
        System.out.println("Hojas: " + arbol.contarHojas());
        System.out.println("Altura (en aristas): " + arbol.altura());
        System.out.println("Grado de la raíz: " + llueve.grado());

        System.out.println("\n=== CLASIFICACIÓN ===");
        System.out.println("¿Lleno?      " + arbol.esLleno());
        System.out.println("¿Completo?   " + arbol.esCompleto());
        System.out.println("¿Perfecto?   " + arbol.esPerfecto());
        System.out.println("¿Degenerado? " + arbol.esDegenerado());

        // 7. Control de null: una hoja existe, pero sus referencias son null.
        System.out.println("\n=== CONTROL DE NULL ===");
        System.out.println("¿'Ir al cine' es hoja? " + cine.esHoja());
        System.out.println("Hijo izquierdo de 'Ir al cine': " + cine.getIzquierdo());

        if (cine.getIzquierdo() != null) {
            System.out.println(cine.getIzquierdo().getDato());
        } else {
            System.out.println("'Ir al cine' no tiene hijo izquierdo: la referencia es null.");
        }

        // 8. Qué pasa si falta un enlace (null NO es lo mismo que hoja).
        System.out.println("\n=== PRUEBA: QUITAR UN ENLACE ===");
        dinero.setDerecho(null); // el subárbol de '¿Hay tiempo libre?' queda desconectado
        System.out.println("Nodos enlazados a la raíz: " + arbol.contarNodos());
        System.out.println("Hojas: " + arbol.contarHojas());
        System.out.println("¿'¿Hay dinero?' es hoja? " + dinero.esHoja());
        System.out.println("Grado de '¿Hay dinero?': " + dinero.grado());
        System.out.println("¿Sigue siendo lleno? " + arbol.esLleno());
        dinero.setDerecho(tiempo); // se restaura el enlace original

        // 9. Árbol vacío: la raíz es null y el programa no falla.
        System.out.println("\n=== ÁRBOL VACÍO ===");
        ArbolBinario<String> vacio = new ArbolBinario<>();
        vacio.imprimirRaizYHijos();
        System.out.println("Nodos: " + vacio.contarNodos());
        System.out.println("Altura: " + vacio.altura());
    }
}
