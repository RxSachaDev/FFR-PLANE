package sae.controller;

import sae.controller.Interfaces.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.HashSet;
import java.util.Set;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import sae.utils.*;
import sae.models.airports.*;
import sae.models.toolbox.*;
import sae.models.flights.*;
import sae.view.airport.*;
import sae.view.mapCustom.*;
import sae.view.easterGame.*;

/**
 * 
 * @author mathe
 */
public class MapController {
    private MapCustom mapCustom;
    
    private final AirportCatalog airportsCatalog = new AirportCatalog();
    private final FlightCatalog flightsCatalog = new FlightCatalog();
    
    private final Set<MapPoint> mapPointSet = new HashSet<>();
    private final Set<MapLine> mapLineSet = new HashSet<>();
    
    private MapLine lastSelectedLine = null;
    
    
    public MapController(MapCustom mapCustom) {
        this.mapCustom = mapCustom;
    }

    
    public void initMapPoints (Boolean isEasterEggActivated) {
        try {
            ToolBox.fillAirportList(Settings.getAirportsFilePath(),airportsCatalog);
        } catch (Exception e) {
        }
        
        for (Airport airport : airportsCatalog.getAirports()) {
            mapPointSet.add(new MapPoint(airport)); // Utilisation du constructeur avec Airport comme argument
        }
        WaypointPainter<MapPoint> ap = new AirportpointRender();
        if(isEasterEggActivated) initEasterEgg();
        ap.setWaypoints(mapPointSet);
        mapCustom.setOverlayPainter(ap);
        for (MapPoint d : mapPointSet) {
            ModelPoint airport = d.getPoint();
            if (airport != null) {
                mapCustom.add(d.getButton());
            } else {
                mapCustom.add(d.getButton());
                System.err.println("Airport associated with AirportWaypoint is null.");
            }
        }
    }
    
    
    public void refreshMap (){
        
    }
    
    /**
     * Supprime les marqueurs des aéroports de la carte.
     */
    public void removeAirports() {
        for (MapPoint d : mapPointSet) {
            mapCustom.remove(d.getButton());
        }
        mapPointSet.clear();
        initMapPoints(true);
    }
    
    
    public void initMapLines() {
        try {
            ToolBox.fillFlightList(Settings.getFlightsFilePath(),flightsCatalog,airportsCatalog);
        } catch (Exception e) {
        }
        for (Flight flight : flightsCatalog.getFlights()) {
            System.out.println(flight);
            mapLineSet.add(new MapLine(flight,Color.BLACK,mapCustom)); // Utilisation du constructeur avec Airport comme argument
        }
        mapCustom.repaint();
        ToolBox.createGraphTextFile(flightsCatalog);
    }

    
    public void initEasterEgg() {
        mapPointSet.add(new EasterPoint(new GeoPosition(45.7893371, 4.8808478)));
    }
    
    
    public Set<MapLine> getMapLineSet(){
        return mapLineSet;
    }
    
    /**
     * Gère les clics sur la carte. Cette méthode est appelée lorsqu'un utilisateur clique sur la carte.
     * Elle convertit le point cliqué en position géographique, trouve la ligne de vol la plus proche
     * et met à jour sa couleur si elle est suffisamment proche.
     *
     * @param e l'événement de clic de la souris
     */
    public void handleMapClick(MouseEvent e) {
        GeoPosition geo = mapCustom.convertPointToGeoPosition(e.getPoint());
        
        System.out.println(geo);
        if (geo != null) {
            MapLine closestLine = null;
            double closestDistance = Double.MAX_VALUE;
            for (MapLine flightLine : mapLineSet) {
                
                double distance = distToMapLine(flightLine,geo);
                System.out.println(flightLine.getModelLine().getPoint1().getLatitude()+" -- "+distance);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestLine = flightLine;
                }
                
            }
            if (closestLine != null && closestDistance <= 0.1) {  
                closestLine.setColor(Color.ORANGE);
                if (lastSelectedLine != null && lastSelectedLine != closestLine) {
                    lastSelectedLine.setColor(Color.BLACK);
                }
                lastSelectedLine = closestLine;
            }
            mapCustom.repaint();
        }
    }
    
    
    /**
     * Calcule la distance entre un point géographique et une ligne de carte. 
     * Cette méthode utilise une formule géométrique pour déterminer la distance perpendiculaire
     * du point à la ligne.
     *
     * @param mapLine la ligne de carte
     * @param point le point géographique
     * @return la distance entre le point et la ligne de carte
     */
    public static double distToMapLine(MapLine mapLine,GeoPosition point) {
        GeoPosition point1 = mapLine.getPoint1();
        GeoPosition point2 = mapLine.getPoint2();
        
        double x = point.getLatitude();
        double y = point.getLongitude();
        

        double a = (point2.getLongitude()- point1.getLongitude()) / (point2.getLatitude()- point1.getLatitude());
        double b = point2.getLongitude()- a * point2.getLatitude();

        double distance = Math.abs(-a*x + y - b)/Math.sqrt(Math.pow(a, 2)+1);
        return distance;
    }
    
    /*public double distToMapLine(MapLine mapLine,GeoPosition point) {
        double[] point1 = calCartesianCoordinates(mapLine.getPoint1());
        double[] point2 = calCartesianCoordinates(mapLine.getPoint2());
        double[] p = calCartesianCoordinates(point);
        

        double a = (point2[1] - point1[1]) / (point2[0] - point1[0]);
        double b = point2[1]- a * point2[0];

        double distance = Math.abs(-a * p[0] + p[1] - b)/Math.sqrt(Math.pow(a, 2)+1);
        return distance;
    }

    private double[] calCartesianCoordinates(GeoPosition geoPosition) { 
        int R = 6371; // Rayon de la Terre (km) 
        double[] result = new double[2];
        result[0] = R * cos(toRadians(geoPosition.getLatitude())) * sin(toRadians(geoPosition.getLongitude()));
        result[1] = R * cos(toRadians(geoPosition.getLatitude())) * cos(toRadians(geoPosition.getLongitude()));
        return result;
    }*/
}
