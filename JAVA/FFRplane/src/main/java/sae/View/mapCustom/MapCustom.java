package sae.view.mapCustom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.GeoPosition;

import sae.controller.Controller;

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
    /**
     * Le niveau de zoom maximal autorisé pour la carte.
     */
    private int maxZoom = 17;

    /**
     * Le niveau de zoom minimal autorisé pour la carte.
     */
    private int minZoom = 2;

    private Controller controller;
    
    
    
    /**
     * Constructeur de la classe MapCustom. Initialise les aéroports prédéfinis.
     */
    public MapCustom() {
        
        init(46.6396031, 2.7105474, 14);
        
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.handleMapClick(e);
            }
        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int wheelRotation = e.getWheelRotation();
                int currentZoom = getZoom();

                if (!(currentZoom < 15)) {
                    setZoom(currentZoom - 1);
                }

                if (!(2 < currentZoom)) {
                    setZoom(currentZoom + 1);
                }
            }
        });
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

    
    public void setController(Controller controller) {
        this.controller = controller;
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
        maxZoom = maxZ;
        if (getZoom() > maxZ) {
            setZoom(maxZ);
        }
    }

    
    /**
     * Définit le niveau de zoom minimal de la carte.
     *
     * @param maxM Le niveau de zoom minimal autorisé.
     */
    public void setMinZoom(int minZ) {
        minZoom = minZ;
        if (getZoom() < minZ) {
            setZoom(minZ);
        }
    }

    
    public int getMaxZoom() {
        return this.maxZoom;
    }

    
    public int getMinZoom() {
        return this.minZoom;
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

    
    /*  
    public void addMonuments(MonumentWaypoint mWP) {
        monumentPointSet.add(mWP);
        WaypointPainter<Airportpoint> ap = new AirportpointRender();
        ap.setWaypoints(monumentPointSet);
        setOverlayPainter(ap);
        add(mWP.getButton());
        validate();
    }*/
     
    public void setCoords(int latitude, int longitude) {
        GeoPosition geo = new GeoPosition(latitude, longitude);
        setAddressLocation(geo);
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //Dessiner les lignes
        for (MapLine line : controller.getMapLineSet()) {
            line.paint(g2, this, getWidth(), getHeight());
        }
    }
}
