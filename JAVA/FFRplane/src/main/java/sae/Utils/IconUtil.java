package sae.utils;

import java.awt.Window;
import javax.swing.ImageIcon;

/**
 * La classe IconUtil fournit des objets utiles pour définir l'icône d'une fenêtre Swing.
 * Elle charge l'icône à partir d'un fichier spécifié et la définit comme icône de la fenêtre.
 * Si le fichier d'icône n'existe pas, elle affiche un message d'erreur.
 * 
 * @author fillo
 */
public class IconUtil {

    /**
     * Le chemin d'accès par défaut vers le fichier d'icône.
     */
    private final String ICON_PATH = "\\src\\main\\java\\sae\\Assets\\logo.png";

    
    /**
     * Définit l'icône pour la fenêtre spécifiée.
     * Charge l'icône à partir du chemin d'accès spécifié et la définit comme icône de la fenêtre.
     * 
     * @param window La fenêtre pour laquelle l'icône doit être définie.
     */
    public void setIcon(Window window) {
        String userDir = System.getProperty("user.dir");
        String fullPath = userDir + ICON_PATH;

        java.io.File imgFile = new java.io.File(fullPath);
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(fullPath);
            window.setIconImage(icon.getImage());
        } else {
            System.err.println("Impossible de trouver le fichier : " + fullPath);
        }
    }
}