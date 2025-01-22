package tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import algoritmo.ComputeCost;

/**
* Clase de prueba para el algoritmo ComputeCost.
*
* Esta clase contiene pruebas unitarias para verificar la exactitud del algoritmo ComputeCost.
*/
public class TestComputeCost {
    /**
     * Matriz de adyacencia del grafo.
     */
    private ArrayList<ArrayList<Integer>> adjMatrix;
    /**
     * Distribución de los productos.
     */
    private ArrayList<Integer> disposicio;
    /**
     * Coste deseado.
     */
    private int costDesitjat;
    /**
     * Ruta de los archivos de prueba.
     */
    private static final String PATH = "./FONT/tests/inputs/computeCost/";
    
    /**
     * Carga los datos de prueba desde un archivo.
     * @param fileName Nombre del archivo.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private void loadTestData(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        int numProd = Integer.parseInt(lines.get(0).trim());
    
        // Inicialització de les variables globals
        adjMatrix = new ArrayList<>(numProd);
        disposicio = new ArrayList<>(numProd);
        costDesitjat = 0;

        if (numProd == 0) {
            return;
        }
    
        // Agafa la matriu d'adjacències
        int i = 1;
        for (; i <= numProd; i++) {
            ArrayList<Integer> row = new ArrayList<>(numProd);
            for (String value : lines.get(i).trim().split(" ")) {
                Integer relation = Integer.valueOf(value);
                row.add(relation);
            }
            adjMatrix.add(row);
        }
        // Llegir la distribució
        for (String value : lines.get(i).trim().split(" ")) {
            Integer product = Integer.valueOf(value);
            disposicio.add(product);
        }
        ++i; // Avança una línia
        // Guarda el cost desitjat
        String line = lines.get(i).trim();
        costDesitjat = Integer.parseInt(line);
    }

    // ------------------------------------------------------------------------------ //
    // ------------------------------TESTS OPTIMALITAT------------------------------ //
    // ------------------------------------------------------------------------------ //

    /**
     * Test con un producto.
     */
    @Test
    public void testComputeCost1prod() {
        try {
            loadTestData(PATH + "1prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test con dos productos.
     */
    @Test
    public void testComputeCost2prod() {
        try {
            loadTestData(PATH + "2prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test con tres productos.
     */
    @Test
    public void testComputeCost3prod() {
        try {
            loadTestData(PATH + "3prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test con cinco productos.
     */
    @Test
    public void testComputeCost5prod() {
        try {
            loadTestData(PATH + "5prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test con siete productos.
     */
    @Test
    public void testComputeCost7prod() {
        try {
            loadTestData(PATH + "7prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test con nueve productos.
     */
    @Test
    public void testComputeCost9prod() {
        try {
            loadTestData(PATH + "9prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test con diez productos.
     */
    @Test
    public void testComputeCost10prod() {
        try {
            loadTestData(PATH + "10prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test con diez productos.
     */
    @Test
    public void testComputeCost10prod2() {
        try {
            loadTestData(PATH + "10prod2.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test con diecisete productos.
     */
    @Test
    public void testComputeCost17prod() {
        try {
            loadTestData(PATH + "17prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test con veintiseis productos.
     */
    @Test
    public void testComputeCost26prod() {
        try {
            loadTestData(PATH + "26prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test con cuarentaocho productos.
     */
    @Test
    public void testComputeCost48prod() {
        try {
            loadTestData(PATH + "48prod.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------------------------------------------------ //
    // -------------------------------TESTS ROBUSTESA------------------------------- //
    // ------------------------------------------------------------------------------ //

    /**
     * Test para encontrar solucion sin relaciones.
     */
    @Test
    public void testGetSolutionNoRelations() {
        try {
            loadTestData(PATH + "all0.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test para encontrar solucion sin todas las relaciones al maximo.
     */
    @Test
    public void testComputeCostWithMaxNoRelations() {
        try {
            loadTestData(PATH + "all0.txt");
            costDesitjat = -1;
            int cost = ComputeCost.getCostWithMax(disposicio, adjMatrix, 0);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test para encontrar solucion con todas las relaciones.
     */
    @Test
    public void testComputeCostFullRelations() {
        try {
            loadTestData(PATH + "all100.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test para encontrar solucion con todas las relaciones al maximo.
     */
    @Test
    public void testComputeCostWithMaxFullRelations() {
        try {
            loadTestData(PATH + "all100.txt");
            int cost = ComputeCost.getCostWithMax(disposicio, adjMatrix, 500);
            costDesitjat = -1;
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test para encontrar solucion con todas las relaciones al maximo.
     */
    @Test
    public void testComputeCostWithMaxFullRelations2() {
        try {
            loadTestData(PATH + "all100.txt");
            int cost = ComputeCost.getCostWithMax(disposicio, adjMatrix, 0);
            costDesitjat = cost;
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test para encontrar solucion con todas las relaciones al maximo.
     */
    @Test
    public void testComputeCostWithMaxFullRelations3() {
        try {
            loadTestData(PATH + "all100.txt");
            int cost = ComputeCost.getCostWithMax(disposicio, adjMatrix, -5);
            costDesitjat = cost;
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test para encontrar solucion con todas las relaciones al maximo.
     */
    @Test
    public void testComputeCostWithMaxFullRelations4() {
        try {
            loadTestData(PATH + "all100.txt");
            int cost = ComputeCost.getCostWithMax(disposicio, adjMatrix, 501);
            costDesitjat = -1;
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test para encontrar solucion con todas las relaciones al maximo.
     */
    @Test
    public void testComputeCostWithMaxFullRelations5() {
        try {
            loadTestData(PATH + "all100.txt");
            int cost = ComputeCost.getCostWithMax(disposicio, adjMatrix, 200);
            costDesitjat = cost;
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test para encontrar solucion con algunas relaciones.
     */
    @Test
    public void testComputeCostFewRelations() {
        try {
            loadTestData(PATH + "fewRelations.txt");
            int cost = ComputeCost.getCost(disposicio, adjMatrix);
            assertEquals("No calcula el cost correctament." ,costDesitjat, cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}