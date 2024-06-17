/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.view.easterGame;

/**
 *
 * @author fillo
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jxmapviewer.viewer.GeoPosition;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class MonumentWaypoint extends EasterPoint {

    private String name;
    private ImageIcon icon;
    private JButton button;

    public MonumentWaypoint(String name, GeoPosition coord, ImageIcon icon) {
        super(coord);
        this.name = name;
        this.icon = icon;
        initButton();
    }

    public String getName() {
        return name;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    private void initButton() {
        button = new JButton(icon);
        button.setContentAreaFilled(false); // Rendre le fond transparent
        button.setBorderPainted(false); // Supprimer le contour du bouton
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logique de gestion de l'action lorsque le bouton est cliqué
                JOptionPane.showMessageDialog(null, name);
                // Redonner le focus à la carte après la fermeture du JOptionPane
                // On va utiliser SwingUtilities.invokeLater pour s'assurer que le focus soit redonné après que le JOptionPane soit fermé
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        button.getParent().requestFocusInWindow(); // Ou utilisez map.requestFocusInWindow() si 'map' est accessible ici
                    }
                });
            }
        });

    }

    @Override
    public JButton getButton() {
        return button;
    }
}
