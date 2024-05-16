import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

public class test {
    private static Graph graph = new MultiGraph(null);
    private static Coloration test = new Coloration(graph);

    public static void main(String[] args) {
        test.setFichier("data/graph-test1.txt");
        try {
            test.charger_graphe();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(test.minConflict());
    }
}
