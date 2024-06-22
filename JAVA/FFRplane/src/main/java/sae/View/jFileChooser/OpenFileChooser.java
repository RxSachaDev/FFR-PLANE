package sae.view.jFileChooser;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import sae.view.jDialog.LoadAirspaceDialog;
import sae.view.jDialog.LoadGraphDialog;

/**
 * OpenFileChooser est une extension de JFileChooser personnalisée pour gérer l'ouverture de fichiers spécifiques.
 * Il préconfigure les filtres de fichiers disponibles et définit le comportement de sélection de fichier.
 * Le répertoire de départ est également déterminé en fonction du parent de ce dialogue de fichier.
 * Si le parent est une instance de LoadAirspaceDialog, le répertoire par défaut est réglé sur le dossier des données d'aéroport.
 * Si le parent est une instance de LoadGraphDialog, le répertoire par défaut est réglé sur le dossier des tests de graphiques.
 * 
 * Ce composant est utilisé pour sélectionner un fichier dans l'interface utilisateur.
 * 
 * Il est configuré pour n'accepter que les fichiers et propose des filtres pour les fichiers .txt et .csv.
 * La taille préférée de la boîte de dialogue est définie sur 800x500 pixels.
 * 
 * @author mathe
 */
public class OpenFileChooser extends JFileChooser {
    
    /**
     * Constructeur par défaut qui initialise les paramètres du dialogue de choix de fichier.
     */
    public OpenFileChooser(){
        // Désactiver l'option de "Tous les fichiers" (Accept All)
        setAcceptAllFileFilterUsed(false);
        
        // Ajouter des filtres pour les fichiers TXT et CSV
        addChoosableFileFilter(new FileNameExtensionFilter("Fichier TXT (*.txt)", "txt"));
        addChoosableFileFilter(new FileNameExtensionFilter("Fichier CSV (*.csv)", "csv"));
        
        // Définir le titre de la boîte de dialogue
        setDialogTitle("Sélectionner un fichier");
        
        // Définir le texte du bouton "Ouvrir"
        setApproveButtonText("Ouvrir");
        
        // Ne sélectionner que des fichiers, pas des dossiers
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        // Définir le type de dialogue comme étant un dialogue d'ouverture
        setDialogType(JFileChooser.OPEN_DIALOG);
        
        // Autoriser la sélection de plusieurs fichiers (désactivé dans ce cas)
        setMultiSelectionEnabled(false);
        
        // Définir la taille préférée de la boîte de dialogue
        setPreferredSize(new Dimension(800, 500));
        
        // Définir le répertoire de départ en fonction du parent de cette boîte de dialogue
        if (getParent() instanceof LoadAirspaceDialog) {
            // Si le parent est LoadAirspaceDialog, utiliser le dossier des données d'aéroport
            setCurrentDirectory(new File(System.getProperty("user.dir") + "\\src\\main\\java\\data\\"));
        } else if (getParent() instanceof LoadGraphDialog) {
            // Si le parent est LoadGraphDialog, utiliser le dossier des tests de graphiques
            setCurrentDirectory(new File(System.getProperty("user.dir") + "\\src\\main\\java\\data\\test\\"));
        }
    }
}
