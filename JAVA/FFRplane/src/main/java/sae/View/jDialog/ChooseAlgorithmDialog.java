/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package sae.View.jDialog;

import java.awt.BorderLayout;
import java.io.IOException;
import org.graphstream.ui.view.Viewer;
import sae.Models.algocoloration.AlgoColoration;
import sae.Models.algocoloration.ResultatColoration;
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
public class ChooseAlgorithmDialog extends javax.swing.JDialog {

    private AlgoColoration algoColoration = new AlgoColoration();

    /**
     * Crée une nouvelle instance de ChooseAlgorithmDialog.
     *
     * @param parent Le frame parent
     * @param modal Si vrai, le dialogue bloque l'entrée utilisateur aux autres
     * fenêtres de premier plan
     */
    public ChooseAlgorithmDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setLocationRelativeTo(parent);
        // Chargement du graphe avec le fichier temporel
        algoColoration.setFichier("src/main/java/data/test/graph-test0.txt");
        try {
            algoColoration.charger_graphe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initComponents();
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
        jPanel1 = new javax.swing.JPanel();
        dsaturButton = new javax.swing.JButton();
        wpButton = new javax.swing.JButton();
        bestalgoButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(431, 181));
        setResizable(false);
        getContentPane().setLayout(new java.awt.FlowLayout());

        jPanel1.setMinimumSize(new java.awt.Dimension(380, 100));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        dsaturButton.setBackground(new java.awt.Color(235, 173, 59));
        dsaturButton.setForeground(new java.awt.Color(0, 0, 0));
        dsaturButton.setText("DSATUR");
        dsaturButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dsaturButtonActionPerformed(evt);
            }
        });
        jPanel1.add(dsaturButton);

        wpButton.setBackground(new java.awt.Color(235, 173, 59));
        wpButton.setForeground(new java.awt.Color(0, 0, 0));
        wpButton.setText("WELSH POWELL");
        wpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wpButtonActionPerformed(evt);
            }
        });
        jPanel1.add(wpButton);

        bestalgoButton.setBackground(new java.awt.Color(235, 173, 59));
        bestalgoButton.setForeground(new java.awt.Color(0, 0, 0));
        bestalgoButton.setText("LE MEILLEUR");
        bestalgoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bestalgoButtonActionPerformed(evt);
            }
        });
        jPanel1.add(bestalgoButton);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Choisissez votre algorithme de coloration");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel2)
                .addGap(48, 48, 48)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

        // Reintialisation du conteneur
        mainFrame.getGraphstreamContener().removeAll();

        // Ajout du vie panel au panel
        mainFrame.getGraphstreamContener().add(viewPanel, BorderLayout.CENTER);

        // Mise à jour du panel
        mainFrame.getGraphstreamContener().revalidate();
        mainFrame.getGraphstreamContener().repaint();
        mainFrame.getGraphstreamContener().setVisible(true);
    }

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
        ResultatColoration resultatColoration = algoColoration.minConflict();
        int conflict = resultatColoration.getConflict();
        StringBuilder generalInformation = new StringBuilder();
        // Modifications du panel contenant les informations générales avec les nouvelles valeurs
        generalInformation.append(" Kmax : ").append(algoColoration.getKmax()).append("\n")
                .append(" Nombre d'arrêtes : ").append(resultatColoration.getGraph().getEdgeCount()).append("\n")
                .append(" Nombre de sommets : ").append(resultatColoration.getGraph().getNodeCount()).append("\n")
                .append(" Nombre de conflits : ").append(conflict).append("\n");
        MainFrame mainFrame = (MainFrame) this.getParent();
        mainFrame.setJTextAreaText2(generalInformation.toString());

        Viewer viewer = new Viewer(resultatColoration.getGraph(), Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();

        // Mise à jour de la graphe visualisation
        updateGraphVisualization(viewer);
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
        int conflict = algoColoration.welshPowell();
        StringBuilder generalInformation = new StringBuilder();
        // Modifications du panel contenant les informations générales avec les nouvelles valeurs
        generalInformation.append(" Kmax : ").append(algoColoration.getKmax()).append("\n")
                .append(" Nombre d'arrêtes : ").append(algoColoration.getGraph().getEdgeCount()).append("\n")
                .append(" Nombre de sommets : ").append(algoColoration.getGraph().getNodeCount()).append("\n")
                .append(" Nombre de conflits : ").append(conflict).append("\n");
        MainFrame mainFrame = (MainFrame) this.getParent();
        mainFrame.setJTextAreaText2(generalInformation.toString());

        Viewer viewer = new Viewer(algoColoration.getGraph(), Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();

        // Mise à jour de la graphe visualisation
        updateGraphVisualization(viewer);
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
        int conflict = algoColoration.dsatur(algoColoration.getGraph());
        StringBuilder generalInformation = new StringBuilder();
        // Modifications du panel contenant les informations générales avec les nouvelles valeurs
        generalInformation.append(" Kmax : ").append(algoColoration.getKmax()).append("\n")
                .append(" Nombre d'arrêtes : ").append(algoColoration.getGraph().getEdgeCount()).append("\n")
                .append(" Nombre de sommets : ").append(algoColoration.getGraph().getNodeCount()).append("\n")
                .append(" Nombre de conflits : ").append(conflict).append("\n");
        MainFrame mainFrame = (MainFrame) this.getParent();
        mainFrame.setJTextAreaText2(generalInformation.toString());

        Viewer viewer = new Viewer(algoColoration.getGraph(), Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();

        // Mise à jour de la graphe visualisation
        updateGraphVisualization(viewer);
    }//GEN-LAST:event_dsaturButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bestalgoButton;
    private javax.swing.JButton dsaturButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton wpButton;
    // End of variables declaration//GEN-END:variables
}
