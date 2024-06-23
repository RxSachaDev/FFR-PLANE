package airports;

import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sae.models.airports.Airport;
import sae.models.airports.AirportsCatalog;

/**
 *
 * @author mathe
 */
public class AirportsCatalogTest {
    private Airport MRS;
    private Airport BES;
    private Airport LYS;
    private Airport BOD;
    
    private AirportsCatalog airportsCatalog;
    
    @Before
    public void setUp() {
        MRS = new Airport("MRS","Marseille",43,26,8,'N',5,12,49,'E');
        BES = new Airport("BES","Brest",48,26,52,'N',4,25,6,'O');
        LYS = new Airport("LYS","Lyon",45,43,35,'N',5,5,27,'E');
        BOD = new Airport("BOD","Bordeaux",44,49,42,'N',0,42,56,'O');
        
        airportsCatalog = new AirportsCatalog();
    }
    
    @Test
    public void testAddAirport() {
        assertTrue(airportsCatalog.addAirport(MRS));
        assertTrue(airportsCatalog.addAirport(BES));
        assertFalse(airportsCatalog.addAirport(MRS));

        Set<Airport> airports = airportsCatalog.getAirports();
        assertEquals(2, airports.size());
        assertTrue(airports.contains(MRS));
        assertTrue(airports.contains(BES));
    }
    
    @Test
    public void testGetAirport() {
        airportsCatalog.addAirport(MRS);
        airportsCatalog.addAirport(BES);

        assertEquals(MRS, airportsCatalog.getAirport("MRS"));
        assertEquals(BES, airportsCatalog.getAirport("BES"));
        assertNull(airportsCatalog.getAirport("LYS"));
    }

    @Test
    public void testGetAirports() {
        airportsCatalog.addAirport(MRS);
        airportsCatalog.addAirport(BES);
        airportsCatalog.addAirport(LYS);

        Set<Airport> airports = airportsCatalog.getAirports();
        assertEquals(3, airports.size());
        assertTrue(airports.contains(MRS));
        assertTrue(airports.contains(BES));
        assertTrue(airports.contains(LYS));
    }
}
