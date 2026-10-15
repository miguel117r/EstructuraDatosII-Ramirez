# 02 - Propiedades de un árbol

**Guía 2:** Anatomía y propiedades de un árbol
**Autor:** Miguel Ángel Ramírez Pardo
**Asignatura:** Estructura de Datos II

## Árbol elegido

Organigrama de un **hospital** con **14 nodos** y **4 niveles** (del nivel 0 al 3).

- Diagrama rotulado (Draw.io): [`arbol-hospital.drawio`](arbol-hospital.drawio) · PDF: [`arbol-hospital.pdf`](arbol-hospital.pdf)
- Código: [`NodoGeneral.java`](NodoGeneral.java) y [`Main.java`](Main.java)

## Rotulado de conceptos

| Concepto | En este árbol |
|---|---|
| Raíz (no tiene padre) | Hospital |
| Nodos internos (tienen hijos, sin contar la raíz) | Dirección Médica, Dirección Administrativa, Dirección de Enfermería, Cirugía, Finanzas |
| Hojas (no tienen hijos) | Urgencias, Pediatría, Talento Humano, Hospitalización, Quirófano, Recuperación, Contabilidad, Facturación (8) |
| Padre - hijos | Dirección Médica es padre de Urgencias, Cirugía y Pediatría |
| Hermanos | Urgencias, Cirugía y Pediatría (mismo padre); también Contabilidad y Facturación |
| Subárbol | El subárbol de Cirugía: Cirugía, Quirófano y Recuperación |
| Ancestros de Quirófano | Cirugía, Dirección Médica y Hospital |

## Tabla de métricas

La raíz está en el nivel 0, así que el nivel de un nodo coincide con su profundidad.

| Nodo | Padre | Grado | Profundidad (nivel) | Altura | Tipo |
|---|---|---|---|---|---|
| Hospital | - | 3 | 0 | 3 | Raíz |
| Dirección Médica | Hospital | 3 | 1 | 2 | Interno |
| Dirección Administrativa | Hospital | 2 | 1 | 2 | Interno |
| Dirección de Enfermería | Hospital | 1 | 1 | 1 | Interno |
| Urgencias | Dirección Médica | 0 | 2 | 0 | Hoja |
| Cirugía | Dirección Médica | 2 | 2 | 1 | Interno |
| Pediatría | Dirección Médica | 0 | 2 | 0 | Hoja |
| Talento Humano | Dirección Administrativa | 0 | 2 | 0 | Hoja |
| Finanzas | Dirección Administrativa | 2 | 2 | 1 | Interno |
| Hospitalización | Dirección de Enfermería | 0 | 2 | 0 | Hoja |
| Quirófano | Cirugía | 0 | 3 | 0 | Hoja |
| Recuperación | Cirugía | 0 | 3 | 0 | Hoja |
| Contabilidad | Finanzas | 0 | 3 | 0 | Hoja |
| Facturación | Finanzas | 0 | 3 | 0 | Hoja |

**Métricas del árbol**

- Total de nodos: 14
- Niveles: 4 (nivel 0 al 3)
- Grado del árbol (mayor grado de un nodo): **3**
- Altura del árbol: **3**
- Nodos por nivel: 1, 3, 6 y 4

## Ejercicios

**1. Profundidad de cada hoja**

| Hoja | Profundidad |
|---|---|
| Urgencias | 2 |
| Pediatría | 2 |
| Talento Humano | 2 |
| Hospitalización | 2 |
| Quirófano | 3 |
| Recuperación | 3 |
| Contabilidad | 3 |
| Facturación | 3 |

**2. Dos caminos distintos y su longitud** (la longitud se mide en aristas)

- Hospital → Dirección Médica → Cirugía → Quirófano: longitud **3**
- Hospital → Dirección de Enfermería → Hospitalización: longitud **2**

**3. Agregar un nuevo nivel**

Se agrega el nodo **Esterilización** como hijo de Quirófano. Ahora Quirófano deja de ser hoja y Esterilización queda en la profundidad 4. La altura del árbol pasa de **3 a 4**, porque el camino más largo desde la raíz creció en una arista. También suben en 1 las alturas de todos los ancestros del nuevo nodo: Quirófano (0 → 1), Cirugía (1 → 2), Dirección Médica (2 → 3) y Hospital (3 → 4). Los nodos de otras ramas no cambian.

## Algoritmo de altura

La altura de un nodo es la longitud del camino más largo desde ese nodo hasta una hoja. El método `altura()` es recursivo:

- **Caso base:** si el nodo no tiene hijos, es una hoja y su altura es 0.
- **Caso recursivo:** se calcula la altura de cada hijo, se toma la mayor y se le suma 1 (por la arista que une al nodo con ese hijo).

```java
static int altura(NodoGeneral<?> nodo) {
    if (nodo.getHijos().isEmpty()) return 0;
    int max = 0;
    for (NodoGeneral<?> hijo : nodo.getHijos()) {
        max = Math.max(max, altura(hijo));
    }
    return 1 + max;
}
```

En `Main` el resultado del código se compara con el cálculo manual de esta tabla (sección de pruebas) y todas las comprobaciones dan `OK`.

## Cómo compilar y ejecutar

```bash
javac NodoGeneral.java Main.java
java Main
```

Si en la consola aparecen símbolos raros en lugar de las tildes, en Windows ejecuta antes `chcp 65001`.

## Salida de la ejecución

```
=== MÉTRICAS POR NODO ===
Nodo                     Padre                    Grado Profundidad  Altura  Tipo
Hospital                 -                            3           0       3  Raíz
Dirección Médica         Hospital                     3           1       2  Interno
Urgencias                Dirección Médica             0           2       0  Hoja
Cirugía                  Dirección Médica             2           2       1  Interno
Quirófano                Cirugía                      0           3       0  Hoja
Recuperación             Cirugía                      0           3       0  Hoja
Pediatría                Dirección Médica             0           2       0  Hoja
Dirección Administrativa Hospital                     2           1       2  Interno
Talento Humano           Dirección Administrativa     0           2       0  Hoja
Finanzas                 Dirección Administrativa     2           2       1  Interno
Contabilidad             Finanzas                     0           3       0  Hoja
Facturación              Finanzas                     0           3       0  Hoja
Dirección de Enfermería  Hospital                     1           1       1  Interno
Hospitalización          Dirección de Enfermería      0           2       0  Hoja

=== MÉTRICAS DEL ÁRBOL ===
Total de nodos: 14
Número de hojas: 8
Grado del árbol: 3
Altura del árbol: 3

=== PRUEBAS (resultado del código vs. cálculo manual) ===
[OK   ] altura(Hospital) -> obtenido: 3, esperado: 3
[OK   ] altura(Dirección Médica) -> obtenido: 2, esperado: 2
[OK   ] altura(Cirugía) -> obtenido: 1, esperado: 1
[OK   ] altura(Urgencias) [hoja] -> obtenido: 0, esperado: 0
[OK   ] profundidad(Hospital) [raíz] -> obtenido: 0, esperado: 0
[OK   ] profundidad(Dirección Médica) -> obtenido: 1, esperado: 1
[OK   ] profundidad(Urgencias) -> obtenido: 2, esperado: 2
[OK   ] profundidad(Hospitalización) -> obtenido: 2, esperado: 2
[OK   ] profundidad(Quirófano) -> obtenido: 3, esperado: 3
[OK   ] grado del árbol -> obtenido: 3, esperado: 3
[OK   ] número de hojas -> obtenido: 8, esperado: 8
[OK   ] total de nodos -> obtenido: 14, esperado: 14

=== CAMINOS ===
Hospital -> Dirección Médica -> Cirugía -> Quirófano  (longitud = 3 aristas)
Hospital -> Dirección de Enfermería -> Hospitalización  (longitud = 2 aristas)

=== AGREGAR UN NUEVO NIVEL ===
Altura antes: 3
Se agregó 'Esterilización' como hijo de 'Quirófano'
Altura después: 4
Profundidad de Esterilización: 4
Altura de Quirófano: 1 | Cirugía: 2 | Dirección Médica: 3
[OK   ] altura(Hospital) tras agregar nivel -> obtenido: 4, esperado: 4
```

### Captura de ejecución

![Captura de ejecución](captura-ejecucion.png)
