package algoritmo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

    /**
    * La clase Prim proporciona un método para encontrar el árbol de expansión mínimo (MST) de un gráfico
    * utilizando el algoritmo de Prim. El gráfico se representa como una matriz de adyacencia.
    */
public class Prim {
    /**
    * Implementa el algoritmo de Prim para encontrar el árbol de expansión mínimo (MST) de un gráfico determinado.
    * El gráfico se representa como una matriz de adyacencia.
    *
    * @param graph Una matriz de adyacencia que representa el gráfico donde graph[i][j] es el peso del borde entre los nodos i y j.
    * @return Una lista de adyacencia que representa el MST del gráfico determinado.
*/
    public static ArrayList<ArrayList<Integer>> primsAlgorithm(ArrayList<ArrayList<Integer>> graph) {
        
        //creem graf buit, serà el resultat
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        int n = graph.size();
        for (int i = 0; i < n; i++) {
            result.add(new ArrayList<>());
        }

        //creem cua de prioritats
        PriorityQueue<List<Object>> pq = new PriorityQueue<>((e1, e2) -> Integer.compare((int)e2.get(0), (int)e1.get(0)));

        //comencem per un producte aleatori
        Random rnd = new Random();
        int first_prod = rnd.nextInt(graph.size());
        // int first_prod = 0;
        //l'afegim a la cua
        //la cua tindrà elements:
        //{cost (1/afinitat productes acumulats), actual, pare} 
        pq.add(Arrays.asList(0, first_prod, first_prod));
        //inicialitzar tot el vector de visitat a false
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < graph.size(); ++i) visited.add(false);
        while(!pq.isEmpty()) {
            List<Object> entry = pq.poll();
            int node_actual = (int)entry.get(1);
            int node_pare = (int)entry.get(2);

            //si ja ha estat visitat, seguent iteració
            if(visited.get(node_actual)) continue;
            //marcar visitat
            visited.set(node_actual, true);
            //primera iteració no ho farà
            if (node_actual != node_pare) {
                result.get(node_actual).add(node_pare);
                result.get(node_pare).add(node_actual);
            }
            //afegir nodes adjacents a la cua
            for (int i = 0; i < graph.size(); ++i) {
                if(i != node_actual && !visited.get(i)) {
                    pq.add(Arrays.asList(graph.get(node_actual).get(i), i, node_actual));
                }
            }
        }
        //retorna graf amb llista d'adjacències   
        return result;
    }

    /**
    * El método principal para ejecutar el algoritmo de Prim en un grado.
    * Inicializa un gráfico representado como una matriz de adyacencia con pesos,
    * ejecuta el algoritmo de Prim para encontrar el árbol de expansión mínimo (MST),
    * e imprime el MST resultante.
    *
    * @param args Argumentos de línea de comandos
    */
    public static void main(String[] args) {
      ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
      graph.add(new ArrayList<>(Arrays.asList(-1, 2, 0))); 
      graph.add(new ArrayList<>(Arrays.asList(2, -1, 1))); 
      graph.add(new ArrayList<>(Arrays.asList(0, 1, -1))); 

      ArrayList<ArrayList<Integer>> result = Prim.primsAlgorithm(graph);
      // Ordenar las listas del resultado para comparación

      for (ArrayList<Integer> neighbors : result) {
          System.out.println(neighbors);
      }
    }
}
