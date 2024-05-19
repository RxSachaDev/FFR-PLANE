
//Penser à integrer les méthodes directement à la classe Vol ou ListeVols
public class Collision {
    private static boolean isOnFlightSegment(double x, double y, Vol vol) {
        boolean bool = false;
        double[] cooDepart = vol.getAero_depart().getCoordonnees();
        double[] cooArrivee = vol.getAero_arrivee().getCoordonnees();
    
        double max_x = Math.max(cooDepart[0], cooArrivee[0]);
        double min_x = Math.min(cooDepart[0], cooArrivee[0]);
        double max_y = Math.max(cooDepart[1], cooArrivee[1]);
        double min_y = Math.min(cooDepart[1], cooArrivee[1]);

        if(min_x <= x && x <= max_x && min_y <= y && y <= max_y) bool = true;

        return bool;
    }
 
    public static boolean hasCollision(Vol V1, Vol V2) {
        Aeroport A1 = V1.getAero_depart();
        Aeroport A2 = V1.getAero_arrivee();
        Aeroport A3 = V2.getAero_depart();
        Aeroport A4 = V2.getAero_arrivee();     
        
        if (A1 == A4 && A2 == A3){ // TAG 3
            if (Math.abs(V1.getHoraireDepart() - V2.getHoraireDepart()) < 15) return true;
            
            Vol firstVolToGo = V1;
            Vol secondVolToGo = V2;
            if(firstVolToGo.getHoraireDepart() > secondVolToGo.getHoraireDepart()) {
                firstVolToGo = V2;
                secondVolToGo = V1;
            }
            
            if (secondVolToGo.getHoraireDepart() - firstVolToGo.getHoraireArrivee() < 15) return true;

            return false;     
        } 
        if (A1 == A3 && (Math.abs(V1.getHoraireDepart() - V2.getHoraireDepart()) < 15)) { // TAG 1
            return true;
        } 
        if (A2 == A4 && (Math.abs(V1.getHoraireArrivee() - V2.getHoraireArrivee()) < 15)) { // TAG 2
            return true;
        } 
        if (A1 == A4 && (Math.abs(V1.getHoraireDepart() - V2.getHoraireArrivee()) < 15)) { // TAG 4
            return true;
        } 
        if (A2 == A3 && (Math.abs(V1.getHoraireArrivee() - V2.getHoraireDepart()) < 15)) { // TAG 5
            return true;
        } else { // TAG 6
            Boolean bool = false;
            double[] point = calIntersectionPoint(V1, V2);
            if(point[0] != 7000){ // Si un point d'intersection existe
                double distancePoint_volA = ToolBox.distance(V1.getAero_depart().getCoordonnees(), point);
                double heurePoint_volA = calHourAtPoint(V1, distancePoint_volA);
                double distancePoint_volB = ToolBox.distance(V2.getAero_depart().getCoordonnees(), point);
                double heurePoint_volB = calHourAtPoint(V2, distancePoint_volB);

                if(timeDifference(heurePoint_volA, heurePoint_volB) < 15.0) bool = true;
            }
            return bool;
        }
    }

    private static double[] calIntersectionPoint(Vol V1, Vol V2){
        double[] point = new double[2];
        point[0] = point[1] = 7000;

        double[] cooArrivee1 = V1.getAero_arrivee().getCoordonnees();
        double[] cooDepart1 = V1.getAero_depart().getCoordonnees();
        double[] cooArrivee2 = V2.getAero_arrivee().getCoordonnees();
        double[] cooDepart2 = V2.getAero_depart().getCoordonnees();

        double m1 = (cooArrivee1[1] - cooDepart1[1]) / (cooArrivee1[0] - cooDepart1[0]); 
        double p1 = cooArrivee1[1] - m1 * cooArrivee1[0];

        double m2 = (cooArrivee2[1]-cooDepart2[1])/(cooArrivee2[0]-cooDepart2[0]);
        double p2 = cooArrivee2[1] - m2 * cooArrivee2[0]; 

        double x = Double.NaN, y = Double.NaN;
        
        x = -(p1 - p2) / (m1 - m2); // Abscisse du point d'intersection
        y = m1 * x + p1; // Ordonnée du point d'intersection
        
        if (isOnFlightSegment(x,y,V1) && isOnFlightSegment(x,y,V2)) {
            point[0] = x;
            point[1] = y;
        }
        return point;
    }

    /*    
    private static double[] calPointIntersection(Vol V1, Vol V2){
        double[] point = new double[2];
        point[0] = point[1] = 7000;

        double[] cooArrivee1 = V1.getAero_arrivee().getCoordonnees();
        double[] cooDepart1 = V1.getAero_depart().getCoordonnees();
        double[] cooArrivee2 = V2.getAero_arrivee().getCoordonnees();
        double[] cooDepart2 = V2.getAero_depart().getCoordonnees();

        double m1 = Double.POSITIVE_INFINITY;  // Coef directeur de la droite 1 (initialisé à infini pour les cas des verticales)
        double p1 = cooDepart1[0]; //initialisé à x pour le cas des verticales
        if(cooArrivee1[0] - cooDepart1[0] != 0) {
            m1 = (cooArrivee1[1] - cooDepart1[1]) / (cooArrivee1[0] - cooDepart1[0]);
            p1 = cooArrivee1[1] - m1 * cooArrivee1[0];
        }
        double m2 = Double.POSITIVE_INFINITY;  // Coef directeur de la droite 2 (initialisé à infini pour les cas des verticales)
        double p2 = cooDepart2[0]; //initialisé à x pour le cas des verticales
        if(cooArrivee2[0] - cooDepart2[0] != 0) {
            m2=(cooArrivee2[1]-cooDepart2[1])/(cooArrivee2[0]-cooDepart2[0]);
            p2 = cooArrivee2[1] - m2 * cooArrivee2[0];
        }
        
        double x = Double.NaN, y = Double.NaN;
        
        if (m1 == m2) {
            if(p1 != p2) { //Cas ou les droites sont paralleles
                return point;
            } else { //Cas ou les droites sont confondues (mais les segments ne sont pas égaux)
                if (isOnFlightSegment(cooDepart2[0], cooDepart2[1],V1)) {
                    point[0] = cooDepart2[0];
                    point[1] = cooDepart2[1];
                } else if (isOnFlightSegment(cooArrivee2[0],cooArrivee2[1],V1)){ 
                    point[0] = cooArrivee2[0];
                    point[1] = cooArrivee2[1];
                }
                return point;
            }

        } else if (Double.isFinite(m1) && Double.isFinite(m2)) { //Cas classique
            x = -(p1 - p2) / (m1 - m2); // Abscisse du point d'intersection
            y = m1 * x + p1; // Ordonnée du point d'intersection

        } else if (Double.isInfinite(m1) && Double.isFinite(m2)) { //La droite 1 est verticale 
            x = cooDepart1[0];
            y = m2 * x + p2;
            y = Math.round(y * 1000.0)/1000.0; //Arrondis au millième

        } else if (Double.isInfinite(m2) && Double.isFinite(m1)) { //La droite 2 est verticale
            x = cooDepart2[0]; 
            y = m1 * x + p1;
            y = Math.round(y * 1000.0)/1000.0; //Arrondis au millième
        }

        if (isOnFlightSegment(x,y,V1) && isOnFlightSegment(x,y,V2)) {
            point[0] = x;
            point[1] = y;
        }

        // System.out.println("    | cooArriveeA : "+cooArrivee1[0]+" , "+cooArrivee1[1]);
        // System.out.println("    | cooDepartA : "+cooDepart1[0]+" , "+cooDepart1[1]);
        // System.out.println("    | cooArriveeB : "+cooArrivee2[0]+" , "+cooArrivee2[1]);
        // System.out.println("    | cooDepartB : "+cooDepart2[0]+" , "+cooDepart2[1]);
        // System.out.println("    | mA : "+m1);
        // System.out.println("    | pA : "+p1);
        // System.out.println("    | mB : "+m2);
        // System.out.println("    | pB : "+p2);
        // System.out.println("    | x : "+x);
        // System.out.println("    | y : "+y);
        // System.out.println("    | isBetweenInterval(volA, x, y) : "+isOnFlightSegment(x, y,V1));
        // System.out.println("    | isBetweenInterval(volB, x, y) : "+isOnFlightSegment(x, y,V2));
        return point;
    }
*/
    public static double calHourAtPoint(Vol vol, double distancePoint) {
        // Simple produit en croix
        double dureePoint = (distancePoint * vol.getDuree()) / vol.getDistanceVol();
        return vol.getHoraireDepart() + dureePoint;
    }

    public static double timeDifference(double horaireMinutes1,double horaireMinutes2) {
        double difference = Math.abs(horaireMinutes1 - horaireMinutes2);
        double difference_avec_minuit = 1440 - difference;  // 1440 minutes dans une journée
        return Math.min(difference, difference_avec_minuit);
    }
}