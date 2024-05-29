/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.View;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import sae.Logiciel;

/**
 * Classe représentant un point d'aéroport sur une carte.
 * Un point d'aéroport est une extension d'un Waypoint par défaut, il inclut également un bouton associé permettant d'afficher le nom de l'aéroport.
 * Lorsque le bouton est cliqué, le nom de l'aéroport est affiché dans un JTextArea parent, dans MainFrame.
 * @author fillo
 */
public class Airportpoint extends DefaultWaypoint {

    // Le nom de l'aéroport
    private String name;
    
    // Le bouton associé à ce point d'aéroport
    private JButton button;

    /**
     * Initialise le bouton associé à ce point d'aéroport.
     */
    private void initButton() {
        button = new CustomAeroportPoint();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // Récupère le Frame parent du bouton
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(button);
                // Vérifie si le parent est une instance de Logiciel
                if (parentFrame instanceof Logiciel) {
                    // Si c'est le cas, affecte le nom de l'aéroport au JTextArea du Logiciel
                    Logiciel textAreaSetter = (Logiciel) parentFrame;
                    textAreaSetter.setJTextAreaText(Airportpoint.this.getName());
                } else {
                    // Sinon, affiche un message d'erreur
                    System.err.println("Le composant parent ne met pas en œuvre JTextAreaSetter.");
                }
            }
        });
    }

    /**
     * Renvoie le nom de l'aéroport.
     * @return Le nom de l'aéroport.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'aéroport.
     * @param name Le nom de l'aéroport à définir.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Renvoie le bouton associé à ce point d'aéroport.
     * @return Le bouton associé à ce point d'aéroport.
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Définit le bouton associé à ce point d'aéroport.
     * @param button Le bouton à associer à ce point d'aéroport.
     */
    public void setButton(JButton button) {
        this.button = button;
    }

    /**
     * Constructeur de la classe Airportpoint.
     * @param name Le nom de l'aéroport.
     * @param coord La position géographique de l'aéroport.
     */
    public Airportpoint(String name, GeoPosition coord) {
        // Appelle le constructeur de la classe parent avec la position géographique spécifiée
        super(coord);
        // Initialise le nom de l'aéroport et le bouton associé
        this.name = name;
        initButton();
    }
}
