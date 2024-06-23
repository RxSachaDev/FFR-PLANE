package sae.Models.coloration;

import org.graphstream.graph.Graph;

/**
 * La classe ColorationResult représente le résultat d'une tentative de
 coloration de graphe, incluant le nombre de conflits et le graphe coloré.
 *
 * @author Sacha
 */
public class ColorationResult {

    /**
     * Le nombre de conflits dans la coloration du graphe.
     */
    private int conflict;

    /**
     * Le graphe coloré résultant.
     */
    private Graph graph;

    /**
     * Le nom de l'algorithme utilisé.
     */
    private String algorithm;

    /**
     * Constructeur de la classe ResultatColoration.
     *
     * @param conflict Le nombre de conflits dans la coloration.
     * @param graph Le graphe coloré.
     */
    public ColorationResult(int conflict, Graph graph, String algorithm) {
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

    /**
     * Retourne l'algorithme utilisé.
     *
     * @return l'algorithme utilisé.
     */
    public String getAlgorithm() {
        return algorithm;
    }

}
