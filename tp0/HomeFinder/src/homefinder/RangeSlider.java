/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package homefinder;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

/**
 *
 * @author alicia
 */
public class RangeSlider extends JSlider implements _RangeSlider{
    
    private int valuemax;
    private boolean maxadj;

    @Override
    public void setValueMin(int valuemin) {
        this.setValue(valuemin);
    }

    @Override
    public void setValueMax(int valuemax) {
        this.valuemax = valuemax;
    }

    @Override
    public int getValueMin() {
        return this.getValue();
    }

    @Override
    public int getValueMax() {
        return this.valuemax;
    }

    @Override
    public void setValueMinAdjusting(boolean minadj) {
        this.setValueIsAdjusting(minadj);
    }

    @Override
    public void setValueMaxAdjusting(boolean maxadj) {
        this.maxadj = maxadj;
    }

    @Override
    public boolean getValueMinAdjusting() {
        return this.getValueIsAdjusting();
    }

    @Override
    public boolean getValueMaxAdjusting() {
        return this.maxadj;
    }

    @Override
    public void setRangeProperties(int valmin, int valmax, int extent, int min, int max, boolean minadj, boolean maxadj) {
  
    }

   
}
