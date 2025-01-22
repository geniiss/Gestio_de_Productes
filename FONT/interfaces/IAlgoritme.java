package interfaces;

import java.util.ArrayList;
import structures.Pair;

/**
 * Proporciona métodos para gestionar y consultar información relacionada con los algoritmos.
 */
public interface IAlgoritme {
    /**
     * Devuelve la solución generada por el algoritmo en forma de lista de enteros.
     * @return La solución calculada.
     */
    ArrayList<Integer> getSolution();

    /**
     * Devuelve el coste asociado a la solución generada.
     * Este coste puede representar tiempo, recursos o cualquier otro criterio
     * relevante para el algoritmo.
     * @return El coste de la solución generada.
     */
    int getCost();

    /**
     * Establece las relaciones entre los productos en una lista de listas de flotantes.
     * @param productRelations Relaciones entre productos, representadas por una lista
     *                         de listas de valores flotantes.
     */
    void setProductRelations(ArrayList<ArrayList<Integer>> productRelations);

    /**
     * Establece las relaciones entre los productos ordenadas en una lista de listas de pares
     * donde cada par contiene un valor flotante y un índice entero.
     * @param productRelationsSorted Relaciones entre productos ordenadas en una lista de
     *                               pares, donde cada par tiene un valor flotante y un índice.
     */
    void setProductRelationsSorted(ArrayList<ArrayList<Pair<Integer, Integer>>> productRelationsSorted);
}