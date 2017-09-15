/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homefinder;

import java.util.LinkedList;
import javax.swing.JSlider;
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
    
    @Override
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void setMaximum(int max) {
        this.max = max;
    }

    @Override
    public void setMinimum(int min) {
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
        if (valuemin >= min && valuemin <= valMax-extent && valuemin%extent == min%extent) {
            this.valMin = valuemin;
        }
    }

    @Override
    public void setValueMax(int valuemax) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getExtent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getValueMin() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getValueMax() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValueMinAdjusting(boolean minadj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValueMaxAdjusting(boolean maxadj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getValueMinAdjusting() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getValueMaxAdjusting() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setRangeProperties(int valmin, int valmax, int extent, int min, int max, boolean minadj, boolean maxadj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
   
}
