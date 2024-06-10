/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sae.view.jFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import sae.Logiciel;
import sae.view.jDialog.LoadAirspaceDialog;
import sae.utils.IconUtil;
import sae.view.jDialog.ColorationsDialog;
import sae.view.jDialog.FonctionsDialog;

/**
 * Cette classe représente la fenêtre principale de l'application. Elle est la
 * jFrame qui affiche le graphe, la carte etc... Elle étend la classe
 * javax.swing.JFrame et implémente l'interface Logiciel.
 *
 * @author fillo
 * @author mathe
 */
public class MainFrame extends javax.swing.JFrame implements Logiciel {
    private boolean isMenuVisible = true;
    private JButton buttonMenu = new JButton();
    private ImageIcon iconButtonMenuClose = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\chevron-right.png");
    private ImageIcon iconButtonMenuOpen = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\chevron-left.png");
    private Rectangle boundsMenuBar;

    /**
     * Instance de la classe IconUtil utilisée pour configurer les icônes des
     * composants graphiques.
     */
    private static final IconUtil iconU = new IconUtil();

    // exemple1 latitude
    private static final double latitude = 46.6396031;
    private static final double longitude = 2.7105474;
    private static final int standardZoom = 14;

    
    /**
     * Crée une nouvelle instance de la classe MainFrame. Initialise les
     * composants de la fenêtre, configure la carte personnalisée avec les
     * coordonnées et le zoom standard, affiche les aéroports sur la carte,
     * configure l'affichage des champs de texte, configure les icônes et
     * configure l'état de la fenêtre pour être maximisé. (constructeur)
     */
    public MainFrame() {
        initComponents();
        
        buttonMenu.setContentAreaFilled(false);

        mapCustom1.add(buttonMenu);
        mapCustom1.init(latitude, longitude, standardZoom);
        mapCustom1.initAirports();
        mapCustom1.initFlightLines();
        
        
        textAreaInfosGene.setEditable(false);
        textAreaInfosGene.setWrapStyleWord(false);
        textAreaInfosSelect.setEditable(true);
        textAreaInfosSelect.setWrapStyleWord(true);
        textAreaInfosGene.setLineWrap(true);
        textAreaInfosSelect.setLineWrap(true);
        textAreaInfosSelect.setEditable(false);
        
        iconU.setIcon(this);
        
        setMinimumSize(new Dimension(1300,900));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setButtonPosition();
            }
        });
        
        boundsMenuBar = panelRightBar.getBounds();
        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                isMenuVisible = !isMenuVisible;
                panelRightBar.setVisible(isMenuVisible);
                setButtonPosition();
            }
        });
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScreenPanel = new javax.swing.JPanel();
        mapCustom1 = new sae.view.airport.MapCustom();
        ComboMapType = new javax.swing.JComboBox<>();
        panelRightBar = new javax.swing.JPanel();
        panelContainerRightBar = new javax.swing.JPanel();
        labelInfosGene = new javax.swing.JLabel();
        panelInfosGene = new javax.swing.JPanel();
        textAreaInfosGene = new javax.swing.JTextArea();
        labelInfosSelect1 = new javax.swing.JLabel();
        labelInfosSelect2 = new javax.swing.JLabel();
        panelInfosSelect = new javax.swing.JPanel();
        textAreaInfosSelect = new javax.swing.JTextArea();
        labelLogo = new javax.swing.JLabel();
        panelButton = new javax.swing.JPanel();
        buttonColoration = new javax.swing.JButton();
        buttonFunctions = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemOpenG = new javax.swing.JMenuItem();
        jMenuItemreturnWelcomeFrame = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        DarkModeCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FFRplane - Main");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        ScreenPanel.setBackground(new java.awt.Color(255, 0, 0));
        ScreenPanel.setLayout(new javax.swing.BoxLayout(ScreenPanel, javax.swing.BoxLayout.LINE_AXIS));

        ComboMapType.setBackground(new java.awt.Color(255, 255, 255));
        ComboMapType.setForeground(new java.awt.Color(0, 0, 0));
        ComboMapType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Open Street", "Virtual Earth", "Hybrid", "Satellite" }));
        ComboMapType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboMapTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mapCustom1Layout = new javax.swing.GroupLayout(mapCustom1);
        mapCustom1.setLayout(mapCustom1Layout);
        mapCustom1Layout.setHorizontalGroup(
            mapCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mapCustom1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ComboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(466, Short.MAX_VALUE))
        );
        mapCustom1Layout.setVerticalGroup(
            mapCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mapCustom1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ComboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(713, Short.MAX_VALUE))
        );

        ScreenPanel.add(mapCustom1);

        getContentPane().add(ScreenPanel);

        panelRightBar.setBackground(new java.awt.Color(255, 255, 255));
        panelRightBar.setMaximumSize(new java.awt.Dimension(200, 32767));
        panelRightBar.setMinimumSize(new java.awt.Dimension(200, 0));
        panelRightBar.setPreferredSize(new java.awt.Dimension(250, 526));
        panelRightBar.setLayout(new java.awt.GridBagLayout());

        panelContainerRightBar.setBackground(null);
        panelContainerRightBar.setPreferredSize(new java.awt.Dimension(250, 700));

        labelInfosGene.setForeground(new java.awt.Color(0, 0, 0));
        labelInfosGene.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInfosGene.setText("INFOS GÉNÉRALES");

        panelInfosGene.setBackground(new java.awt.Color(204, 204, 204));
        panelInfosGene.setPreferredSize(new java.awt.Dimension(200, 160));

        textAreaInfosGene.setBackground(null);
        textAreaInfosGene.setColumns(20);
        textAreaInfosGene.setRows(5);
        textAreaInfosGene.setBorder(null);

        javax.swing.GroupLayout panelInfosGeneLayout = new javax.swing.GroupLayout(panelInfosGene);
        panelInfosGene.setLayout(panelInfosGeneLayout);
        panelInfosGeneLayout.setHorizontalGroup(
            panelInfosGeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfosGeneLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(textAreaInfosGene, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        panelInfosGeneLayout.setVerticalGroup(
            panelInfosGeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfosGeneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textAreaInfosGene, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        labelInfosSelect1.setForeground(new java.awt.Color(0, 0, 0));
        labelInfosSelect1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInfosSelect1.setText(" INFOS SUR L’OBJET ");
        labelInfosSelect1.setPreferredSize(new java.awt.Dimension(200, 16));

        labelInfosSelect2.setForeground(new java.awt.Color(0, 0, 0));
        labelInfosSelect2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInfosSelect2.setText("SÉLECTIONNÉ");

        panelInfosSelect.setBackground(new java.awt.Color(204, 204, 204));
        panelInfosSelect.setPreferredSize(new java.awt.Dimension(200, 160));

        textAreaInfosSelect.setBackground(null);
        textAreaInfosSelect.setColumns(20);
        textAreaInfosSelect.setForeground(new java.awt.Color(0, 0, 0));
        textAreaInfosSelect.setRows(5);
        textAreaInfosSelect.setBorder(null);

        javax.swing.GroupLayout panelInfosSelectLayout = new javax.swing.GroupLayout(panelInfosSelect);
        panelInfosSelect.setLayout(panelInfosSelectLayout);
        panelInfosSelectLayout.setHorizontalGroup(
            panelInfosSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfosSelectLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(textAreaInfosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        panelInfosSelectLayout.setVerticalGroup(
            panelInfosSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfosSelectLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textAreaInfosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        labelLogo.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\logo_1.png"));

        panelButton.setBackground(null);

        buttonColoration.setBackground(new java.awt.Color(235, 173, 59));
        buttonColoration.setForeground(new java.awt.Color(0, 0, 0));
        buttonColoration.setText("Éditables");
        buttonColoration.setFocusPainted(false);
        buttonColoration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonColorationActionPerformed(evt);
            }
        });

        buttonFunctions.setBackground(new java.awt.Color(235, 173, 59));
        buttonFunctions.setForeground(new java.awt.Color(0, 0, 0));
        buttonFunctions.setText("Fonctions");
        buttonFunctions.setFocusPainted(false);
        buttonFunctions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonFunctionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonLayout = new javax.swing.GroupLayout(panelButton);
        panelButton.setLayout(panelButtonLayout);
        panelButtonLayout.setHorizontalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonColoration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonFunctions, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonFunctions, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonColoration, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelContainerRightBarLayout = new javax.swing.GroupLayout(panelContainerRightBar);
        panelContainerRightBar.setLayout(panelContainerRightBarLayout);
        panelContainerRightBarLayout.setHorizontalGroup(
            panelContainerRightBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelInfosGene, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelInfosSelect1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(labelInfosSelect2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerRightBarLayout.createSequentialGroup()
                .addGroup(panelContainerRightBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelContainerRightBarLayout.createSequentialGroup()
                        .addGroup(panelContainerRightBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelContainerRightBarLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(panelInfosGene, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelContainerRightBarLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelContainerRightBarLayout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(panelContainerRightBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelInfosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(25, 25, 25))
        );
        panelContainerRightBarLayout.setVerticalGroup(
            panelContainerRightBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerRightBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInfosGene)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfosGene, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(labelInfosSelect1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(labelInfosSelect2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInfosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelRightBar.add(panelContainerRightBar, new java.awt.GridBagConstraints());

        getContentPane().add(panelRightBar);

        jMenuFile.setText("Fichier");

        jMenuItemOpenG.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemOpenG.setText("ouvrir un graphe");
        jMenuItemOpenG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenGActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpenG);

        jMenuItemreturnWelcomeFrame.setText("Revenir au menu principal");
        jMenuItemreturnWelcomeFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemreturnWelcomeFrameActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemreturnWelcomeFrame);

        jMenuBar1.add(jMenuFile);

        jMenuEdit.setText("Affichage");

        DarkModeCheckBoxMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        DarkModeCheckBoxMenuItem.setText("put in dark");
        DarkModeCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DarkModeCheckBoxMenuItemActionPerformed(evt);
            }
        });
        jMenuEdit.add(DarkModeCheckBoxMenuItem);

        jMenuBar1.add(jMenuEdit);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    private void setButtonPosition(){
        int buttonHeight = 50;
        int buttonWidth = 50;
        int buttonY = getHeight()/2 - buttonHeight;
        buttonMenu.setBounds(getWidth() - (isMenuVisible ? boundsMenuBar.width + buttonWidth : buttonWidth) , buttonY, buttonWidth, buttonHeight);
        buttonMenu.setIcon(isMenuVisible ? iconButtonMenuOpen : iconButtonMenuClose);
    }
    
    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur le bouton des
     * fonctions. Elle ouvre une instance de la classe FonctionsDialog en tant
     * que boîte de dialogue modale.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void buttonFunctionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFunctionsActionPerformed
        FonctionsDialog dialogF = new FonctionsDialog(this, true);
        dialogF.setLocationRelativeTo(this);
        dialogF.setVisible(true);
    }//GEN-LAST:event_buttonFunctionsActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur l'élément de
     * menu du mode sombre. Elle active ou désactive le mode sombre en fonction
     * de l'état de la case à cocher.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void DarkModeCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DarkModeCheckBoxMenuItemActionPerformed
        if (DarkModeCheckBoxMenuItem.isSelected()) {
            // le darkmode est activé
            setPanelBackground(this.getContentPane(), Color.BLACK);
            setLabelForeground(this.getContentPane(), Color.WHITE);
        } else {
            //le darkmode est désactivé
            setPanelBackground(this.getContentPane(), Color.WHITE);
            setLabelForeground(this.getContentPane(), Color.BLACK);
        }
    }//GEN-LAST:event_DarkModeCheckBoxMenuItemActionPerformed
    
    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur le bouton des
     * colorations. Elle ouvre une instance de la classe ColorationsDialog en
     * tant que boîte de dialogue modale.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void buttonColorationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonColorationActionPerformed
        ColorationsDialog cdialog = new ColorationsDialog(this, true);
        cdialog.setLocationRelativeTo(this);
        cdialog.setVisible(true);
    }//GEN-LAST:event_buttonColorationActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur l'élément de
     * menu pour ouvrir un graphe. Elle ouvre une instance de la classe
     * LoadAgraphDialog en tant que boîte de dialogue modale.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void jMenuItemOpenGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenGActionPerformed
        LoadAirspaceDialog loadDialog = new LoadAirspaceDialog(this, true);
        loadDialog.setLocationRelativeTo(this);
        loadDialog.setVisible(true);

    }//GEN-LAST:event_jMenuItemOpenGActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur l'élément de
     * menu pour revenir à la fenêtre d'accueil. Elle masque cette fenêtre et
     * ouvre une nouvelle instance de la classe WelcomeFrame.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void jMenuItemreturnWelcomeFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemreturnWelcomeFrameActionPerformed
        this.setVisible(false);
        WelcomeFrame welcomef = new WelcomeFrame();
        welcomef.setLocationRelativeTo(this);
        welcomef.setVisible(true);
    }//GEN-LAST:event_jMenuItemreturnWelcomeFrameActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur le sélecteur
     * de type de carte. Elle change le style de la carte en fonction de l'index
     * sélectionné dans le sélecteur.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void ComboMapTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboMapTypeActionPerformed
        int index = ComboMapType.getSelectedIndex();
        mapCustom1.changeStyle(index);
    }//GEN-LAST:event_ComboMapTypeActionPerformed

    
    /**
     * Définit la couleur de fond de tous les composants JPanel dans le
     * conteneur spécifié ainsi que dans ses sous-conteneurs de manière
     * récursive.
     *
     * @param container le conteneur dans lequel rechercher les composants
     * JPanel
     * @param color la couleur à définir comme couleur de fond
     */
    private void setPanelBackground(Container container, Color color) {
        for (Component component : container.getComponents()) {
            if (component instanceof JPanel jPanel) {
                jPanel.setBackground(color);
                // Récursivement, parcourir les sous-panneaux
                setPanelBackground(jPanel, color);
            }
        }
    }

    
    /**
     * Définit la couleur du texte de tous les composants JLabel dans le
     * conteneur spécifié ainsi que dans ses sous-conteneurs de manière
     * récursive.
     *
     * @param container le conteneur dans lequel rechercher les composants
     * JLabel
     * @param color la couleur à définir comme couleur du texte
     */
    private void setLabelForeground(Container container, Color color) {
        for (Component component : container.getComponents()) {
            if (component instanceof JLabel jLabel) {
                jLabel.setForeground(color);
            } else if (component instanceof JPanel jPanel) {
                // Si c'est un panel, parcourir les composants à l'intérieur
                setLabelForeground(jPanel, color);
            }
        }
    }

    
    /**
     * Définit le texte du composant JTextArea sur le texte spécifié.
     *
     * @param text le texte à définir dans le JTextArea
     */
    @Override
    public void setJTextAreaText1(String text) {
        String actualString = text;
        textAreaInfosSelect.setText(actualString);
    }
    
    @Override
    public void setJTextAreaText2(String text) {
        String actualString = text;
        textAreaInfosGene.setText(actualString);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboMapType;
    private javax.swing.JCheckBoxMenuItem DarkModeCheckBoxMenuItem;
    private javax.swing.JPanel ScreenPanel;
    private javax.swing.JButton buttonColoration;
    private javax.swing.JButton buttonFunctions;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemOpenG;
    private javax.swing.JMenuItem jMenuItemreturnWelcomeFrame;
    private javax.swing.JLabel labelInfosGene;
    private javax.swing.JLabel labelInfosSelect1;
    private javax.swing.JLabel labelInfosSelect2;
    private javax.swing.JLabel labelLogo;
    private sae.view.airport.MapCustom mapCustom1;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelContainerRightBar;
    private javax.swing.JPanel panelInfosGene;
    private javax.swing.JPanel panelInfosSelect;
    private javax.swing.JPanel panelRightBar;
    private javax.swing.JTextArea textAreaInfosGene;
    private javax.swing.JTextArea textAreaInfosSelect;
    // End of variables declaration//GEN-END:variables

    @Override
    public void setJTextAreaText(String text) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
