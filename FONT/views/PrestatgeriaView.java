package views;

import controladores.CntrlView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.*;
import structures.Pair;
import structures.Paths;


/**
 * Vista de la prestatgeria.
*/
public class PrestatgeriaView extends JPanel {
    /**
     * Indica què s'ha de mostrar a l'ScrollPane
     */
    ArrayList<Pair<String,String>> productNamesIcons;
    /**
     * Cost de la disposició actual
     */
    int cost;
    /**
     * Panell inferior
     */
    JPanel bottomPanel;
    /**
     * Controlador de la vista
     */
    private final CntrlView cntrlView = CntrlView.getInstance();
    /**
     * Productes a intercanviar
     */
    ArrayList<String> prodsAIntercanviar;
    /**
     * Constructor de la clase PrestatgeriaView.
     * Inicializa la interfaz de usuario.
     */
    public PrestatgeriaView() {
      productNamesIcons = new ArrayList<>();
      prodsAIntercanviar = new ArrayList<>();
      initUI();
    }

    /**
     * Método que se encarga de intercambiar los productos seleccionados.
     */
    private void intercanviarProds() {
      int i = -1;
      int j = -1;
  
      // Buscar los índices de los productos seleccionados
      for (int k = 0; k < productNamesIcons.size(); ++k) {
          if (productNamesIcons.get(k).getFirst().equals(prodsAIntercanviar.get(0))) i = k;
          if (productNamesIcons.get(k).getFirst().equals(prodsAIntercanviar.get(1))) j = k;
      }
  
      // Intercambiar los productos
      Pair<String, String> aux = productNamesIcons.get(i);
      productNamesIcons.set(i, productNamesIcons.get(j));
      productNamesIcons.set(j, aux);

      cost = cntrlView.getCostThisDisp(productNamesIcons);
  
      // Actualizar la UI
      repaintWithModifiedDisp();
  
      // Limpiar la lista de selección
      prodsAIntercanviar = new ArrayList<>();
  }

    /**
     * Método que se encarga de cargar los botones en la parte inferior de la interfaz.
     */
    private void chargeButtonsBottomPanel() {

      // ComboBox per seleccionar l'algorisme

      String[] algoritmes = {"Seleccionar Algorisme","BruteForce", "BruteForce2", "Greedy", "TSPApproximation"};
      String[] descriptions = {
        "Seleccioneu un algorisme per continuar",
        "Algorisme BruteForce: cerca totes les possibles solucions (triga molt)",
        "Algorisme BruteForce2: optimització de BruteForce",
        "Algorisme Greedy: solució ràpida i eficient",
        "TSPApproximation: solució aproximada per al TSP (2-Approximation)"
      };
      
      JComboBox<String> algorithmComboBox = new JComboBox<>(algoritmes);
      algorithmComboBox.setPreferredSize(new Dimension(270, 50));
      algorithmComboBox.setFont(new Font("SansSerif", Font.BOLD, 16));
      algorithmComboBox.setSelectedIndex(0);

      // Actualitzar el tooltip segons l'element seleccionat
      algorithmComboBox.addItemListener(e -> {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            int index = algorithmComboBox.getSelectedIndex();
            algorithmComboBox.setToolTipText(descriptions[index]);
        }
      });

      JButton generateButton = new JButton("Generar");
      generateButton.setPreferredSize(new Dimension(170, 50));
      generateButton.setFont(new Font("SansSerif", Font.BOLD, 16));
      generateButton.addActionListener((_) -> {
          String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();
          if (selectedAlgorithm.equals("Seleccionar Algorisme")) {
              JOptionPane.showMessageDialog(this, "Si us plau, seleccioni un algorisme", "Error", JOptionPane.ERROR_MESSAGE);
          }
          else{
              // Crear un JDialog para mostrar el mensaje de progreso
              JDialog loadingDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Calculant...", Dialog.ModalityType.APPLICATION_MODAL);
              JLabel loadingLabel = new JLabel("Calculant la solució, si us plau, esperi...", SwingConstants.CENTER);
              loadingDialog.add(loadingLabel);
              loadingDialog.setSize(300, 100);
              loadingDialog.setLocationRelativeTo(this);

              // Mostrar el JDialog en un nuevo hilo
              SwingUtilities.invokeLater(() -> loadingDialog.setVisible(true));

              // Iniciar el cálculo en otro hilo para no bloquear la interfaz
              new Thread(() -> {
                  try {
                      //espera 1 segundo
                      // Realizar el cálculo
                      productNamesIcons = cntrlView.getSolution(selectedAlgorithm);
                      cost = cntrlView.getCost();
                      SwingUtilities.invokeLater(this::repaintWithModifiedDisp); // Actualizar la UI en el hilo de eventos
                  } catch (Exception i) {
                      SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, "No s'ha pogut generar solució: " + i.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
                  } finally {
                      // Cerrar el JDialog una vez terminado el cálculo
                      SwingUtilities.invokeLater(() -> loadingDialog.dispose());
                  }
              }).start();
          }
      });

      JButton saveButton = new JButton("Guardar");
      saveButton.setPreferredSize(new Dimension(170, 50));
      saveButton.setFont(new Font("SansSerif", Font.BOLD, 16));
      saveButton.addActionListener((_) -> {
        cntrlView.saveCurrentDisposition(productNamesIcons,cost);
        // Mostrar un missatge de confirmació durant 1 segon
        JDialog savedDialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Guardat", Dialog.ModalityType.MODELESS);
        JLabel savedLabel = new JLabel("Disposició emmagatzemada amb èxit!", SwingConstants.CENTER);
        savedDialog.add(savedLabel);
        savedDialog.setSize(300, 100);
        savedDialog.setLocationRelativeTo(this);
        savedDialog.setUndecorated(true); // Treure els decorats de la finestra per semblar una bafarada

        // Mostrar el JDialog en un nou fil
        SwingUtilities.invokeLater(() -> savedDialog.setVisible(true));

        // Crear un Timer per difuminar el missatge
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
          savedDialog.dispose();
          ((Timer) e.getSource()).stop();
            }
        });
        timer.setRepeats(false); // Executar només una vegada després del retard inicial
        timer.start();
      });

      JButton llistat_productes = new JButton("Veure llistat productes");
      llistat_productes.setPreferredSize(new Dimension(250, 50));
      llistat_productes.setFont(new Font("SansSerif", Font.BOLD, 16));
      // Botó per passar a nova vista --> llista productes
      llistat_productes.setFocusPainted(false);
      llistat_productes.addActionListener((_) -> {
          cntrlView.showLlistaProductesView();
      });

      bottomPanel.add(algorithmComboBox);
      bottomPanel.add(generateButton);
      bottomPanel.add(saveButton);
      bottomPanel.add(llistat_productes);
    }
  
    /**
     * Método que se encarga de dibujar la vista
     */
    public void repaintUI() {
      try {
        productNamesIcons = cntrlView.getProductNamesIconsDisposition();
        cost = cntrlView.getCostDisposition();
      } catch (Exception e) {
      }
      this.removeAll(); // Limpia todos los componentes del panel principal
      initUI(); // Reconstruye la interfaz
      this.revalidate();
      this.repaint();
    }

    private void repaintWithModifiedDisp() {
      this.removeAll(); // Limpia todos los componentes del panel principal
      initUI(); // Reconstruye la interfaz
      this.revalidate();
      this.repaint();
    }

    /**
     * Método que configura la interfaz de usuario.
     * Establece el layout y añade los componentes necesarios.
     */
    private void initUI() {
        // Configurar el layout
        setLayout(new BorderLayout());

        // Panell superior amb botó de retorn i títol
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(220, 228, 238));
        
        // Botó de retorn
        JButton backButton = new JButton("⬅");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 25));
        backButton.setBackground(new Color(173, 216, 230));
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(80, 50));
        backButton.addActionListener((_) -> {
            // Retorna sense guardar res
            cntrlView.showMainView();
        });
        topPanel.add(backButton, BorderLayout.WEST);
        
        // Títol
        String title = "Prestatgeria de " + cntrlView.getNomSupermercatActual();
        while (title.length() < 30) title += " ";

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Etiqueta de cost actual
        String costText = (cost == -1) ? "No hi ha disposicó": "Puntuació d'optimalitat: " + cost;  
        JLabel costLabel = new JLabel(costText, SwingConstants.CENTER);
        costLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        topPanel.add(costLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Parte central (panell en forma de quadrícula per mostrar imatges)
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridBagLayout());
        // gridPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 5px padding around the grid
        gridPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 5px gaps between components
        gbc.fill = GridBagConstraints.NONE; // Don't resize components
        gbc.weightx = 1.0;
        gbc.weighty = 0.0; // No extra vertical space for buttons
        gbc.anchor = GridBagConstraints.NORTHWEST; // Align components to the top-left of the cell

        JPanel leftAlignedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        leftAlignedPanel.setBackground(Color.WHITE);

        int screenWidth = (getParent() != null) ? getParent().getWidth() : 1908;
        int numCols = (int) (screenWidth * 11 / 1908); // Adjust the divisor to control the number of columns based on screen width
        // Exemple: Afegim imatges a la quadrícula
        for (int i = 0; i < productNamesIcons.size(); ++i) {
          Pair<String,String> prodNameIcon = productNamesIcons.get(i);
          JPanel itemPanel = new JPanel(new BorderLayout());
          JLabel imageLabel = new JLabel();
          itemPanel.setBackground(new Color(238, 238, 238));
          imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
          imageLabel.setVerticalAlignment(SwingConstants.CENTER);
          
          // Carregar la imatge i ajustar-la a la mida del JLabel
          ImageIcon originalIcon = (prodNameIcon.getSecond().equals("")) ? new ImageIcon(Paths.DEFAULT_ICON) : new ImageIcon(prodNameIcon.getSecond()); // Canvia la ruta a les teves imatges
          Image originalImage = originalIcon.getImage();
          Image scaledImage = originalImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH); // Ajusta la mida segons sigui necessari
          ImageIcon scaledIcon = new ImageIcon(scaledImage);
          
          imageLabel.setIcon(scaledIcon);
          itemPanel.add(imageLabel, BorderLayout.CENTER);

          //Intercambiar productos
          String prodName = prodNameIcon.getFirst();
          String[] prodNameSplit = prodName.split(" ");
          if (prodNameSplit.length == 3) {
            if (prodNameSplit[0].length() > prodNameSplit[2].length()){
              prodName = prodNameSplit[0] + "<br>" + prodNameSplit[1] + " " + prodNameSplit[2];
            }
            else {
              prodName = prodNameSplit[0] + " " + prodNameSplit[1] + "<br>" + prodNameSplit[2];
            }
          }
          else if (prodName.contains(" ")) {
            prodName = prodName.replace(" ", "<br>");
          }
          JLabel itemLabel = new JLabel("<html>" + prodName + "</html>", SwingConstants.CENTER);
          itemLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Ajusta el tamaño de la letra según sea necesario
          itemPanel.add(itemLabel, BorderLayout.NORTH);
          itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
              if (prodsAIntercanviar.contains(prodNameIcon.getFirst())) {
                bottomPanel.removeAll();
                chargeButtonsBottomPanel();
                bottomPanel.revalidate();
                bottomPanel.repaint();
                prodsAIntercanviar.remove(prodNameIcon.getFirst());
                itemPanel.setBackground(new Color(238, 238, 238));
                itemLabel.setForeground(Color.BLACK);
              } else {
                if(prodsAIntercanviar.size() == 0){
                  JLabel info = new JLabel("Selecciona un altre producte per intercanviar", SwingConstants.CENTER);
                  bottomPanel.removeAll();
                  bottomPanel.add(info);
                  bottomPanel.revalidate();
                  bottomPanel.repaint();
                }
                prodsAIntercanviar.add(prodNameIcon.getFirst());
                itemPanel.setBackground(Color.DARK_GRAY);
                itemLabel.setForeground(Color.WHITE);
              }
              if (prodsAIntercanviar.size() == 2) intercanviarProds();
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
              if(prodsAIntercanviar.size() == 1 && !prodNameIcon.getFirst().equals(prodsAIntercanviar.get(0))) {
                itemPanel.setBackground(Color.LIGHT_GRAY);
              } else if (prodsAIntercanviar.size() == 0) {
                itemPanel.setBackground(Color.LIGHT_GRAY);
              }
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
              if((prodsAIntercanviar.size() == 1 && !prodNameIcon.getFirst().equals(prodsAIntercanviar.get(0))) || prodsAIntercanviar.size() == 0){
                itemPanel.setBackground(new Color(238, 238, 238));
                itemLabel.setForeground(Color.BLACK);
              }
            }
          });
          if (i % (numCols*2) >= numCols) gbc.gridx = numCols - 1 - i % numCols;
          else gbc.gridx = i % numCols;
          gbc.gridy = i / numCols;
          gbc.ipady = 30; // Increase the height of the panels
          gbc.ipadx = 10; // Increase the width of the panels
          if(productNamesIcons.size() < numCols) {
            itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5)); // Add padding to each element
            leftAlignedPanel.add(itemPanel);
          }
          else gridPanel.add(itemPanel,gbc);
        }
        if (productNamesIcons.size() < numCols) {
          gbc.gridx = 0;
          gbc.gridy = 0;
          gbc.weighty = 1.0;
          gbc.weightx = numCols;
          gbc.anchor = GridBagConstraints.CENTER;
          gridPanel.add(leftAlignedPanel, gbc);
        }
        JScrollPane scrollPane = new JScrollPane(gridPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Mejora del scroll
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
        if (productNamesIcons.isEmpty()) {
          JLabel message = new JLabel("No hi ha disposició", SwingConstants.CENTER);
          message.setFont(new Font("Arial", Font.PLAIN, 20));
          message.setForeground(Color.GRAY);
          scrollPane = new JScrollPane(message, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        }

        // Parte inferior (botons)
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setPreferredSize(new Dimension(0, 55));

        chargeButtonsBottomPanel();
        
        // Afegir components al panell principal
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Método principal para probar la interfaz de usuario.
     * Crea un marco y añade una instancia de PrestatgeriaView.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Prestatgeria View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new PrestatgeriaView());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
