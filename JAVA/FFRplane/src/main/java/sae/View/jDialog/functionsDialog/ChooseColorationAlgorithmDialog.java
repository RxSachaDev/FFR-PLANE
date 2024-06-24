package sae.view.jDialog.functionsDialog;

import java.awt.BorderLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

import sae.model.coloration.Coloration;
import sae.model.coloration.ColorationAlgorithm;
import sae.model.coloration.ColorationResult;
import sae.model.toolbox.ToolBox;
import sae.utils.Settings;
import sae.view.jFrame.MainFrame;

/**
 * ChooseAlgorithmDialog permet à l'utilisateur de sélectionner un algorithme de
 * coloration de graphe. Il étend JDialog et est modal. Il initialise le graphe
 * à partir d'un fichier et permet à l'utilisateur d'exécuter différents
 * algorithmes de coloration. Après la sélection d'un algorithme, il met à jour
 * le JTextArea du MainFrame avec le résultat.
 *
 * @see javax.swing.JDialog
 * @author Sacha
 */
public class ChooseColorationAlgorithmDialog extends javax.swing.JDialog {

    /**
     * Instance de la classe Coloration représentant le graphe à colorier. Cette
     * instance est initialisée à partir d'un fichier spécifié et utilisée pour
     * appliquer les différents algorithmes de coloration.
     */
    private Coloration coloration = new Coloration();

    /**
     * Instance de la classe ColorationAlgorithm utilisée pour exécuter les
     * différents algorithmes de coloration sur le graphe chargé. Cette instance
     * est initialisée avec l'objet Coloration.
     */
    private ColorationAlgorithm colorationAlgorithm;

    /**
     * Crée une nouvelle instance de ChooseAlgorithmDialog.
     *
     * @param parent Le frame parent
     * @param modal Si vrai, le dialogue bloque l'entrée utilisateur aux autres
     * fenêtres de premier plan
     */
    public ChooseColorationAlgorithmDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        // Chargement du graphe avec le fichier graph-testTEMP, 
        // généré automatiquement après chaque chargement d'espace aérien
        coloration.setFile(Settings.getGraphTEMPPath());
        try {
            coloration = ToolBox.fillGraph(coloration.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        colorationAlgorithm = new ColorationAlgorithm(coloration);
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * Cette méthode est appelée à partir du constructeur pour initialiser le
     * formulaire. ATTENTION : Ne modifiez pas ce code. Le contenu de cette
     * méthode est toujours régénéré par l'éditeur de formulaires.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();
        dsaturButton = new javax.swing.JButton();
        wpButton = new javax.swing.JButton();
        bestalgoButton = new javax.swing.JButton();
        labelPanel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new java.awt.FlowLayout());

        buttonPanel.setMinimumSize(new java.awt.Dimension(380, 100));

        dsaturButton.setBackground(new java.awt.Color(235, 173, 59));
        dsaturButton.setForeground(new java.awt.Color(0, 0, 0));
        dsaturButton.setText("DSATUR");
        dsaturButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dsaturButtonActionPerformed(evt);
            }
        });

        wpButton.setBackground(new java.awt.Color(235, 173, 59));
        wpButton.setForeground(new java.awt.Color(0, 0, 0));
        wpButton.setText("WELSH POWELL");
        wpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wpButtonActionPerformed(evt);
            }
        });

        bestalgoButton.setBackground(new java.awt.Color(235, 173, 59));
        bestalgoButton.setForeground(new java.awt.Color(0, 0, 0));
        bestalgoButton.setText("LE MEILLEUR DES DEUX");
        bestalgoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bestalgoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(bestalgoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(buttonPanelLayout.createSequentialGroup()
                        .addComponent(dsaturButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(wpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dsaturButton)
                    .addComponent(wpButton))
                .addGap(18, 18, 18)
                .addComponent(bestalgoButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        labelPanel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPanel.setText("Choisissez votre algorithme de coloration");
        labelPanel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(labelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(labelPanel)
                .addGap(48, 48, 48)
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /* ••••••••••••• MÉTHODES ••••••••••••• */
    /**
     * Met à jour la visualisation du graphe dans le viewer spécifié.
     *
     * <p>
     * Cette méthode récupère le parent {@link MainFrame}, supprime tous les
     * composants existants du conteneur de graphe, puis ajoute un nouveau
     * {@link org.graphstream.ui.swingViewer.ViewPanel} pour afficher le graphe
     * mis à jour. Le conteneur de graphe est ensuite revalidé, repeint et rendu
     * visible.
     *
     * @param viewer l'instance de {@link Viewer} qui contient le graphe à
     * visualiser
     */
    private void updateGraphVisualization(Viewer viewer) {
        MainFrame mainFrame = (MainFrame) this.getParent();
        org.graphstream.ui.swingViewer.ViewPanel viewPanel = viewer.addDefaultView(false);

        viewPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mwe) {
                ToolBox.zoomGraphMouseWheelMoved(mwe, viewPanel);
            }
        });

        // Reintialisation du conteneur
        mainFrame.getGraphstreamContener().removeAll();

        // Ajout du vie panel au panel
        mainFrame.getGraphstreamContener().add(viewPanel, BorderLayout.CENTER);

        // Mise à jour du panel
        mainFrame.getGraphstreamContener().revalidate();
        mainFrame.getGraphstreamContener().repaint();
        mainFrame.getGraphstreamPanel().setVisible(true);
    }

    /**
     * Définit la visualisation d'un graphe dans le visualiseur avec un mode
     * sombre ou clair.
     *
     * @param graph Le graphe à visualiser.
     * @param darkMode Indique si le mode sombre est activé.
     */
    public void setViewer(Graph graph, boolean darkMode) {
        if (darkMode) {
            String css = "graph { fill-color: #313338; }"
                    + "edge { fill-color: #ffffff; }"
                    + "node { text-color: #ffffff; }";
            graph.setAttribute("ui.stylesheet", css);
        } else {
            String css = "graph { fill-color: #ffffff; }"
                    + "edge { fill-color: #000000; }"
                    + "node { text-color: #000000; }";
            graph.setAttribute("ui.stylesheet", css);
        }

        // Initialisation du Viewer et activation du layout automatique
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();

        // Attribution des labels aux nœuds
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }
        updateGraphVisualization(viewer);
    }

    /**
     * Met à jour l'interface utilisateur du MainFrame avec les informations sur
     * le graphe colorié, telles que le nombre de conflits, le nombre
     * chromatique, et l'algorithme utilisé.
     *
     * @param graph Le graphe colorié à afficher.
     * @param conflict Le nombre de conflits dans le graphe colorié.
     * @param algorithm Le nom de l'algorithme de coloration utilisé.
     */
    public void setMainFrame(Graph graph, int conflict, String algorithm) {
        // Construction des informations générales sur le graphe
        StringBuilder generalInformation = new StringBuilder();
        if (coloration.getKmax() != -1) {
            generalInformation.append(" Kmax : ").append(coloration.getKmax()).append("\n")
                    .append(" Nombre d'arrête(s) : ").append(graph.getEdgeCount()).append("\n")
                    .append(" Nombre de sommets : ").append(graph.getNodeCount()).append("\n")
                    .append(" Nombre de conflits : ").append(conflict).append("\n")
                    .append(" Nombre chromatique : ").append(coloration.countChromaticNumber(graph)).append("\n")
                    .append(" Algorithme utilisé : ").append(algorithm).append("\n");
        } else {
            generalInformation.append(" Kmax : Aucun").append("\n")
                    .append(" Nombre d'arrête(s) : ").append(graph.getEdgeCount()).append("\n")
                    .append(" Nombre de sommet(s) : ").append(graph.getNodeCount()).append("\n")
                    .append(" Nombre de conflit(s) : ").append(conflict).append("\n")
                    .append(" Nombre chromatique : ").append(coloration.countChromaticNumber(graph)).append("\n")
                    .append(" Algorithme utilisé : ").append(algorithm).append("\n");
        }

        // Récupère le parent MainFrame et met à jour le texte des informations générales
        MainFrame mainFrame = (MainFrame) this.getParent();
        mainFrame.getTextAreaInfosGene().setText(generalInformation.toString());

        // Met à jour le graphe colorié dans MainFrame
        mainFrame.setColoringGraph(graph);

        // Configure le viewer en mode sombre ou clair selon la sélection
        if (mainFrame.getDarkModeCheckBoxMenuItem().isSelected()) {
            setViewer(graph, true);
        } else {
            setViewer(graph, false);
        }

        // Met à jour la colorMapLine avec le graphe colorié
        mainFrame.getController().colorMapLine(graph);

        // Met à jour l'algorithme choisi dans MainFrame
        mainFrame.setChosenAlgorithm(algorithm);
    }

    /* ••••••••••••• LISTENERS ••••••••••••• */
    /**
     * Gère l'événement lorsque l'utilisateur clique sur le bouton "Le
     * Meilleur". Ferme la fenêtre de dialogue. Exécute l'algorithme de
     * coloration "Le Meilleur" sur le graphe chargé. Met à jour le JTextArea
     * dans le MainFrame avec les résultats de l'algorithme.
     *
     * @param evt L'événement d'action associé à cet écouteur
     */
    private void bestalgoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bestalgoButtonActionPerformed

        this.dispose();
        ColorationResult resultatColoration = colorationAlgorithm.minConflict();
        setMainFrame(resultatColoration.getGraph(), resultatColoration.getConflict(), resultatColoration.getAlgorithm());
    }//GEN-LAST:event_bestalgoButtonActionPerformed

    /**
     * Gère l'événement lorsque l'utilisateur clique sur le bouton "Welsh
     * Powell". Ferme la fenêtre de dialogue. Exécute l'algorithme de coloration
     * "Welsh Powell" sur le graphe chargé. Met à jour le JTextArea dans le
     * MainFrame avec les résultats de l'algorithme.
     *
     * @param evt L'événement d'action associé à cet écouteur
     */
    private void wpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wpButtonActionPerformed
        this.dispose();
        int conflict = colorationAlgorithm.welshPowell();
        setMainFrame(coloration.getFileGraph(), conflict, "WelshPowell");
    }//GEN-LAST:event_wpButtonActionPerformed

    /**
     * Gère l'événement lorsque l'utilisateur clique sur le bouton "DSATUR".
     * Ferme la fenêtre de dialogue. Exécute l'algorithme de coloration "DSATUR"
     * sur le graphe chargé. Met à jour le JTextArea dans le MainFrame avec les
     * résultats de l'algorithme.
     *
     * @param evt L'événement d'action associé à cet écouteur
     */
    private void dsaturButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dsaturButtonActionPerformed
        this.dispose();
        int conflict = colorationAlgorithm.dsatur(coloration.getFileGraph());
        setMainFrame(coloration.getFileGraph(), conflict, "Dsatur");
    }//GEN-LAST:event_dsaturButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bestalgoButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton dsaturButton;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelPanel;
    private javax.swing.JButton wpButton;
    // End of variables declaration//GEN-END:variables
}
