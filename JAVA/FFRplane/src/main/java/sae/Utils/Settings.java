/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.utils;

/**
 *
 * @author mathe
 */
public class Settings {
    private static int kmax; // =0 Initialement : Signifie qu'il n'y a pas de limite de couleur
    private static int safetyMargin = 15;
    private static String airportsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\aeroports.txt";
    private static String flightsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test1.csv";

    public Settings() {}
    
    public static String getAirportsFilePath() {
        return airportsFilePath;
    }

    public static String getFlightsFilePath() {
        return flightsFilePath;
    }

    public static int getKmax() {
        return kmax;
    }

    public static int getSafetyMargin() {
        return safetyMargin;
    }

    public static void setAirportsFilePath(String airportsFilePath) {
        Settings.airportsFilePath = airportsFilePath;
    }

    public static void setFlightsFilePath(String flightsFilePath) {
        Settings.flightsFilePath = flightsFilePath;
    }

    public static void setKmax(int kmax) {
        Settings.kmax = kmax;
    }

    public static void setSafetyMargin(int safetyMargin) {
        Settings.safetyMargin = safetyMargin;
    }
}
