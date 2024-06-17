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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import sae.controller.Logiciel;
import sae.controller.Interfaces.ModelLine;
import sae.models.flights.Flight;

public class MapLine implements Painter<JXMapViewer>, MouseListener {

    private Color couleur;
    private GeoPosition point1;
    private GeoPosition point2;
    private Logiciel logiciel;
    private MapCustom map;
    private ModelLine modelLine;

    public MapLine(ModelLine modelLine, Color couleur, MapCustom map) {
        this.point1 = modelLine.getPoint1();
        this.point2 = modelLine.getPoint2();
        this.couleur = couleur;
        this.map = map;
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

    public boolean isPointOnLineSegment(GeoPosition clickPoint, GeoPosition pt1, GeoPosition pt2, double threshold) {
        double distance = pointLineDistance(clickPoint, pt1, pt2);
        return distance <= threshold;
    }

    public double pointLineDistance(GeoPosition clickPoint, GeoPosition pt1, GeoPosition pt2) {
        double x0 = clickPoint.getLatitude();
        double y0 = clickPoint.getLongitude();
        double x1 = pt1.getLatitude();
        double y1 = pt1.getLongitude();
        double x2 = pt2.getLatitude();
        double y2 = pt2.getLongitude();

        double A = y2 - y1;
        double B = x1 - x2;
        double C = x2 * y1 - x1 * y2;

        return Math.abs(A * x0 + B * y0 + C) / Math.sqrt(A * A + B * B);
    }

    public GeoPosition getPoint1() {
        return point1;
    }

    public GeoPosition getPoint2() {
        return point2;
    }

    @Override
    public String toString() {
        return "{Couleur : " + this.couleur + " pt1  : " + this.point1 + " pt2 : " + this.point2 + "}";
    }

    @Override
    public void mousePressed(MouseEvent e) {
        logiciel.setJTextAreaText1("Le texte que vous voulez définir dans JTextArea 1");
        logiciel.setJTextAreaText2("Le texte que vous voulez définir dans JTextArea 2");
        System.out.println("La ligne a été cliquée");
    }
   
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    public ModelLine getModelLine(){
        return modelLine;
    }
}
