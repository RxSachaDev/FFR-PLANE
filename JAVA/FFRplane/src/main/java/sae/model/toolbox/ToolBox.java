package sae.model.toolbox;

import java.awt.Event;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;
import sae.exceptions.DataMismatchException;
import sae.model.airports.Airport;
import sae.model.airports.AirportsCatalog;
import sae.exceptions.FileFormatException;
import sae.model.flights.Flight;
import sae.model.flights.FlightsCatalog;
import sae.model.intersection.FlightCollisionTools;
import sae.utils.Settings;
import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.implementations.MultiGraph;
import sae.model.coloration.Coloration;
import sae.model.coloration.ColorationAlgorithm;
import sae.model.coloration.ColorationResult;

/**
 * La classe ToolBox fournit des méthodes utilitaires pour différents calculs.
 *
 * @author mathe
 */
public class ToolBox {

    /**
     * Calcule la distance entre deux points dans un plan cartésien.
     *
     * @param pointA les coordonnées du premier point (x, y)
     * @param pointB les coordonnées du deuxième point (x, y)
     * @return la distance entre pointA et pointB
     */
    public static double calDistance(double[] pointA, double[] pointB) {
        // distanceAB = sqrt((xB - xA)^2 + (yB - yA)^2)
        return Math.sqrt(Math.pow((pointB[0] - pointA[0]), 2) + Math.pow((pointB[1] - pointA[1]), 2));
    }

    /**
     * Remplit la liste des aéroports avec les données d'un fichier.
     *
     * @param filePath le chemin vers le fichier
     * @param airportsCatalog le catalogue des aéroports
     * @return true si l'opération est réussie, false sinon
     * @throws FileNotFoundException si le fichier est introuvable
     * @throws FileFormatException s'il y a une erreur de format dans le fichier
     */
    public static boolean fillAirportsCatalog(String filePath, AirportsCatalog airportsCatalog) throws FileNotFoundException, FileFormatException {
        List<String> possibleValues = new ArrayList<>();
        possibleValues.add("N");
        possibleValues.add("E");
        possibleValues.add("O");
        possibleValues.add("S");
        int lineCount = 1;
        try {
            FileReader file = new FileReader(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");

                if (!possibleValues.contains(values[5]) || !possibleValues.contains(values[9])) {
                    throw new NumberFormatException(); //On l'envoi directement dans le catch correspondant
                }

                airportsCatalog.addAirport(new Airport(values[0], values[1],
                        Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), values[5].charAt(0),
                        Integer.parseInt(values[6]), Integer.parseInt(values[7]), Integer.parseInt(values[8]), values[9].charAt(0)));
                lineCount++;
            }
            scanner.close();
        } catch (FileNotFoundException error) {
            throw new FileNotFoundException();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException error) {
            throw new FileFormatException(lineCount, filePath);
        }
        return true;
    }

    /**
     * Remplit la liste des vols avec les données d'un fichier.
     *
     * @param filePathAirports chemin vers le fichier d'aéroports
     * @param filePathFlights chemin vers le ficher de vols
     * @param flightsCatalog le catalogue des vols
     * @param airportsCatalog le catalogue des aéroports
     * @return true si l'opération est réussie, false sinon
     * @throws FileNotFoundException si le fichier est introuvable
     */
    public static boolean fillFlightsCatalog(String filePathAirports, String filePathFlights, FlightsCatalog flightsCatalog, AirportsCatalog airportsCatalog) throws FileNotFoundException, FileFormatException, DataMismatchException {
        int lineCount = 1;
        try {
            File file = new File(filePathFlights);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");

                flightsCatalog.addFlight(new Flight(values[0], airportsCatalog.getAirport(values[1]), airportsCatalog.getAirport(values[2]),
                        Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5])));
                lineCount++;
            }
            scanner.close();
        } catch (FileNotFoundException error) {
            throw new FileNotFoundException();
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException error) {
            throw new FileFormatException(lineCount, filePathFlights);
        } catch (NullPointerException e) {
            throw new DataMismatchException(filePathAirports, filePathFlights);
        }
        Flight.resetFlightIdIterator();
        return true;
    }

    /**
     * Crée un fichier texte contenant les données de collision du graphe des
     * vols chargé.
     *
     * @param flightCatalog le catalogue des vols
     * @return true si l'opération réussit, false sinon
     */
    public static boolean createGraphTextFile(FlightsCatalog flightCatalog) {
        List<Flight> flightsList = flightCatalog.getFlights();
        int nbNode = 0;
        HashMap<Integer, Set<Integer>> edgeMap = new HashMap<>();

        // Parcourir la liste des vols pour détecter les collisions
        for (int i = 0; i < flightsList.size(); i++) {

            Set<Integer> tempSet = new HashSet<>(); // Créer un nouveau HashSet pour chaque itération
            for (int j = i + 1; j < flightsList.size(); j++) {
                if (FlightCollisionTools.hasCollision(flightsList.get(i), flightsList.get(j))) {
                    tempSet.add(flightsList.get(j).getFlightNumber());
                }
            }
            nbNode++;
            edgeMap.put(flightsList.get(i).getFlightNumber(), tempSet);
        }

        // Écriture des données dans un fichier txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\data\\temp\\graph-testTEMP.txt"))) {
            // Écrire Kmax ou -1
            writer.write(String.valueOf(Settings.getKmax() == 0 ? -1 : Settings.getKmax()));
            writer.newLine();

            // Écrire le nombre de nœuds
            writer.write(String.valueOf(nbNode));
            writer.newLine();

            // Écrire les arêtes
            for (Map.Entry<Integer, Set<Integer>> entry : edgeMap.entrySet()) {
                Integer key = entry.getKey();
                Set<Integer> values = entry.getValue();
                if (values.isEmpty()) {
                    writer.write(String.valueOf(key));
                    writer.newLine();
                } else {
                    for (Integer value : values) {
                        writer.write(key + " " + value);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Colore les nœuds d'un graphe avec des couleurs prédéfinies.
     *
     * <p>
     * Cette méthode parcourt chaque nœud du graphe et attribue une couleur de
     * remplissage en fonction de l'attribut "color" du nœud. Les couleurs sont
     * prédéfinies dans un tableau. Si la valeur de l'attribut "color" est
     * inférieure à la longueur du tableau de couleurs, la couleur
     * correspondante est appliquée au nœud.
     *
     * @param graph le {@link Graph} contenant les nœuds à colorer
     */
    public static void colorGraph(Graph graph) {
        for (Node node : graph) {
            // Tableau de couleurs
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
            // Modification de l'attribut ui style de chaque sommet du graphe
            if ((int) node.getAttribute("color") < colors.length) {
                String color = colors[(int) node.getAttribute("color")];
                node.setAttribute("ui.style", "fill-color: " + color + ";");
            }
        }
    }

    /**
     * Affiche le graphe en utilisant l'interface utilisateur GraphStream.
     *
     * @param graph le graphe à afficher
     */
    public static void displayGraph(Graph graph) {
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing");
        graph.display();
    }

    /**
     * Modifie le niveau de zoom du graphique en réponse à l'événement de
     * déplacement de la molette de la souris.
     *
     * @param mwe l'événement de déplacement de la molette de la souris
     * @param viewPanel le panneau de vue contenant le graphique
     */
    public static void zoomGraphMouseWheelMoved(MouseWheelEvent mwe, ViewPanel viewPanel) {
        // Vérification si il y a un mouvement de la mollette
        if (Event.ALT_MASK != 0) {
            // Si mouvement roulette vers le haut
            if (mwe.getWheelRotation() > 0) {
                double newViewPercent = viewPanel.getCamera().getViewPercent() + 0.05;
                viewPanel.getCamera().setViewPercent(newViewPercent);
                // Si mouvement roulette vers le bas
            } else if (mwe.getWheelRotation() < 0) {
                double currentViewPercent = viewPanel.getCamera().getViewPercent();
                if (currentViewPercent > 0.05) {
                    viewPanel.getCamera().setViewPercent(currentViewPercent - 0.05);
                }
            }
        }
    }

    /**
     * Calcule le nombre de composantes connexes dans un graphe.
     *
     * @param graph le graphe dont on veut calculer les composantes connexes
     * @return le nombre de composantes connexes dans le graphe
     */
    public static int connectedComponent(Graph graph) {
        // Création de l'instance de l'algorithme de composante connexe
        ConnectedComponents cc = new ConnectedComponents();

        // Ajout du graphe à l'algorithme
        cc.init(graph);

        // Exécution de l'algorithme pour attribuer les composantes connexes
        cc.compute();

        return cc.getConnectedComponentsCount();
    }

    /**
     * Charge le graphe à partir du fichier source.
     *
     * @param filePath le fichier à charger
     * @return
     * @throws IOException si une erreur d'entrée/sortie se produit
     * @throws java.io.FileNotFoundException
     */
    public static Coloration fillGraph(String filePath) throws IOException {
        int lineCount = 1;
        Graph fileGraph = new MultiGraph(filePath);
        Coloration coloration = new Coloration();
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Scanner scanner = new Scanner(fileInputStream);

        // Lire le fichier ligne par ligne
        try {
            coloration.setFile(filePath);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] elements = line.split("\\s+");
                    if (elements.length > 2) {
                        throw new NumberFormatException();
                    }

                    switch (lineCount) {
                        // La première ligne contient kmax
                        case 1:
                            if (elements.length > 1) {
                                throw new NumberFormatException();
                            }
                            coloration.setKmax(Integer.parseInt(elements[0]));
                            break;
                        // La deuxième ligne contient le nombre de sommets
                        case 2:
                            if (elements.length > 1) {
                                throw new NumberFormatException();
                            }
                            coloration.setNbNode(Integer.parseInt(elements[0]));
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
            coloration.setFileGraph(fileGraph);
            return coloration;
        } catch (NumberFormatException | IndexOutOfBoundsException problem) {
            throw new FileFormatException(lineCount, filePath);
        }

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
        Coloration coloration = new Coloration();
        ColorationAlgorithm colorationAlgorithm;

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
                coloration.setFile(directoryPath + "/" + nomFichier);
                try {
                    coloration = ToolBox.fillGraph(coloration.getFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                colorationAlgorithm = new ColorationAlgorithm(coloration);
                ColorationResult res = colorationAlgorithm.minConflict();
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
                coloration.setFileGraph(new MultiGraph(coloration.getFile()));
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
}
