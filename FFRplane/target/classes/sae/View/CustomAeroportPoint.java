/*
 * CustomAeroportPoint.java
 * 
 * Représente un bouton personnalisé pour afficher les points d'aéroport sur une carte.
 * 
 * @author fillo
 * @version 1.0
 */

package sae.View;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Classe de bouton personnalisé représentant les points d'aéroport sur une carte.
 */
public class CustomAeroportPoint extends JButton {
    
    /**
     * Constructeur du bouton CustomAeroportPoint.
     */
    public CustomAeroportPoint() {
        setContentAreaFilled(false);        // Rendre le bouton transparent        
        
        // Définir l'icône pour représenter un aéroport
        setIcon(new ImageIcon("C:/Users/fillo/OneDrive/Documents/BUT1_2023-2024/S2/BIG SAE/JAVA/FFRplane/src/main/java/sae/Assets/airportIcon.png"));
        setCursor(new Cursor(Cursor.HAND_CURSOR));      // Changer le curseur en curseur de main au survol
        setSize(new Dimension(30, 30));          // Définir la taille du bouton
        setHorizontalAlignment(SwingConstants.CENTER);         // Centrer l'icône horizontalement et verticalement
        setVerticalAlignment(SwingConstants.CENTER);        
        setBorder(null);        // Supprimer toute bordure autour du bouton
        setFocusable(false);        // Rendre le bouton non focusable

    }  
}
