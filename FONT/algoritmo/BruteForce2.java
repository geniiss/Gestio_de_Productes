package algoritmo;

import interfaces.IAlgoritme;
import java.util.ArrayList;
import java.util.HashSet;
import structures.Pair;


/**
* La clase BruteForceGenerator implementa la interfaz IAlgoritme y proporciona
* un algoritmo de fuerza bruta basado en la poda para resolver el problema de 
* la disposición de productos en un supermercado.
*/
public class BruteForce2 implements IAlgoritme {
    /**
     * La instancia única de la clase BruteForce2.
     */
    private static final BruteForce2 instance = new BruteForce2();
    /**
     * La disposición óptima generada por el algoritmo de fuerza bruta.
     */
    private ArrayList<Integer> disposicio;
    /**
     * El coste de la disposición óptima.
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
    private BruteForce2() {
        cost = -1;
        solutionReady = false;
    }

    /**
    * Restablece el estado de BruteForceGenerator.
    */
    public void reset() {
        disposicio = null;
        relationMatrix = null;
        cost = -1;
        solutionReady = false;
    }

    /**
    * Devuelve la instancia singleton de BruteForceGenerator.
    *
    * @return la instancia singleton de BruteForceGenerator
    */
    public static BruteForce2 getInstance() {
        return instance;
    }

    /**
    * Genera una solución llamando a un mètodo que es recursivo para hacer backtracking.
    * @throws IllegalStateException si la matriz de relación es nula o está vacía.
    */
    private void generateSolution() {

      if (relationMatrix == null || relationMatrix.isEmpty()) {
        throw new IllegalStateException("Matriu de relacions no inicialitzada.");  
      }

      if (relationMatrix.size() == 1) {
        disposicio = new ArrayList<>();
        disposicio.add(0);
        cost = 0;
        solutionReady = true;
        return;
      }

      ArrayList<Integer> current = new ArrayList<>();
      current.add(0);
      HashSet<Integer> notUsed = new HashSet<>();
      for (Integer i = 1; i < relationMatrix.size(); ++i) {
        notUsed.add(i);
      }
      disposicio = new ArrayList<>();
      generateSolRec(current, notUsed, relationMatrix, 0);
      solutionReady = true;
      
    }

    /**
     * Genera una solución recursiva evaluando las disposiciones sobre la marcha.
     * @param current La permutación actual.
     * @param notUsed Los elementos que aún no se han utilizado.
     * @param relationMatrix La matriz de relaciones de productos.
     * @param best La mejor permutación encontrada hasta ahora.
     * @param currentCost El coste de la permutación actual.
     */
    private void generateSolRec(ArrayList<Integer> current, HashSet<Integer> notUsed, ArrayList<ArrayList<Integer>> relationMatrix, int currentCost) {
      //cas base
      Integer last = current.get(current.size()-1);
      if (notUsed.isEmpty()) {
        if (currentCost + relationMatrix.get(0).get(last) > cost) {
          cost = currentCost + relationMatrix.get(0).get(last);
          disposicio = new ArrayList<>(current);
        }
        return;
      }

      //cas recursiu
      //copia notUsed
      ArrayList<Integer> notUsedCopy = new ArrayList<>();
      //copiar productes no vistos en un vector per evitar que quan s'esborrin o afegeixin elements peti bucle de recorrer els no usats
      for (Integer i : notUsed) notUsedCopy.add(i);
      for (Integer i : notUsedCopy) {
        //poda
        if (currentCost + relationMatrix.get(last).get(i) + 100*notUsed.size() > cost) {
          //fwd
          notUsed.remove(i);
          current.add(i);
          //call
          generateSolRec(current, notUsed, relationMatrix, currentCost + relationMatrix.get(last).get(i));
          //bkwd
          notUsed.add(i);
          current.remove(current.size()-1);
        }
      }
    }

    /**
    * Establece la matriz de relaciones de productos.
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
    * Recupera la solución de permutación óptima.
    * @return Una lista de enteros que representa la solución de permutación óptima.
    */
    @Override
    public ArrayList<Integer> getSolution() {
        if (!solutionReady) generateSolution();
        return disposicio;
    }

    /**
    * Devuelve el coste de la solución. Si la solución no está lista, primero genera la solución.
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
    * Este método tiene como objetivo establecer las relaciones de productos de manera ordenada,
    * funcionalidad no necesaria para este algoritmo.
    * @param productRelationsSorted Una ArrayList anidada que contiene pares de números enteros
    * que representan las relaciones de productos ordenadas.
    * @throws UnsupportedOperationException Siempre se genera ya que este método no es necesario.
    */
    @Override
    public void setProductRelationsSorted(ArrayList<ArrayList<Pair<Integer, Integer>>> productRelationsSorted) {
        throw new UnsupportedOperationException("Not necessary.");
    }
}
