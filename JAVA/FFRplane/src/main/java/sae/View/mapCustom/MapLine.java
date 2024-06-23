package sae.view.mapCustom;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import javax.swing.Painter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Rectangle;

import sae.controller.Interfaces.ModelLine;


/**
 * Classe pour dessiner une ligne entre deux points géographiques sur un composant JXMapViewer.
 * Utilisé comme un Painter pour personnaliser l'affichage de la carte.
 * La couleur et les points géographiques de la ligne sont définis par le ModelLine passé en paramètre.
 * Implements Painter<JXMapViewer> pour dessiner la ligne sur le JXMapViewer.
 * Les méthodes toString et paint sont surchargées pour afficher les informations et dessiner la ligne, respectivement.
 * Les couleurs de ligne et les points géographiques peuvent être modifiés via des setters.
 * Les informations détaillées de la ligne sont accessibles via les méthodes getModelLine et toStringModelLine.
 * 
 * @author mathe
 * @author fillo
 */
public class MapLine implements Painter<JXMapViewer>,Comparable<MapLine> {

    private Color couleur;
    private final GeoPosition point1;
    private final GeoPosition point2;
    
    private final ModelLine modelLine;

    /**
     * Constructeur pour initialiser la MapLine à partir de la ModelLine et d'une couleur spécifiés.
     *
     * @param modelLine Représentation de la MapLine dans le model.
     * @param couleur   Couleur de la ligne à dessiner sur la carte.
     */
    public MapLine(ModelLine modelLine, Color couleur) {
        this.point1 = modelLine.getPoint1();
        this.point2 = modelLine.getPoint2();
        this.couleur = couleur;
        this.modelLine = modelLine;
    }

    
    /* ••••••••••••• MÉTHODES ••••••••••••• */
    
    
    /**
     * Méthode pour obtenir une représentation en chaîne des noms des points géographiques de la ligne.
     *
     * @return Une chaîne représentant les noms des points géographiques de la ligne.
     */
    public String toStringModelLine(){
        return modelLine.toString();
    }
    

    /* ••••••••••••• GETTERS / SETTERS ••••••••••••• */
    
    
    /**
     * Méthode pour définir la couleur de la ligne.
     *
     * @param couleur Nouvelle couleur de la ligne à définir.
     */
    public void setColor(Color couleur) {
        this.couleur = couleur;
    }

    
    /**
     * Méthode pour obtenir la couleur actuelle de la ligne.
     *
     * @return Couleur actuelle de la ligne.
     */
    public Color getColor() {
        return this.couleur;
    }
    
    
    /**
     * Méthode pour obtenir le premier point géographique de la ligne.
     *
     * @return Premier point géographique de la ligne.
     */
    public GeoPosition getPoint1() {
        return point1;
    }

    
    /**
     * Méthode pour obtenir le deuxième point géographique de la ligne.
     *
     * @return Deuxième point géographique de la ligne.
     */
    public GeoPosition getPoint2() {
        return point2;
    }

   
    /**
     * Méthode pour obtenir la modelLine associé à cette instance.
     *
     * @return modelLine associé à cette instance.
     */
    public ModelLine getModelLine(){
        return modelLine;
    }
    
    
    /* ••••••••••••• MÉTHODES @OVERRIDE ••••••••••••• */
    
    
    /**
     * Méthode Override pour obtenir une représentation en chaîne de cette ligne.
     *
     * @return Une chaîne représentant les noms des points géographiques de la ligne.
     */
    @Override
    public String toString() {
        return modelLine.toStringPointNames();
    }
    
    
    /**
     * Méthode Override pour dessiner la ligne sur le composant JXMapViewer.
     *
     * @param g      Graphics2D utilisé pour dessiner.
     * @param map    JXMapViewer sur lequel dessiner la ligne.
     * @param width  Largeur du composant à dessiner.
     * @param height Hauteur du composant à dessiner.
     */
    @Override
    public void paint(Graphics2D g, JXMapViewer map, int width, int height) {
        g = (Graphics2D) g.create();
        Rectangle rect = map.getViewportBounds();
        g.translate(-rect.x, -rect.y);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(couleur);
        g.setStroke(new BasicStroke(1));

        Point2D pt1 = map.getTileFactory().geoToPixel(point1, map.getZoom());
        Point2D pt2 = map.getTileFactory().geoToPixel(point2, map.getZoom());

        g.drawLine((int) pt1.getX(), (int) pt1.getY(), (int) pt2.getX(), (int) pt2.getY());
        g.dispose();
    }

    @Override
    public int compareTo(MapLine other) {
        return toString().compareTo(other.toString());
    }
}
