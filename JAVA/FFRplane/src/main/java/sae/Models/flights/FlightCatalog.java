package sae.models.flights;

import java.util.*;

/**
 * The FlightCatalog class manages a catalog of flights.
 */
public class FlightCatalog {
    private List<Flight> flightList;

    /**
     * Constructs a new FlightCatalog with an empty list of flights.
     */
    public FlightCatalog() {
        flightList = new ArrayList<Flight>();
    }


    /**
     * Adds a flight to the catalog if it is not already present.
     *
     * @param flight the flight to be added
     * @return true if the flight was added, false otherwise
     */
    public boolean addFlight(Flight flight) {
        boolean isAdded = false;
        if (flight != null && !flightList.contains(flight)) {
            isAdded = flightList.add(flight);
        }
        return isAdded;
    }


    /**
     * Displays all flights in the catalog.
     */
    public void displayFlights() {
        for (int i = 0; i < flightList.size(); i++) {
            System.out.println(flightList.get(i));
        }
    }

    
    /**
     * Clears all flights from the catalog.
     */
    public void clearCatalog() {
        flightList.clear();
    }


    /**
     * Gets the list of flights in the catalog.
     *
     * @return the list of flights
     */
    public List<Flight> getFlights() {
        return flightList;
    }
}
