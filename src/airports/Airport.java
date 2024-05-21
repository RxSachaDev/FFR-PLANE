package airports;

/**
 * The Airport class represents an airport with its code, location, and coordinates.
 * It provides methods to calculate Cartesian coordinates and accessors and mutators for its fields.
 */
public class Airport {
    private String code;
    private String location;

    private int latDegree;
    private int latMinute;
    private int latSecond;
    private char latOrientation;

    private int longDegree;
    private int longMinute;
    private int longSecond;
    private char longOrientation;
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
        this.latDegree = latDegree;
        this.latMinute = latMinute;
        this.latSecond = latSecond;
        this.latOrientation = latOrientation;
        this.longDegree = longDegree;
        this.longMinute = longMinute;
        this.longSecond = longSecond;
        this.longOrientation = longOrientation;
        this.coordinates = calCartesianCoordinates();
    }



    /**
     * Calculates the Cartesian coordinates of the airport.
     *
     * @return the Cartesian coordinates as a double array where coordinates[0] = x and coordinates[1] = y
     */
    public double[] calCartesianCoordinates() {
        int R = 6371; // Radius of the Earth (km)
        int latCoefficient = 1;
        int longCoefficient = 1;
        double[] result = new double[2];
        
        if (latOrientation == 'O' || latOrientation == 'S') {
            latCoefficient = -1;
        }
        if (longOrientation == 'O' || latOrientation == 'S') {
            longCoefficient = -1;
        }
        
        double latitude = Math.toRadians(latCoefficient * (latDegree + latMinute / 60.0 + latSecond / 3600.0));
        double longitude = Math.toRadians(longCoefficient * (longDegree + longMinute / 60.0 + longSecond / 3600.0));
        
        result[0] = R * Math.cos(latitude) * Math.sin(longitude);
        result[1] = R * Math.cos(latitude) * Math.cos(longitude);
        
        return result;
    }



    /**
     * Returns a string representation of the Airport object.
     *
     * @return a string representation of the Airport object
     */
    @Override
    public String toString() {
        return String.format("| %s | %d | %d | %d | %c | %d | %d | %d | %c |", 
                              code, latDegree, latMinute, latSecond, latOrientation, 
                              longDegree, longMinute, longSecond, longOrientation);
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
}
