/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.view.airport;

import sae.Logiciel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import sae.view.easterGame.EasterPoint;
import java.util.HashSet;
import java.util.Iterator;
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
import sae.models.toolbox.FileTreatment;
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
/*
    private void handleMapClick(MouseEvent e) {
        GeoPosition geo = convertPointToGeoPosition(e.getPoint());
        if (geo != null) {
            FlightLine flightLine;
            boolean bool=false;
            Iterator<FlightLine> iterator = flightLineSet.iterator();
            while(iterator.hasNext() && !bool) {
                flightLine = iterator.next();
                if(isOnFlightLine(geo, flightLine,0.5)) {
                    bool = false;
                    flightLine.setColor(Color.ORANGE);
                    if(lastSelectedLine != null){
                        lastSelectedLine.setColor(Color.BLACK);
                    }
                    lastSelectedLine = flightLine;
                    lastSelectedLine.setColor(Color.ORANGE);
                    
                }
            }
            repaint();
        }
    }
    
    private static boolean isOnInterval(GeoPosition pose, FlightLine flightLine) {
        GeoPosition departureCoordinates = flightLine.getPoint1();
        GeoPosition arrivalCoordinates = flightLine.getPoint2();

        double maxLatitude = Math.max(departureCoordinates.getLatitude(), arrivalCoordinates.getLatitude());
        double minLatitude = Math.min(departureCoordinates.getLatitude(), arrivalCoordinates.getLatitude());
        double maxLongitude = Math.max(departureCoordinates.getLongitude(), arrivalCoordinates.getLongitude());
        double minLongitude = Math.min(departureCoordinates.getLongitude(), arrivalCoordinates.getLongitude());

        return (minLatitude <= pose.getLatitude() && pose.getLatitude() <= maxLatitude && 
                minLongitude <= pose.getLongitude() && pose.getLongitude() <= maxLongitude);
    }
    
    private static boolean isOnFlightLine(GeoPosition point, FlightLine flightLine,double margin) {
        GeoPosition point1 = flightLine.getPoint1();
        GeoPosition point2 = flightLine.getPoint2();
        

        double m = (point2.getLongitude() - point1.getLongitude()) / (point2.getLatitude() - point1.getLatitude());
        double p = point2.getLongitude() - m * point2.getLatitude();

        double y = m * point.getLatitude() + p;
        if(point.getLongitude() - margin<=y && y<=point.getLongitude() + margin)
            if(isOnInterval(point,flightLine))
                return true;
        return false;
    }*/
    private void handleMapClick(MouseEvent e) {
        GeoPosition geo = convertPointToGeoPosition(e.getPoint());
        if (geo != null) {
            FlightLine closestFlightLine = null;
            double closestDistance = Double.MAX_VALUE;

            for (FlightLine flightLine : flightLineSet) {
                double distance = distanceFromPointToLine(geo, flightLine.getPoint1(), flightLine.getPoint2());
                if (distance < closestDistance) {
                    
                    System.out.println(closestDistance+" - ");
                    closestDistance = distance;
                    closestFlightLine = flightLine;
                }
            }

            if (closestFlightLine != null && closestDistance <= 0.05) {  // Assurez-vous que la distance est dans la marge
                closestFlightLine.setColor(Color.ORANGE);
                if (lastSelectedLine != null && lastSelectedLine != closestFlightLine) {
                    lastSelectedLine.setColor(Color.BLACK);
                }
                lastSelectedLine = closestFlightLine;
            }

            repaint();
        }
    }

    private static double distanceFromPointToLine(GeoPosition point, GeoPosition lineStart, GeoPosition lineEnd) {
        double x0 = point.getLongitude();
        double y0 = point.getLatitude();
        
        double x1 = lineStart.getLongitude();
        double y1 = lineStart.getLatitude();
        
        double x2 = lineEnd.getLongitude();
        double y2 = lineEnd.getLatitude();

        // Calcul des coordonnées du point projeté sur le segment
        double dx = x2 - x1;
        double dy = y2 - y1;
        double t = ((x0 - x1) * dx + (y0 - y1) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t)); // Garantit que le point projeté est sur le segment
        double projX = x1 + t * dx;
        double projY = y1 + t * dy;

        // Calcul de la distance entre le point et le point projeté
        double distX = projX - x0;
        double distY = projY - y0;
        return Math.sqrt(distX * distX + distY * distY);
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

    /**
     * Initialise les marqueurs des aéroports sur la carte.
     */
    public void initAirports() {
        try {
            FileTreatment.fillAirportList(Settings.getAirportsFilePath(),airportsCatalog);
        } catch (Exception e) {
        }
        for (Airport airport : airportsCatalog.getAirports()) {
            airportPointSet.add(new Airportpoint(airport)); // Utilisation du constructeur avec Airport comme argument
        }
        //easterEgg();
        //afficherSet();

        WaypointPainter<Airportpoint> ap = new AirportpointRender();
        ap.setWaypoints(airportPointSet);
        setOverlayPainter(ap);
        for (Airportpoint d : airportPointSet) {
            Airport airport = d.getAirport();
            if (airport != null) {
                add(d.getButton());
                compteur++;
            } else {
                System.err.println("Airport associated with AirportWaypoint is null.");
            }
        }
        // System.out.print(compteur);
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

    private void setupMouseListeners() {
        MouseInputListener ml = new PanMouseInputListener(this);
        addMouseListener(ml);
        addMouseMotionListener(ml);
        addMouseWheelListener(new ZoomMouseWheelListenerCenter(this));
    }

    public void initIntersection() {
        try {
            FileTreatment.fillFlightList(Settings.getFlightsFilePath(),flightsCatalog,airportsCatalog);
        } catch (Exception e) {
        }
        for (Flight flight : flightsCatalog.getFlights()) {
            System.out.println(flight);
            flightLineSet.add(new FlightLine(flight,Color.BLACK,this,logiciel)); // Utilisation du constructeur avec Airport comme argument
        }
        repaint();
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
        //System.out.println(lineSet.size());
        // Dessiner les lignes
        for (FlightLine line : flightLineSet) {
            //System.out.println("add" + line.toString());
            line.paint(g2, this, getWidth(), getHeight());
            /*line.addMouseMotionListener( new MouseListener(){

                });*/
        }
    }
}
