# EstructuraDatosII-Ramirez

**Actividad:** De lo lineal a lo jerárquico
**Autor:** Miguel Ángel Ramírez Pardo
**Asignatura:** Estructura de Datos II

## Clasificación de los cuatro escenarios

| Escenario | Clasificación | Justificación |
|---|---|---|
| Sistema de archivos | Jerárquica | Hay una raíz, las carpetas contienen archivos y otras carpetas, y cada elemento tiene un único padre. |
| Organigrama empresarial | Jerárquica | Cada cargo depende de un superior y puede tener varios subordinados, formando niveles. |
| Menú de una aplicación | Lineal o jerárquica | Un menú plano (Inicio, Perfil, Salir) es una lista lineal. Si tiene submenús (Archivo → Exportar → PDF), requiere una estructura jerárquica. |
| Árbol genealógico | Jerárquica, con matiz | Si se sigue una sola línea de descendencia es un árbol. Si se registran ambos padres de cada persona, un nodo tiene dos padres y pasa a ser un grafo. |

## Escenario modelado

**Sistema de archivos** con 10 nodos y 4 niveles (raíz en el nivel 0, altura 3). Las carpetas son nodos internos y los archivos son hojas.

- Diagrama (PDF): [`diagrama/arbol-sistema-archivos.pdf`](diagrama/arbol-sistema-archivos.pdf)
- Diagrama (editable): [`diagrama/arbol-sistema-archivos.drawio`](diagrama/arbol-sistema-archivos.drawio) (se abre en [draw.io](https://app.diagrams.net))
- Código: [`src/Nodo.java`](src/Nodo.java) y [`src/Main.java`](src/Main.java)

## Explicación del diagrama

El diagrama representa un sistema de archivos como un árbol general de 10 nodos y 4 niveles. La raíz (`/`) está en el nivel 0 y de ella dependen tres carpetas: Documentos, Imagenes y Musica. Dentro de Documentos hay otra carpeta, Universidad, que guarda dos archivos del curso.

Las carpetas son nodos internos, porque pueden contener otros elementos. Los archivos son hojas, porque no tienen hijos. Cada nodo tiene un solo padre, y por eso la ubicación de cualquier archivo queda definida por un único camino desde la raíz, por ejemplo `/Documentos/Universidad/taller1.java`.

Una estructura lineal no sirve para este caso. En una lista, cada elemento solo conoce al siguiente, y no hay forma natural de expresar que una carpeta contiene varios elementos a la vez. Habría que recorrer la lista completa para saber qué hay dentro de cada carpeta. En el árbol, cada nodo guarda directamente a sus hijos.

En el código, la clase `Nodo` tiene un nombre, un indicador de si es carpeta y una lista de hijos. Con recursión se recorre el árbol para imprimirlo, contar sus nodos (10) y calcular su altura (3).

## Cómo compilar y ejecutar

```bash
cd src
javac Main.java Nodo.java
java Main
```

Salida esperada:

```
Sistema de archivos:
[+] /
    [+] Documentos
        [+] Universidad
                estructura_datos.docx
                taller1.java
            hoja_de_vida.pdf
    [+] Imagenes
            foto.png
    [+] Musica
            playlist.mp3

Total de nodos: 10
Altura del árbol: 3
```

## Reflexión

Al analizar los cuatro escenarios noté que la pregunta clave no es qué tan complejos son los datos, sino cómo se relacionan entre sí. Una lista o una cola sirven cuando los elementos van uno detrás de otro, pero en un sistema de archivos una carpeta contiene varias cosas a la vez, y esas carpetas contienen a su vez otras. Guardar eso en una lista obligaría a recorrerla completa para saber qué hay dentro de cada carpeta. Con un árbol, cada nodo conoce directamente a sus hijos y la ubicación de cualquier elemento queda definida por un único camino desde la raíz. Al programar la clase Nodo vi que bastan una lista de hijos y un método recursivo para recorrer el árbol, contar nodos y calcular la altura. También me llamó la atención el árbol genealógico: parece un árbol, pero si cada persona tiene dos padres en realidad es un grafo. Y el menú de una aplicación puede ser lineal o jerárquico según tenga submenús. Entendí que elegir la estructura correcta depende de la forma real del problema, y que una buena elección simplifica tanto el diagrama como el código.
