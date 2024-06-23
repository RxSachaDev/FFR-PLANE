package sae.models.flights;

import java.util.Date;
import org.jxmapviewer.viewer.GeoPosition;

import sae.controller.Interfaces.ModelLine;
import sae.models.airports.Airport;
import sae.models.toolbox.ToolBox;


/**
 * La classe Flight représente un vol avec ses détails incluant le nom, les aéroports de départ et d'arrivée, 
 * l'heure de départ, la durée et la distance calculée.
 * 
 * @author mathe
 */
public class Flight implements ModelLine{
    
    private static int i = 0; // Compteur pour attribuer un numéro de vol à chaque vol
    private final int flightNumber;
    
    private String name;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private int departureHour;
    private int departureMinutes;
    private int duration;
    private int flightHeighLevel;

    private double flightDistance; // Calculée une fois pour éviter de recalculer pour chaque comparaison entre vols

    /**
     * Constructeur pour instancier un vol avec des informations détaillées.
     *
     * @param name le nom du vol
     * @param departureAirport l'aéroport de départ
     * @param arrivalAirport l'aéroport d'arrivée
     * @param departureHour l'heure de départ
     * @param departureMinutes les minutes de départ
     * @param duration la durée du vol en minutes
     */
    public Flight(String name, Airport departureAirport, Airport arrivalAirport, int departureHour, int departureMinutes, int duration) {
        this.name = name;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureHour = departureHour;
        this.departureMinutes = departureMinutes;
        this.duration = duration;
        this.flightDistance = ToolBox.calDistance(departureAirport.getCoordinates(), arrivalAirport.getCoordinates());
        i++;
        this.flightNumber = i;
    }

    
    /* ••••••••••••• MÉTHODES ••••••••••••• */
    
    
    /**
     * Vérifie si un vol se situe dans une plage horaire donnée.
     *
     * @param startTimeRange Le début de la plage horaire (Date).
     * @param endTimeRange La fin de la plage horaire (Date).
     * @return true si le vol se situe dans la plage horaire, false sinon.
     */
    public boolean isFlightWithinTimeRange(Date startTimeRange, Date endTimeRange) {
        int startMinutes = startTimeRange.getHours() * 60 + startTimeRange.getMinutes();
        int endMinutes = endTimeRange.getHours() * 60 + endTimeRange.getMinutes(); 
        int flightDepartureMinutes = getDepartureTime();
        int flightArrivalMinutes = getArrivalTime();

        if (startMinutes <= endMinutes) { // Plage horaire dans la même journée
            return (startMinutes <= flightDepartureMinutes && flightArrivalMinutes <= endMinutes);
        } else { // Cas ou minuit est passé
            // exemple : 16h30 - 14h30 => Seuls les vols entre 14h31 et 16h29 retournerons false
            return (startMinutes <= flightDepartureMinutes || flightArrivalMinutes <= endMinutes);
        }
    }

    
    /**
     * Vérifie si ce vol est lié à un code d'aéroport spécifié.
     *
     * @param airportCode le code d'aéroport à vérifier
     * @return true si le vol est lié à l'aéroport spécifié, sinon false
     */
    public boolean isLinkedToAirport(String airportCode){
        if(departureAirport.getCode().equals(airportCode) || arrivalAirport.getCode().equals(airportCode)) return true;
        return false;
    }
    
    
    public static void resetFlightIdIterator(){
        i=0;
    }
    
    
    /* ••••••••••••• GETTERS / SETTERS ••••••••••••• */
    

    /**
     * Définit le niveau de vol en altitude.
     *
     * @param flightHeighLevel le nouveau niveau de vol en altitude
     */
    public void setFlightHeighLevel(int flightHeighLevel) {
        this.flightHeighLevel = flightHeighLevel;
    }

    
    /**
     * Définit le nom du vol.
     *
     * @param name le nouveau nom du vol
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * Définit l'aéroport de départ.
     *
     * @param departureAirport le nouvel aéroport de départ
     */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }


    /**
     * Définit l'aéroport d'arrivée.
     *
     * @param arrivalAirport le nouvel aéroport d'arrivée
     */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }


    /**
     * Définit l'heure de départ.
     *
     * @param departureHour la nouvelle heure de départ
     */
    public void setDepartureHour(int departureHour) {
        this.departureHour = departureHour;
    }


    /**
     * Définit la minute de départ.
     *
     * @param departureMinutes la nouvelle minute de départ
     */
    public void setDepartureMinutes(int departureMinutes) {
        this.departureMinutes = departureMinutes;
    }


    /**
     * Définit la durée du vol en minutes.
     *
     * @param duration la nouvelle durée du vol en minutes
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }


    /**
     * Définit la distance du vol.
     *
     * @param flightDistance la nouvelle distance du vol
     */
    public void setFlightDistance(double flightDistance) {
        this.flightDistance = flightDistance;
    }
    

    /**
     * Obtient le niveau de vol en altitude.
     *
     * @return le niveau de vol en altitude
     */
    public int getFlightHeighLevel() {
        return flightHeighLevel;
    }

    
    /**
     * Obtient le nom du vol.
     *
     * @return le nom du vol
     */
    public String getName() {
        return name;
    }


    /**
     * Obtient l'aéroport de départ.
     *
     * @return l'aéroport de départ
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }


    /**
     * Obtient l'aéroport d'arrivée.
     *
     * @return l'aéroport d'arrivée
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }


    /**
     * Obtient l'heure de départ.
     *
     * @return l'heure de départ
     */
    public int getDepartureHour() {
        return departureHour;
    }


    /**
     * Obtient la minute de départ.
     *
     * @return la minute de départ
     */
    public int getDepartureMinutes() {
        return departureMinutes;
    }


    /**
     * Obtient la durée du vol en minutes.
     *
     * @return la durée du vol en minutes
     */
    public int getDuration() {
        return duration;
    }


    /**
     * Obtient la distance du vol.
     *
     * @return la distance du vol
     */
    public double getFlightDistance() {
        return flightDistance;
    }


    /**
     * Obtient le numéro de vol.
     *
     * @return le numéro de vol
     */
    public int getFlightNumber() {
        return flightNumber;
    }


    /**
     * Obtient l'heure de départ en minutes depuis minuit.
     *
     * @return l'heure de départ en minutes depuis minuit
     */
    public int getDepartureTime() {
        return departureHour * 60 + departureMinutes;
    }


    /**
     * Obtient l'heure d'arrivée en minutes depuis minuit.
     *
     * @return l'heure d'arrivée en minutes depuis minuit
     */
    public int getArrivalTime() {
        return getDepartureTime() + duration;
    }
    
    
    /* ••••••••••••• MÉTHODES @OVERRIDE ••••••••••••• */
    
    
    /**
     * Retourne une représentation en chaîne de caractères de l'objet Flight.
     *
     * @return une représentation en chaîne de caractères de l'objet Flight
     */
    @Override
    public String toString() {
        if(flightHeighLevel == 0){
            return ("• VOL \n"+
                    "   • Code : "+name+"\n"+ 
                    "   • Depart : "+departureAirport.getLocation()+"\n"+
                    "   • Arrivée : "+arrivalAirport.getLocation()+"\n"+
                    "   • Heure départ : "+departureHour+" h "+departureMinutes+"\n"+
                    "   • Durée : "+duration+" min\n"+
                    "   • Hauteur : Non Parametrée");
        } else {
            return ("• VOL \n"+
                    "   • Code : "+name+"\n"+ 
                    "   • Depart : "+departureAirport.getLocation()+"\n"+
                    "   • Arrivée : "+arrivalAirport.getLocation()+"\n"+
                    "   • Heure départ : "+departureHour+" h "+departureMinutes+"\n"+
                    "   • Durée : "+duration+"\n"+
                    "   • Hauteur : "+flightHeighLevel);
        }
    }
    
    
    /**
     * Obtient la première position géographique de la ligne de vol, correspondant à l'aéroport de départ.
     *
     * @return la position géographique de l'aéroport de départ
     */
    @Override 
    public GeoPosition getPoint1(){
        return departureAirport.getGeoPosition();
    }
    
    
    /**
     * Obtient la deuxième position géographique de la ligne de vol, correspondant à l'aéroport d'arrivée.
     *
     * @return la position géographique de l'aéroport d'arrivée
     */
    @Override 
    public GeoPosition getPoint2() {
        return arrivalAirport.getGeoPosition();
    }
    
    
    /**
     * Retourne une représentation en chaîne des noms des points de la ligne de vol, séparés par un tiret.
     *
     * @return une représentation en chaîne des noms des points de la ligne de vol
     */
    @Override
    public String toStringPointNames(){
        return (departureAirport.getLocation()+" - "+arrivalAirport.getLocation());
    }
}
