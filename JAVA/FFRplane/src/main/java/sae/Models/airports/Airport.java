package sae.models.airports;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import org.jxmapviewer.viewer.GeoPosition;

import sae.controller.Interfaces.ModelPoint;

/**
 * La classe Airport représente un aéroport avec son code, sa localisation et ses coordonnées.
 * Elle fournit des méthodes pour calculer les coordonnées cartésiennes et des accesseurs et mutateurs pour ses champs.
 * 
 * @author mathe
 */
public class Airport implements ModelPoint{
    
    private String code;
    private String location;
    
    /**
     * Coordonnées géographique d'un aéroport
     */
    private GeoPosition geoPosition;
    
    /**
     * Coordonnées cartésiennes des l'aeroport.
     * Où coordinates[0] = x et coordinates[1] = y
     */
    private double[] coordinates; // 

    /**
     * Constructeur pour instancier un Airport avec des informations de localisation détaillées.
     *
     * @param code le code de l'aéroport
     * @param location la localisation de l'aéroport
     * @param latDegree degré de latitude
     * @param latMinute minute de latitude
     * @param latSecond seconde de latitude
     * @param latOrientation orientation de latitude ('N', 'S', 'E', 'W')
     * @param longDegree degré de longitude
     * @param longMinute minute de longitude
     * @param longSecond seconde de longitude
     * @param longOrientation orientation de longitude ('N', 'S', 'E', 'W')
     */
    public Airport(String code, String location, int latDegree, int latMinute, int latSecond, char latOrientation, 
                   int longDegree, int longMinute, int longSecond, char longOrientation) {
        this.code = code;
        this.location = location;
        geoPosition = calGeoPosition(latDegree,latMinute,latSecond,latOrientation,
                                     longDegree,longMinute,longSecond,longOrientation);
        coordinates = calCartesianCoordinates();
    }


    /**
     * Constructeur pour instancier un Airport avec un code et des coordonnées géographiques.
     *
     * @param code le code de l'aéroport
     * @param geoPosition
     */
    public Airport(String code, GeoPosition geoPosition) {
        this.code = code;
        this.geoPosition = geoPosition;
    }


    /* ••••••••••••• MÉTHODES ••••••••••••• */
    
    
    /**
     * Calcule les coordonnées cartésiennes de l'aéroport.
     *
     * @return les coordonnées cartésiennes sous forme d'un tableau double où coordinates[0] = x et coordinates[1] = y
     */
    public double[] calCartesianCoordinates() { 
        int R = 6371; // Rayon de la Terre (km) 
        double[] result = new double[2];
        result[0] = R * cos(toRadians(geoPosition.getLatitude())) * sin(toRadians(geoPosition.getLongitude()));
        result[1] = R * cos(toRadians(geoPosition.getLatitude())) * cos(toRadians(geoPosition.getLongitude()));
        return result;
    }

    
    /**
     * Calcule la position géographique à partir des degrés, minutes, secondes et orientations fournies.
     *
     * @param latDegree degré de latitude
     * @param latMinute minute de latitude
     * @param latSecond seconde de latitude
     * @param latOrientation orientation de latitude ('N', 'S')
     * @param longDegree degré de longitude
     * @param longMinute minute de longitude
     * @param longSecond seconde de longitude
     * @param longOrientation orientation de longitude ('E', 'W')
     * @return la position géographique calculée
     */
    public GeoPosition calGeoPosition(int latDegree, int latMinute, int latSecond, 
            char latOrientation,int longDegree, int longMinute, int longSecond, char longOrientation){
        int coef_lat = 1,coef_long = 1;
        if(latOrientation == 'O' || latOrientation == 'S') coef_lat = -1;
        if(longOrientation == 'O' || longOrientation == 'S') coef_long = -1;
        double latitude = coef_lat * (latDegree+latMinute/60.0+latSecond/3600.0);
        double longitude = coef_long * (longDegree+longMinute/60.0+longSecond/3600.0);
        return new GeoPosition(latitude, longitude);
    }

    
    /* ••••••••••••• GETTERS / SETTERS ••••••••••••• */
    
    
    /**
     * Définit le code de l'aéroport.
     *
     * @param code le nouveau code de l'aéroport
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * Définit la localisation de l'aéroport.
     *
     * @param location la nouvelle localisation de l'aéroport
     */
    public void setLocation(String location) {
        this.location = location;
    }

    
    /**
     * Définit les coordonnées cartésiennes de l'aéroport.
     *
     * @param coordinates les nouvelles coordonnées cartésiennes de l'aéroport
     */
    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }


    /**
     * Obtient le code de l'aéroport.
     *
     * @return le code de l'aéroport
     */
    public String getCode() {
        return code;
    }


    /**
     * Obtient la localisation de l'aéroport.
     *
     * @return la localisation de l'aéroport
     */
    public String getLocation() {
        return location;
    }


    /**
     * Obtient les coordonnées cartésiennes de l'aéroport.
     *
     * @return les coordonnées cartésiennes de l'aéroport
     */
    public double[] getCoordinates() {
        return coordinates;
    }


    /* ••••••••••••• MÉTHODES @OVERRIDE ••••••••••••• */
    

    /**
     * Retourne une représentation en chaîne de caractères de l'objet Airport.
     *
     * @return une représentation en chaîne de caractères de l'objet Airport
     */
    @Override
    public String toString() {
        return ("AEROPORT \n"+
                "• Code : "+code+"\n"+ 
                "• Ville : "+location+"\n"+
                "• Latitude : "+Math.round(geoPosition.getLatitude()*1000)/1000.0+"\n"+
                "• Longitude : "+Math.round(geoPosition.getLongitude()*1000)/1000.0);
    }

    
    /**
     * Obtient la position géographique de l'aéroport.
     *
     * @return la position géographique de l'aéroport
     */
    @Override
    public GeoPosition getGeoPosition() {
        return geoPosition;
    }   
    
    
    /**
     * Retourne le nom de la localisation de l'aéroport.
     *
     * @return le nom de la localisation de l'aéroport
     */
    @Override
    public String toStringName(){
        return location;
    }

    
    /**
     * Retourne le code de l'aéroport.
     *
     * @return le code de l'aéroport
     */
    @Override
    public String getModelPointCode() {
        return code;
    }
}
