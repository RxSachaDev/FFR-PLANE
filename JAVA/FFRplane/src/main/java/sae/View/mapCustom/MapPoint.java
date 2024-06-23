package sae.view.mapCustom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import sae.view.jFrame.MainFrame;
import sae.controller.Interfaces.ModelPoint;
import sae.view.jButton.CustomButtonMapPoint;

/**
 * Représente un point sur une carte (mapPoint).
 * Un mapPoint est une extension d'un Waypoint par défaut avec un bouton associé pour afficher des informations.
 * Lorsque le bouton est cliqué, les informations sur le point sont affichées dans une JTextArea du parent (MainFrame).
 * 
 * @author mathe
 * @author fillo
 */
public class MapPoint extends DefaultWaypoint implements Comparable<MapPoint> {

    private ModelPoint modelPoint;
    private JButton button;

    
    /**
     * Constructeur pour créer un MapPoint à partir d'un modèle de point spécifié.
     *
     * @param point Modèle de point contenant la position géographique et d'autres informations.
     */
    public MapPoint(ModelPoint point) {
        super(point.getGeoPosition()); // Appelle le constructeur de la classe parent avec la position géographique spécifiée
        this.modelPoint = point;
        initButton();
    }
    

    /**
     * Constructeur pour créer un MapPoint à partir d'une position géographique.
     *
     * @param geoPosition Position géographique du MapPoint à créer.
     */
    public MapPoint(GeoPosition geoPosition) {
        super(geoPosition); // Appelle le constructeur de la classe parent avec la position géographique spécifiée
        this.modelPoint = null;
        initButton();
    }
    

    /* ••••••••••••• MÉTHODES ••••••••••••• */
    
    
    /**
     * Initialise le bouton associé à ce MapPoint.
     * Utilise CustomButtonMapPoint comme type de bouton spécialisé.
     * Configure l'ActionListener pour afficher les informations du point dans le parent (MainFrame).
     */
    private void initButton() {
        button = new CustomButtonMapPoint();
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // Récupère le Frame parent du bouton
                MainFrame parent = (MainFrame) SwingUtilities.getWindowAncestor(button);
                parent.getTextAreaInfosSelect().setText(modelPoint.toString());
            }
        });
    }

    
    /**
     * Retourne une représentation en chaîne des informations sur le modelPoint associé.
     *
     * @return Représentation en chaîne des informations du modelPoint.
     */
    public String toStringModelPoint(){
        return modelPoint.toString();
    }
    
    
    /* ••••••••••••• GETTERS / SETTERS ••••••••••••• */
    
    
    /**
     * Définit le ModelPoint associé à ce MapPoint.
     *
     * @param point Nouveau MapPoint à associer à ce MapPoint.
     */
    public void setModelPoint(ModelPoint point) {
        this.modelPoint = point;
    }

    
    /**
     * Définit le bouton associé à ce MapPoint.
     *
     * @param button Nouveau bouton à associer à ce MapPoint.
     */
    public void setButton(JButton button) {
        this.button = button;
    }

    
    /**
     * Retourne le ModelPoint associé à ce MapPoint.
     *
     * @return ModelPoint associé à ce MapPoint.
     */
    public ModelPoint getModelPoint() {
        return modelPoint;
    }

    
    /**
     * Retourne le bouton associé à ce MapPoint.
     *
     * @return Bouton associé à ce MapPoint.
     */
    public JButton getButton() {
        return this.button;
    }

    
    /* ••••••••••••• MÉTHODES @OVERRIDE ••••••••••••• */
    
    
    /**
     * Retourne une représentation en chaîne du nom du ModelPoint.
     *
     * @return Représentation en chaîne du nom du ModelPoint.
     */
    @Override
    public String toString() {
        if(modelPoint!=null) return modelPoint.toStringName();
        return "";
    }

    @Override
    public int compareTo(MapPoint other) {
        return toString().compareTo(other.toString());
    }
}
