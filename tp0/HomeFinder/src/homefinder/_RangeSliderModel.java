package homefinder;

import javax.swing.event.ChangeListener;

/**
 *
 * @author alicia
 */
public interface _RangeSliderModel {
    
    public void addListener(ChangeListener listener);
    public void removeListener(ChangeListener listener);
    
    public void setMaximum(int max);
    public void setMinimum(int min);
    
    public int getMaximum();
    public int getMinimum();
    
    public void setExtent(int extent);
    public void setValue(int valuemax);
    
    public int getExtent();
    public int getValue();
    
    public void setValueIsAdjusting(boolean adj);
    public boolean getValueIsAdjusting();
    
    public void setRangeProperties(int value, int extent, int min, int max, boolean adj);   
    
}
