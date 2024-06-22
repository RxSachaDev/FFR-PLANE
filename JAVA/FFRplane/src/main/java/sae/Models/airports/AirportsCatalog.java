package sae.models.airports;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * La classe AirportsCatalog gère un catalogue d'aéroports.
 * 
 * @author mathe
 */
public class AirportsCatalog {
    
    /**
     * Map référencant chacun des aéroports contenus, par leur code
     */
    private final Map<String, Airport> airportsMap;
  
    /**
     * Construit un nouveau AirportCatalog avec une map vide d'aéroports.
     */
    public AirportsCatalog() {
        airportsMap = new HashMap<>();
    }
    
    
    /* ••••••••••••• MÉTHODES ••••••••••••• */


    /**
     * Ajoute un aéroport au catalogue s'il n'est pas déjà présent.
     *
     * @param airport l'aéroport à ajouter
     * @return true si l'aéroport a été ajouté, false sinon
     */
    public boolean addAirport(Airport airport) {
        boolean isAdded = false;
        if (airportsMap.get(airport.getCode()) == null) {
            airportsMap.put(airport.getCode(), airport);
            isAdded = true;
        }
        return isAdded;
    }

    
    /**
     * Affiche tous les aéroports dans le catalogue et le nombre total.
     */
    public void displayAirports() {
        int count = 0;
        for (String key : airportsMap.keySet()) {
            System.out.println(airportsMap.get(key));
            count++;
        }
        System.out.println("###### " + count + " ######");
    }
    

    /* ••••••••••••• GETTERS / SETTERS ••••••••••••• */
    
    
    /**
     * Obtient un aéroport du catalogue par son code.
     *
     * @param code le code de l'aéroport
     * @return l'aéroport avec le code spécifié, ou null s'il n'est pas trouvé
     */
    public Airport getAirport(String code) {
        return airportsMap.get(code);
    }
    
    
    /**
     * Obtient tous les aéroports du catalogue.
     *
     * @return un ensemble contenant tous les aéroports du catalogue
     */
    public Set<Airport> getAirports(){
        Set<Airport> airportSet = new HashSet<>();
        // Parcours de l'ensemble des entrées de la map d'aéroports
        for (Map.Entry<String, Airport> entry : airportsMap.entrySet()) {
            // Ajout de chaque aéroport à l'ensemble
            airportSet.add(entry.getValue());
        }
        return airportSet;
    }
}
