/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sae.View.jFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import sae.Logiciel;
import sae.Utils.IconUtil;
import sae.View.jDialog.ColorationsDialog;
import sae.View.jDialog.FonctionsDialog;
import sae.View.jDialog.LoadAgraphDialog;

/**
 * Cette classe représente la fenêtre principale de l'application. Elle est la
 * jFrame qui affiche le graphe, la carte etc... Elle étend la classe
 * javax.swing.JFrame et implémente l'interface Logiciel.
 *
 * @author fillo
 */
public class MainFrame extends javax.swing.JFrame implements Logiciel {

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
        mapCustom1.init(latitude, longitude, standardZoom);
        mapCustom1.initAirports();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.pack();
        jTextArea2OnOverallInfos.setEditable(false);
        jTextArea2OnOverallInfos.setWrapStyleWord(false);
        jTextAreaOnObject.setEditable(true);
        jTextAreaOnObject.setWrapStyleWord(true);
        jTextArea2OnOverallInfos.setLineWrap(true);
        jTextAreaOnObject.setLineWrap(true);
        jTextAreaOnObject.setEditable(false);
        iconU.setIcon(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScreenPanel = new javax.swing.JPanel();
        mapCustom1 = new sae.View.airport.MapCustom();
        ComboMapType = new javax.swing.JComboBox<>();
        leftBarPanel = new javax.swing.JPanel();
        TopPanel = new javax.swing.JPanel();
        infosLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2OnOverallInfos = new javax.swing.JTextArea();
        MiddlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        titleLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaOnObject = new javax.swing.JTextArea();
        BottomPanel = new javax.swing.JPanel();
        ColorationsButton = new javax.swing.JButton();
        FunctionsButton = new javax.swing.JButton();
        jLabelLogo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemOpenG = new javax.swing.JMenuItem();
        jMenuItemSaveAsG = new javax.swing.JMenuItem();
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
                .addContainerGap(551, Short.MAX_VALUE))
        );
        mapCustom1Layout.setVerticalGroup(
            mapCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mapCustom1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ComboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(581, Short.MAX_VALUE))
        );

        ScreenPanel.add(mapCustom1);

        getContentPane().add(ScreenPanel);

        leftBarPanel.setBackground(new java.awt.Color(255, 255, 255));
        leftBarPanel.setMaximumSize(new java.awt.Dimension(200, 32767));
        leftBarPanel.setMinimumSize(new java.awt.Dimension(200, 0));
        leftBarPanel.setPreferredSize(new java.awt.Dimension(200, 526));
        leftBarPanel.setLayout(new java.awt.GridLayout(3, 1));

        TopPanel.setBackground(new java.awt.Color(255, 255, 255));
        TopPanel.setForeground(new java.awt.Color(0, 0, 0));

        infosLabel.setForeground(new java.awt.Color(0, 0, 0));
        infosLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infosLabel.setText("INFOS GENERALES");

        jTextArea2OnOverallInfos.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea2OnOverallInfos.setColumns(20);
        jTextArea2OnOverallInfos.setRows(5);
        jTextArea2OnOverallInfos.setBorder(null);
        jScrollPane2.setViewportView(jTextArea2OnOverallInfos);

        javax.swing.GroupLayout TopPanelLayout = new javax.swing.GroupLayout(TopPanel);
        TopPanel.setLayout(TopPanelLayout);
        TopPanelLayout.setHorizontalGroup(
            TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(infosLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addGroup(TopPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        TopPanelLayout.setVerticalGroup(
            TopPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TopPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infosLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addContainerGap())
        );

        leftBarPanel.add(TopPanel);

        MiddlePanel.setBackground(new java.awt.Color(255, 255, 255));

        titleLabel.setForeground(new java.awt.Color(0, 0, 0));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText(" INFO SUR L’OBJET ");

        titleLabel2.setForeground(new java.awt.Color(0, 0, 0));
        titleLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel2.setText("SELECTIONNÉ");

        jTextAreaOnObject.setBackground(new java.awt.Color(204, 204, 204));
        jTextAreaOnObject.setColumns(20);
        jTextAreaOnObject.setForeground(new java.awt.Color(0, 0, 0));
        jTextAreaOnObject.setRows(5);
        jTextAreaOnObject.setBorder(null);
        jScrollPane1.setViewportView(jTextAreaOnObject);

        javax.swing.GroupLayout MiddlePanelLayout = new javax.swing.GroupLayout(MiddlePanel);
        MiddlePanel.setLayout(MiddlePanelLayout);
        MiddlePanelLayout.setHorizontalGroup(
            MiddlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(titleLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MiddlePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        MiddlePanelLayout.setVerticalGroup(
            MiddlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MiddlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addContainerGap())
        );

        leftBarPanel.add(MiddlePanel);

        BottomPanel.setBackground(new java.awt.Color(255, 255, 255));

        ColorationsButton.setBackground(new java.awt.Color(235, 173, 59));
        ColorationsButton.setForeground(new java.awt.Color(0, 0, 0));
        ColorationsButton.setText("Editables");
        ColorationsButton.setFocusPainted(false);
        ColorationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColorationsButtonActionPerformed(evt);
            }
        });

        FunctionsButton.setBackground(new java.awt.Color(235, 173, 59));
        FunctionsButton.setForeground(new java.awt.Color(0, 0, 0));
        FunctionsButton.setText("Fonctions");
        FunctionsButton.setFocusPainted(false);
        FunctionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FunctionsButtonActionPerformed(evt);
            }
        });

        jLabelLogo.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\logo_1.png"));

        javax.swing.GroupLayout BottomPanelLayout = new javax.swing.GroupLayout(BottomPanel);
        BottomPanel.setLayout(BottomPanelLayout);
        BottomPanelLayout.setHorizontalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BottomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ColorationsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                    .addComponent(FunctionsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BottomPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        BottomPanelLayout.setVerticalGroup(
            BottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BottomPanelLayout.createSequentialGroup()
                .addComponent(jLabelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(FunctionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ColorationsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        leftBarPanel.add(BottomPanel);

        getContentPane().add(leftBarPanel);

        jMenuFile.setText("fichier");

        jMenuItemOpenG.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemOpenG.setText("ouvrir un graphe");
        jMenuItemOpenG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenGActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpenG);

        jMenuItemSaveAsG.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemSaveAsG.setText("sauvergarder sous le graphe");
        jMenuItemSaveAsG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSaveAsGActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemSaveAsG);

        jMenuItemreturnWelcomeFrame.setText("revenir au menu principal");
        jMenuItemreturnWelcomeFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemreturnWelcomeFrameActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemreturnWelcomeFrame);

        jMenuBar1.add(jMenuFile);

        jMenuEdit.setText("affichage");

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

    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur le bouton des
     * fonctions. Elle ouvre une instance de la classe FonctionsDialog en tant
     * que boîte de dialogue modale.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void FunctionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FunctionsButtonActionPerformed
        FonctionsDialog dialogF = new FonctionsDialog(this, true);
        dialogF.setLocationRelativeTo(this);
        dialogF.setVisible(true);
    }//GEN-LAST:event_FunctionsButtonActionPerformed

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
    private void ColorationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColorationsButtonActionPerformed
        ColorationsDialog cdialog = new ColorationsDialog(this, true);
        cdialog.setLocationRelativeTo(this);
        cdialog.setVisible(true);
    }//GEN-LAST:event_ColorationsButtonActionPerformed

    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur l'élément de
     * menu pour ouvrir un graphe. Elle ouvre une instance de la classe
     * LoadAgraphDialog en tant que boîte de dialogue modale.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void jMenuItemOpenGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenGActionPerformed
        LoadAgraphDialog loadDialog = new LoadAgraphDialog(this, true);
        loadDialog.setLocationRelativeTo(this);
        loadDialog.setVisible(true);

    }//GEN-LAST:event_jMenuItemOpenGActionPerformed

    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur l'élément de
     * menu pour enregistrer un graphe.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void jMenuItemSaveAsGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSaveAsGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemSaveAsGActionPerformed

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
    public void setJTextAreaText(String text) {
        String actualString = "aéroport : " + text;
        jTextAreaOnObject.setText(actualString);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BottomPanel;
    private javax.swing.JButton ColorationsButton;
    private javax.swing.JComboBox<String> ComboMapType;
    private javax.swing.JCheckBoxMenuItem DarkModeCheckBoxMenuItem;
    private javax.swing.JButton FunctionsButton;
    private javax.swing.JPanel MiddlePanel;
    private javax.swing.JPanel ScreenPanel;
    private javax.swing.JPanel TopPanel;
    private javax.swing.JLabel infosLabel;
    private javax.swing.JLabel jLabelLogo;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemOpenG;
    private javax.swing.JMenuItem jMenuItemSaveAsG;
    private javax.swing.JMenuItem jMenuItemreturnWelcomeFrame;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2OnOverallInfos;
    private javax.swing.JTextArea jTextAreaOnObject;
    private javax.swing.JPanel leftBarPanel;
    private sae.View.airport.MapCustom mapCustom1;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel titleLabel2;
    // End of variables declaration//GEN-END:variables
}
