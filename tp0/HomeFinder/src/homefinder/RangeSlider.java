package homefinder;

import javax.swing.JSlider;

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
