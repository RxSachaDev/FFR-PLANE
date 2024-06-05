/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sae.View.easterGame;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.OverlayLayout;
import javax.swing.Timer;
import javax.swing.border.Border;
import org.jxmapviewer.viewer.GeoPosition;
import sae.Utils.IconUtil;
import sae.View.airport.MapCustom;

public class EasterGame extends JFrame {

    private static final IconUtil iconU = new IconUtil();
    private MapCustom map = new MapCustom();
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();
    private JPanel instrumentboard = new JPanel();

    private final JLabel infosCommandLabel = new JLabel("Appuyez sur ZQSD pour vous déplacer");

    ImageIcon planeUpIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_up.png");
    ImageIcon planeDownIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_down.png");
    ImageIcon planeLeftIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_left.png");
    ImageIcon planeRightIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_right.png");
    ImageIcon planeUpLeftIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_up_left.png");
    ImageIcon planeUpRightIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_up_right.png");
    ImageIcon planeDownLeftIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_down_left.png");
    ImageIcon planeDownRightIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\plane_down_right.png");

    ImageIcon airplaneThroMaxIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\airplaneThro1.png");
    ImageIcon airplaneThroAvgIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\airplaneThro2.png");
    ImageIcon airplaneThroMinIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\airplaneThro3.png");
    ImageIcon artificialHorizonIndicatorIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\artificialH.gif");

    ImageIcon eiffelTowerIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\eiffelTowerIcon.png");
    ImageIcon tour1 = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\tour1.png");
    ImageIcon tour2 = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\tour2.png");
    ImageIcon liberteIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\liberté.png");

    private JLabel labelInstru = new JLabel();
    private JButton addGazButton = new JButton(airplaneThroMinIcon);
    private int levelGaz = 1;
    private Thread thread;
    private long sleep;
    private boolean start = true;

    private Plane planeObject = new Plane("Plane", new GeoPosition(40.7115201, 74.015944),
            planeUpIcon, planeDownIcon, planeLeftIcon, planeRightIcon,
            planeUpLeftIcon, planeUpRightIcon, planeDownLeftIcon, planeDownRightIcon);
    ;

    private double STEP_SIZE = planeObject.getVitesse();

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;

    public EasterGame() {
        this.setSize(600, 350);
        this.setTitle("Super vous avez trouvé un easter egg !");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //map.initGameMap(44.6858971, 3.4487081);
        iconU.setIcon(this);
        map.initGameMap(40.7107653, -74.0130491);
        initComponents();
        drawGame();
        start();
    }

    public void start() {
        getWidth();
        getHeight();
        thread = new Thread(() -> {
            while (start) {
                long startTime = System.nanoTime();
                render();
                update();
                long time = System.nanoTime() - startTime;
                sleep = (time < TARGET_TIME) ? (TARGET_TIME - time) / 1000000 : 0;
                sleep(sleep);
            }
        });
        thread.start();
    }

    private void update() {
        double stepX = 0;
        double stepY = 0;
        STEP_SIZE = planeObject.getVitesse();
        if (upPressed) {
            stepY += STEP_SIZE;
        }
        if (downPressed) {
            stepY -= STEP_SIZE;
        }
        if (leftPressed) {
            stepX -= STEP_SIZE;
        }
        if (rightPressed) {
            stepX += STEP_SIZE;
        }

        //System.out.println(STEP_SIZE);
        map.moveMap(stepX, stepY);
        updateSense();
    }

    private void drawGame() {
        map.addMonuments(new MonumentWaypoint("Tour Eiffel", new GeoPosition(48.8568764, 2.2948237), eiffelTowerIcon));
        map.addMonuments(new MonumentWaypoint("WORLD TRADE CENTER 1", new GeoPosition(40.7118114, -74.0126466), tour1));
        map.addMonuments(new MonumentWaypoint("WORLD TRADE CENTER 2", new GeoPosition(40.7107653, -74.0130491), tour2));
        map.addMonuments(new MonumentWaypoint("Statue de la Liberté", new GeoPosition(40.6892, -74.0445), liberteIcon));
        /*map.addMonuments(new MonumentWaypoint("Pyramides de Gizeh", new GeoPosition(29.9792, 31.1342), new ImageIcon("path/to/pyramidsIcon.png")));
        map.addMonuments(new MonumentWaypoint("Taj Mahal", new GeoPosition(27.1751, 78.0421), new ImageIcon("path/to/tajMahalIcon.png")));
        map.addMonuments(new MonumentWaypoint("Muraille de Chine", new GeoPosition(40.4319, 116.5704), new ImageIcon("path/to/greatWallIcon.png")));
        map.addMonuments(new MonumentWaypoint("Sydney Opera House", new GeoPosition(-33.8568, 151.2153), new ImageIcon("path/to/sydneyOperaHouseIcon.png")));
        map.addMonuments(new MonumentWaypoint("Christ Rédempteur", new GeoPosition(-22.9519, -43.2105), new ImageIcon("path/to/christRedeemerIcon.png")));
        map.addMonuments(new MonumentWaypoint("Empire State Building", new GeoPosition(40.748817, -73.985428), new ImageIcon("path/to/empireStateBuildingIcon.png")));*/
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                map.requestFocusInWindow();
            }
        });
    }

    private void updateSense() {
        if (upPressed && leftPressed && !rightPressed) {
            planeObject.setPlaneIcon(planeObject.planeUpLeftIcon, 100, 90);
        } else if (upPressed && rightPressed && !leftPressed) {
            planeObject.setPlaneIcon(planeObject.planeUpRightIcon, 100, 90);
        } else if (downPressed && leftPressed && !rightPressed) {
            planeObject.setPlaneIcon(planeObject.planeDownLeftIcon, 100, 90);
        } else if (downPressed && rightPressed && !leftPressed) {
            planeObject.setPlaneIcon(planeObject.planeDownRightIcon, 100, 90);
        } else if (upPressed && !downPressed) {
            planeObject.setPlaneIcon(planeObject.planeUpIcon, 80, 60);
        } else if (downPressed && !upPressed) {
            planeObject.setPlaneIcon(planeObject.planeDownIcon, 80, 60);
        } else if (leftPressed && !rightPressed) {
            planeObject.setPlaneIcon(planeObject.planeLeftIcon, 60, 80);
        } else if (rightPressed && !leftPressed) {
            planeObject.setPlaneIcon(planeObject.planeRightIcon, 60, 80);
        }

        planeObject.repaint();
    }

    private void render() {
        map.repaint();
    }

    private void sleep(long speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            Logger.getLogger(EasterGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initComponents() {
        planeObject.setPlaneIcon(planeUpIcon, 80, 60);
        getContentPane().setLayout(new OverlayLayout(getContentPane()));

        p1.setOpaque(false);
        p1.setLayout(new BorderLayout());

        p2.setOpaque(false);
        p2.setEnabled(false);
        p2.setLayout(new BorderLayout());
        p2.add(planeObject, BorderLayout.CENTER);

        p3.setOpaque(true);
        p3.setLayout(new FlowLayout());
        infosCommandLabel.setForeground(Color.BLACK);
        p3.add(infosCommandLabel);

        p1.add(p2, BorderLayout.CENTER);

        p1.add(p3, BorderLayout.SOUTH);

        map.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Z:
                        upPressed = true;
                        break;
                    case KeyEvent.VK_S:
                        downPressed = true;
                        break;
                    case KeyEvent.VK_Q:
                        leftPressed = true;
                        break;
                    case KeyEvent.VK_D:
                        rightPressed = true;
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Z:
                        upPressed = false;
                        break;
                    case KeyEvent.VK_S:
                        downPressed = false;
                        break;
                    case KeyEvent.VK_Q:
                        leftPressed = false;
                        break;
                    case KeyEvent.VK_D:
                        rightPressed = false;
                        break;
                }
            }
        });

        map.setFocusable(true);

        map.setOpaque(false);
        JPanel instrumentboard_2 = new JPanel();
        addGazButton.setPreferredSize(new Dimension(100, 150)); // Définir la taille préférée du bouton
        addGazButton.setBorder(null);
        addGazButton.setFocusable(false);
        addGazButton.setBackground(null); // Enlever le fond du bouton
        addGazButton.setIcon(resizeIcon(airplaneThroMinIcon, 102, 145));
        addGazButton.setContentAreaFilled(false);

        addGazButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (levelGaz == 1 || levelGaz == 4) {
                    levelGaz = 2;
                    addGazButton.setIcon(resizeIcon(airplaneThroAvgIcon, 102, 145));
                    planeObject.setVitesse(0.003);
                } else if (levelGaz == 2 || levelGaz == 5) {
                    levelGaz = 3;
                    addGazButton.setIcon(resizeIcon(airplaneThroMaxIcon, 102, 145));
                    planeObject.setVitesse(0.005);
                } else if (levelGaz == 3 || levelGaz == 6) {
                    levelGaz = 1;
                    addGazButton.setIcon(resizeIcon(airplaneThroMinIcon, 102, 145));
                    planeObject.setVitesse(0.001);
                }
            }
        });
        JPanel labelInstruPanel, bottomRightPanel;
        JButton planeTypeButton, actionsPlaneButton;
        planeTypeButton = new JButton("Type de l'avion");
        planeTypeButton.setPreferredSize(new Dimension(140, 30));
        actionsPlaneButton = new JButton("actions");
        actionsPlaneButton.setPreferredSize(new Dimension(140, 30));
        labelInstruPanel = new JPanel();
        bottomRightPanel = new JPanel();
        instrumentboard.setLayout(new BorderLayout());
        instrumentboard.setOpaque(false);
        instrumentboard_2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        instrumentboard_2.setBackground(new Color(0, 0, 0, 0));
        labelInstru.setPreferredSize(new Dimension(140, 140));
        labelInstru.setIcon(artificialHorizonIndicatorIcon);
        labelInstruPanel.setBackground(new Color(0, 0, 0, 220));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        labelInstruPanel.setBorder(border);
        labelInstruPanel.setLayout(new BorderLayout());
        labelInstruPanel.setPreferredSize(new Dimension(140, 140));
        labelInstruPanel.add(labelInstru, BorderLayout.CENTER);
        instrumentboard_2.add(labelInstruPanel);
        instrumentboard_2.add(addGazButton);
        instrumentboard.setOpaque(false); // Rendre le panneau transparent
        instrumentboard.add(instrumentboard_2, BorderLayout.PAGE_END);
        bottomRightPanel.setOpaque(false);
        bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.X_AXIS)); // Utiliser un layout en colonne
        bottomRightPanel.add(Box.createHorizontalGlue()); // Ajout d'un espace flexible à gauche
        bottomRightPanel.add(actionsPlaneButton);

        planeTypeButton.addActionListener((ActionEvent e) -> {
            TypeOfplaneDialog tdialog = new TypeOfplaneDialog(EasterGame.this, true, planeObject, map);
            tdialog.setLocationRelativeTo(null);
            tdialog.setVisible(true);
        });

        bottomRightPanel.add(planeTypeButton);
        bottomRightPanel.add(Box.createHorizontalGlue()); // Ajout d'un espace flexible à droite
        bottomRightPanel.setBackground(new Color(0, 0, 0, 0));
        instrumentboard.add(bottomRightPanel, BorderLayout.LINE_END); // Ajouter à droite
        p1.add(instrumentboard, BorderLayout.PAGE_START);

        // Ajout des composants au conteneur principal
        getContentPane().add(map, BorderLayout.CENTER);
        getContentPane().add(p1, BorderLayout.CENTER); // Assurez-vous que p1 soit dans le centre

        // Ajuster les niveaux de z-order pour garantir le bon affichage
        getContentPane().setComponentZOrder(p1, 0);
        getContentPane().setComponentZOrder(map, 1);

        requestFocusInWindowLater();

        Timer timer = new Timer(30, e -> updateSense());
        timer.start();
    }

    public void updateTypePlane(String typePlane) {
        planeObject.setTypePlane(typePlane);
    }

    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private void requestFocusInWindowLater() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                map.requestFocusInWindow();
            }
        });
    }

    public static void main(String[] args) {
        EasterGame easterGame = new EasterGame();
        easterGame.setVisible(true);
    }
}
