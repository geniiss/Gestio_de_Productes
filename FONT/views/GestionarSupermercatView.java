package views;

import controladores.CntrlView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import structures.Paths;

/**
 * Vista para gestionar un supermercado.
 * Permite editar el nombre, la icono y los productos del supermercado.
 */
public class GestionarSupermercatView extends JPanel {

    /**
     * Nombre del supermercado que se está editando.
     */
    private String nomAntic;

    /**
     * Nuevo nombre del supermercado (puede ser el mismo que el anterior).
     */
    private String nomSupermercat;

    /**
     * Ruta del icono del supermercado (puede ser "").
     * Si existe la ruta, esta pertenece a una imagen válida.
     */
    private String iconPath;

    /**
     * Controlador de la vista que maneja las interacciones y la lógica de la interfaz de usuario.
     */
    private static final CntrlView cntrlView = CntrlView.getInstance();
    
    /**
     * Constructor de la clase.
     */
    public GestionarSupermercatView() {
      nomSupermercat = "";
      nomAntic = "";
      iconPath = "";
    }

    /**
     * Función para editar el supermercado.
     * @param nom Nombre del supermercado.
     * @param icon Ruta del icono del supermercado.
     */
    public void editarSupermercat(String nom, String icon) {
        nomAntic = nom;
        nomSupermercat = nom;
        iconPath = icon;
        removeAll();
        initUI();
    }
    
    /**
     * Inicializa la interfaz de usuario.
     */
    private void initUI() {
        
        // Panell principal
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));

        // Panell superior amb botó de retorn
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(220, 228, 238));

        add(topPanel, BorderLayout.NORTH);

        // Panell central amb layout GridBagLayout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout()); // Layout per centrar els components
        centerPanel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espai entre components

        // Marc amb icona
        JPanel iconPanel = new JPanel();
        iconPanel.setPreferredSize(new Dimension(100, 100)); // Dimensió quadrada del marc
        iconPanel.setBackground(new Color(173, 216, 230));
        iconPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true)); // Marc rodó
        iconPanel.setLayout(new GridBagLayout()); // Afegeix un GridBagLayout per centrar el contingut

        JLabel iconLabel = new JLabel(); // Etiqueta per a la icona
        //Carregar la imatge si hi ha un path correcte
          try {
              Image img;
              if (iconPath.equals("")) img = ImageIO.read(new File(Paths.DEFAULT_ICON));
              else img = ImageIO.read(new File(iconPath));
              img = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Escalar la imatge
              iconLabel.setIcon(new ImageIcon(img));
          } catch (IOException ex) {
              JOptionPane.showMessageDialog(this, "Error loading icon: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }

        iconPanel.add(iconLabel); // Afegeix l'etiqueta al panell

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(iconPanel, gbc); // Afegeix el panell al panell central


        // Panell amb els botons "Canviar icona" i "Esborrar icona"
        JPanel iconButtonPanel = new JPanel();
        iconButtonPanel.setLayout(new GridLayout(2, 1, 5, 5)); // Dues files, una columna
        iconButtonPanel.setBackground(new Color(240, 248, 255));

        // Botó canviar icona
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
          File dataDirectory = new File(Paths.ICONES_SUPERMERCATS);
          if (dataDirectory.exists() && dataDirectory.isDirectory()) {
              fileChooser.setCurrentDirectory(dataDirectory);
          }
          // Detecta només imatges
          fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "png", "jpeg", "jpg"));
          int result = fileChooser.showOpenDialog(this);
          if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            // Segona comprovació de que és una imatge vàlida
            if (filePath.endsWith(".png") || filePath.endsWith(".jpeg") || filePath.endsWith(".jpg")) {
              iconPath = filePath;
            } else {
              // Tauler d'error
              JOptionPane.showMessageDialog(this, "Please select a valid image file (.png, .jpeg, .jpg)", "Invalid File", JOptionPane.ERROR_MESSAGE);
            }
          }

          // Treure icona anterior (si en tenia)
          iconLabel.setIcon(null);
          //Carregar nova icona
          try {
            Image img = ImageIO.read(new File(iconPath));
            img = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // Escalar la imatge
            iconLabel.setIcon(new ImageIcon(img));
          } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading icon: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
          iconPanel.repaint();
        });

        // Botó esborrar icona
        JButton deleteIconButton = new JButton("Esborrar icona");
        deleteIconButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        deleteIconButton.setBackground(new Color(255, 182, 193)); // Color rosa per al botó d'esborrar
        deleteIconButton.setFocusPainted(false);
        deleteIconButton.setPreferredSize(new Dimension(150, 40));
        deleteIconButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteIconButton.addActionListener((_) -> {
            //Treure icona, repintar i esborrar path de la icona
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

        gbc.gridx = 1;
        gbc.gridy = 0;
        centerPanel.add(iconButtonPanel, gbc);

        // Camp de text per al nom del supermercat
        JTextField nameField = new JTextField(nomAntic);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setToolTipText("Nom del supermercat");
        nameField.setMargin(new Insets(5, 10, 5, 10));

        nameField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            // Qualsevol canvi que es faci al text ha de quedar guardat a la variable nomSupermercat
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
              nomSupermercat = nameField.getText();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
              nomSupermercat = nameField.getText();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
              nomSupermercat = nameField.getText();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa dues columnes
        gbc.ipadx = 200; // Amplada del camp de text
        centerPanel.add(nameField, gbc);

        // Botó "Acceptar"
        JButton acceptButton = new JButton("Acceptar");
        acceptButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        acceptButton.setBackground(new Color(60, 179, 113)); // Verd
        acceptButton.setForeground(Color.WHITE);
        acceptButton.setFocusPainted(false);
        acceptButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        acceptButton.addActionListener((_) -> {

            if (nomSupermercat.equals("")){
                JOptionPane.showMessageDialog(this, "El nom és necessari per crear el supermercat", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
              cntrlView.addEditSupermercat(nomAntic, nomSupermercat, iconPath);
            } catch (Exception a) {
              JOptionPane.showMessageDialog(this, "Error al afegir supermercat: " + a.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
              return;
            }
            cntrlView.hideGestionarSupermercatView();
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(acceptButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    
        // Detectar la tecla Enter
        nameField.addActionListener((_) -> {
            nomSupermercat = nameField.getText().trim(); // Guardar el nombre ingresado
            if (!nomSupermercat.isEmpty()) {
                try {
                    // Añadir o editar el supermercado
                    cntrlView.addEditSupermercat(nomAntic, nomSupermercat, iconPath);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error afegint el supermercat: el supermercat ja existeix", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this, "El nom del supermercat no pot estar buit.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            cntrlView.hideGestionarSupermercatView(); // Volver a la vista principal
        }
      );

      //Focus al camp de text
      SwingUtilities.invokeLater(() -> nameField.requestFocusInWindow());
    }
}
