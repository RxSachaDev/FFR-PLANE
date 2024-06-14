package sae.models.flights;

import java.util.*;

/**
 * La classe FlightCatalog gère un catalogue de vols.
 * 
 * @author mathe
 */
public class FlightCatalog {
    private List<Flight> flightList;

    /**
     * Construit un nouveau FlightCatalog avec une liste vide de vols.
     */
    public FlightCatalog() {
        flightList = new ArrayList<Flight>();
    }


    /**
     * Ajoute un vol au catalogue s'il n'est pas déjà présent.
     *
     * @param flight le vol à ajouter
     * @return true si le vol a été ajouté, false sinon
     */
    public boolean addFlight(Flight flight) {
        boolean isAdded = false;
        if (flight != null && !flightList.contains(flight)) {
            isAdded = flightList.add(flight);
        }
        return isAdded;
    }


    /**
     * Affiche tous les vols dans le catalogue.
     */
    public void displayFlights() {
        for (int i = 0; i < flightList.size(); i++) {
            System.out.println(flightList.get(i));
        }
    }

    
    /**
     * Efface tous les vols du catalogue.
     */
    public void clearCatalog() {
        flightList.clear();
    }


    /**
     * Obtient la liste des vols dans le catalogue.
     *
     * @return la liste des vols
     */
    public List<Flight> getFlights() {
        return flightList;
    }
}
