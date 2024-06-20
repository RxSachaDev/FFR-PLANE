/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package sae.view.jDialog;

import javax.swing.SpinnerNumberModel;

import sae.utils.*;

/**
 * Cette classe correspond à la dialog comportant tout les éléments liés aux
 * couleurs et permet l'édition et la modification de tout ces éléments
 *
 * @author fillo
 */
public class ColorationsDialog extends javax.swing.JDialog {

    /**
     * Instance de la classe IconUtil utilisée pour configurer les icônes des
     * composants graphiques.
     */
    private static final IconUtil iconU = new IconUtil();

    /**
     * Constructeur de la classe ColorationsDialog.
     *
     * @param parent La fenêtre parente de ce dialogue.
     * @param modal Indique si le dialogue est modal ou non.
     */
    public ColorationsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setLocationRelativeTo(parent);
        initComponents();
        iconU.setIcon(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        EditableItemsPanel = new javax.swing.JPanel();
        KmaxLabel = new javax.swing.JLabel();
        KmaxSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        safetyMarginLabel = new javax.swing.JLabel();
        safetyMarginSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        ChoicesPanel = new javax.swing.JPanel();
        CancelButton = new javax.swing.JButton();
        OkButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("FFRplane - Paramètres de Coloration");
        setPreferredSize(null);
        setResizable(false);
        setSize(new java.awt.Dimension(400, 230));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Modifiez ces paramètres");

        KmaxLabel.setText("K-Max (niveau de vols) :");

        KmaxSpinner.setValue(Settings.getKmax());

        safetyMarginLabel.setText("Marge de sécurité (en min) : ");

        safetyMarginSpinner.setValue(Settings.getSafetyMargin());

        javax.swing.GroupLayout EditableItemsPanelLayout = new javax.swing.GroupLayout(EditableItemsPanel);
        EditableItemsPanel.setLayout(EditableItemsPanelLayout);
        EditableItemsPanelLayout.setHorizontalGroup(
            EditableItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditableItemsPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(EditableItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(KmaxLabel)
                    .addComponent(safetyMarginLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(EditableItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(safetyMarginSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(KmaxSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );
        EditableItemsPanelLayout.setVerticalGroup(
            EditableItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditableItemsPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(EditableItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(KmaxLabel)
                    .addComponent(KmaxSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(EditableItemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(safetyMarginLabel)
                    .addComponent(safetyMarginSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChoicesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(EditableItemsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addComponent(EditableItemsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(ChoicesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void OkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OkButtonActionPerformed
        Settings.setKmax((int)KmaxSpinner.getValue());
        Settings.setSafetyMargin((int)safetyMarginSpinner.getValue());
        dispose();
    }//GEN-LAST:event_OkButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JPanel ChoicesPanel;
    private javax.swing.JPanel EditableItemsPanel;
    private javax.swing.JLabel KmaxLabel;
    private javax.swing.JSpinner KmaxSpinner;
    private javax.swing.JButton OkButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel safetyMarginLabel;
    private javax.swing.JSpinner safetyMarginSpinner;
    // End of variables declaration//GEN-END:variables
}
