package sae.Models.toolbox;

import org.jxmapviewer.viewer.GeoPosition;

/**
 * The ToolBox class provides utility methods for various calculations.
 */
public class ToolBox {
    /**
     * Calculates the distance between two points in a Cartesian plane.
     *
     * @param pointA the coordinates of the first point (x, y)
     * @param pointB the coordinates of the second point (x, y)
     * @return the distance between pointA and pointB
     */
    public static double calDistance(GeoPosition pointA, GeoPosition pointB) {
        // distanceAB = sqrt((xB - xA)^2 + (yB - yA)^2)
        return Math.sqrt(Math.pow((pointB.getLatitude() - pointA.getLatitude()), 2) + Math.pow((pointB.getLongitude() - pointA.getLongitude()), 2));
    }
}

