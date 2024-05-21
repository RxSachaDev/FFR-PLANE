package intersection;

import airports.Airport;
import flights.Flight;
import toolbox.ToolBox;

public class FlightCollisionTools { 

    public static boolean hasCollision(Flight V1, Flight V2) {
        double res = 16; // (PARAMETRE MODIFIABLE)
        int intersectionCase = calIntersectionCase(V1,V2);
        switch (intersectionCase) {
            case 1:     // Cas où V1 et V2 ont les même trajectoires (directions opposées)
                if (V1.getDepartureTime() < V2.getDepartureTime())
                    res = V2.getDepartureTime() - V1.getArrivalTime();
                else
                    res = V1.getDepartureTime() - V2.getArrivalTime();
                break;
            case 2:     // Cas où V1 et V2 ont le même aeroport de départ
                res = Math.abs(V1.getDepartureTime() - V2.getDepartureTime());
                break;
            case 3:     // Cas où V1 et V2 ont le même aeroport d'arrivé
                res = Math.abs(V1.getArrivalTime() - V2.getArrivalTime());
                break;
            case 4:     // Cas où l'aeroport de départ de V1 = aeroport d'arrivé de V2
                res = Math.abs(V1.getDepartureTime() - V2.getArrivalTime());
                break;
            case 5:     // Cas où l'aeroport de départ de V2 = aeroport d'arrivé de V1
                res = Math.abs(V1.getArrivalTime() - V2.getDepartureTime());
                break;
            case 6:     // Tous les autres cas
                double[] intersectionPoint = calIntersectionPoint(V1, V2);
                if(isOnFlightSegment(intersectionPoint[0], intersectionPoint[1], V1) && isOnFlightSegment(intersectionPoint[0], intersectionPoint[1], V2)) {
                    double distancePointV1 = ToolBox.calDistance(V1.getDepartureAirport().getCoordinates(), intersectionPoint);
                    double hIntersect1 = calHourAtPoint(V1, distancePointV1);
                    double distancePointV2 = ToolBox.calDistance(V2.getDepartureAirport().getCoordinates(), intersectionPoint);
                    double hIntersect2 = calHourAtPoint(V2, distancePointV2);
                    res = Math.abs(hIntersect1 - hIntersect2);
                }
                break;
        }
        return (res < 15); // (PARAMETRE MODIFIABLE)
    }

    private static int calIntersectionCase(Flight V1, Flight V2) {    
        Airport A1 = V1.getDepartureAirport();
        Airport A2 = V1.getArrivalAirport();
        Airport A3 = V2.getDepartureAirport();
        Airport A4 = V2.getArrivalAirport();

        if (A1 == A4 && A2 == A3) {
            return 1;   // Cas où V1 et V2 ont les même trajectoires (directions opposées)
        }
        if (A1 == A3) {
            return 2;   // Cas où V1 et V2 ont le même aeroport de départ
        }
        if (A2 == A4) { 
            return 3;   // Cas où V1 et V2 ont le même aeroport d'arrivé
        }
        if (A1 == A4) { 
            return 4;   // Cas où l'aeroport de départ de V1 = aeroport d'arrivé de V2
        }
        if (A2 == A3) {
            return 5;   // Cas où l'aeroport de départ de V2 = aeroport d'arrivé de V1
        }
        return 6;       // Tous les autres cas
    }

    private static double[] calIntersectionPoint(Flight V1, Flight V2) {
        double[] point = {Double.NaN,Double.NaN};
    
        double[] cooArrivee1 = V1.getArrivalAirport().getCoordinates();
        double[] cooDepart1 = V1.getDepartureAirport().getCoordinates();
        double[] cooArrivee2 = V2.getArrivalAirport().getCoordinates();
        double[] cooDepart2 = V2.getDepartureAirport().getCoordinates();
    
        double m1 = (cooArrivee1[1] - cooDepart1[1]) / (cooArrivee1[0] - cooDepart1[0]);
        double p1 = cooArrivee1[1] - m1 * cooArrivee1[0];
    
        double m2 = (cooArrivee2[1] - cooDepart2[1]) / (cooArrivee2[0] - cooDepart2[0]);
        double p2 = cooArrivee2[1] - m2 * cooArrivee2[0];
    
        double x = -(p1 - p2) / (m1 - m2);
        double y = m1 * x + p1;
    
        if (isOnFlightSegment(x, y, V1) && isOnFlightSegment(x, y, V2)) {
            point[0] = x;
            point[1] = y;
        }
    
        return point;
    }

    private static boolean isOnFlightSegment(double x, double y, Flight vol) {
        double[] cooDepart = vol.getDepartureAirport().getCoordinates();
        double[] cooArrivee = vol.getArrivalAirport().getCoordinates();
    
        double maxX = Math.max(cooDepart[0], cooArrivee[0]);
        double minX = Math.min(cooDepart[0], cooArrivee[0]);
        double maxY = Math.max(cooDepart[1], cooArrivee[1]);
        double minY = Math.min(cooDepart[1], cooArrivee[1]);
    
        return (minX <= x && x <= maxX && minY <= y && y <= maxY);
    }

    private static double calHourAtPoint(Flight vol, double distancePoint) {
        double dureePoint = (distancePoint * vol.getDuration()) / vol.getFlightDistance();
        return vol.getDepartureTime() + dureePoint;
    }
    

/**
 * PARTIE QUI FAIT DIFFERER LES RESULTATS DES vol-test4.csv ET vol-test8.csv
    public static boolean hasCollision(Flight V1, Flight V2) {
        Airport A1 = V1.getDepartureAirport();
        Airport A2 = V1.getArrivalAirport();
        Airport A3 = V2.getDepartureAirport();
        Airport A4 = V2.getArrivalAirport();     
        
        if (A1 == A4 && A2 == A3){ // TAG 3
            if (Math.abs(V1.getDepartureTime() - V2.getDepartureTime()) < 15) return true;
            
            Flight firstVolToGo = V1;
            Flight secondVolToGo = V2;
            if(firstVolToGo.getDepartureTime() > secondVolToGo.getDepartureTime()) {
                firstVolToGo = V2;
                secondVolToGo = V1;
            }
            
            if (secondVolToGo.getDepartureTime() - firstVolToGo.getArrivalTime() < 15) return true;

            return false;     
        } 
        if (A1 == A3 && (Math.abs(V1.getDepartureTime() - V2.getDepartureTime()) < 15)) { // TAG 1
            return true;
        } 
        if (A2 == A4 && (Math.abs(V1.getArrivalTime() - V2.getArrivalTime()) < 15)) { // TAG 2
            return true;
        } 
        if (A1 == A4 && (Math.abs(V1.getDepartureTime() - V2.getArrivalTime()) < 15)) { // TAG 4
            return true;
        } 
        if (A2 == A3 && (Math.abs(V1.getArrivalTime() - V2.getDepartureTime()) < 15)) { // TAG 5
            return true;
        } else { // TAG 6
            Boolean bool = false;
            double[] point = calIntersectionPoint(V1, V2);
            if(point[0] != 7000){ // Si un point d'intersection existe
                double distancePoint_volA = ToolBox.calDistance(V1.getDepartureAirport().getCoordinates(), point);
                double heurePoint_volA = calHourAtPoint(V1, distancePoint_volA);
                double distancePoint_volB = ToolBox.calDistance(V2.getDepartureAirport().getCoordinates(), point);
                double heurePoint_volB = calHourAtPoint(V2, distancePoint_volB);

                if(Math.abs(heurePoint_volA - heurePoint_volB) < 15) bool = true;
            }
            return bool;
        }
    }
*/
}