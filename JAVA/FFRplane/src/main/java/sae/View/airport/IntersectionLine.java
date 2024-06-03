/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.View.airport;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import javax.swing.Painter;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import sae.Logiciel;

public class IntersectionLine extends JButton implements Painter<JXMapViewer> {

    private Color couleur;
    private GeoPosition point1;
    private GeoPosition point2;
    private Logiciel logiciel;

public IntersectionLine(GeoPosition point1, GeoPosition point2, Color couleur) {
        this.point1 = point1;
        this.point2 = point2;
        this.couleur = couleur;

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBackground(Color.RED);
        setPreferredSize(new Dimension(100, 100));
        addActionListener(e -> System.out.println("Clic sur la ligne détecté !"));

        // Set the size of the icon
        int iconSize = 20;

        // Create an image to draw the line
        ImageIcon icon = new ImageIcon(new java.awt.image.BufferedImage(iconSize, iconSize, java.awt.image.BufferedImage.TYPE_INT_ARGB));
        Graphics2D g2 = (Graphics2D) icon.getImage().getGraphics();

        // Draw the line
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new java.awt.BasicStroke(2));
        Point pt1 = new Point(0, iconSize);
        Point pt2 = new Point(iconSize, 0);
        g2.draw(new Line2D.Double(pt1, pt2));

        // Set the icon
        setIcon(icon);

        // Make the button transparent and remove border
        setBorderPainted(false);
        setContentAreaFilled(false);

        // Dispose graphics object
        g2.dispose();
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

        g.setStroke(new BasicStroke(2));

        Point2D pt1 = map.getTileFactory().geoToPixel(point1, map.getZoom());
        Point2D pt2 = map.getTileFactory().geoToPixel(point2, map.getZoom());

        g.drawLine((int) pt1.getX(), (int) pt1.getY(), (int) pt2.getX(), (int) pt2.getY());

        g.dispose();
    }

    public void setLogiciel(Logiciel logiciel) {
        this.logiciel = logiciel;
    }

    @Override
    public String toString() {
        return "{Couleur : " + this.couleur + " pt1  : " + this.point1 + " pt2 : " + this.point2 + "}";
    }
}
