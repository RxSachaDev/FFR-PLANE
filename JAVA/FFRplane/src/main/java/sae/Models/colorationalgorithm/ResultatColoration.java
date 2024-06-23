package sae.models.colorationalgorithm;

import org.graphstream.graph.Graph;

/**
 * La classe ResultatColoration représente le résultat d'une tentative de
 * coloration de graphe, incluant le nombre de conflits et le graphe coloré.
 *
 * @author Sacha
 */
public class ResultatColoration {

    /**
     * Le nombre de conflits dans la coloration du graphe.
     */
    private int conflict;

    /**
     * Le graphe coloré résultant.
     */
    private Graph graph;

    private String algorithm;

    /**
     * Constructeur de la classe ResultatColoration.
     *
     * @param conflict Le nombre de conflits dans la coloration.
     * @param graph Le graphe coloré.
     */
    public ResultatColoration(int conflict, Graph graph, String algorithm) {
        this.conflict = conflict;
        this.graph = graph;
        this.algorithm = algorithm;
    }

    /**
     * Retourne le nombre de conflits dans la coloration du graphe.
     *
     * @return Le nombre de conflits.
     */
    public int getConflict() {
        return conflict;
    }

    /**
     * Retourne le graphe coloré.
     *
     * @return Le graphe coloré.
     */
    public Graph getGraph() {
        return graph;
    }

    public String getAlgorithm() {
        return algorithm;
    }

}
