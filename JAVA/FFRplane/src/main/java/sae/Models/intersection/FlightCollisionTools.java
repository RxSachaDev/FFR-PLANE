package sae.models.intersection;

import java.io.FileNotFoundException;
import java.util.List;
import sae.models.airports.Airport;
import sae.models.airports.AirportsCatalog;
import sae.models.flights.Flight;
import sae.models.flights.FlightsCatalog;
import sae.models.toolbox.ToolBox;
import sae.utils.Settings;

/**
 * La classe FlightCollisionTools fournit des méthodes pour détecter les collisions potentielles entre les vols.
 * 
 * @author mathe
 */
public class FlightCollisionTools {

    /**
     * Détermine s'il y a une collision entre deux vols.
     *
     * @param flight1 le premier vol
     * @param flight2 le deuxième vol
     * @return true s'il y a une collision, false sinon
     */
    public static boolean hasCollision(Flight flight1, Flight flight2) {
        double res = Settings.getSafetyMargin()+1; 
        int intersectionCase = calculateIntersectionCase(flight1, flight2);
        switch (intersectionCase) {
            case 1: // Cas où flight1 et flight2 ont les mêmes trajectoires (directions opposées)
                if (flight1.getDepartureTime() < flight2.getDepartureTime())
                    res = flight2.getDepartureTime() - flight1.getArrivalTime();
                else
                    res = flight1.getDepartureTime() - flight2.getArrivalTime();
                break;
            case 2: // Cas où flight1 et flight2 ont le même aéroport de départ
                res = Math.abs(flight1.getDepartureTime() - flight2.getDepartureTime());
                break;
            case 3: // Cas où flight1 et flight2 ont le même aéroport d'arrivée
                res = Math.abs(flight1.getArrivalTime() - flight2.getArrivalTime());
                break;
            case 4: // Cas où l'aéroport de départ de flight1 = l'aéroport d'arrivée de flight2
                res = Math.abs(flight1.getDepartureTime() - flight2.getArrivalTime());
                break;
            case 5: // Cas où l'aéroport de départ de flight2 = l'aéroport d'arrivée de flight1
                res = Math.abs(flight1.getArrivalTime() - flight2.getDepartureTime());
                break;
            case 6: // Tous les autres cas
                double[] intersectionPoint = calculateIntersectionPoint(flight1, flight2);
                if (isOnFlightSegment(intersectionPoint[0], intersectionPoint[1], flight1) && isOnFlightSegment(intersectionPoint[0], intersectionPoint[1], flight2)) {
                    double distancePointFlight1 = ToolBox.calDistance(flight1.getDepartureAirport().getCoordinates(), intersectionPoint);
                    double hourAtPoint1 = calculateHourAtPoint(flight1, distancePointFlight1);
                    double distancePointFlight2 = ToolBox.calDistance(flight2.getDepartureAirport().getCoordinates(), intersectionPoint);
                    double hourAtPoint2 = calculateHourAtPoint(flight2, distancePointFlight2);
                    res = Math.abs(hourAtPoint1 - hourAtPoint2);
                }
                break;
        }
        return (res < Settings.getSafetyMargin());
    }


    /**
     * Calcule le cas d'intersection entre deux vols.
     *
     * @param flight1 le premier vol
     * @param flight2 le deuxième vol
     * @return un entier représentant le cas d'intersection
     */
    private static int calculateIntersectionCase(Flight flight1, Flight flight2) {
        Airport departureAirport1 = flight1.getDepartureAirport();
        Airport arrivalAirport1 = flight1.getArrivalAirport();
        Airport departureAirport2 = flight2.getDepartureAirport();
        Airport arrivalAirport2 = flight2.getArrivalAirport();

        if (departureAirport1 == arrivalAirport2 && arrivalAirport1 == departureAirport2) {
            return 1; // Cas où flight1 et flight2 ont les mêmes trajectoires (directions opposées)
        }
        if (departureAirport1 == departureAirport2) {
            return 2; // Cas où flight1 et flight2 ont le même aéroport de départ
        }
        if (arrivalAirport1 == arrivalAirport2) {
            return 3; // Cas où flight1 et flight2 ont le même aéroport d'arrivée
        }
        if (departureAirport1 == arrivalAirport2) {
            return 4; // Cas où l'aéroport de départ de flight1 = l'aéroport d'arrivée de flight2
        }
        if (arrivalAirport1 == departureAirport2) {
            return 5; // Cas où l'aéroport de départ de flight2 = l'aéroport d'arrivée de flight1
        }
        return 6; // Tous les autres cas
    }


    /**
     * Calcule le point d'intersection entre deux vols.
     *
     * @param flight1 le premier vol
     * @param flight2 le deuxième vol
     * @return un tableau contenant les coordonnées du point d'intersection
     */
    private static double[] calculateIntersectionPoint(Flight flight1, Flight flight2) {
        double[] point = {Double.NaN, Double.NaN};

        double[] arrivalCoordinates1 = flight1.getArrivalAirport().getCoordinates();
        double[] departureCoordinates1 = flight1.getDepartureAirport().getCoordinates();
        double[] arrivalCoordinates2 = flight2.getArrivalAirport().getCoordinates();
        double[] departureCoordinates2 = flight2.getDepartureAirport().getCoordinates();

        double m1 = (arrivalCoordinates1[1] - departureCoordinates1[1]) / (arrivalCoordinates1[0] - departureCoordinates1[0]);
        double p1 = arrivalCoordinates1[1] - m1 * arrivalCoordinates1[0];

        double m2 = (arrivalCoordinates2[1] - departureCoordinates2[1]) / (arrivalCoordinates2[0] - departureCoordinates2[0]);
        double p2 = arrivalCoordinates2[1] - m2 * arrivalCoordinates2[0];

        double x = -(p2 - p1) / (m2 - m1);
        double y = m1 * x + p1;

        if (isOnFlightSegment(x, y, flight1) && isOnFlightSegment(x, y, flight2)) {
            point[0] = x;
            point[1] = y;
        }

        return point;
    }


    /**
     * Vérifie si un point est sur le segment de vol.
     *
     * @param x la coordonnée x du point
     * @param y la coordonnée y du point
     * @param flight le vol
     * @return true si le point est sur le segment de vol, false sinon
     */
    private static boolean isOnFlightSegment(double x, double y, Flight flight) {
        double[] departureCoordinates = flight.getDepartureAirport().getCoordinates();
        double[] arrivalCoordinates = flight.getArrivalAirport().getCoordinates();

        double maxX = Math.max(departureCoordinates[0], arrivalCoordinates[0]);
        double minX = Math.min(departureCoordinates[0], arrivalCoordinates[0]);
        double maxY = Math.max(departureCoordinates[1], arrivalCoordinates[1]);
        double minY = Math.min(departureCoordinates[1], arrivalCoordinates[1]);

        return (minX <= x && x <= maxX && minY <= y && y <= maxY);
    }


    /**
     * Calcule l'heure à un point donné sur le segment de vol.
     *
     * @param flight le vol
     * @param distanceToPoint la distance jusqu'au point
     * @return l'heure au point donné
     */
    private static double calculateHourAtPoint(Flight flight, double distanceToPoint) {
        double durationToPoint = (distanceToPoint * flight.getDuration()) / flight.getFlightDistance();
        return flight.getDepartureTime() + durationToPoint;
    }
    
    
    /**
     * Calcule le nombre de collisions potentielles entre les vols dans les fichiers spécifiés.
     * <p>
     * Cette méthode charge les catalogues d'aéroports et de vols à partir des fichiers spécifiés,
     * et vérifie les collisions entre les vols.
     * 
     * @param airportsFilePath  le chemin du fichier contenant les informations sur les aéroports
     * @param flightsFilePath   le chemin du fichier contenant les informations sur les vols
     * @return le nombre de collisions potentielles entre les vols
     * @throws Exception si une erreur survient lors du chargement des fichiers ou du traitement des données
     */
    public static int getNumberOfCollisions(String airportsFilePath, String flightsFilePath) throws Exception{
        AirportsCatalog airportsCatalog = new AirportsCatalog();
        FlightsCatalog flightsCatalog = new FlightsCatalog();
        
        //Génére des exceptions si les fichiers n'existent pas
        ToolBox.fillAirportsCatalog(airportsFilePath, airportsCatalog);
        ToolBox.fillFlightsCatalog(flightsFilePath, flightsFilePath, flightsCatalog, airportsCatalog);
         
        int count = 0;
        List<Flight> flightsList = flightsCatalog.getFlights();
        for(int i = 0 ; i < flightsList.size() ; i++) {
            for(int j = i+1 ; j < flightsList.size() ; j++){
                if(hasCollision(flightsList.get(i), flightsList.get(j))) count++;
            }
        }
        return count;
    }
}
    
