package sae.utils;

import java.util.Date;

/**
 * La classe Settings définit les paramètres globaux de l'application.
 * Ces paramètres comprennent la limite de couleurs (kmax), 
 * la marge de sécurité pour la détection de collisions, 
 * ainsi que les chemins des fichiers d'aéroports et de vols.
 * Elle offre des méthodes pour définir et obtenir ces paramètres.
 * Elle contient certains parametres parmettant d'affiner l'affichage des vols.
 * L'activation de l'easter egg est gérée par un booléen.
 * Les chemins par défaut des fichiers sont initialisés à partir du répertoire de travail de l'utilisateur.
 * 
 * Les variables statiques suivantes sont définies :
 * - {@code kmax} : Limite de couleurs pour l'affichage des vols.
 * - {@code safetyMargin} : Marge de sécurité en minutes pour la détection de collisions.
 * - {@code refiningColor} : Couleur d'affinage pour l'affichage des vols. La valeur par défaut est 0, indiquant aucun affinage.
 * - {@code refiningStartTimeRange} : Heure de début pour l'affinage de l'affichage des vols.
 * - {@code refiningEndTimeRange} : Heure de fin pour l'affinage de l'affichage des vols.
 *
 * @author mathe
 */
public class Settings {
    
    private static String airportsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\aeroports.txt";
    private static String flightsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test8.csv";
    private static final String graphTestPath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\test\\graph-test0.txt";
    private static final String graphTEMPPath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\temp\\graph-testTEMP.txt";
    
    private static int kmax = 0;
    private static int safetyMargin = 15;
    
    private static int refiningColor = 0;
    private static Date refiningStartTimeRange = null;
    private static Date refiningEndTimeRange = null;
    private static String refiningAirportCode = null;
    
    private static final boolean easterEggActivated = true;
    
    
    /* ••••••••••••• MÉTHODES ••••••••••••• */
    
    
    /**
     * Réinitialise tous les paramètres à leurs valeurs par défaut.
     */
    public static void resetAll(){
        safetyMargin = 15;
        kmax = 0;
        refiningColor = 0;
        refiningStartTimeRange = null;
        refiningEndTimeRange = null;
        refiningAirportCode = null;
    }
    
    /**
     * Vérifie si aucun paramètre n'a été configuré.
     * <p>
     * Cette méthode retourne true si la carte des paramètres est nulle, vide, ou
     * si tous les paramètres de la carte sont soit nuls, soit des chaînes vides.
     * </p>
     *
     * @return true si aucun paramètre n'a été configuré, false sinon
     */
    public static boolean noSettingsSet(){
        if(refiningColor == 0 && refiningStartTimeRange == null && refiningAirportCode == null) return true;
        return false;
    }
    
    
    /* ••••••••••••• GETTERS / SETTERS ••••••••••••• */
    
    
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
    
    
    /**
     * Définit l'heure de début pour l'affinage.
     *
     * @param refiningStartTimeRange l'heure de début pour l'affinage
     */
    public static void setRefiningStartTimeRange(Date refiningStartTimeRange) {
        Settings.refiningStartTimeRange = refiningStartTimeRange;
    }
    
    
    /**
     * Définit l'heure de fin pour l'affinage.
     *
     * @param refiningEndTimeRange l'heure de fin pour l'affinage
     */
    public static void setRefiningEndTimeRange(Date refiningEndTimeRange) {
        Settings.refiningEndTimeRange = refiningEndTimeRange;
    }


    /**
     * Définit la couleur d'affinage pour l'affichage des vols.
     *
     * @param refiningColor la couleur d'affinage pour l'affichage des vols
     */
    public static void setRefiningColor(int refiningColor) {
        Settings.refiningColor = refiningColor;
    }


    /**
     * Définit le code d'aéroport utilisé pour l'affinage de l'affichage des vols.
     *
     * @param refiningAirportCode le nouveau code d'aéroport à utiliser pour l'affinage
     */
    public static void setRefiningAirportCode(String refiningAirportCode) {
        Settings.refiningAirportCode = refiningAirportCode;
    }

    
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
     * Renvoie le chemin vers le fichier de test du graphe.
     *
     * @return le chemin vers le fichier de test du graphe
     */
    public static String getGraphTestPath() {
        return graphTestPath;
    }

    
    /**
     * Renvoie le chemin vers le fichier graph-testTEMP actualisé automatiquement 
     * à chaque chargement d'un espace aérien.
     *
     * @return le chemin vers le fichier graph-testTEMP
     */
    public static String getGraphTEMPPath() {
        return graphTEMPPath;
    }
    

    /**
     * Renvoie l'heure de début pour l'affinage.
     *
     * @return l'heure de début pour l'affinage
     */
    public static Date getRefiningStartTimeRange() {
        return refiningStartTimeRange;
    }

    
    /**
     * Renvoie l'heure de fin pour l'affinage.
     *
     * @return l'heure de fin pour l'affinage
     */
    public static Date getRefiningEndTimeRange() {
        return refiningEndTimeRange;
    }
    

    /**
     * Renvoie la couleur d'affinage pour l'affichage des vols.
     *
     * @return la couleur d'affinage pour l'affichage des vols
     */
    public static int getRefiningColor() {
        return refiningColor;
    }

    
    /**
     * Renvoie le code d'aéroport utilisé pour l'affinage de l'affichage des vols.
     *
     * @return le code d'aéroport utilisé pour l'affinage de l'affichage des vols
     */
    public static String getRefiningAirportCode() {
        return refiningAirportCode;
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
     * Vérifie si l'easter egg est activé.
     *
     * @return true si l'easter egg est activé, false sinon
     */
    public static boolean isEasterEggActivated(){
        return easterEggActivated;
    }
}
