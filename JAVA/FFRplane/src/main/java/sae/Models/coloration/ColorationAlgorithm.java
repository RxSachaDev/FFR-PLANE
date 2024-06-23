/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Models.coloration;

import java.io.IOException;
import java.util.ArrayList;
import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import sae.models.toolbox.ToolBox;

/**
 * Cette classe représente un algorithme de coloration pour les graphes.
 * Elle contient des méthodes pour appliquer différents algorithmes de coloration
 * et réduire les conflits de coloration.
 * 
 */
public class ColorationAlgorithm {
    
    /**
     * L'objet Coloration qui contient l'adresse du fichier.
     */
    private Coloration coloration;
    
    /**
     * Constructeur de la classe ColorationAlgorithm.
     * 
     * @param coloration l'objet Coloration utilisé pour la coloration du graphe
     */
    public ColorationAlgorithm(Coloration coloration) {
        this.coloration = coloration;
    }
    
    /**
     * Applique l'algorithme de Welsh-Powell pour colorier le graphe. Réduit les
     * conflits en ajustant les couleurs.
     *
     * @return le nombre de conflits après la coloration
     */
    public int welshPowell() {
        WelshPowell wp = new WelshPowell("color");
        int chromaticNumber = 0;
        int kmax = coloration.getKmax();
        Graph fileGraph = coloration.getFileGraph();
        wp.init(fileGraph);
        wp.compute();

        chromaticNumber = wp.getChromaticNumber();
        
        // Ajuster la coloration si le nombre chromatique dépasse kmax
        if (kmax != -1 && chromaticNumber > kmax) {
            for (int i = 0; i < 20; i++) {
                for (Node aloneNode : fileGraph.getEachNode()) {
                    int minConflicts = Integer.MAX_VALUE;
                    int bestColor = -1;

                    // Parcourir toutes les couleurs possibles pour ce nœud
                    for (int color = 0; color < kmax; color++) {
                        // Appliquer temporairement la couleur et compter les conflits
                        aloneNode.setAttribute("color", color);
                        int nbConflicts = coloration.countConflicts(fileGraph);

                        // Mettre à jour la meilleure couleur si elle minimise les conflits
                        if (nbConflicts < minConflicts) {
                            minConflicts = nbConflicts;
                            bestColor = color;
                        }
                    }

                    // Appliquer la meilleure couleur trouvée avec le minimum de conflits
                    aloneNode.setAttribute("color", bestColor);
                }
            }
        }
        
        // Ajuster les couleurs des nœuds pour qu'elles soient indexées à partir de 1
        for (Node node : fileGraph.getEachNode()) {
            node.setAttribute("color", (int) node.getAttribute("color") + 1);
        }

        ToolBox.colorGraph(fileGraph);
        coloration.setFileGraph(fileGraph);
        return coloration.countConflicts(fileGraph);
    }
    
    /**
     * Trie les nœuds par degré décroissant.
     *
     * @param graph le graphe à trier
     * @return un tableau de nœuds triés par degré décroissant
     */
    public Node[] orderByDegreeNodes(Graph graph) {
        Node[] tab = new Node[graph.getNodeCount()];
        // Insérer chaque nœud à la position appropriée dans le tableau trié
        for (Node node : graph) {
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
     *
     * @param nodes le tableau de nœuds à vérifier
     * @return true si tous les nœuds ne sont pas encore coloriés, sinon false
     */
    public boolean colorNotFill(Node[] nodes) {
        boolean notFill = false;
        // Parcourir tous les nœuds et vérifier si la couleur est null
        for (int i = 0; i < nodes.length && !notFill; i++) {
            if (nodes[i].getAttribute("color") == null) {
                notFill = true;
            }
        }
        return notFill;
    }

    /**
     * Trouve le nœud non utilisé avec le degré le plus élevé.
     *
     * @param nodes le tableau de nœuds à vérifier
     * @return le nœud avec le plus haut degré non utilisé
     */
    public Node highestUnusedDegree(Node[] nodes) {
        Node val = null;
        int maxDegree = -1;
        // Parcourir tous les nœuds pour trouver celui avec le plus grand nbColor
        for (Node node : nodes) {
            if (node.getAttribute("color") == null) {
                int degree = (int) node.getAttribute("nbColor");
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
     *
     * @param node le nœud dont les voisins doivent être mis à jour
     */
    public void setNbColorOpposite(Node node) {
        // Parcourir tous les voisins du nœud
        for (Edge edge : node.getEdgeSet()) {
            Node otherNode = edge.getOpposite(node);
            ArrayList<Integer> couleurAutour = otherNode.getAttribute("couleurAutour");
            if (couleurAutour == null) {
                couleurAutour = new ArrayList<>();
                otherNode.setAttribute("couleurAutour", couleurAutour);
            }
            Integer color = node.getAttribute("color");
            if (!couleurAutour.contains(color)) {
                couleurAutour.add(color);
                int nbColor = couleurAutour.size();
                otherNode.setAttribute("nbColor", nbColor);
            }
        }
    }

    /**
     * Applique une couleur à un nœud en évitant les conflits.
     *
     * @param node le nœud à colorier
     */
    public void putColor(Node node) {
        boolean coloring = false;
        int i = 1;
        // Trouver une couleur non conflictuelle
        while (!coloring) {
            boolean conflict = false;
            // Vérifier les couleurs des voisins
            for (Edge edge : node.getEdgeSet()) {
                Node otherNode = edge.getOpposite(node);
                if (otherNode.getAttribute("color") != null && otherNode.getAttribute("color").equals(i)) {
                    conflict = true;
                    break;
                }
            }
            if (!conflict) {
                node.setAttribute("color", i);
                coloring = true;
            } else {
                i++;
            }
        }
    }

    /**
     * Crée une copie d'un graphe en incluant les attributs des nœuds et des arêtes.
     *
     * @param original le graphe original à copier
     * @return une copie du graphe avec les mêmes attributs
     */
    public Graph copyGraphWithAttributes(Graph original) {
        Graph copy = new MultiGraph("copy");
        // Copier tous les nœuds et leurs attributs
        for (Node node : original.getEachNode()) {
            Node copyNode = copy.addNode(node.getId());
            copyNode.addAttribute("color", (int) node.getAttribute("color"));
        }
        // Copier toutes les arêtes
        for (Edge edge : original.getEachEdge()) {
            copy.addEdge(edge.getId(), edge.getNode0().getId(), edge.getNode1().getId());
        }
        return copy;
    }

    /**
     * Applique l'algorithme DSATUR pour colorier le graphe.
     *
     * @param graph le graphe à colorier
     * @return le nombre de conflits après la coloration
     */
    public int dsatur(Graph graph) {
        Node[] tab = orderByDegreeNodes(graph);
        int chromaticNumber = 0;
        int kmax = coloration.getKmax();
        // Initialiser chaque nœud avec son degré
        for (Node node : tab) {
            node.addAttribute("nbColor", node.getDegree());
        }
        tab[0].addAttribute("color", 1);
        setNbColorOpposite(tab[0]);
        // Colorier les nœuds restants
        while (colorNotFill(tab)) {
            Node n = highestUnusedDegree(tab);
            putColor(n);
            setNbColorOpposite(n);
        }
        chromaticNumber = coloration.countChromaticNumber(graph);
        
        // Ajuster la coloration si le nombre chromatique dépasse kmax
        if (chromaticNumber > kmax && kmax != -1) {
            for (int i = 0; i < 20; i++) {
                for (Node aloneNode : graph.getEachNode()) {
                    int minConflicts = Integer.MAX_VALUE;
                    int bestColor = -1;

                    // Parcourir toutes les couleurs possibles pour ce nœud
                    for (int color = 1; color <= kmax; color++) {
                        // Appliquer temporairement la couleur et compter les conflits
                        aloneNode.setAttribute("color", color);
                        int nbConflicts = coloration.countConflicts(graph);

                        // Mettre à jour la meilleure couleur si elle minimise les conflits
                        if (nbConflicts < minConflicts) {
                            minConflicts = nbConflicts;
                            bestColor = color;
                        }
                    }

                    // Appliquer la meilleure couleur trouvée avec le minimum de conflits
                    aloneNode.setAttribute("color", bestColor);
                }
            }
        }
        ToolBox.colorGraph(graph);
        return coloration.countConflicts(graph);
    }

    /**
     * Minimise les conflits de coloration en utilisant les algorithmes DSATUR
     * et Welsh-Powell.
     *
     * @return le nombre de conflits après la coloration, le graphe et l'algorithme le plus performant.
     */
    public ColorationResult minConflict() {
        String algorithm = "Dsatur";
        Graph fileGraph = coloration.getFileGraph();
        String file = coloration.getFile();
        // Test avec l'algorithme Dsatur
        int conflict = dsatur(fileGraph);
        Graph saveGraph = copyGraphWithAttributes(fileGraph);
        if (conflict != 0) {
            // On réintialise le graphe
            fileGraph = new MultiGraph(file);
            try {
                coloration = ToolBox.fillGraph(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int wp = welshPowell();
            // On change les valeurs si WelshPowell est plus performant
            if (conflict > wp) {
                conflict = wp;
                saveGraph = copyGraphWithAttributes(fileGraph);
                algorithm = "WelshPowell";
            }
        }

        ToolBox.colorGraph(saveGraph);
        return new ColorationResult(conflict, saveGraph, algorithm);
    }
}
