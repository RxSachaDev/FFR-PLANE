package sae.view.jButton;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * Classe de bouton personnalisé représentant les points du model (Aeroports) sur une Map.
 */
public class CustomButtonMapPoint extends JButton {
    
    /**
     * Constructeur du bouton CustomAeroportPoint.
     */
    public CustomButtonMapPoint() {
        setContentAreaFilled(false);        // Rendre le bouton transparent        
        setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\airportIcon.png"));
        setCursor(new Cursor(Cursor.HAND_CURSOR));      
        setSize(new Dimension(30, 30));          
        setHorizontalAlignment(SwingConstants.CENTER);         
        setVerticalAlignment(SwingConstants.CENTER);        
        setBorder(null);       
        setFocusable(false);        
    }  
}
