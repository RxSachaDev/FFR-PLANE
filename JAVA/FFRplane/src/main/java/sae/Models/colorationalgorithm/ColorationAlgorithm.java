package sae.models.colorationalgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import sae.exceptions.FileFormatException;
import sae.models.toolbox.ToolBox;

/**
 * La classe Coloration permet de lire un graphe à partir d'un fichier, de
 * l'afficher, et d'appliquer des algorithmes de coloration pour minimiser les
 * conflits de coloration.
 */
public class ColorationAlgorithm {

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

    private ToolBox toolBox = new ToolBox();


    /**
     * Constructeur par défaut qui initialise un nouveau MultiGraph.
     */
    public ColorationAlgorithm() {
        fileGraph = new MultiGraph("test");
    }

    /**
     * Constructeur qui initialise un nouveau MultiGraph avec un graphe donné.
     *
     * @param graph le graphe à utiliser
     */
    public ColorationAlgorithm(Graph graph) {
        this.fileGraph = graph = new MultiGraph("test");
    }

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
     * Charge le graphe à partir du fichier source.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit
     * @throws java.io.FileNotFoundException
     */
    public void fillGraph() throws IOException {
        int lineCount = 0;
        FileInputStream fileInputStream = new FileInputStream(this.file);
        Scanner scanner = new Scanner(fileInputStream);

        // Lire le fichier ligne par ligne
        try {
            while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                String[] elements = line.split("\\s+");
                if (elements.length > 2){
                    throw new NumberFormatException();
                }
                
                    switch (lineCount) {
                        // La première ligne contient kmax
                        case 0:
                            kmax = Integer.parseInt(elements[0]);
                            fileGraph.addAttribute("kmax", kmax);
                            break;
                        // La deuxième ligne contient le nombre de sommets
                        case 1:
                            nbNode = Integer.parseInt(elements[0]);
                            fileGraph.addAttribute("nbSommet", nbNode);
                            break;
                        // Les lignes suivantes contiennent les arêtes
                        default:
                            if (elements.length == 2) {
                                Node node1 = fileGraph.getNode(elements[0]);
                                Node node2 = fileGraph.getNode(elements[1]);
                                // Ajouter les sommets s'ils n'existent pas déjà
                                if (node1 == null) {
                                    node1 = fileGraph.addNode(elements[0]);
                                }
                                if (node2 == null) {
                                    node2 = fileGraph.addNode(elements[1]);
                                }
                                // Ajouter une arête entre les sommets
                                String edgeId = elements[0] + "_" + elements[1];
                                fileGraph.addEdge(edgeId, node1, node2);
                                break;
                            } else {
                                Node node1 = fileGraph.getNode(elements[0]);
                                if (node1 == null) {
                                    node1 = fileGraph.addNode(elements[0]);
                                    node1.setAttribute("color", 1);
                                }
                            }
                    }
                }

                lineCount++;
            }
        } catch( NumberFormatException | IndexOutOfBoundsException problem){
            throw new FileFormatException(lineCount, this.file);
        } 
        
        
    }

    /**
     * Applique l'algorithme de Welsh-Powell pour colorier le graphe. Réduit les
     * conflits en ajustant les couleurs.
     *
     * @return le nombre de conflits après la coloration
     */
    public int welshPowell() {
        WelshPowell wp = new WelshPowell("color");
        wp.init(fileGraph);
        wp.compute();

        int chromaticNumber = wp.getChromaticNumber();

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
                        int nbConflicts = countConflicts(fileGraph);

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
        for (Node node : fileGraph.getEachNode()) {
            node.setAttribute("color", (int) node.getAttribute("color") + 1);
        }

        toolBox.colorGraph(fileGraph);

        return countConflicts(fileGraph);
    }

    /**
     * Applique l'algorithme DSATUR pour colorier le graphe.
     *
     * @param graph le graphe à colorier
     * @return le nombre de conflits après la coloration
     */
    public int dsatur(Graph graph) {
        Node[] tab = orderByDegreeNodes(graph);
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

        int chromaticNumber = countChromaticNumber(graph);

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
        chromaticNumber = countChromaticNumber(graph);
        toolBox.colorGraph(graph);
        return countConflicts(graph);
    }

    /**
     * Minimise les conflits de coloration en utilisant les algorithmes DSATUR
     * et Welsh-Powell.
     *
     * @return le nombre de conflits après la coloration
     */
    public ResultatColoration minConflict() {
        String algorithm = "Dsatur";
        int conflict = dsatur(fileGraph);
        Graph saveGraph = copyGraphWithAttributes(fileGraph);
        if (conflict != 0) {
            fileGraph = new MultiGraph(file);
            try {
                fillGraph();
            } catch (IOException e) {
                e.printStackTrace();
            }
            int wp = welshPowell();
            if (conflict > wp) {

                conflict = wp;
                saveGraph = copyGraphWithAttributes(fileGraph);
                algorithm = "WelshPowell";
            }
        }

        toolBox.colorGraph(saveGraph);
        return new ResultatColoration(conflict, saveGraph, algorithm);
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

    public int countChromaticNumber(Graph g) {
        int chromaticNumber = 0;
        for (Node node : g) {
            if ((int) node.getAttribute("color") > chromaticNumber) {
                chromaticNumber = (int) node.getAttribute("color");
            }
        }
        return chromaticNumber;
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

    public Graph copyGraphWithAttributes(Graph original) {
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
     * Génère les fichiers de résultats de coloration pour les graphes dans un
     * répertoire donné. Les fichiers générés comprennent un fichier CSV
     * répertoriant les noms des fichiers et le nombre de conflits de
     * coloration, ainsi que des fichiers texte contenant les colorations des
     * graphes.
     *
     * @param directoryPath le chemin du répertoire contenant les fichiers de
     * graphes
     */
    private void generateFiles(String directoryPath) {
        ArrayList<String> filesList = new ArrayList<>();

        File directory = new File(directoryPath);
        // Création de la liste de fichiers
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        // Incrémentation de la liste de fichiers
        if (files != null) {
            for (File file : files) {
                filesList.add(file.getName());
            }
        }

        // Création du fichier .csv
        try (FileWriter csvWriter = new FileWriter("src/main/java/data/test/coloration-groupeX.Y.csv")) {
            csvWriter.append("nomFichier.txt;nbre conflits\n"); // 1ere ligne qui montre le format d'écriture

            // Boucle permettant la conception des fichiers colo-eval
            for (int i = 0; i < filesList.size(); i++) {
                String nomFichier = filesList.get(i);
                setFile(directoryPath + "/" + nomFichier);
                try {
                    fillGraph();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ResultatColoration res = minConflict();
                int nbConflicts = res.getConflict();

                csvWriter.append(nomFichier + ";" + nbConflicts + "\n"); // Ajout de la nouvelle ligne avec le même format d'écriture dans le fichier.csv

                String nameColoFile = "colo-eval" + i + ".txt";

                //Création du fichier .txt
                try (FileWriter coloWriter = new FileWriter(nameColoFile)) {
                    for (Node node : res.getGraph()) {
                        coloWriter.write(node.getId() + " ; " + node.getAttribute("color") + "\n"); // Ajout de la nouvelle ligne avec le format sommet; couleur
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fileGraph = new MultiGraph(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ajouter les fichiers à une archive (zip) pour le groupe
        try {
            // Crée un flux de sortie pour le fichier ZIP à générer
            java.util.zip.ZipOutputStream zipOut = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream("src/main/java/data/test/coloration-eval.zip"));

            // Parcourt la liste des fichiers à ajouter à l'archive ZIP
            for (int i = 0; i < filesList.size(); i++) {
                // Détermine le nom du fichier à zipper
                String nameTextFile = "colo-eval" + i + ".txt";

                // Crée un objet File pour le fichier à zipper
                java.io.File fileToZip = new java.io.File(nameTextFile);

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
        Graph graph = new MultiGraph("test");
        ColorationAlgorithm test = new ColorationAlgorithm(graph);
        /*test.setFichier("src/main/java/data/test/graph-test17.txt");
        try {
            test.charger_graphe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(test.minConflict().getConflict());*/

        //test.generateFiles("src/main/java/data/test");
    }
}
