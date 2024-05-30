package sae.Models.intersection;

import org.jxmapviewer.viewer.GeoPosition;
import sae.Models.airports.*;
import sae.Models.flights.*;
import sae.Models.toolbox.*;

/**
 * The FlightCollisionTools class provides methods to detect potential collisions between flights.
 */
public class FlightCollisionTools {
    /**
     * Determines if there is a collision between two flights.
     *
     * @param flight1 the first flight
     * @param flight2 the second flight
     * @return true if there is a collision, false otherwise
     */
    public static boolean hasCollision(Flight flight1, Flight flight2) {
        double res = 16; // (PARAMETRE MODIFIABLE)
        int intersectionCase = calculateIntersectionCase(flight1, flight2);
        switch (intersectionCase) {
            case 1: // Case where flight1 and flight2 have the same trajectories (opposite directions)
                if (flight1.getDepartureTime() < flight2.getDepartureTime())
                    res = flight2.getDepartureTime() - flight1.getArrivalTime();
                else
                    res = flight1.getDepartureTime() - flight2.getArrivalTime();
                break;
            case 2: // Case where flight1 and flight2 have the same departure airport
                res = Math.abs(flight1.getDepartureTime() - flight2.getDepartureTime());
                break;
            case 3: // Case where flight1 and flight2 have the same arrival airport
                res = Math.abs(flight1.getArrivalTime() - flight2.getArrivalTime());
                break;
            case 4: // Case where the departure airport of flight1 = arrival airport of flight2
                res = Math.abs(flight1.getDepartureTime() - flight2.getArrivalTime());
                break;
            case 5: // Case where the departure airport of flight2 = arrival airport of flight1
                res = Math.abs(flight1.getArrivalTime() - flight2.getDepartureTime());
                break;
            case 6: // All other cases
                GeoPosition intersectionPoint = calculateIntersectionPoint(flight1, flight2);
                if (isOnFlightSegment(intersectionPoint, flight1) && isOnFlightSegment(intersectionPoint, flight2)) {
                    double distancePointFlight1 = ToolBox.calDistance(flight1.getDepartureAirport().getCoordinates(), intersectionPoint);
                    double hourAtPoint1 = calculateHourAtPoint(flight1, distancePointFlight1);
                    double distancePointFlight2 = ToolBox.calDistance(flight2.getDepartureAirport().getCoordinates(), intersectionPoint);
                    double hourAtPoint2 = calculateHourAtPoint(flight2, distancePointFlight2);
                    res = Math.abs(hourAtPoint1 - hourAtPoint2);
                }
                break;
        }
        return (res < 15); // (PARAMETRE MODIFIABLE)
    }


    /**
     * Calculates the intersection case between two flights.
     *
     * @param flight1 the first flight
     * @param flight2 the second flight
     * @return an integer representing the intersection case
     */
    private static int calculateIntersectionCase(Flight flight1, Flight flight2) {
        Airport departureAirport1 = flight1.getDepartureAirport();
        Airport arrivalAirport1 = flight1.getArrivalAirport();
        Airport departureAirport2 = flight2.getDepartureAirport();
        Airport arrivalAirport2 = flight2.getArrivalAirport();

        if (departureAirport1 == arrivalAirport2 && arrivalAirport1 == departureAirport2) {
            return 1; // Case where flight1 and flight2 have the same trajectories (opposite directions)
        }
        if (departureAirport1 == departureAirport2) {
            return 2; // Case where flight1 and flight2 have the same departure airport
        }
        if (arrivalAirport1 == arrivalAirport2) {
            return 3; // Case where flight1 and flight2 have the same arrival airport
        }
        if (departureAirport1 == arrivalAirport2) {
            return 4; // Case where the departure airport of flight1 = arrival airport of flight2
        }
        if (arrivalAirport1 == departureAirport2) {
            return 5; // Case where the departure airport of flight2 = arrival airport of flight1
        }
        return 6; // All other cases
    }


    /**
     * Calculates the intersection point between two flights.
     *
     * @param flight1 the first flight
     * @param flight2 the second flight
     * @return an array containing the coordinates of the intersection point
     */
    private static GeoPosition calculateIntersectionPoint(Flight flight1, Flight flight2) {
        double[] point = {Double.NaN, Double.NaN};

        GeoPosition arrivalCoordinates1 = flight1.getArrivalAirport().getCoordinates();
        GeoPosition departureCoordinates1 = flight1.getDepartureAirport().getCoordinates();
        GeoPosition arrivalCoordinates2 = flight2.getArrivalAirport().getCoordinates();
        GeoPosition departureCoordinates2 = flight2.getDepartureAirport().getCoordinates();

        double m1 = (arrivalCoordinates1.getLongitude() - departureCoordinates1.getLongitude()) / (arrivalCoordinates1.getLatitude() - departureCoordinates1.getLatitude());
        double p1 = arrivalCoordinates1.getLongitude() - m1 * arrivalCoordinates1.getLatitude();

        double m2 = (arrivalCoordinates2.getLongitude() - departureCoordinates2.getLongitude()) / (arrivalCoordinates2.getLatitude() - departureCoordinates2.getLatitude());
        double p2 = arrivalCoordinates2.getLongitude() - m2 * arrivalCoordinates2.getLatitude();

        double x = -(p1 - p2) / (m1 - m2);
        double y = m1 * x + p1;

        if (isOnFlightSegment(new GeoPosition(x,y), flight1) && isOnFlightSegment(new GeoPosition(x,y), flight2)) {
            point[0] = x;
            point[1] = y;
        }

        return new GeoPosition(point);
    }


    /**
     * Checks if a point is on the flight segment.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param flight the flight
     * @return true if the point is on the flight segment, false otherwise
     */
    private static boolean isOnFlightSegment(GeoPosition point, Flight flight) {
        GeoPosition departureCoordinates = flight.getDepartureAirport().getCoordinates();
        GeoPosition arrivalCoordinates = flight.getArrivalAirport().getCoordinates();

        double maxX = Math.max(departureCoordinates.getLatitude(), arrivalCoordinates.getLatitude());
        double minX = Math.min(departureCoordinates.getLatitude(), arrivalCoordinates.getLatitude());
        double maxY = Math.max(departureCoordinates.getLongitude(), arrivalCoordinates.getLongitude());
        double minY = Math.min(departureCoordinates.getLongitude(), arrivalCoordinates.getLongitude());

        return (minX <= point.getLatitude() && point.getLatitude() <= maxX 
             && minY <= point.getLongitude() && point.getLongitude() <= maxY);
    }


    /**
     * Calculates the hour at a given point on the flight segment.
     *
     * @param flight the flight
     * @param distanceToPoint the distance to the point
     * @return the hour at the given point
     */
    private static double calculateHourAtPoint(Flight flight, double distanceToPoint) {
        double durationToPoint = (distanceToPoint * flight.getDuration()) / flight.getFlightDistance();
        return flight.getDepartureTime() + durationToPoint;
    }
}
           
