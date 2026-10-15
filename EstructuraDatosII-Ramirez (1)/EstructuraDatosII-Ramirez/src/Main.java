public class Main {

    public static void main(String[] args) {
        // Nivel 0: raíz
        Nodo raiz = new Nodo("/", true);

        // Nivel 1
        Nodo documentos = new Nodo("Documentos", true);
        Nodo imagenes = new Nodo("Imagenes", true);
        Nodo musica = new Nodo("Musica", true);
        raiz.agregar(documentos);
        raiz.agregar(imagenes);
        raiz.agregar(musica);

        // Nivel 2
        Nodo universidad = new Nodo("Universidad", true);
        documentos.agregar(universidad);
        documentos.agregar(new Nodo("hoja_de_vida.pdf", false));
        imagenes.agregar(new Nodo("foto.png", false));
        musica.agregar(new Nodo("playlist.mp3", false));

        // Nivel 3
        universidad.agregar(new Nodo("estructura_datos.docx", false));
        universidad.agregar(new Nodo("taller1.java", false));

        System.out.println("Sistema de archivos:");
        mostrar(raiz, 0);

        System.out.println();
        System.out.println("Total de nodos: " + contar(raiz));
        System.out.println("Altura del árbol: " + altura(raiz));
    }

    // Imprime el árbol con sangría según el nivel (recorrido en preorden)
    static void mostrar(Nodo nodo, int nivel) {
        String sangria = "    ".repeat(nivel);
        String marca = nodo.esCarpeta() ? "[+] " : "    ";
        System.out.println(sangria + marca + nodo.getNombre());
        for (Nodo hijo : nodo.getHijos()) {
            mostrar(hijo, nivel + 1);
        }
    }

    // Cuenta todos los nodos del árbol
    static int contar(Nodo nodo) {
        int total = 1;
        for (Nodo hijo : nodo.getHijos()) {
            total += contar(hijo);
        }
        return total;
    }

    // Altura: número de niveles desde la raíz hasta la hoja más profunda (raíz = 0)
    static int altura(Nodo nodo) {
        int max = 0;
        for (Nodo hijo : nodo.getHijos()) {
            max = Math.max(max, 1 + altura(hijo));
        }
        return max;
    }
}
