
package pixelmaster.core.api.gui;


import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 * This class represents slider with spinner, which are synchronized with each other.
 * User can use either of them to adjust value in text field.
 * When value in text field is modified using spinner, slider's is changed accordingly,
 * and vice-versa.
 */
public final class SliderWithSpinner extends JPanel
{
	private final JSpinner spinner;
	private final JSlider slider;
	private final SliderWithSpinnerModel sliderWithSpinnerModel;
	private final int orientation;
	
	
	/**
	 * Creates new slider with spinner which uses supplied model.
	 *
	 * @param model Ownership: {@link pixelmaster.core.api.domain.ObjectOwnership#OWNERSHIP_CALLEE}.
	 * @param orientation Orientation of slider: {@link javax.swing.SwingConstants#VERTICAL}
	 * 		or {@link javax.swing.SwingConstants#HORIZONTAL}.
	 * @param doDrawLabels Whether to draw labels on slider. If <code>true</code>,
	 * 		numeric labels will be put near minimum and maximum values.
	 */
	public SliderWithSpinner(SliderWithSpinnerModel model, int orientation, boolean doDrawLabels)
	{
		assert model != null;
		assert orientation == SwingConstants.HORIZONTAL || orientation == SwingConstants.VERTICAL;
		
		this.sliderWithSpinnerModel = model;
		this.orientation = orientation;
		
		this.setAlignmentY(Component.TOP_ALIGNMENT);
		
		// using inner objects as models
		this.spinner = SliderWithSpinner.createSpinner(model.getSpinnerModel());
		this.slider = SliderWithSpinner.createSlider(model.getSliderModel(), orientation, doDrawLabels);
		
		if (orientation == SwingConstants.VERTICAL)
		{
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(this.slider);
			this.add(this.spinner);
		}
		else
		{
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.add(this.spinner);
			this.add(this.slider);
		}
	}
	
	/**
	 * Changes current value of slider with spinner.
	 * 
	 * @param value New value.
	 */
	public void setValue(int value)
	{
		assert this.sliderWithSpinnerModel.getSliderModel().getMinimum() <= value
				&& value <= this.sliderWithSpinnerModel.getSliderModel().getMaximum();
		
		this.sliderWithSpinnerModel.getSliderModel().setValue(value);
		//this.sliderWithSpinnerModel.getSpinnerModel().setValue(value);
	}
	
	/**
	 * Adds listener that is notified each time spinner with slider changes its value.
	 *
	 * @param listener Listener to add.
	 */
	public void addChangeListener(ChangeListener listener)
	{
		this.spinner.addChangeListener(listener);
	}
	
	/**
	 * Removes listener from this slider with spinner.
	 *
	 * @param listener Listener to remove.
	 */
	public void removeChangeListener(ChangeListener listener)
	{
		this.spinner.removeChangeListener(listener);
	}
	
	private static JSlider createSlider(BoundedRangeModel model, int orientation, boolean doDrawLabels)
	{
		JSlider slider = new JSlider(model);
		
		slider.setOrientation(orientation);
		slider.setAlignmentY(Component.TOP_ALIGNMENT);
		
		if (doDrawLabels)
		{
			Hashtable <Integer, JLabel> labelTable = new Hashtable <Integer, JLabel> ();
			labelTable.put(new Integer((Integer) model.getMinimum()), new JLabel(((Integer) model.getMinimum()).toString()));
			labelTable.put(new Integer((Integer) model.getMaximum()), new JLabel(((Integer) model.getMaximum()).toString()));
			slider.setLabelTable(labelTable);
			slider.setPaintLabels(true);
		}
		
		//slider.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		
		return slider;
	}
	
	private static JSpinner createSpinner(SpinnerModel model)
	{
		JSpinner spinner = new JSpinner(model);
		
		JFormattedTextField textField = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		textField.setEditable(false);
		
		return spinner;
	}
}
