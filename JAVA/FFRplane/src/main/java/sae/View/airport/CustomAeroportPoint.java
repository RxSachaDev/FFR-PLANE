/*
 * CustomAeroportPoint.java
 * 
 * Représente un bouton personnalisé pour afficher les points d'aéroport sur une carte.
 * 
 * @author fillo
 * @version 1.0
 */

package sae.view.airport;

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
        setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\airportIcon.png"));
        setCursor(new Cursor(Cursor.HAND_CURSOR));      
        setSize(new Dimension(30, 30));          
        setHorizontalAlignment(SwingConstants.CENTER);         
        setVerticalAlignment(SwingConstants.CENTER);        
        setBorder(null);       
        setFocusable(false);        
    }  
}
