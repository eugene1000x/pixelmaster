
package eugenejonas.pixelmaster.modules.binarization.gui;


import eugenejonas.pixelmaster.core.api.domain.*;
import eugenejonas.pixelmaster.core.api.gui.*;
import eugenejonas.pixelmaster.modules.binarization.domain.*;

import javax.swing.*;


public final class BinarizationWindowModel
{
	static final class RgbPanelModel
	{
		private SliderWithSpinnerModel redSliderWithSpinnerModel, greenSliderWithSpinnerModel, blueSliderWithSpinnerModel;
		
		
		private RgbPanelModel()
		{
			this.redSliderWithSpinnerModel = new SliderWithSpinnerModel(RGB.DEFAULT_RED_COEFF, 0, 100);
			this.greenSliderWithSpinnerModel = new SliderWithSpinnerModel(RGB.DEFAULT_GREEN_COEFF, 0, 100);
			this.blueSliderWithSpinnerModel = new SliderWithSpinnerModel(RGB.DEFAULT_BLUE_COEFF, 0, 100);
		}
		
		int getRed()
		{
			return this.getRedSliderWithSpinnerModel().getValue();
		}
		
		int getGreen()
		{
			return this.getGreenSliderWithSpinnerModel().getValue();
		}
		
		int getBlue()
		{
			return this.getBlueSliderWithSpinnerModel().getValue();
		}
		
		SliderWithSpinnerModel getRedSliderWithSpinnerModel()
		{
			return this.redSliderWithSpinnerModel;
		}
		
		SliderWithSpinnerModel getGreenSliderWithSpinnerModel()
		{
			return this.greenSliderWithSpinnerModel;
		}
		
		SliderWithSpinnerModel getBlueSliderWithSpinnerModel()
		{
			return this.blueSliderWithSpinnerModel;
		}
	}
	
	
	// constants that define default settings
	private static final int DEFAULT_GLOBAL_THRESHOLD = 125;
	private static final int DEFAULT_BERNSEN_THRESHOLD = 10;
	private static final int DEFAULT_BERNSEN_RADIUS = 15;
	private static final double DEFAULT_NIBLACK_COEFFICIENT = -0.2;
	private static final int DEFAULT_NIBLACK_RADIUS = 10;
	
	// constants enumerating different algorithms
	static final int ALGORITHM_GLOBAL_BINARIZATION = 0;
	static final int ALGORITHM_GLOBAL_GRAYSCALING = 1;
	static final int ALGORITHM_BERNSEN_BINARIZATION = 2;
	static final int ALGORITHM_NIBLACK_BINARIZATION = 3;
	
	
	// original and working images
	private RasterImage image, origImage;
	
	// RGB settings
	private RgbPanelModel rgbPanelModel = new RgbPanelModel();
	
	// settings for global binarization algorithm
	private SliderWithSpinnerModel globalThresholdSliderWithSpinnerModel = new SliderWithSpinnerModel(DEFAULT_GLOBAL_THRESHOLD, 0, 255);
	
	// settings for Bernsen algorithm
	private SpinnerNumberModel bernsenRadiusSpinnerModel = new SpinnerNumberModel(DEFAULT_BERNSEN_RADIUS, 0, 999, 1);
	private SpinnerNumberModel bernsenThresholdSpinnerModel = new SpinnerNumberModel(DEFAULT_BERNSEN_THRESHOLD, 0, 255, 1);
	
	// settings for Niblack algorithm
	private SpinnerNumberModel niblackRadiusSpinnerModel = new SpinnerNumberModel(DEFAULT_NIBLACK_RADIUS, 0, 999, 1);
	private SpinnerNumberModel niblackCoeffSpinnerModel = new SpinnerNumberModel(DEFAULT_NIBLACK_COEFFICIENT, -999.00, 999.00, 0.1);
	
	
	public BinarizationWindowModel(RasterImage image)
	{
		this.origImage = new RasterImage(image);
		this.image = new RasterImage(image);
	}
	
	SliderWithSpinnerModel getGlobalThresholdSliderWithSpinnerModel()
	{
		return this.globalThresholdSliderWithSpinnerModel;
	}
	
	SpinnerNumberModel getBernsenRadiusSpinnerModel()
	{
		return this.bernsenRadiusSpinnerModel;
	}
	
	SpinnerNumberModel getBernsenThresholdSpinnerModel()
	{
		return this.bernsenThresholdSpinnerModel;
	}
	
	SpinnerNumberModel getNiblackRadiusSpinnerModel()
	{
		return this.niblackRadiusSpinnerModel;
	}
	
	SpinnerNumberModel getNiblackCoeffSpinnerModel()
	{
		return this.niblackCoeffSpinnerModel;
	}
	
	RgbPanelModel getRgbPanelModel()
	{
		return this.rgbPanelModel;
	}
	
	public RasterImage getImage()
	{
		return this.image;
	}
	
	void applyNiblackBinarization()
	{
		Binarization.performNiblackBinarization(
			this.image,
			this.image,
			this.rgbPanelModel.getRed(),
			this.rgbPanelModel.getGreen(),
			this.rgbPanelModel.getBlue(),
			(Integer) this.niblackRadiusSpinnerModel.getValue(),
			(Double) this.niblackCoeffSpinnerModel.getValue()
		);
	}
	
	void applyBernsenBinarization()
	{
		Binarization.performBernsenBinarization(
			this.image,
			this.image,
			this.rgbPanelModel.getRed(),
			this.rgbPanelModel.getGreen(),
			this.rgbPanelModel.getBlue(),
			(Integer) this.bernsenRadiusSpinnerModel.getValue(),
			(Integer) this.bernsenThresholdSpinnerModel.getValue()
		);
	}
	
	void applyGlobalBinarization()
	{
		Binarization.performGlobalBinarization(
			this.image,
			this.image,
			this.globalThresholdSliderWithSpinnerModel.getValue(),
			this.rgbPanelModel.getRed(),
			this.rgbPanelModel.getGreen(),
			this.rgbPanelModel.getBlue()
		);
	}
	
	void applyGlobalGrayScaling()
	{
		GrayScale.performGlobalGrayScaling(
			this.image,
			this.image,
			this.rgbPanelModel.getRed(),
			this.rgbPanelModel.getGreen(),
			this.rgbPanelModel.getBlue()
		);
	}
	
	void loadOriginalImage()
	{
		this.image = new RasterImage(this.origImage);
	}
}
