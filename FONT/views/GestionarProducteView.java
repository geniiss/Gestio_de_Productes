/**
 * Vista para gestionar un producto.
 * Permite editar el nombre, el precio, la descripción y el icono del producto.
 */
package views;

import controladores.CntrlView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import structures.Paths;

/**
 * Vista para gestionar un producto.
 */
public class GestionarProducteView extends JPanel {

    /**
     * Nombre del producto que se está editando.
     */
    private String nomAntic;

    /**
     * Indica si el producto es nuevo o no. En el caso de que nomAntic sea "", se trata de un nuevo producto.
     */
    private Boolean esNouProducte;

    /**
     * Nuevo nombre del producto (puede ser el mismo que el anterior).
     */
    private String nomProducte;

    /**
     * Precio del producto.
     */
    private double preuProducte;

    /**
     * Descripción del producto.
     */
    private String descripcioProducte;

    /**
     * Ruta del icono del producto (puede ser "").
     * Si existe la ruta, esta pertenece a una imagen válida.
     */
    private String iconPath;

    /**
     * Relaciones del producto seleccionado.
     */
    private ArrayList<Integer> relacions;

    // Vistes adjacents //

    /**
     * Controlador de la vista que maneja las interacciones y la lógica de la interfaz de usuario.
     */
    private static final CntrlView cntrlView = CntrlView.getInstance();

    /**
     * Constructor de la clase.
     */
    public GestionarProducteView() {
        nomProducte = "";
        nomAntic = "";
        esNouProducte = true;
        preuProducte = 0.0;
        descripcioProducte = "";
        iconPath = "";
        relacions = new ArrayList<>();
    }

    /**
     * Función para editar el producto.
     * @param nom Nombre del producto.
     * @param preu Precio del producto.
     * @param descripcio Descripción del producto.
     * @param icon Ruta del icono del producto.
     */
    public void editarProducte(String nom, double preu, String descripcio, String icon) {
        nomAntic = nom;
        esNouProducte = nomAntic == "";
        nomProducte = nom;
        preuProducte = preu;
        descripcioProducte = descripcio;
        iconPath = icon;
        removeAll();
        initUI();
    }

    /**
     * Inicializa la interfaz de usuario.
     */
    private void initUI() {
        relacions.clear();
        if (!esNouProducte) relacions = cntrlView.getRelacionsProducte(nomAntic);

        // Tamaño uniforme para los campos de texto
        Dimension uniformSize = new Dimension(100, 30);

        // Panel principal
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // Panel superior con botón de retorno
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 228, 238));

        add(topPanel, BorderLayout.NORTH);

        // Panel central con layout GridBagLayout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Crear el panel para el icono y los botones de los iconos
        JPanel iconPanelContainer = new JPanel();
        iconPanelContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centrado con separación
        iconPanelContainer.setBackground(new Color(240, 248, 255));

        // Marco con icono
        JPanel iconPanel = new JPanel();
        iconPanel.setPreferredSize(new Dimension(100, 100));
        iconPanel.setBackground(new Color(173, 216, 230));
        iconPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        iconPanel.setLayout(new GridBagLayout());

        JLabel iconLabel = new JLabel();
        try {
            Image img;
            if (!iconPath.equals("")) img = ImageIO.read(new File(iconPath));
            else img = ImageIO.read(new File(Paths.DEFAULT_ICON));
            img = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            iconLabel.setIcon(new ImageIcon(img));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error carregant la icona: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        iconPanel.add(iconLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(iconPanel, gbc);

        // Panel con los botones "Cambiar icono" y "Borrar icono"
        JPanel iconButtonPanel = new JPanel();
        iconButtonPanel.setLayout(new GridLayout(2, 1, 5, 5));
        iconButtonPanel.setBackground(new Color(240, 248, 255));

        JButton changeIconButton = new JButton("Canviar icona");
        changeIconButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        changeIconButton.setBackground(new Color(200, 200, 255));
        changeIconButton.setFocusPainted(false);
        changeIconButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        changeIconButton.addActionListener((_) -> {
            //Obrir un explorador de fitxers
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            // Configurar el directori inicial
            File dataDirectory = new File(Paths.ICONES_PRODUCTES);
            if (dataDirectory.exists() && dataDirectory.isDirectory()) {
                fileChooser.setCurrentDirectory(dataDirectory);
            }
            // Detecta només imatges
            fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "png", "jpeg", "jpg"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                if (filePath.endsWith(".png") || filePath.endsWith(".jpeg") || filePath.endsWith(".jpg")) {
                    iconPath = filePath;
                } else {
                    JOptionPane.showMessageDialog(this, "Si us plau, selecciona un format d'imatge correcte (.png, .jpeg, .jpg)", "Invalid File", JOptionPane.ERROR_MESSAGE);
                }
            }

            iconLabel.setIcon(null);
            try {
                Image img = ImageIO.read(new File(iconPath));
                img = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error carregant la icona: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            iconPanel.repaint();
        });

        // Botón para borrar la imagen
        JButton deleteIconButton = new JButton("Esborrar icona");
        deleteIconButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        deleteIconButton.setBackground(new Color(255, 182, 193));
        deleteIconButton.setFocusPainted(false);
        deleteIconButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteIconButton.addActionListener((_) -> {
            // Treure icona, repintar i esborrar path de la icona
            try {
              Image img = ImageIO.read(new File(Paths.DEFAULT_ICON));
              img = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Escalar la imatge
              iconLabel.setIcon(new ImageIcon(img));
            } catch (IOException ex) {
              JOptionPane.showMessageDialog(this, "Error loading icon: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            iconPanel.repaint();
            iconPath = "";
        });

        iconButtonPanel.setLayout(new GridLayout(2, 1, 5, 20));
        iconButtonPanel.add(changeIconButton);
        iconButtonPanel.add(deleteIconButton);

        // Agregar el panel de icono y botones de icono al contenedor centrado
        iconPanelContainer.setLayout(new GridLayout(1, 2, 40, 5));
        iconPanelContainer.add(iconPanel);
        iconPanelContainer.add(iconButtonPanel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(iconPanelContainer, gbc);

        // Campo de texto para el nombre del producto con etiqueta
        JLabel nameLabel = new JLabel("Nom:");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(nomAntic);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        nameField.setMargin(new Insets(2, 5, 2, 5));
        nameField.setPreferredSize(uniformSize);
        
        nameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                nomProducte = nameField.getText();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                nomProducte = nameField.getText();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                nomProducte = nameField.getText();
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipadx = 200;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(nameField, gbc);

        // Campo de texto para el precio del producto con etiqueta
        JLabel priceLabel = new JLabel("Preu:");
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        priceLabel.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.ipadx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(priceLabel, gbc);

        JTextField priceField;
        if (!esNouProducte) priceField = new JTextField(String.valueOf(preuProducte));
        else priceField = new JTextField();
        priceField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        priceField.setMargin(new Insets(2, 5, 2, 5));
        priceField.setPreferredSize(uniformSize);

        priceField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                try {
                    preuProducte = Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    preuProducte = 0.0;
                }
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                try {
                    preuProducte = Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    preuProducte = 0.0;
                }
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                try {
                    preuProducte = Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    preuProducte = 0.0;
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipadx = 200;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(priceField, gbc);

        // Área de texto para la descripción del producto con etiqueta
        JLabel descriptionLabel = new JLabel("Descripció:");
        descriptionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        descriptionLabel.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        centerPanel.add(descriptionLabel, gbc);

        JTextArea descriptionArea = new JTextArea(descripcioProducte);
        descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setMargin(new Insets(2, 5, 2, 5));

        descriptionArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                descripcioProducte = descriptionArea.getText();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                descripcioProducte = descriptionArea.getText();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                descripcioProducte = descriptionArea.getText();
            }
        });

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionScrollPane.setPreferredSize(new Dimension((int) uniformSize.getWidth() + 200, 100)); // el número de l'amplada és per quadrar-ho amb la resta de camps

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(descriptionScrollPane, gbc);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Centrado y separación entre botones
        buttonsPanel.setBackground(new Color(240, 248, 255));

        // Botón "Acceptar"
        JButton acceptButton = new JButton("Acceptar");
        acceptButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        acceptButton.setBackground(new Color(60, 179, 113));
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setFocusPainted(false);
        acceptButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        acceptButton.addActionListener((_) -> {
            // Error per no tenir tots els camps
            if (nomProducte.equals("") || descripcioProducte.equals("") || preuProducte <= 0) {
                JOptionPane.showMessageDialog(this, "Tots els camps són obligatoris i el preu ha de ser major que 0 (si hi ha decimals, has de posar un punt)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Error per no tenir les relacions
            if (relacions.size() == 0 && cntrlView.getNumProductes() >= 1) {
                JOptionPane.showMessageDialog(this, "No s'han introduït les relacions amb la resta de productes", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Tot correcte
            try {
                float preu = (float) preuProducte;
                cntrlView.addEditProducte(nomAntic, nomProducte, iconPath, preu, descripcioProducte, relacions);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar el producte: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            cntrlView.hideGestionarProducteView();
        });

        // Botón "Modificar relacions" / "Afegir relacions"
        String nom_boto;
        if (esNouProducte) nom_boto = "Afegir relacions";
        else nom_boto = "Modificar relacions";
        JButton modifyRelationsButton = new JButton(nom_boto);
        modifyRelationsButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        modifyRelationsButton.setBackground(new Color(100, 149, 237));
        modifyRelationsButton.setForeground(Color.WHITE);
        modifyRelationsButton.setFocusPainted(false);
        modifyRelationsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        modifyRelationsButton.addActionListener((_) -> {
            if (esNouProducte) {
                relacions.clear();
                int numProductes = cntrlView.getNumProductes();

                Boolean cancelar = false;
                for (int i = 0; i < numProductes && !cancelar; i++) {
                    String nomProducte2 = cntrlView.getNomProducteEnSupermercat(i);
                    String message = "Afegeix la relació amb el producte \"" + nomProducte2 + "\"\nHa de ser un nombre enter entre 0 i 100\n\nIntrodueix la relació:";
                    String title = "Afegir relació";
                    
                    // Bucle per si hi ha errors en l'entrada
                    String relacioStr;
                    try { relacioStr = JOptionPane.showInputDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, null, null, 0).toString(); }
                    catch (NullPointerException p) {relacioStr = null;}
                    do  {
                        try {
                            // Cancel·lar
                            if (relacioStr == null) {
                                cancelar = true;
                                break;
                            }
                            
                            // Relació buida
                            if (relacioStr.isEmpty()) {
                                relacions.add(0);
                                break;
                            }

                            // Afegir relacions
                            int relacioIn = Integer.parseInt(relacioStr);
                            if (relacioIn < 0 || relacioIn > 100) {
                                JOptionPane.showMessageDialog(this, "La relació ha de ser un número enter positiu entre 0 i 100", "Error", JOptionPane.ERROR_MESSAGE);
                                try { relacioStr = JOptionPane.showInputDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, null, null, 0).toString(); }
                                catch (NullPointerException p) {relacioStr = null;}
                            }
                            else {
                                relacions.add(relacioIn);
                                break;
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Si us plau, introdueix un número enter vàlid", "Error", JOptionPane.ERROR_MESSAGE);
                            try { relacioStr = JOptionPane.showInputDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, null, null, 0).toString(); }
                            catch (NullPointerException p) {relacioStr = null;}
                        }
                    } while (relacioStr != null);
                }
            }
            // Modificar relacions
            else {
                relacions.clear();
                ArrayList<Integer> relacionsAux = cntrlView.getRelacionsProducte(nomAntic);
                Boolean cancelar = false;
                for (int i = 0; i < relacionsAux.size() && !cancelar; i++) {
                    if (relacionsAux.get(i) == -1) continue; // No volem modificar la relació amb ell mateix

                    String nomProducte2 = cntrlView.getNomProducteEnSupermercat(i);
                    Integer relacio = relacionsAux.get(i);
                    String message = "Modifica la relació amb el producte \"" + nomProducte2 + "\"\nHa de ser un nombre enter entre 0 i 100\n\nIntrodueix la nova relació:";
                    String title = "Modificar relació";
                    
                    // Bucle per si hi ha errors en l'entrada
                    String relacioStr;
                    
                    try { relacioStr = JOptionPane.showInputDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, null, null, relacio).toString(); }
                    catch (NullPointerException p) {relacioStr = null;}
                    do  {
                        try {
                            // Cancel·lar
                            if (relacioStr == null) {
                                cancelar = true;
                                break;
                            }
                            
                            // Modificar
                            int relacioIn = Integer.parseInt(relacioStr);
                            if (relacioIn < 0 || relacioIn > 100) {
                                JOptionPane.showMessageDialog(this, "La relació ha de ser un número enter positiu entre 0 i 100", "Error", JOptionPane.ERROR_MESSAGE);
                                try { relacioStr = JOptionPane.showInputDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, null, null, relacio).toString(); }
                                catch (NullPointerException p) {relacioStr = null;}
                            }
                            else {
                                relacionsAux.set(i, relacioIn);
                                break;
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(this, "Si us plau, introdueix un número enter vàlid", "Error", JOptionPane.ERROR_MESSAGE);
                            try { relacioStr = JOptionPane.showInputDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE, null, null, relacio).toString(); }
                            catch (NullPointerException p) {relacioStr = null;}
                        }
                    } while (relacioStr != null);
                }

                // Eliminar la relacion consigo misma
                for (int i = 0; i < relacionsAux.size(); i++) {
                    if (relacionsAux.get(i) != -1) {
                        relacions.add(relacionsAux.get(i));
                    }
                }
            }
        });

        buttonsPanel.add(acceptButton);
        if (cntrlView.getNumProductes() >= 1) buttonsPanel.add(modifyRelationsButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(buttonsPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);

        SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
    }
}