package algoritmo;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
* La clase DFS proporciona un método para realizar una búsqueda en profundidad (DFS) en un gráfico.
* El gráfico se representa como una lista de adyacencia.
*/
public class DFS {
    /**
    * Realiza una búsqueda en profundidad (DFS) en un gráfico representado por una lista de adyacencia.
    *
    * @param graph Una lista de adyacencia que representa el gráfico. Cada índice de la lista externa
    * corresponde a un nodo y las listas internas contienen los nodos adyacentes.
    * @return Una lista de enteros que representa los nodos en el orden en que fueron visitados.
    */
    static public ArrayList<Integer> dfs(ArrayList<ArrayList<Integer>> graph) {
        //Li arriba un graf amb LLISTA D'ADJACÈNCIES
        ArrayList<Integer> result = new ArrayList<>();
        Stack<Integer> stk = new Stack<>();
        ArrayList<Boolean> visited = new ArrayList<>();
        for(int i = 0; i < graph.size(); ++i) {
            visited.add(false);
        }
        
        //comencem per un producte aleatori
        Random rnd = new Random();
        int first_prod = rnd.nextInt(graph.size());

        //utilitzem stack per fer dfs
        stk.push(first_prod);
        while (!stk.isEmpty()) {
            int prod = stk.pop();
            if (visited.get(prod)) continue;
            visited.set(prod, true);
            result.add(prod);
            for (int i = 0; i < graph.get(prod).size(); ++i) {
                int vert = graph.get(prod).get(i);
                if(!visited.get(vert)) stk.push(vert);
            }
        }
        return result;
    }
}
