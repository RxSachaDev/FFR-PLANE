/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.View.easterGame;

import sae.View.easterGame.EasterGame;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.jxmapviewer.viewer.GeoPosition;
import sae.View.airport.AirportWaypoint;
import sae.View.airport.CustomAeroportPoint;

/**
 *
 * @author fillo
 */
public class EasterPoint extends AirportWaypoint {

    private JButton button;
    private EasterGame easterGame;

    private void initButton() {
    button = new CustomAeroportPoint();
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

}
