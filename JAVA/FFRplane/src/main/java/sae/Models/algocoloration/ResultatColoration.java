/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.models.algocoloration;

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

        /**
         * Constructeur de la classe ResultatColoration.
         *
         * @param conflict Le nombre de conflits dans la coloration.
         * @param graph Le graphe coloré.
         */
        public ResultatColoration(int conflict, Graph graph) {
            this.conflict = conflict;
            this.graph = graph;
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
    }
