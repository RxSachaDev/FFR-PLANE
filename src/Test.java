import java.io.*;
import java.util.List;

import airports.*;
import errors.*;
import flights.*;
import intersection.*;
import toolbox.*;

public class Test {
    public static final String FICHIER_AEROPORTS = "./data/aeroports.txt";
    public static final String FICHIER_VOLS = "./data/vol-test4.csv";
    private static int k_max = 2;
    public static void main(String[] args) {
        AirportCatalog airportCatalog = new AirportCatalog();
        FlightCatalog flightCatalog = new FlightCatalog();
        try {
            FileTreatment.fillAirportList(FICHIER_AEROPORTS, airportCatalog);
            
            // Airport A1 = listeAero.getAeroport("MRS");
            // Airport A2 = listeAero.getAeroport("BES");
            // Airport A3 = listeAero.getAeroport("LYS");
            // Airport A4 = listeAero.getAeroport("BOD");
            // Flight V1 = new Flight("AF000090",A1,A2,7,33,81);
            // Flight V2 = new Flight("AF000132",A3,A4,7,34,47);
            // System.out.println(FlightCollisionTools.hasCollision(V1,V2));
            flightsTestFilesResult(flightCatalog,airportCatalog);
            

        } catch (FileNotFoundException erreur) {
            System.err.println("    > ERREUR : Impossible de traiter ce fichier !");
        } catch (FileFormatError erreur) {
            System.err.println("    > ERREUR : "+erreur);
        }
    }

    public static void flightsTestFilesResult(FlightCatalog flightCatalog,AirportCatalog airportCatalog){
        List<Flight> flightsList = flightCatalog.getFlightList();
        for(int ii = 1 ; ii<10 ; ii++){
            String temp = "./data/vol-test"+ii+".csv";
            try {
                FileTreatment.fillFlightList(temp,flightCatalog,airportCatalog);
            } catch (Exception e) {
                // z
            }
            int count = 0;
            flightsList = flightCatalog.getFlightList();
            for(int i = 0 ; i<flightsList.size()-1 ; i++) {
                for(int j = i+1 ; j<flightsList.size() ; j++){
                    if(FlightCollisionTools.hasCollision(flightsList.get(i), flightsList.get(j))) count++;
                }
            }
            System.out.println("    | fichier vol-test"+ii+".csv : > "+count+" < collisions");
            flightCatalog.clearCatalog();
        }
    }
}


