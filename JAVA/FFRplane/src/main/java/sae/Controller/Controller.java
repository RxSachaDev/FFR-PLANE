package sae.controller;

import sae.view.mapCustom.MapPointRender;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.graphstream.graph.Graph;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import sae.utils.*;
import sae.models.airports.*;
import sae.models.toolbox.*;
import sae.models.flights.*;
import sae.view.mapCustom.*;
import sae.view.easterGame.*;
import sae.view.jFrame.MainFrame;
import sae.controller.Interfaces.*;

/**
 * Contrôleur principal de l'application, gère les interactions entre la vue et les modèles.
 * 
 * @autor mathe
 */
public class Controller {
    private final MainFrame mainFrame;
    private final MapCustom mapCustom;
    
    private final AirportsCatalog airportsCatalog = new AirportsCatalog();
    private final FlightsCatalog flightsCatalog = new FlightsCatalog();
    
    private final FlightsCatalog refinedFlightsCatalog = new FlightsCatalog();
    
    private final Set<MapPoint> mapPointSet = new HashSet<>();
    private final Set<MapLine> mapLineSet = new HashSet<>();
    
    
    /**
     * Constructeur du contrôleur.
     * 
     * @param mainFrame La fenêtre principale de l'application.
     */
    public Controller(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.mapCustom = mainFrame.getMapCustom();
        this.mapCustom.setController(this);
    }
    
    
    public void colorMapLine(Graph graph){
        for (Flight flight :  flightsCatalog.getFlights()){
            flight.setFlightHeighLevel(graph.getNode(String.valueOf(flight.getFlightNumber())).getAttribute("color"));
        }
    }

    
    /**
     * Remplit le catalogue des aéroports à partir du fichier présent dans la classe Settings.
     */
    public void fillAirportsCatalog(){
        try {
            ToolBox.fillAirportsCatalog(Settings.getAirportsFilePath(), airportsCatalog);
        } catch (Exception e) {}
    }
    
    
    /**
     * Remplit le catalogue des vols à partir des fichiers présents dans la classe Settings.
     */
    public void fillFlightsCatalog(){
        try {
            ToolBox.fillFlightsCatalog(Settings.getAirportsFilePath(), Settings.getFlightsFilePath(), flightsCatalog, airportsCatalog);
        } catch (Exception e) {}
    }
   
    
    /**
     * Réactualise le fichier de collisions des differentes MapLines.
     * Utilisé en cas de modification de KMAX (Condition de coloration) ou de la SafetyMargin (Condition d'intersection) 
     */
    public void refreshIntersections(){
        ToolBox.createGraphTextFile(flightsCatalog);
    }
    
    
    //PARTIE RELATIVE A LA MainFrame

    
    /**
     * Initialise la MainFrame.
     */
    public void initMainFrame(){
        initMapCutom();
        initMapLineComboBoxModel();
        ToolBox.createGraphTextFile(flightsCatalog);
    }
    
    
    /**
     * Rafraîchit l'affichage de la MainFrame.
     */
    public void refreshMainFrame (){
        refreshMapCustom();
        refreshMapLineComboBoxModel();
    }
    
    
    /**
     * Initialise le modèle de la MapLineComboBox.
     */
    public void initMapLineComboBoxModel(){
        for(MapLine mapLine : mapLineSet){
            mainFrame.getMapLineComboBox().addItem(mapLine);
        }
    }
  
    
    /**
     * Rafraîchit le modèle de la MapLineComboBox.
     */
    public void refreshMapLineComboBoxModel (){
        if (mainFrame.getMapLineComboBox().getItemCount() != 0){
            mainFrame.getMapLineComboBox().removeAllItems();
        }
        initMapLineComboBoxModel();
    }
    
    
    //PARTIE RELATIVE A LA MapCustom

    
    /**
     * Initialise la MapCustom.
     */
    public void initMapCutom(){
        fillAirportsCatalog();
        fillFlightsCatalog();
        initMapPoints(Settings.isEasterEggActivated());
        initMapLines();
    }
   
    
    /**
     * Rafraîchit la MapCustom.
     */
    public void refreshMapCustom() {
        mapLineSet.clear();
        initMapLines();
    }

    
    /**
     * Initialise les points sur la carte.
     * 
     * @param isEasterEggActivated Indique si l'easter egg est activé.
     */
    public void initMapPoints (Boolean isEasterEggActivated) {
        for (Airport airport : airportsCatalog.getAirports()) {
            mapPointSet.add(new MapPoint(airport)); // Utilisation du constructeur avec Airport comme argument
        }
        WaypointPainter<MapPoint> ap = new MapPointRender();
        if(isEasterEggActivated) initEasterEgg();
        ap.setWaypoints(mapPointSet);
        mapCustom.setOverlayPainter(ap);
        for (MapPoint d : mapPointSet) {
            ModelPoint airport = d.getModelPoint();
            if (airport != null) {
                mapCustom.add(d.getButton());
            } else {
                mapCustom.add(d.getButton());
                System.err.println("Airport associated with AirportWaypoint is null.");
            }
        }
    }

    
    /**
     * Initialise les lignes sur la carte, en fontion des parametres d'affinage.
     */
    public void initMapLines() {
        int refiningColor = Settings.getRefiningColor();
        Date refiningStartHour = Settings.getRefiningStartHour();
        Date refiningEndHour = Settings.getRefiningEndHour();
        
        if(refiningColor != 0){
            for (Flight flight : flightsCatalog.getFlights()) {
                if(flight.getFlightHeighLevel() == Settings.getRefiningColor()) {
                    mapLineSet.add(new MapLine(flight, Color.BLACK));
                    refinedFlightsCatalog.addFlight(flight);
                }
            }
        }
        if(refiningStartHour != null){
            FlightsCatalog tempCatalog = flightsCatalog;
            if(!refinedFlightsCatalog.getFlights().isEmpty()) {
                tempCatalog = refinedFlightsCatalog;
                refinedFlightsCatalog.clearCatalog();
            }
            for (Flight flight : tempCatalog.getFlights()) {
                if(refiningStartHour.getHours()*60+refiningStartHour.getMinutes() <= flight.getDepartureTime() && 
                   flight.getArrivalTime() <= refiningEndHour.getHours()*60+refiningEndHour.getMinutes()){
                    mapLineSet.add(new MapLine(flight, Color.BLACK)); 
                    refinedFlightsCatalog.addFlight(flight);
                }
            }
        } else {
            for (Flight flight : flightsCatalog.getFlights()) {
                mapLineSet.add(new MapLine(flight, Color.BLACK));    
            }
        }
        
        mapCustom.repaint();
    }

    
    /**
     * Initialise l'easter egg.
     */
    public void initEasterEgg() {
        mapPointSet.add(new EasterPoint(new GeoPosition(45.7893371, 4.8808478)));
    }
 
    
    //GETTERS / SETTERS

    
    /**
     * Retourne l'ensemble des lignes sur la carte.
     * 
     * @return Ensemble des lignes sur la carte.
     */
    public Set<MapLine> getMapLineSet(){
        return mapLineSet;
    }

    
    public Set<MapPoint> getMapPointSet(){
        return mapPointSet;
    }
    
    // <*A RETIRER
    public void handleMapClick(MouseEvent e) {
        /*GeoPosition geo = mapCustom.convertPointToGeoPosition(e.getModelPoint());
        
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
        }*/
    }
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
    // *>
}
