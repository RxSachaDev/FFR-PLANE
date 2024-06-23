package sae.controller.Interfaces;

import org.jxmapviewer.viewer.GeoPosition;

/**
 * L'interface ModelPoint, située dans le package Controller.Interfaces, est implémentée par la classe Airport du package Model.
 * Cette interface permet de faire la liaison entre la classe Airport du package Model et la classe MapPoint du package View.
 * 
 * @see Airport
 * @see MapPoint
 * @see GeoPosition
 * 
 * @author mathe
 */
public interface ModelPoint {
    /**
     * Obtient la position géographique du point.
     *
     * @return la position géographique (GeoPosition).
     */
    GeoPosition getGeoPosition();
    
    String toStringName();
    
    String getModelPointCode();
}
