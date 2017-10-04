package homefinder;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author Gilles B. & Alicia A.
 */
public class RangeSlider extends JSlider {

	/* This UID was necessary because of the Serializable properties of this class */
	private static final long serialVersionUID = -6605208274396436620L;
	RangeSliderModel model;
	
	/**
	 * Constructor for the RangeSlider
	 * 
	 * @param min the minimal value for the slider
	 * @param max the maximal possible value for the slider
	 * @param valuemin the initial value for the lower thumb
	 * @param valuemax the initial value for the upper thumb
	 */
    public RangeSlider(int min, int max, int valuemin, int valuemax) 
    {
        super(min, max, valuemin); 
        /* Using the Extent of the BoudedRangeModel as the gap between the lowest and upper value of the slider */
        this.getModel().setExtent(valuemax - valuemin);
        updateUI();
    }
    
    @Override
    /**
     * Setting the lowest value of the slider if possible
     * @param value, the value we want to set
     */
    public void setValue(int value) {
    	BoundedRangeModel model = getModel();
    	if(value < this.getValueMax() && value >= this.getMinimum()) {
    		int changed = model.getValue() - value;
        	model.setExtent(model.getExtent() + changed); // Modifying the extent so that the upper value doesn't change
            model.setValue(value);
    	}    	
    }
    
    /**
     * Setting the upper value of the RangeSlider if possible
     * @param valuemax
     */
    public void setValueMax(int valuemax) {
    	BoundedRangeModel model = this.getModel();
    	if(valuemax > this.getValue() && valuemax <= model.getMaximum()) {
    		model.setExtent(valuemax - model.getValue());
    	}
    }
    
    /**
     * Getter for the upper value
     * @return The current value of the upper thumb
     */
    public int getValueMax() {
    	BoundedRangeModel model = this.getModel();
    	return model.getValue() + model.getExtent();
    }
    
    @Override
    /**
     * Updating the RangeSliderUI
     */
    public void updateUI() {
    	setUI(new RangeSliderUI(this));
    	updateLabelUIs();
    }
    
}
