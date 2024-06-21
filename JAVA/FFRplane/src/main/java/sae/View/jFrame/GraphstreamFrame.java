/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sae.View.jFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.util.DefaultMouseManager;
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
    private Graph graph;

    private String algo;
    private JFrame parent;

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
        this.parent = parent;
        initComponents();
        labelLogo.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\logo_1.png"));
        jPanel1.setLayout(new BorderLayout());

        // Mettre la frame en plein écran immédiatement
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        if (parent instanceof MainFrame) {
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            loadAnotherGraphButton.setVisible(false);
        }

        // Charger le graph en arrière-plan
        new GraphLoader(this, chemin, parent).execute();
    }

    /**
     * La classe GraphLoader charge le graphe en arrière-plan.
     */
    private class GraphLoader extends SwingWorker<Void, Void> {

        private String chemin;
        private JFrame parent;
        private JFrame frameParent;

        /**
         * Construit une nouvelle instance de GraphLoader.
         *
         * @param chemin Le chemin vers le fichier du graphe.
         */
        public GraphLoader(JFrame parent, String chemin, JFrame frameParent) {
            this.chemin = chemin;
            this.parent = parent;
            this.frameParent = frameParent;
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

            int conflict;
            // Récupère le graph 
            if (algo.equals(""
                    + "BestAlgo")) {
                ResultatColoration resultatColoration = algoColoration.minConflict();
                conflict = resultatColoration.getConflict();
                graph = resultatColoration.getGraph();
            } else if (algo.equals("Dsatur")) {
                conflict = algoColoration.dsatur(algoColoration.getFileGraph());
                graph = algoColoration.getFileGraph();
            } else {
                conflict = algoColoration.welshPowell();
                graph = algoColoration.getFileGraph();
            }

            // Création de la vision de graphstream
            if (frameParent instanceof MainFrame) {
                MainFrame mainFrame = (MainFrame) frameParent;
                if (mainFrame.getDarkModeCheckBoxMenuItem().isSelected()) {
                    String css = "graph { fill-color: #313338; }"
                            + "edge { fill-color: #ffffff; }"
                            + "node { text-color: #ffffff; }";
                    graph.setAttribute("ui.stylesheet", css);
                    System.out.println("Dark mode CSS applied.");
                } else {
                    String css = "graph { fill-color: #ffffff; }"
                            + "edge { fill-color: #000000; }"
                            + "node { text-color: #000000; }";
                    graph.setAttribute("ui.stylesheet", css);
                    System.out.println("Light mode CSS applied.");
                }
            }
            
            // Initialisation du Viewer et activation du layout automatique
            Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
            viewer.enableAutoLayout();

            // Attribution des labels aux nœuds
            for (Node node : graph) {
                node.addAttribute("ui.label", node.getId());
            }

            org.graphstream.ui.swingViewer.ViewPanel viewPanel = viewer.addDefaultView(false);
            viewer.getDefaultView().setMouseManager(new CustomMouseManager(parent, viewer.getDefaultView()));
            jPanel1.add(viewPanel, BorderLayout.CENTER);

            // Mettre à jour jPanel1
            jPanel1.revalidate();
            jPanel1.repaint();
            // Modifier les labels avec les bonnes valeurs
            if (algoColoration.getKmax() != -1) {
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

    public void graphstreamFrameDarkMode() {
        rightBarPanel.setBackground(new Color(30, 31, 34));
        informationPanel.setBackground(new Color(49, 51, 56));

        for (int i = 0; i < informationPanel.getComponentCount(); i++) {
            informationPanel.getComponent(i).setForeground(Color.white);
        }
        nodeInformationPanel.setBackground(new Color(49, 51, 56));

        for (int i = 0; i < nodeInformationPanel.getComponentCount(); i++) {
            nodeInformationPanel.getComponent(i).setForeground(Color.white);
        }
    }

    public class CustomMouseManager extends DefaultMouseManager {

        private View view;
        private JFrame parent;

        public CustomMouseManager(JFrame parent, View view) {
            this.view = view;
            this.parent = parent;
            view.addMouseListener(this);
            view.addMouseMotionListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            GraphstreamFrame graphstreamFrame = (GraphstreamFrame) parent;

            GraphicElement node = view.findNodeOrSpriteAt(x, y);

            if (node != null) {
                String nodeId = node.getId();
                graphstreamFrame.getColorLabel().setText("Couleur : " + graphstreamFrame.getGraph().getNode(nodeId).getAttribute("color"));
                graphstreamFrame.getNodeLabel().setText("Sommet : " + node.getId());
                graphstreamFrame.getEdgeLabel().setText("Nombre d'arrêtes : " + graphstreamFrame.getGraph().getNode(nodeId).getDegree());
            }
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
        rightBarPanel = new javax.swing.JPanel();
        informationPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        kmaxLabel = new javax.swing.JLabel();
        nbNodeLabel = new javax.swing.JLabel();
        nbEdgeLabel = new javax.swing.JLabel();
        chromaticNumberLabel = new javax.swing.JLabel();
        conflictLabel = new javax.swing.JLabel();
        loadAnotherGraphButton = new javax.swing.JButton();
        labelLogo = new javax.swing.JLabel();
        nodeInformationPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        nodeLabel = new javax.swing.JLabel();
        edgeLabel = new javax.swing.JLabel();
        colorLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FFR Plane - Graphstream Frame");
        setMinimumSize(new java.awt.Dimension(1000, 800));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        getContentPane().add(jPanel1);

        rightBarPanel.setBackground(java.awt.Color.white);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Information sur la coloration");

        kmaxLabel.setText("Kmax :  Loading");

        nbNodeLabel.setText("Nombre de sommets : Loading");

        nbEdgeLabel.setText("Nombre d'arrêtes : Loading");

        chromaticNumberLabel.setText("Nombre chromatique : Loading");

        conflictLabel.setText("Nombre de conflits : Loading");

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nbNodeLabel)
                    .addComponent(kmaxLabel)
                    .addComponent(nbEdgeLabel)
                    .addComponent(chromaticNumberLabel)
                    .addComponent(conflictLabel)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel1)))
                .addGap(20, 20, 20))
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
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

        loadAnotherGraphButton.setBackground(new java.awt.Color(235, 173, 59));
        loadAnotherGraphButton.setForeground(new java.awt.Color(0, 0, 0));
        loadAnotherGraphButton.setText("Charger un autre graphe");
        loadAnotherGraphButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAnotherGraphButtonActionPerformed(evt);
            }
        });

        labelLogo.setText("jLabel2");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Information sur le sommet cliqué");

        nodeLabel.setText("Sommet :  Loading");

        edgeLabel.setText("Nombre d'arrêtes : Loading");

        colorLabel.setText("Couleur : Loading");

        javax.swing.GroupLayout nodeInformationPanelLayout = new javax.swing.GroupLayout(nodeInformationPanel);
        nodeInformationPanel.setLayout(nodeInformationPanelLayout);
        nodeInformationPanelLayout.setHorizontalGroup(
            nodeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nodeInformationPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(nodeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edgeLabel)
                    .addComponent(nodeLabel)
                    .addComponent(colorLabel)
                    .addGroup(nodeInformationPanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel2)))
                .addGap(20, 20, 20))
        );
        nodeInformationPanelLayout.setVerticalGroup(
            nodeInformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nodeInformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nodeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edgeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colorLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout rightBarPanelLayout = new javax.swing.GroupLayout(rightBarPanel);
        rightBarPanel.setLayout(rightBarPanelLayout);
        rightBarPanelLayout.setHorizontalGroup(
            rightBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightBarPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(rightBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(informationPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nodeInformationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadAnotherGraphButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        rightBarPanelLayout.setVerticalGroup(
            rightBarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightBarPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(informationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(nodeInformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(loadAnotherGraphButton)
                .addGap(18, 18, 18)
                .addComponent(labelLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(104, Short.MAX_VALUE))
        );

        getContentPane().add(rightBarPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Gère l'action lorsque le bouton jButton1 est cliqué. Cette méthode
     * affiche une boîte de dialogue LoadGraphDialog.
     *
     * @param evt l'événement d'action déclenché par le clic.
     */
    private void loadAnotherGraphButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadAnotherGraphButtonActionPerformed
        LoadGraphDialog loadGraphDialog = new LoadGraphDialog(this, true);
        loadGraphDialog.setVisible(true);
    }//GEN-LAST:event_loadAnotherGraphButtonActionPerformed

    public JLabel getColorLabel() {
        return colorLabel;
    }

    public JLabel getEdgeLabel() {
        return edgeLabel;
    }

    public JLabel getNodeLabel() {
        return nodeLabel;
    }

    public Graph getGraph() {
        return graph;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chromaticNumberLabel;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JLabel conflictLabel;
    private javax.swing.JLabel edgeLabel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel kmaxLabel;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JButton loadAnotherGraphButton;
    private javax.swing.JLabel nbEdgeLabel;
    private javax.swing.JLabel nbNodeLabel;
    private javax.swing.JPanel nodeInformationPanel;
    private javax.swing.JLabel nodeLabel;
    private javax.swing.JPanel rightBarPanel;
    // End of variables declaration//GEN-END:variables
}
