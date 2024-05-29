/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sae;


/**
 * Interface représentant un composant logiciel pouvant interagir avec un
 * JTextArea. Les classes implémentant cette interface doivent fournir une
 * fonctionnalité pour définir du texte dans un JTextArea.
 *
 * @author fillo
 */
public interface Logiciel {
    /**
     * Définit le texte à afficher dans un composant JTextArea.
     *
     * @param text Le texte à afficher dans le composant JTextArea.
     */
    void setJTextAreaText(String text);
    
}
