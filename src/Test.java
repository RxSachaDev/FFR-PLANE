import java.io.*;
import java.util.List;

import airports.Airport;
import errors.FileFormatError;
import flights.Flight;
import intersection.FlightCollisionTools;

public class Test {
    public static final String FICHIER_AEROPORTS = "./data/aeroports.txt";
    public static final String FICHIER_VOLS = "./data/vol-test4.csv";
    private static int k_max = 2;
    public static void main(String[] args) {
        ListeAeroports listeAero = new ListeAeroports();
        ListeVols listeVols = new ListeVols();
        try {
            listeAero.remplirListe(FICHIER_AEROPORTS);
            
            Airport A1 = listeAero.getAeroport("MRS");
            Airport A2 = listeAero.getAeroport("BES");
            Airport A3 = listeAero.getAeroport("LYS");
            Airport A4 = listeAero.getAeroport("BOD");
            Flight V1 = new Flight("AF000090",A1,A2,7,33,81);
            Flight V2 = new Flight("AF000132",A3,A4,7,34,47);

            System.out.println(FlightCollisionTools.hasCollision(V1,V2));

        } catch (FileNotFoundException erreur) {
            System.err.println("    > ERREUR : Impossible de traiter ce fichier !");
        } catch (FileFormatError erreur) {
            System.err.println("    > ERREUR : "+erreur);
        }

        List<Flight> listeVolsA = listeVols.getListeVols();
        for(int ii = 1 ; ii<10 ; ii++){
            String temp = "./data/vol-test"+ii+".csv";
            try {
                listeVols.remplirListe(temp, listeAero);
            } catch (Exception e) {
                // TODO: handle exception
            }
            int count = 0;
            listeVolsA = listeVols.getListeVols();
            for(int i = 0 ; i<listeVolsA.size()-1 ; i++) {
                for(int j = i+1 ; j<listeVolsA.size() ; j++){
                    if(FlightCollisionTools.hasCollision(listeVolsA.get(i), listeVolsA.get(j))) count++;
                }
            }
            
            System.out.println("    | fichier vol-test"+ii+".csv : > "+count+" < collisions");
            listeVols.viderListe();
        }
    }
}
