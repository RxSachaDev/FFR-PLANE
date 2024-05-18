
//Penser à integrer les méthodes directement à la classe Vol ou ListeVols
public class Collision {
    private static boolean isBetweenInterval(Vol vol, double x, double y){
        boolean bool = false;
        double[] vol_Depart = vol.getAero_depart().getCoordonnees();
        double[] vol_Arrivee = vol.getAero_arrivee().getCoordonnees();
    
        double max_x = Math.max(vol_Depart[0], vol_Arrivee[0]);
        double min_x = Math.min(vol_Depart[0], vol_Arrivee[0]);
        double max_y = Math.max(vol_Depart[1], vol_Arrivee[1]);
        double min_y = Math.min(vol_Depart[1], vol_Arrivee[1]);

        if(min_x <= x && x <= max_x && min_y <= y && y <= max_y) bool = true;

        // System.out.println("        | min_x : "+min_x);
        // System.out.println("        | x : "+x);
        // System.out.println("        | max_x : "+max_x);
        // System.out.println("        | min_y : "+min_y);
        // System.out.println("        | y : "+y);
        // System.out.println("        | max_y : "+max_y);

        return bool;
    }
    
    private static double[] pointIntersection(Vol volA, Vol volB){
        double[] point = new double[2];
        point[0] = 7000;

        double[] cooArriveeA = volA.getAero_arrivee().getCoordonnees();
        double[] cooDepartA = volA.getAero_depart().getCoordonnees();
        double[] cooArriveeB = volB.getAero_arrivee().getCoordonnees();
        double[] cooDepartB = volB.getAero_depart().getCoordonnees();

        double mA = Double.POSITIVE_INFINITY;  // Coef directeur de la droite A
        mA = (cooArriveeA[1] - cooDepartA[1]) / (cooArriveeA[0] - cooDepartA[0]); // Si la droite n'est pas verticale
        double pA = cooArriveeA[1] - mA * cooArriveeA[0];

        double mB = Double.POSITIVE_INFINITY;  // Coef directeur de la droite B
        mB = (cooArriveeB[1] - cooDepartB[1]) / (cooArriveeB[0] - cooDepartB[0]);
        double pB = cooArriveeB[1] - mB * cooArriveeB[0];

        double x=Double.NaN, y=Double.NaN;

        if (mA == mB && pA == pB) { // Cas où les droites sont identiques
            if (isBetweenInterval(volA, cooDepartB[0], cooDepartB[1]) || isBetweenInterval(volA, cooArriveeB[0], cooArriveeB[1])){
                //Si les segment sont egaux V1 = A1=>A2 et V2 = A2=>A1
                if(volA.getAero_depart()==volB.getAero_arrivee() && volB.getAero_depart()==volA.getAero_arrivee()) {
                    //Normalement c'est bon Ici
                    int tempsMinA = volA.getH_depart()*60+volA.getMin_depart();
                    int tempsMinB = volB.getH_depart()*60+volB.getMin_depart();
                    Vol volSelect = volA;
                    if(volB.getH_depart()<volA.getH_depart() || volB.getMin_depart()<volB.getMin_depart()) volSelect=volB;
                    if(Math.abs(tempsMinA-tempsMinB)>volSelect.getDuree()+15) {
                        point[0] = point[1]=Double.POSITIVE_INFINITY;
                        System.out.println("YYYYYYYYYY");
                        return point; //Infinité d'intersection
                    }
                } else if (isBetweenInterval(volA, cooDepartB[0], cooDepartB[1])) { //Normalement c'est bon Ici
                    point[0] = cooDepartB[0];
                    point[1] = cooDepartB[1];
                    System.out.println("ZZZZZZZZZZA");
                    return point;
                } else { 
                    point[0] = cooArriveeB[0];
                    point[1] = cooArriveeB[1];
                    System.out.println("ZZZZZZZZZZB");
                    return point;
                }
            }
        } else if (Double.isFinite(mA) && Double.isFinite(mB)) { //Cas classique
            System.out.println("AAAAAAAAAA");
            x = -(pA - pB) / (mA - mB); // Abscisse du point d'intersection
            y = mA * x + pA; // Ordonnée du point d'intersection
        } else if (Double.isInfinite(mA) && Double.isFinite(mB)) { //Cas ou A est verticale 
            System.out.println("BBBBBBBBBB");
            x = cooDepartA[0]; // La droite A est verticale
            y = mB * x + pB;
            y = Math.round(y * 1000.0)/1000.0; //Arrondis au millième
        } else if (Double.isInfinite(mB) && Double.isFinite(mA)) { //Cas ou B est verticale
            System.out.println("CCCCCCCCCC");
            x = cooDepartB[0]; // La droite B est verticale
            y = mA * x + pA;
            y = Math.round(y * 1000.0)/1000.0; //Arrondis au millième
        } else { //Cas ou A et B sont paralleles (disjointes)
            System.out.println("DDDDDDDDDD");
            // Les deux droites sont verticales, pas d'intersection possible
            return point;
        }

        if (isBetweenInterval(volA, x, y) && isBetweenInterval(volB, x, y)) { // Si le point d'intersection est dans l'intervalle des segments
            point[0] = x;
            point[1] = y;
        }

        // System.out.println("    | cooArriveeA : "+cooArriveeA[0]+" , "+cooArriveeA[1]);
        // System.out.println("    | cooDepartA : "+cooDepartA[0]+" , "+cooDepartA[1]);
        // System.out.println("    | cooArriveeB : "+cooArriveeB[0]+" , "+cooArriveeB[1]);
        // System.out.println("    | cooDepartB : "+cooDepartB[0]+" , "+cooDepartB[1]);
        // System.out.println("    | mA : "+mA);
        // System.out.println("    | pA : "+pA);
        // System.out.println("    | mB : "+mB);
        // System.out.println("    | pB : "+pB);
        // System.out.println("    | x : "+x);
        // System.out.println("    | y : "+y);
        // System.out.println("    | isBetweenInterval(volA, x, y) : "+isBetweenInterval(volA, x, y));
        // System.out.println("    | isBetweenInterval(volB, x, y) : "+isBetweenInterval(volB, x, y));

        return point;
    }
    
    public static boolean enCollision(Vol volA, Vol volB){
        boolean bool = false;
        double[] point = pointIntersection(volA, volB);
        if(Double.isInfinite(point[0])) return true;
        if(point[0] != 7000){ // Si un point d'intersection existe
            double distancePoint_volA = ToolBox.distance(volA.getAero_depart().getCoordonnees(), point);
            double heurePoint_volA = tempsVol(volA.getDistanceVol(), distancePoint_volA, volA.getDuree(), volA.getH_depart(), volA.getMin_depart());
            double distancePoint_volB = ToolBox.distance(volB.getAero_depart().getCoordonnees(), point);
            double heurePoint_volB = tempsVol(volB.getDistanceVol(), distancePoint_volB, volB.getDuree(), volB.getH_depart(), volB.getMin_depart());

            if(ecartHoraire(heurePoint_volA, heurePoint_volB) < 15.0) bool = true;
            
            // System.out.println("    | Vols : "+volA.getNumeroVol()+" - "+volB.getNumeroVol());
            // System.out.println("    | Point d'intersection : (" + point[0] + "," + point[1] + ")");
            // System.out.println("    | Intervalle : "+heurePoint_volA+" - "+heurePoint_volB+" = "+(heurePoint_volA-heurePoint_volB));
            // System.out.println("    | EcartHoraire : "+ecartHoraire(heurePoint_volA, heurePoint_volB));
            // System.out.println("    | Collision : "+bool+"\n");
        }
        return bool;
    }

    public static double tempsVol(double distanceVol, double distancePoint, double dureeVol, int h_DepartVol, int min_DepartVol) {
        // Simple produit en croix
        double dureePoint = (distancePoint * dureeVol) / distanceVol;
        return h_DepartVol * 60 + min_DepartVol + dureePoint;
    }

    public static double ecartHoraire(double horaireMinutes1,double horaireMinutes2){
        double difference = Math.abs(horaireMinutes1 - horaireMinutes2);
        double difference_avec_minuit = 1440 - difference;  // 1440 minutes dans une journée
        return Math.min(difference, difference_avec_minuit);
    }
}