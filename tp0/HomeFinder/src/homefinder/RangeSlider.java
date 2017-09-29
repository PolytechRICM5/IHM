package homefinder;

import javax.swing.JSlider;

/**
 *
 * @author bonhourg
 */
public class RangeSlider extends JSlider {

    RangeSliderModel model;
    
    public RangeSlider(int min, int max, int minVal, int maxVal, int extent) {
        super(min, max, minVal);
        this.model = new RangeSliderModel(min, max, extent, minVal, maxVal);
        this.model.addListener(changeListener);
        updateUI();
    }
    
    @Override
    public void setValue(int value) {
        this.model.setValueMin(value);
    }
    
    public void setValueMin(int valuemin) {
        setValue(valuemin);
    }
    
    public void setValueMax(int valuemax) {
        this.model.setValueMax(valuemax);
    }
    
    @Override
    public int getValue() {
        return model.getValueMin();
    }
    
    public int getValueMin() {
        return this.getValue();
    }
    
    public int getValueMax() {
        return model.getValueMax();
    }
    
    @Override
    public void setValueIsAdjusting(boolean b) {
        this.model.setValueMinAdjusting(b);
    }
    
    public void setValueMinIsAdjusting(boolean adj) {
        setValueIsAdjusting(adj);
    }
    
    public void setValueMaxIsAdjusting(boolean adj) {
        this.model.setValueMaxAdjusting(adj);
    }
    
    @Override
    public boolean getValueIsAdjusting() {
        return this.model.getValueMinAdjusting();
    }
    
    public boolean getValueMinIsAdjusting() {
        return getValueIsAdjusting();
    }
    
    public boolean getValueMaxIsAdjusting() {
        return this.model.getValueMaxAdjusting();
    }
    
}
