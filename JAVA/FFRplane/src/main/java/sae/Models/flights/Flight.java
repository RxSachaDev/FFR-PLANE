package sae.Models.flights;

import sae.Models.airports.*;
import sae.Models.toolbox.*;

/**
 * The Flight class represents a flight with its details including name, departure and arrival airports, 
 * departure time, duration, and calculated distance.
 */
public class Flight {
    private static int i = 0; // Counter to assign a flight number to each flight
    private int flightNumber;
    
    private String name;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private int departureHour;
    private int departureMinute;
    private int duration;

    private double flightDistance; // Calculated once to avoid recalculating for each comparison between flights

    /**
     * Constructor to instantiate a Flight with detailed information.
     *
     * @param name the name of the flight
     * @param departureAirport the departure airport
     * @param arrivalAirport the arrival airport
     * @param departureHour the departure hour
     * @param departureMinute the departure minute
     * @param duration the duration of the flight in minutes
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
     * Returns a string representation of the Flight object.
     *
     * @return a string representation of the Flight object
     */
    @Override
    public String toString() {
        return String.format("| %d | %s | %s | %s | %d | %d | %d |", 
                             flightNumber, name, departureAirport.getCode(), arrivalAirport.getCode(), 
                             departureHour, departureMinute, duration);
    }


    /**
     * Gets the name of the flight.
     *
     * @return the name of the flight
     */
    public String getName() {
        return name;
    }


    /**
     * Gets the departure airport.
     *
     * @return the departure airport
     */
    public Airport getDepartureAirport() {
        return departureAirport;
    }


    /**
     * Gets the arrival airport.
     *
     * @return the arrival airport
     */
    public Airport getArrivalAirport() {
        return arrivalAirport;
    }


    /**
     * Gets the departure hour.
     *
     * @return the departure hour
     */
    public int getDepartureHour() {
        return departureHour;
    }


    /**
     * Gets the departure minute.
     *
     * @return the departure minute
     */
    public int getDepartureMinute() {
        return departureMinute;
    }


    /**
     * Gets the duration of the flight in minutes.
     *
     * @return the duration of the flight in minutes
     */
    public int getDuration() {
        return duration;
    }


    /**
     * Gets the distance of the flight.
     *
     * @return the distance of the flight
     */
    public double getFlightDistance() {
        return flightDistance;
    }


    /**
     * Sets the name of the flight.
     *
     * @param name the new name of the flight
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * Sets the departure airport.
     *
     * @param departureAirport the new departure airport
     */
    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }


    /**
     * Sets the arrival airport.
     *
     * @param arrivalAirport the new arrival airport
     */
    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }


    /**
     * Sets the departure hour.
     *
     * @param departureHour the new departure hour
     */
    public void setDepartureHour(int departureHour) {
        this.departureHour = departureHour;
    }


    /**
     * Sets the departure minute.
     *
     * @param departureMinute the new departure minute
     */
    public void setDepartureMinute(int departureMinute) {
        this.departureMinute = departureMinute;
    }


    /**
     * Sets the duration of the flight in minutes.
     *
     * @param duration the new duration of the flight in minutes
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }


    /**
     * Sets the distance of the flight.
     *
     * @param flightDistance the new distance of the flight
     */
    public void setFlightDistance(double flightDistance) {
        this.flightDistance = flightDistance;
    }


    /**
     * Gets the flight number.
     *
     * @return the flight number
     */
    public int getFlightNumber() {
        return flightNumber;
    }


    /**
     * Gets the departure time in minutes since midnight.
     *
     * @return the departure time in minutes since midnight
     */
    public int getDepartureTime() {
        return departureHour * 60 + departureMinute;
    }


    /**
     * Gets the arrival time in minutes since midnight.
     *
     * @return the arrival time in minutes since midnight
     */
    public int getArrivalTime() {
        return getDepartureTime() + duration;
    }
}

