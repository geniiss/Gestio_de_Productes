/**
 * Main class for the supermarket product distribution application.
 * This class provides a command-line interface for managing products and their relationships.
 * It allows users to select algorithms, consult product relationships, add, remove, and modify products, 
 * and view solutions and their costs.
 * 
 * Methods:
 * - main(String[] args): Entry point of the application. Displays the main menu and handles user input.
 * - seleccionarAlgoritme(Scanner scanner): Allows the user to select an algorithm for product distribution.
 * - consultarRelacioProducte(Scanner scanner): Consults the relationship between two products.
 * - consultarProducte(Scanner scanner): Consults information about a specific product.
 * - consultarProductes(): Lists all products.
 * - afegirProducte(Scanner scanner): Adds a new product to the system.
 * - eliminarProducte(Scanner scanner): Removes a product from the system.
 * - modificarRelacioProducte(Scanner scanner): Modifies the relationship between two products.
 * - consultarCostSolucio(): Consults the cost of the current solution.
 * - consultarSolucio(): Consults the current solution.
 */

import controladores.CntrlView;
import javax.swing.*;



public class Main {

    /**
     * Entrada de la aplicación.
     * @param args
     */
    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
      CntrlView cntrlView = CntrlView.getInstance();
      cntrlView.initializeViews(); // Inicializar vistas después de que CntrlView esté listo
      cntrlView.setWindowVisible();
      cntrlView.showMainView();
      });
    }
}