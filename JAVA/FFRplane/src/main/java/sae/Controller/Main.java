/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sae.controller;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sae.utils.IconUtil;
import sae.view.jFrame.WelcomeFrame;

/**
 * La classe Main est la classe principale de l'application. Elle initialise et
 * lance la JFrame d'accueil de l'application. Elle configure également les
 * icônes pour les composants graphiques.
 *
 * @author fillo
 */
public class Main {

    /**
     * La jFrame d'accueil de l'application qui propose les différentes actions.
     */
    private static final WelcomeFrame wframe = new WelcomeFrame();

    /**
     * Object IconUtil pour configurer les icônes des composants graphiques.
     */
    private static final IconUtil iconU = new IconUtil();

    /**
     * La méthode principale de l'application. Elle initialise la jFrame
     * d'accueil et configure les icônes, puis affiche le cadre.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans
     * cette application).
     */
    public static void main(String[] args) {

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
        iconU.setIcon(wframe);
        wframe.setVisible(true);
    }

}
