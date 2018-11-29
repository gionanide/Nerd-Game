/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nerdgame;

import javax.swing.JFrame;



/**
 *
 * @author aggeliki
 */
public class NerdGame {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //start game
        GUI nerdgame = new GUI();
        nerdgame.setVisible(true);
        
        nerdgame.setSize(350, 500);
        nerdgame.setLocationRelativeTo(null);
        
        nerdgame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
