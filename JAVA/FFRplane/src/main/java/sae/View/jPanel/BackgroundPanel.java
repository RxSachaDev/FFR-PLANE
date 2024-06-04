/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.View.jPanel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author mathe
 */
public class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;

    // Constructeur pour charger l'image
    public BackgroundPanel(String filePath) {
        try {
            backgroundImage = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner l'image d'arrière-plan redimensionnée et centrée
        if (backgroundImage != null) {
            int imageWidth = backgroundImage.getWidth();
            int imageHeight = backgroundImage.getHeight();

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
