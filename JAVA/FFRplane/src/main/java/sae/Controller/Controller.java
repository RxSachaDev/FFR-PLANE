package sae.controller;

import java.awt.Color;
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
    
    private final AirportCatalog airportsCatalog = new AirportCatalog();
    private final FlightCatalog flightsCatalog = new FlightCatalog();
    
    private final FlightCatalog refinedFlightsCatalog = new FlightCatalog();
    
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
   
    
    //PARTIE RELATIVE A LA MainFrame

    
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
    public void initMapCustom(){
        fillAirportsCatalog();
        fillFlightsCatalog();
        initMapPoints(Settings.isEasterEggActivated());
        initMapLines();
        initMapLineComboBoxModel();
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
     * Initialise les lignes sur la carte, en fontion des parametres d'affinage.
     */
    public void initMapLines() {
        refinedFlightsCatalog.clearCatalog();
        if(Settings.getRefiningColor() == 0) {
            for (Flight flight : flightsCatalog.getFlights()) {
                mapLineSet.add(new MapLine(flight, Color.BLACK)); // Utilisation du constructeur avec Airport comme argument
                refinedFlightsCatalog.addFlight(flight);
            }
        } 
        if(refinedFlightsCatalog.getFlights().isEmpty()) { //Afficher les vols par niveau de hauteur
            for (Flight flight : flightsCatalog.getFlights()) {
                if(flight.getFlightHeighLevel() == Settings.getRefiningColor()) {
                    mapLineSet.add(new MapLine(flight, Color.BLACK)); // Utilisation du constructeur avec Airport comme argument
                    refinedFlightsCatalog.addFlight(flight);
                }
            }
        }
        mapCustom.repaint();
        ToolBox.createGraphTextFile(refinedFlightsCatalog);
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
}
