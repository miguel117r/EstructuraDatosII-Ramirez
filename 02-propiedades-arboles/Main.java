public class Main {

    public static void main(String[] args) {
        // ---------- Construcción del árbol (organigrama de un hospital) ----------
        NodoGeneral<String> hospital = new NodoGeneral<>("Hospital");

        NodoGeneral<String> medica = hospital.agregarHijo("Dirección Médica");
        NodoGeneral<String> admin = hospital.agregarHijo("Dirección Administrativa");
        NodoGeneral<String> enfermeria = hospital.agregarHijo("Dirección de Enfermería");

        NodoGeneral<String> urgencias = medica.agregarHijo("Urgencias");
        NodoGeneral<String> cirugia = medica.agregarHijo("Cirugía");
        medica.agregarHijo("Pediatría");

        admin.agregarHijo("Talento Humano");
        NodoGeneral<String> finanzas = admin.agregarHijo("Finanzas");

        NodoGeneral<String> hospitalizacion = enfermeria.agregarHijo("Hospitalización");

        NodoGeneral<String> quirofano = cirugia.agregarHijo("Quirófano");
        cirugia.agregarHijo("Recuperación");

        finanzas.agregarHijo("Contabilidad");
        finanzas.agregarHijo("Facturación");

        // ---------- Tabla de métricas por nodo ----------
        System.out.println("=== MÉTRICAS POR NODO ===");
        System.out.printf("%-24s %-24s %5s %11s %7s  %s%n",
                "Nodo", "Padre", "Grado", "Profundidad", "Altura", "Tipo");
        imprimirMetricas(hospital);

        // ---------- Métricas del árbol ----------
        System.out.println();
        System.out.println("=== MÉTRICAS DEL ÁRBOL ===");
        System.out.println("Total de nodos: " + contarNodos(hospital));
        System.out.println("Número de hojas: " + contarHojas(hospital));
        System.out.println("Grado del árbol: " + gradoArbol(hospital));
        System.out.println("Altura del árbol: " + altura(hospital));

        // ---------- Comparación con el cálculo manual ----------
        System.out.println();
        System.out.println("=== PRUEBAS (resultado del código vs. cálculo manual) ===");
        verificar("altura(Hospital)", altura(hospital), 3);
        verificar("altura(Dirección Médica)", altura(medica), 2);
        verificar("altura(Cirugía)", altura(cirugia), 1);
        verificar("altura(Urgencias) [hoja]", altura(urgencias), 0);
        verificar("profundidad(Hospital) [raíz]", hospital.profundidad(), 0);
        verificar("profundidad(Dirección Médica)", medica.profundidad(), 1);
        verificar("profundidad(Urgencias)", urgencias.profundidad(), 2);
        verificar("profundidad(Hospitalización)", hospitalizacion.profundidad(), 2);
        verificar("profundidad(Quirófano)", quirofano.profundidad(), 3);
        verificar("grado del árbol", gradoArbol(hospital), 3);
        verificar("número de hojas", contarHojas(hospital), 8);
        verificar("total de nodos", contarNodos(hospital), 14);

        // ---------- Ejercicio: dos caminos ----------
        System.out.println();
        System.out.println("=== CAMINOS ===");
        mostrarCamino(quirofano);
        mostrarCamino(hospitalizacion);

        // ---------- Ejercicio: agregar un nuevo nivel ----------
        System.out.println();
        System.out.println("=== AGREGAR UN NUEVO NIVEL ===");
        System.out.println("Altura antes: " + altura(hospital));
        NodoGeneral<String> esterilizacion = quirofano.agregarHijo("Esterilización");
        System.out.println("Se agregó 'Esterilización' como hijo de 'Quirófano'");
        System.out.println("Altura después: " + altura(hospital));
        System.out.println("Profundidad de Esterilización: " + esterilizacion.profundidad());
        System.out.println("Altura de Quirófano: " + altura(quirofano)
                + " | Cirugía: " + altura(cirugia)
                + " | Dirección Médica: " + altura(medica));
        verificar("altura(Hospital) tras agregar nivel", altura(hospital), 4);
    }

    // Altura de un nodo: longitud del camino más largo hasta una hoja (hoja = 0)
    static int altura(NodoGeneral<?> nodo) {
        if (nodo.getHijos().isEmpty()) return 0;
        int max = 0;
        for (NodoGeneral<?> hijo : nodo.getHijos()) {
            max = Math.max(max, altura(hijo));
        }
        return 1 + max;
    }

    static int contarNodos(NodoGeneral<?> nodo) {
        int total = 1;
        for (NodoGeneral<?> hijo : nodo.getHijos()) {
            total += contarNodos(hijo);
        }
        return total;
    }

    static int contarHojas(NodoGeneral<?> nodo) {
        if (nodo.esHoja()) return 1;
        int total = 0;
        for (NodoGeneral<?> hijo : nodo.getHijos()) {
            total += contarHojas(hijo);
        }
        return total;
    }

    // Grado del árbol = mayor grado entre todos sus nodos
    static int gradoArbol(NodoGeneral<?> nodo) {
        int max = nodo.grado();
        for (NodoGeneral<?> hijo : nodo.getHijos()) {
            max = Math.max(max, gradoArbol(hijo));
        }
        return max;
    }

    static String tipo(NodoGeneral<?> nodo) {
        if (nodo.esRaiz()) return "Raíz";
        return nodo.esHoja() ? "Hoja" : "Interno";
    }

    static void imprimirMetricas(NodoGeneral<?> nodo) {
        String padre = nodo.esRaiz() ? "-" : nodo.getPadre().toString();
        System.out.printf("%-24s %-24s %5d %11d %7d  %s%n",
                nodo, padre, nodo.grado(), nodo.profundidad(), altura(nodo), tipo(nodo));
        for (NodoGeneral<?> hijo : nodo.getHijos()) {
            imprimirMetricas(hijo);
        }
    }

    static void mostrarCamino(NodoGeneral<?> destino) {
        StringBuilder sb = new StringBuilder();
        NodoGeneral<?> actual = destino;
        while (actual != null) {
            sb.insert(0, actual + (sb.length() == 0 ? "" : " -> "));
            actual = actual.getPadre();
        }
        System.out.println(sb + "  (longitud = " + destino.profundidad() + " aristas)");
    }

    static void verificar(String descripcion, int obtenido, int esperado) {
        String estado = (obtenido == esperado) ? "OK   " : "ERROR";
        System.out.println("[" + estado + "] " + descripcion
                + " -> obtenido: " + obtenido + ", esperado: " + esperado);
    }
}
