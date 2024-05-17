import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * La classe Coloration permet de lire un graphe à partir d'un fichier, de l'afficher,
 * et d'appliquer des algorithmes de coloration pour minimiser les conflits de coloration.
 */
public class Coloration {
    private int kmax;
    private int nbSommet;
    private ArrayList<Integer> sommet = new ArrayList<>();
    private String fichier;
    private Graph graph;

    /**
     * Constructeur par défaut qui initialise un nouveau MultiGraph.
     */
    Coloration() {     
        graph = new MultiGraph(fichier);
    }

    /**
     * Constructeur qui initialise un nouveau MultiGraph avec un graphe donné.
     * @param graph le graphe à utiliser
     */
    Coloration(Graph graph) {
        this.graph = graph = new MultiGraph(fichier);
    }

    /**
     * Définit le fichier source du graphe.
     * @param fichier le chemin du fichier source
     */
    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    /**
     * Définit le graphe.
     * @param g le graphe à utiliser
     */
    public void setGraph(Graph g) {
        this.graph = g;
    }

    /**
     * Renvoie le fichier source du graphe.
     * @return le chemin du fichier source
     */
    public String getFichier() {
        return fichier;
    }

    /**
     * Renvoie le graphe.
     * @return le graphe
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Affiche le graphe en utilisant l'interface utilisateur GraphStream.
     */
    public void afficherGraphe() {
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing");
        graph.display();
    }

    /**
     * Charge le graphe à partir du fichier source.
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    public void charger_graphe() throws IOException {
        int cpt = 0;
        FileInputStream fileInputStream = new FileInputStream(this.fichier);
        Scanner scanner = new Scanner(fileInputStream);

        // Lire le fichier ligne par ligne
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] elements = line.split("\\s+");

                // La première ligne contient kmax
                if (cpt == 0) {
                    kmax = Integer.parseInt(elements[0]);
                    graph.addAttribute("kmax", kmax);
                } 
                // La deuxième ligne contient le nombre de sommets
                else if (cpt == 1) {
                    nbSommet = Integer.parseInt(elements[0]);
                    graph.addAttribute("nbSommet", nbSommet);
                } 
                // Les lignes suivantes contiennent les arêtes
                else {
                    Node node1 = graph.getNode(elements[0]);
                    Node node2 = graph.getNode(elements[1]);

                    // Ajouter les sommets s'ils n'existent pas déjà
                    if (node1 == null)
                        node1 = graph.addNode(elements[0]);
                    if (node2 == null)
                        node2 = graph.addNode(elements[1]);

                    // Ajouter une arête entre les sommets
                    String edgeId = elements[0] + "_" + elements[1];
                    graph.addEdge(edgeId, node1, node2);
                }
                cpt++;
            }
        }
    }

    /**
     * Crée une copie d'un graphe.
     * @param original le graphe original
     * @return une copie du graphe original
     */
    public static Graph copyGraph(Graph original) {
        Graph copy = new MultiGraph("Copy");

        // Copier les nœuds
        for (Node node : original) {
            copy.addNode(node.getId());
        }

        // Copier les arêtes
        for (Edge edge : original.getEachEdge()) {
            String sourceId = edge.getSourceNode().getId();
            String targetId = edge.getTargetNode().getId();
            copy.addEdge(edge.getId(), sourceId, targetId);
        }

        return copy;
    }

    /**
     * Compte le nombre de conflits de coloration dans un graphe.
     * @param graph le graphe à analyser
     * @return le nombre de conflits de coloration
     */
    public int countConflicts(Graph graph) {
        int conflictCount = 0;
        for (Edge edge : graph.getEachEdge()) {
            Node source = edge.getSourceNode();
            Node target = edge.getTargetNode();
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
     * Applique l'algorithme de Welsh-Powell pour colorier le graphe.
     * Réduit les conflits en ajustant les couleurs.
     * @return le nombre de conflits après la coloration
     */
    public int welshPowell() {
        WelshPowell wp = new WelshPowell("color");
        wp.init(graph);
        wp.compute();

        int chromaticNumber = wp.getChromaticNumber();
        System.out.println("Chromatic Number: " + chromaticNumber);

        // Ajuster la coloration si le nombre chromatique dépasse kmax
        while (chromaticNumber > kmax) {
            Graph graphCopy = copyGraph(graph);
            for (Node node : graph) {
                Integer colorIndex = node.getAttribute("color");
                // Supprimer les couleurs dépassant kmax
                if (colorIndex != null && colorIndex >= kmax) {
                    node.removeAttribute("color");
                } 
                // Supprimer les nœuds avec des couleurs valides
                else if (colorIndex != null) {
                    graphCopy.removeNode(node.getId());
                }
            }
            wp.init(graphCopy);
            wp.compute();

            // Appliquer les nouvelles couleurs au graphe original
            for (Node node : graphCopy) {
                Integer colorIndex = node.getAttribute("color");
                if (colorIndex != null) {
                    graph.getNode(node.getId()).setAttribute("color", colorIndex);
                }
            }
            chromaticNumber = wp.getChromaticNumber();
        }
        return countConflicts(graph);
    }

    /**
     * Trie les nœuds par degré décroissant.
     * @param g le graphe à trier
     * @return un tableau de nœuds triés par degré décroissant
     */
    public Node[] rangerParDegreeNodes(Graph g) {
        Node[] tab = new Node[g.getNodeCount()];
        // Insérer chaque nœud à la position appropriée dans le tableau trié
        for (Node node : g) {
            int i = 0;
            while (i < tab.length && tab[i] != null && node.getDegree() < tab[i].getDegree()) {
                i++;
            }
            if (i < tab.length) {
                System.arraycopy(tab, i, tab, i + 1, tab.length - 1 - i); // Décale les éléments du tableau vers la droite
                tab[i] = node;
            }
        }
        return tab;
    }

    /**
     * Vérifie si tous les nœuds du tableau ont été coloriés.
     * @param nodes le tableau de nœuds à vérifier
     * @return true si tous les nœuds ne sont pas encore coloriés, sinon false
     */
    public boolean colorPasRempli(Node[] nodes) {
        boolean pasRempli = false;
        for (int i = 0; i < nodes.length && !pasRempli; i++) {
            if (nodes[i].getAttribute("color") == null) {
                pasRempli = true;
            }
        }
        return pasRempli;
    }

    /**
     * Trouve le nœud non utilisé avec le degré le plus élevé.
     * @param nodes le tableau de nœuds à vérifier
     * @return le nœud avec le plus haut degré non utilisé
     */
    public Node plusHautDegreNonUtilise(Node[] nodes) {
        Node val = null;
        int maxDegree = -1;
        for (Node node : nodes) {
            if (node.getAttribute("color") == null) {
                int degree = node.getAttribute("nbColor");
                if (degree > maxDegree) {
                    maxDegree = degree;
                    val = node;
                }
            }
        }
        return val;
    }

    /**
     * Met à jour le nombre de couleurs des nœuds adjacents.
     * @param n le nœud dont les voisins doivent être mis à jour
     */
    public void setNbColorOpposite(Node n) {
        for (Edge edge : n.getEdgeSet()) {
            Node otherNode = edge.getOpposite(n);
            ArrayList<Integer> couleurAutour = otherNode.getAttribute("couleurAutour");
            if (couleurAutour == null) {
                couleurAutour = new ArrayList<>();
                otherNode.setAttribute("couleurAutour", couleurAutour);
            }
            Integer color = n.getAttribute("color");
            if (!couleurAutour.contains(color)) {
                couleurAutour.add(color);
                int nbColor = couleurAutour.size();
                otherNode.setAttribute("nbColor", nbColor);
            }
        }
    }

    /**
     * Applique une couleur à un nœud en évitant les conflits.
     * @param node le nœud à colorier
     */
    public void appliquerColor(Node node) {
        boolean colore = false;
        int i = 1;
        // Trouver une couleur non conflictuelle
        while (!colore) {
            boolean conflit = false;
            for (Edge edge : node.getEdgeSet()) {
                Node otherNode = edge.getOpposite(node);
                if (otherNode.getAttribute("color") != null && otherNode.getAttribute("color").equals(i)) {
                    conflit = true;
                    break;
                }
            }
            if (!conflit) {
                node.setAttribute("color", i);
                colore = true;
            } else {
                i++;
            }
        }
    }

    /**
     * Applique l'algorithme DSATUR pour colorier le graphe.
     * @param g le graphe à colorier
     * @return le nombre de conflits après la coloration
     */
    public int dsatur(Graph g) {
        Node[] tab = rangerParDegreeNodes(g);
        // Initialiser chaque nœud avec son degré
        for (Node node : tab) {
            node.addAttribute("nbColor", node.getDegree());
        }
        tab[0].addAttribute("color", 1);
        setNbColorOpposite(tab[0]);
        // Colorier les nœuds restants
        while (colorPasRempli(tab)) {
            Node n = plusHautDegreNonUtilise(tab);
            appliquerColor(n);
            setNbColorOpposite(n);
        }

        int chromaticNumber = 0;
        for (Node node : g) {
            if ((int) node.getAttribute("color") > chromaticNumber) {
                chromaticNumber = (int) node.getAttribute("color");
            }
        }

        // Ajuster la coloration si le nombre chromatique dépasse kmax
        while (chromaticNumber > kmax) {
            Graph graphCopy = copyGraph(g);
            for (Node node : g) {
                Integer colorIndex = (Integer) node.getAttribute("color");
                if (colorIndex >= kmax) {
                    node.removeAttribute("color");
                    node.removeAttribute("nbColor");
                    node.removeAttribute("couleurAutour");
                } else {
                    graphCopy.removeNode(node.getId());
                }
            }
            dsatur(graphCopy);
            for (Node node : graphCopy) {
                Integer colorIndex = (Integer) node.getAttribute("color");
                g.getNode(node.getId()).setAttribute("color", colorIndex.intValue());
            }

            chromaticNumber = 0;
            for (Node node : g) {
                if ((int) node.getAttribute("color") > chromaticNumber) {
                    chromaticNumber = (int) node.getAttribute("color");
                }
            }
        }
        return countConflicts(g);
    }

    /**
     * Minimise les conflits de coloration en utilisant les algorithmes DSATUR et Welsh-Powell.
     * @return le nombre de conflits après la coloration
     */
    public int minConflict() {
        int conflict = dsatur(graph);
        System.out.println(conflict);
        graph = new MultiGraph(fichier);
        try {
            charger_graphe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (conflict > welshPowell()) {
            conflict = welshPowell();
        }
        for (Node node : graph) {
            System.out.println(node + ": " + node.getAttribute("color"));
        }
        return conflict;
    }
}
