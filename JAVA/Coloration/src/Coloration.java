import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class Coloration {
    private int kmax;
    private int nbSommet;
    private ArrayList<Integer> sommet = new ArrayList<>();
    private String fichier;
    private Graph graph;


    Coloration() {     
        graph = new MultiGraph(fichier);
    }

    Coloration( Graph graph){
        this.graph = graph = new MultiGraph(fichier);
        
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getFichier() {
        return fichier;
    }

    public Graph getGraph() {
        return graph;
    }
    

    public void afficherGraphe() {
        // Définir le package d'interface utilisateur à utiliser
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing");

        graph.display();
    }

    public void charger_graphe(String f) throws IOException {
        int cpt = 0;
        FileInputStream fileInputStream = new FileInputStream(f);
        Scanner scanner = new Scanner(fileInputStream);
    
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] elements = line.split("\\s+");
    
                if (cpt == 0) {
                    kmax = Integer.parseInt(elements[0]);
                    graph.addAttribute("kmax", kmax);
                } else if (cpt == 1) {
                    nbSommet = Integer.parseInt(elements[0]);
                    graph.addAttribute("nbSommet", nbSommet);
                } else {
                    Node node1 = graph.getNode(elements[0]);
                    Node node2 = graph.getNode(elements[1]);
    
                    if (node1 == null)
                        node1 = graph.addNode(elements[0]);
                    if (node2 == null)
                        node2 = graph.addNode(elements[1]);
    
                    String edgeId = elements[0] + "_" + elements[1];
    
                    graph.addEdge(edgeId, node1, node2);
                }
                cpt++;
            }
        }
    }

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

    public int welshPowell() {
        // Appliquer l'algorithme de Welsh-Powell
        WelshPowell wp = new WelshPowell("color");
        wp.init(graph);
        wp.compute();
    
        Integer chromaticNumber = wp.getChromaticNumber();
        System.out.println("Chromatic Number: " + chromaticNumber);
    
        while (chromaticNumber > kmax) {
            Graph graphCopy = copyGraph(graph);
            for (Node node : graph) {
                Integer colorIndex = (Integer) node.getAttribute("color"); // Cast en Integer
                if (colorIndex >= kmax) { // Si la couleur dépasse kmax
                    node.removeAttribute("color"); // Supprimer la couleur attribuée
                } else {
                    graphCopy.removeNode(node.getId());
                }
            }
            // Appliquer à nouveau l'algorithme de Welsh-Powell récursivement jusqu'à ce que chromaticNumber <= kmax
            wp.init(graphCopy);
            wp.compute(); // Appel récursif
    
            // Mettre à jour les couleurs dans le graphe original avec les couleurs du graphe copié
            for (Node node : graphCopy) {
                Integer colorIndex = (Integer) node.getAttribute("color"); // Cast en Integer
                graph.getNode(node.getId()).setAttribute("color", colorIndex.intValue()); // Convertir en int
            }
    
            // Mettre à jour chromaticNumber après l'appel récursif
            chromaticNumber = wp.getChromaticNumber();
        }
    
        // Attribuer les couleurs mises à jour aux nœuds du graphe original
        String[] colors = {"red", "blue", "green", "yellow", "orange", "purple", "pink", "cyan", "magenta", "brown"};
        for (Node node : graph) {
            Integer colorIndex = (Integer) node.getAttribute("color"); // Cast en Integer
            if (colorIndex != null && colorIndex >= 0 && colorIndex < colors.length) {
                String color = colors[colorIndex];
                node.setAttribute("ui.style", "fill-color: " + color + ";");
            }
        }
        return countConflicts(graph);
    }

    public Node[] rangerParDegreeNodes() {
        Node[] tab = new Node[graph.getNodeCount()];
        for (Node node : graph) {
            int i = 0;
            while (i < tab.length && tab[i] != null && node.getDegree() < tab[i].getDegree()) {
                i++;
            }
            if (i < tab.length) {
                System.arraycopy(tab, i, tab, i + 1, tab.length - 1 - i);
                tab[i] = node;
            }
        }
        return tab;
    }

    public boolean colorPasRempli(Node[] node){
        boolean pasRempli = false;
        for (int i = 0; i<node.length && !pasRempli ;i++){
            if (node[i].getAttribute("color") == null){
                pasRempli = true;
            }
        }
        return pasRempli;
    }

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
                int nbColor = otherNode.getAttribute("nbColor");
                otherNode.setAttribute("nbColor", nbColor + 1);
            }
        }
    }

    public void appliquerColor(Node node) {
        boolean colore = false;
        int i = 1;
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
     
    public int dsatur() {
        Node[] tab = rangerParDegreeNodes();
        for (Node node : tab) {
            node.addAttribute("nbColor", node.getDegree());
            node.addAttribute("couleurAutour", new ArrayList<Integer>());
        }
        tab[0].addAttribute("color", 1);
        setNbColorOpposite(tab[0]);
        while (colorPasRempli(tab)) {
            Node n = plusHautDegreNonUtilise(tab);
            appliquerColor(n);
            setNbColorOpposite(n);       
        }
        for (Node node : graph) {
            System.out.println(node.getId() + ": " + node.getAttribute("color"));
        }
        return countConflicts(graph);
    }
}


