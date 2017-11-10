package homefinder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author alicia
 */
public class HomeFinder 
{

    public static int   minX = 0,
                        maxX = 500,
                        minY = 0,
                        maxY = 500,
                        minRooms = 2,
                        maxRooms = 8,
                        minPrice = 100000,
                        maxPrice = 500000;
                    
    public static List<Home> homes_list;
    public static int nb_homes = 100;
    
    public static RangeSlider   nb_rooms_slider,
                                price_slider;
    
    public static JLabel        rooms_label,
                                price_label;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        JFrame w = new JFrame("Home Finder");
        w.setLayout(new BorderLayout());
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        homes_list = new LinkedList<Home>();
        for (int i = 0; i < nb_homes; i++) {
            homes_list.add(new Home(minX, maxX, minY, maxY, minRooms, maxRooms, minPrice, maxPrice));
        }
        
        MapViewer map = new MapViewer(maxX - minX, maxY - minY,homes_list);
        w.add(map, BorderLayout.CENTER);
        
        JPanel choice_panel = new JPanel();
        choice_panel.setLayout(new BoxLayout(choice_panel, BoxLayout.Y_AXIS));
        w.add(choice_panel, BorderLayout.EAST);
        
        nb_rooms_slider = new RangeSlider(minRooms, maxRooms, minRooms, maxRooms);
        nb_rooms_slider.setMajorTickSpacing(1);
        nb_rooms_slider.setPaintTicks(true);
        nb_rooms_slider.addChangeListener(map);
        choice_panel.add(new JLabel("Room number"));
        rooms_label = new JLabel(nb_rooms_slider.getValue() + " - " + nb_rooms_slider.getValueMax());
        nb_rooms_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                rooms_label.setText(nb_rooms_slider.getValue() + " - " + nb_rooms_slider.getValueMax());
            }
        });
        choice_panel.add(rooms_label);
        choice_panel.add(nb_rooms_slider);
        
        price_slider = new RangeSlider(minPrice, maxPrice, minPrice, maxPrice);
        price_slider.setMajorTickSpacing(10000);
        price_slider.setPaintTicks(true);
        price_slider.addChangeListener(map);
        choice_panel.add(new JLabel("Price"));
        price_label = new JLabel(price_slider.getValue() + " - " + price_slider.getValueMax());
        price_slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                price_label.setText(price_slider.getValue() + " - " + price_slider.getValueMax());
            }
        });
        choice_panel.add(price_label);
        choice_panel.add(price_slider);
        
        /*
        RangeSlider rs = new RangeSlider(100, 200, 120, 140);
        rs.setMajorTickSpacing(2);
        rs.setPaintTicks(true);
        */
        
        w.pack();
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        w.setLocation((int) ((dimension.getWidth() - w.getWidth()) / 2),
                (int) ((dimension.getHeight() - w.getHeight()) / 2));
        
        w.setVisible(true);
     
    }
    
    
}
