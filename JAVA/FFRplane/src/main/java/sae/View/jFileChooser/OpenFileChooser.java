/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.view.jFileChooser;

import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import sae.view.jDialog.*;


/**
 *
 * @author mathe
 */
public class OpenFileChooser extends JFileChooser {
    public OpenFileChooser(){
        setAcceptAllFileFilterUsed(false);
        addChoosableFileFilter(new FileNameExtensionFilter("Fichier TXT (*.txt)", "txt"));
        addChoosableFileFilter(new FileNameExtensionFilter("Fichier CSV (*.csv)", "csv"));
        setDialogTitle("Selectionner un fichier");
        setApproveButtonText("Ouvrir");
        setFileSelectionMode(JFileChooser.FILES_ONLY);
        setDialogType(JFileChooser.OPEN_DIALOG);
        setMultiSelectionEnabled(false);
        setPreferredSize(new Dimension(800,500));
        if(getParent() instanceof LoadAirspaceDialog) {
            setCurrentDirectory(new File(System.getProperty("user.dir") + "\\src\\main\\java\\data\\"));
        } else if(getParent() instanceof LoadGraphDialog) {
            setCurrentDirectory(new File(System.getProperty("user.dir") + "\\src\\main\\java\\data\\test\\"));
        }
    }
}
