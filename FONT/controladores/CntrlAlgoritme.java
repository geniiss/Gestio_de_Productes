package controladores;

import algoritmo.BruteForce2;
import algoritmo.BruteForceGenerator;
import algoritmo.ComputeCost;
import algoritmo.GreedySolutionGenerator;
import algoritmo.TSPApproximator;
import interfaces.IAlgoritme;
import java.util.ArrayList;
import structures.Pair;

/**
* La clase CntrlAlgoritme es un controlador singleton que administra distintos generadores de algoritmos
* para resolver problemas. Proporciona métodos para establecer el algoritmo a utilizar y para recuperar la
* solución y su coste.
*/
public class CntrlAlgoritme {


    /**
     * Generador de algoritmos actualmente seleccionado.
     */
    private static IAlgoritme generator = null;
    /**
     * Instancia singleton de la clase CntrlAlgoritme.
     */
    private static final CntrlAlgoritme cntrlAlg = new CntrlAlgoritme();

    
    /**
     * Devuelve la instancia singleton de la clase CntrlAlgoritme.
     *
     * @return la instancia singleton de CntrlAlgoritme
     */
    public static CntrlAlgoritme getInstance() {
		  return cntrlAlg;
    }

    
    /**
     * Constructor privado para la clase CntrlAlgoritme.
     * Este constructor evita la instanciación de la clase desde el exterior,
     * asegurando que la clase solo se pueda usar en un contexto estático.
     */
    private CntrlAlgoritme() {
    }


    /**
     * Inicializa el generador con una instancia de GreedySolutionGenerator.
     * Este método configura el generador para que utilice un algoritmo voraz para la generación de soluciones.
     */
    public void GreedyGenerator() {
      generator = GreedySolutionGenerator.getInstance();
    }


    /**
     * Inicializa la instancia del generador TSPAppimator.
     * Este método recupera la instancia singleton de TSPAppimator
     * y la asigna a la variable del generador.
     */
    public void TSPApproximatorGenerator() {
      generator = TSPApproximator.getInstance();
    }


    /**
     * Inicializa BruteForceGenerator obteniendo una instancia de él.
     * Este método establece el generador en la instancia singleton de BruteForceGenerator.
     */
    public void BruteForceGenerator() {
      generator = BruteForceGenerator.getInstance();
    }

    /**
     * Inicializa BruteForce2 obteniendo una instancia de él.
     */
    public void BruteForce2() {
      generator = BruteForce2.getInstance();
    }

    /**
     * Calcula el coste de una solución dada.
     * @param disp Dispoción de los productos.
     * @param relMatrix Matriz de relaciones entre productos.
     * @return El coste de la solución.
     */
    public int computeCost(ArrayList<Integer> disp, ArrayList<ArrayList<Integer>> relMatrix) {
      return ComputeCost.getCost(disp, relMatrix);
    }

    
    /**
     * Devuelve el coste de la solución actual generada por el algoritmo.
     *
     * @return El coste de la solución actual.
     * @throws NullPointerException Si no se selecciona ningún algoritmo.
     */
    public int getSolutionCost() {
        if (generator == null) throw new NullPointerException("No hi ha cap algorisme seleccionat.");
        return generator.getCost();
    }


    /**
     * Recupera la solución generada por el generador.
     *
     * @return Una nueva ArrayList que contiene la solución.
     */
    public ArrayList<Integer> getSolution() {
        return new ArrayList<>(generator.getSolution());
    }


    /**
     * Establece las relaciones de productos utilizando las distancias proporcionadas.
     *
     * @param distances una ArrayList 2D de números enteros que representan las distancias entre productos.
     */
    public void setProductRelations(ArrayList<ArrayList<Integer>> distances) {
        generator.setProductRelations(distances);
    }


   /**
    * Establece las relaciones de productos ordenadas por distancias.
    *
    * @param distances una lista de listas que contienen pares de números enteros, donde cada par representa
    * la distancia entre dos productos. La lista externa representa diferentes
    * conjuntos de relaciones de productos, y la lista interna contiene los pares de distancias
    * de productos para cada conjunto.
    */
    public void setProductRelationsSorted(ArrayList<ArrayList<Pair<Integer,Integer>>> distances) {
      generator.setProductRelationsSorted(distances);
    }
}
