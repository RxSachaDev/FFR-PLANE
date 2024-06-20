/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sae.View.jFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer;
import sae.Models.algocoloration.AlgoColoration;
import sae.Models.algocoloration.ResultatColoration;
import sae.utils.IconUtil;
import sae.view.jDialog.LoadGraphDialog;
import sae.view.jFrame.MainFrame;

/**
 * La classe GraphstreamFrame représente une interface graphique (GUI) pour
 * visualiser un graphe en utilisant la bibliothèque GraphStream. Elle affiche
 * des informations sur le graphe et ses propriétés.
 *
 * @author Sacha
 */
public class GraphstreamFrame extends javax.swing.JFrame {

    /**
     * Instance de la classe AlgoColoration utilisée pour gérer les opérations
     * de coloration. Cette instance est initialisée au moment de la
     * déclaration.
     */
    private AlgoColoration algoColoration = new AlgoColoration();
    private final IconUtil iconU = new IconUtil();
    
    private String algo;

    /**
     * Construit une nouvelle instance de GraphstreamFrame.
     *
     * @param parent parent de cette JDialog
     * @param chemin Le chemin vers le fichier du graphe.
     * @param algo L'algorithme utilisé
     */
    public GraphstreamFrame(JFrame parent, String chemin, String algo) {
        iconU.setIcon(this);
        this.algo = algo;
        initComponents();
        labelLogo.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\logo_1.png"));
        jPanel1.setLayout(new BorderLayout());

        // Mettre la frame en plein écran immédiatement
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        if (parent instanceof MainFrame ){
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }

        // Charger le graph en arrière-plan
        new GraphLoader(chemin).execute();
    }


    /**
     * La classe GraphLoader charge le graphe en arrière-plan.
     */
    private class GraphLoader extends SwingWorker<Void, Void> {

        private String chemin;

        /**
         * Construit une nouvelle instance de GraphLoader.
         *
         * @param chemin Le chemin vers le fichier du graphe.
         */
        public GraphLoader(String chemin) {
            this.chemin = chemin;
        }

        @Override
        protected Void doInBackground() {
            algoColoration.setFichier(chemin);
            try {
                algoColoration.fillGraph();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void done() {
            Graph graph;
            int conflict;
            // Récupère le graph 
            if (algo.equals(""
                    + "BestAlgo")){
                ResultatColoration resultatColoration = algoColoration.minConflict();
                conflict = resultatColoration.getConflict();
                graph = resultatColoration.getGraph();
            }
            else if (algo.equals("Dsatur")){
                conflict = algoColoration.dsatur(algoColoration.getFileGraph());
                graph = algoColoration.getFileGraph();
            }
            else {
                conflict = algoColoration.welshPowell();
                graph = algoColoration.getFileGraph();
            }
            

            // Création de la vision de graphstream
            Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
            viewer.enableAutoLayout();
            
            //viewer.getDefaultView().setBackground(Color.red);

            org.graphstream.ui.swingViewer.ViewPanel viewPanel = viewer.addDefaultView(false);
            jPanel1.add(viewPanel, BorderLayout.CENTER);

            // Mettre à jour jPanel1
            jPanel1.revalidate();
            jPanel1.repaint();
            // Modifier les labels avec les bonnes valeurs
            if (algoColoration.getKmax() != -1){
                kmaxLabel.setText("Kmax : " + algoColoration.getKmax());
            } else {
                kmaxLabel.setText("Kmax : Aucun");
            }
            
        nbNodeLabel.setText("Nombre de sommets : " + algoColoration.getNbNode());
        nbEdgeLabel.setText("Nombre d'arêtes : " + algoColoration.getFileGraph().getEdgeCount());
        chromaticNumberLabel.setText("Nombre chromatique : " + algoColoration.countChromaticNumber(graph));
        conflictLabel.setText("Nombre de conflits : " + conflict);

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        kmaxLabel = new javax.swing.JLabel();
        nbNodeLabel = new javax.swing.JLabel();
        nbEdgeLabel = new javax.swing.JLabel();
        chromaticNumberLabel = new javax.swing.JLabel();
        conflictLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        labelLogo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FFR Plane - Graphstream Frame");
        setMinimumSize(new java.awt.Dimension(1000, 800));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        getContentPane().add(jPanel1);

        jPanel3.setBackground(java.awt.Color.white);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Information sur la coloration");

        kmaxLabel.setText("Kmax :  Loading");

        nbNodeLabel.setText("Nombre de sommets : Loading");

        nbEdgeLabel.setText("Nombre d'arrêtes : Loading");

        chromaticNumberLabel.setText("Nombre chromatique : Loading");

        conflictLabel.setText("Nombre de conflits : Loading");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nbNodeLabel)
                    .addComponent(kmaxLabel)
                    .addComponent(nbEdgeLabel)
                    .addComponent(chromaticNumberLabel)
                    .addComponent(conflictLabel)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1)))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kmaxLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nbNodeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nbEdgeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chromaticNumberLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conflictLabel)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(235, 173, 59));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Charger un autre graphe");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        labelLogo.setText("jLabel2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Gère l'action lorsque le bouton jButton1 est cliqué. Cette méthode
     * affiche une boîte de dialogue LoadGraphDialog.
     *
     * @param evt l'événement d'action déclenché par le clic.
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        LoadGraphDialog loadGraphDialog = new LoadGraphDialog(this, true);
        loadGraphDialog.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chromaticNumberLabel;
    private javax.swing.JLabel conflictLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel kmaxLabel;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JLabel nbEdgeLabel;
    private javax.swing.JLabel nbNodeLabel;
    // End of variables declaration//GEN-END:variables
}
