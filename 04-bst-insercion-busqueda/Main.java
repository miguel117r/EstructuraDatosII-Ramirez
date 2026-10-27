import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Guía 4. BST I: inserción y búsqueda.
 *
 * 1) Ejercicio de la guía: 50,30,70,20,40,60,80 + búsquedas con camino.
 * 2) 15 códigos de productos en orden aleatorio vs. ya ordenados.
 * 3) Tabla comparativa (se guarda también en comparacion_bst.csv).
 * 4) Experimento con 1000 claves para ver la tendencia O(log n) vs O(n).
 *
 * Autor: Miguel Ángel Ramírez Pardo
 */
public class Main {

    public static void main(String[] args) {

        // ==============================================================
        // 1. EJERCICIO DE LA GUÍA
        // ==============================================================
        System.out.println("=== EJERCICIO: 50, 30, 70, 20, 40, 60, 80 ===");
        ArbolBST ejercicio = construir(new int[]{50, 30, 70, 20, 40, 60, 80});

        System.out.println("Árbol (lateral, derecha arriba):");
        ejercicio.imprimirLateral();
        System.out.println("\nInorden: " + ejercicio.inorden());
        System.out.println("Altura: " + ejercicio.altura()
                + " | Nodos: " + ejercicio.contarNodos());

        System.out.println("\nBúsqueda de un valor existente y uno inexistente:");
        System.out.println(ejercicio.buscarConCamino(60));
        System.out.println(ejercicio.buscarConCamino(65));
        System.out.println("buscar(60) = " + ejercicio.buscar(60)
                + " | buscar(65) = " + ejercicio.buscar(65));

        // ==============================================================
        // 2. QUINCE CÓDIGOS DE PRODUCTOS
        // ==============================================================
        int[] aleatorios = {
            4521, 2308, 6745, 1190, 3876,
            8012, 5467, 2951, 7230, 1604,
            9183, 4017, 6392, 3145, 8750
        };

        int[] ordenados = aleatorios.clone();
        java.util.Arrays.sort(ordenados);

        // 3 códigos que existen y 2 que no.
        int[] consultas = {1190, 6392, 8750, 5000, 9999};

        ArbolBST bstAleatorio = construir(aleatorios);
        ArbolBST bstOrdenado = construir(ordenados);

        System.out.println("\n=== 15 CÓDIGOS EN ORDEN ALEATORIO ===");
        System.out.println("Orden de inserción: " + java.util.Arrays.toString(aleatorios));
        bstAleatorio.imprimirLateral();
        System.out.println();
        int totalAleatorio = probar(bstAleatorio, consultas);

        System.out.println("\n=== LOS MISMOS 15 CÓDIGOS, YA ORDENADOS ===");
        System.out.println("Orden de inserción: " + java.util.Arrays.toString(ordenados));
        bstOrdenado.imprimirLateral();
        System.out.println();
        int totalOrdenado = probar(bstOrdenado, consultas);

        // ==============================================================
        // 3. TABLA COMPARATIVA
        // ==============================================================
        double promAleatorio = totalAleatorio / (double) consultas.length;
        double promOrdenado = totalOrdenado / (double) consultas.length;

        System.out.println("\n=== TABLA COMPARATIVA (15 códigos, 5 búsquedas) ===");
        System.out.printf(Locale.US, "%-18s %6s %8s %14s %10s%n",
                "Escenario", "Nodos", "Altura", "Comparaciones", "Promedio");
        System.out.printf(Locale.US, "%-18s %6d %8d %14d %10.2f%n", "Aleatorio",
                bstAleatorio.contarNodos(), bstAleatorio.altura(), totalAleatorio, promAleatorio);
        System.out.printf(Locale.US, "%-18s %6d %8d %14d %10.2f%n", "Ordenado",
                bstOrdenado.contarNodos(), bstOrdenado.altura(), totalOrdenado, promOrdenado);

        guardarCsv(bstAleatorio, bstOrdenado, totalAleatorio, totalOrdenado, promAleatorio, promOrdenado);

        // ==============================================================
        // 4. EXPERIMENTO A MAYOR ESCALA
        // ==============================================================
        int n = 1000;
        List<Integer> claves = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            claves.add(i * 3);
        }

        List<Integer> mezcladas = new ArrayList<>(claves);
        Collections.shuffle(mezcladas, new Random(42)); // semilla fija: resultado repetible

        ArbolBST grandeAleatorio = construir(mezcladas);
        ArbolBST grandeOrdenado = construir(claves);

        System.out.println("\n=== EXPERIMENTO CON " + n + " CLAVES (se busca cada clave una vez) ===");
        System.out.printf(Locale.US, "%-18s %8s %22s%n", "Escenario", "Altura", "Comparaciones (prom.)");
        System.out.printf(Locale.US, "%-18s %8d %22.2f%n", "Aleatorio",
                grandeAleatorio.altura(), promedioTodas(grandeAleatorio, claves));
        System.out.printf(Locale.US, "%-18s %8d %22.2f%n", "Ordenado",
                grandeOrdenado.altura(), promedioTodas(grandeOrdenado, claves));
        System.out.printf(Locale.US, "Referencias: log2(%d) = %.2f | (n + 1) / 2 = %.2f%n",
                n, Math.log(n) / Math.log(2), (n + 1) / 2.0);

        System.out.println("\nConclusión: con datos ordenados el BST se degenera en una lista,"
                + " su altura llega a n - 1 y buscar pasa de ~O(log n) a O(n).");
    }

    // ------------------------------------------------------------------
    // Utilidades
    // ------------------------------------------------------------------

    private static ArbolBST construir(int[] valores) {
        ArbolBST arbol = new ArbolBST();
        for (int v : valores) {
            arbol.insertar(v);
        }
        return arbol;
    }

    private static ArbolBST construir(List<Integer> valores) {
        ArbolBST arbol = new ArbolBST();
        for (int v : valores) {
            arbol.insertar(v);
        }
        return arbol;
    }

    /** Imprime cada búsqueda y devuelve el total de comparaciones. */
    private static int probar(ArbolBST arbol, int[] consultas) {
        int total = 0;
        for (int valor : consultas) {
            ResultadoBusqueda r = arbol.buscarConCamino(valor);
            System.out.println(r);
            total += r.getComparaciones();
        }
        System.out.println("Total de comparaciones: " + total);
        return total;
    }

    private static double promedioTodas(ArbolBST arbol, List<Integer> claves) {
        long suma = 0;
        for (int clave : claves) {
            suma += arbol.comparaciones(clave);
        }
        return suma / (double) claves.size();
    }

    private static void guardarCsv(ArbolBST a, ArbolBST o, int totalA, int totalO,
                                   double promA, double promO) {
        String csv = "Escenario,Nodos,Altura (aristas),Comparaciones totales,Promedio en 5 busquedas\n"
                + String.format(Locale.US, "Aleatorio,%d,%d,%d,%.2f%n", a.contarNodos(), a.altura(), totalA, promA)
                + String.format(Locale.US, "Ordenado,%d,%d,%d,%.2f%n", o.contarNodos(), o.altura(), totalO, promO);
        try {
            Files.writeString(Path.of("comparacion_bst.csv"), csv, StandardCharsets.UTF_8);
            System.out.println("\n(Tabla guardada en comparacion_bst.csv)");
        } catch (IOException e) {
            System.out.println("\nNo se pudo guardar el CSV: " + e.getMessage());
        }
    }
}
