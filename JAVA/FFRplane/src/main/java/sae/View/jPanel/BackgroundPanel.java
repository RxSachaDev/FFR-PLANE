package sae.view.jPanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel personnalisé avec une image d'arrière-plan redimensionnée et centrée.
 * Utilisé pour afficher une image spécifiée lors de la création du panel.
 * L'image est redimensionnée pour remplir complètement le panel tout en conservant l'aspect ratio.
 * Si l'image ne peut pas être chargée, une exception est affichée dans la console.
 * 
 * @author mathe
 */
public class BackgroundPanel extends JPanel {
    
    private BufferedImage backgroundImage;

    /**
     * Constructeur pour initialiser le panel avec une image d'arrière-plan.
     *
     * @param filePath le chemin vers le fichier de l'image à charger
     */
    public BackgroundPanel(String filePath) {
        try {
            backgroundImage = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /**
     * Méthode Override pour dessiner l'image d'arrière-plan redimensionnée et centrée.
     * 
     * @param g l'objet Graphics utilisé pour dessiner
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner l'image d'arrière-plan redimensionnée et centrée
        if (backgroundImage != null) {
            int imageWidth = backgroundImage.getWidth(this);
            int imageHeight = backgroundImage.getHeight(this);

            int panelWidth = this.getWidth();
            int panelHeight = this.getHeight();

            // Calculer le ratio de redimensionnement tout en conservant l'aspect ratio
            double widthRatio = (double) panelWidth / imageWidth;
            double heightRatio = (double) panelHeight / imageHeight;
            double ratio = Math.max(widthRatio, heightRatio);

            int newWidth = (int) (imageWidth * ratio);
            int newHeight = (int) (imageHeight * ratio);

            // Calculer les coordonnées pour centrer l'image
            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newHeight) / 2;

            g.drawImage(backgroundImage, x, y, newWidth, newHeight, this);
        }
    }
}
