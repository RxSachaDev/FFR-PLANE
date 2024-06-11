/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package sae.view.jDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import sae.models.errors.FileFormatError;
import sae.utils.IconUtil;
import sae.utils.Settings;
import sae.view.jFileChooser.OpenFileChooser;

/**
 * Cette classe représente une boîte de dialogue de chargement de graphique.
 * Elle étend la classe javax.swing.JDialog.
 *
 * @author fillo
 * @author mathe
 */
public class LoadGraphDialog extends javax.swing.JDialog {

    /**
     * Instance de la classe IconUtil utilisée pour configurer les icônes des
     * composants graphiques.
     */
    private static final IconUtil iconU = new IconUtil();

    /**
     * Crée une nouvelle instance de la boîte de dialogue de chargement de
     * graphique qui permet de spécifier les liens des fichiers qui crééront le
     * graphe
     *
     * @param parent La fenêtre parente de la boîte de dialogue.
     * @param modal Spécifie si la boîte de dialogue doit être modale ou non.
     */
    public LoadGraphDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setLocationRelativeTo(parent);
        initComponents();
        iconU.setIcon(this);
        this.setResizable(false);
        if(Settings.getAirportsFilePath()!=null) graphFileTextField.setText(Settings.getGraphTestPath());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        ChoicesPanel = new javax.swing.JPanel();
        CancelButton = new javax.swing.JButton();
        OkButton = new javax.swing.JButton();
        firstFileLabel = new javax.swing.JLabel();
        buttonChooseFile = new javax.swing.JButton();
        graphFileTextField = new javax.swing.JTextField();
        labelError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("load a graph");
        setResizable(false);

        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Sélectionnez les fichiers suivants");

        ChoicesPanel.setMaximumSize(new java.awt.Dimension(360, 80));
        ChoicesPanel.setMinimumSize(new java.awt.Dimension(360, 80));
        ChoicesPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 15));

        CancelButton.setBackground(new java.awt.Color(235, 173, 59));
        CancelButton.setForeground(new java.awt.Color(0, 0, 0));
        CancelButton.setText("Annuler");
        CancelButton.setFocusPainted(false);
        CancelButton.setMaximumSize(new java.awt.Dimension(100, 25));
        CancelButton.setMinimumSize(new java.awt.Dimension(100, 25));
        CancelButton.setPreferredSize(new java.awt.Dimension(100, 25));
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });
        ChoicesPanel.add(CancelButton);

        OkButton.setBackground(new java.awt.Color(235, 173, 59));
        OkButton.setForeground(new java.awt.Color(0, 0, 0));
        OkButton.setText("Ok");
        OkButton.setFocusPainted(false);
        OkButton.setMaximumSize(new java.awt.Dimension(100, 25));
        OkButton.setMinimumSize(new java.awt.Dimension(100, 25));
        OkButton.setPreferredSize(new java.awt.Dimension(100, 25));
        OkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OkButtonActionPerformed(evt);
            }
        });
        ChoicesPanel.add(OkButton);

        firstFileLabel.setText("Choisissez votre fichier graph-test :");

        buttonChooseFile.setBackground(new java.awt.Color(235, 173, 59));
        buttonChooseFile.setForeground(new java.awt.Color(0, 0, 0));
        buttonChooseFile.setText("Ouvrir");
        buttonChooseFile.setFocusPainted(false);
        buttonChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonChooseFileActionPerformed(evt);
            }
        });

        labelError.setForeground(new java.awt.Color(255, 51, 51));
        labelError.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(firstFileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ChoicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(graphFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(buttonChooseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(6, 6, 6))))
                        .addGap(42, 42, 42)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(labelError)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(titleLabel)
                .addGap(30, 30, 30)
                .addComponent(firstFileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonChooseFile)
                    .addComponent(graphFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(labelError)
                .addGap(3, 3, 3)
                .addComponent(ChoicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
        System.out.println("ok");
        try {
            Scanner scan = new Scanner(new File(graphFileTextField.getText())); //Déclanche l'éxception si le chemin d'exces est foireux
            Settings.setFlightsFilePath(graphFileTextField.getText());
            //GERER L'EXCEPTION FileFormatError
            dispose();
            SwingUtilities.getWindowAncestor(this).dispose();
            //OUVRIR LA FRAME GRAPHSTREAM
        } catch(FileNotFoundException e){
            labelError.setText("Le chemin d'accès est introuvable !");
        } catch(FileFormatError e) {
            
        }
        
        
    }//GEN-LAST:event_OkButtonActionPerformed

    private void buttonChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonChooseFileActionPerformed
        JFileChooser fileChooser = new OpenFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\src\\main\\java\\data\\test\\"));
        
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION) {
            graphFileTextField.setText(fileChooser.getSelectedFile().getPath());
        } else if(result == JFileChooser.CANCEL_OPTION) {}
        
    }//GEN-LAST:event_buttonChooseFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JPanel ChoicesPanel;
    private javax.swing.JButton OkButton;
    private javax.swing.JButton buttonChooseFile;
    private javax.swing.JLabel firstFileLabel;
    private javax.swing.JTextField graphFileTextField;
    private javax.swing.JLabel labelError;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
