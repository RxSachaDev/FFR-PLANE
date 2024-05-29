/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.View;

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

/**
 *
 * @author fillo
 */
public class MapCustom extends JXMapViewer {

    //private final int MAXZOOMSIZE = 16;
    //private final int MINZOOMSIZE = 1;
    private final Set<Airportpoint> airportP = new HashSet<>();

    public MapCustom() {
        airportP.add(new Airportpoint("Roissy Charles de Gaulle", new GeoPosition(49.0039586, 2.5148261)));
        airportP.add(new Airportpoint("Lyon-Saint Exupéry", new GeoPosition(45.719036, 5.0739078)));
        airportP.add(new Airportpoint("Montpellier Méditerranée", new GeoPosition(43.5795989, 3.9652374)));
        airportP.add(new Airportpoint("Grenoble Alpes Isère", new GeoPosition(45.3621752, 5.3276695)));
        airportP.add(new Airportpoint("Toulouse-Blagnac", new GeoPosition(43.6314401, 1.3646339)));
    }

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

    public void initAirports() {
        WaypointPainter<Airportpoint> ap = new AirportpointRender();
        ap.setWaypoints(airportP);
        setOverlayPainter(ap);
        for (Airportpoint d : airportP) {
            add(d.getButton());
        }
    }

    public void removeAirports() {
        for (Airportpoint d : airportP) {
            remove(d.getButton());
        }
        airportP.clear();
        initAirports();
    }

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

    public void setMaxZoom(int maxZ) {
        if (getZoom() > maxZ) {
            setZoom(maxZ);
        }
    }
    
    public void setMinZoom(int maxM){
        if(getZoom() < maxM){
            setZoom(maxM);
        }
    }
}
