package sae.models.toolbox;

import java.io.BufferedWriter;
import java.io.File;
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
import sae.exceptions.DataMismatchException;
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
     * Remplit la liste des aéroports avec les données d'un fichier.
     *
     * @param filePath le chemin vers le fichier
     * @param airportsCatalog le catalogue des aéroports
     * @return true si l'opération est réussie, false sinon
     * @throws FileNotFoundException si le fichier est introuvable
     * @throws FileFormatException s'il y a une erreur de format dans le fichier
     */
    public static boolean fillAirportList(String filePath, AirportCatalog airportsCatalog) throws FileNotFoundException, FileFormatException {
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
     * @param filePath le chemin vers le fichier
     * @param flightsCatalog le catalogue des vols
     * @param airportsCatalog le catalogue des aéroports
     * @return true si l'opération est réussie, false sinon
     * @throws FileNotFoundException si le fichier est introuvable
     */
    public static boolean fillFlightList(String filePathAirports, String filePathFlights, FlightCatalog flightsCatalog, AirportCatalog airportsCatalog) throws FileNotFoundException {
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
        } catch (NullPointerException e){
            throw new DataMismatchException(filePathAirports,filePathFlights);
        }
        return true;
    }
    
    
    /**
    * Crée un fichier texte contenant les données de collision du graphe des vols chargé.
    *
    * @param flightCatalog le catalogue des vols
    * @return true si l'opération réussit, false sinon
    */
    public static boolean createGraphTextFile(FlightCatalog flightCatalog) {
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
                if(values.isEmpty()){
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
}
