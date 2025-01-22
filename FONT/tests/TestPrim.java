package tests;

import algoritmo.Prim;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

/**
* Esta clase contiene pruebas unitarias para la implementación del algoritmo de Prim.
* Las pruebas cubren varios escenarios, incluidos gráficos con diferentes cantidades de nodos,
* gráficos vacíos y gráficos de un solo nodo.
*/
public class TestPrim {
    /**
     * Prueba con un grafo de 2 nodos.
     */
    @Test 
    public void test2node() {
        //Grad amb 2 nodes
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        graph.add(new ArrayList<>(Arrays.asList(0, 1))); 
        graph.add(new ArrayList<>(Arrays.asList(1, 0))); 

        ArrayList<ArrayList<Integer>> result = Prim.primsAlgorithm(graph);

        assertEquals(2, result.size());
        assertEquals(Arrays.asList(1), result.get(0));
        assertEquals(Arrays.asList(0), result.get(1));
    }

    /**
     * Prueba con un grafo de 3 nodos.
     */
    @Test 
    public void test3node() {
        //Graf amb 3 nodes
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        graph.add(new ArrayList<>(Arrays.asList(-1, 2, 0))); 
        graph.add(new ArrayList<>(Arrays.asList(2, -1, 1))); 
        graph.add(new ArrayList<>(Arrays.asList(0, 1, -1))); 

        ArrayList<ArrayList<Integer>> result = Prim.primsAlgorithm(graph);
        // Ordenar las listas del resultado para comparación
        for (ArrayList<Integer> neighbors : result) {
            neighbors.sort(Integer::compareTo);
        }

        assertEquals(3, result.size());
        assertEquals(Arrays.asList(1), result.get(0));
        assertEquals(Arrays.asList(0, 2), result.get(1));
        assertEquals(Arrays.asList(1), result.get(2));
    }

    /**
     * Prueba con un grafo de 4 nodos.
     */
    @Test 
    public void test4node() {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        graph.add(new ArrayList<>(Arrays.asList(0, 3, 0, 0))); 
        graph.add(new ArrayList<>(Arrays.asList(3, 0, 2, 0))); 
        graph.add(new ArrayList<>(Arrays.asList(0, 2, 0, 1))); 
        graph.add(new ArrayList<>(Arrays.asList(0, 0, 1, 0))); 

        ArrayList<ArrayList<Integer>> result = Prim.primsAlgorithm(graph);
        // Ordenar las listas del resultado para comparación
        for (ArrayList<Integer> neighbors : result) {
            neighbors.sort(Integer::compareTo);
        }

        assertEquals(4, result.size());
        assertEquals(Arrays.asList(1), result.get(0));
        assertEquals(Arrays.asList(0, 2), result.get(1));
        assertEquals(Arrays.asList(1, 3), result.get(2));
        assertEquals(Arrays.asList(2), result.get(3));
    }

    /**
     * Prueba con un grafo de 10 nodos.
     */
    @Test   
    public void test10node(){
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        graph.add(new ArrayList<>(Arrays.asList(0, 1, 0, 0, 0, 0, 0, 0, 0, 0))); 
        graph.add(new ArrayList<>(Arrays.asList(1, 0, 2, 0, 0, 0, 0, 0, 0, 0)));
        graph.add(new ArrayList<>(Arrays.asList(0, 2, 0, 3, 0, 0, 0, 0, 0, 0)));
        graph.add(new ArrayList<>(Arrays.asList(0, 0, 3, 0, 4, 0, 0, 0, 0, 0)));
        graph.add(new ArrayList<>(Arrays.asList(0, 0, 0, 4, 0, 5, 0, 0, 0, 0)));
        graph.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 5, 0, 6, 0, 0, 0)));
        graph.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 6, 0, 7, 0, 0)));
        graph.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 7, 0, 8, 0)));
        graph.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 8, 0, 9)));
        graph.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 9, 0)));

        ArrayList<ArrayList<Integer>> result = Prim.primsAlgorithm(graph);
        // Ordenar las listas del resultado para comparación
        for (ArrayList<Integer> neighbors : result) {
           neighbors.sort(Integer::compareTo);
        }

        assertEquals(10, result.size());
        assertEquals(Arrays.asList(1), result.get(0)); 
        assertEquals(Arrays.asList(0, 2), result.get(1)); 
        assertEquals(Arrays.asList(1, 3), result.get(2)); 
        assertEquals(Arrays.asList(2, 4), result.get(3)); 
        assertEquals(Arrays.asList(3, 5), result.get(4)); 
        assertEquals(Arrays.asList(4, 6), result.get(5)); 
        assertEquals(Arrays.asList(5, 7), result.get(6)); 
        assertEquals(Arrays.asList(6, 8), result.get(7)); 
        assertEquals(Arrays.asList(7, 9), result.get(8)); 
        assertEquals(Arrays.asList(8), result.get(9));
    }

    /**
     * Prueba con un grafo de 30 nodos.
     */
    @Test   
    public void test30node(){
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < 30; j++) {
                row.add(i == j ? 0 : (i + j) % 10 + 1);
            }
            graph.add(row);
        }

        ArrayList<ArrayList<Integer>> result = Prim.primsAlgorithm(graph);

        assertEquals(30, result.size());
        for (int i = 0; i < 30; i++) {
            assertEquals(true, result.get(i).size() > 0);
        }
    }

    /**
     * Prueba con un grafo de 50 nodos.
     */
    @Test 
    public void test50node() {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < 50; j++) {
                row.add(i == j ? 0 : (i + j) % 10 + 1);
            }
            graph.add(row);
        }

        ArrayList<ArrayList<Integer>> result = Prim.primsAlgorithm(graph);

        assertEquals(50, result.size());
        for (int i = 0; i < 50; i++) {
            assertEquals(true, result.get(i).size() > 0);
        }
    }

//--------------------------------------------------------------------------------
//------------------------TESTOS FUNCIONALS---------------------------------------
//--------------------------------------------------------------------------------

    /**
     * Prueba con un grafo vacío.
     */
    @Test
    public void testEmptyGraph() {
        //graf buit
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> Prim.primsAlgorithm(graph));
    }

    /**
     * Prueba con un grafo de un solo nodo.
     */
    @Test
    public void testSingleNode() {
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        graph.add(new ArrayList<>()); 

        ArrayList<ArrayList<Integer>> result = Prim.primsAlgorithm(graph);

        assertEquals(1, result.size());
        assertEquals(0, result.get(0).size()); 
    }
}
