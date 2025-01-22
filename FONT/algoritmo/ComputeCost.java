package algoritmo;

import java.util.ArrayList;

/**
* La clase ComputeCost proporciona métodos para calcular el coste de viajar a través de una serie de nodos en un gráfico.
* Incluye métodos para calcular el coste total y para verificar si el coste excede un valor máximo determinado.
*/
public class ComputeCost {
  /**
  * Calcula el coste total de viajar a través de una serie de nodos en un grafo.
  *
  * @param prestatgeria Una ArrayList de números enteros que representa la secuencia de nodos que se visitarán.
  * @param adjMatrix Una matriz de adyacencia representada como una ArrayList de ArrayLists de números enteros,
  * donde adjMatrix.get(i).get(j) proporciona el coste de viajar desde el nodo i al nodo j.
  * @return El coste total de viajar a través de los nodos en el orden especificado por prestatgeria.
  */
  public static int getCost(ArrayList<Integer> prestatgeria, ArrayList<ArrayList<Integer>> adjMatrix) {
    if (prestatgeria.isEmpty()|| prestatgeria.size() == 1) return 0;

    int acum = adjMatrix.get(prestatgeria.get(prestatgeria.size()-1)).get(prestatgeria.get(0));
    for (int i = 1; i < prestatgeria.size(); ++i) {
      acum += adjMatrix.get(prestatgeria.get(i-1)).get(prestatgeria.get(i));
    }
    return acum;
  }


  /**
  * Calcula el coste de viajar a través de una ruta dada representada por `prestatgeria`
  * utilizando la matriz de adyacencia `adjMatrix`, y verifica si el coste excede un valor máximo dado `max`.
  *
  * @param prestatgeria Una ArrayList de enteros que representa la ruta a evaluar.
  * @param adjMatrix Una matriz de adyacencia representada como una ArrayList de ArrayLists de enteros,
  * donde adjMatrix.get(i).get(j) proporciona el coste de viajar desde el nodo i al nodo j.
  * @param max Un valor flotante que representa el coste máximo permitido.
  * @return El coste total de viajar a través de la ruta si excede `max`,
  * o -1 si el coste no excede `max` o si la ruta está vacía o tiene solo un nodo.
  */
  public static int getCostWithMax(ArrayList<Integer> prestatgeria, ArrayList<ArrayList<Integer>> adjMatrix, float max) {
    // Cas base
    if (prestatgeria.isEmpty() || prestatgeria.size() == 1) {
      if (max == 0) return -1;
      else return 0;
    }

    int acum = adjMatrix.get(prestatgeria.get(prestatgeria.size()-1)).get(prestatgeria.get(0));
    for (int i = 1; i < prestatgeria.size(); ++i) {
      acum += adjMatrix.get(prestatgeria.get(i-1)).get(prestatgeria.get(i));
      if (acum + (prestatgeria.size() - i - 1)*100 <= max) return -1;
    }
    return acum;
  }
}