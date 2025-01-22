package algoritmo;

import interfaces.IAlgoritme;
import java.util.ArrayList;
import java.util.Collections;
import structures.Pair;

/**
* La clase GreedySolutionGenerator implementa un algoritmo voraz para generar una solución
* para organizar productos en un estante en función de sus relaciones. Sigue el patrón de diseño Singleton
* para garantizar que solo se cree una instancia de la clase.
*/
public class GreedySolutionGenerator implements IAlgoritme {
    
    /**
     * SolutionReady indica si la solución ya ha sido generada.
     */
    private boolean solutionReady;
    /**
     * numProductes indica el número total de productos.
     */
    private int numProductes;
    /**
     * prestatgeria contiene la solución.
     */
    private ArrayList<Integer> prestatgeria; // Contiene la solución
    /**
     * l y r son los índices de la izquierda y derecha de la estantería.
     */
    private int l, r;
    /**
     * adjMatrix es la matriz de adyacencia de las relaciones entre productos.
     */
    private ArrayList<ArrayList<Pair<Integer, Integer>>> adjMatrixOrdered;
    /**
     * adjMatrix es la matriz de adyacencia de las relaciones entre productos.
     */
    private ArrayList<ArrayList<Integer>> adjMatrix;
    /**
     * productosColocados es un ArrayList que indica si un producto ya ha sido colocado.
     */
    private ArrayList<Boolean> productosColocados; 
    /**
     * cost es el cost total de la solución.
     */
    private int cost; 

    /**
     * La instancia singleton de GreedySolutionGenerator.
     */
    private static final GreedySolutionGenerator instance = new GreedySolutionGenerator();
    
    /**
    * Constructor privado para la clase GreedySolutionGenerator.
    * Este constructor inicializa el objeto llamando al método resetSolution().
    */
    private GreedySolutionGenerator() {
        resetSolution();
    }


    /**
    * Restablece el estado de la solución a sus valores iniciales.
    */
    private void resetSolution() {
        solutionReady = false;
        cost = -1;
        l = 0;
        r = 1;
    }


    /**
    * Mueve el contador hacia la izquierda disminuyendo su valor.
    * Si el contador baja a menos de cero, vuelve al último producto.
    */
    private void moveCounterLeft() {
        --l;
        if (l < 0) l = numProductes - 1;
    }

    
    /**
    * Mueve el contador hacia la derecha incrementando el valor de 'r'.
    * Si 'r' alcanza o supera el número de productos ('numProductes'),
    * da la vuelta y restablece 'r' a 0.
    */
    private void moveCounterRight() {
        ++r;
        if (r >= numProductes) r = 0;
    }

    /**
    * Agrega un producto al lado izquierdo del estante en el índice especificado.
    *
    * @param indexProducte el índice del producto que se agregará al estante
    */
    private void addProductLeft(Integer indexProducte) {
        prestatgeria.set(l, indexProducte);
        productosColocados.set(indexProducte, true);
        moveCounterLeft();
    }
    

    /**
    * Agrega un producto al lado derecho del estante en el índice especificado.
    *
    * @param indexProducte el índice del producto que se agregará al estante
    */
    private void addProductRight(Integer indexProducte) {
        prestatgeria.set(r, indexProducte);
        productosColocados.set(indexProducte, true);
        moveCounterRight();
    }


    /**
    * Recupera el producto a la izquierda de la posición actual en el estante.
    *
    * @return el producto ubicado a la izquierda de la posición actual en el estante.
    */
    private Integer getLeftProduct() {
        int index = (l + 1) % numProductes; 
        return prestatgeria.get(index);
    }


    /**
    * Recupera el producto ubicado a la derecha de la posición actual en el estante.
    * El método asegura que el índice esté dentro del rango válido de productos.
    *
    * @return El producto ubicado a la derecha de la posición actual.
    */
    private Integer getRightProduct() {
        int index = (r - 1 + numProductes) % numProductes; 
        return prestatgeria.get(index);
    }


    /**
    * Comprueba si un producto está colocado en el estante.
    *
    * @param producte el identificador del producto
    * @return true si el producto está colocado en el estante, false en caso contrario
    */
    private boolean producteEnPrestatgeria(Integer producte) {
        return productosColocados.get(producte);
    }

    
    /**
    * Devuelve la instancia singleton de GreedySolutionGenerator.
    *
    * @return la instancia singleton de GreedySolutionGenerator
    */
    public static GreedySolutionGenerator getInstance() {
        return instance;
    }

    /**
    * Restablece el estado de GreedySolutionGenerator borrando las matrices de adyacencia. Se utiliza en los tests.
    */
    public void reset() {
        adjMatrix = null;
        adjMatrixOrdered = null;
        prestatgeria = null;
        cost = -1;
        solutionReady = false;
    }


    /**
    * Establece la matriz de adyacencia de las relaciones de productos.
    *
    * @param productRelations Una ArrayList 2D que representa la matriz de adyacencia de las relaciones de productos.
    * No debe ser nula ni estar vacía.
    * @throws IllegalArgumentException si la matriz de adyacencia proporcionada es nula o está vacía.
    */
    @Override
    public void setProductRelations(ArrayList<ArrayList<Integer>> productRelations) {
        if (productRelations == null || productRelations.isEmpty()) {
            throw new IllegalArgumentException("La matriu d'adjacències no pot ser null o estar buida.");
        }
        adjMatrix = productRelations;
        numProductes = adjMatrix.size();
        resetSolution();
    }


    /**
    * Establece las relaciones de productos ordenadas en una matriz de adyacencia.
    *
    * @param productRelationsSorted Una ArrayList 2D de pares que representan las relaciones de productos ordenadas.
    * Cada ArrayList interna representa la lista de adyacencia de un producto.
    * @throws IllegalArgumentException si la matriz de adyacencia proporcionada es nula o está vacía.
    */
    @Override
    public void setProductRelationsSorted(ArrayList<ArrayList<Pair<Integer, Integer>>> productRelationsSorted) {
        if (productRelationsSorted == null || productRelationsSorted.isEmpty()) {
            throw new IllegalArgumentException("La matriu d'adjacències no pot ser null o estar buida.");
        }
        adjMatrixOrdered = productRelationsSorted;
    }


    /**
    * Genera una solución para colocar productos en un estante utilizando un algoritmo voraz.
    * El método inicializa las variables necesarias y coloca iterativamente los productos
    * en el estante en función de sus relaciones, con el objetivo de maximizar el coste total de la relación.
    *
    * @throws  IllegalStateException si la matriz de adyacencia no está inicializada.
    */
    private void generateSolution() {
        // Inicializar variables
        cost = 0;
        // La estantería está vacía
        prestatgeria = new ArrayList<>(Collections.nCopies(numProductes, -1));
        // Asignar que todos los productos estás por colocar
        productosColocados = new ArrayList<>(Collections.nCopies(numProductes, false));

        if (adjMatrix == null || adjMatrix.isEmpty()) {
            throw new IllegalStateException("Matriu d'adjacències no inicialitzada.");
        }
        // Comprueba si la entrada de productos es válida (y los casos base)
        else if (numProductes == 1) {
            prestatgeria.set(0, 0);
            solutionReady = true;
            return;
        }
        else if (numProductes == 2) {
            prestatgeria.set(0, 0);
            prestatgeria.set(1, 1);
            cost = adjMatrix.get(0).get(1)*2; // Es relaciona el primer producte amb l'últim dues vegades degut al seu comportament circular
            solutionReady = true;
            return;
        }

        // Quedarse con la relación más grande de entre todos los productos (y los dos productos que la conforman)
        int relacioMax = -1;
        Integer indexProducteMax1 = -1;
        Integer indexProducteMax2 = -1;
        for (int i = 0; i < numProductes; ++i) {
            ArrayList<Pair<Integer, Integer>> relacionsProducte = adjMatrixOrdered.get(i);
            for (Pair<Integer, Integer> relacioPair : relacionsProducte) {
                int relacio = relacioPair.getFirst();
                Integer indexProducte1 = i;
                Integer indexProducte2 = relacioPair.getSecond();
                
                if (relacio > relacioMax) {
                    relacioMax = relacio;
                    indexProducteMax1 = indexProducte1;
                    indexProducteMax2 = indexProducte2;
                }
            }
        }

        // Añadir a la estantería los productos con mayor relación
        addProductLeft(indexProducteMax1);
        addProductRight(indexProducteMax2);
        // Añadir el coste
        cost += relacioMax;

        // Ir añadiendo a la estantería los productos con más relación con los que ya están en la estantería
        int posicioMaxProductLeft = 1;
        int posicioMaxProductRight = 1;
        Pair<Integer,Integer> relacioMaxLeftPair = adjMatrixOrdered.get(getLeftProduct()).get(posicioMaxProductLeft);
        Pair<Integer,Integer> relacioMaxRightPair = adjMatrixOrdered.get(getRightProduct()).get(posicioMaxProductRight);
        while (l != r) {
            // Se queda con la relación máxima entre los dos productos que hay en la estantería
            if (relacioMaxRightPair.getFirst() > relacioMaxLeftPair.getFirst()) {
                Integer product = relacioMaxRightPair.getSecond();
                // Añadir el producto a la estantería (si no estaba ya)
                if (!producteEnPrestatgeria(product)) {
                    addProductRight(product);
                    posicioMaxProductRight = 0;
                    cost += relacioMaxRightPair.getFirst();
                }
                else ++posicioMaxProductRight; // Se escoge el siguiente con más relación
                
                // Actualizar relacioMaxRightPair
                relacioMaxRightPair = adjMatrixOrdered.get(getRightProduct()).get(posicioMaxProductRight);
            }
            else {
                Integer product = relacioMaxLeftPair.getSecond();
                // Añade el producto a la estantería (si no estaba antes)
                if (!producteEnPrestatgeria(product)) {
                    addProductLeft(product);
                    posicioMaxProductLeft = 0;
                    cost += relacioMaxLeftPair.getFirst();
                }
                else ++posicioMaxProductLeft; // Se escoge el siguiente con más relación
                
                // Actualizar relacioMaxRightPair
                relacioMaxLeftPair = adjMatrixOrdered.get(getLeftProduct()).get(posicioMaxProductLeft);
            }
        }

        // Añadir el que falta por añadir.
        for (int i = 0; i < numProductes; ++i) {
            if (!productosColocados.get(i)) {
                int leftProd = getLeftProduct();
                int rightProd = getRightProduct();
                addProductLeft(i);

                // Añadir el coste que tiene con los dos productos adjacentes (porque es el último en colocar)
                cost += adjMatrix.get(i).get(leftProd) + adjMatrix.get(i).get(rightProd);
                break;
            }
        }

        solutionReady = true;
    }


    /**
    * Devuelve la solución como una ArrayList de números enteros.
    * Si la solución ya se generó, devuelve la solución existente.
    * De lo contrario, genera la solución antes de devolverla.
    *
    * @return una ArrayList de números enteros que representan la solución.
    */
    @Override
    public ArrayList<Integer> getSolution() {
        if (solutionReady) return prestatgeria;
        generateSolution();
        return prestatgeria;
    }


    /**
    * Devuelve el coste de la solución generada. Si la solución no está lista,
    * genera la solución primero.
    *
    * @return el coste de la solución generada
    */
    @Override
    public int getCost() {
        if (!solutionReady) {
            generateSolution();
        }
        return cost;
    }
}