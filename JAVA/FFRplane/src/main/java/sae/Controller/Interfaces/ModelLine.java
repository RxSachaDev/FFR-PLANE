package sae.controller.Interfaces;

import org.jxmapviewer.viewer.GeoPosition;

/**
 * L'interface ModelLine, située dans le package Controller.Interfaces, est implémentée par la classe Flight du package Model.
 * Cette interface permet de faire la liaison entre la classe Flight du package Model et la classe MapLine du package View.
 * 
 * @see Flight
 * @see MapLine
 * @see GeoPosition
 * 
 * @author mathe
 */
public interface ModelLine {
    /**
     * Obtient le premier point géographique.
     *
     * @return la première position géographique (GeoPosition).
     */
    GeoPosition getPoint1();

    /**
     * Obtient le deuxième point géographique de la Ligne.
     *
     * @return la deuxième position géographique (GeoPosition).
     */
    GeoPosition getPoint2();
    
    String toStringPointNames();
}
