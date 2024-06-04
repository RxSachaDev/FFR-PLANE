/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Models;

/**
 *
 * @author mathe
 */
public class Settings {
    private static int kmax = 0;
    private static String airportsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\aeroports.txt";
    private static String flightsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test";

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

    public static void setAirportsFilePath(String airportsFilePath) {
        Settings.airportsFilePath = airportsFilePath;
    }

    public static void setFlightsFilePath(String flightsFilePath) {
        Settings.flightsFilePath = flightsFilePath;
    }

    public static void setKmax(int kmax) {
        Settings.kmax = kmax;
    }
}
