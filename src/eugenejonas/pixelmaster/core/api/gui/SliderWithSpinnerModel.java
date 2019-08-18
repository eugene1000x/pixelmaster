
package eugenejonas.pixelmaster.core.api.gui;


import javax.swing.*;
import javax.swing.event.*;


/**
 * This class represents model for {@link SliderWithSpinner}.
 * Model's state is defined by minimal, maximal and current position.
 */
public class SliderWithSpinnerModel
{
	private final class SpinnerModelImpl implements SpinnerModel
	{
		private SpinnerModelImpl()
		{
			//nothing
		}
		
		@Override
		public Object getValue()
		{
			return SliderWithSpinnerModel.this.sliderModel.getValue();
		}
		
		@Override
		public void setValue(Object value)
		{
			SliderWithSpinnerModel.this.sliderModel.setValue((Integer) value);
		}
		
		@Override
		public Object getNextValue()
		{
			return SliderWithSpinnerModel.this.sliderModel.getValue() + 1;
		}
		
		@Override
		public Object getPreviousValue()
		{
			return SliderWithSpinnerModel.this.sliderModel.getValue() - 1;
		}
		
		@Override
		public void addChangeListener(ChangeListener changeListener)
		{
			SliderWithSpinnerModel.this.sliderModel.addChangeListener(changeListener);
		}
		
		@Override
		public void removeChangeListener(ChangeListener changeListener)
		{
			SliderWithSpinnerModel.this.sliderModel.removeChangeListener(changeListener);
		}
	}
	
	
	private BoundedRangeModel sliderModel;
	private SpinnerModel spinnerModel = new SpinnerModelImpl();
	
	
	/**
	 * Constructs new model.
	 */
	public SliderWithSpinnerModel(int value, int minimum, int maximum)
	{
		assert minimum <= value && value <= maximum;
		
		this.sliderModel = new DefaultBoundedRangeModel(value, 0, minimum, maximum);
	}
	
	SpinnerModel getSpinnerModel()
	{
		return this.spinnerModel;
	}
	
	BoundedRangeModel getSliderModel()
	{
		return this.sliderModel;
	}
	
	/**
	 * Returns current position of slider and spinner.
	 */
	public int getValue()
	{
		return this.sliderModel.getValue();
	}
}
