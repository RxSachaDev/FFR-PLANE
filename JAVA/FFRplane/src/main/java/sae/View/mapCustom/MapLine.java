/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

public class MapLine implements Painter<JXMapViewer> {

    private Color couleur;
    private final GeoPosition point1;
    private final GeoPosition point2;
    
    private final ModelLine modelLine;

    public MapLine(ModelLine modelLine, Color couleur) {
        this.point1 = modelLine.getPoint1();
        this.point2 = modelLine.getPoint2();
        this.couleur = couleur;
        this.modelLine = modelLine;
    }

    
    public void setColor(Color couleur) {
        this.couleur = couleur;
    }

    
    public Color getColor() {
        return this.couleur;
    }

    
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


    public GeoPosition getPoint1() {
        return point1;
    }

    
    public GeoPosition getPoint2() {
        return point2;
    }

       
    public ModelLine getModelLine(){
        return modelLine;
    }
    
    
    @Override
    public String toString() {
        return modelLine.getPointNames();
    }
    
    public String toStringModelLine(){
        return modelLine.toString();
    }
}
