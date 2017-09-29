/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homefinder;

import javax.swing.JFrame;

/**
 *
 * @author alicia
 */
public class HomeFinder {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JFrame w = new JFrame();
        RangeSlider rs = new RangeSlider(0, 100, 50, 60, 10);
        rs.setMajorTickSpacing(10);
        rs.setPaintTicks(true);
        w.add(rs);
        w.pack();
        w.setVisible(true);
        
    }
    
}
