package tests;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.IOException;


import algoritmo.BruteForce2;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


/**
* Clase de prueba para BruteForceGenerator.
*
* Esta clase contiene varios casos de prueba para verificar la funcionalidad y la solidez de BruteForceGenerator.
*/
public class TestBruteForce2 {
  /**
   * Instancia de BruteForceGenerator que se utilizará en las pruebas.
   */
  static BruteForce2 bfg;

  /**
   * Número de productos en la matriz de relaciones.
   */
  static int numProd;

  /**
   * Reinicia la instancia de BruteForceGenerator antes de cada prueba.
   */
  @Before
  public void ferReset() {
    bfg.reset();
  }

  /**
   * Inicializa la instancia de BruteForceGenerator antes de todas las pruebas.
   */
  @BeforeClass
  static public void setUp() {
    bfg = BruteForce2.getInstance();
  }

  /**
   * Carga los datos de prueba desde un archivo.
   * @param fileName Nombre del archivo que contiene los datos de prueba.
   * @throws IOException si hay un problema al leer el archivo.
   */
  
  private void loadTestData(String fileName) throws IOException {
    List<String> lines = Files.readAllLines(Paths.get(fileName));
    numProd = Integer.parseInt(lines.get(0).trim());

    // Entrada de matriz de adyacencia
    ArrayList<ArrayList<Integer>> adjMatrix = new ArrayList<>(numProd);
    for (int i = 1; i <= numProd; i++) {
        ArrayList<Integer> row = new ArrayList<>(numProd);
        for (String value : lines.get(i).trim().split(" ")) {
            row.add(Integer.valueOf(value));
        }
        adjMatrix.add(row);
    }

    // Necesario para el funcionamiento de getSolution()
    bfg.setProductRelations(adjMatrix);
  }


  /**
   * Verifica que la solución generada sea válida.
   * @param solution Solución generada por el algoritmo.
   * @return true si la solución es válida, false en caso contrario.
   */
  private boolean verificarSolution(ArrayList<Integer> solution) {
        // Casos base
        if (solution.isEmpty()) return true;
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
     * Verifica si la solución dada contiene todos los productos exactamente una vez.
     * @param solucio
     */
    private void checkSolucioCorrecta(ArrayList<Integer> solucio) {
      // Generar la solució i veure que és correcta
      boolean solucioCorrecta = verificarSolution(solucio);
      assertEquals("La solució no té tots els productes exactament." ,true, solucioCorrecta);
  }

  /**
  * Ejecuta una prueba con los datos de prueba cargados.
  */
  private void execTest() {
    ArrayList<Integer> solution = bfg.getSolution();
    checkSolucioCorrecta(solution);
    //no comprova el cost optim, perque el cost optim es generat pel bruteforce
  }

  //--------------------------------------------------------------------------------
  //------------------------------TESTOS OPTIMALITAT--------------------------------
  //--------------------------------------------------------------------------------
  
  /**
   * Prueba con un solo producto.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testSolucio1prod() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/1prod.txt");
    execTest();
  }
  
  /**
   * Prueba con dos productos.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testSolucio2prod() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/2prod.txt");
    execTest();
  }
  
  /**
   * Prueba con tres productos.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testSolucio3prod() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/3prod.txt");
    execTest();
  }
  
  /**
   * Prueba con cinco productos.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testSolucio5prod() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/5prod.txt");
    execTest();
  }
  
  /**
   * Prueba con siete productos.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testSolucio7prod() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/7prod.txt");
    execTest();
  }
  
  /**
   * Prueba con nueve productos.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testSolucio9prod() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/9prod.txt");
    execTest();
  }
  
  /**
   * Prueba con diez productos.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testSolucio10prod() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/10prod.txt");
    execTest();
  }
 
  /**
   * Prueba con diez productos.
   * @throws IOException si hay un problema al leer el archivo. 
   */
  @Test
  public void testSolucio10prod2() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/10prod2.txt");
    execTest();
  }
  //17 productes triga massa temps


  //--------------------------------------------------------------------------------
  //------------------------TESTOS ROBUSTESA----------------------------------------
  //--------------------------------------------------------------------------------


  /**
   * Prueba que el algoritmo no genere la solución dos veces.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testNotGenerateTwice() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/5prod.txt");

      // Mesura el temps necessari per generar la solució la primera vegada
    long startTimeFirst = System.nanoTime();
    bfg.getSolution();
    long endTimeFirst = System.nanoTime();
    long durationFirst = endTimeFirst - startTimeFirst;

      // Mesura el temps necessari per generar la solució la segona vegada
    long startTimeSecond = System.nanoTime();
    bfg.getSolution();
    long endTimeSecond = System.nanoTime();
    long durationSecond = endTimeSecond - startTimeSecond;

        // Verifica que la segona durada és menor que la primera durada
    assertEquals("És possible que s'estigui generant un altre cop la solució.", true, durationSecond < durationFirst);
  }

  /**
   * Prueba con una matriz de relaciones nula.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testNullMatrixThrowsException() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> {
      bfg.setProductRelations(null);
    });
  }

  /**
   * Prueba con una matriz de relaciones vacía.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testGetSolutionWithoutSetProductRelations() throws IOException {
    assertThrows(IllegalStateException.class, () -> {
      bfg.getSolution();
    });
  }
  
  /**
   * Prueba con una matriz de relaciones mezcladas.
   */
  @Test
  public void testSetProductRelationsSortedThrowsException() {
    assertThrows(UnsupportedOperationException.class, () -> {
      bfg.setProductRelationsSorted(null);
    });
  }


  //--------------------------------------------------------------------------------
  //----------------------------TESTOS EXTREMS--------------------------------------
  //--------------------------------------------------------------------------------

  /**
   * Prueba a obtener una solucion con una matriz de relaciones vacía.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testGetSolutionNoRelations() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/all0.txt");
    execTest();
  }

  /**
   * Prueba a obtener una solucion con una matriz de relaciones llena.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testGetSolutionFullRelations() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/all100.txt");
    execTest();
  }

  /**
   * Prueba a obtener una solucion con una matriz de relaciones con pocas relaciones.
   * @throws IOException si hay un problema al leer el archivo.
   */
  @Test
  public void testSoluciofewRelations() throws IOException {
    loadTestData("./FONT/tests/inputs/algorisme/fewRelations.txt");
    execTest();
  }
}
