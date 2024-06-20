package sae.tests;

import sae.exceptions.FileFormatException;
import java.io.*;
import java.util.List;
import sae.utils.Settings;

import sae.models.airports.*;
import sae.models.flights.*;
import sae.models.intersection.*;
import sae.models.toolbox.*;

/**
 * 
 * @author mathe
 */

public class Test {
    
    public static void main(String[] args) {
        AirportCatalog airportCatalog = new AirportCatalog();
        FlightCatalog flightCatalog = new FlightCatalog();
        try {
            ToolBox.fillAirportsCatalog(Settings.getAirportsFilePath(), airportCatalog);
            
            Airport A1 = airportCatalog.getAirport("MRS");
            System.out.println(A1.getCoordinates()[0]+","+A1.getCoordinates()[1]);
            System.out.println(A1.getGeoPosition());
            
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
        } catch (FileFormatException erreur) {
            System.err.println("    > ERREUR : "+erreur);
        }
    }

    
    public static void flightsTestFilesResult(FlightCatalog flightCatalog,AirportCatalog airportCatalog){
        for(int ii = 1 ; ii<10 ; ii++){
            String temp = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test"+ii+".csv";
            String temp2 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\aeroports.txt";
            try {
                ToolBox.fillFlightsCatalog(temp2,temp,flightCatalog,airportCatalog);
            } catch (FileNotFoundException e) {}
            int count = 0;
            List<Flight> flightsList = flightCatalog.getFlights();
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


