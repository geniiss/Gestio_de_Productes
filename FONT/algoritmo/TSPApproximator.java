package algoritmo;

import interfaces.IAlgoritme;
import java.util.ArrayList;
import java.util.HashSet;
import structures.Pair;

/**
* La clase TSPAppimator proporciona un algoritmo de aproximación para resolver el problema del viajante de comercio (TSP).
* Utiliza un patrón singleton para garantizar que solo se cree una instancia de la clase.
* La clase incluye métodos para establecer la matriz de distancia, restablecer el estado, generar una solución y recuperar la solución y su costo.
* La solución se genera utilizando el algoritmo de Prim para construir un árbol de expansión mínimo y una búsqueda en profundidad (DFS) para obtener un orden de recorrido.
* El orden de recorrido se procesa para eliminar duplicados, lo que da como resultado una aproximación de circuito hamiltoniano.
*/
public class TSPApproximator implements IAlgoritme {
    /**
     * La matriz de distancias entre los nodos.
     */
    private ArrayList<ArrayList<Integer>> distanceMatrix;
    /**
     * La disposición de los nodos en la solución.
     */
    private ArrayList<Integer> disposition;
    /**
     * Un indicador de si la solución está lista.
     */
    boolean solReady;
    /**
     * La instancia única de la clase TSPApproximator.
     */
    private static final TSPApproximator instance = new TSPApproximator();
    /**
     * El coste de la solución.
     */
    private int cost;

    /**
    * Constructor privado para la clase TSPAppimator.
    * Inicializa el costo en -1 y establece el indicador de preparación de la solución en falso.
    * Este constructor es privado para evitar la creación de instancias de la clase.
    */
    private TSPApproximator() {
      cost = -1;
      solReady = false;
    }

    /**
    * Restablece el estado de la instancia TSPAppimator.
    * Este método borra la matriz de distancia, la disposición y el costo,
    * y establece el indicador de preparación de la solución en falso.
    */
    public void reset() {
        distanceMatrix = null;
        disposition = null;
        cost = -1;
        solReady = false;
    }

    /**
    * Devuelve la instancia singleton de TSPAppimator.
    *
    * @return la instancia singleton de TSPAppimator
    */
    public static TSPApproximator getInstance() {
      return instance;
    }


    /**
    * Establece las relaciones de productos utilizando la matriz de distancias proporcionada.
    *
    * @param distanceMatrix Una ArrayList 2D que representa la matriz de distancias.
    * La matriz no debe ser nula o estar vacía.
    * @throws IllegalArgumentException si la matriz de distancias es nula o está vacía.
    */
    @Override
    public void setProductRelations(ArrayList<ArrayList<Integer>> distanceMatrix) {
        if (distanceMatrix == null || distanceMatrix.size() == 0) {
            throw new IllegalArgumentException("La matriu de distàncies no pot ser nul·la o buida.");
        }
        this.distanceMatrix = distanceMatrix;
        solReady = false;
    }


    /**
    * Este método tiene como objetivo establecer las relaciones de productos de manera ordenada.
    *
    * @param productRelationsSorted Una lista de listas que contienen pares de números enteros
    * que representan las relaciones de productos ordenadas.
    * @throws UnsupportedOperationException Siempre se genera para indicar que esta operación no se admite.
    */
    @Override
    public void setProductRelationsSorted(ArrayList<ArrayList<Pair<Integer, Integer>>> productRelationsSorted) {
        throw new UnsupportedOperationException("No necessari.");
    }


    /**
    * Genera una solución para el Problema del Viajante (TSP) utilizando un algoritmo de aproximación.
    *
    * @throws IllegalStateException si la matriz de distancia no está inicializada o está vacía.
    */
    private void generateSolution() {
        if (distanceMatrix == null || distanceMatrix.size() == 0) {
            throw new IllegalStateException("Matriu de distàncies no inicialitzada.");
        }
        // Implementació del algoritme aproximat per TSP (Travelling Salesman Problem)
        ArrayList<ArrayList<Integer>> minSpanTree = Prim.primsAlgorithm(distanceMatrix);
        ArrayList<Integer> recorregut = DFS.dfs(minSpanTree);
        ArrayList<Integer> recorregutSenseRepetits = new ArrayList<>();
        HashSet<Integer> checkSet = new HashSet<>();
        for (int i = 0; i < recorregut.size(); ++i) {
            if (!checkSet.contains(recorregut.get(i))) {
                recorregutSenseRepetits.add(recorregut.get(i));
                checkSet.add(recorregut.get(i));
            }
        }
        this.disposition = recorregutSenseRepetits; 
        cost = ComputeCost.getCost(disposition, distanceMatrix);
        solReady = true;

    }

    /**
    * Devuelve la solución para la aproximación TSP (problema del viajante de comercio).
    * Si la solución no está lista, primero genera la solución.
    *
    * @return Una lista de enteros que representan la solución.
    */
    @Override
    public ArrayList<Integer> getSolution(){
        if (!solReady) generateSolution();
        return disposition;
    }


    /**
    * Devuelve el costo de la solución. Si la solución no está lista, primero genera la solución.
    *
    * @return el costo de la solución
    */
    @Override
    public int getCost() {
      if (!solReady) generateSolution();
      return cost;
    }
}
