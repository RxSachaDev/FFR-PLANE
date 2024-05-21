package toolbox;
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
    public static double calDistance(double[] pointA, double[] pointB) {
        // distanceAB = sqrt((xB - xA)^2 + (yB - yA)^2)
        return Math.sqrt(Math.pow((pointB[0] - pointA[0]), 2) + Math.pow((pointB[1] - pointA[1]), 2));
    }
}

