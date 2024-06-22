/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sae.controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sae.view.jFrame.*;

/**
 * La classe Main est la classe principale de l'application. Elle initialise et
 * lance la JFrame d'accueil de l'application. Elle configure également les
 * icônes pour les composants graphiques.
 *
 * @author fillo
 * @author mathe
 * @author sacha
 */
public class Main {
    /**
     * La méthode principale de l'application. Elle initialise la jFrame
     * d'accueil et configure les icônes, puis affiche le cadre.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans
     * cette application).
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "100%");
        setPoliceNimbus();
        WelcomeFrame wframe = new WelcomeFrame();
        wframe.setVisible(true);
    }
    
    public static void setPoliceNimbus(){
        try {
            // Mettre en place le Look and Feel Nimbus
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
