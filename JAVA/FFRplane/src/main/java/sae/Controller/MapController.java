package sae.controller;

import sae.controller.Interfaces.*;
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
}
