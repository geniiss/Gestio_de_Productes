package tests;

import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;

import algoritmo.ComputeCost;
import algoritmo.GreedySolutionGenerator;
import structures.Pair;

/**
* Clase de prueba para el algoritmo GreedySolutionGenerator.
* Esta clase contiene varios casos de prueba para verificar la corrección, optimalidad y solidez del algoritmo GreedySolutionGenerator.
*/
public class TestGreedy {
    /**
     * Instancia singleton de GreedySolutionGenerator.
     */
    private static GreedySolutionGenerator greedy;

    /**
     * Path de los archivos de prueba.
     */
    private static final String PATH = "./FONT/tests/inputs/algorisme/";

    // Atributs llegits en l'input

    /**
     * Matriz de adyacencia de los productos.
     */
    private ArrayList<ArrayList<Integer>> adjMatrix;

    /**
     * Matriz de adyacencia de los productos ordenada.
     */
    private ArrayList<ArrayList<Pair<Integer, Integer>>> adjMatrixSorted;

    /**
     * Coste óptimo de la solución.
     */
    private int costOptim;

    /**
     * Inicializa la instancia singleton de GreedySolutionGenerator.
     */
    @BeforeClass
    public static void setUp() {
        // Obtén la instancia singleton de GreedySolutionGenerator
        greedy = GreedySolutionGenerator.getInstance();
    }
    
    /**
     * Resetea la instancia singleton de GreedySolutionGenerator.
     */
    @Before
    public void ferReset() {
        greedy.reset();
    }

    // FUNCIONS AUXILIARS //

    /**
     * Carga los datos de prueba desde un archivo de texto.
     * @param fileName Nombre del archivo de texto.
     * @param readCostOptim Indica si se debe leer el coste óptimo del archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private void loadTestData(String fileName, boolean readCostOptim) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        int numProd = Integer.parseInt(lines.get(0).trim());
    
        adjMatrix = new ArrayList<>(numProd);
        adjMatrixSorted = new ArrayList<>(numProd);

        if (numProd == 0) {
            return;
        }
    
        // Agafa la matriu d'adjacències
        int i = 1;
        for (; i <= numProd; i++) {
            ArrayList<Pair<Integer, Integer>> rowSorted = new ArrayList<>(numProd);
            ArrayList<Integer> row = new ArrayList<>(numProd);
            int j = 0;
            for (String value : lines.get(i).trim().split(" ")) {
                Integer relation = Integer.valueOf(value);
                row.add(relation);
                rowSorted.add(new Pair<>(relation, j));
                ++j;
            }
            adjMatrix.add(row);
            rowSorted.sort(Comparator.comparing(Pair<Integer, Integer>::getFirst).reversed());
            adjMatrixSorted.add(rowSorted);
        }
        // Guarda el cost òptim
        if (readCostOptim) {
            String line = lines.get(i).trim();
            costOptim = Integer.parseInt(line);
        }
    
        // Donar les matrius d'adjacència a l'algorisme
        greedy.setProductRelations(adjMatrix);
        greedy.setProductRelationsSorted(adjMatrixSorted);
    }

    /**
     * Verifica si la solución dada contiene todos los productos exactamente una vez.
     * @param solution Solución a verificar.
     * @return true si la solución es correcta, false en caso contrario.
     */
    private boolean verificarSolution(ArrayList<Integer> solution) {
        // Casos base
        if (solution.isEmpty()) return true;
        int n = solution.size();
        if (n != adjMatrix.size()) {
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

    // CHECKS //

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
     * Verifica si el coste de la solución es correcto.
     * @param cost Coste de la solución.
     * @param solucio Solución a verificar.
     */
    private void checkCost(int cost, ArrayList<Integer> solucio) {
        // Verificar que se calcula el coste correctamente
        int costSolucio = ComputeCost.getCost(solucio, adjMatrix);
        assertEquals("El cost no s'ha calculat correctament.", costSolucio, cost);
    }

    /**
     * Verifica si el coste de la solución es, como mínimo, la mitad del coste óptimo.
     * @param cost Coste de la solución.
     */
    private void checkTwoAproximation(int cost) {
        boolean twoAproximation = cost*2 >= costOptim;
        assertEquals("No compleix que sigui, com a mínim, la meitat del cost més òptim.", true, twoAproximation);
    }

    // VERIFICADORS //
    // Verifiquen que donen els resultats que haurien de donar

    /**
     * Verifica si la solución es correcta.
     */
    private void execTest() {
        ArrayList<Integer> solucio = greedy.getSolution();
        int cost = greedy.getCost();
        checkSolucioCorrecta(solucio);
        checkCost(cost, solucio);
    }

    /**
     * Verifica si la solución es correcta y si el coste es, como mínimo, la mitad del coste óptimo.
     */
    private void execTestWithOptimCost() {
        ArrayList<Integer> solucio = greedy.getSolution();
        int cost = greedy.getCost();
        checkSolucioCorrecta(solucio);
        checkCost(cost, solucio);
        checkTwoAproximation(cost);
    }
    
    // ------------------------------------------------------------------------------ //
    // ------------------------------TESTS OPTIMALITAT------------------------------ //
    // ------------------------------------------------------------------------------ //

    /**
    * Test con un producto.
    * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution1prod() throws IOException {
        String inputFile = PATH + "1prod.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test con dos productos.
     * @throws IOException  Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution2prod() throws IOException {
        String inputFile = PATH + "2prod.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test con tres productos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution3prod() throws IOException {
        String inputFile = PATH + "3prod.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test con cinco productos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution5prod() throws IOException {
        String inputFile = PATH + "5prod.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test con siete productos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution7prod() throws IOException {
        String inputFile = PATH + "7prod.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test con nueve productos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution9prod() throws IOException {
        String inputFile = PATH + "9prod.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test con diez productos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution10prod() throws IOException {
        String inputFile = PATH + "10prod.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test con diez productos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution10prod2() throws IOException {
        String inputFile = PATH + "10prod2.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test con diecisiete productos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution17prod() throws IOException {
        String inputFile = PATH + "17prod.txt";
        loadTestData(inputFile, false); // No es verifica el costOptim

        execTest();
    }

    /**
     * Test con veintisiete productos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution26prod() throws IOException {
        String inputFile = PATH + "26prod.txt";
        loadTestData(inputFile, false); // No es verifica el costOptim

        execTest();
    }
    
    /**
     * Test con cuarenta y ocho productos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolution48prod() throws IOException {
        String inputFile = PATH + "48prod.txt";
        loadTestData(inputFile, false); // No es verifica el costOptim

        execTest();
    }

    // ------------------------------------------------------------------------------ //
    // -------------------------------TESTS ROBUSTESA------------------------------- //
    // ------------------------------------------------------------------------------ //

    /**
     * Test para no generar la solución dos veces.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testNotGenerateTwice() throws IOException {
        String inputFile = PATH + "10prod.txt";
        loadTestData(inputFile, false);

        // Mesura el temps necessari per generar la solució la primera vegada
        long startTimeFirst = System.nanoTime();
        greedy.getSolution();
        long endTimeFirst = System.nanoTime();
        long durationFirst = endTimeFirst - startTimeFirst;

        // Mesura el temps necessari per generar la solució la segona vegada
        long startTimeSecond = System.nanoTime();
        greedy.getSolution();
        long endTimeSecond = System.nanoTime();
        long durationSecond = endTimeSecond - startTimeSecond;

        // Verifica que la segona durada és menor que la primera durada
        assertEquals("És possible que s'estigui generant un altre cop la solució.", true, durationSecond < durationFirst);
    }

    /**
     * Test para verificar resultado con matriz vacia
     */
    @Test
    public void testNullMatrixThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            greedy.setProductRelations(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            greedy.setProductRelationsSorted(null);
        });
    }

    /**
     * Test para obtener solucion sin haber establecido las relaciones de productos.
     */
    @Test
    public void testGetSolutionWithoutSetProductRelations() {
        assertThrows(IllegalStateException.class, () -> {
            greedy.getSolution();
        });
    }

    /**
     * Test para verificar que al cambiar la matriz se genera una nueva solución.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolutionGeneratesSolutionsIfChangesMatrix() throws IOException {
        String inputFile = PATH + "48prod.txt";
        loadTestData(inputFile, false);

        long startTimeFirst = System.nanoTime();
        ArrayList<Integer> solucio1 = greedy.getSolution();
        long endTimeFirst = System.nanoTime();
        long durationFirst = endTimeFirst - startTimeFirst;

        // Cambiar matriu d'adjacències
        greedy.setProductRelations(adjMatrix);
        greedy.setProductRelationsSorted(adjMatrixSorted);

        long startTimeSecond = System.nanoTime();
        ArrayList<Integer> solucio2 = greedy.getSolution();
        long endTimeSecond = System.nanoTime();
        long durationSecond = endTimeSecond - startTimeSecond;

        // Tolerancia máxima
        double tolerance = 0.8;
        double maxAllowedDifference = durationFirst*tolerance;

        // Verificar que la diferencia entre las duraciones no exceda el umbral de tolerancia
        assertTrue("La diferència de temps entre les execusions és massa gran.", Math.abs(durationSecond - durationFirst) <= maxAllowedDifference);
        
        assertArrayEquals("Ha de donar la mateixa solució.", solucio2.toArray(new Integer[0]), solucio1.toArray(new Integer[0]));
    }

    // ------------------------------------------------------------------------------ //
    // --------------------------------TESTS EXTREMS-------------------------------- //
    // ------------------------------------------------------------------------------ //

    /**
     * Test para obtener solución con una matriz sin relaciones.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolutionNoRelations() throws IOException {
        String inputFile = PATH + "all0.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test para obtener solución con una matriz con todas las relaciones.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolutionFullRelations() throws IOException {
        String inputFile = PATH + "all100.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }

    /**
     * Test para obtener solución con una matriz con pocas relaciones.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Test
    public void testGetSolutionFewRelations() throws IOException {
        String inputFile = PATH + "fewRelations.txt";
        loadTestData(inputFile, true);

        execTestWithOptimCost();
    }
}