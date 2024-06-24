/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sae.exceptions.DataMismatchException;
import sae.exceptions.FileFormatException;
import sae.models.airports.AirportsCatalog;
import sae.models.flights.FlightsCatalog;
import sae.models.toolbox.ToolBox;

/**
 *
 * @author sacha
 */
public class ToolBoxTest {

    private AirportsCatalog airportsCatalog;
    private FlightsCatalog flightsCatalog;

    @Before
    void setUp() {
        airportsCatalog = new AirportsCatalog();
        flightsCatalog = new FlightsCatalog();
    }

    @Test
    void testFillAirportsCatalog_FileNotFound() {
        try {
            ToolBox.fillAirportsCatalog("invalid/path/to/file.txt", airportsCatalog);
            fail("Expected FileNotFoundException");
        } catch (FileNotFoundException e) {
            // Test passé
        }
    }

    @Test
    void testFillAirportsCatalog_FileFormatException() {
        String filePath = "airports.txt";
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("invalid;format;data");
        } catch (IOException e) {
            fail("Problème pour créer le test file: " + e.getMessage());
        }

        try {
            ToolBox.fillAirportsCatalog(filePath, airportsCatalog);
            fail("Expected FileFormatException");
        } catch (FileFormatException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.getMessage());
        } finally {
            new File(filePath).delete();
        }
    }

    @Test
    void testFillFlightsCatalog_FileNotFound() {
        try {
            ToolBox.fillFlightsCatalog("invalid/path/to/airports.txt", "invalid/path/to/flights.txt", flightsCatalog, airportsCatalog);
            fail("Expected FileNotFoundException");
        } catch (FileNotFoundException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.getMessage());
        }
    }

    @Test
    void testFillFlightsCatalog_FileFormatException() {
        String airportsFilePath = "airports.txt";
        String flightsFilePath = "flights.txt";
        try (FileWriter airportsWriter = new FileWriter(airportsFilePath);
             FileWriter flightsWriter = new FileWriter(flightsFilePath)) {
            airportsWriter.write("valid;airport;data");
            flightsWriter.write("invalid;format;data");
        } catch (IOException e) {
            fail("Problème pour créer le test file: " + e.getMessage());
        }

        try {
            ToolBox.fillFlightsCatalog(airportsFilePath, flightsFilePath, flightsCatalog, airportsCatalog);
            fail("Expected FileFormatException");
        } catch (FileFormatException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.getMessage());
        } finally {
            new File(airportsFilePath).delete();
            new File(flightsFilePath).delete();
        }
    }

    @Test
    void testFillFlightsCatalog_DataMismatchException() {
        String airportsFilePath = "airports.txt";
        String flightsFilePath = "flights.txt";
        try (FileWriter airportsWriter = new FileWriter(airportsFilePath);
             FileWriter flightsWriter = new FileWriter(flightsFilePath)) {
            airportsWriter.write("AIR1;Airport 1;10;20;30;N;40;50;60;E\n");
            flightsWriter.write("FL1;UNKNOWN_AIRPORT;AIR1;100;200;300\n");
        } catch (IOException e) {
            fail("Problème pour créer le test file: " + e.getMessage());
        }

        try {
            ToolBox.fillFlightsCatalog(airportsFilePath, flightsFilePath, flightsCatalog, airportsCatalog);
            fail("Expected DataMismatchException");
        } catch (DataMismatchException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.getMessage());
        } finally {
            new File(airportsFilePath).delete();
            new File(flightsFilePath).delete();
        }
    }

    @Test
    void testFillGraph_FileFormatException() {
        String graphFilePath = "graph.txt";
        try (FileWriter writer = new FileWriter(graphFilePath)) {
            writer.write("invalid graph data");
        } catch (IOException e) {
            fail("Problème pour créer le test file: " + e.getMessage());
        }

        try {
            ToolBox.fillGraph(graphFilePath);
            fail("Expected FileFormatException");
        } catch (FileFormatException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.getMessage());
        } finally {
            new File(graphFilePath).delete();
        }
    }
}
