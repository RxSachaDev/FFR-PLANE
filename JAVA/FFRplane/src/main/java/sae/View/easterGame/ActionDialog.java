/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package sae.view.easterGame;

import java.awt.Color;

import sae.view.mapCustom.*;

/**
 *
 * @author fillo
 */
public class ActionDialog extends javax.swing.JDialog {

    private MapCustom map;

    /**
     * Creates new form ActionDialog
     */
    public ActionDialog(java.awt.Frame parent, boolean modal, MapCustom map) {
        super(parent, modal);
        initComponents();
        this.map = map;
        canceljButton.setBackground(new Color(235, 173, 59));
        okButton.setBackground(new Color(235, 173, 59));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        locationComboBox = new javax.swing.JComboBox<>();
        canceljButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        locationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "New York", "Paris" }));

        canceljButton.setText("Cancel");
        canceljButton.setMaximumSize(new java.awt.Dimension(85, 25));
        canceljButton.setMinimumSize(new java.awt.Dimension(85, 25));
        canceljButton.setPreferredSize(new java.awt.Dimension(85, 25));
        canceljButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canceljButtonActionPerformed(evt);
            }
        });

        okButton.setText("Ok");
        okButton.setMaximumSize(new java.awt.Dimension(85, 25));
        okButton.setMinimumSize(new java.awt.Dimension(85, 25));
        okButton.setPreferredSize(new java.awt.Dimension(85, 25));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(canceljButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(locationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(canceljButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        String Combochoice = (String) locationComboBox.getSelectedItem();
        switch (Combochoice) {
            case "New York":
                map.initGameMap(40.7245649, -73.9268405);
                break;
            case "Paris":
                map.initGameMap(48.8351816, 2.297795);
                break;
        }
        dispose();
        map.requestFocusInWindow();
    }//GEN-LAST:event_okButtonActionPerformed

    private void canceljButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canceljButtonActionPerformed
        dispose();
        map.requestFocusInWindow();
    }//GEN-LAST:event_canceljButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton canceljButton;
    private javax.swing.JComboBox<String> locationComboBox;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
}
