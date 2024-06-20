package sae.controller;

import sae.controller.Interfaces.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
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
public class Controller {
    private MapCustom mapCustom;
    
    private final AirportCatalog airportsCatalog = new AirportCatalog();
    private final FlightCatalog flightsCatalog = new FlightCatalog();
    
    private final Set<MapPoint> mapPointSet = new HashSet<>();
    private final Set<MapLine> mapLineSet = new HashSet<>();
    
    private MapLine lastSelectedLine = null;
    
    
    public Controller(MapCustom mapCustom) {
        this.mapCustom = mapCustom;
        this.mapCustom.setController(this);
    }

    
    public void refreshMainFrame (){
        refreshMapCustom();
    }
    
    public void initMapCustom(){
        fillAirportsCatalog();
        fillFlightsCatalog();
        initMapPoints(Settings.isEasterEggActivated());
        initMapLines();
    }
    
    
    public void initMapPoints (Boolean isEasterEggActivated) {
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
    
    
    /**
     * Supprime les marqueurs des aéroports de la carte.
     */
    public void refreshMapCustom() {
        mapLineSet.clear();
        initMapLines();
    }
    
    
    public void fillAirportsCatalog(){
        try {
            ToolBox.fillAirportsCatalog(Settings.getAirportsFilePath(),airportsCatalog);
        } catch (Exception e) {}
    }
    
    
    public void fillFlightsCatalog(){
        try {
            ToolBox.fillFlightsCatalog(Settings.getAirportsFilePath(),Settings.getFlightsFilePath(),flightsCatalog,airportsCatalog);
        } catch (Exception e) {}
    }
    
    
    public void initMapLines() {
        FlightCatalog tempCatalog = new FlightCatalog();
        if(Settings.getRefiningColor() == 0) {
            for (Flight flight : flightsCatalog.getFlights()) {
                mapLineSet.add(new MapLine(flight,Color.BLACK,mapCustom)); // Utilisation du constructeur avec Airport comme argument
                tempCatalog.addFlight(flight);
            }
        } 
        if(tempCatalog.getFlights().isEmpty()) { //Afficher les vols par niveau de hauteur
            for (Flight flight : flightsCatalog.getFlights()) {
                if(flight.getFlightHeighLevel() == Settings.getRefiningColor()) {
                    mapLineSet.add(new MapLine(flight,Color.BLACK,mapCustom)); // Utilisation du constructeur avec Airport comme argument
                    tempCatalog.addFlight(flight);
                }
            }
        }
        mapCustom.repaint();
        ToolBox.createGraphTextFile(tempCatalog);
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
        //Point1
        double x1 = mapLine.getPoint1().getLatitude();
        double y1 = mapLine.getPoint1().getLongitude();
        
        //Point2
        double x2 = mapLine.getPoint2().getLatitude();
        double y2 = mapLine.getPoint2().getLongitude();
        
        //Point cliqué
        double x = point.getLatitude(); 
        double y = point.getLongitude();
        
        //Equation de la droite Point1,Point2
        double a = (y2 - y1) / (x2 - x1);
        double b = y2 - a * x2;

        double distance = Math.abs(-a*x + y - b)/Math.sqrt(Math.pow(a, 2)+1);
        return distance;
    }
    
    public void colorMapLine(Graph graph){
        for (Flight flight :  flightsCatalog.getFlights()){
            flight.setFlightHeighLevel(graph.getNode(String.valueOf(flight.getFlightNumber())).getAttribute("color"));
        }
    }
}
