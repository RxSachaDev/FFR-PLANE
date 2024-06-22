/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.view.easterGame;

import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jxmapviewer.viewer.GeoPosition;

import sae.models.airports.Airport;
import sae.view.mapCustom.*;
import sae.view.jButton.*;

/**
 *
 * @author fillo
 */
public class EasterPoint extends MapPoint {

    private JButton button;
    private EasterGame easterGame;

    private void initButton() {
    button = new CustomButtonMapPoint();
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
            // Récupère le Frame parent du bouton
            Window windowAncestor = SwingUtilities.getWindowAncestor(button);
            if(windowAncestor instanceof Frame){
                Frame parentFrame = (Frame) windowAncestor;
                easterGame = new EasterGame();
                //easterGame.start();
                easterGame.setVisible(true);
            } else {
                JDialog dialog = new JDialog();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                JOptionPane.showMessageDialog(dialog, "Le composant parent ne met pas en la jdialog", "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
        }
    });
}

    public EasterPoint(GeoPosition coord) {
        // Appelle le constructeur de la classe parent avec la position géographique spécifiée
        super(coord); 
        super.setModelPoint(new Airport("IUT c'est bizarre", coord));
        initButton();
    }

    /**
     * getter Renvoie le bouton associé à ce point.
     *
     * @return Le bouton associé à ce point.
     */
    public JButton getButton() {
        return button;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}
