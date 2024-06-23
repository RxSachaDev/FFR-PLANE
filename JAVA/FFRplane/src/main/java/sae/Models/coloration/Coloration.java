package sae.Models.coloration;

import java.io.IOException;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import sae.models.toolbox.ToolBox;

/**
 * La classe Coloration permet de lire un graphe à partir d'un fichier, de
 * l'afficher, et d'appliquer des algorithmes de coloration pour minimiser les
 * conflits de coloration.
 */
public class Coloration {
    
    /**
     * Le nombre de couleurs max.
     */
    private int kmax;

    /**
     * Le nombre de sommets dans le graphe.
     */
    private int nbNode;

    /**
     * Le nom du fichier contenant les données du graphe.
     */
    private String file;

    /**
     * L'objet Graph représentant le graphe à colorer.
     */
    private Graph fileGraph;

    /**
     * Constructeur par défaut qui initialise un nouveau MultiGraph.
     */
    public Coloration() {
        fileGraph = new MultiGraph("test");
    }

    /**
     * Constructeur qui initialise un nouveau MultiGraph avec un graphe donné.
     *
     * @param graph le graphe à utiliser
     */
    public Coloration(Graph graph) {
        this.fileGraph = graph = new MultiGraph("test");
    }

    /* ••••••••••••• MÉTHODES ••••••••••••• */
    /**
     * Compte le nombre de conflits de coloration dans un graphe.
     *
     * @param graph le graphe à analyser
     * @return le nombre de conflits de coloration
     */
    public int countConflicts(Graph graph) {
        int conflictCount = 0;
        // Boucle for pour sélectionner chaque arrête
        for (Edge edge : graph.getEachEdge()) {
            Node source = edge.getSourceNode();
            Node target = edge.getTargetNode();
            // Si les sommets sont coloriés, comparer les couleurs
            if (source.hasAttribute("color") && target.hasAttribute("color")) {
                int sourceColor = source.getAttribute("color");
                int targetColor = target.getAttribute("color");
                if (sourceColor == targetColor) {
                    conflictCount++;
                }
            }
        }
        return conflictCount;
    }

    /**
     * Compte le nombre chromatique du graphe.
     *
     * @param graph le graphe à analyser
     * @return le nombre chromatique du graphe
     */
    public int countChromaticNumber(Graph graph) {
        int chromaticNumber = 0;
        for (Node node : graph) {
            if ((int) node.getAttribute("color") > chromaticNumber) {
                chromaticNumber = (int) node.getAttribute("color");
            }
        }
        return chromaticNumber;
    }

    /* ••••••••••••• GETTERS / SETTERS ••••••••••••• */
    /**
     * Définit le fichier source du graphe.
     *
     * @param fichier le chemin du fichier source
     */
    public void setFile(String fichier) {
        this.file = fichier;
    }

    /**
     * Définit le graphe.
     *
     * @param g le graphe à utiliser
     */
    public void setFileGraph(Graph g) {
        this.fileGraph = g;
    }

    public void setKmax(int kmax) {
        this.kmax = kmax;
    }

    public void setNbNode(int nbNode) {
        this.nbNode = nbNode;
    }

    /**
     * Renvoie le fichier source du graphe.
     *
     * @return le chemin du fichier source
     */
    public String getFile() {
        return file;
    }

    /**
     * Renvoie le graphe.
     *
     * @return le graphe
     */
    public Graph getFileGraph() {
        return fileGraph;
    }

    public int getKmax() {
        return kmax;
    }

    public int getNbNode() {
        return nbNode;
    }

    /**
     * Méthode principale pour tester l'algorithme de coloration. Charge un
     * graphe à partir d'un fichier, applique l'algorithme de DSATUR suivi de
     * Welsh-Powell pour minimiser les conflits de coloration, et affiche le
     * graphe colorié avec les conflits réduits.
     *
     * @param args les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Graph graph = new MultiGraph("test");
        Coloration test = new Coloration(graph);
        test.setFile("src\\main\\java\\data\\test\\graph-test10.txt");
        try {
            test = ToolBox.fillGraph(test.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ColorationAlgorithm cA = new ColorationAlgorithm(test);
        System.out.println(cA.minConflict().getConflict());

        //test.generateFiles("src/main/java/data/test");
    }
}
