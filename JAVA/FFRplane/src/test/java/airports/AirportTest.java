package airports;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jxmapviewer.viewer.GeoPosition;
import sae.models.airports.Airport;

/**
 *
 * @author mathe
 */
public class AirportTest {
    private Airport MRS;
    private Airport BES;
    private Airport LYS;
    private Airport BOD;
    
    @Before
    public void setUp() {
        MRS = new Airport("MRS","Marseille",43,26,8,'N',5,12,49,'E');
        BES = new Airport("BES","Brest",48,26,52,'N',4,25,6,'O');
        LYS = new Airport("LYS","Lyon",45,43,35,'N',5,5,27,'E');
        BOD = new Airport("BOD","Bordeaux",44,49,42,'N',0,42,56,'O');
    }

    @Test
    public void testCalCartesianCoordinates() {
        double[] cooMRS = MRS.calCartesianCoordinates();
        double[] cooBES = BES.calCartesianCoordinates();
        double[] cooLYS = LYS.calCartesianCoordinates();
        double[] cooBOD = BOD.calCartesianCoordinates();

        assertEquals(420.387, cooMRS[0], 0.001);
        assertEquals(4607.150, cooMRS[1], 0.001);
        
        assertEquals(-325.555, cooBES[0], 0.001);
        assertEquals(4213.340, cooBES[1], 0.001);
        
        assertEquals(394.648, cooLYS[0], 0.001);
        assertEquals(4429.959, cooLYS[1], 0.001);
        
        assertEquals(-56.428, cooBOD[0], 0.001);
        assertEquals(4518.102, cooBOD[1], 0.001);
    }

    @Test
    public void testCalGeoPosition() {
        // Test avec l'aéroport MRS
        GeoPosition geoMRS = MRS.getGeoPosition();
        assertEquals(43.435556, geoMRS.getLatitude(), 0.001);
        assertEquals(5.213611, geoMRS.getLongitude(), 0.001);

        // Test avec l'aéroport BES
        GeoPosition geoBES = BES.getGeoPosition();
        assertEquals(48.447778, geoBES.getLatitude(), 0.001);
        assertEquals(-4.418333, geoBES.getLongitude(), 0.001);

        // Test avec l'aéroport LYS
        GeoPosition geoLYS = LYS.getGeoPosition();
        assertEquals(45.726389, geoLYS.getLatitude(), 0.001);
        assertEquals(5.090833, geoLYS.getLongitude(), 0.001);

        // Test avec l'aéroport BOD
        GeoPosition geoBOD = BOD.getGeoPosition();
        assertEquals(44.828333, geoBOD.getLatitude(), 0.001);
        assertEquals(-0.715556, geoBOD.getLongitude(), 0.001);
    }

    @Test
    public void testToString() {
        // Test avec l'aéroport MRS
        String resultMRS = MRS.toString();
        assertTrue(resultMRS.contains("AEROPORT"));
        assertTrue(resultMRS.contains("Code : MRS"));
        assertTrue(resultMRS.contains("Ville : Marseille"));

        // Test avec l'aéroport BES
        String resultBES = BES.toString();
        assertTrue(resultBES.contains("AEROPORT"));
        assertTrue(resultBES.contains("Code : BES"));
        assertFalse(resultBES.contains("Ville : Breast"));

        // Test avec l'aéroport LYS
        String resultLYS = LYS.toString();
        assertTrue(resultLYS.contains("AEROPORT"));
        assertTrue(resultLYS.contains("Code : LYS"));
        assertFalse(resultLYS.contains("Ville : Lion"));

        // Test avec l'aéroport BOD
        String resultBOD = BOD.toString();
        assertTrue(resultBOD.contains("AEROPORT"));
        assertTrue(resultBOD.contains("Code : BOD"));
        assertFalse(resultBOD.contains("Ville : Bordau"));
    }

    @Test
    public void testAccessors() {
        // Test avec l'aéroport MRS
        assertEquals("MRS", MRS.getCode());
        assertEquals("Marseille", MRS.getLocation());

        MRS.setCode("MRS2");
        MRS.setLocation("Marseille 2");

        assertEquals("MRS2", MRS.getCode());
        assertEquals("Marseille 2", MRS.getLocation());
    }
    
    @Test
    public void testToStringName() {
        assertEquals("Marseille", MRS.toStringName());
        assertEquals("Brest", BES.toStringName());
        assertEquals("Lyon", LYS.toStringName());
        assertEquals("Bordeaux", BOD.toStringName());
    }

    @Test
    public void testGetModelPointCode() {
        assertEquals("MRS", MRS.getModelPointCode());
        assertEquals("BES", BES.getModelPointCode());
        assertEquals("LYS", LYS.getModelPointCode());
        assertEquals("BOD", BOD.getModelPointCode());
    }
}
