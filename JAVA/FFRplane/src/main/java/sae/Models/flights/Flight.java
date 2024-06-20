package sae.models.flights;

import sae.controller.Interfaces.ModelLine;
import org.jxmapviewer.viewer.GeoPosition;
import sae.controller.*;
import sae.models.airports.*;
import sae.models.toolbox.*;

/**
 * La classe Flight représente un vol avec ses détails incluant le nom, les aéroports de départ et d'arrivée, 
 * l'heure de départ, la durée et la distance calculée.
 * 
 * @author mathe
 */
public class Flight implements ModelLine{
    private static int i = 0; // Compteur pour attribuer un numéro de vol à chaque vol
    private int flightNumber;
    
    private String name;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private int departureHour;
    private int departureMinute;
    private int duration;
    private int flightHeighLevel;

    private double flightDistance; // Calculé une fois pour éviter de recalculer pour chaque comparaison entre vols

    /**
     * Constructeur pour instancier un vol avec des informations détaillées.
     *
     * @param name le nom du vol
     * @param departureAirport l'aéroport de départ
     * @param arrivalAirport l'aéroport d'arrivée
     * @param departureHour l'heure de départ
     * @param departureMinute la minute de départ
     * @param duration la durée du vol en minutes
     */
    public Flight(String name, Airport departureAirport, Airport arrivalAirport, int departureHour, int departureMinute, int duration) {
        this.name = name;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureHour = departureHour;
        this.departureMinute = departureMinute;
        this.duration = duration;
        this.flightDistance = ToolBox.calDistance(departureAirport.getCoordinates(), arrivalAirport.getCoordinates());
        i++;
        this.flightNumber = i;
    }


    /**
     * Retourne une représentation en chaîne de caractères de l'objet Flight.
     *
     * @return une représentation en chaîne de caractères de l'objet Flight
     */
    @Override
    public String toString() {
        return String.format("| %d | %s | %s | %s | %d | %d | %d |", 
                             flightNumber, name, departureAirport.getCode(), arrivalAirport.getCode(), 
                             departureHour, departureMinute, duration);
    }

    public int getFlightHeighLevel() {
        return flightHeighLevel;
    }

    public void setFlightHeighLevel(int flightHeighLevel) {
        this.flightHeighLevel = flightHeighLevel;
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
    public int getDepartureMinute() {
        return departureMinute;
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
     * @param departureMinute la nouvelle minute de départ
     */
    public void setDepartureMinute(int departureMinute) {
        this.departureMinute = departureMinute;
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
        return departureHour * 60 + departureMinute;
    }


    /**
     * Obtient l'heure d'arrivée en minutes depuis minuit.
     *
     * @return l'heure d'arrivée en minutes depuis minuit
     */
    public int getArrivalTime() {
        return getDepartureTime() + duration;
    }
    
    @Override 
    public GeoPosition getPoint1(){
        return departureAirport.getGeoPosition();
    }
    
    @Override 
    public GeoPosition getPoint2() {
        return arrivalAirport.getGeoPosition();
    }
}
