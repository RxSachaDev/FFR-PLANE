package airports;

import java.util.*;

/**
 * The AirportCatalog class manages a catalog of airports.
 */
public class AirportCatalog {
    private Map<String, Airport> airportMap;
  
    /**
     * Constructs a new AirportCatalog with an empty map of airports.
     */
    public AirportCatalog() {
        airportMap = new HashMap<>();
    }


    /**
     * Adds an airport to the catalog if it is not already present.
     *
     * @param airport the airport to be added
     * @return true if the airport was added, false otherwise
     */
    public boolean addAirport(Airport airport) {
        boolean isAdded = false;
        if (airportMap.get(airport.getCode()) == null) {
            airportMap.put(airport.getCode(), airport);
            isAdded = true;
        }
        return isAdded;
    }


    /**
     * Gets an airport from the catalog by its code.
     *
     * @param code the code of the airport
     * @return the airport with the specified code, or null if not found
     */
    public Airport getAirport(String code) {
        return airportMap.get(code);
    }


    /**
     * Displays all airports in the catalog and the total count.
     */
    public void displayAirports() {
        int count = 0;
        for (String key : airportMap.keySet()) {
            System.out.println(airportMap.get(key));
            count++;
        }
        System.out.println("###### " + count + " ######");
    }
}
