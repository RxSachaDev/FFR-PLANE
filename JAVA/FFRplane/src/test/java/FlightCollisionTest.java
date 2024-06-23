import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sae.models.airports.Airport;
import sae.models.flights.Flight;
import sae.models.intersection.FlightCollisionTools;
import sae.utils.Settings;

/**
 *
 * @author mathe
 */
public class FlightCollisionTest {
    private Airport MRS;
    private Airport BES;
    private Airport LYS;
    private Airport BOD;
    
    private Flight flight1;
    private Flight flight2;
    private Flight flight3;

    @Before
    public void setUp() {
        MRS = new Airport("MRS", "Marseille", 43, 26, 8, 'N', 5, 12, 49, 'E');
        BES = new Airport("BES", "Brest", 48, 26, 52, 'N', 4, 25, 6, 'O');
        LYS = new Airport("LYS", "Lyon", 45, 43, 35, 'N', 5, 5, 27, 'E');
        BOD = new Airport("BOD", "Bordeaux", 44, 49, 42, 'N', 0, 42, 56, 'O');
        
        flight1 = new Flight("AF000090", MRS, BES, 7, 33, 81);
        flight2 = new Flight("AF000132", LYS, BOD, 7, 34, 47);
        flight3 = new Flight("AF000234", MRS, LYS, 8, 0, 90);
        
        Settings.setSafetyMargin(15);
    }

    @Test
    public void testHasCollision_SameDepartureArrival() {
        // Flights with the same departure and arrival airports but different times
        assertFalse(FlightCollisionTools.hasCollision(flight1, flight3));
    }

    @Test
    public void testHasCollision_DirectIntersection() {
        // Flights with different departure and arrival airports
        assertTrue(FlightCollisionTools.hasCollision(flight1, flight2));
    }

    @Test
    public void testNoCollisionFarApart() {
        Flight farApartFlight = new Flight("AF000567", MRS, BES, 10, 0, 81);
        assertFalse(FlightCollisionTools.hasCollision(flight1, farApartFlight));
    }

    @Test
    public void testGetNumberOfCollisions(){
        String airportsFilePath = System.getProperty("user.dir") + "\\src\\main\\java\\data\\aeroports.txt";
        String flightsFilePath1 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test1.csv";
        String flightsFilePath2 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test2.csv";
        String flightsFilePath3 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test3.csv";
        String flightsFilePath4 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test4.csv";
        String flightsFilePath5 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test5.csv";
        String flightsFilePath6 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test6.csv";
        String flightsFilePath7 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test7.csv";
        String flightsFilePath8 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test8.csv";
        String flightsFilePath9 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test9.csv";
        String flightsFilePath10 = System.getProperty("user.dir") + "\\src\\main\\java\\data\\vol-test10.csv";
        
        
        try{
            int count1 = FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath1);
            int count2 = FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath2);
            int count3 = FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath3);
            int count4 = FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath4);
            int count5 = FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath5);
            int count6 = FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath6);
            int count7 = FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath7);
            int count8 = FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath8);
            int count9 = FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath9);
            
            assertEquals(0, count1);
            assertEquals(5, count2);
            assertEquals(17, count3);
            assertEquals(35, count4);
            assertEquals(23, count5);
            assertEquals(426, count6);
            assertEquals(593, count7);
            assertEquals(1299, count8);
            assertEquals(934, count9);
            
            FlightCollisionTools.getNumberOfCollisions(airportsFilePath,flightsFilePath10);
            fail();
        } catch (Exception e) {
            
        } 
    }

}
