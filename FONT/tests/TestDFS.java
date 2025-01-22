package tests;

import algoritmo.DFS;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import java.util.ArrayList;
import java.util.Arrays;

/**
* Esta clase contiene pruebas unitarias para el algoritmo DFS (búsqueda en profundidad).
* Las pruebas se implementan utilizando el marco JUnit.
*/
public class TestDFS {
  //------------------------------TESTOS CORRECTESA--------------------------------
  //--------------------------------------------------------------------------------
  /**
   * Prueba con un grafo de 2 nodos.
   */
  @Test 
  public void test2node() {
    //creem un graf amb 2 nodes i matriu adjacències que sigui un arbre
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    graph.add(new ArrayList<>(Arrays.asList(1)));
    graph.add(new ArrayList<>(Arrays.asList(0)));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(2, solution.size());
    for (int i = 0; i < 2; i++) {
        assertEquals(true, solution.contains(i));
    }
  }

  /**
   * Prueba con un grafo de 3 nodos.
   */
  @Test 
  public void test3node() {
    //creem un graf amb 3 nodes i matriu adjacències que sigui un arbre
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    graph.add(new ArrayList<>(Arrays.asList(1, 2)));
    graph.add(new ArrayList<>(Arrays.asList(0)));
    graph.add(new ArrayList<>(Arrays.asList(0)));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(3, solution.size());
    for (int i = 0; i < 3; i++) {
        assertEquals(true, solution.contains(i));
    }
  }
  
  /**
   * Prueba con un grafo de 4 nodos.
   */
  @Test 
  public void test4node() {
    //creem un graf amb 4 nodes i matriu adjacències que sigui un arbre
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    graph.add(new ArrayList<>(Arrays.asList(1, 2)));
    graph.add(new ArrayList<>(Arrays.asList(0, 3)));
    graph.add(new ArrayList<>(Arrays.asList(0)));
    graph.add(new ArrayList<>(Arrays.asList(1)));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(4, solution.size());
    for (int i = 0; i < 4; i++) {
        assertEquals(true, solution.contains(i));
    }
  }

  /**
   * Prueba con un grafo de 5 nodos.
   */
  @Test 
  public void test5node() {
    //creem un graf amb 5 nodes i matriu adjacències que sigui un arbre
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    graph.add(new ArrayList<>(Arrays.asList(1, 2)));
    graph.add(new ArrayList<>(Arrays.asList(0, 3)));
    graph.add(new ArrayList<>(Arrays.asList(0)));
    graph.add(new ArrayList<>(Arrays.asList(1, 4)));
    graph.add(new ArrayList<>(Arrays.asList(3)));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(5, solution.size());
    for (int i = 0; i < 5; i++) {
        assertEquals(true, solution.contains(i));
    }
  }

  /**
   * Prueba con un grafo de 10 nodos.
   */
  @Test 
  public void test10node() {
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    graph.add(new ArrayList<>(Arrays.asList(1, 2)));
    graph.add(new ArrayList<>(Arrays.asList(0, 3, 4)));
    graph.add(new ArrayList<>(Arrays.asList(0, 5, 6)));
    graph.add(new ArrayList<>(Arrays.asList(1)));
    graph.add(new ArrayList<>(Arrays.asList(1)));
    graph.add(new ArrayList<>(Arrays.asList(2)));
    graph.add(new ArrayList<>(Arrays.asList(2, 7, 8, 9)));
    graph.add(new ArrayList<>(Arrays.asList(6)));
    graph.add(new ArrayList<>(Arrays.asList(6)));
    graph.add(new ArrayList<>(Arrays.asList(6)));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(10, solution.size());
    for (int i = 0; i < 10; i++) {
        assertEquals(true, solution.contains(i));
    }
  }

  /**
   * Prueba con un grafo de 20 nodos.
   */
  @Test 
  public void test20node() {
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
        graph.add(new ArrayList<>());
    }
    graph.get(0).addAll(Arrays.asList(1, 2));
    graph.get(1).addAll(Arrays.asList(0, 3, 4));
    graph.get(2).addAll(Arrays.asList(0, 5, 6));
    graph.get(3).addAll(Arrays.asList(1, 7, 8));
    graph.get(4).addAll(Arrays.asList(1, 9, 10));
    graph.get(5).addAll(Arrays.asList(2, 11, 12));
    graph.get(6).addAll(Arrays.asList(2, 13, 14));
    graph.get(7).addAll(Arrays.asList(3));
    graph.get(8).addAll(Arrays.asList(3));
    graph.get(9).addAll(Arrays.asList(4));
    graph.get(10).addAll(Arrays.asList(4));
    graph.get(11).addAll(Arrays.asList(5));
    graph.get(12).addAll(Arrays.asList(5));
    graph.get(13).addAll(Arrays.asList(6));
    graph.get(14).addAll(Arrays.asList(6, 15, 16, 17, 18, 19));
    graph.get(15).addAll(Arrays.asList(14));
    graph.get(16).addAll(Arrays.asList(14));
    graph.get(17).addAll(Arrays.asList(14));
    graph.get(18).addAll(Arrays.asList(14));
    graph.get(19).addAll(Arrays.asList(14));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(20, solution.size());
    for (int i = 0; i < 20; i++) {
        assertEquals(true, solution.contains(i));
    }
  }

  /**
   * Prueba con un grafo de 30 nodos.
   */
  @Test 
  public void test30node() {
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < 30; i++) {
        graph.add(new ArrayList<>());
    }
    graph.get(0).addAll(Arrays.asList(1, 2));
    graph.get(1).addAll(Arrays.asList(0, 3, 4));
    graph.get(2).addAll(Arrays.asList(0, 5, 6));
    graph.get(3).addAll(Arrays.asList(1, 7, 8));
    graph.get(4).addAll(Arrays.asList(1, 9, 10));
    graph.get(5).addAll(Arrays.asList(2, 11, 12));
    graph.get(6).addAll(Arrays.asList(2, 13, 14));
    graph.get(7).addAll(Arrays.asList(3, 15, 16));
    graph.get(8).addAll(Arrays.asList(3, 17, 18));
    graph.get(9).addAll(Arrays.asList(4, 19, 20));
    graph.get(10).addAll(Arrays.asList(4, 21, 22));
    graph.get(11).addAll(Arrays.asList(5, 23, 24));
    graph.get(12).addAll(Arrays.asList(5, 25, 26));
    graph.get(13).addAll(Arrays.asList(6, 27, 28));
    graph.get(14).addAll(Arrays.asList(6, 29));
    graph.get(15).addAll(Arrays.asList(7));
    graph.get(16).addAll(Arrays.asList(7));
    graph.get(17).addAll(Arrays.asList(8));
    graph.get(18).addAll(Arrays.asList(8));
    graph.get(19).addAll(Arrays.asList(9));
    graph.get(20).addAll(Arrays.asList(9));
    graph.get(21).addAll(Arrays.asList(10));
    graph.get(22).addAll(Arrays.asList(10));
    graph.get(23).addAll(Arrays.asList(11));
    graph.get(24).addAll(Arrays.asList(11));
    graph.get(25).addAll(Arrays.asList(12));
    graph.get(26).addAll(Arrays.asList(12));
    graph.get(27).addAll(Arrays.asList(13));
    graph.get(28).addAll(Arrays.asList(13));
    graph.get(29).addAll(Arrays.asList(14));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(30, solution.size());
    for (int i = 0; i < 30; i++) {
        assertEquals(true, solution.contains(i));
    }
  }

  /**
   * Prueba con un grafo de 40 nodos.
   */
  @Test 
  public void test40node() {
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < 40; i++) {
        graph.add(new ArrayList<>());
    }
    graph.get(0).addAll(Arrays.asList(1, 2));
    graph.get(1).addAll(Arrays.asList(0, 3, 4));
    graph.get(2).addAll(Arrays.asList(0, 5, 6));
    graph.get(3).addAll(Arrays.asList(1, 7, 8));
    graph.get(4).addAll(Arrays.asList(1, 9, 10));
    graph.get(5).addAll(Arrays.asList(2, 11, 12));
    graph.get(6).addAll(Arrays.asList(2, 13, 14));
    graph.get(7).addAll(Arrays.asList(3, 15, 16));
    graph.get(8).addAll(Arrays.asList(3, 17, 18));
    graph.get(9).addAll(Arrays.asList(4, 19, 20));
    graph.get(10).addAll(Arrays.asList(4, 21, 22));
    graph.get(11).addAll(Arrays.asList(5, 23, 24));
    graph.get(12).addAll(Arrays.asList(5, 25, 26));
    graph.get(13).addAll(Arrays.asList(6, 27, 28));
    graph.get(14).addAll(Arrays.asList(6, 29, 30));
    graph.get(15).addAll(Arrays.asList(7, 31, 32));
    graph.get(16).addAll(Arrays.asList(7, 33, 34));
    graph.get(17).addAll(Arrays.asList(8, 35, 36));
    graph.get(18).addAll(Arrays.asList(8, 37, 38, 39));
    graph.get(19).addAll(Arrays.asList(9));
    graph.get(20).addAll(Arrays.asList(9));
    graph.get(21).addAll(Arrays.asList(10));
    graph.get(22).addAll(Arrays.asList(10));
    graph.get(23).addAll(Arrays.asList(11));
    graph.get(24).addAll(Arrays.asList(11));
    graph.get(25).addAll(Arrays.asList(12));
    graph.get(26).addAll(Arrays.asList(12));
    graph.get(27).addAll(Arrays.asList(13));
    graph.get(28).addAll(Arrays.asList(13));
    graph.get(29).addAll(Arrays.asList(14));
    graph.get(30).addAll(Arrays.asList(14));
    graph.get(31).addAll(Arrays.asList(15));
    graph.get(32).addAll(Arrays.asList(15));
    graph.get(33).addAll(Arrays.asList(16));
    graph.get(34).addAll(Arrays.asList(16));
    graph.get(35).addAll(Arrays.asList(17));
    graph.get(36).addAll(Arrays.asList(17));
    graph.get(37).addAll(Arrays.asList(18));
    graph.get(38).addAll(Arrays.asList(18));
    graph.get(39).addAll(Arrays.asList(18));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(40, solution.size());
    for (int i = 0; i < 40; i++) {
        assertEquals(true, solution.contains(i));
    }
  }

  /**
   * Prueba con un grafo de 50 nodos.
   */
  @Test 
  public void test50node() {
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
        graph.add(new ArrayList<>());
    }
    graph.get(0).addAll(Arrays.asList(1, 2));
    graph.get(1).addAll(Arrays.asList(0, 3, 4));
    graph.get(2).addAll(Arrays.asList(0, 5, 6));
    graph.get(3).addAll(Arrays.asList(1, 7, 8));
    graph.get(4).addAll(Arrays.asList(1, 9, 10));
    graph.get(5).addAll(Arrays.asList(2, 11, 12));
    graph.get(6).addAll(Arrays.asList(2, 13, 14));
    graph.get(7).addAll(Arrays.asList(3, 15, 16));
    graph.get(8).addAll(Arrays.asList(3, 17, 18));
    graph.get(9).addAll(Arrays.asList(4, 19, 20));
    graph.get(10).addAll(Arrays.asList(4, 21, 22));
    graph.get(11).addAll(Arrays.asList(5, 23, 24));
    graph.get(12).addAll(Arrays.asList(5, 25, 26));
    graph.get(13).addAll(Arrays.asList(6, 27, 28));
    graph.get(14).addAll(Arrays.asList(6, 29, 30));
    graph.get(15).addAll(Arrays.asList(7, 31, 32));
    graph.get(16).addAll(Arrays.asList(7, 33, 34));
    graph.get(17).addAll(Arrays.asList(8, 35, 36));
    graph.get(18).addAll(Arrays.asList(8, 37, 38));
    graph.get(19).addAll(Arrays.asList(9, 39, 40));
    graph.get(20).addAll(Arrays.asList(9, 41, 42));
    graph.get(21).addAll(Arrays.asList(10, 43, 44));
    graph.get(22).addAll(Arrays.asList(10, 45, 46));
    graph.get(23).addAll(Arrays.asList(11, 47, 48));
    graph.get(24).addAll(Arrays.asList(11, 49));
    graph.get(25).addAll(Arrays.asList(12));
    graph.get(26).addAll(Arrays.asList(12));
    graph.get(27).addAll(Arrays.asList(13));
    graph.get(28).addAll(Arrays.asList(13));
    graph.get(29).addAll(Arrays.asList(14));
    graph.get(30).addAll(Arrays.asList(14));
    graph.get(31).addAll(Arrays.asList(15));
    graph.get(32).addAll(Arrays.asList(15));
    graph.get(33).addAll(Arrays.asList(16));
    graph.get(34).addAll(Arrays.asList(16));
    graph.get(35).addAll(Arrays.asList(17));
    graph.get(36).addAll(Arrays.asList(17));
    graph.get(37).addAll(Arrays.asList(18));
    graph.get(38).addAll(Arrays.asList(18));
    graph.get(39).addAll(Arrays.asList(19));
    graph.get(40).addAll(Arrays.asList(19));
    graph.get(41).addAll(Arrays.asList(20));
    graph.get(42).addAll(Arrays.asList(20));
    graph.get(43).addAll(Arrays.asList(21));
    graph.get(44).addAll(Arrays.asList(21));
    graph.get(45).addAll(Arrays.asList(22));
    graph.get(46).addAll(Arrays.asList(22));
    graph.get(47).addAll(Arrays.asList(23));
    graph.get(48).addAll(Arrays.asList(23));
    graph.get(49).addAll(Arrays.asList(24));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(50, solution.size());
    for (int i = 0; i < 50; i++) {
        assertEquals(true, solution.contains(i));
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
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> DFS.dfs(graph));
  }

  /**
   * Prueba con un grafo de un solo nodo.
   */
  @Test
  public void testSingleNode() {
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    graph.add(new ArrayList<>());
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(1, solution.size());
    assertEquals(0, (int)solution.get(0));
  }

  /**
   * Prueba con un grafo de un solo nodo que forma un ciclo.
   */
  @Test
  public void testSingleNodeLoop() {
    ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    graph.add(new ArrayList<>(Arrays.asList(0)));
    ArrayList<Integer> solution = DFS.dfs(graph);
    assertEquals(1, solution.size());
    assertEquals(0, (int)solution.get(0));
  }
}
