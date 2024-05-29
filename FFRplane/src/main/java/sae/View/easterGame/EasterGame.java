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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.OverlayLayout;
import javax.swing.Timer;
import org.jxmapviewer.viewer.GeoPosition;
import sae.Utils.IconUtil;
import sae.View.airport.MapCustom;

public class EasterGame extends JFrame {

    private static final IconUtil iconU = new IconUtil();
    private MapCustom map;
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
    ImageIcon artificialHorizonIndicatorIcon = new ImageIcon(System.getProperty("user.dir") + "\\src\\main\\java\\sae\\Assets\\artificialHorizonIndicator.gif");

    private JLabel labelInstru;
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
        this.setTitle("Super vous avez trouvé l'easter egg !");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        initComponents();
        iconU.setIcon(this);
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

        //System.out.println("StepX: " + stepX + ", StepY: " + stepY);
        map.moveMap(stepX, stepY);
        updateSense();
    }

    private void drawGame() {
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
        drawGame();
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
        map = new MapCustom();
        map.initGameMap(44.6858971, 3.4487081);

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
                levelGaz++;
                if (levelGaz == 2) {
                    addGazButton.setIcon(resizeIcon(airplaneThroAvgIcon, 102, 145));
                    planeObject.setVitesse(0.003);
                } else if (levelGaz == 3) {
                    addGazButton.setIcon(resizeIcon(airplaneThroMaxIcon, 102, 145));
                    planeObject.setVitesse(0.005);
                } else if (levelGaz > 3) {
                    levelGaz = 1;
                    addGazButton.setIcon(resizeIcon(airplaneThroMinIcon, 102, 145));
                    planeObject.setVitesse(0.001);
                }
            }
        });

        labelInstru = new JLabel(resizeIcon(artificialHorizonIndicatorIcon, 100, 100));
        instrumentboard.setLayout(new BorderLayout());
        instrumentboard.setOpaque(false);
        instrumentboard_2.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Alignement à droite pour le bouton
        instrumentboard_2.add(addGazButton);
        instrumentboard_2.add(labelInstru);
        instrumentboard.add(instrumentboard_2, BorderLayout.PAGE_END);
        p1.add(instrumentboard, BorderLayout.PAGE_END); // Placer instrumentboard en bas

        // Ajout des composants au conteneur principal
        getContentPane().add(map, BorderLayout.CENTER);
        getContentPane().add(p1, BorderLayout.CENTER); // Assurez-vous que p1 soit dans le centre

        // Ajuster les niveaux de z-order pour garantir le bon affichage
        getContentPane().setComponentZOrder(p1, 0);
        getContentPane().setComponentZOrder(map, 1);

        Timer timer = new Timer(30, e -> updateSense());
        timer.start();
    }

    private static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static void main(String[] args) {
        EasterGame easterGame = new EasterGame();
        easterGame.setVisible(true);
    }
}
