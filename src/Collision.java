
//Penser à integrer les méthodes directement à la classe Vol ou ListeVols
public class Collision {
    private static boolean intersecVolsAuPoint(Vol volA, Vol volB, double x, double y){
        boolean bool = false;
        double[] volA_Depart = volA.getAero_depart().getCoordonnees();
        double[] volA_Arrivee = volA.getAero_arrivee().getCoordonnees();
        double[] volB_Depart = volB.getAero_depart().getCoordonnees();
        double[] volB_Arrivee = volB.getAero_arrivee().getCoordonnees();
    
        double max_xA = Math.max(volA_Depart[0], volA_Arrivee[0]);
        double min_xA = Math.min(volA_Depart[0], volA_Arrivee[0]);
        double max_yA = Math.max(volA_Depart[1], volA_Arrivee[1]);
        double min_yA = Math.min(volA_Depart[1], volA_Arrivee[1]);
    
        double max_xB = Math.max(volB_Depart[0], volB_Arrivee[0]);
        double min_xB = Math.min(volB_Depart[0], volB_Arrivee[0]);
        double max_yB = Math.max(volB_Depart[1], volB_Arrivee[1]);
        double min_yB = Math.min(volB_Depart[1], volB_Arrivee[1]);
    
        if(min_xA <= x && x <= max_xA && min_xB <= x && x <= max_xB && 
           min_yA <= y && y <= max_yA && min_yB <= y && y <= max_yB)
            bool = true;
        return bool;
    }
    
    private static double[] pointIntersection(Vol volA, Vol volB){
        double[] point = new double[2];
        point[0] = 7000; // Car les coordonnées terrestres < 7000 (sert au cas où il n'y a pas d'intersection)
    
        double[] cooArriveeA = volA.getAero_arrivee().getCoordonnees();
        double[] cooDepartA = volA.getAero_depart().getCoordonnees();
        double[] cooArriveeB = volB.getAero_arrivee().getCoordonnees();
        double[] cooDepartB = volB.getAero_depart().getCoordonnees();
    
        double mA = (cooArriveeA[1] - cooDepartA[1]) / (cooArriveeA[0] - cooDepartA[0]); // Coef directeur de la droite A
        double pA = cooArriveeA[1] - mA * cooArriveeA[0];
    
        double mB = (cooArriveeB[1] - cooDepartB[1]) / (cooArriveeB[0] - cooDepartB[0]); // Coef directeur de la droite B
        double pB = cooArriveeB[1] - mB * cooArriveeB[0];
    
        if (mA != mB) { // Vérifie si les droites ne sont pas parallèles
            double x = -(pA - pB) / (mA - mB); // abscisse du point d'intersection
            double y = mA * x + pA; // ordonnée du point d'intersection
            if (intersecVolsAuPoint(volA, volB, x, y)) { // Si le point d'intersection est dans l'intervalle des segments
                System.out.println("    | Point d'intersection : (" + x + "," + y + ")");
                point[0] = x;
                point[1] = y;
            }
        }
        return point;
    }
    
    public static boolean enCollision(Vol volA, Vol volB){
        boolean bool = false;
        double[] point = pointIntersection(volA, volB);
        if(point[0] != 7000){ // équivaut à x != null <= (Si aucun point d'intersection n'a été trouvé)
            double distancePoint_volA = ToolBox.distance(volA.getAero_depart().getCoordonnees(), point);
            double heurePoint_volA = tempsVol(volA.getDistanceVol(), distancePoint_volA, volA.getDuree(), volA.getH_depart(), volA.getMin_depart());

            double distancePoint_volB = ToolBox.distance(volB.getAero_depart().getCoordonnees(), point);
            double heurePoint_volB = tempsVol(volB.getDistanceVol(), distancePoint_volB, volB.getDuree(), volB.getH_depart(), volB.getMin_depart());

            if(Math.abs(heurePoint_volA - heurePoint_volB) < 15)
                bool = true;
    
            System.out.println("    | Intervalle : "+heurePoint_volA+" - "+heurePoint_volB+" = "+(heurePoint_volA-heurePoint_volB));
        }
        System.out.println("    | Collision : "+bool);
        
        return bool;
    }

    public static double tempsVol(double distanceVol, double distancePoint, double dureeVol, int h_DepartVol, int min_DepartVol) {
        // Simple produit en croix
        double dureePoint = (distancePoint * dureeVol) / distanceVol;
        return h_DepartVol * 60 + min_DepartVol + dureePoint;
    }
}