// /**
//  * Main class for the supermarket product distribution application.
//  * This class provides a command-line interface for managing products and their relationships.
//  * It allows users to select algorithms, consult product relationships, add, remove, and modify products, 
//  * and view solutions and their costs.
//  * 
//  * Methods:
//  * - main(String[] args): Entry point of the application. Displays the main menu and handles user input.
//  * - seleccionarAlgoritme(Scanner scanner): Allows the user to select an algorithm for product distribution.
//  * - consultarRelacioProducte(Scanner scanner): Consults the relationship between two products.
//  * - consultarProducte(Scanner scanner): Consults information about a specific product.
//  * - consultarProductes(): Lists all products.
//  * - afegirProducte(Scanner scanner): Adds a new product to the system.
//  * - eliminarProducte(Scanner scanner): Removes a product from the system.
//  * - modificarRelacioProducte(Scanner scanner): Modifies the relationship between two products.
//  * - consultarCostSolucio(): Consults the cost of the current solution.
//  * - consultarSolucio(): Consults the current solution.
//  */

// import controladores.CntrlAlgoritme;
// import controladores.CntrlDades;
// import java.util.ArrayList;
// import java.util.Scanner;


// public class Main {

//     private static String separador = "----------------------------------------";
    
//     /**
//      * Main method that serves as the entry point for the application.
//      * It initializes a new shelf with ID 0 and provides a menu for the user to interact with.
//      * The user can select various options to manage products in a supermarket.
//      * 
//      * Options:
//      * 1. Select algorithm
//      * 2. Consult product relationship
//      * 3. Consult product
//      * 4. Consult products
//      * 5. Add product
//      * 6. Remove product
//      * 7. Modify product relationship
//      * 8. Consult solution cost
//      * 9. Consult solution
//      * 0. Exit
//      * 
//      * @param args Command line arguments
//      */
//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         //crea una nova prestatgeria ID 0;
//         CntrlDades.getInstance().supermercat("Mercahome");
//         boolean sortir = false;
//         System.out.println("Distribució de productes a un supermercat:");
//         System.out.println(separador);

//         while (!sortir) {
//             System.out.println("Seleccioneu una opció:");
//             System.out.println("1. Seleccionar algoritme");
//             System.out.println("2. Consultar relació producte");
//             System.out.println("3. Consultar producte");
//             System.out.println("4. Consultar productes");
//             System.out.println("5. Afegir producte");
//             System.out.println("6. Eliminar producte");
//             System.out.println("7. Modificar relació producte");
//             System.out.println("8. Consultar cost solució");
//             System.out.println("9. Consultar solució");
//             System.out.println("0. Sortir");
//             System.out.println(separador);

//             String opcio = scanner.nextLine();
    
//             System.out.println(separador);

//             switch (opcio) {
//                 case "1":
//                     seleccionarAlgoritme(scanner);
//                     break;
//                 case "2":
//                     consultarRelacioProducte(scanner);
//                     break;
//                 case "3":
//                     consultarProducte(scanner);
//                     break;
//                 case "4":
//                     consultarProductes();
//                     break;
//                 case "5":
//                     afegirProducte(scanner);
//                     break;
//                 case "6":
//                     eliminarProducte(scanner);
//                     break;
//                 case "7":
//                     modificarRelacioProducte(scanner);
//                     break;
//                 case "8":
//                     consultarCostSolucio();
//                     break;
//                 case "9":
//                     consultarSolucio();
//                     break;
//                 case "0":
//                     sortir = true;
//                     break;
//                 default:
//                     System.out.println("Opció no vàlida.");
//             }

//             System.out.println(separador);

//         }

//         scanner.close();
//     }


//     /**
//      * Displays a menu for selecting an algorithm and executes the corresponding algorithm based on user input.
//      *
//      * @param scanner the Scanner object used to read user input
//      *
//      * The method presents three algorithm options to the user:
//      * 1. Brute Force
//      * 2. Greedy
//      * 3. TSP Approximation
//      *
//      * Based on the user's selection, the corresponding algorithm is executed by calling the appropriate method
//      * from the CntrlAlgoritme singleton instance. If the user input does not match any of the options, 
//      * an invalid option message is displayed.
//      */
//     private static void seleccionarAlgoritme(Scanner scanner) {
//         System.out.println("Seleccioneu l'algoritme:");
//         System.out.println("1. Brute Force");
//         System.out.println("2. Greedy");
//         System.out.println("3. TSP Approximation");

//         String algoritme = scanner.nextLine();

//         switch (algoritme) {
//             case "1":
//                 System.out.println("Algoritme Brute Force seleccionat");
//                 CntrlAlgoritme.getInstance().BruteForceGenerator();
//                 break;
//             case "2":
//                 System.out.println("Algoritme Greedy seleccionat");
//                 CntrlAlgoritme.getInstance().GreedyGenerator();
//                 break;
//             case "3":
//                 System.out.println("Algoritme TSP Approximation seleccionat");
//                 CntrlAlgoritme.getInstance().TSPApproximatorGenerator();
//                 break;
//             default:
//                 System.out.println("Opció no vàlida.");
//         }
//     }


//     /**
//      * Consulta la relació entre dos productes introduïts per l'usuari.
//      *
//      * @param scanner l'objecte Scanner utilitzat per llegir l'entrada de l'usuari.
//      * 
//      * Aquesta funció demana a l'usuari que introdueixi els noms de dos productes i després consulta la relació entre ells
//      * utilitzant el mètode consultarRelacioProducte de la classe CntrlDades. Si algun dels productes no existeix, es mostra
//      * un missatge d'error.
//      */
//     private static void consultarRelacioProducte(Scanner scanner) {
//         System.out.println("Introdueix el nom del producte1: ");
//         String nomProducte1 = scanner.nextLine();
//         System.out.println("Introdueix el nom del producte2: ");
//         String nomProducte2 = scanner.nextLine();
//         int relacio;
//         try {
//             relacio = CntrlDades.getInstance().consultarRelacioProducte(nomProducte1, nomProducte2);
//         } catch (Exception e) {
//             System.out.println("Algun producte dels introduïts no existeix.");
//             return;
//         }

//         System.out.println("La relació del producte " + nomProducte1 + " amb el producte " + nomProducte2 + " és: " + relacio);
//     }


//     /**
//      * Consulta la informació d'un producte introduït per l'usuari.
//      * 
//      * @param scanner l'objecte Scanner utilitzat per llegir l'entrada de l'usuari
//      * 
//      * Aquesta funció demana a l'usuari que introdueixi el nom d'un producte i 
//      * després consulta la informació d'aquest producte utilitzant el mètode 
//      * consultarProducte de la classe CntrlDades. Si el producte no existeix, 
//      * es mostra un missatge indicant-ho. Si el producte existeix, es mostra 
//      * la informació del producte.
//      */
//     private static void consultarProducte(Scanner scanner) {
//         System.out.println("Introdueix el nom del producte:");
//         String producte = scanner.nextLine();

//         String info = CntrlDades.getInstance().consultarProducte(producte);
//         if (info.equals("-1")) System.out.println("El producte no existeix");
//         else System.out.println("Informació del producte:\n" + info);
//     }


//     /**
//      * This method retrieves a list of product names from the data controller and prints them.
//      * If there are no products, it prints a message indicating that there are no products.
//      * Otherwise, it prints the product names separated by commas.
//      */
//     private static void consultarProductes() {
//         ArrayList<String> productes = CntrlDades.getInstance().getNomProductes();
//         if (productes.isEmpty()) {
//             System.out.println("No hi ha productes.");
//         } else {
//             StringBuilder sb = new StringBuilder();
//             for (int i = 0; i < productes.size(); i++) {
//             sb.append(productes.get(i));
//             if (i < productes.size() - 1) {
//                 sb.append(", ");
//             }
//             }
//             System.out.println(sb.toString());
//         }
//     }


//     /**
//      * Adds a new product by prompting the user for product details.
//      *
//      * @param scanner the Scanner object used to read user input
//      * 
//      * The method performs the following steps:
//      * 1. Prompts the user to enter the product name.
//      * 2. Prompts the user to enter the product description.
//      * 3. Prompts the user to enter the product price and validates it as a float.
//      * 4. Prompts the user to enter the relationship with other products.
//      * 5. Adds the new product to the system using the provided details.
//      * 
//      * If the price entered is not a valid float, the method prints an error message and returns without adding the product.
//      */
//     private static void afegirProducte(Scanner scanner) {
//         // Implementar lògica per afegir producte
//         System.out.println("Introdueix el nom del producte:");
//         String name = scanner.nextLine();

//         System.out.println("Introdueix la descripcio del producte:");
//         String descrip = scanner.nextLine();

//         System.out.println("Introdueix el preu del producte:");
//         Float preu;
//         try {
//             String p = scanner.nextLine();
//             preu = Float.parseFloat(p);
//         } catch (NumberFormatException e) {
//             System.out.println("El preu ha de ser un número.");
//             return;
//         }

//         System.out.println("Introdueix la relacio amb altres productes:");
//         int n = CntrlDades.getInstance().getNumProductes();
//         ArrayList<Integer> r = new ArrayList<>();
    
//         for (int j = 0; j < n; j++) {
//             String nomProd = CntrlDades.getInstance().consultarNomProducteAmbIdx(j);
//             System.out.println("Introdueix relacio amb el producte " + nomProd + ":");
//             r.add(Integer.parseInt(scanner.nextLine()));
//         }
//         CntrlDades.getInstance().altaProducte(name, descrip, preu, r);
//         System.out.println("Producte " + name + " afegit correctament.");
//     }


//     /**
//      * Elimina un producte del sistema.
//      * 
//      * Aquesta funció demana a l'usuari que introdueixi el nom del producte que vol eliminar.
//      * Si el producte existeix, es crida al mètode baixaProducte de la classe CntrlDades per eliminar-lo.
//      * Si el producte no existeix, es captura l'excepció i es mostra un missatge indicant que el producte no existeix.
//      * 
//      * @param scanner L'objecte Scanner utilitzat per llegir l'entrada de l'usuari.
//      */
//     private static void eliminarProducte(Scanner scanner) {
//         System.out.println("Introdueix el nom del producte a eliminar:");
//         String nomProd = scanner.nextLine();
//         try {
//             CntrlDades.getInstance().baixaProducte(nomProd);
//             System.out.println("Producte eliminat correctament.");
//         } catch (Exception e) {
//             System.out.println("El producte no existeix");
//         }
//     }


//     /**
//      * Modifies the relationship between two products.
//      *
//      * This method prompts the user to input the names of two products and a new relationship value.
//      * It then attempts to update the relationship between the specified products using the provided value.
//      * If the update is successful, a confirmation message is displayed. If an error occurs, the exception
//      * message is printed.
//      *
//      * @param scanner the Scanner object used to read user input
//      */
//     private static void modificarRelacioProducte(Scanner scanner) {
//         System.out.println("Introdueix el nom del producte1:");
//         String nomProd1 = scanner.nextLine();
//         System.out.println("Introdueix el nom del producte2:");
//         String nomProd2 = scanner.nextLine();
//         System.out.println("Introdueix la nova relació entre els productes:");
//         int novaRelacio = scanner.nextInt();
//         try {
//             CntrlDades.getInstance().modificarRelacioProducte(nomProd1, nomProd2, novaRelacio);
//             System.out.println("Relació del producte modificada correctament.");
//         }
//         catch   (Exception e) {
//             System.out.println(e);
//         }
//     }


//     /**
//      * This method consults the cost of the solution.
//      * It retrieves product relations and sorted product relations from the data controller,
//      * sets them in the algorithm controller, and then calculates the solution cost.
//      * The cost of the solution is printed to the console.
//      */
//     private static void consultarCostSolucio() {
//         CntrlAlgoritme.getInstance().setProductRelations(CntrlDades.getInstance().getProductRelations());

//         try {
//             CntrlAlgoritme.getInstance().setProductRelationsSorted(CntrlDades.getInstance().getProductRelationsSorted());
//         } catch (Exception e) {}

//         int cost = CntrlAlgoritme.getInstance().getSolutionCost();
//         System.out.println("El cost de la solució és: " + cost);
//     }


//     /**
//      * This method consults the solution by performing the following steps:
//      * 1. Sets the product relations in the algorithm controller using data from the data controller.
//      * 2. Attempts to set the sorted product relations in the algorithm controller using data from the data controller.
//      *    If an exception occurs during this step, it is caught and ignored.
//      * 3. Retrieves the solution from the algorithm controller.
//      * 4. Prints the solution to the console.
//      */
//     private static void consultarSolucio() {
//         CntrlAlgoritme.getInstance().setProductRelations(CntrlDades.getInstance().getProductRelations());

//         try {
//             CntrlAlgoritme.getInstance().setProductRelationsSorted(CntrlDades.getInstance().getProductRelationsSorted());
            
//         } catch (Exception e) {}
//         ArrayList<Integer> solucio = CntrlAlgoritme.getInstance().getSolution();
//         System.out.println("La solució és:\n" + solucio);
//     }
// }