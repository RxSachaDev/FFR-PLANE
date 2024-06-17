
import java.io.IOException;
import org.graphstream.graph.Graph;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import sae.Models.algocoloration.AlgoColoration;
import sae.Models.algocoloration.ResultatColoration;

public class AlgoTest {

    private AlgoColoration algoColoration;

    @Before
    public void setUp() {
        algoColoration = new AlgoColoration();
        
    }

    @Test
    public void testSetFichier() {
        String fichier = "src/main/java/data/test/graph-test7.txt";
        algoColoration.setFichier(fichier);
        assertEquals(fichier, algoColoration.getFichier());
    }

    
    @Test
    public void testChargerGraphe() throws IOException {
        // Créer un fichier de test
        
        try {
            algoColoration.charger_graphe();
            fail();
        } catch (Exception p){
            
        }
        String fichier = "src/main/java/data/test/graph-test7.txt";
        algoColoration.setFichier(fichier);
        algoColoration.charger_graphe();

        Graph graph = algoColoration.getGraph();
        assertNotNull(graph);
        assertEquals(algoColoration.getNbSommet(), graph.getNodeCount());
    }

    @Test
    public void testWelshPowell() throws IOException {
        // Créer un fichier de test
        String fichier = "src/main/java/data/test/graph-test0.txt";
        try {
            algoColoration.setFichier(fichier);
        algoColoration.charger_graphe();
        } catch (Exception p){
            fail();
        }

        

        int conflicts = algoColoration.welshPowell();
        assertEquals(0, conflicts);
    }

    @Test
    public void testDSatur() throws IOException {
        // Créer un fichier de test
        String fichier = "src/main/java/data/test/graph-test0.txt";
        try {
            algoColoration.setFichier(fichier);
        algoColoration.charger_graphe();
        } catch (Exception p){
            fail();
        }

        int conflicts = algoColoration.dsatur(algoColoration.getGraph());
        assertEquals(0, conflicts);
    }

    @Test
    public void testMinConflict() throws IOException {
        // Créer un fichier de test
        String fichier = "src/main/java/data/test/graph-test0.txt";
        try {
            algoColoration.setFichier(fichier);
        algoColoration.charger_graphe();
        } catch (Exception p){
            fail();
        }
        ResultatColoration result = algoColoration.minConflict();
        assertNotNull(result);
        assertEquals(0, result.getConflict());
    }
    
    @Test
    public void testCopyGraphWithAttributes(){
        algoColoration.getGraph().addNode("1");
        algoColoration.getGraph().getNode("1").addAttribute("color", 5);
        algoColoration.getGraph().addNode("2");
        algoColoration.getGraph().getNode("2").addAttribute("color", 2);
        algoColoration.getGraph().addNode("3");
        algoColoration.getGraph().getNode("3").addAttribute("color", 1);
        Graph testGraph = algoColoration.copyGraphWithAttributes(algoColoration.getGraph());
        
        assertEquals((int)testGraph.getNode("1").getAttribute("color"),(int)algoColoration.getGraph().getNode("1").getAttribute("color"));
        assertEquals((int)testGraph.getNode("2").getAttribute("color"),(int)algoColoration.getGraph().getNode("2").getAttribute("color"));
    }
}
