package views;

import controladores.CntrlView;
import structures.Paths;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.text.Normalizer;

//por implementar
//sort por nombre o precio, mas bajo o mas alto
//busqueda por substring 
//live update mientras busca --- pulir la busqueda
//eliminar desde view
//boton de confimar borrado + popup -- falta eliminarlo debidamente
//encuadrar todo


/**
    * Vista de la lista de productos.
 */
public class LlistaProductesView extends JPanel {
    /**
     * Conjunto de nombres de productos a modificar.
     */
    private Set<String> modificateRel;
    /**
     * Indica si se está modificando la relación entre dos productos.
     */
    private boolean isModifying;
    /**
     * Indica si se está pintando la interfaz.
     */
    private boolean isPainting;
    /**
     * Valor de la barra de desplazamiento.
     */
    private int scrollBarValue;
    /**
     * Lista de productos a mostrar.
     */
    ArrayList<ArrayList<String>> screen; //pantalla
    /**
     * Panel inferior.
     */
    private JPanel bottomPanel;
    /**
     * Botón para añadir un nuevo producto.
     */
    private JButton addButton;
    /**
     * Botón para modificar la relación entre dos productos.
     */
    private JButton modifyRelationButton;
    /**
     * Nombre del producto.
     */
    private String nomProducto;
    /**
     * ScrollPane.
     */
    private JScrollPane scrollPane;
    /**
     * Campo de texto superior.
     */
    private JTextField topTextField;
    /**
     * Controlador de la vista.
     */
    private CntrlView cntrlView = CntrlView.getInstance();
    
    /**
     * Constructor de la clase LlistaProductesView.
     * Inicializa la interfaz de usuario.
     */
    public LlistaProductesView() {
        isModifying = false;
        isPainting = false;
        scrollBarValue = 0;
        modificateRel = new HashSet<String>();
        initUI();
    }
    
    /**
     * Actualiza la interfaz de usuario.
     */
    public void repaintUI() {
        this.removeAll(); // Limpia todos los componentes del panel principal
        initUI(); // Reconstruye la interfaz
        this.revalidate();
        this.repaint();
    }

    /**
     * Normaliza strings eliminando acentos y mayúsculas.
     * 
     * @param s  String cualquiera.
     * @return String normalizado.
     */
    private static String normalizeString(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        s = s.toLowerCase();
        return s;
    }

    /**
     * Crea un botón personalizado.
     * 
     * @param name   El nombre del botón.
     * @param font   La fuente del texto del botón.
     * @param color  El color de fondo del botón.
     * @param focus  Si el botón debe tener enfoque pintado.
     * @param cursor El cursor que se mostrará cuando el ratón esté sobre el botón.
     * @return El botón personalizado.
     */
    private JButton createJButton(String name, Font font, Color color, Boolean focus, Cursor cursor) {
        JButton ret = new JButton(name);
        ret.setFont(font);
        ret.setBackground(color);
        ret.setFocusPainted(focus);
        ret.setCursor(cursor);
        return ret;
    }

    /**
     * Crea un JLabel con un icono redimensionado.
     * 
     * @param Path  La ruta de la imagen del icono.
     * @param sizeX El ancho deseado del icono.
     * @param sizeY El alto deseado del icono.
     * @return El JLabel con el icono redimensionado.
     */
    private JLabel createIconLabel(String Path, int sizeX, int sizeY) {
        // Load the image from a file or URL
        ImageIcon originalIcon = new ImageIcon(Path); // Make sure to replace with actual image path
        
        // Resize the image icon
        Image img = originalIcon.getImage(); // Get the Image from the ImageIcon
        Image resizedImage = img.getScaledInstance(sizeX, sizeY, Image.SCALE_SMOOTH); 
        
        // Create a new ImageIcon with the resized image
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(resizedIcon);
        return iconLabel;
    }

    /**
     * Crea un JTextField personalizado.
     * 
     * @param size El tamaño del JTextField.
     * @return El JTextField personalizado.
     */
    private JTextField createJTextField(int size) {
        JTextField textField = new JTextField(size);
        //personalizacion (descomentar)
        //fuente
        //textField.setFont(new Font("Arial", Font.BOLD, 24));
        //color texto
        //textField.setForeground(Color.green);
        //fondo 
        //textField.setBackground(Color.YELLOW);
        //margen
        textField.setMargin(new Insets(5, 10, 5, 10));
        //mensaje
        textField.setToolTipText("Nom del producte");

        return textField;
    }

    /**
    * Busca un producte por el nombre.
    * 
    * @param name Nom del producte a buscar.
    * @param info ArrayList de la información de cada producto.
    * @return ArrayList de información actualizado con el producto encontrado.
    */
    private ArrayList<ArrayList<String>> buscaProducte(String name, ArrayList<ArrayList<String>> info) {
        //implementar busqueda por substring
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
        int i = 0;
        while (i < info.size()) {
            //esborra les " " i els accents
            String t = normalizeString(info.get(i).get(0).substring(1, info.get(i).get(0).length() - 1).toLowerCase());
            String n = normalizeString(name);
            if (t.contains(n)) {
                ret.add(info.get(i));
            }
            i++;
        }
        return ret;
    }

    /**
     * Actualiza el contenido del JScrollPane con la información proporcionada.
     * 
     * @param info La información de los productos a mostrar.
     */
    private void updateScrollPane() {

      if(screen.isEmpty()) {
        JLabel noProductsLabel = new JLabel("No hi ha productes per mostrar", SwingConstants.CENTER);
        noProductsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        noProductsLabel.setForeground(Color.GRAY);
        scrollPane.setViewportView(noProductsLabel);
        return;
      }

      JPanel contentPanel = new JPanel();
      contentPanel.setLayout(new GridBagLayout());
      contentPanel.setBackground(scrollPane.getBackground());
      contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(5, 5, 5, 5);
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.NORTH;
      gbc.weightx = 1.0;
      gbc.weighty = 0.0;
      
      final int PANEL_HEIGHT = 50;
      
      for (int i = 0; i < screen.size(); i++) {
        ArrayList<String> s = screen.get(i);
        String nom = s.get(0).substring(1, s.get(0).length() - 1);
        JPanel rowPanel = new JPanel();

        JLabel nameLabel = new JLabel(nom, SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        String descripcio = s.get(1).substring(1, s.get(1).length() - 1);
        JLabel descriptionLabel = new JLabel(descripcio, SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        String preu = s.get(2);
        JLabel priceLabel = new JLabel(preu + " €", SwingConstants.CENTER);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        if(isModifying && modificateRel.contains(nom)) {
          rowPanel.setBackground(Color.DARK_GRAY);
          nameLabel.setForeground(Color.WHITE);
          descriptionLabel.setForeground(Color.WHITE);
          priceLabel.setForeground(Color.WHITE);
        }
        else rowPanel.setBackground(scrollPane.getBackground());
        MouseAdapter ModifyRow = new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            if (isModifying && !modificateRel.contains(nom)) rowPanel.setBackground(Color.LIGHT_GRAY); // Highlight row on hover
          }

          @Override
          public void mouseExited(MouseEvent e) {
            if (isModifying && !modificateRel.contains(nom)) rowPanel.setBackground(scrollPane.getBackground()); // Reset background color
          }

          @Override
          public void mouseClicked(MouseEvent e) {
            if (isModifying) {
              if (modificateRel.contains(nom)) {
                modificateRel.remove(nom);
                rowPanel.setBackground(scrollPane.getBackground());
              } else {
                modificateRel.add(nom);
                rowPanel.setBackground(Color.DARK_GRAY);
                nameLabel.setForeground(Color.WHITE);
                descriptionLabel.setForeground(Color.WHITE);
                priceLabel.setForeground(Color.WHITE);
              }
              if(modificateRel.size() == 2) {
                String[] productes = modificateRel.toArray(new String[0]);
                String relacioAntiga = String.valueOf(cntrlView.getRelacio(productes[0], productes[1]));
                JTextField newRelationField = new JTextField(relacioAntiga, 20);
                int option = JOptionPane.showConfirmDialog(
                  LlistaProductesView.this,
                  new Object[]{"Relació:", newRelationField},
                  "Modificar relació",
                  JOptionPane.OK_CANCEL_OPTION,
                  JOptionPane.PLAIN_MESSAGE
                );

                if (option == JOptionPane.OK_OPTION) {
                  String novaRelacio = newRelationField.getText();
                  cntrlView.setRelacio(productes[0], productes[1], Integer.valueOf(novaRelacio));
                }
                isModifying = false;
                isPainting = true;
                modificateRel.clear();
                updateScrollPane();
                isPainting = false;
                bottomPanel.removeAll();
                bottomPanel.setLayout(new FlowLayout());
                bottomPanel.add(addButton);
                bottomPanel.add(modifyRelationButton);
                bottomPanel.revalidate();
                bottomPanel.repaint();
              }
            }
          }
        };
        rowPanel.setLayout(new GridLayout(1, 6, 10, 10)); // 1 fila, 6 columnes, espaiat de 10px

        // ICONA
        JLabel icon = new JLabel();
        String iconPath = s.get(3).substring(1, s.get(3).length() - 1);
        if (iconPath.equals("")) iconPath = Paths.DEFAULT_ICON;
        icon = createIconLabel(iconPath, PANEL_HEIGHT, PANEL_HEIGHT);
        icon.setHorizontalAlignment(SwingConstants.CENTER);
        icon.addMouseListener(ModifyRow);
        rowPanel.add(icon);
    
        // NOM
        nameLabel.setPreferredSize(new Dimension(100, PANEL_HEIGHT));
        nameLabel.addMouseListener(ModifyRow);
        rowPanel.add(nameLabel);
    
        // DESCRIPCIÓ
        descriptionLabel.setPreferredSize(new Dimension(150, PANEL_HEIGHT));
        descriptionLabel.addMouseListener(ModifyRow);
        rowPanel.add(descriptionLabel);
    
        // PREU
        priceLabel.setPreferredSize(new Dimension(100, PANEL_HEIGHT));
        priceLabel.addMouseListener(ModifyRow);
        rowPanel.add(priceLabel);
    
        // BOTÓ EDITA
        JButton editButton = new JButton("Edita");
        editButton.setFont(new Font("Arial", Font.PLAIN, 18));
        editButton.setBackground(new Color(200, 200, 255)); // Color azul claro
        editButton.setFocusPainted(false);
        final String finalIconPath = iconPath; // Hace falta para que funcione correctamente el ActionListener
        editButton.addActionListener((_) -> {
          double preuDouble = Double.parseDouble(preu);
          cntrlView.getGestionarProducteView().editarProducte(nom, preuDouble, descripcio, finalIconPath);
          cntrlView.showGestionarProducteView();
        });
        rowPanel.add(editButton);
    
        // BOTÓ ELIMINA
        JButton deleteButton = new JButton("Elimina");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 18));
        deleteButton.setBackground(new Color(255, 200, 200)); // Color rojo claro
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener((_) -> {
          int confirm = JOptionPane.showConfirmDialog(
              LlistaProductesView.this,
              "Estàs segur que vols eliminar el producte " + s.get(0) + "?",
              "Confirmació d'eliminació",
              JOptionPane.YES_NO_OPTION,
              JOptionPane.WARNING_MESSAGE
          );
    
          if (confirm == JOptionPane.YES_OPTION) {
            cntrlView.baixaProducte(nom);
            screen = cntrlView.getInfoProductes();
            updateScrollPane();
          }
        });
        rowPanel.add(deleteButton);
        gbc.gridy = i;
        gbc.ipady = 10;
        contentPanel.add(rowPanel, gbc);
      }
  
      // Fa que el JPanel s'adapti al contenidor
      gbc.gridy++;
      gbc.weighty = 1.0;
      contentPanel.add(Box.createGlue(), gbc);
      contentPanel.setPreferredSize(new Dimension(scrollPane.getWidth(), contentPanel.getPreferredSize().height));
  
      // Actualitza el scrollPane
      scrollPane.setViewportView(contentPanel);
      scrollPane.getVerticalScrollBar().setValue(scrollBarValue); // Set the scroll position to the top
      scrollPane.revalidate();
      scrollPane.repaint();
  }
  

    /**
     * Inicializa la interfaz de usuario.
     */
    private void initUI() {
        //si no existe ningun super no se ejecuta
        setLayout(new BorderLayout());
    
        // Panel superior (topPanel)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout()); // Cambia a BorderLayout para dividir en zonas
        topPanel.setBackground(new Color(220, 228, 238));
    
        // Subpanel para los botones de navegación
        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navigationPanel.setBackground(new Color(220, 228, 238));
    
        // Botón de retorno
        JButton backButton = createJButton("⬅", new Font("SansSerif", Font.BOLD, 25), 
                new Color(173, 216, 230), false, new Cursor(Cursor.HAND_CURSOR));
        backButton.setPreferredSize(new Dimension(80, 50));
        backButton.addActionListener((_) -> {
            cntrlView.showPrestatgeriaView();
        });
        navigationPanel.add(backButton);
    
        // Botón HOME
        JButton homeButton = createJButton("HOME", new Font("SansSerif", Font.BOLD, 18), 
                new Color(173, 216, 230), false, new Cursor(Cursor.HAND_CURSOR));
        homeButton.setPreferredSize(new Dimension(100, 50));
        homeButton.addActionListener((_) -> {
            cntrlView.showMainView();
        });
        navigationPanel.add(homeButton);

        // Título del panel de navegación
        String nomSupermercat = cntrlView.getNomSupermercatActual();
        JLabel titleLabel = new JLabel("Llistat de productes de " + nomSupermercat, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(220, 228, 238));
        titlePanel.add(titleLabel, BorderLayout.EAST);
        titlePanel.setPreferredSize(new Dimension(950, 50));
        navigationPanel.add(titlePanel, BorderLayout.CENTER);
        navigationPanel.add(Box.createHorizontalGlue()); // Ocupa el resto del espacio hacia la derecha
    
        // Añade el panel de navegación al topPanel
        topPanel.add(navigationPanel, BorderLayout.NORTH);
    
        // Subpanel para la barra de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(220, 228, 238));
    
        JLabel textLabel = new JLabel("Busca el producte:");
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        searchPanel.add(textLabel);

        topTextField = createJTextField(10);
        topTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        topTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                nomProducto = topTextField.getText();
                ArrayList<ArrayList<String>> prod = cntrlView.getInfoProductes();
                screen = buscaProducte(nomProducto, prod);
                updateScrollPane();
            }
        });
        searchPanel.add(topTextField);
    
        // Añade el panel de búsqueda al topPanel
        topPanel.add(searchPanel, BorderLayout.SOUTH);
    
        // Añade el topPanel completo al contenedor principal
        add(topPanel, BorderLayout.NORTH);
    
        scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
          @Override
          public void adjustmentValueChanged(AdjustmentEvent e) {
            if (isPainting) return;
            scrollBarValue = scrollPane.getVerticalScrollBar().getValue();
          }
        });
        // Mejora del scroll
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setBlockIncrement(100);
        add(scrollPane, BorderLayout.CENTER);
       
        // Panel inferior (bottomPanel)
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(new Color(220, 228, 238));
    
        // Afegir nou producte
        addButton = new JButton("Afegir nou producte");
        addButton.setPreferredSize(new Dimension(270, 50));
        addButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        addButton.setFocusPainted(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cntrlView.getGestionarProducteView().editarProducte("", 0, "", "");
                cntrlView.showGestionarProducteView();
            }
        });
        bottomPanel.add(addButton);

        // Modificar relació entre dos productes
        modifyRelationButton = new JButton("Modificar relació entre dos productes");
        modifyRelationButton.setPreferredSize(new Dimension(400, 50));
        modifyRelationButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        modifyRelationButton.setFocusPainted(false);
        modifyRelationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isModifying = true;
                bottomPanel.removeAll();
                bottomPanel.setLayout(new FlowLayout());
                JLabel label = new JLabel("Selecciona dos productes per modificar la seva relació");
                label.setFont(new Font("SansSerif", Font.BOLD, 14));
                JButton cancelButton = new JButton("Cancel·lar");
                cancelButton.setPreferredSize(new Dimension(170, 50));
                cancelButton.setFont(new Font("SansSerif", Font.BOLD, 16));
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) { 
                        isModifying = false;
                        modificateRel.clear();
                        isPainting = true;
                        updateScrollPane();
                        isPainting = false;
                        bottomPanel.removeAll();
                        bottomPanel.setLayout(new FlowLayout());
                        bottomPanel.add(addButton);
                        bottomPanel.add(modifyRelationButton);
                        bottomPanel.revalidate();
                        bottomPanel.repaint();
                    }
                });
                bottomPanel.add(label, BorderLayout.NORTH);
                bottomPanel.add(cancelButton, BorderLayout.SOUTH);
                bottomPanel.revalidate();
                bottomPanel.repaint();
                // Aquí puedes agregar la lógica para modificar la relación entre dos productos
                // Por ejemplo, podrías mostrar una nueva vista o un cuadro de diálogo
            }
        });
        bottomPanel.add(modifyRelationButton);

        add(bottomPanel, BorderLayout.SOUTH);

        try {
            screen = cntrlView.getInfoProductes();
            updateScrollPane();
        } catch (Exception e) {

        }

        //Focus al camp de text
        SwingUtilities.invokeLater(() -> topTextField.requestFocusInWindow());
    }
}
