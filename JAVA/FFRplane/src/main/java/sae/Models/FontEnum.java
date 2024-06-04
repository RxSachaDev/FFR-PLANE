/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Models;

/**
 * Énumération définissant des méthodes qui renvoient des généralités (par exemple des chaînes de caractères).
 * Cette énumération contient une seule valeur pour l'exemple : COMPARAISON2NOEUDS.
 * Chaque valeur de l'énumération peut être convertie en une chaîne de caractères.
 * 
 * @author fillo
 */
public enum FontEnum {
    
    /**
     * Compare les 2 nœuds sélectionnés.
     */
    COMPARAISON2NOEUDS {
        @Override
        public String toString() {
            return "Comparer les 2 noeuds sélectionnés";
        }
    }
}