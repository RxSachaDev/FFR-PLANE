package sae.view.jFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import org.graphstream.graph.Graph;

import sae.utils.IconUtil;
import sae.utils.Settings;
import sae.controller.Controller;
import sae.view.mapCustom.MapLine;
import sae.view.mapCustom.MapCustom;
import sae.view.jDialog.LoadAirspaceDialog;
import sae.view.jDialog.ColorationSettingsDialog;
import sae.view.jDialog.functionsDialog.FunctionChooserDialog;
import sae.view.jDialog.functionsDialog.ChooseColorationAlgorithmDialog;

/**
 * Cette classe représente la fenêtre principale de l'application. Elle est la
 * jFrame qui affiche le la MapCustom et toutes ses informations. 
 * Elle étend la classe javax.swing.JFrame.
 *
 * @author fillo
 * @author mathe
 */
public class MainFrame extends JFrame {
    private boolean isMenuVisible = true;
    private final JButton buttonMenu = new JButton();
    private final ImageIcon iconButtonMenuClose = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\chevron-right.png");
    private final ImageIcon iconButtonMenuOpen = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\chevron-left.png");
    private final Rectangle boundsMenuBar; //Sert pour la MenuBar rétractable
    
    private Graph coloringGraph;
    private String chosenAlgorithm;

    private final Controller controller;
    
    /**
     * Instance de la classe IconUtil utilisée pour configurer les icônes des
     * composants graphiques.
     */
    private static final IconUtil iconU = new IconUtil();

    
    /**
     * Crée une nouvelle instance de la classe MainFrame. Initialise les
     * composants de la fenêtre, fais appel au controller pour initialiser la MapCustom, 
     * configure l'affichage des champs de texte, configure les icônes et
     * configure l'état de la fenêtre pour être maximisé.
     */
    public MainFrame() {
        Settings.resetAll();
        initComponents();
        listenersManager();
        setMinimumSize(new Dimension(1300,900));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        buttonMenu.setContentAreaFilled(false);
        
        controller = new Controller(this);

        controller.initMainFrame();
        
        mapCustom.add(buttonMenu);
        
        textAreaInfosGene.setEditable(false);
        textAreaInfosSelect.setEditable(false);
        
        iconU.setIcon(this);
        
        boundsMenuBar = panelRightBar.getBounds();
        
        graphstreamPanel.setVisible(false);
    }

    
    /**
     * Gere les listeners
     */
    private void listenersManager(){
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setButtonMenuPosition();
            }
        });
        
        buttonMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                isMenuVisible = !isMenuVisible;
                panelRightBar.setVisible(isMenuVisible);
                setButtonMenuPosition();
            }
        });
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScreenPanel = new javax.swing.JPanel();
        mapCustom = new sae.view.mapCustom.MapCustom();
        ComboMapType = new javax.swing.JComboBox<>();
        labelLogo = new javax.swing.JLabel();
        panelRightBar = new javax.swing.JPanel();
        panelContainerRightBar = new javax.swing.JPanel();
        labelInfosGene = new javax.swing.JLabel();
        panelInfosGene = new javax.swing.JPanel();
        textAreaInfosGene = new javax.swing.JTextArea();
        labelInfosSelect1 = new javax.swing.JLabel();
        labelInfosSelect2 = new javax.swing.JLabel();
        panelInfosSelect = new javax.swing.JPanel();
        textAreaInfosSelect = new javax.swing.JTextArea();
        mapLineComboBox = new javax.swing.JComboBox<>();
        panelButton = new javax.swing.JPanel();
        buttonColoration = new javax.swing.JButton();
        buttonFunctions = new javax.swing.JButton();
        graphstreamPanel = new javax.swing.JPanel();
        graphstreamContener = new javax.swing.JPanel();
        enlargeButton = new javax.swing.JButton();
        graphLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemOpenG = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        colorGrapheMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
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

        labelLogo.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\logo_1.png"));

        javax.swing.GroupLayout mapCustomLayout = new javax.swing.GroupLayout(mapCustom);
        mapCustom.setLayout(mapCustomLayout);
        mapCustomLayout.setHorizontalGroup(
            mapCustomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mapCustomLayout.createSequentialGroup()
                .addGroup(mapCustomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mapCustomLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ComboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mapCustomLayout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        mapCustomLayout.setVerticalGroup(
            mapCustomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mapCustomLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ComboMapType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 694, Short.MAX_VALUE)
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        ScreenPanel.add(mapCustom);

        getContentPane().add(ScreenPanel);

        panelRightBar.setBackground(new java.awt.Color(221, 221, 221));
        panelRightBar.setMaximumSize(new java.awt.Dimension(200, 32767));
        panelRightBar.setMinimumSize(new java.awt.Dimension(200, 0));
        panelRightBar.setPreferredSize(new java.awt.Dimension(250, 526));
        panelRightBar.setLayout(new java.awt.GridBagLayout());

        panelContainerRightBar.setBackground(null);
        panelContainerRightBar.setToolTipText("");
        panelContainerRightBar.setPreferredSize(new java.awt.Dimension(250, 750));

        labelInfosGene.setBackground(new java.awt.Color(221, 221, 221));
        labelInfosGene.setForeground(new java.awt.Color(0, 0, 0));
        labelInfosGene.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInfosGene.setText("INFOS GÉNÉRALES");

        panelInfosGene.setBackground(new java.awt.Color(255, 255, 255));
        panelInfosGene.setPreferredSize(new java.awt.Dimension(200, 160));

        textAreaInfosGene.setColumns(20);
        textAreaInfosGene.setRows(5);
        textAreaInfosGene.setText("  \n\n\nPour avoir plus d'informations \n      veuillez colorier le graphe\n");
        textAreaInfosGene.setBorder(null);

        javax.swing.GroupLayout panelInfosGeneLayout = new javax.swing.GroupLayout(panelInfosGene);
        panelInfosGene.setLayout(panelInfosGeneLayout);
        panelInfosGeneLayout.setHorizontalGroup(
            panelInfosGeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfosGeneLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(textAreaInfosGene, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        panelInfosGeneLayout.setVerticalGroup(
            panelInfosGeneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfosGeneLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(textAreaInfosGene, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        labelInfosSelect1.setBackground(new java.awt.Color(221, 221, 221));
        labelInfosSelect1.setForeground(new java.awt.Color(0, 0, 0));
        labelInfosSelect1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInfosSelect1.setText(" INFOS SUR L’OBJET ");
        labelInfosSelect1.setPreferredSize(new java.awt.Dimension(200, 16));

        labelInfosSelect2.setBackground(new java.awt.Color(221, 221, 221));
        labelInfosSelect2.setForeground(new java.awt.Color(0, 0, 0));
        labelInfosSelect2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelInfosSelect2.setText("SÉLECTIONNÉ");

        panelInfosSelect.setBackground(new java.awt.Color(255, 255, 255));
        panelInfosSelect.setPreferredSize(new java.awt.Dimension(200, 160));

        textAreaInfosSelect.setColumns(20);
        textAreaInfosSelect.setRows(5);
        textAreaInfosSelect.setBorder(null);
        textAreaInfosSelect.setDisabledTextColor(null);

        mapLineComboBox.setModel(new DefaultComboBoxModel<MapLine>());
        mapLineComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInfosSelectLayout = new javax.swing.GroupLayout(panelInfosSelect);
        panelInfosSelect.setLayout(panelInfosSelectLayout);
        panelInfosSelectLayout.setHorizontalGroup(
            panelInfosSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfosSelectLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(textAreaInfosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addComponent(mapLineComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelInfosSelectLayout.setVerticalGroup(
            panelInfosSelectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInfosSelectLayout.createSequentialGroup()
                .addComponent(mapLineComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textAreaInfosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
            .addComponent(buttonColoration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(buttonFunctions, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        panelButtonLayout.setVerticalGroup(
            panelButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelButtonLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(buttonFunctions, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonColoration, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        graphstreamPanel.setBackground(null);

        graphstreamContener.setBackground(new java.awt.Color(221, 221, 221));
        graphstreamContener.setLayout(new java.awt.BorderLayout());

        enlargeButton.setBackground(new java.awt.Color(235, 173, 59));
        enlargeButton.setForeground(new java.awt.Color(0, 0, 0));
        enlargeButton.setText("Agrandir");
        enlargeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enlargeButtonActionPerformed(evt);
            }
        });

        graphLabel.setForeground(new java.awt.Color(0, 0, 0));
        graphLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        graphLabel.setText("GRAPHSTREAM GRAPHE");
        graphLabel.setPreferredSize(new java.awt.Dimension(200, 16));

        javax.swing.GroupLayout graphstreamPanelLayout = new javax.swing.GroupLayout(graphstreamPanel);
        graphstreamPanel.setLayout(graphstreamPanelLayout);
        graphstreamPanelLayout.setHorizontalGroup(
            graphstreamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graphstreamPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(graphstreamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(graphstreamContener, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(enlargeButton, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(graphLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        graphstreamPanelLayout.setVerticalGroup(
            graphstreamPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(graphstreamPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(graphLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(graphstreamContener, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(enlargeButton))
        );

        javax.swing.GroupLayout panelContainerRightBarLayout = new javax.swing.GroupLayout(panelContainerRightBar);
        panelContainerRightBar.setLayout(panelContainerRightBarLayout);
        panelContainerRightBarLayout.setHorizontalGroup(
            panelContainerRightBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerRightBarLayout.createSequentialGroup()
                .addGroup(panelContainerRightBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(graphstreamPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelInfosGene, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelContainerRightBarLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(panelInfosGene, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(labelInfosSelect1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(labelInfosSelect2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelContainerRightBarLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(panelInfosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelContainerRightBarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(panelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelContainerRightBarLayout.setVerticalGroup(
            panelContainerRightBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerRightBarLayout.createSequentialGroup()
                .addComponent(labelInfosGene, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(panelInfosGene, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(labelInfosSelect1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(labelInfosSelect2)
                .addGap(6, 6, 6)
                .addComponent(panelInfosSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(graphstreamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(panelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRightBar.add(panelContainerRightBar, new java.awt.GridBagConstraints());

        getContentPane().add(panelRightBar);

        jMenuFile.setText("Fichier");
        jMenuFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuFileActionPerformed(evt);
            }
        });

        jMenuItemOpenG.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItemOpenG.setText("Charger un nouvel Espace Aerien");
        jMenuItemOpenG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenGActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemOpenG);
        jMenuFile.add(jSeparator1);

        colorGrapheMenuItem.setText("Charger le Graph de Coloration");
        colorGrapheMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorGrapheMenuItemActionPerformed(evt);
            }
        });
        jMenuFile.add(colorGrapheMenuItem);
        jMenuFile.add(jSeparator2);

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
        DarkModeCheckBoxMenuItem.setText("Put in dark");
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

    
    /* ••••••••••••• LISTENERS ••••••••••••• */
    
    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur le 
     * buttonFunctions. Elle ouvre une instance de la classe FunctionChooserDialog en
     * tant que boîte de dialogue modale.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void buttonFunctionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonFunctionsActionPerformed
        FunctionChooserDialog dialogF = new FunctionChooserDialog(this, true);
        dialogF.setLocationRelativeTo(this);
        dialogF.setVisible(true);
    }//GEN-LAST:event_buttonFunctionsActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur l'élément de menu
     * DarkModeCheckBoxMenuItem. Elle applique les changements de style pour activer
     * ou désactiver le mode sombre.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void DarkModeCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        ChooseColorationAlgorithmDialog chooseAlgorithmDialog = new ChooseColorationAlgorithmDialog(this, true);
        if (DarkModeCheckBoxMenuItem.isSelected()) {
            // le darkmode est activé
            panelRightBar.setBackground(new Color(30, 31, 34));
            panelInfosGene.setBackground(new Color(49, 51, 56));
            panelInfosSelect.setBackground(new Color(49, 51, 56));
            textAreaInfosGene.setBackground(new Color(49, 51, 56));
            textAreaInfosSelect.setBackground(new Color(49, 51, 56));
            ComboMapType.setSelectedIndex(3);
            mapCustom.changeStyle(3);
            labelInfosGene.setForeground(Color.white);
            labelInfosSelect1.setForeground(Color.white);
            labelInfosSelect2.setForeground(Color.white);
            textAreaInfosGene.setForeground(Color.white);
            textAreaInfosSelect.setForeground(Color.white);
            graphLabel.setForeground(Color.white);
            if (coloringGraph != null) {
                chooseAlgorithmDialog.setViewer(coloringGraph, true);
            }
            
        } else {
            //le darkmode est désactivé
            textAreaInfosSelect.setBackground(Color.white);
            textAreaInfosGene.setBackground(Color.white);
            panelRightBar.setBackground(new Color(221, 221, 221));
            panelInfosSelect.setBackground(Color.white);
            panelInfosGene.setBackground(Color.white);
            ComboMapType.setSelectedIndex(0);
            mapCustom.changeStyle(0);
            labelInfosGene.setForeground(Color.BLACK);
            labelInfosSelect1.setForeground(Color.BLACK);
            labelInfosSelect2.setForeground(Color.BLACK);
            textAreaInfosGene.setForeground(Color.BLACK);
            textAreaInfosSelect.setForeground(Color.BLACK);
            graphLabel.setForeground(Color.BLACK);
            if (coloringGraph != null) {
                chooseAlgorithmDialog.setViewer(coloringGraph, false);
            }
        }
    }

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur le bouton
     * buttonColoration. Elle ouvre une instance de la classe ColorationSettingsDialog
     * en tant que boîte de dialogue modale.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void buttonColorationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonColorationActionPerformed
        ColorationSettingsDialog cdialog = new ColorationSettingsDialog(this, true);
        cdialog.setLocationRelativeTo(this);
        cdialog.setVisible(true);
    }//GEN-LAST:event_buttonColorationActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur l'élément de menu
     * jMenuItemOpenG. Elle ouvre une instance de la classe LoadAirspaceDialog en
     * tant que boîte de dialogue modale.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void jMenuItemOpenGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenGActionPerformed
        LoadAirspaceDialog loadDialog = new LoadAirspaceDialog(this, true);
        loadDialog.setLocationRelativeTo(this);
        loadDialog.setVisible(true);
    }//GEN-LAST:event_jMenuItemOpenGActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur l'élément de menu
     * jMenuItemreturnWelcomeFrame. Elle ferme la fenêtre actuelle et ouvre une
     * nouvelle instance de la classe WelcomeFrame.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void jMenuItemreturnWelcomeFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemreturnWelcomeFrameActionPerformed
        dispose();
        WelcomeFrame welcomef = new WelcomeFrame();
        welcomef.setVisible(true);
    }//GEN-LAST:event_jMenuItemreturnWelcomeFrameActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur ComboMapType.
     * Elle change le style de la carte en fonction de l'index sélectionné dans
     * ComboMapType.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void ComboMapTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboMapTypeActionPerformed
        int index = ComboMapType.getSelectedIndex();
        mapCustom.changeStyle(index);
    }//GEN-LAST:event_ComboMapTypeActionPerformed


    private void jMenuFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuFileActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuFileActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur l'élément de menu
     * colorGrapheMenuItem. Elle ouvre une instance de la classe ChooseColorationAlgorithmDialog
     * en tant que boîte de dialogue modale.
     *  
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void colorGrapheMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorGrapheMenuItemActionPerformed
        ChooseColorationAlgorithmDialog chooseAlgorithm = new ChooseColorationAlgorithmDialog((JFrame) this, true);
        chooseAlgorithm.setVisible(true);
    }//GEN-LAST:event_colorGrapheMenuItemActionPerformed

    
    /**
     * Actualise l'affiche de la mapLineComboBox en fonction de l'item qui est selectionné
     */
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        MapLine selectedMapLine = (MapLine)mapLineComboBox.getSelectedItem();
        if(selectedMapLine != null) {
            textAreaInfosSelect.setText(selectedMapLine.toStringModelLine());
            for(MapLine mapLine : controller.getMapLineSet()){
                mapLine.setColor(Color.BLACK);
            } 
            selectedMapLine.setColor(Color.ORANGE);
            mapCustom.repaint();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    
    /**
     * Méthode appelée lorsqu'un événement d'action se produit sur enlargeButton.
     * Elle ouvre une nouvelle instance de la classe GraphstreamFrame.
     *
     * @param evt L'événement d'action associé à l'appel de cette méthode.
     */
    private void enlargeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enlargeButtonActionPerformed
        GraphstreamFrame graphstreamFrame = new GraphstreamFrame(this, "src/main/java/data/temp/graph-testTEMP.txt", chosenAlgorithm);
        if (DarkModeCheckBoxMenuItem.isSelected()){
            graphstreamFrame.graphstreamFrameDarkMode();
        }
        graphstreamFrame.setVisible(true);
    }//GEN-LAST:event_enlargeButtonActionPerformed
    
    
    /* ••••••••••••• GETTERS / SETTERS ••••••••••••• */
    
    
    /**
     * Retourne l'instance de MapCustom associée à cette fenêtre.
     *
     * @return L'instance de MapCustom.
     */
    public MapCustom getMapCustom() {
        return mapCustom;
    }
    
    
    /**
     * Retourne l'instance de Controller associée à cette fenêtre.
     *
     * @return L'instance de Controller.
     */
    public Controller getController(){
            return controller;
    }
    
    
    /**
     * Retourne la JComboBox contenant les MapLine.
     *
     * @return La JComboBox des MapLine.
     */
    public JComboBox<MapLine> getMapLineComboBox() {
        return mapLineComboBox;
    }
    
    
    /**
     * Retourne le JPanel contenant le graphe Graphstream.
     *
     * @return Le JPanel du graphe Graphstream.
     */
    public JPanel getGraphstreamContener() {
        return graphstreamContener;
    }

    
    /**
     * Retourne le JPanel du panel Graphstream.
     *
     * @return Le JPanel du panel Graphstream.
     */
    public JPanel getGraphstreamPanel() {
        return graphstreamPanel;
    }
    
    
    /**
     * Retourne la JCheckBoxMenuItem du mode sombre.
     *
     * @return La JCheckBoxMenuItem du mode sombre.
     */
    public JCheckBoxMenuItem getDarkModeCheckBoxMenuItem() {
        return DarkModeCheckBoxMenuItem;
    }
    
    
    /**
     * Retourne la JTextArea contenant les informations générales.
     *
     * @return La JTextArea des informations générales.
     */
    public JTextArea getTextAreaInfosGene() {
        return textAreaInfosGene;
    }

    
    /**
     * Retourne la JTextArea contenant les informations de sélection.
     *
     * @return La JTextArea des informations de sélection.
     */
    public JTextArea getTextAreaInfosSelect() {
        return textAreaInfosSelect;
    }
    
    
    /**
     * Positionne le bouton du menu à la bonne position sur l'écran.
     */
    private void setButtonMenuPosition(){
        int buttonHeight = 50;
        int buttonWidth = 50;
        int buttonY = getHeight()/2 - buttonHeight;
        buttonMenu.setBounds(getWidth() - (isMenuVisible ? boundsMenuBar.width + buttonWidth : buttonWidth) , buttonY, buttonWidth, buttonHeight);
        buttonMenu.setIcon(isMenuVisible ? iconButtonMenuOpen : iconButtonMenuClose);
    }
    
    
    /**
     * Définit le graphe de coloration.
     *
     * @param coloringGraph Le graphe de coloration.
     */
    public void setColoringGraph(Graph coloringGraph) {
        this.coloringGraph = coloringGraph;
    }

    
    /**
     * Définit l'algorithme choisi.
     *
     * @param chosenAlgorithm L'algorithme choisi.
     */
    public void setChosenAlgorithm(String chosenAlgorithm) {
        this.chosenAlgorithm = chosenAlgorithm;
    }
    
      
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboMapType;
    private javax.swing.JCheckBoxMenuItem DarkModeCheckBoxMenuItem;
    private javax.swing.JPanel ScreenPanel;
    private javax.swing.JButton buttonColoration;
    private javax.swing.JButton buttonFunctions;
    private javax.swing.JMenuItem colorGrapheMenuItem;
    private javax.swing.JButton enlargeButton;
    private javax.swing.JLabel graphLabel;
    private javax.swing.JPanel graphstreamContener;
    private javax.swing.JPanel graphstreamPanel;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemOpenG;
    private javax.swing.JMenuItem jMenuItemreturnWelcomeFrame;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel labelInfosGene;
    private javax.swing.JLabel labelInfosSelect1;
    private javax.swing.JLabel labelInfosSelect2;
    private javax.swing.JLabel labelLogo;
    private sae.view.mapCustom.MapCustom mapCustom;
    private javax.swing.JComboBox<MapLine> mapLineComboBox;
    private javax.swing.JPanel panelButton;
    private javax.swing.JPanel panelContainerRightBar;
    private javax.swing.JPanel panelInfosGene;
    private javax.swing.JPanel panelInfosSelect;
    private javax.swing.JPanel panelRightBar;
    private javax.swing.JTextArea textAreaInfosGene;
    private javax.swing.JTextArea textAreaInfosSelect;
    // End of variables declaration//GEN-END:variables
}

