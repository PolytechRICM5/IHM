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
    public void setValueMin(int valuemin);
    public void setValueMax(int valuemax);
    
    public int getExtent();
    public int getValueMin();
    public int getValueMax();
    
    public void setValueMinAdjusting(boolean minadj);
    public void setValueMaxAdjusting(boolean maxadj);
    public boolean getValueMinAdjusting();
    public boolean getValueMaxAdjusting();
    
    public void setRangeProperties(int valmin, int valmax, int extent, int min, int max, boolean minadj, boolean maxadj);   
    
}
