package tests;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.IOException;


import algoritmo.TSPApproximator;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * TestTSP es una clase de prueba para la clase TSPApproximator.
 * Utiliza JUnit y Mockito para probar varias funcionalidades de TSPApproximator.
 */
public class TestTSP {
  /**
   * Instancia de TSPApproximator.
   */
  static TSPApproximator tsp;
  /**
   * Ruta de los archivos de prueba.
   */
  private static final String PATH = "./FONT/tests/inputs/algorisme/";
  /**
   * Número de productos y coste óptimo.
   */
  static int numProd;
  /**
   * Coste óptimo.
   */
  static int costOptim;

  /**
   * Resetea el estado del TSPApproximator antes de cada prueba.
   */
  @Before
  public void ferReset() {
    tsp.reset();
  }

  /**
   * Configura el TSPApproximator antes de ejecutar cualquier prueba.
   */
  @BeforeClass
  static public void setUp() {
    tsp = TSPApproximator.getInstance();
  }
  
  /**
   * Carga los datos de prueba desde un archivo.
   * 
   * @param fileName El nombre del archivo que contiene los datos de prueba.
   * @param readCostOptim Indica si se debe leer el costo óptimo del archivo.
   * @throws IOException Si ocurre un error al leer el archivo.
   */
  private void loadTestData(String fileName, Boolean readCostOptim) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get(fileName));
    numProd = Integer.parseInt(lines.get(0).trim());

    // Entrada de matriz de adyacencia
    ArrayList<ArrayList<Integer>> adjMatrix = new ArrayList<>();
    int i = 1;
    for (; i <= numProd; i++) {
        ArrayList<Integer> row = new ArrayList<>(numProd);
        for (String value : lines.get(i).trim().split(" ")) {
            row.add(Integer.valueOf(value));
        }
        adjMatrix.add(row);
    }

    // Necesario para el funcionamiento de getSolution()
    tsp.setProductRelations(adjMatrix);


    // Guarda el cost òptim
    if (readCostOptim) {
      String line = lines.get(i).trim();
      costOptim = Integer.parseInt(line);
    }
  }

  /**
   * Verifica que en la solución aparezcan todos los productos solo una vez.
   * 
   * @param solution La solución a verificar.
   * @return true si la solución es correcta, false en caso contrario.
   */
  private boolean verificarSolution(ArrayList<Integer> solution) {
        int n = solution.size();
        if (n != numProd) {
            return false;
        }

        // Crear un conjunto con los números del 0 al n-1
        Set<Integer> expectedNumbers = new HashSet<>();
        for (int i = 0; i < n; i++) {
            expectedNumbers.add(i);
        }

        // Crear un conjunto con los números de la solución
        Set<Integer> solutionNumbers = new HashSet<>(solution);

        // Verificar que ambos conjuntos sean iguales
        return expectedNumbers.equals(solutionNumbers);
    }

    /**
     * Verifica que la solución dada tenga todos los productos exactamente una vez.
     * 
     * @param solucio La solución a verificar.
     */
    private void checkSolucioCorrecta(ArrayList<Integer> solucio) {
        // Generar la solució i veure que és correcta
        boolean solucioCorrecta = verificarSolution(solucio);
        assertEquals("La solució no té tots els productes exactament." ,true, solucioCorrecta);
    }

    /**
     * Verifica que el costo dado de la solución sea, como mínimo, la mitad del costo óptimo.
     * 
     * @param cost El costo de la solución a verificar.
     */
    private void checkTwoAproximation(int cost) {
      boolean twoAproximation = cost*2 >= costOptim;
      assertEquals("No compleix que sigui, com a mínim, la meitat del cost més òptim.", true, twoAproximation);
    }

    /**
     * Ejecuta la prueba verificando todo menos el costo óptimo.
     */
    private void execTest() {
      ArrayList<Integer> solucio = tsp.getSolution();
      checkSolucioCorrecta(solucio);
  }

  /**
   * Ejecuta la prueba verificando todo, incluyendo el costo óptimo.
   */
  private void execTestWithOptimCost() {
      ArrayList<Integer> solucio = tsp.getSolution();
      int cost = tsp.getCost();
      checkSolucioCorrecta(solucio);
      checkTwoAproximation(cost);
  }

  //--------------------------------------------------------------------------------
  //------------------------TESTOS CORRECTESA SOLUCIÓ-------------------------------
  //--------------------------------------------------------------------------------

  /**
   * Prueba con un producto.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test 
  public void test1prod() throws IOException {
    loadTestData(PATH + "1prod.txt", true);
    execTestWithOptimCost();
  }

  /**
   * Prueba con dos productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test
  public void test2prod() throws IOException {
    loadTestData(PATH + "2prod.txt", true);
    execTestWithOptimCost();
  }

  /**
   * Prueba con tres productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test 
  public void test3prod() throws IOException {
    loadTestData(PATH + "3prod.txt", true);
    execTestWithOptimCost();
  }

  /**
   * Prueba con cinco productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test
  public void test5prod() throws IOException {
    loadTestData(PATH + "5prod.txt", true);
    execTestWithOptimCost();
  }

  /**
   * Prueba con siete productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test 
  public void test7prod() throws IOException {
    loadTestData(PATH + "7prod.txt", true);
    execTestWithOptimCost();
  }

  /**
   * Prueba con nueve productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test 
  public void test9prod() throws IOException {
    loadTestData(PATH + "9prod.txt", true);
    execTestWithOptimCost();
  }

  /**
   * Prueba con diez productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test 
  public void test10prod() throws IOException {
    loadTestData(PATH + "10prod.txt", true);
    execTestWithOptimCost();
  }

  /**
   *Prueba con diez productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test 
  public void test10prod2() throws IOException {
    loadTestData(PATH + "10prod2.txt", true);
    execTestWithOptimCost();
  }

  /**
   * Prueba con diecisiete productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test 
  public void test17prod() throws IOException {
    loadTestData(PATH + "17prod.txt", false);
    execTest();
  }

  /**
   * Prueba con veintiseis productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test 
  public void test26prod() throws IOException {
    loadTestData(PATH + "26prod.txt", false);
    execTest();
  }

  /**
   * Prueba con cuarentaocho productos.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test 
  public void test48prod() throws IOException {
    loadTestData(PATH + "48prod.txt", false);
    execTest();
  }

  //--------------------------------------------------------------------------------
  //------------------------TESTOS ROBUSTESA----------------------------------------
  //--------------------------------------------------------------------------------
  
  /**
   * Prueba que no se genere la solución dos veces.
   */
  @Test 
  public void testNotGenerateTwice(){
    ArrayList<ArrayList<Integer>> adjMatrix = new ArrayList<>();
    adjMatrix.add(new ArrayList<>(List.of(0, 100)));
    adjMatrix.add(new ArrayList<>(List.of(100, 0)));

    tsp.setProductRelations(adjMatrix);

    // Mesura el temps necessari per generar la solució la primera vegada
    long startTimeFirst = System.currentTimeMillis();
    tsp.getSolution();
    long endTimeFirst = System.currentTimeMillis();
    long durationFirst = endTimeFirst - startTimeFirst;

    // Mesura el temps necessari per generar la solució la segona vegada
    long startTimeSecond = System.currentTimeMillis();
    tsp.getSolution();
    long endTimeSecond = System.currentTimeMillis();
    long durationSecond = endTimeSecond - startTimeSecond;

    // Verifica que la segona durada és menor que la primera durada
    assertEquals(true, durationSecond < durationFirst);
  }

  /**
   * Prueba que una matriz vacía lance una excepción.
   */
  @Test
  public void testEmptyMatrixThrowsException() {
    ArrayList<ArrayList<Integer>> adjMatrix = new ArrayList<>();
    assertThrows(IllegalArgumentException.class, () -> {
      tsp.setProductRelations(adjMatrix);
    });
  }

  /**
   * Prueba que una matriz nula lance una excepción.
   */
  @Test 
  public void testNullMatrixThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> {
      tsp.setProductRelations(null);
    });
  }

  /**
   * Prueba que setProductRelationsSorted lance una excepción.
   */
  @Test
  public void testSetProductRelationsSortedThrowsException() {
    assertThrows(UnsupportedOperationException.class, () -> {
      tsp.setProductRelationsSorted(null);
    });
  }

  /**
   * Prueba que getSolution lance una excepción si no se ha llamado a setProductRelations.
   */
  @Test 
  public void testGetSolutionWithoutSetProductRelations() {
    assertThrows(IllegalStateException.class, () -> {
      tsp.getSolution();
    });
  }

  //--------------------------------------------------------------------------------
  //----------------------------TESTOS EXTREMS--------------------------------------
  //--------------------------------------------------------------------------------
  
  /**
   * Prueba con todos los valores de la matriz en 0.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test
  public void testAll0() throws IOException {
    loadTestData(PATH + "all0.txt", true);
    execTestWithOptimCost();
  }

  /**
   * Prueba con todos los valores de la matriz en 100.
   * 
   * @throws IOException Si ocurre un error al leer el archivo de prueba.
   */
  @Test
  public void testAll100() throws IOException {
    loadTestData(PATH + "all100.txt", true);
    execTestWithOptimCost();
  }
}
