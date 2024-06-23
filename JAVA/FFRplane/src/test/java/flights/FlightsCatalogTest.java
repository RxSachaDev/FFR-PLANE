package flights;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sae.models.airports.Airport;
import sae.models.flights.Flight;
import sae.models.flights.FlightsCatalog;

/**
 *
 * @author mathe
 */
public class FlightsCatalogTest {
    private Airport MRS;
    private Airport BES;
    private Airport LYS;
    private Airport BOD;
    
    private Flight flight1;
    private Flight flight2;
    
    private FlightsCatalog catalog;
    
    @Before
    public void setUp() {
        MRS = new Airport("MRS","Marseille",43,26,8,'N',5,12,49,'E');
        BES = new Airport("BES","Brest",48,26,52,'N',4,25,6,'O');
        LYS = new Airport("LYS","Lyon",45,43,35,'N',5,5,27,'E');
        BOD = new Airport("BOD","Bordeaux",44,49,42,'N',0,42,56,'O');
        
        flight1 = new Flight("AF000090",MRS,BES,7,33,81);
        flight2 = new Flight("AF000132",LYS,BOD,7,34,47);
        
        catalog = new FlightsCatalog();
    }

    @Test
    public void testAddFlight() {
        assertTrue(catalog.addFlight(flight1));
        assertTrue(catalog.addFlight(flight2));
        assertFalse(catalog.addFlight(flight1));
    }

    @Test
    public void testClearCatalog() {
        catalog.addFlight(flight1);
        catalog.addFlight(flight2);
        assertEquals(2, catalog.getFlights().size());

        catalog.clearCatalog();
        assertEquals(0, catalog.getFlights().size());
    }

    @Test
    public void testGetFlights() {
        catalog.addFlight(flight1);
        catalog.addFlight(flight2);

        List<Flight> flights = catalog.getFlights();
        assertEquals(2, flights.size());
        assertTrue(flights.contains(flight1));
        assertTrue(flights.contains(flight2));
    }
}
