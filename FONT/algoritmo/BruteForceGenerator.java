package algoritmo;

import interfaces.IAlgoritme;
import java.util.ArrayList;
import java.util.Collections;
import structures.Pair;


/**
* La clase BruteForceGenerator implementa la interfaz IAlgoritme y proporciona
* un generador de soluciones de fuerza bruta para una matriz de relación dada. Genera todas
* las permutaciones posibles de la entrada y calcula el coste de cada permutación
* para encontrar la solución óptima.
*/
public class BruteForceGenerator implements IAlgoritme {
    /**
     * La instancia única de la clase BruteForceGenerator.
     */
    private static final BruteForceGenerator instance = new BruteForceGenerator();
    /**
     * La permutación óptima generada por el algoritmo de fuerza bruta.
     */
    private ArrayList<Integer> permutacioOptima;
    /**
     * El coste de la permutación óptima.
     */
    private int cost;
    /**
     * Un indicador de si la solución óptima está lista.
     */
    private boolean solutionReady;

    /**
     * La matriz de relaciones de productos.
     */
    private ArrayList<ArrayList<Integer>> relationMatrix;

    /**
    * Constructor privado para la clase BruteForceGenerator.
    */
    private BruteForceGenerator() {
        cost = -1;
        solutionReady = false;
    }


    /**
    * Restablece el estado de BruteForceGenerator.
    */
    public void reset() {
        permutacioOptima = null;
        relationMatrix = null;
        cost = -1;
        solutionReady = false;
    }

    /**
    * Devuelve la instancia singleton de BruteForceGenerator.
    *
    * @return la instancia singleton de BruteForceGenerator
    */
    public static BruteForceGenerator getInstance() {
        return instance;
    }


    /**
    * Genera una solución evaluando permutaciones de elementos en función de la matriz de relación.
    * @throws IllegalStateException si la matriz de relación es nula o está vacía.
    */
    private void generateSolution() {
        if (relationMatrix == null || relationMatrix.isEmpty()) {
            throw new IllegalStateException("Matriu de relacions no inicialitzada.");
            
        }

        int n = relationMatrix.size();
        
        //cas n < 1 retorna null
        if (n < 1) { 
            //System.err.println("Minimum size of parameter: 1");
            solutionReady = false;
            permutacioOptima = null;
            //System.exit(1);
        }
        else {
            // Fixar el primer element i ordenar la resta
            ArrayList<Integer> input = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                input.add(i);
            }
            Integer first = input.get(0);
            ArrayList<Integer> rest = new ArrayList<>(input.subList(1, input.size()));
            Collections.sort(rest); // Generar permutacions en ordre canònic

            checkPermutations(first, rest, new ArrayList<>());
            solutionReady = true;
        }
    }


    /**
    * Establece la matriz de relaciones de productos.
    *
    * @param productRelations Una ArrayList 2D que representa la matriz de relaciones de productos.
    * No puede ser nula o estar vacía.
    * @throws IllegalArgumentException si la matriz productRelations es nula o está vacía.
    */
    @Override
    public void setProductRelations(ArrayList<ArrayList<Integer>> productRelations) {
        if (productRelations == null || productRelations.isEmpty()) {
            throw new IllegalArgumentException("La matriu d'adjacències no pot ser null o estar buida.");
        }
        solutionReady = false;
        relationMatrix = productRelations;
        cost = -1;
    }


    /**
    * Genera y verifica recursivamente todas las permutaciones de una lista de números enteros.
    *
    * @param first El primer número entero que se agregará a la permutación.
    * @param remain La lista de números enteros restantes para permutar.
    * @param current La permutación actual que se está construyendo.
    */
    private void checkPermutations(Integer first, ArrayList<Integer> remaining, ArrayList<Integer> current) {
        if (remaining.isEmpty()) {
            ArrayList<Integer> perm = new ArrayList<>();
            perm.add(first);
            perm.addAll(current);
            //comprovar perm
            int tmp = ComputeCost.getCostWithMax(perm, relationMatrix, cost);
            if (tmp > cost) {
                cost = tmp;
                permutacioOptima = perm;
            }
            return;
        }


        for (int i = 0; i < remaining.size(); i++) {
            // Evitar duplicats si els elements són iguals
            if (i > 0 && remaining.get(i).equals(remaining.get(i - 1))) {
                continue;
            }

            ArrayList<Integer> newRemaining = new ArrayList<>(remaining);
            ArrayList<Integer> newCurrent = new ArrayList<>(current);

            newCurrent.add(newRemaining.remove(i));
            checkPermutations(first, newRemaining, newCurrent);
        }
    }


    /**
    * Recupera la solución de permutación óptima.
    *
    * @return Una lista de enteros que representa la solución de permutación óptima.
    */
    @Override
    public ArrayList<Integer> getSolution() {
        if (solutionReady) return permutacioOptima;
        generateSolution();
        return permutacioOptima;
    }


    /**
    * Devuelve el coste de la solución. Si la solución no está lista, primero genera la solución.
    *
    * @return el coste de la solución
    */
    @Override
    public int getCost() {
        if (!solutionReady) {
            generateSolution();
        }
        return cost;
    }

    /**
    * Este método tiene como objetivo establecer las relaciones de productos de manera ordenada.
    *
    * @param productRelationsSorted Una ArrayList anidada que contiene pares de números enteros
    * que representan las relaciones de productos ordenadas.
    * @throws UnsupportedOperationException Siempre se genera ya que este método no es necesario.
    */
    @Override
    public void setProductRelationsSorted(ArrayList<ArrayList<Pair<Integer, Integer>>> productRelationsSorted) {
        throw new UnsupportedOperationException("Not necessary.");
    }
}
