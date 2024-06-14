/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.view.airport;

import sae.Logiciel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import sae.view.easterGame.EasterPoint;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.GeoPosition;
import sae.utils.Settings;
import sae.models.airports.Airport;
import sae.models.airports.AirportCatalog;
import sae.models.flights.Flight;
import sae.models.flights.FlightCatalog;
import sae.models.toolbox.ToolBox;
import sae.view.easterGame.MonumentWaypoint;

/**
 * Cette classe étend JXMapViewer pour créer une carte personnalisée avec des
 * fonctionnalités spécifiques. Elle permet d'initialiser la carte avec des
 * aéroports prédéfinis, de gérer les événements de souris pour le déplacement
 * et le zoom, et de changer le style de la carte en fonction de l'index fourni.
 *
 * Elle utilise des types provenant de différents fournisseurs de cartes, comme
 * OpenStreetMap ou Virtual Earth.
 *
 * @author fillo
 * @author mathe
 */
public class MapCustom extends JXMapViewer {

    private Logiciel logiciel;
    private static int compteur = 0;
    private final Set<Airportpoint> airportPointSet = new HashSet<>();
    private final Set<FlightLine> flightLineSet = new HashSet<>();
    private final Set<Airportpoint> monumentPointSet;
    private FlightLine lastSelectedLine = null;
    private final AirportCatalog airportsCatalog = new AirportCatalog();
    private final FlightCatalog flightsCatalog = new FlightCatalog();

    /**
     * Constructeur de la classe MapCustom. Initialise les aéroports prédéfinis.
     */
    public MapCustom() {
        this.monumentPointSet = new HashSet<>();
        easterEgg();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMapClick(e);
            }
            
        });
    }
    
    private void handleMapClick(MouseEvent e) {
        GeoPosition geo = convertPointToGeoPosition(e.getPoint());
        
        System.out.println(geo);
        if (geo != null) {
            FlightLine closestFlightLine = null;
            double closestDistance = Double.MAX_VALUE;
            for (FlightLine flightLine : flightLineSet) {
                
                double distance = distToFlightLine(flightLine,geo);
                System.out.println(distance);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestFlightLine = flightLine;
                }
                
            }
            if (closestFlightLine != null && closestDistance <= 0.1) {  
                closestFlightLine.setColor(Color.ORANGE);
                if (lastSelectedLine != null && lastSelectedLine != closestFlightLine) {
                    lastSelectedLine.setColor(Color.BLACK);
                }
                lastSelectedLine = closestFlightLine;
            }
            repaint();
        }
    }
   
    private static double distToFlightLine(FlightLine flightLine,GeoPosition point) {
        GeoPosition point1 = flightLine.getPoint1();
        GeoPosition point2 = flightLine.getPoint2();
        
        double x = point.getLatitude();
        double y = point.getLongitude();
        

        double a = (point2.getLongitude()- point1.getLongitude()) / (point2.getLatitude()- point1.getLatitude());
        double b = point2.getLongitude()- a * point2.getLatitude();

        double distance = Math.abs(a*x - y+b)/Math.sqrt(Math.pow(a, 2)+1);
        return distance;
    }
    
    

    
    /**
     * Initialise la carte avec la latitude, la longitude et le zoom spécifiés.
     *
     * @param latitude La latitude du point central de la carte.
     * @param longitude La longitude du point central de la carte.
     * @param actualZoom Le niveau de zoom initial de la carte.
     */
    public void init(double latitude, double longitude, int actualZoom) {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(latitude, longitude);
        setAddressLocation(geo);
        setZoom(actualZoom);

        // rendre la map dragable 
        MouseInputListener ml = new PanMouseInputListener(this);
        addMouseListener(ml);
        addMouseMotionListener(ml);

        // rendre la map zoomable
        addMouseWheelListener(new ZoomMouseWheelListenerCenter(this));
    }
    
    //TEMPORAIRE <
    public static double distanceBetween(GeoPosition p1, GeoPosition p2) {
        double deltaX = p2.getLatitude()- p1.getLatitude();
        double deltaY = p2.getLongitude()- p1.getLongitude();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    
    public static GeoPosition orthogonalProjection(GeoPosition p1, GeoPosition p2, GeoPosition p3) {
        // Convertir les points en coordonnées cartésiennes (latitude, longitude)
        double x1 = p1.getLongitude(), y1 = p1.getLatitude();
        double x2 = p2.getLongitude(), y2 = p2.getLatitude();
        double x3 = p3.getLongitude(), y3 = p3.getLatitude();

        // Vecteur directeur de la droite P2P3
        double dx = x3 - x2;
        double dy = y3 - y2;

        // Vecteur de P2 à P1
        double px = x1 - x2;
        double py = y1 - y2;

        // Calculer la projection scalaire de P2P1 sur P2P3
        double scalarProjection = (px * dx + py * dy) / (dx * dx + dy * dy);

        // Calculer les coordonnées du projeté orthogonal
        double xProj = x2 + scalarProjection * dx;
        double yProj = y2 + scalarProjection * dy;

        return new GeoPosition(yProj, xProj);
    }
    // >
    
    /**
     * Initialise les marqueurs des aéroports sur la carte.
     */
    public void initAirports() {
        try {
            ToolBox.fillAirportList(Settings.getAirportsFilePath(),airportsCatalog);
        } catch (Exception e) {
        }
        for (Airport airport : airportsCatalog.getAirports()) {
            airportPointSet.add(new Airportpoint(airport)); // Utilisation du constructeur avec Airport comme argument
        }
        
        //TEMPORAIRE <
        WaypointPainter<Airportpoint> ap = new AirportpointRender();
        airportPointSet.add(new Airportpoint(new GeoPosition(45.20858185060021,1.9104409217834473)));
        airportPointSet.add(new Airportpoint(orthogonalProjection(new GeoPosition(45.20858185060021,1.9104409217834473), new GeoPosition(45.72638888888889,5.090833333333333), new GeoPosition(44.82833333333333,-0.7155555555555555))));
        airportPointSet.add(new Airportpoint(orthogonalProjection(new GeoPosition(45.20858185060021,1.9104409217834473), new GeoPosition(43.43555555555555,5.213611111111112), new GeoPosition(48.44777777777777,-4.418333333333334))));
        System.out.println(distanceBetween(new GeoPosition(45.20858185060021,1.9104409217834473), orthogonalProjection(new GeoPosition(45.20858185060021,1.9104409217834473), new GeoPosition(45.72638888888889,5.090833333333333), new GeoPosition(44.82833333333333,-0.7155555555555555))));
        System.out.println(distanceBetween(new GeoPosition(45.20858185060021,1.9104409217834473), orthogonalProjection(new GeoPosition(45.20858185060021,1.9104409217834473), new GeoPosition(43.43555555555555,5.213611111111112), new GeoPosition(48.44777777777777,-4.418333333333334))));
        // >
        
        ap.setWaypoints(airportPointSet);
        setOverlayPainter(ap);
        for (Airportpoint d : airportPointSet) {
            Airport airport = d.getAirport();
            if (airport != null) {
                add(d.getButton());
                compteur++;
            } else {
                add(d.getButton());
                System.err.println("Airport associated with AirportWaypoint is null.");
            }
        }
    }

    /**
     * Supprime les marqueurs des aéroports de la carte.
     */
    public void removeAirports() {
        for (Airportpoint d : airportPointSet) {
            remove(d.getButton());
        }
        airportPointSet.clear();
        initAirports();
    }

    public void initFlightLines() {
        try {
            ToolBox.fillFlightList(Settings.getFlightsFilePath(),flightsCatalog,airportsCatalog);
        } catch (Exception e) {
        }
        for (Flight flight : flightsCatalog.getFlights()) {
            System.out.println(flight);
            flightLineSet.add(new FlightLine(flight,Color.BLACK,this,logiciel)); // Utilisation du constructeur avec Airport comme argument
        }
        repaint();
        ToolBox.createGraphTextFile(flightsCatalog);
    }

    /**
     * Change le style de la carte en fonction de l'index spécifié.
     *
     * @param index L'index indiquant le style de carte à utiliser.
     */
    public void changeStyle(int index) {
        TileFactoryInfo info = null;
        GeoPosition currentPosition = getAddressLocation();
        double actualLatitude = currentPosition.getLatitude();
        double actualLongitude = currentPosition.getLongitude();
        int actualZoom = getZoom();
        switch (index) {
            case 0:
                if (actualZoom > 16) {
                    actualZoom = 16;
                    System.out.println("Zoom : " + actualZoom);
                }
                init(actualLatitude, actualLongitude, actualZoom);
                break;
            case 1:
                info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                System.out.println("Zoom : " + actualZoom);
                break;
            case 2:
                info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                System.out.println("Zoom : " + actualZoom);

                break;
            case 3:
                info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
                break;
            default:
                info = new OSMTileFactoryInfo();
                break;
        }
        if (info != null) {
            DefaultTileFactory tileFactory = new DefaultTileFactory(info);
            setTileFactory(tileFactory);
        }
        setMinZoom(2);
        setMaxZoom(17);
    }

    /**
     * Définit le niveau de zoom maximal de la carte.
     *
     * @param maxZ Le niveau de zoom maximal autorisé.
     */
    public void setMaxZoom(int maxZ) {
        if (getZoom() > maxZ) {
            setZoom(maxZ);
        }
    }

    /**
     * Définit le niveau de zoom minimal de la carte.
     *
     * @param maxM Le niveau de zoom minimal autorisé.
     */
    public void setMinZoom(int maxM) {
        if (getZoom() < maxM) {
            setZoom(maxM);
        }
    }

    public void easterEgg() {
        airportPointSet.add(new EasterPoint(new GeoPosition(45.7893371, 4.8808478)));
    }

    public void initGameMap(double latitude, double longitude) {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(latitude, longitude);
        setAddressLocation(geo);
        setZoom(5);
    }

    public void moveMap(double stepX, double stepY) {
        double slowdownFactor = 0.1;
        GeoPosition currentPosition = getAddressLocation();
        double latitude = currentPosition.getLatitude() + stepY * slowdownFactor;
        double longitude = currentPosition.getLongitude() + stepX * slowdownFactor;
        setAddressLocation(new GeoPosition(latitude, longitude));
    }

    public void addMonuments(MonumentWaypoint mWP) {
        monumentPointSet.add(mWP);
        WaypointPainter<Airportpoint> ap = new AirportpointRender();
        ap.setWaypoints(monumentPointSet);
        setOverlayPainter(ap);
        add(mWP.getButton());
        validate();
    }

    public void setCoords(int latitude, int longitude) {
        GeoPosition geo = new GeoPosition(latitude, longitude);
        setAddressLocation(geo);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Dessiner les lignes
        for (FlightLine line : flightLineSet) {
            line.paint(g2, this, getWidth(), getHeight());
        }
    }
}
