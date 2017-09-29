package homefinder;

import java.util.LinkedList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author alicia
 */
public class RangeSliderModel implements _RangeSliderModel {

    private int min, max, valMin, valMax;
    private int extent;
    private boolean minAdj, maxAdj;
    private LinkedList<ChangeListener> listeners;
    
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
        this.extent = extent;
    }

    @Override
    public void setValueMin(int valuemin) {
        if (valuemin < this.min) {
            valuemin = min;
        } else if (valuemin > this.valMax) {
            valuemin = this.valMax;
        }
        
        if(valuemin != this.valMin) {
            this.valMin = valuemin;
            for (ChangeListener listener : listeners) {
                listener.stateChanged(new ChangeEvent(this));
            }
        }
    }

    @Override
    public void setValueMax(int valuemax) {
        if (valuemax < this.valMin) {
            valuemax = this.valMin;
        } else if (valuemax > this.max) {
            valuemax = this.max;
        }

        if (valuemax != this.valMax) {
            this.valMax = valuemax;
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
    public int getValueMin() {
        return this.valMin;
    }

    @Override
    public int getValueMax() {
        return this.valMax;
    }

    @Override
    public void setValueMinAdjusting(boolean minadj) {
        this.minAdj = minadj;
    }

    @Override
    public void setValueMaxAdjusting(boolean maxadj) {
        this.maxAdj = maxadj;
    }

    @Override
    public boolean getValueMinAdjusting() {
        return this.minAdj;
    }

    @Override
    public boolean getValueMaxAdjusting() {
        return this.maxAdj;
    }

    @Override
    public void setRangeProperties(int valmin, int valmax, int extent, int min, int max, boolean minadj, boolean maxadj) {
        setMinimum(min);
        setMaximum(max);
        setExtent(extent);
        setValueMinAdjusting(minadj);
        setValueMaxAdjusting(maxadj);
        setValueMin(valmin);
        setValueMax(valmax);
    }
    
}
