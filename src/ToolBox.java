public class ToolBox {
    public static double distance(double[] pointA, double[] pointB) {
        // distanceAB = racine(carre(xB - xA) + carre(yB - yA))
        return Math.sqrt(Math.pow((pointB[0] - pointA[0]), 2) + Math.pow((pointB[1] - pointA[1]), 2));
    }

    
}
