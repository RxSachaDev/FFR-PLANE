package sae.view.mapCustom;

import java.awt.Graphics;
import java.awt.Graphics2D;
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
 * MapPoint (Aeroports) et des MapLine (Vols) prédéfinis, 
 * de gérer les événements de souris pour le déplacement et le zoom, 
 * et de changer le style de la carte en fonction de l'index fourni.
 *
 * Elle utilise des types provenant de différents fournisseurs de cartes, comme
 * OpenStreetMap ou Virtual Earth.
 *
 * @author fillo
 * @author mathe
 */
public class MapCustom extends JXMapViewer {
    
    /**
     * Le niveau de dézoom maximal autorisé pour la Map.
     */
    private int maxUnzoom = 13;

    /**
     * Le niveau de zoom maximal autorisé pour la Map.
     */
    private int maxZoom = 8;
    
    /**
     * Le niveau de zoom initial de la Map.
     */
    private final int initialZoom = 13;

    private Controller controller; 

    /**
     * Constructeur de la classe MapCustom.
     */
    public MapCustom() {
        init(46.6396031, 2.7105474, initialZoom);
        listenersManager();
    }
    
    
    /* ••••••••••••• MÉTHODES ••••••••••••• */

    
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
     * Gère les différents écouteurs d'événements de la carte.
     */
    private void listenersManager(){
        addMouseWheelListener(new MouseWheelListener(){
            /**
             * Gère le comportement du zoom de la carte en réponse aux événements de la molette de la souris.
             * Réduit le niveau de zoom si celui-ci est supérieur au maxZoom, 
             * l'augmente s'il est inférieur au minZoom.
             */
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int currentZoom = getZoom();

                if (currentZoom > maxUnzoom) {
                    setZoom(maxUnzoom);
                }

                if (currentZoom < maxZoom) {
                    setZoom(maxZoom);
                }
            }
        });
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
        
        // Sert à limiter les bugs de zoom lorsqu'on change de style en zoomant/dézoomant
        int actualZoom = getZoom();
        if(actualZoom > maxUnzoom) actualZoom = maxUnzoom;
        else if (actualZoom < maxZoom) actualZoom = maxZoom;
        setZoom(actualZoom);
        
        switch (index) {
            case 0:
                init(actualLatitude, actualLongitude, actualZoom);
                break;
            case 1:
                info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                break;
            case 2:
                info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
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
    }

    
    /**
     * Initialise une Map avec une vue satellite pour l'easterGame.
     *
     * @param latitude La latitude du point central de la carte.
     * @param longitude La longitude du point central de la carte.
     */
    public void initGameMap(double latitude, double longitude) {
        TileFactoryInfo info = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(latitude, longitude);
        setAddressLocation(geo);
        setZoom(5);
    }
 

    /* ••••••••••••• GETTERS / SETTERS ••••••••••••• */
    
    
    /**
     * Définit le contrôleur associé à cette MapCustom.
     *
     * @param controller Le contrôleur à associer.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
    

    /**
     * Définit les coordonnées de la Map avec une latitude et une longitude
     * spécifiques.
     *
     * @param latitude La latitude du point central de la carte.
     * @param longitude La longitude du point central de la carte.
     */
    public void setCoords(int latitude, int longitude) {
        GeoPosition geo = new GeoPosition(latitude, longitude);
        setAddressLocation(geo);
    }
    
        
    /**
     * Définit le niveau de zoom maximal de la carte.
     *
     * @param maxZ Le niveau de zoom maximal autorisé.
     */
    public void setMaxUnzoom(int maxZ) {
        maxUnzoom = maxZ;
        if (getZoom() > maxZ) {
            setZoom(maxZ);
        }
    }

    
    /**
     * Définit le niveau de zoom minimal de la carte.
     *
     * @param minZ Le niveau de zoom minimal autorisé.
     */
    public void setMaxZoom(int minZ) {
        maxZoom = minZ;
        if (getZoom() < minZ) {
            setZoom(minZ);
        }
    }

    
    /**
     * Retourne le niveau de zoom maximal actuellement autorisé.
     *
     * @return Le niveau de zoom maximal.
     */
    public int getMaxUnzoom() {
        return this.maxUnzoom;
    }
    

    /**
     * Retourne le niveau de zoom minimal actuellement autorisé.
     *
     * @return Le niveau de zoom minimal.
     */
    public int getMaxZoom() {
        return this.maxZoom;
    }


    /* ••••••••••••• MÉTHODES @OVERRIDE ••••••••••••• */
    
    
    /**
     * Redéfinition de la méthode paintComponent pour dessiner les lignes sur la
     * carte, si un contrôleur est défini.
     *
     * @param g L'objet Graphics pour dessiner.
     */
    @Override
    protected void paintComponent(Graphics g) {
        if(controller!=null){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            //Dessiner les lignes
            for (MapLine line : controller.getMapLineSet()) {
                line.paint(g2, this, getWidth(), getHeight());
            }
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    // ******************************************* JE DOUTE QUE CE SOIT DANS MAPCUSTOM   
    
    /**
     * Déplace la carte de manière relative aux coordonnées actuelles.
     *
     * @param stepX Pas de déplacement horizontal.
     * @param stepY Pas de déplacement vertical.
     */
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
    }
*/
// *******************************************<**>



}
