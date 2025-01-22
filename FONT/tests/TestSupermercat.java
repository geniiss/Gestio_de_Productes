package tests;

import dades.Supermercat;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import structures.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.assertThrows;

/**
 * Clase de pruebas para la clase Supermercat.
 */
public class TestSupermercat {
    /**
     * PATH de los inputs de los supermercados.
     */
    private static final String PATH = "./FONT/tests/inputs/supermercat/";

    /**
     * Supermercado de pruebas.
     */
    Supermercat supermercat;

    /**
     * Relaciones esperadas de los productos.
     */
    ArrayList<ArrayList<Integer>> relacionsEsperades;

    /**
     * Número de productos en el supermercado.
     */
    int numProd;

    /**
     * Lee un archivo de productos y los da de alta en el supermercado.
     * @param fileName Nombre del archivo que contiene los productos.
     */
    private void altaProducteFile(String fileName) {
      try (BufferedReader br = new BufferedReader(new FileReader(PATH+fileName))) {
        String line;
        int numLinies = -1; // La primera línia indica quantes línies hi ha per als productes
        
        if ((line = br.readLine()) != null) {
            numLinies = Integer.parseInt(line.trim()); // Llegim el nombre de línies
        }
        
        // Llegim els productes
        for (int i = 0; i < numLinies; i++) {
            line = br.readLine();
            if (line == null) break;
            
            // Separar la línia en parts
            String[] parts = line.split("\"");
            
            if (parts.length > 1) {
                // Llegir les dues strings
                String nom = parts[1];
                String descripcio = parts[3];
                
                // La resta (float i enters) després de les cometes
                String remaining = parts[4].trim();
                String[] numbers = remaining.split(" ");
                
                // Llegir el float
                float preu = Float.parseFloat(numbers[0]);
                
                // Llegir la llista d'enters
                List<Integer> relacions = new ArrayList<>();
                for (int j = 1; j < numbers.length; j++) {
                    relacions.add(Integer.parseInt(numbers[j]));
                }
                
                // Imprimir els resultats (o fer la teva pròpia lògica amb les dades)
                supermercat.altaProducte(nom, descripcio, preu, "",(ArrayList<Integer>) relacions);
            }
        }
        
        // Llegim la matriu d'enters
        relacionsEsperades = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] nums = line.trim().split(" ");
            ArrayList<Integer> fila = new ArrayList<>();
            
            for (String num : nums) {
                fila.add(Integer.valueOf(num));
            }
            
            relacionsEsperades.add(fila);
        }
          
      } catch (IOException e) {
          System.err.println("Error al llegir el fitxer: " + e.getMessage());
      } catch (NumberFormatException e) {
          System.err.println("Error en el format del número: " + e.getMessage());
      }
    }

    /**
     * Verifica que las relaciones de productos sean las esperadas.
     */
    private void checkAlta() {
      ArrayList<ArrayList<Integer>> relacions = supermercat.getProductRelations();
      for (int i = 0; i < relacions.size(); i++) {
        assertEquals("No compleix que les relacions siguin les esperades", relacionsEsperades.get(i), relacions.get(i));
      }
    }
    
    /**
     * Verifica la corrección de las relaciones de productos.
     * @param relacions Lista de relaciones de productos.
     */
    private void checkCorrectesa(ArrayList<ArrayList<Integer>> relacions) {
      for (int i = 0; i < relacions.size(); i++) {
        for (int j = 0; j < relacions.get(i).size(); j++) {
          assertEquals("Matriu no quadrada", relacions.size(), relacions.get(i).size());
          assertEquals("Relació incorrecta", relacions.get(i).get(j), relacions.get(j).get(i));
          if (i == j) assertEquals("Relació incorrecta", -1, relacions.get(i).get(j).intValue());
        }
      }
    }

    /**
     * Verifica que las relaciones de productos ordenadas sean correctas.
     * @param relacionsSorted Lista de relaciones de productos ordenadas.
     */
    private void checkCorrectesaSorted(ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted) {
      for (int i = 0; i < relacionsSorted.size(); i++) {
        int last = relacionsSorted.get(i).get(0).getFirst();
        for (int j = 1; j < relacionsSorted.get(i).size(); j++) {
          assertEquals("Fila no ordenada", true, last >= relacionsSorted.get(i).get(j).getFirst());
          last = relacionsSorted.get(i).get(j).getFirst();
        }
      }
    }

    /**
     * Configuración inicial antes de cada prueba.
     */
    @Before
    public void setUp() {
      supermercat = new Supermercat("Mercahome");
      checkAlta();
    }

    //--------------------------------TESTOS FUNCIONAMENT NORMAL--------------------------------

    /**
     * Prueba de alta de 4 productos.
     */
    @Test
    public void testAlta4Prod() {
      altaProducteFile("altaProductes4.txt");
      checkAlta();
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de alta de 6 productos.
     */
    @Test
    public void testAlta6Prod() {
      altaProducteFile("altaProductes6.txt");
      checkAlta();      
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de alta de 8 productos.
     */
    @Test
    public void testAlta8Prod() {
      altaProducteFile("altaProductes8.txt");
      checkAlta();
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de alta de 10 productos.
     */
    @Test
    public void testAlta10Prod() {
      altaProducteFile("altaProductes10.txt");
      checkAlta();
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de alta de 12 productos.
     */
    @Test
    public void testAlta12Prod() {
      altaProducteFile("altaProductes12.txt");
      checkAlta();
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de baja del primer producto.
     */
    @Test
    public void testBaixaPrimer()  {
      altaProducteFile("altaProductes12.txt");
      supermercat.baixaProducte("Macarrons");
      assertEquals("Nombre de productes incorrecte",11, supermercat.getNumProductes());
      ArrayList<ArrayList<Integer>> relacions = supermercat.getProductRelations();
      checkCorrectesa(relacions);
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de baja de un producto en medio de la lista.
     */
    @Test
    public void testBaixaMig()  {
      altaProducteFile("altaProductes12.txt");
      supermercat.baixaProducte("Pollastre");
      assertEquals("Nombre de productes incorrecte",11, supermercat.getNumProductes());
      ArrayList<ArrayList<Integer>> relacions = supermercat.getProductRelations();
      checkCorrectesa(relacions);
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de baja del último producto.
     */
    @Test
    public void testBaixaUltim()  {
      altaProducteFile("altaProductes12.txt");
      supermercat.baixaProducte("Galetes");
      assertEquals("Nombre de productes incorrecte",11, supermercat.getNumProductes());
      ArrayList<ArrayList<Integer>> relacions = supermercat.getProductRelations();
      checkCorrectesa(relacions);
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de establecer una disposición normal de productos.
     */
    @Test
    public void testSetNormalDisposition() {
      altaProducteFile("altaProductes12.txt");
      supermercat.setDisposition(new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11)));
      assertEquals("Disposició incorrecta",12, supermercat.getDisposition().size());
      ArrayList<ArrayList<Integer>> relacions = supermercat.getProductRelations();
      checkCorrectesa(relacions);
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }


    //--------------------------------TESTOS EXTREMS--------------------------------------------

    /**
     * Prueba de baja de un producto inexistente.
     */
    @Test
    public void testBaixaInexistent()  {
      altaProducteFile("altaProductes12.txt");
      assertThrows(IllegalArgumentException.class, () -> supermercat.baixaProducte("Inexistent"));
      ArrayList<ArrayList<Integer>> relacions = supermercat.getProductRelations();
      checkCorrectesa(relacions);
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de borrar un producto sin productos en el supermercado.
     */
    @Test
    public void testEsborrarSenseProductes()  {
      assertThrows(IllegalArgumentException.class, () -> supermercat.baixaProducte("Inexistent"));
      ArrayList<ArrayList<Integer>> relacions = supermercat.getProductRelations();
      checkCorrectesa(relacions);
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de alta de un producto existente.
     */
    @Test
    public void testAltaProducteExistent() {
      altaProducteFile("altaProductes12.txt");
      assertThrows(IllegalArgumentException.class, () -> supermercat.altaProducte("Vi", "descripcio", 1.0f, "", new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12))));
      ArrayList<ArrayList<Integer>> relacions = supermercat.getProductRelations();
      checkCorrectesa(relacions);
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de baja de todos los productos.
     */
    @Test 
    public void testBaixaTot() {
      altaProducteFile("altaProductes12.txt");
      ArrayList<String> productes = new ArrayList<>(Arrays.asList("Macarrons", "Tonyina", "Formatge", "Pa", "Vi", "Pollastre", "Enciam", "Pebrot", "Mel", "Ou", "Xocolata", "Galetes"));
      for (int i = 0; i < 12; i++) {
        supermercat.baixaProducte(productes.get(i));
      }
      assertEquals("Nombre de productes incorrecte",0, supermercat.getNumProductes());
      ArrayList<ArrayList<Integer>> relacions = supermercat.getProductRelations();
      checkCorrectesa(relacions);
      ArrayList<ArrayList<Pair<Integer,Integer>>> relacionsSorted = supermercat.getProductRelationsSorted();
      checkCorrectesaSorted(relacionsSorted);
    }

    /**
     * Prueba de establecer una disposición sin todos los productos.
     */
    @Test 
    public void testSetDispositionWithoutAllProds () {
      altaProducteFile("altaProductes12.txt");
      assertThrows(IllegalArgumentException.class, () -> supermercat.setDisposition(new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10))));
    }

    /**
     * Prueba de establecer una disposición con productos repetidos.
     */
    @Test 
    public void testSetDispositionWithRepeatedProds () {
      altaProducteFile("altaProductes12.txt");
      assertThrows(IllegalArgumentException.class, () -> supermercat.setDisposition(new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,10))));
    }
}