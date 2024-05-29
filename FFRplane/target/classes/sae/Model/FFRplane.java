/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package sae.Model;

import sae.Utils.IconUtil;
import sae.View.WelcomeFrame;

/**
 *
 * @author fillo
 */
public class FFRplane {
    private static final WelcomeFrame wframe = new WelcomeFrame();
    private static final IconUtil iconU = new IconUtil();

    public static void main(String[] args) {
        iconU.setIcon(wframe);
        wframe.setVisible(true);
    }
}