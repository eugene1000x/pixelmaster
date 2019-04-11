
package pixelmaster.modules.transform.gui;


import pixelmaster.core.api.domain.*;
import pixelmaster.core.api.gui.*;
import pixelmaster.modules.transform.domain.resizing.*;

import javax.swing.*;


public final class ResizingWindowModel
{
	public final class NotApplicableException extends Exception
	{
		private NotApplicableException(String message)
		{
			super(message);
		}
	}
	
	
	// settings for nearest neighbor algorithm
	private SpinnerNumberModel coeffXSpinnerModel = new SpinnerNumberModel(1, 1, ImageSizeConstraints.MAX_IMAGE_WIDTH, 1);
	private SpinnerNumberModel coeffYSpinnerModel = new SpinnerNumberModel(1, 1, ImageSizeConstraints.MAX_IMAGE_HEIGHT, 1);
	
	// settings for bilinear resampling algorithm
	private SliderWithSpinnerModel newWidthSliderWithSpinnerModel = new SliderWithSpinnerModel(2, 2, ImageSizeConstraints.MAX_IMAGE_WIDTH);
	private SliderWithSpinnerModel newHeightSliderWithSpinnerModel = new SliderWithSpinnerModel(2, 2, ImageSizeConstraints.MAX_IMAGE_HEIGHT);
	
	
	public ResizingWindowModel()
	{
		//nothing
	}
	
	SpinnerNumberModel getCoeffXSpinnerModel()
	{
		return this.coeffXSpinnerModel;
	}
	
	SpinnerNumberModel getCoeffYSpinnerModel()
	{
		return this.coeffYSpinnerModel;
	}
	
	SliderWithSpinnerModel getNewWidthSliderWithSpinnerModel()
	{
		return this.newWidthSliderWithSpinnerModel;
	}
	
	SliderWithSpinnerModel getNewHeightSliderWithSpinnerModel()
	{
		return this.newHeightSliderWithSpinnerModel;
	}
	
	public RasterImage applyNearestNeighborResampling(RasterImage image) throws NotApplicableException, ImageSizeConstraintViolationException
	{
		if (image == null)
		{
			throw new NotApplicableException("No image loaded");
		}
		
		int kx = (Integer) this.coeffXSpinnerModel.getValue();
		int ky = (Integer) this.coeffYSpinnerModel.getValue();
		int newWidth = kx * image.getWidth();
		int newHeight = ky * image.getHeight();
		
		if (newWidth > ImageSizeConstraints.MAX_IMAGE_WIDTH || newHeight > ImageSizeConstraints.MAX_IMAGE_HEIGHT)
		{
			throw new ImageSizeConstraintViolationException("Image too large");
		}
		
		return NearestNeighborResampling.performNearestNeighborResampling(image, kx, ky);
	}
	
	public RasterImage applyBilinearResampling(RasterImage image) throws NotApplicableException
	{
		if (image == null)
		{
			throw new NotApplicableException("No image loaded");
		}
		
		if (image.getWidth() == 1 || image.getHeight() == 1)
		{
			throw new NotApplicableException("Bilinear resample algorithm can only be applied to an image of size 2x2 or bigger");
		}
		
		return SplineAlgorithm.resizeImage(image, this.newWidthSliderWithSpinnerModel.getValue(), this.newHeightSliderWithSpinnerModel.getValue());
	}
}
