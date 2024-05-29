/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sae.Controller;

import sae.Utils.IconUtil;
import sae.View.jFrame.WelcomeFrame;

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
    
    
    public static final String FICHIER_AEROPORTS = "./data/aeroports.txt";
    public static final String FICHIER_VOLS = "./data/vol-test4.csv";
    private static int k_max = 2;

    /**
     * La méthode principale de l'application. Elle initialise la jFrame
     * d'accueil et configure les icônes, puis affiche le cadre.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans
     * cette application).
     */
    public static void main(String[] args) {
        iconU.setIcon(wframe);
        wframe.setVisible(true);
    }

}
