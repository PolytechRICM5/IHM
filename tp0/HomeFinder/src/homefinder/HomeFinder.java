package homefinder;

import javax.swing.JFrame;

/**
 *
 * @author alicia
 */
public class HomeFinder 
{

    public static int   minX = 0,
                        maxX = 100,
                        minY = 0,
                        maxY = 100,
                        minRooms = 2,
                        maxRooms = 8,
                        minPrice = 100000,
                        maxPrice = 500000;
                    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        JFrame w = new JFrame();
        RangeSlider rs = new RangeSlider(0, 100, 50, 40);
        rs.setMajorTickSpacing(10);
        rs.setPaintTicks(true);
        w.add(rs);
        w.pack();
        w.setVisible(true);
        
    }
    
}
