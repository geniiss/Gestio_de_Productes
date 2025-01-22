package views;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import controladores.CntrlView;
import structures.Pair;
import structures.Paths;

/**
 * Vista principal de la aplicación.
 */
public class MainView extends JPanel {
    /**
     * Instancia única de la clase CntrlView.
     */
    private static final CntrlView cntrlView = CntrlView.getInstance();
    /**
     * Panel donde se muestran los supermercados.
     */
    private JPanel supermarketPanel; // Panel donde se muestran los supermercados
    /**
     * Indica si los supermercados han sido cargados.
     */
    private boolean loadedSupers;
    /**
     * Mapa de supermercados.
     */
    private Map<String, JPanel> supermarketMap = new HashMap<>();
    /**
     * Mapa de labels de supermercados.
     */
    private Map<String, JLabel> supermarketLabels = new HashMap<>();

    /**
     * Filtra los supermercados según el texto de búsqueda.
     * @param searchText Texto de búsqueda.
     */
    private void filterSupermarkets(String searchText) {
        // Limpiar el panel de supermercados
        supermarketPanel.removeAll();
        supermarketPanel.setLayout(new GridLayout(0, 3, 10, 10));
        supermarketPanel.setBackground(Color.WHITE);
        supermarketPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ArrayList<Pair<String,JPanel>> supermercatsCoincidents = new ArrayList<>();
    
        // Iterar sobre los supermercados para buscar coincidencias
        for (Map.Entry<String, JPanel> entry : supermarketMap.entrySet()) {
            String name = entry.getKey();
            JPanel supermarket = entry.getValue();
    
            if (name.toLowerCase().contains(searchText)) {
                supermercatsCoincidents.add(new Pair<>(name, supermarket));
            }
        }

        if (supermercatsCoincidents.isEmpty()) {
            // Mostrar un mensaje si no hay coincidencias
            supermarketPanel.setLayout(new BorderLayout());
            JLabel noResultsLabel = new JLabel("No hi ha supermercats disponibles", SwingConstants.CENTER);
            noResultsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            noResultsLabel.setForeground(Color.GRAY);
            supermarketPanel.add(noResultsLabel);
        }

        // Ordenar la lista de supermercados coincidentes alfabéticamente por nombre
        supermercatsCoincidents.sort(Comparator.comparing(pair -> pair.getFirst().toLowerCase()));

        // Añadir los supermercados coincidentes al panel
        for (Pair<String, JPanel> pair : supermercatsCoincidents) {
            supermarketPanel.add(pair.getSecond());
        }
    
        // Actualizar la vista
        supermarketPanel.revalidate();
        supermarketPanel.repaint();
    }
    

    /**
     * Constructor de la clase MainView.
     * Inicializa el controlador y la interfaz de usuario.
     */
    public MainView() {
        loadedSupers = false;
        initUI();
    }

    /**
     * Repinta la interfaz de usuario.
     * Limpia todos los componentes del panel principal y reconstruye la interfaz.
     */
    public void repaintUI() {
        this.removeAll(); // Limpia todos los componentes del panel principal
        supermarketMap.clear();
        supermarketLabels.clear();

        if (!loadedSupers){
          try {
            cntrlView.loadSupermercats();
          } catch (Exception i) {
            JOptionPane.showMessageDialog(this, "Els supermercats no s'han carregat correctament: " + i.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
          loadedSupers = true;
        }

        initUI(); // Reconstruye la interfaz
        this.revalidate();
        this.repaint();
    }

    /**
     * Crea un JTextField con un tamaño específico.
     * @param size Tamaño del JTextField.
     * @return JTextField creado.
     */
    public JTextField createJTextField(int size) {
        JTextField textField = new JTextField(size);
        textField.setMargin(new Insets(5, 10, 5, 10));
        return textField;
    }

    /**
     * Inicializa la interfaz de usuario.
     * Configura el layout, los paneles y los componentes de la vista principal.
     */
    private void initUI() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(240, 240, 240)); // Fondo gris claro
    
        // Panel superior (topPanel)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout()); // Cambia a BorderLayout para organizar en zonas
        topPanel.setBackground(new Color(220, 228, 238));
    
        // Título
        JLabel label = new JLabel("SUPERMERCATS DISPONIBLES", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setForeground(new Color(45, 45, 45)); // Color de texto más oscuro
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Espaciado alrededor del título
        topPanel.add(label, BorderLayout.NORTH);
    
        // Subpanel para la barra de búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(new Color(220, 228, 238));
    
        JLabel textLabel = new JLabel("Busca el teu supermercat:");
        searchPanel.add(textLabel);
    
        // Campo de texto para buscar
        JTextField textField = createJTextField(20);
        textField.setToolTipText("Nom del supermercat");
        searchPanel.add(textField);
    
        topPanel.add(searchPanel, BorderLayout.SOUTH);
    
        // Añade el topPanel completo al contenedor principal
        this.add(topPanel, BorderLayout.NORTH);
    
        // Panel para añadir supermercado
        JPanel addSupermarketPanel = new JPanel();
        addSupermarketPanel.setLayout(new BorderLayout());
        addSupermarketPanel.setBackground(new Color(240, 240, 240));
    
        // Botón para añadir supermercado
        JButton addButton = new JButton("AFEGIR SUPERMERCAT");
        addButton.setPreferredSize(new Dimension(200, 50));
        addButton.setFont(new Font("Arial", Font.BOLD, 18));
        addButton.setBackground(new Color(100, 200, 100)); // Fondo verde
        addButton.setForeground(Color.WHITE); // Texto blanco
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createLineBorder(new Color(80, 160, 80), 2));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cntrlView.getGestionarSupermercatView().editarSupermercat("", "");
                cntrlView.showGestionarSupermercatView();
            }
        });
        addSupermarketPanel.add(addButton, BorderLayout.CENTER);
    
        // Carga inicial de supermercados
        chargeSupermercats();
    
        // Coloca todo en el panel principal
        this.add(addSupermarketPanel, BorderLayout.SOUTH);
    
        // Agregar funcionalidad de búsqueda
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String searchText = textField.getText().toLowerCase(); // Texto en minúsculas
                filterSupermarkets(searchText);
            }
        });
    
        // Focus al campo de texto
        SwingUtilities.invokeLater(() -> textField.requestFocusInWindow());
    }

    /**
     * Carga los supermercados existentes y los muestra en el panel.
     */
    private void chargeSupermercats() {
    supermarketPanel = new JPanel();
    supermarketPanel.setLayout(new GridLayout(0, 3, 10, 10));
    supermarketPanel.setBackground(Color.WHITE);
    supermarketPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Cargar los supermercados existentes
    ArrayList<Pair<String, String>> nomsIIconesSupermercats = cntrlView.getNomIIconaSupermercats();

    for (int i = 0; i < nomsIIconesSupermercats.size(); i++) {
        String nom = nomsIIconesSupermercats.get(i).getFirst();
        String iconPath = nomsIIconesSupermercats.get(i).getSecond();
        if (iconPath.isEmpty()) iconPath = Paths.DEFAULT_ICON;
        if (!supermarketMap.containsKey(nom)) {
            addSupermarket(supermarketPanel, nom, iconPath);
        }
    }

    if(supermarketMap.isEmpty()) {
        // Mostrar un mensaje si no hay coincidencias
        supermarketPanel.setLayout(new BorderLayout());
        JLabel noResultsLabel = new JLabel("No hi ha supermercats disponibles", SwingConstants.CENTER);
        noResultsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        noResultsLabel.setForeground(new Color(100, 100, 100)); // Color de texto más oscuro
        supermarketPanel.add(noResultsLabel);
    }

    // Ordenar los supermercados en el panel alfabéticamente
    ArrayList<JPanel> sortedPanels = new ArrayList<>(supermarketMap.values());
    sortedPanels.sort(Comparator.comparing(panel -> {
      JLabel label = (JLabel) ((Container) panel.getComponent(1)).getComponent(0);
      return label.getText().toLowerCase();
    }));

    // Añadir los supermercados ordenados al panel
    for (JPanel panel : sortedPanels) {
      supermarketPanel.add(panel);
    }

    // JScrollPane
    JScrollPane scrollPane = new JScrollPane(supermarketPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Sin borde
    // Mejora del scroll
    scrollPane.getVerticalScrollBar().setUnitIncrement(20);
    scrollPane.getVerticalScrollBar().setBlockIncrement(100);


    // JScrollPane para el centro del layout principal
    this.add(scrollPane, BorderLayout.CENTER);
}


    /**
     * Redimensiona un icono de imagen.
     * @param iconPath Ruta del icono.
     * @param width Ancho deseado.
     * @param height Alto deseado.
     * @return ImageIcon redimensionado.
     */
    private ImageIcon resizeImageIcon(String iconPath, int width, int height) {
        ImageIcon icon = new ImageIcon(iconPath);
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    /**
     * Añade un supermercado al panel.
     * @param supermarketPanel Panel donde se añadirá el supermercado.
     * @param name Nombre del supermercado.
     * @param iconPath Ruta del icono del supermercado.
     */
    private void addSupermarket(JPanel supermarketPanel, String name, String iconPath) {
        // Verificar si el supermercado ya existe
        if (supermarketMap.containsKey(name)) {
            JOptionPane.showMessageDialog(this, "El supermercat \"" + name + "\" ja existeix.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Si existe salimos
        }
        // Crear un JPanel para cada supermercado
        JPanel supermarket = new JPanel();
        supermarket.setLayout(new BorderLayout());
        supermarket.setBackground(new Color(238, 238, 238));

        // Cargar el icono
        ImageIcon icon = resizeImageIcon(iconPath, 200, 200); // Redimensionar el icono
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setPreferredSize(new Dimension(80, 80));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200))); // Borde suave alrededor del icono
        iconLabel.addMouseListener(new java.awt.event.MouseAdapter() {
          @Override
          public void mouseEntered(java.awt.event.MouseEvent evt) {
            supermarket.setBackground(Color.LIGHT_GRAY);
          }
          @Override
          public void mouseExited(java.awt.event.MouseEvent evt) {
            supermarket.setBackground(new Color(238, 238, 238));
          }
        });

        supermarket.add(iconLabel, BorderLayout.CENTER);

        // Crear el panel inferior con botones y nombre
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(240, 240, 240));

        // Crear y almacenar una referencia del nameLabel
        JLabel nameLabel = new JLabel(name, JLabel.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setForeground(new Color(50, 50, 50)); // Color de texto más oscuro
        bottomPanel.add(nameLabel, BorderLayout.NORTH);

        // Panel para botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Botón de editar
        JButton editButton = new JButton("Editar");
        editButton.setPreferredSize(new Dimension(0, 40));
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        editButton.setBackground(new Color(200, 200, 255)); // Color azul claro
        editButton.setFocusPainted(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String icon = iconPath.isEmpty() ? Paths.DEFAULT_ICON : iconPath;

                // Envía a la vista de AddSupermercatView
                cntrlView.getGestionarSupermercatView().editarSupermercat(name, icon);
                cntrlView.showGestionarSupermercatView();
            }
        });

        // Botón de eliminar
        JButton deleteButton = new JButton("Eliminar");
        editButton.setPreferredSize(new Dimension(0, 40));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(255, 200, 200)); // Color rojo claro
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar cuadro de confirmación
                int confirm = JOptionPane.showConfirmDialog(
                        MainView.this,
                        "Estàs segur que vols eliminar el supermercat \"" + name + "\"?",
                        "Confirmació d'eliminació",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // Eliminar el supermercado de la vista
                    cntrlView.hidePopUps();
                    supermarketPanel.remove(supermarket);
                    supermarketMap.remove(name); // Eliminar el supermercado del mapa
                    supermarketLabels.remove(name); // Eliminar el label del mapa
                    cntrlView.eliminarSupermercat(name); // Eliminar el supermercado de la base de datos
                    supermarketPanel.revalidate();
                    supermarketPanel.repaint();
                }
            }
        });

        // Agregar botones al panel de botones
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Agregar el panel de botones al panel inferior
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar el panel inferior al supermercado
        supermarket.add(bottomPanel, BorderLayout.SOUTH);

        // Almacenar los supermercados y labels en los mapas para acceso posterior
        supermarketMap.put(name, supermarket);
        supermarketLabels.put(name, nameLabel);

        // Añadir el supermercado al panel
        supermarketPanel.add(supermarket);
        supermarketPanel.revalidate(); // Revalidar el panel para que los cambios se apliquen
        supermarketPanel.repaint(); 

        // Icono con doble clic para cambiar de vista
        iconLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Doble clic
                    cntrlView.changeToSuper(name);
                    cntrlView.setNomSupermercatActual(name); // Set the current supermarket name
                    cntrlView.showPrestatgeriaView();
                }
            }
        });
    }
}
