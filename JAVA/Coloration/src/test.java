import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class test {
    private static Graph graph = new SingleGraph(null);
    private static Coloration test = new Coloration(graph);

    public static void main(String[] args) {
        try {
            test.charger_graphe("data/graph-test1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(test.dsatur());
        
    }
}
