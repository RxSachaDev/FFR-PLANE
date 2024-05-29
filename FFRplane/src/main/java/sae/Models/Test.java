package sae.Models;

import java.io.*;
import java.util.List;

import sae.Models.airports.*;
import sae.Models.errors.*;
import sae.Models.flights.*;
import sae.Models.intersection.*;
import sae.Models.toolbox.*;

public class Test {
    public static final String FICHIER_AEROPORTS = "C:\\Users\\mathe\\OneDrive\\Bureau\\FFRplane\\src\\main\\java\\data\\aeroports.txt";
    public static final String FICHIER_VOLS = "C:\\Users\\mathe\\OneDrive\\Bureau\\FFRplane\\src\\main\\java\\data\\vol-test4.csv";
    private static int k_max = 2;
    public static void main(String[] args) {
        AirportCatalog airportCatalog = new AirportCatalog();
        FlightCatalog flightCatalog = new FlightCatalog();
        try {
            FileTreatment.fillAirportList(FICHIER_AEROPORTS, airportCatalog);
            
            Airport A1 = airportCatalog.getAirport("MRS");
            System.out.println(A1.getCoordinates());
            
            Airport A2 = airportCatalog.getAirport("BES");
            Airport A3 = airportCatalog.getAirport("LYS");
            Airport A4 = airportCatalog.getAirport("BOD");
            Flight V1 = new Flight("AF000090",A1,A2,7,33,81);
            Flight V2 = new Flight("AF000132",A3,A4,7,34,47);
            System.out.println(FlightCollisionTools.hasCollision(V1,V2));
            flightsTestFilesResult(flightCatalog,airportCatalog);
            //airportCatalog.displayAirports();
            

        } catch (FileNotFoundException erreur) {
            System.err.println("    > ERREUR : Impossible de traiter ce fichier !");
        } catch (FileFormatError erreur) {
            System.err.println("    > ERREUR : "+erreur);
        }
    }

    public static void flightsTestFilesResult(FlightCatalog flightCatalog,AirportCatalog airportCatalog){
        List<Flight> flightsList = flightCatalog.getFlightList();
        for(int ii = 1 ; ii<10 ; ii++){
            String temp = "C:\\Users\\mathe\\OneDrive\\Bureau\\FFRplane\\src\\main\\java\\data\\vol-test"+ii+".csv";
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


