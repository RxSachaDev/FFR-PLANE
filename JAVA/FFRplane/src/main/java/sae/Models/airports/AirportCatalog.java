package sae.models.airports;

import java.util.*;

/**
 * La classe AirportCatalog gère un catalogue d'aéroports.
 * 
 * @author mathe
 */
public class AirportCatalog {
    private Map<String, Airport> airportMap;
  
    /**
     * Construit un nouveau AirportCatalog avec une map vide d'aéroports.
     */
    public AirportCatalog() {
        airportMap = new HashMap<>();
    }


    /**
     * Ajoute un aéroport au catalogue s'il n'est pas déjà présent.
     *
     * @param airport l'aéroport à ajouter
     * @return true si l'aéroport a été ajouté, false sinon
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
     * Obtient un aéroport du catalogue par son code.
     *
     * @param code le code de l'aéroport
     * @return l'aéroport avec le code spécifié, ou null s'il n'est pas trouvé
     */
    public Airport getAirport(String code) {
        return airportMap.get(code);
    }
    

    /**
     * Affiche tous les aéroports dans le catalogue et le nombre total.
     */
    public void displayAirports() {
        int count = 0;
        for (String key : airportMap.keySet()) {
            System.out.println(airportMap.get(key));
            count++;
        }
        System.out.println("###### " + count + " ######");
    }
    
    
    public Set<Airport> getAirports(){
        Set<Airport> airportSet = new HashSet<>();
        
        // Parcours de l'ensemble des entrées de la map d'aéroports
        for (Map.Entry<String, Airport> entry : airportMap.entrySet()) {
            // Ajout de chaque aéroport à l'ensemble
            airportSet.add(entry.getValue());
        }
        
        // Retour de l'ensemble d'aéroports
        return airportSet;
    }
    
    
    public int size(){
        return airportMap.size();
    }
}
