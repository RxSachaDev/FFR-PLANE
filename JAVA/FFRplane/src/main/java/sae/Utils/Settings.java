/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.utils;

/**
 * La classe Settings définit les paramètres globaux de l'application.
 * Ces paramètres comprennent la limite de couleurs (kmax), la marge de sécurité pour la détection de collisions, 
 * ainsi que les chemins des fichiers d'aéroports et de vols.
 * 
 * @author mathe
 */
public class Settings {
    private static int kmax; // =0 Initialement : Signifie qu'il n'y a pas de limite de couleur
    private static int safetyMargin = 15;
    private static String airportsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\aeroports.txt";
    private static String flightsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test1.csv";

    /**
     * Constructeur par défaut de la classe Settings.
     */
    public Settings() {}
    
    
    /**
     * Renvoie le chemin vers le fichier des aéroports.
     *
     * @return le chemin vers le fichier des aéroports
     */
    public static String getAirportsFilePath() {
        return airportsFilePath;
    }
    
    
    /**
     * Renvoie le chemin vers le fichier des vols.
     *
     * @return le chemin vers le fichier des vols
     */
    public static String getFlightsFilePath() {
        return flightsFilePath;
    }
    
    
    /**
     * Renvoie la limite de couleurs (kmax).
     *
     * @return la limite de couleurs (kmax)
     */
    public static int getKmax() {
        return kmax;
    }
    
    
    /**
     * Renvoie la marge de sécurité pour la détection de collisions.
     *
     * @return la marge de sécurité pour la détection de collisions
     */
    public static int getSafetyMargin() {
        return safetyMargin;
    }
    
    
    /**
     * Définit le chemin vers le fichier des aéroports.
     *
     * @param airportsFilePath le chemin vers le fichier des aéroports
     */
    public static void setAirportsFilePath(String airportsFilePath) {
        Settings.airportsFilePath = airportsFilePath;
    }
    
    
    /**
     * Définit le chemin vers le fichier des vols.
     *
     * @param flightsFilePath le chemin vers le fichier des vols
     */
    public static void setFlightsFilePath(String flightsFilePath) {
        Settings.flightsFilePath = flightsFilePath;
    }
    
    
    /**
     * Définit la limite de couleurs (kmax).
     *
     * @param kmax la limite de couleurs (kmax)
     */
    public static void setKmax(int kmax) {
        Settings.kmax = kmax;
    }
    
    
    /**
     * Définit la marge de sécurité pour la détection de collisions.
     *
     * @param safetyMargin la marge de sécurité pour la détection de collisions
     */
    public static void setSafetyMargin(int safetyMargin) {
        Settings.safetyMargin = safetyMargin;
    }
}
