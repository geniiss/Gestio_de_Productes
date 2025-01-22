package drivers;

import algoritmo.BruteForce2;
import algoritmo.BruteForceGenerator;
import algoritmo.ComputeCost;
import algoritmo.DFS;
import algoritmo.GreedySolutionGenerator;
import algoritmo.Prim;
import algoritmo.TSPApproximator;
import controladores.CntrlAlgoritme;
import java.util.*;
import structures.Pair;

/**
 * La clase DriverAlgoritmo permite probar los algoritmos implementados en el paquete algoritmo.
 */
public class DriverAlgoritmo {
    /**
     * Scanner para leer la entrada del usuario.
     */
    public static Scanner in;
    /**
     * Controlador de algoritmo para probar los algoritmos.
     */
    public static CntrlAlgoritme calg;


    /**
     * Prueba la implementación del algoritmo de Prim.
     *
     * Este método solicita al usuario que ingrese la cantidad de vértices en el gráfico
     * y los pesos de las aristas. Luego inicializa el gráfico con los
     * pesos dados, ejecuta el algoritmo de Prim en el gráfico e imprime el árbol de expansión mínimo resultante.
     *
     * Pasos:
     * 1. Solicita al usuario que ingrese la cantidad de vértices.
     * 2. Lee los pesos de las aristas de la entrada del usuario.
     * 3. Inicializa el gráfico con los pesos dados.
     * 4. Ejecuta el algoritmo de Prim en el gráfico.
     * 5. Imprime las aristas y sus pesos en el árbol de expansión mínimo resultante.
     */
    public void testPrim() {
        System.out.println("Probando algoritmo de Prim");
        System.out.println("Introduce el número de vértices del grafo:");
        int vertices = in.nextInt();
        in.nextLine(); 

        //Inicializa y lee pesos de las aristas   
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        System.out.println("Introduce los pesos de las aristas:");
        for (int i = 0; i < vertices; i++) {
            //Inicializa una fila 
            ArrayList<Integer> row = new ArrayList<>();
            //Lee una línea de entrada y la divide por espacios
            String[] line = in.nextLine().split(" ");
            for (int j = 0; j < vertices; j++) {
                //Convierte cada peso a int y lo añade a la fila
                row.add(Integer.valueOf(line[j]));
            }
            graph.add(row);
        }

        //Instancia de la clase Prim y lo ejecuta
        ArrayList<ArrayList<Integer>> result = Prim.primsAlgorithm(graph);

        //Muestra el resultado
        System.out.println("Arista \tPeso");
        for (int i = 0; i < result.size(); i++) {
            for (int j : result.get(i)) {
                System.out.println(i + " - " + j);
            }
        }
    }


    /**
     * Este método prueba el algoritmo de búsqueda en profundidad (DFS).
     * Solicita al usuario que ingrese la cantidad de vértices en el gráfico y las aristas entre ellos.
     * Luego, el método inicializa el gráfico, lee la lista de adyacencia de la entrada del usuario
     * y ejecuta el algoritmo DFS en el gráfico.
     * Finalmente, imprime el resultado del recorrido DFS.
     *
     * Pasos:
     * 1. Solicita al usuario que ingrese la cantidad de vértices.
     * 2. Inicializa el gráfico con la cantidad de vértices dada.
     * 3. Solicita al usuario que ingrese las aristas (lista de adyacencia) del gráfico.
     * 4. Ejecuta el algoritmo DFS en el gráfico.
     * 5. Imprime el resultado del recorrido DFS.
     */
    public void testDfs() {
        System.out.println("Probando algoritmo DFS");
        System.out.println("Introduce el número de vértices del grafo:");
        int vertices = in.nextInt();
        in.nextLine();

        //Inicializa y lee adyacencias del grafo
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }
        System.out.println("Introduce dos aristas adyacentes en cada linea:");

        System.out.println("Introduce las aristas adyacentes (una por línea) y una línea vacía para terminar:");
        while (true) {
            String line = in.nextLine();
            if (line.isEmpty()) break;
            String[] parts = line.split(" ");
            int a = Integer.parseInt(parts[0]);
            int b = Integer.parseInt(parts[1]);
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        //Llama a DFS y lo ejectuta
        ArrayList<Integer> result = DFS.dfs(graph);

        //Muestra el resultado
        System.out.println("Resultado del DFS:");
        for (int node : result) {
            System.out.print(node + " ");
        }
        System.out.println();
    }


    /**
     * Prueba el algoritmo Greedy generando una solución basada en la entrada del usuario.
     *
     * El método realiza los siguientes pasos:
     * 1. Solicita al usuario que ingrese la cantidad total de productos.
     * 2. Solicita al usuario que ingrese la matriz de relación entre los productos.
     * 3. Ordena cada fila de la matriz de relación en orden descendente según el valor de la relación.
     * 4. Establece las matrices de relación ordenadas y no ordenadas en GreedySolutionGenerator.
     * 5. Genera una solución utilizando GreedySolutionGenerator.
     * 6. Imprime la solución generada y su costo.
     */
    public void testGreedy() {
        System.out.println("Probando algoritmo Greedy");
        // Entrada de valores
        System.out.print("Introduce el número de productos totales: ");
        int numProd = in.nextInt();
        
        System.out.println("Introduce la relación entre los productos (matriz de relaciones): ");
        ArrayList<ArrayList<Pair<Integer, Integer>>> relacionsOrdenades = new ArrayList<>(numProd);
        ArrayList<ArrayList<Integer>> relacions = new ArrayList<>(numProd);
        for (int i = 0; i < numProd; ++i) {
            ArrayList<Pair<Integer, Integer>> rowSorted = new ArrayList<>(numProd);
            ArrayList<Integer> row = new ArrayList<>(numProd);
            for (int j = 0; j < numProd; ++j) {
                int relacion = in.nextInt();
                row.add(relacion);
                rowSorted.add(new Pair<>(relacion, j));
            }
            relacions.add(row);
            // Ordenar cada fila en orden descendente por el valor de relación
            rowSorted.sort(Comparator.comparing(Pair<Integer, Integer>::getFirst).reversed());
            relacionsOrdenades.add(rowSorted);
        }

        GreedySolutionGenerator generator = GreedySolutionGenerator.getInstance();
        generator.setProductRelationsSorted(relacionsOrdenades);
        generator.setProductRelations(relacions);
        //System.out.println(relacionsOrdenades);

        long start= System.nanoTime();
        Object solution = generator.getSolution();
        long end = System.nanoTime();
        System.out.println("Tiempo de ejecución: " + (end - start) / 1_000_000_000.0 + " segundos");
        System.out.println("Solución generada: " + solution);
        int cost = generator.getCost();
        System.out.println("Cost de la solució: " + cost);
    }

    
    /**
     * Prueba el algoritmo de fuerza bruta generando una solución basada en la entrada del usuario.
     */
    public void testBruteForce() {
        System.out.println("Probando algoritmo de Fuerza Bruta");
        System.out.println("Introduce el número de elementos:");
        int numProd = in.nextInt();
        
        System.out.println("Introduce la relación entre los productos (matriz de relaciones): ");
        ArrayList<ArrayList<Integer>> relacions = new ArrayList<>(numProd);
        for (int i = 0; i < numProd; ++i) {
            ArrayList<Integer> row = new ArrayList<>(numProd);
            for (int j = 0; j < numProd; ++j) {
                int relacion = in.nextInt();
                row.add(relacion);
            }
            relacions.add(row);
        }
        
        //Instancia de la clase BruteForceGenerator y lo llama
        BruteForceGenerator bruteForce = BruteForceGenerator.getInstance();
        bruteForce.setProductRelations(relacions);
        long start= System.nanoTime();
        List<Integer> result = bruteForce.getSolution();
        long end = System.nanoTime();
        System.out.println("Tiempo de ejecución: " + (end - start) / 1_000_000_000.0 + " segundos");


        //Muestra el resultado
        System.out.println("Resultado del algoritmo de Fuerza Bruta: " + result);
        System.out.println("Cost: "+ bruteForce.getCost());

    }

    /**
     * Prueba el algoritmo de fuerza bruta generando una solución basada en la entrada del usuario.
     */
    public void testBruteForce2() {
      System.out.println("Probando algoritmo de Fuerza Bruta 2");
      System.out.println("Introduce el número de elementos:");
      int numProd = in.nextInt();
      
      System.out.println("Introduce la relación entre los productos (matriz de relaciones): ");
      ArrayList<ArrayList<Integer>> relacions = new ArrayList<>(numProd);
      for (int i = 0; i < numProd; ++i) {
          ArrayList<Integer> row = new ArrayList<>(numProd);
          for (int j = 0; j < numProd; ++j) {
              int relacion = in.nextInt();
              row.add(relacion);
          }
          relacions.add(row);
      }
      
      //Instancia de la clase BruteForceGenerator y lo llama
      BruteForce2 bruteForce = BruteForce2.getInstance();
      bruteForce.setProductRelations(relacions);
      long start= System.nanoTime();
      List<Integer> result = bruteForce.getSolution();
      long end = System.nanoTime();
      System.out.println("Tiempo de ejecución: " + (end - start) / 1_000_000_000.0 + " segundos");


      //Muestra el resultado
      System.out.println("Resultado del algoritmo de Fuerza Bruta: " + result);
      System.out.println("Cost: "+ bruteForce.getCost());

  }

    
    /**
     * Prueba el algoritmo aproximador del Problema del viajante (TSP).
     *
     * El método solicita al usuario que ingrese la cantidad total de productos y la matriz de relación entre los productos.
     * Luego, establece la matriz de relación en el aproximador TSP, ejecuta el algoritmo e imprime la solución generada y su costo.
     */
    public void testTSP() {
        System.out.println("Probando algoritmo TSP");
        System.out.println("Introduce el número de productos totales: ");
        int numProd = in.nextInt();
        
        System.out.println("Introduce la relación entre los productos (matriz de relaciones): ");
        ArrayList<ArrayList<Integer>> relacions = new ArrayList<>(numProd);
        for (int i = 0; i < numProd; ++i) {
            ArrayList<Integer> row = new ArrayList<>(numProd);
            for (int j = 0; j < numProd; ++j) {
                int relacion = in.nextInt();
                row.add(relacion);
            }
            relacions.add(row);
        }
        TSPApproximator tsp = TSPApproximator.getInstance();
        tsp.setProductRelations(relacions);
        long start= System.nanoTime();
        ArrayList<Integer> result = tsp.getSolution();
        long end = System.nanoTime();
        System.out.println("Tiempo de ejecución: " + (end - start) / 1_000_000_000.0 + " segundos");
        System.out.println("Resultado del algoritmo TSP: " + result);
        System.out.println("Cost: "+ tsp.getCost());
    }

    
    /**
     * Prueba el algoritmo ComputeCost calculando el costo de la disposición de un producto determinado.
     *
     * El método solicita al usuario que ingrese la cantidad total de productos, la matriz de relaciones entre los productos
     * y la disposición de los productos. Luego calcula el costo de la disposición utilizando el algoritmo ComputeCost.
     */
    public void testComputeCost() {
        System.out.println("Probando ComputeCost");
        System.out.println("Introduce el número de productos totales: ");
        int numProd = in.nextInt();
        
        System.out.println("Introduce la relación entre los productos (matriz de relaciones): ");
        ArrayList<ArrayList<Integer>> relacions = new ArrayList<>(numProd);
        for (int i = 0; i < numProd; ++i) {
            ArrayList<Integer> row = new ArrayList<>(numProd);
            for (int j = 0; j < numProd; ++j) {
                int relacion = in.nextInt();
                row.add(relacion);
            }
            relacions.add(row);
        }

        System.out.println("Introdueix la disposició dels " + numProd + " productes: ");
        ArrayList<Integer> disposition = new ArrayList<>(numProd);
        for (int i = 0; i < numProd; ++i) {
            Integer prod = in.nextInt();
            disposition.add(prod);
        }
        
        int cost = ComputeCost.getCost(disposition, relacions);
        System.out.println("Cost: "+ cost);
    }


    /**
     * Método principal para ejecutar el programa controlador y seleccionar el algoritmo a probar.
     * El método muestra las opciones disponibles para probar diferentes algoritmos y solicita al usuario que seleccione una opción.
     * Luego, llama al método de prueba correspondiente según la elección del usuario.
     * El programa controlador continúa ejecutándose hasta que el usuario elija salir.
     * 
     * @param args Argumentos de la línea de comandos.
     * @throws Exception Si ocurre un error al leer la entrada del usuario.
     */
    public static void main (String [] args) throws Exception {
        DriverAlgoritmo dor = new DriverAlgoritmo();

        System.out.println("Driver de Controlador de Algoritmo (PROP Grup 33.3)");
        metodes();

        in = new Scanner(System.in);
        String opcion;
        do {
            System.out.print("Selecciona una opción: ");
            opcion = in.nextLine();
            switch (opcion) {
                case "1":
                    dor.testPrim();
                    break;
                case "2":
                    dor.testDfs();
                    break;
                case "3":
                    dor.testGreedy();
                    break;
                case "4":
                    dor.testBruteForce();
                    break;
                case "5":
                    dor.testBruteForce2();
                    break;
                case "6":
                    dor.testTSP();
                    break;
                case "7":
                    dor.testComputeCost();
                    break;
                case "0":
                    System.out.println("Cerrando Driver");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        }
        while (!opcion.equals("0"));
        in.close();
    }


    /**
     * Muestra las opciones disponibles para probar diferentes algoritmos.
     */
    public static void metodes(){
        System.out.println("1 - Comprobacion Prim");
        System.out.println("2 - Comprobacion DFS");
        System.out.println("3 - Comprobacion Greedy");
        System.out.println("4 - Comprobacion BruteForce");
        System.out.println("5 - Comprobacion BruteForce2");
        System.out.println("6 - Comprobacion TSPApproximator");
        System.out.println("7 - Comprobacion ComputeCost");
        System.out.println("0 - Cerrando Driver");
    }
}
