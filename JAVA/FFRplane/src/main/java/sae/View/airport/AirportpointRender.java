

package sae.View.airport;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import javax.swing.JButton;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 * Classe peintre de points d'aéroport pour afficher les points d'aéroport sur une carte.
 * Ce peintre positionne les boutons associés aux points d'aéroport sur la carte.
 * @author fillo
 */
public class AirportpointRender extends WaypointPainter<Airportpoint> {
    
    /**
     * Peint les points d'aéroport sur la carte et positionne les boutons associés.
     * @param g L'objet Graphics2D utilisé pour dessiner.
     * @param map Le JXMapViewer sur lequel les points d'aéroport sont affichés.
     * @param width La largeur de la zone de dessin.
     * @param height La hauteur de la zone de dessin.
     */
    @Override
    protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height){
        for(Airportpoint ap : getWaypoints()){  // Parcours tous les points d'aéroport à peindre
            Point2D p = map.getTileFactory().geoToPixel(ap.getPosition(), map.getZoom()); // Convertit la position géographique du point en coordonnées de pixels sur la carte
            Rectangle rec = map.getViewportBounds();
            int x = (int)(p.getX() - rec.getX());
            int y = (int)(p.getY() - rec.getY());
            JButton cmd = ap.getButton();  // Récupère le bouton associé au point d'aéroport
            cmd.setLocation(x - cmd.getWidth() / 2, y - cmd.getHeight()); // Positionne le bouton à l'emplacement du point d'aéroport sur la carte
        }
    }
}
