package sae.models.toolbox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import sae.models.airports.Airport;
import sae.models.airports.AirportCatalog;
import sae.exceptions.FileFormatException;
import sae.models.flights.Flight;
import sae.models.flights.FlightCatalog;
import sae.models.intersection.FlightCollisionTools;
import sae.utils.Settings;

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
     * Remplit la liste des vols avec les données d'un fichier.
     *
     * @param filePath le chemin vers le fichier
     * @param flightsCatalog le catalogue des vols
     * @param airportsCatalog le catalogue des aéroports
     * @return true si l'opération est réussie, false sinon
     * @throws FileNotFoundException si le fichier est introuvable
     */
    public static boolean fillFlightList(String filePath, FlightCatalog flightsCatalog, AirportCatalog airportsCatalog) throws FileNotFoundException {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");
                flightsCatalog.addFlight(new Flight(values[0], airportsCatalog.getAirport(values[1]), airportsCatalog.getAirport(values[2]),
                        Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5])));
            }
            scanner.close();
        } catch (FileNotFoundException error) {
            throw new FileNotFoundException();
        }
        return true;
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
    public static boolean fillAirportList(String filePath, AirportCatalog airportsCatalog) throws FileNotFoundException, FileFormatException {
        int lineCount = 1;
        try {
            FileReader file = new FileReader(filePath);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");
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
     * Crée un fichier texte contenant les données de collision du graphe des
     * vols chargé.
     *
     * @param flightCatalog le catalogue des vols
     * @return true si l'opération réussit, false sinon
     */
    public static boolean createGraphTextFile(FlightCatalog flightCatalog) {
        List<Flight> flightsList = flightCatalog.getFlights();
        boolean canAddNode = false;
        int nbNode = 0;
        HashMap<Integer, Set<Integer>> edgeMap = new HashMap<>();

        // Parcourir la liste des vols pour détecter les collisions
        for (int i = 0; i < flightsList.size() - 1; i++) {
            canAddNode = false;
            Set<Integer> tempSet = new HashSet<>(); // Créer un nouveau HashSet pour chaque itération
            for (int j = i + 1; j < flightsList.size(); j++) {
                if (FlightCollisionTools.hasCollision(flightsList.get(i), flightsList.get(j))) {
                    canAddNode = true;
                    tempSet.add(flightsList.get(j).getFlightNumber());
                }
            }
            if (canAddNode) {
                nbNode++;
            }
            if (!tempSet.isEmpty()) {
                edgeMap.put(flightsList.get(i).getFlightNumber(), tempSet);
            }
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
                for (Integer value : values) {
                    writer.write(key + " " + value);
                    writer.newLine();
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
    public void colorGraph(Graph graph) {
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
    public void displayGraph(Graph graph) {
        System.setProperty("org.graphstream.ui", "org.graphstream.ui.swing");
        graph.display();
    }

}
