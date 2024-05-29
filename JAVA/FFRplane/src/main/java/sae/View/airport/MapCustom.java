/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.View.airport;

import sae.View.easterGame.EasterPoint;
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
import sae.View.easterGame.Plane;

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

    private final Set<Airportpoint> airportP = new HashSet<>();

    /**
     * Constructeur de la classe MapCustom. Initialise les aéroports prédéfinis.
     */
    public MapCustom() {
        easterEgg();
        airportP.add(new Airportpoint("Roissy Charles de Gaulle", new GeoPosition(49.0039586, 2.5148261)));
        airportP.add(new Airportpoint("Lyon-Saint Exupéry", new GeoPosition(45.719036, 5.0739078)));
        airportP.add(new Airportpoint("Montpellier Méditerranée", new GeoPosition(43.5795989, 3.9652374)));
        airportP.add(new Airportpoint("Grenoble Alpes Isère", new GeoPosition(45.3621752, 5.3276695)));
        airportP.add(new Airportpoint("Toulouse-Blagnac", new GeoPosition(43.6314401, 1.3646339)));
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
        WaypointPainter<Airportpoint> ap = new AirportpointRender();
        ap.setWaypoints(airportP);
        setOverlayPainter(ap);
        for (Airportpoint d : airportP) {
            add(d.getButton());
        }
    }

    /**
     * Supprime les marqueurs des aéroports de la carte.
     */
    public void removeAirports() {
        for (Airportpoint d : airportP) {
            remove(d.getButton());
        }
        airportP.clear();
        initAirports();
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
        airportP.add(new EasterPoint(new GeoPosition(45.7893371, 4.8808478)));
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

}
