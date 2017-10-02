package homefinder;

import java.util.LinkedList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author alicia
 */
public class RangeSliderModel  implements _RangeSliderModel {

    private int min, max, value;
    private int extent;
    private boolean adj;
    private LinkedList<ChangeListener> listeners;
    
    /**
     * Constructor with default values 0 to 100
     * with extent 10 and values on min and max
     */
    public RangeSliderModel(){
        this.min = 0;
        this.max = 100;
        this.value = 0;
        this.extent = 0;
        this.adj = false;
        listeners = new LinkedList<>();
    }
    
    /**
     * Constructor for the RangeSlider model with range interval and initial values.
     * 
     * @param min, the minimal value of the range of the rangeslider
     * @param max, the maximal value of the range of the rangeslider
     * @param value, the initial value of the lowest value
     * @param extent, the distance between the maximum value of the rangeslider and the max value
     */
    public RangeSliderModel(int min, int max, int value, int extent) {
        this.min = min;
        this.max = max;
        this.value = value;
        this.extent = extent;
        this.adj = false;
        listeners = new LinkedList<>();
    }
    
    @Override
    public void addListener(ChangeListener listener) {
        if(!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(ChangeListener listener) {
        if(listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    @Override
    public void setMaximum(int max) {
        for (ChangeListener listener : listeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
        this.max = max;
    }

    @Override
    public void setMinimum(int min) {
        for (ChangeListener listener : listeners) {
            listener.stateChanged(new ChangeEvent(this));
        }
        this.min = min;
    }

    @Override
    public int getMaximum() {
        return this.max;
    }

    @Override
    public int getMinimum() {
        return this.min;
    }

    @Override
    public void setExtent(int extent) {
        if (extent >= this.max - this.value) {
            extent = this.value+1;
        } else if (extent > this.max) {
            extent = this.max;
        }

        if (extent != this.extent) {
            this.extent = extent;
            for (ChangeListener listener : listeners) {
                listener.stateChanged(new ChangeEvent(this));
            }
        } 
    }

    @Override
    public void setValue(int value) {
        if (value < this.min) {
            value = min;
        } else if (value >= this.max - this.extent) {
            value = this.max - extent - 1;
        }
        
        if(value != this.value) {
            this.value = value;
            for (ChangeListener listener : listeners) {
                listener.stateChanged(new ChangeEvent(this));
            }
        }
    }

    @Override
    public int getExtent() {
        return this.extent;
    }


    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void setValueIsAdjusting(boolean adj) {
        this.adj = adj;
    }

    @Override
    public boolean getValueIsAdjusting() {
        return this.adj;
    }

    @Override
    public void setRangeProperties(int value, int extent, int min, int max, boolean adj) {
        setMinimum(min);
        setMaximum(max);
        setValue(value);
        setExtent(extent);
        setValueIsAdjusting(adj);
    }
    
}
