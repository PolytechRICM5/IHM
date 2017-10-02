package homefinder;

import javax.swing.JSlider;

/**
 *
 * @author bonhourg
 */
public class RangeSlider extends JSlider {

    RangeSliderModel model;
    
    public RangeSlider(int min, int max, int value, int extent) 
    {
        super(min, max, value);
        this.model = new RangeSliderModel(min, max, value, extent);
        this.model.addListener(changeListener);
        updateUI();
    }
    
    @Override
    public void setValue(int value) 
    {
        this.model.setValue(value);
    }
    
    @Override
    public int getValue() 
    {
        return model.getValue();
    }
    
    public void setExtent(int extent) 
    {
        this.model.setExtent(extent);
    }
    
    @Override
    public void setValueIsAdjusting(boolean b) 
    {
        this.model.setValueIsAdjusting(b);
    }
    
    @Override
    public boolean getValueIsAdjusting() 
    {
        return this.model.getValueIsAdjusting();
    }
    
}
