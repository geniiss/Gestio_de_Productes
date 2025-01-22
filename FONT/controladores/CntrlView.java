package controladores;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import structures.Pair;
import views.GestionarProducteView;
import views.GestionarSupermercatView;
import views.LlistaProductesView;
import views.MainView;
import views.PrestatgeriaView;

/**
 * La clase CntrlView es un controlador singleton que administra las vistas de la aplicación.
 * Proporciona métodos para mostrar y ocultar las vistas, y para acceder a los datos de la aplicación.
 */
public class CntrlView {
    /**
     * Vistas de la aplicación.
     */
    private GestionarSupermercatView gestionarSupermercatView;
    /**
     * Vista de lista de productos.
     */
    private LlistaProductesView llistaProductesView;
    /**
     * Vista principal.
     */
    private MainView mainView;
    /**
     * Vista de gestionar producto.
     */
    private GestionarProducteView gestionarProducteView;
    /**
     * Vista de prestatgeria.
     */
    private PrestatgeriaView prestatgeriaView;
    /**
     * Mapa de productos de supermercados.
     */
    private Map<String, Set<String>> supermarketProducts; 
    /**
     * Nombre del supermercado actual.
     */
    private String nomSupermercatActual;

    /**
     * Ventana de la aplicación.
     */
    private JFrame window;
    private JFrame gestionarSupermercatFrame;
    private JFrame gestionarProducteFrame;
    /**
     * Panel de tarjetas para cambiar entre vistas.
     */
    private JPanel cardPanel;
    /**
     * Layout de tarjetas para cambiar entre vistas.
     */
    private CardLayout cardLayout;

    /**
     * Hace visible la ventana de la aplicación.
     */
    public void setWindowVisible() {
      this.window.setVisible(true);
    }
    /**
     * Controlador de dominio de la aplicación.
     */
    private CntrlDomini cntrlDomini = CntrlDomini.getInstance();
   
    /**
     * Instancia única de la clase CntrlView.
     */
    private static final CntrlView cntrlView = new CntrlView();


    /**
     * Método para obtener la instancia de CntrlView.
     * @return la instancia única de CntrlView.
     */
    public static CntrlView getInstance() {
        return cntrlView;
    }
    
    /**
     * Constructor privado para evitar la creación de múltiples instancias.
     */
    private CntrlView() {
        // Inicializamos ventana
        window = new JFrame();
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);

        gestionarSupermercatFrame = new JFrame("Gestionar Supermercat");
        gestionarSupermercatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gestionarSupermercatFrame.setSize(800, 600);
        gestionarSupermercatFrame.setLocationRelativeTo(null);

        gestionarProducteFrame = new JFrame("Gestionar Producte");
        gestionarProducteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gestionarProducteFrame.setSize(800, 600);
        gestionarProducteFrame.setLocationRelativeTo(null);

        //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setSize(800, 600);
        window.setLocationRelativeTo(null);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        // Añadimos el panel al frame
        window.add(cardPanel);

        supermarketProducts = new HashMap<>();

        //Tancament finestra amb confirmació
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    window, 
                    "Està segur que vol tancar l'aplicació?", 
                    "Confirmació tancament", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE
                );            
                        
        
                if (confirm == JOptionPane.YES_OPTION) {
                    cntrlDomini.saveAllSupermercat();
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Inicializa las vistas de la aplicación.
     */
    public void initializeViews() {
        gestionarSupermercatView = new GestionarSupermercatView();
        llistaProductesView = new LlistaProductesView();
        mainView = new MainView();
        gestionarProducteView = new GestionarProducteView();
        prestatgeriaView = new PrestatgeriaView();

        cardPanel.add(mainView, "MainView");
        cardPanel.add(llistaProductesView, "LlistaProductesView");
        cardPanel.add(prestatgeriaView, "PrestatgeriaView");

        gestionarSupermercatFrame.add(gestionarSupermercatView);
        gestionarProducteFrame.add(gestionarProducteView);
    }
  

    // Métodos para obtener las vistas (getters)
    /**
     * Obtiene la vista de gestionar supermercado.
     * @return la vista de gestionar supermercado.
     */
    public GestionarSupermercatView getGestionarSupermercatView() {
        return gestionarSupermercatView;
    }
    
    /**
     * Obtiene el coste de una disposición.
     * @param disp la disposición.
     * @return el coste de la disposición.
     */
    public int getCostThisDisp(ArrayList<Pair<String,String>> disp) {
      return cntrlDomini.getCostThisDisp(disp);
    }

    /**
     * Obtiene la vista de lista de productos.
     * @return la vista de lista de productos.
     */
    public LlistaProductesView getLlistaProductesView() {
        return llistaProductesView;
    }

    /**
     * Obtiene la vista principal.
     * @return la vista principal.
     */
    public MainView getMainView() {
        return mainView;
    }

    /**
     * Obtiene la vista de gestionar producto.
     * @return la vista de gestionar producto.
     */
    public GestionarProducteView getGestionarProducteView() {
        return gestionarProducteView;
    }

    /**
     * Obtiene la vista de prestatgeria.
     * @return la vista de prestatgeria.
     */
    public PrestatgeriaView getPrestatgeriaView() {
        return prestatgeriaView;
    }

    // Métodos para cambiar las vistas
    /**
     * Muestra la vista principal y oculta las demás.
     */
    public void showMainView() {
        gestionarProducteFrame.setVisible(false);
        gestionarProducteFrame.setVisible(false);
        // Mostrar la vista principal y ocultar las demás
        mainView.repaintUI();
        cardLayout.show(cardPanel, "MainView");
    }

    /**
     * Muestra la vista de gestionar supermercado y oculta las demás.
     */
    public void showGestionarSupermercatView() {
        // Hacer visible el nuevo JFrame
        gestionarSupermercatFrame.setVisible(true);
    }

    /**
     * Oculta la vista de gestionar supermercado y actualiza las demás.
     */
    public void hideGestionarSupermercatView() {
        mainView.repaintUI(); // Actualizar la vista principal
        gestionarSupermercatFrame.setVisible(false);
    }

    /**
     * Oculta las ventanas emergentes.
     */
    public void hidePopUps() {
        gestionarSupermercatFrame.setVisible(false);
        gestionarProducteFrame.setVisible(false);
    }

    /**
     * Muestra la vista de prestatgeria y oculta las demás.
     */
    public void showPrestatgeriaView() {
        hidePopUps();
        prestatgeriaView.repaintUI();
        // Mostrar la vista de prestatgeria y ocultar las demás
        cardLayout.show(cardPanel, "PrestatgeriaView");
    }

    /**
     * Muestra la vista de lista de productos y oculta las demás.
     */
    public void showLlistaProductesView() {
        // Mostrar la vista de lista de productos y ocultar las demás
        llistaProductesView.repaintUI();
        cardLayout.show(cardPanel, "LlistaProductesView");
    }

    /**
     * Muestra la vista de gestioanar producto y oculta las demás.
     */
    public void showGestionarProducteView() {
        // Mostrar el nou JFrame
        gestionarProducteFrame.setVisible(true);
    }

    /**
     * Oculta la vista de gestioanar producto y actualiza las demás.
     */
    public void hideGestionarProducteView() {
        llistaProductesView.repaintUI(); // Actualizar la vista de lista de productos
        gestionarProducteFrame.setVisible(false);
    }

    /**
     * Devuelve la relación entre dos productos.
     * @param nomProd1 el nombre del primer producto.
     * @param nomProd2 el nombre del segundo producto.
     * @return la relación entre los productos.
     */
    public int getRelacio(String nomProd1, String nomProd2) {
        return cntrlDomini.consultarRelacioProducte(nomProd1, nomProd2);
    }

    /**
     * Modifica la relación entre dos productos.
     * @param nomProd1 el nombre del primer producto.
     * @param nomProd2 el nombre del segundo producto.
     * @param relacio la nueva relación entre los productos.
     */
    public void setRelacio(String nomProd1, String nomProd2, Integer relacio) {
        cntrlDomini.modificarRelacioProducte(nomProd1, nomProd2, relacio);
    }

    /**
     * Cambia el supermercado actual.
     * @param nomSupermercat el nombre del supermercado.
     */
    public void changeToSuper(String nomSupermercat) {
      cntrlDomini.setSupermercat(nomSupermercat);
    }

    /**
     * Guarda la disposición actual.
     * @param namesIcons los nombres e íconos de los productos.
     * @param cost el costo de la disposición.
     */
    public void saveCurrentDisposition(ArrayList<Pair<String,String>> namesIcons, int cost) {
      cntrlDomini.saveCurrentDisposition(namesIcons,cost);
    }

    /**
     * Obtiene el coste de la disposición.
     * @return el coste de la disposición.
     */
    public int getCostDisposition() {
      return cntrlDomini.getCostDisposition();
    }

    /**
     * Obtiene la disposición actual con los nombres e íconos de los productos.
     * @param selectedAlgorithm el algoritmo seleccionado.
     * @return los nombres e íconos de los productos de la disposición.
     */
    public ArrayList<Pair<String,String>> getSolution(String selectedAlgorithm) {
      try {
        return cntrlDomini.getNameIconSolution(selectedAlgorithm);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * Obtiene el coste de la solución.
     * @return el coste de la solución.
     */
    public int getCost() {
      return cntrlDomini.getCostSolution();
    }

    /**
     * Carga los supermercados.
     * @throws RuntimeException si hay un problema al cargar los supermercados.
     */
    public void loadSupermercats() {
      try {
        cntrlDomini.loadSupermercats();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * Obtiene los nombres e íconos de los productos.
     * @return los nombres e íconos de los productos.
     */
    public ArrayList<Pair<String,String>> getProductNamesIconsDisposition() {
      return cntrlDomini.getProductNamesIconsDisposition();
    }

    // Métodos para acceder a datos
    /**
     * Obtiene los nombres e íconos de los supermercados.
     * @return una lista de pares con los nombres e íconos de los supermercados.
     */
    public ArrayList<Pair<String,String>> getNomIIconaSupermercats() {
        return cntrlDomini.getNomIIconaSupermercats();
    }

    /**
     * Consulta la información de un producto por su nombre.
     * @param nomProducte el nombre del producto.
     * @return una lista con la información del producto.
     */
    public ArrayList<String> consultaProducte(String nomProducte) {
        return cntrlDomini.consultarProducte(nomProducte);
    }

    /**
     * Obtiene la información de todos los productos.
     * @return una lista de listas con la información de los productos.
     */
    public ArrayList<ArrayList<String>> getInfoProductes() {
        return cntrlDomini.getInfoProductes();
    }

    /**
     * Obtiene el número de productos.
     * @return el número de productos.
     */
    public Integer getNumProductes() {
        return cntrlDomini.getNumProductes();
    }

    /**
     * Añade o edita un producto. Si "nomAntic" es "", se crea un nuevo producto.
     * @param nomAntic el nombre antiguo del producto.
     * @param nomNou el nombre nuevo del producto.
     * @param iconPath la ruta del icono del producto.
     * @param preu el precio del producto.
     * @param descripcio la descripción del producto.
     * @param relacions las relaciones del producto.
     */
    public void addEditProducte(String nomAntic, String nomNou, String iconPath, Float preu, String descripcio, ArrayList<Integer> relacions) {
      cntrlDomini.addEditInfoProducte(nomAntic, nomNou, iconPath, preu, descripcio, relacions);
    }

    /**
     * Añade o edita un supermercado.
     * @param nomAntic el nombre antiguo del supermercado.
     * @param nomNou el nombre nuevo del supermercado.
     * @param iconPath la ruta del icono del supermercado.
     */
    public void addEditSupermercat(String nomAntic, String nomNou, String iconPath) {
        if (!nomAntic.equals(nomNou) && !nomAntic.equals("")) {
            cntrlDomini.swapInfoSupermercats(nomAntic, nomNou);
            cntrlDomini.setSupermercatIcon(iconPath);
            cntrlDomini.setSupermercat(nomNou);
        }
        else if (nomAntic.equals(nomNou)) cntrlDomini.setSupermercat(nomNou);
        else cntrlDomini.createSupermercat(nomNou);
        cntrlDomini.setSupermercatIcon(iconPath);
    }

    /**
     * Elimina un supermercado.
     * @param nom el nombre del supermercado a eliminar.
     */
    public void eliminarSupermercat(String nom) {
        cntrlDomini.eliminarSupermercat(nom);
    }

    /**
     * Obtiene los productos de un supermercado.
     * @param supermarketName el nombre del supermercado.
     * @return un conjunto de productos del supermercado.
     */
    public Set<String> getProductsForSupermarket(String supermarketName) {
        return supermarketProducts.getOrDefault(supermarketName, new HashSet<>());
    }

    /**
     * Obtiene el nombre de un producto en el supermercado seleccionado, dado su índice.
     * @param index el índice del producto.
     * @return el nombre del producto del supermercado.
     */
    public String getNomProducteEnSupermercat(Integer index) {
        return cntrlDomini.getNomProducteEnSupermercat(index);
    }

    /**
     * Obtiene las relaciones de un producto en el supermercado seleccionado.
     * @param nomProducte el nombre del producto.
     * @return una lista de relaciones del producto.
     */
    public ArrayList<Integer> getRelacionsProducte(String nomProducte) {
        return cntrlDomini.getRelacionsProducte(nomProducte);
    }
    /**
     * Añade un producto a un supermercado.
     * @param supermarketName el nombre del supermercado.
     * @param productName el nombre del producto.
     */
    public void addProductToSupermarket(String supermarketName, String productName) {
        supermarketProducts
            .computeIfAbsent(supermarketName, _ -> new HashSet<>())
            .add(productName);
    }

    /**
     * Elimina un producto del supermercado seleccionado.
     * @param nomProd el nombre del producto.
     */
    public void baixaProducte(String nomProd) {
        cntrlDomini.baixaProducte(nomProd);
    }

    /**
     * Obtiene el nombre del supermercado actual.
     * @return el nombre del supermercado actual.
     */
    public String getNomSupermercatActual() {
        return nomSupermercatActual;
    }

    /**
     * Establece el nombre del supermercado actual.
     * @param nomSupermercatActual el nombre del supermercado actual.
     */
    public void setNomSupermercatActual(String nomSupermercatActual) {
        this.nomSupermercatActual = nomSupermercatActual;
    }

    /**
     * Método principal para ejecutar la aplicación.
     * @param args los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        // Inicialización tardía de la instancia de CntrlDades
        SwingUtilities.invokeLater(() -> {
            CntrlView cntrlView = CntrlView.getInstance();
            cntrlView.initializeViews(); // Inicializar vistas después de que CntrlView esté listo
            cntrlView.setWindowVisible();
            cntrlView.showMainView();
        });
    }
}
