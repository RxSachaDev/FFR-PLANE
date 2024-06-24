import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import sae.exceptions.DataMismatchException;
import sae.exceptions.FileFormatException;
import sae.model.airports.AirportsCatalog;
import sae.model.flights.FlightsCatalog;
import sae.model.toolbox.ToolBox;

/**
 *
 * @author sacha
 */
public class ToolBoxTest {

    private AirportsCatalog airportsCatalog;
    private FlightsCatalog flightsCatalog;

    @Before
    public void setUp() {
        airportsCatalog = new AirportsCatalog();
        flightsCatalog = new FlightsCatalog();
    }

    @Test
    public void testFillAirportsCatalog_FileNotFound() {
        try {
            ToolBox.fillAirportsCatalog("invalid/path/to/file.txt", airportsCatalog);
            fail("Expected FileNotFoundException");
        } catch (FileNotFoundException e) {
            // Test passé
        }
    }

    
    @Test
    public void testFillAirportsCatalog_FileFormatException() {
        String filePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\exceptionFiles\\aeroports.txt";
        try {
            ToolBox.fillAirportsCatalog(filePath, airportsCatalog);
            fail();
        } catch (FileFormatException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.toString());
        }
    }


    @Test
    public void testFillFlightsCatalog_FileNotFound() {
        try {
            ToolBox.fillFlightsCatalog("airports.txt", "flights.txt", flightsCatalog, airportsCatalog);
            fail("Expected FileNotFoundException");
        } catch (FileNotFoundException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.getMessage());
        }
    }

   
    @Test
    public void testFillFlightsCatalog_FileFormatException() {
        String airportsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\aeroports.txt";
        String flightsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\exceptionFiles\\vol-test1.csv";

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
    public void testFillFlightsCatalog_DataMismatchException() {
        String airportsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\aeroports.txt";
        String flightsFilePath =  System.getProperty("user.dir") + "\\src\\main\\java\\data\\exceptionFiles\\vol-test2.csv";
        try {
            ToolBox.fillFlightsCatalog(airportsFilePath, flightsFilePath, flightsCatalog, airportsCatalog);
            fail();
        } catch (DataMismatchException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.toString());
        }
    }

    @Test
    public void testFillGraph_FileFormatException1() {
        String graphFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\exceptionFiles\\graph-test1.txt";
        try {
            ToolBox.fillGraph(graphFilePath);
            fail();
        } catch (FileFormatException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.getMessage());
        }
    }
    
    @Test
    public void testFillGraph_FileFormatException2() {
        String graphFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\exceptionFiles\\graph-test2.txt";
        try {
            ToolBox.fillGraph(graphFilePath);
            fail();
        } catch (FileFormatException e) {
            // Test passé
        } catch (Exception e) {
            fail("Exception non attendu : " + e.getMessage());
        }
    }
}
