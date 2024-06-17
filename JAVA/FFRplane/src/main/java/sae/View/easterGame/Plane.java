package sae.view.easterGame;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.jxmapviewer.viewer.GeoPosition;

/**
 *
 * Cette classe représente un label d'un avion et ses différents comportements
 */
public class Plane extends JLabel {

    public ImageIcon planeUpIcon;
    public ImageIcon planeDownIcon;
    public ImageIcon planeLeftIcon;
    public ImageIcon planeRightIcon;
    public ImageIcon planeUpLeftIcon;
    public ImageIcon planeUpRightIcon;
    public ImageIcon planeDownLeftIcon;
    public ImageIcon planeDownRightIcon;

    private GeoPosition position;
    private double vitesse = 0.002;
    private String typePlane;

    public Plane(String type, GeoPosition position,
            ImageIcon planeUpIcon, ImageIcon planeDownIcon,
            ImageIcon planeLeftIcon, ImageIcon planeRightIcon,
            ImageIcon planeUpLeftIcon, ImageIcon planeUpRightIcon,
            ImageIcon planeDownLeftIcon, ImageIcon planeDownRightIcon) {
        super(resizeIcon(planeUpIcon, 60, 80)); // Taille par défaut de l'avion

        this.planeUpIcon = resizeIcon(planeUpIcon, 60, 80);
        this.planeDownIcon = resizeIcon(planeDownIcon, 60, 80);
        this.planeLeftIcon = resizeIcon(planeLeftIcon, 80, 60);
        this.planeRightIcon = resizeIcon(planeRightIcon, 80, 60);
        this.planeUpLeftIcon = resizeIcon(planeUpLeftIcon, 80, 60);
        this.planeUpRightIcon = resizeIcon(planeUpRightIcon, 80, 60);
        this.planeDownLeftIcon = resizeIcon(planeDownLeftIcon, 80, 60);
        this.planeDownRightIcon = resizeIcon(planeDownRightIcon, 80, 60);
        this.typePlane = type;
        this.position = position;
    }

    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void setPlaneIcon(ImageIcon planeIcon, int width, int height) {
        this.setIcon(resizeIcon(planeIcon, width, height));
    }

    public GeoPosition getPosition() {
        return position;
    }

    public void setPosition(GeoPosition position) {
        this.position = position;
    }

    public String getTypePlane() {
        return this.typePlane;
    }

    public void setTypePlane(String typePlane) {
        this.typePlane = typePlane;
    }

    public double getVitesse() {
        return this.vitesse;
    }

    public void setVitesse(double vitesse) {
        this.vitesse = vitesse;
    }

    public void updateTypePlane(String typePlane) {
        this.typePlane = typePlane;
    }

    public void setRafaleIcon(ImageIcon planeUpIcon, ImageIcon planeDownIcon,
            ImageIcon planeLeftIcon, ImageIcon planeRightIcon,
            ImageIcon planeUpLeftIcon, ImageIcon planeUpRightIcon,
            ImageIcon planeDownLeftIcon, ImageIcon planeDownRightIcon) {
        this.planeUpIcon = resizeIcon(planeUpIcon, 60, 80);
        this.planeDownIcon = resizeIcon(planeDownIcon, 60, 80);
        this.planeLeftIcon = resizeIcon(planeLeftIcon, 80, 60);
        this.planeRightIcon = resizeIcon(planeRightIcon, 80, 60);
        this.planeUpLeftIcon = resizeIcon(planeUpLeftIcon, 80, 60);
        this.planeUpRightIcon = resizeIcon(planeUpRightIcon, 80, 60);
        this.planeDownLeftIcon = resizeIcon(planeDownLeftIcon, 80, 60);
        this.planeDownRightIcon = resizeIcon(planeDownRightIcon, 80, 60);
        this.setPlaneIcon(this.planeUpIcon, 80, 60);
        this.typePlane = "Rafale";
    }

    public void setPlaneIcon(ImageIcon planeUpIcon, ImageIcon planeDownIcon,
            ImageIcon planeLeftIcon, ImageIcon planeRightIcon,
            ImageIcon planeUpLeftIcon, ImageIcon planeUpRightIcon,
            ImageIcon planeDownLeftIcon, ImageIcon planeDownRightIcon) {
        this.planeUpIcon = resizeIcon(planeUpIcon, 60, 80);
        this.planeDownIcon = resizeIcon(planeDownIcon, 60, 80);
        this.planeLeftIcon = resizeIcon(planeLeftIcon, 80, 60);
        this.planeRightIcon = resizeIcon(planeRightIcon, 80, 60);
        this.planeUpLeftIcon = resizeIcon(planeUpLeftIcon, 80, 60);
        this.planeUpRightIcon = resizeIcon(planeUpRightIcon, 80, 60);
        this.planeDownLeftIcon = resizeIcon(planeDownLeftIcon, 80, 60);
        this.planeDownRightIcon = resizeIcon(planeDownRightIcon, 80, 60);
        this.setPlaneIcon(this.planeUpIcon, 80, 60);
        this.typePlane = "Avion de ligne";
    }
}
