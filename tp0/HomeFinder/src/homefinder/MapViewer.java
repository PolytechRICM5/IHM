package homefinder;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author bonhourg
 */
public class MapViewer extends JPanel implements ChangeListener {
    
    private List<Home> homes_list;
    private int home_size = 4;
    private int width;
    private int height;
    
    public MapViewer(int width, int height, List<Home> homes_list) 
    {
        super();
        this.width = width;
        this.height = height;
        this.homes_list = homes_list;
        this.setPreferredSize(new Dimension(width, height));
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        for (Home home : homes_list) 
        {
            if (
                    home.price >= HomeFinder.price_slider.getValue() && 
                    home.price <= HomeFinder.price_slider.getValueMax() &&
                    home.rooms >= HomeFinder.nb_rooms_slider.getValue() &&
                    home.rooms <= HomeFinder.nb_rooms_slider.getValueMax()
                ) 
            {
                g.drawOval(home.x - home_size / 2, home.y - home_size / 2, home_size, home_size);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        repaint();
    }

    
}
