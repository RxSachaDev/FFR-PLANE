/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package sae.View.easterGame;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import sae.View.airport.MapCustom;

/**
 *
 * @author fillo
 */
public class TypeOfplaneDialog extends javax.swing.JDialog {

    private MapCustom map;
    Plane planeIconChangerObject;

    /**
     * Creates new form typeOfplaneDialog
     */
    public TypeOfplaneDialog(java.awt.Frame parent, boolean modal, Plane planeIconChangerObject, MapCustom map) {
        super(parent, modal);
        initComponents();
        this.planeIconChangerObject = planeIconChangerObject;
        this.map = map;
        updatePlaneIcon();

        // Ajouter un écouteur d'événements à la ComboBox
        jtypeOfPlaneComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePlaneIcon(); // Mettre à jour l'icône lorsque l'élément est modifié
            }
        });
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                map.requestFocusInWindow();
            }
        });
    }

    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void updatePlaneIcon() {
        String selectedPlaneType = (String) jtypeOfPlaneComboBox.getSelectedItem();

        // Mise à jour de l'icône de l'avion en fonction du type sélectionné
        switch (selectedPlaneType) {
            case "Avion de ligne":
                ImageIcon planeUpIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_up.png");
                planeIconLabel.setIcon(resizeIcon(planeUpIcon, 143, 108)); // Redimensionner l'icône à la taille du label
                planeIconChangerObject.setTypePlane("Avion de ligne");
                break;
            case "Rafale":
                ImageIcon rafaleUpIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\rafale_up.png");
                planeIconLabel.setIcon(resizeIcon(rafaleUpIcon, 143, 108)); // Redimensionner l'icône à la taille du label
                planeIconChangerObject.setTypePlane("Rafale");
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        planeIconLabel = new javax.swing.JLabel();
        jtypeOfPlaneComboBox = new javax.swing.JComboBox<>();
        jButtonOkay = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        planeIconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        planeIconLabel.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_up.png"));

        jtypeOfPlaneComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Avion de ligne", "Rafale" }));

        jButtonOkay.setText("ok");
        jButtonOkay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkayActionPerformed(evt);
            }
        });

        jButtonCancel.setText("annuler");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jtypeOfPlaneComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(jButtonOkay, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(planeIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(planeIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtypeOfPlaneComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOkay)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonOkayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkayActionPerformed
        String selectedPlaneType = (String) jtypeOfPlaneComboBox.getSelectedItem();
        switch (selectedPlaneType) {
            case "Avion de ligne":
                // Charger toutes les icônes de l'avion de ligne appropriées
                ImageIcon planeUpIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_up.png");
                ImageIcon planeDownIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_down.png");
                ImageIcon planeLeftIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_left.png");
                ImageIcon planeRightIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_right.png");
                ImageIcon planeUpLeftIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_up_left.png");
                ImageIcon planeUpRightIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_up_right.png");
                ImageIcon planeDownLeftIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_down_left.png");
                ImageIcon planeDownRightIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_down_right.png");

                // Appeler la méthode setPlaneIcon pour définir toutes les icônes de l'avion de ligne d'un coup
                planeIconChangerObject.setPlaneIcon(resizeIcon(planeUpIcon, 60, 80), planeDownIcon, planeLeftIcon, planeRightIcon,
                        planeUpLeftIcon, planeUpRightIcon, planeDownLeftIcon, planeDownRightIcon);

                planeIconChangerObject.setTypePlane("Avion de ligne");
                break;

            case "Rafale":
                // Charger toutes les icônes Rafale appropriées
                ImageIcon rafaleUpIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\rafale_up.png");
                ImageIcon rafaleDownIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\rafale_down.png");
                ImageIcon rafaleLeftIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\rafale_left.png");
                ImageIcon rafaleRightIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\rafale_right.png");
                ImageIcon rafaleUpLeftIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\rafale_up_left.png");
                ImageIcon rafaleUpRightIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\rafale_up_right.png");
                ImageIcon rafaleDownLeftIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\rafale_down_left.png");
                ImageIcon rafaleDownRightIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\rafale_down_right.png");

                // Appeler la méthode setRafaleIcon pour définir toutes les icônes Rafale d'un coup
                planeIconChangerObject.setRafaleIcon(resizeIcon(rafaleUpIcon, 60, 80), rafaleDownIcon, rafaleLeftIcon, rafaleRightIcon,
                        rafaleUpLeftIcon, rafaleUpRightIcon, rafaleDownLeftIcon, rafaleDownRightIcon);

                planeIconChangerObject.setTypePlane("Rafale");
                break;
        }

        // Mettre à jour le type de l'avion dans EasterGame après la fermeture de la boîte de dialogue
        planeIconChangerObject.updateTypePlane(selectedPlaneType);
        dispose();

        map.requestFocusInWindow();

    }//GEN-LAST:event_jButtonOkayActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
        map.requestFocusInWindow();
    }//GEN-LAST:event_jButtonCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOkay;
    private javax.swing.JComboBox<String> jtypeOfPlaneComboBox;
    private javax.swing.JLabel planeIconLabel;
    // End of variables declaration//GEN-END:variables
}
