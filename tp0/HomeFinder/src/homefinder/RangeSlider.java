package homefinder;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

/**
 *
 * @author bonhourg
 */
public class RangeSlider extends JSlider {

    RangeSliderModel model = new RangeSliderModel();
    
    public RangeSlider(int min, int max, int minVal, int maxVal, int extent) {
        super(min, max, minVal);
        this.model.setRangeProperties(minVal, maxVal, extent, min, max, false, false);
    }
    
}
