package sae.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageUtil {
    /**
     * Redimensionne une image avec l'interpolation de haute qualité (SCALE_SMOOTH).
     *
     * @param originalImage l'image d'origine à redimensionner
     * @param targetWidth la largeur cible
     * @param targetHeight la hauteur cible
     * @return une nouvelle BufferedImage redimensionnée
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }
}
