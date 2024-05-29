/*  
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.Utils;

import java.awt.Window;
import javax.swing.ImageIcon;

/**
 *
 * @author fillo
 */
public class IconUtil {

    private String ICON_PATH = "../ressources/Assets/logo.png";

    public void setIcon(Window window) {
        java.net.URL imgURL = getClass().getResource(ICON_PATH);

        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            window.setIconImage(icon.getImage());
        } else {
            System.err.println("Couldn't find file: " + ICON_PATH);
        }
    }
}