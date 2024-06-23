package sae.controller;

import sae.view.mapCustom.MapPointPainter;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JComboBox;
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
    
    private final Set<MapPoint> mapPointSet = new TreeSet<>(); //TreeSet, choix pratique pour afficher les infos de manière trié
    private final Set<MapLine> mapLineSet = new TreeSet<>();
    
    
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
        
        MapLine selectedMapLine = (MapLine)mainFrame.getMapLineComboBox().getSelectedItem();
        if(selectedMapLine == null) mainFrame.getTextAreaInfosSelect().setText("");
        else {
            mainFrame.getTextAreaInfosSelect().setText(selectedMapLine.toStringModelLine());
            selectedMapLine.setColor(Color.ORANGE);
        }
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
        Set<MapPoint> mapPointSetTemp = new HashSet<>();
        
        for (Airport airport : airportsCatalog.getAirports()) {
            mapPointSet.add(new MapPoint(airport));
            mapPointSetTemp.add(new MapPoint(airport));
        }
        
        WaypointPainter<MapPoint> mpPainter = new MapPointPainter();
        if(isEasterEggActivated) {
            initEasterEgg(mapPointSetTemp);
        }
        mpPainter.setWaypoints(mapPointSetTemp);
        mapCustom.setOverlayPainter(mpPainter);
        for (MapPoint mapPoint : mapPointSetTemp) {
            mapCustom.add(mapPoint.getButton());
        }
    }
    
    
    /**
     * Initialise l'easter egg.
     * @param mapPointSetTemp Le set dans lequel ajouter le MapPoint EasterEgg
     */
    public void initEasterEgg(Set<MapPoint> mapPointSetTemp) {
        mapPointSetTemp.add(new EasterPoint(new GeoPosition(45.7893371, 4.8808478)));
    }
 
    
    /**
     * Initialise les lignes sur la carte en fonction des paramètres d'affinage.
     * Cette méthode ajuste les lignes affichées sur la carte en fonction des paramètres définis dans les Settings,
     * tels que la couleur d'affinage, la plage horaire d'affinage et le code d'aéroport d'affinage.
     * Si aucun paramètre d'affinage n'est défini, toutes les lignes de vol sont affichées.
     */
    public void initMapLines() {
        refinedFlightsCatalog.clearCatalog();
        
        // Récupération des paramètres d'affinage depuis les Settings
        int refiningColor = Settings.getRefiningColor();
        Date refiningStartTimeRange = Settings.getRefiningStartTimeRange();
        Date refiningEndTimeRange = Settings.getRefiningEndTimeRange();
        String refiningAirportCode = Settings.getRefiningAirportCode();
        
        // Affichage des lignes de vol en fonction de la couleur d'affinage
        if(refiningColor != 0){
            for (Flight flight : flightsCatalog.getFlights()) {
                if(flight.getFlightHeighLevel() == Settings.getRefiningColor()) {
                    mapLineSet.add(new MapLine(flight, Color.BLACK));
                    refinedFlightsCatalog.addFlight(flight);
                }
            }
        }
        
        // Affichage des lignes de vol en fonction de la plage horaire d'affinage
        if(refiningStartTimeRange != null){
            mapLineSet.clear();
            FlightsCatalog tempCatalog = defineTempCatalog();
            for (Flight flight : tempCatalog.getFlights()) {
                if(flight.isFlightWithinTimeRange(refiningStartTimeRange,refiningEndTimeRange)){
                    mapLineSet.add(new MapLine(flight, Color.BLACK)); 
                    refinedFlightsCatalog.addFlight(flight);
                }
            }
        } 
        
        // Affichage des lignes de vol en fonction du code d'aéroport d'affinage
        if(refiningAirportCode != null){
            mapLineSet.clear();
            FlightsCatalog tempCatalog = defineTempCatalog();
            for (Flight flight : tempCatalog.getFlights()) {
                if(flight.isLinkedToAirport(refiningAirportCode)){
                    mapLineSet.add(new MapLine(flight, Color.BLACK)); 
                    refinedFlightsCatalog.addFlight(flight);
                }
            }
        }
        
        // Affichage de toutes les lignes de vol si aucun paramètre d'affinage n'est défini
        if (Settings.noSettingsSet()) {
            for (Flight flight : flightsCatalog.getFlights()) {
                mapLineSet.add(new MapLine(flight, Color.BLACK));    
            }
        }
        mapCustom.repaint();
    }
    
    
    /**
     * Méthode utilisé par initMapLines, elle sert dans l'affinage des vols à afficher.
     * Définit un catalogue temporaire de vols en fonction du catalogue de vols affinés actuel.
     * Si le catalogue de vols affinés n'est pas vide, les vols sont copiés dans le catalogue temporaire,
     * puis le catalogue de vols affinés est effacé. Si le catalogue de vols affinés est vide,
     * le catalogue temporaire est initialisé avec le catalogue principal de vols.
     *
     * @return Le catalogue temporaire de vols défini.
     */
    private FlightsCatalog defineTempCatalog(){
        FlightsCatalog tempCatalog = new FlightsCatalog();
        if(!refinedFlightsCatalog.getFlights().isEmpty()) {
            for(Flight flight : refinedFlightsCatalog.getFlights()){
                tempCatalog.addFlight(flight);
            }
            refinedFlightsCatalog.clearCatalog();
        } else {
            tempCatalog = flightsCatalog;
        }
        return tempCatalog;
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
    //private MapLine lastSelectedLine;
    public void handleMapClick(MouseEvent e) {
        /*GeoPosition geo = mapCustom.convertPointToGeoPosition(e.getPoint());
        
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
        return distance;*/
    }
    // *>
}
