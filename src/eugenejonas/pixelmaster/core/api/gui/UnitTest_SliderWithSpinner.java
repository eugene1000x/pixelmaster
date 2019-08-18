
package eugenejonas.pixelmaster.core.api.gui;


import javax.swing.*;
import javax.swing.event.*;

import org.junit.*;


public final class UnitTest_SliderWithSpinner
{
	private static final class NotificationCounter implements ChangeListener
	{
		private boolean wasNotified = false;
		
		
		private NotificationCounter()
		{
			//nothing
		}
		
		@Override
		public void stateChanged(ChangeEvent event)
		{
			this.wasNotified = true;
		}
		
		private boolean wasNotified()
		{
			return this.wasNotified;
		}
		
		private void reset()
		{
			this.wasNotified = false;
		}
	}
	
	
	private SliderWithSpinner sliderWithSpinner;
	private SliderWithSpinnerModel sliderWithSpinnerModel;
	private NotificationCounter counter;
	
	
	@Before
	public void setUp()
	{
		this.sliderWithSpinnerModel = new SliderWithSpinnerModel(0, 0, 100);
		this.sliderWithSpinner = new SliderWithSpinner(this.sliderWithSpinnerModel, SwingConstants.VERTICAL, true);
		this.counter = new NotificationCounter();
	}
	
	@Test
	public void test_addChangeListener_1()
	{
		this.sliderWithSpinner.addChangeListener(this.counter);
		this.sliderWithSpinner.setValue(40);
		
		Assert.assertTrue(this.counter.wasNotified());
	}
	
	@Test
	public void test_addChangeListener_2()
	{
		this.sliderWithSpinner.addChangeListener(this.counter);
		this.sliderWithSpinner.setValue(5);
		this.sliderWithSpinner.setValue(7);
		
		Assert.assertTrue(this.counter.wasNotified());
	}
	
	@Test
	public void test_addChangeListener_3()
	{
		this.sliderWithSpinner.addChangeListener(this.counter);
		this.sliderWithSpinner.setValue(16);
		this.sliderWithSpinner.setValue(72);
		this.sliderWithSpinner.setValue(72);
		
		Assert.assertTrue(this.counter.wasNotified());
	}
	
	@Test
	public void test_addChangeListener_4()
	{
		this.sliderWithSpinner.addChangeListener(this.counter);
		this.sliderWithSpinner.setValue(14);
		this.sliderWithSpinner.setValue(12);
		this.sliderWithSpinner.setValue(12);
		this.sliderWithSpinner.setValue(14);
		
		Assert.assertTrue(this.counter.wasNotified());
	}
	
	@Test
	public void test_setValue_1()
	{
		this.sliderWithSpinner.setValue(40);
		
		Assert.assertEquals(40, this.sliderWithSpinnerModel.getValue());
		Assert.assertEquals(40, this.sliderWithSpinnerModel.getValue());
	}
	
	@Test
	public void test_setValue_2()
	{
		this.sliderWithSpinner.setValue(5);
		this.sliderWithSpinner.setValue(7);
		
		Assert.assertEquals(7, this.sliderWithSpinnerModel.getValue());
		Assert.assertEquals(7, this.sliderWithSpinnerModel.getValue());
	}
	
	@Test
	public void test_setValue_3()
	{
		this.sliderWithSpinner.setValue(16);
		this.sliderWithSpinner.setValue(72);
		this.sliderWithSpinner.setValue(72);
		
		Assert.assertEquals(72, this.sliderWithSpinnerModel.getValue());
		Assert.assertEquals(72, this.sliderWithSpinnerModel.getValue());
	}
	
	@Test
	public void test_setValue_4()
	{
		this.sliderWithSpinner.setValue(20);
		this.sliderWithSpinner.setValue(19);
		this.sliderWithSpinner.setValue(14);
		
		Assert.assertEquals(14, this.sliderWithSpinnerModel.getValue());
		Assert.assertEquals(14, this.sliderWithSpinnerModel.getValue());
	}
	
	@Test
	public void test_removeChangeListener_1()
	{
		this.sliderWithSpinner.addChangeListener(this.counter);
		this.sliderWithSpinner.setValue(5);
		this.sliderWithSpinner.setValue(1);
		this.sliderWithSpinner.removeChangeListener(this.counter);
		
		this.counter.reset();
		
		this.sliderWithSpinner.setValue(0);
		
		Assert.assertFalse(this.counter.wasNotified());
	}
	
	@Test
	public void test_removeChangeListener_2()
	{
		NotificationCounter counter2 = new NotificationCounter();
		
		this.sliderWithSpinner.addChangeListener(this.counter);
		this.sliderWithSpinner.addChangeListener(counter2);
		this.sliderWithSpinner.setValue(96);
		this.sliderWithSpinner.setValue(15);
		this.sliderWithSpinner.removeChangeListener(counter2);
		
		counter2.reset();
		
		this.sliderWithSpinner.setValue(33);
		
		Assert.assertFalse(counter2.wasNotified());
		Assert.assertTrue(this.counter.wasNotified());
	}
}
