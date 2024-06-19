
import java.io.IOException;
import java.util.ArrayList;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
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
        } catch (Exception p) {

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
        } catch (Exception p) {
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
        } catch (Exception p) {
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
        } catch (Exception p) {
            fail();
        }
        ResultatColoration result = algoColoration.minConflict();
        assertNotNull(result);
        assertEquals(0, result.getConflict());
    }

    @Test
    public void testCopyGraphWithAttributes() {
        algoColoration.getGraph().addNode("1");
        algoColoration.getGraph().getNode("1").addAttribute("color", 5);
        algoColoration.getGraph().addNode("2");
        algoColoration.getGraph().getNode("2").addAttribute("color", 2);
        algoColoration.getGraph().addNode("3");
        algoColoration.getGraph().getNode("3").addAttribute("color", 1);
        Graph testGraph = algoColoration.copyGraphWithAttributes(algoColoration.getGraph());

        assertEquals((int) testGraph.getNode("1").getAttribute("color"), (int) algoColoration.getGraph().getNode("1").getAttribute("color"));
        assertEquals((int) testGraph.getNode("2").getAttribute("color"), (int) algoColoration.getGraph().getNode("2").getAttribute("color"));
    }

    @Test
    public void testRangerParDegreeNodes() {
        algoColoration.getGraph().addNode("1");
        algoColoration.getGraph().addNode("2");
        algoColoration.getGraph().addNode("3");
        algoColoration.getGraph().addNode("4");
        Node node1 = algoColoration.getGraph().getNode("1");
        Node node2 = algoColoration.getGraph().getNode("2");
        Node node3 = algoColoration.getGraph().getNode("3");
        Node node4 = algoColoration.getGraph().getNode("4");
        algoColoration.getGraph().addEdge("3_1", node3, node1);
        algoColoration.getGraph().addEdge("3_2", node3, node2);
        algoColoration.getGraph().addEdge("4_3", node4, node3);
        algoColoration.getGraph().addEdge("4_2", node4, node2);
        Node[] testTab = new Node[4];
        testTab[0] = node3;
        testTab[1] = node4;
        testTab[2] = node2;
        testTab[3] = node1;
        Node[] resultTab = algoColoration.rangerParDegreeNodes(algoColoration.getGraph());
        assertEquals(testTab[0], resultTab[0]);
        assertEquals(testTab[1], resultTab[1]);
        assertEquals(testTab[2], resultTab[2]);
        assertEquals(testTab[3], resultTab[3]);
    }

    @Test
    public void testCountChromaticNumber() {
        algoColoration.getGraph().addNode("1");
        algoColoration.getGraph().getNode("1").addAttribute("color", 3);
        algoColoration.getGraph().addNode("2");
        algoColoration.getGraph().getNode("2").addAttribute("color", 2);
        algoColoration.getGraph().addNode("3");
        algoColoration.getGraph().getNode("3").addAttribute("color", 1);
        int chromaticNumberResult = algoColoration.countChromaticNumber(algoColoration.getGraph());
        assertEquals(3, chromaticNumberResult);
    }

    @Test
    public void testCountConflicts() {
        algoColoration.getGraph().addNode("1");
        algoColoration.getGraph().addNode("2");
        algoColoration.getGraph().addNode("3");
        Node node1 = algoColoration.getGraph().getNode("1");
        Node node2 = algoColoration.getGraph().getNode("2");
        Node node3 = algoColoration.getGraph().getNode("3");
        node3.addAttribute("color", 1);
        node2.addAttribute("color", 2);
        node1.addAttribute("color", 2);
        algoColoration.getGraph().addEdge("1_2", node1, node2);
        algoColoration.getGraph().addEdge("3_2", node3, node2);
        algoColoration.getGraph().addEdge("1_3", node1, node3);
        int conflictResult = algoColoration.countConflicts(algoColoration.getGraph());
        assertEquals(1, conflictResult);
    }

    @Test
    public void testColorPasRempli() {
        algoColoration.getGraph().addNode("1");
        algoColoration.getGraph().addNode("2");
        algoColoration.getGraph().addNode("3");
        Node node1 = algoColoration.getGraph().getNode("1");
        Node node2 = algoColoration.getGraph().getNode("2");
        Node node3 = algoColoration.getGraph().getNode("3");
        node2.addAttribute("color", 2);
        node3.addAttribute("color", 1);
        Node[] testTab = new Node[3];
        testTab[0] = node3;
        testTab[1] = node1;
        testTab[2] = node2;
        boolean colorPasRempliResult = algoColoration.colorPasRempli(testTab);
        assertTrue(colorPasRempliResult);
        algoColoration.getGraph().getNode("1").addAttribute("color", 2);
        colorPasRempliResult = algoColoration.colorPasRempli(testTab);
        assertFalse(colorPasRempliResult);

    }

    @Test
    public void testPlusHautDegreNonUtilise() {
        algoColoration.getGraph().addNode("1");
        algoColoration.getGraph().addNode("2");
        algoColoration.getGraph().addNode("3");
        algoColoration.getGraph().addNode("4");
        Node node1 = algoColoration.getGraph().getNode("1");
        Node node2 = algoColoration.getGraph().getNode("2");
        Node node3 = algoColoration.getGraph().getNode("3");
        Node node4 = algoColoration.getGraph().getNode("4");
        node2.addAttribute("color", 2);
        node3.addAttribute("color", 1);
        algoColoration.getGraph().addEdge("3_1", node3, node1);
        algoColoration.getGraph().addEdge("3_2", node3, node2);
        algoColoration.getGraph().addEdge("4_3", node4, node3);
        algoColoration.getGraph().addEdge("4_2", node4, node2);
        node1.setAttribute("nbColor", 1);
        node2.setAttribute("nbColor", 2);
        node3.setAttribute("nbColor", 3);
        node4.setAttribute("nbColor", 2);
        Node[] testTab = new Node[4];
        testTab[0] = node3;
        testTab[1] = node4;
        testTab[2] = node2;
        testTab[3] = node1;

        Node resultNode = algoColoration.plusHautDegreNonUtilise(testTab);
        assertEquals(node4, resultNode);
    }
    
    @Test
    public void testSetNbColorOpposite() {
        Node node1 = algoColoration.getGraph().addNode("1");
        Node node2 = algoColoration.getGraph().addNode("2");
        Node node3 = algoColoration.getGraph().addNode("3");
        algoColoration.getGraph().addEdge("1_2", "1", "2");
        algoColoration.getGraph().addEdge("1_3", "1", "3");
        
        node1.setAttribute("color", 1);
        algoColoration.setNbColorOpposite(node1);
        
        ArrayList<Integer> colorsNode2 = node2.getAttribute("couleurAutour");
        ArrayList<Integer> colorsNode3 = node3.getAttribute("couleurAutour");

        assertNotNull(colorsNode2);
        assertTrue(colorsNode2.contains(1));
        assertEquals(1, colorsNode2.size());

        assertNotNull(colorsNode3);
        assertTrue(colorsNode3.contains(1));
        assertEquals(1, colorsNode3.size());
    }

    @Test
    public void testAppliquerColor() {
        Node node1 = algoColoration.getGraph().addNode("1");
        Node node2 = algoColoration.getGraph().addNode("2");
        Node node3 = algoColoration.getGraph().addNode("3");
        algoColoration.getGraph().addEdge("1_2", "1", "2");
        algoColoration.getGraph().addEdge("1_3", "1", "3");

        node1.setAttribute("color", 1);
        node2.setAttribute("color", 2);

        algoColoration.appliquerColor(node3);
        
        Integer colorNode3 = (int) node3.getAttribute("color");

        assertNotNull(colorNode3);
        assertTrue(colorNode3.equals(2));
    }
}
