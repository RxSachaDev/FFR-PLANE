package sae.Models.airports;

import org.jxmapviewer.viewer.GeoPosition;

/**
 * The Airport class represents an airport with its code, location, and coordinates.
 * It provides methods to calculate Cartesian coordinates and accessors and mutators for its fields.
 */
public class Airport {
    private String code;
    private String location;
    
    private GeoPosition geoPosition; //Position latitude longitude de l'aeroport
    
    private double[] coordinates; // coordinates[0] = x and coordinates[1] = y

    /**
     * Constructor to instantiate an Airport with detailed location information.
     *
     * @param code the airport code
     * @param location the location of the airport
     * @param latDegree latitude degree
     * @param latMinute latitude minute
     * @param latSecond latitude second
     * @param latOrientation latitude orientation ('N', 'S', 'E', 'W')
     * @param longDegree longitude degree
     * @param longMinute longitude minute
     * @param longSecond longitude second
     * @param longOrientation longitude orientation ('N', 'S', 'E', 'W')
     */
    public Airport(String code, String location, int latDegree, int latMinute, int latSecond, char latOrientation, 
                   int longDegree, int longMinute, int longSecond, char longOrientation) {
        this.code = code;
        this.location = location;
        this.geoPosition = new GeoPosition(latDegree, latMinute, latSecond, longDegree, longMinute, longSecond);
        this.coordinates = calculateCartesianCoordinates(latDegree,latMinute,latSecond,latOrientation,longDegree,longMinute,longSecond,longOrientation);
    }


    /**
     * Constructor to instantiate an Airport with a code and coordinates.
     *
     * @param code the airport code
     * @param geoPosition
     */
    public Airport(String code, GeoPosition geoPosition) {
        this.code = code;
        this.geoPosition = geoPosition;
    }


    /**
     * Calculates the Cartesian coordinates of the airport.
     *
     * @param latDegree
     * @param latMinute
     * @param latSecond
     * @param latOrientation
     * @param longDegree
     * @param longMinute
     * @param longSecond
     * @param longOrientation
     * @return the Cartesian coordinates as a double array where coordinates[0] = x and coordinates[1] = y
     */
    public double[] calculateCartesianCoordinates(int latDegree, int latMinute, int latSecond, 
            char latOrientation,int longDegree, int longMinute, int longSecond, char longOrientation) { 
        int R = 6371; //Rayon de la Terre (km) 
        int coef_lat = 1;
        int coef_long = 1;
        double[] result = new double[2];
        if(latOrientation == 'O' || latOrientation == 'S')
            coef_lat = -1;
        if(longOrientation == 'O' || longOrientation == 'S')
            coef_long = -1;
        double latitude = Math.toRadians(coef_lat*(latDegree+latMinute/60.0+latSecond/3600.0));
        double longitude = Math.toRadians(coef_long*(longDegree+longMinute/60.0+longSecond/3600.0));
        result[0] = R*Math.cos(latitude)*Math.sin(longitude);
        result[1] = R*Math.cos(latitude)*Math.cos(longitude);
        return result;
    }


    /**
     * Returns a string representation of the Airport object.
     *
     * @return a string representation of the Airport object
     */
    @Override
    public String toString() {
        return ("> AÉROPORT : \n"+
                "  - Code : "+code+"\n"+ 
                "  - Location : "+location+"\n"+
                "  - Latitude : "+Math.round(geoPosition.getLatitude()*1000)/1000.0+"\n"+
                "  - Longitude : "+Math.round(geoPosition.getLongitude()*1000)/1000.0);

    }


    /**
     * Gets the airport code.
     *
     * @return the airport code
     */
    public String getCode() {
        return code;
    }


    /**
     * Gets the location of the airport.
     *
     * @return the location of the airport
     */
    public String getLocation() {
        return location;
    }


    /**
     * Gets the Cartesian coordinates of the airport.
     *
     * @return the Cartesian coordinates of the airport
     */
    public double[] getCoordinates() {
        return coordinates;
    }


    /**
     * Sets the airport code.
     *
     * @param code the new airport code
     */
    public void setCode(String code) {
        this.code = code;
    }


    /**
     * Sets the location of the airport.
     *
     * @param location the new location of the airport
     */
    public void setLocation(String location) {
        this.location = location;
    }

    
    /**
     * Sets the Cartesian coordinates of the airport.
     *
     * @param coordinates the new Cartesian coordinates of the airport
     */
    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }
    
    
}



