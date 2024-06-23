package flights;

import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sae.models.airports.Airport;
import sae.models.flights.Flight;

/**
 *
 * @author mathe
 */
public class FlightTest {
    private Airport MRS;
    private Airport BES;
    private Airport LYS;
    private Airport BOD;
    
    private Flight flight1;
    private Flight flight2;
    
    @Before
    public void setUp() {
        MRS = new Airport("MRS","Marseille",43,26,8,'N',5,12,49,'E');
        BES = new Airport("BES","Brest",48,26,52,'N',4,25,6,'O');
        LYS = new Airport("LYS","Lyon",45,43,35,'N',5,5,27,'E');
        BOD = new Airport("BOD","Bordeaux",44,49,42,'N',0,42,56,'O');
        
        flight1 = new Flight("AF000090",MRS,BES,7,33,81);
        flight2 = new Flight("AF000132",LYS,BOD,7,34,47);
    }
    

    @Test
    public void testIsFlightWithinTimeRange() {
        assertTrue(flight1.isFlightWithinTimeRange(new Date(0, 0, 0, 6, 0), new Date(0, 0, 0, 10, 0)));

        assertFalse(flight2.isFlightWithinTimeRange(new Date(0, 0, 0, 8, 0), new Date(0, 0, 0, 9, 0)));
    }

    @Test
    public void testIsLinkedToAirport() {
        assertTrue(flight1.isLinkedToAirport("MRS"));
        assertTrue(flight1.isLinkedToAirport("BES"));
        assertFalse(flight1.isLinkedToAirport("LYS"));
        assertTrue(flight2.isLinkedToAirport("LYS"));
        assertTrue(flight2.isLinkedToAirport("BOD"));
    }

    @Test
    public void testSettersAndGetters() {
        // Test setters
        flight1.setFlightHeighLevel(100);
        flight1.setName("Nouveau nom");
        flight1.setDepartureAirport(LYS);
        flight1.setArrivalAirport(BOD);
        flight1.setDepartureHour(8);
        flight1.setDepartureMinutes(0);
        flight1.setDuration(90);
        flight1.setFlightDistance(1000.0);

        assertEquals(100, flight1.getFlightHeighLevel());
        assertEquals("Nouveau nom", flight1.getName());
        assertEquals(LYS, flight1.getDepartureAirport());
        assertEquals(BOD, flight1.getArrivalAirport());
        assertEquals(8, flight1.getDepartureHour());
        assertEquals(0, flight1.getDepartureMinutes());
        assertEquals(90, flight1.getDuration());
        assertEquals(1000, flight1.getFlightDistance(), 0.001);
    }

    @Test
    public void testGetDepartureTimeAndArrivalTime() {
        assertEquals(453, flight1.getDepartureTime()); // 7h33 en minutes
        assertEquals(534, flight1.getArrivalTime()); // 7h33 + 81 minutes

        assertEquals(454, flight2.getDepartureTime()); // 7h34 en minutes
        assertEquals(501, flight2.getArrivalTime()); // 7h34 + 47 minutes
    }

    @Test
    public void testModelLineMethods() {
        assertEquals(MRS.getGeoPosition(), flight1.getPoint1());
        assertEquals(BES.getGeoPosition(), flight1.getPoint2());
        assertEquals(LYS.getGeoPosition(), flight2.getPoint1());
        assertEquals(BOD.getGeoPosition(), flight2.getPoint2());

        assertEquals("Marseille - Brest", flight1.toStringPointNames());
        assertEquals("Lyon - Bordeaux", flight2.toStringPointNames());
    }  
}
