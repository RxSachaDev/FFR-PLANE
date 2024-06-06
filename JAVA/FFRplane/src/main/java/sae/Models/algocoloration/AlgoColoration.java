/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sae.Models.algocoloration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

/**
 * La classe AlgoColoration permet de charger un graphe à partir d'un fichier,
 * d'appliquer des algorithmes de coloration pour minimiser les conflits de
 * coloration, et de générer des fichiers de sortie.
 *
 * @author Sacha
 */
public class AlgoColoration {

    /**
     * Le nombre maximum de couleurs utilisables pour colorer le graphe.
     */
    private int kmax;

    /**
     * Le nombre de sommets dans le graphe.
     */
    private int nbSommet;

    /**
     * Le nom du fichier contenant les données du graphe.
     */
    private String fichier;

    /**
     * L'objet Graph représentant le graphe à colorer.
     */
    private Graph graph;

    /**
     * Constructeur par défaut qui initialise un nouveau MultiGraph.
     */
    public AlgoColoration() {
        graph = new MultiGraph(fichier);
    }

    /**
     * Constructeur qui initialise un nouveau MultiGraph avec un graphe donné.
     *
     * @param graph le graphe à utiliser
     */
    public AlgoColoration(Graph graph) {
        this.graph = graph = new MultiGraph(fichier);
    }

    /**
     * Définit le fichier source du graphe.
     *
     * @param fichier le chemin du fichier source
     */
    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    /**
     * Définit le graphe.
     *
     * @param g le graphe à utiliser
     */
    public void setGraph(Graph g) {
        this.graph = g;
    }

    /**
     * Renvoie le fichier source du graphe.
     *
     * @return le chemin du fichier source
     */
    public String getFichier() {
        return fichier;
    }

    /**
     * Renvoie le graphe.
     *
     * @return le graphe
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * Affiche le graphe en utilisant l'interface utilisateur GraphStream.
     *
     * @param graph le graphe à afficher
     */
    public void afficherGraphe(Graph graph) {
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing");
        graph.display();
    }

    /**
     * Charge le graphe à partir du fichier source.
     *
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
                switch (cpt) {
                    // La deuxième ligne contient le nombre de sommets
                    case 0:
                        kmax = Integer.parseInt(elements[0]);
                        graph.addAttribute("kmax", kmax);
                        break;
                    // Les lignes suivantes contiennent les arêtes
                    case 1:
                        nbSommet = Integer.parseInt(elements[0]);
                        graph.addAttribute("nbSommet", nbSommet);
                        break;
                    default:
                        Node node1 = graph.getNode(elements[0]);
                        Node node2 = graph.getNode(elements[1]);
                        // Ajouter les sommets s'ils n'existent pas déjà
                        if (node1 == null) {
                            node1 = graph.addNode(elements[0]);
                        }
                        if (node2 == null) {
                            node2 = graph.addNode(elements[1]);
                        }
                        // Ajouter une arête entre les sommets
                        String edgeId = elements[0] + "_" + elements[1];
                        graph.addEdge(edgeId, node1, node2);
                        break;
                }
                cpt++;
            }
        }
    }

    /**
     * Crée une copie d'un graphe.
     *
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
     *
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
     * Applique l'algorithme de Welsh-Powell pour colorier le graphe. Réduit les
     * conflits en ajustant les couleurs.
     *
     * @return le nombre de conflits après la coloration
     */
    public int welshPowell() {
        WelshPowell wp = new WelshPowell("color");
        wp.init(graph);
        wp.compute();

        int chromaticNumber = wp.getChromaticNumber();
        System.out.println("Chromatic Number: " + chromaticNumber);

        // Ajuster la coloration si le nombre chromatique dépasse kmax
        if (chromaticNumber > kmax) {
            for (int i = 0; i < 20; i++) {
                for (Node aloneNode : graph.getEachNode()) {
                    int minConflicts = Integer.MAX_VALUE;
                    int bestColor = -1;

                    // Parcourir toutes les couleurs possibles pour ce nœud
                    for (int color = 0; color < kmax; color++) {
                        // Appliquer temporairement la couleur et compter les conflits
                        aloneNode.setAttribute("color", color);
                        int nbConflicts = countConflicts(graph);

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
        chromaticNumber = 0;
        for (Node node : graph) {
            if ((int) node.getAttribute("color") > chromaticNumber) {
                chromaticNumber = (int) node.getAttribute("color");
            }
        }
        System.out.println(chromaticNumber + 1);
        return countConflicts(graph);
    }

    /**
     * Trie les nœuds par degré décroissant.
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
        if (chromaticNumber > kmax) {
            for (int i = 0; i < 20; i++) {
                for (Node aloneNode : g.getEachNode()) {
                    int minConflicts = Integer.MAX_VALUE;
                    int bestColor = -1;

                    // Parcourir toutes les couleurs possibles pour ce nœud
                    for (int color = 1; color <= kmax; color++) {
                        // Appliquer temporairement la couleur et compter les conflits
                        aloneNode.setAttribute("color", color);
                        int nbConflicts = countConflicts(g);

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
        chromaticNumber = 0;
        for (Node node : g) {
            if ((int) node.getAttribute("color") > chromaticNumber) {
                chromaticNumber = (int) node.getAttribute("color");
            }
        }
        System.out.println(chromaticNumber);
        return countConflicts(g);
    }

    private Graph copyGraphWithAttributes(Graph original) {
        Graph copy = new MultiGraph("copy");
        for (Node node : original.getEachNode()) {
            Node copyNode = copy.addNode(node.getId());
            copyNode.addAttribute("color", (int) node.getAttribute("color"));
        }
        for (Edge edge : original.getEachEdge()) {
            copy.addEdge(edge.getId(), edge.getNode0().getId(), edge.getNode1().getId());
        }
        return copy;
    }

    /**
     * La classe ResultatColoration représente le résultat d'une tentative de
     * coloration de graphe, incluant le nombre de conflits et le graphe coloré.
     *
     * @author Sacha
     */
    private class ResultatColoration {

        /**
         * Le nombre de conflits dans la coloration du graphe.
         */
        private int conflict;

        /**
         * Le graphe coloré résultant.
         */
        private Graph graph;

        /**
         * Constructeur de la classe ResultatColoration.
         *
         * @param conflict Le nombre de conflits dans la coloration.
         * @param graph Le graphe coloré.
         */
        public ResultatColoration(int conflict, Graph graph) {
            this.conflict = conflict;
            this.graph = graph;
        }

        /**
         * Retourne le nombre de conflits dans la coloration du graphe.
         *
         * @return Le nombre de conflits.
         */
        public int getConflict() {
            return conflict;
        }

        /**
         * Retourne le graphe coloré.
         *
         * @return Le graphe coloré.
         */
        public Graph getGraph() {
            return graph;
        }
    }

    /**
     * Minimise les conflits de coloration en utilisant les algorithmes DSATUR
     * et Welsh-Powell.
     *
     * @return le nombre de conflits après la coloration
     */
    public ResultatColoration minConflict() {
        int conflict = dsatur(graph);
        System.out.println(conflict);
        Graph saveGraph = copyGraphWithAttributes(graph);
        if (conflict != 0) {
            graph = new MultiGraph(fichier);
            try {
                charger_graphe();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int wp = welshPowell();
            if (conflict > wp) {
                System.out.println("WP");
                conflict = wp;
                saveGraph = copyGraphWithAttributes(graph);
            }
        }

        for (Node node : saveGraph) {
            String[] colors = {
                "red", "green", "blue", "yellow", "cyan", "magenta", "orange", "pink", "purple", "brown",
                "maroon", "navy", "teal", "olive", "lime", "aqua", "fuchsia", "silver", "gray", "black",
                "indianred", "lightcoral", "salmon", "darksalmon", "lightsalmon", "crimson", "firebrick", "darkred", "red",
                "orangered", "tomato", "coral", "darkorange", "orange", "gold", "yellow", "lightyellow", "lemonchiffon",
                "lightgoldenrodyellow", "papayawhip", "moccasin", "peachpuff", "palegoldenrod", "khaki", "darkkhaki",
                "lavender", "thistle", "plum", "violet", "orchid", "fuchsia", "magenta", "mediumorchid", "mediumpurple",
                "rebeccapurple", "blueviolet", "darkviolet", "darkorchid", "darkmagenta", "purple", "indigo", "slateblue",
                "darkslateblue", "mediumslateblue", "greenyellow", "chartreuse", "lawngreen", "lime", "limegreen", "palegreen",
                "lightgreen", "mediumspringgreen", "springgreen", "mediumseagreen", "seagreen", "forestgreen", "green", "darkgreen",
                "yellowgreen", "olivedrab", "darkolivegreen", "mediumaquamarine", "darkseagreen", "lightseagreen", "darkcyan",
                "cyan", "lightcyan", "paleturquoise", "aquamarine", "turquoise", "mediumturquoise", "darkturquoise", "cadetblue",
                "steelblue", "lightsteelblue", "powderblue", "lightblue", "skyblue", "lightskyblue", "deepskyblue", "dodgerblue",
                "cornflowerblue", "royalblue", "blue", "mediumblue", "darkblue", "navy", "midnightblue", "cornsilk", "blanchedalmond",
                "bisque", "navajowhite", "wheat", "burlywood", "tan", "rosybrown", "sandybrown", "goldenrod", "darkgoldenrod",
                "peru", "chocolate", "saddlebrown", "sienna", "brown", "darkred", "azure", "aliceblue", "mintcream", "honeydew", "lightcoral", "cornflowerblue", "skyblue", "thistle", "seashell", "lavender",
                "blanchedalmond", "bisque", "antiquewhite", "floralwhite", "ghostwhite", "oldlace", "linen", "mistyrose", "peachpuff", "navajowhite",
                "palegoldenrod", "lightgoldenrodyellow", "lemonchiffon", "lightyellow", "papayawhip", "moccasin", "khaki", "darkkhaki", "ivory",
                "beige", "lightgrey", "lightsteelblue", "lightslategray", "slategray", "dimgrey", "darkslategray", "grey", "darkgrey", "lightslategrey",
                "midnightblue", "navy", "darkblue", "mediumblue", "blue", "darkgreen", "darkolivegreen", "olive", "olivedrab", "yellowgreen",
                "greenyellow", "darkseagreen", "forestgreen", "limegreen", "lightgreen", "palegreen", "springgreen", "mediumspringgreen", "lawngreen",
                "chartreuse", "aquamarine", "mediumaquamarine", "paleturquoise", "lightseagreen", "darkturquoise", "cadetblue", "darkcyan", "teal",
                "lightcyan", "powderblue", "lightblue", "deepskyblue", "dodgerblue", "cornflowerblue", "steelblue", "royalblue", "mediumslateblue",
                "slateblue", "darkslateblue", "mediumorchid", "blueviolet", "darkviolet", "darkorchid", "darkmagenta", "purple", "indigo", "mediumpurple",
                "thistle", "plum", "violet", "orchid", "fuchsia", "magenta", "mediumorchid", "mediumpurple", "rebeccapurple"
            };
            if ((int) node.getAttribute("color") < colors.length) {
                String color = colors[(int) node.getAttribute("color")];
                node.setAttribute("ui.style", "fill-color: " + color + ";");
            }

        }
        //afficherGraphe(saveGraph);
        return new ResultatColoration(conflict, saveGraph);
    }

    /**
     * Génère les fichiers de résultats de coloration pour les graphes dans un
     * répertoire donné. Les fichiers générés comprennent un fichier CSV
     * répertoriant les noms des fichiers et le nombre de conflits de
     * coloration, ainsi que des fichiers texte contenant les colorations des
     * graphes.
     *
     * @param directoryPath le chemin du répertoire contenant les fichiers de
     * graphes
     */
    private void genererFichiers(String directoryPath) {
        ArrayList<String> fichierListe = new ArrayList<>();

        File directory = new File(directoryPath);
        // Création de la liste de fichiers
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        // Incrémentation de la liste de fichiers
        if (files != null) {
            for (File file : files) {
                fichierListe.add(file.getName());
            }
        }

        // Création du fichier .csv
        try (FileWriter csvWriter = new FileWriter("src/main/java/data/test/coloration-groupeX.Y.csv")) {
            csvWriter.append("nomFichier.txt;nbre conflits\n"); // 1ere ligne qui montre le format d'écriture

            // Boucle permettant la conception des fichiers colo-eval
            for (int i = 0; i < fichierListe.size(); i++) {
                String nomFichier = fichierListe.get(i);
                setFichier(directoryPath + "/" + nomFichier);
                try {
                    charger_graphe();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ResultatColoration res = minConflict();
                int nbConflits = res.getConflict();

                csvWriter.append(nomFichier + ";" + nbConflits + "\n"); // Ajout de la nouvelle ligne avec le même format d'écriture dans le fichier.csv

                String nomFichierColo = "colo-eval" + i + ".txt";

                //Création du fichier .txt
                try (FileWriter coloWriter = new FileWriter(nomFichierColo)) {
                    for (Node node : res.getGraph()) {
                        coloWriter.write(node.getId() + " ; " + node.getAttribute("color") + "\n"); // Ajout de la nouvelle ligne avec le format sommet; couleur
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                graph = new MultiGraph(fichier);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ajouter les fichiers à une archive (zip) pour le groupe
        try {
            // Crée un flux de sortie pour le fichier ZIP à générer
            java.util.zip.ZipOutputStream zipOut = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream("src/main/java/data/test/coloration-eval.zip"));

            // Parcourt la liste des fichiers à ajouter à l'archive ZIP
            for (int i = 0; i < fichierListe.size(); i++) {
                // Détermine le nom du fichier à zipper
                String nomFichier = "colo-eval" + i + ".txt";

                // Crée un objet File pour le fichier à zipper
                java.io.File fileToZip = new java.io.File(nomFichier);

                // Crée un flux d'entrée pour lire le contenu du fichier à zipper
                java.io.FileInputStream fis = new java.io.FileInputStream(fileToZip);

                // Crée une nouvelle entrée ZIP pour le fichier à zipper
                java.util.zip.ZipEntry zipEntry = new java.util.zip.ZipEntry(fileToZip.getName());

                // Ajoute l'entrée ZIP au flux de sortie ZIP
                zipOut.putNextEntry(zipEntry);

                // Tampon pour lire les données du fichier
                byte[] bytes = new byte[1024];
                int length;

                // Lit les données du fichier et les écrit dans l'archive ZIP
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }

                // Ferme le flux d'entrée du fichier
                fis.close();
            }

            // Ferme le flux de sortie ZIP
            zipOut.close();
        } catch (IOException e) {
            // Gère les exceptions d'entrée/sortie en imprimant la trace de la pile
            e.printStackTrace();
        }
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
        Graph graph = new MultiGraph(null);
        AlgoColoration test = new AlgoColoration(graph);
        test.setFichier("src/main/java/data/test/graph-test7.txt");
        try {
            test.charger_graphe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(test.minConflict().getConflict());

        //test.genererFichiers("src/main/java/data/test");
    }

}
