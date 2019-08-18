
package eugenejonas.pixelmaster.modules.filters.gui;


import eugenejonas.pixelmaster.core.api.domain.*;
import eugenejonas.pixelmaster.core.api.gui.*;
import eugenejonas.pixelmaster.modules.filters.domain.*;

import javax.swing.*;


public final class FilterWindowModel
{
	// constants that define default settings
	private static final int DEFAULT_THRESHOLD = 15;
	private static final int DEFAULT_RADIUS = 3;
	
	// constants enumerating different filtering algorithms
	static final int ALGORITHM_MEDIAN_FILTER_SQUARE = 0;
	static final int ALGORITHM_MEDIAN_FILTER_BIDIRECTIONAL = 1;
	static final int ALGORITHM_MEAN_FILTER_SQUARE = 2;
	static final int ALGORITHM_VARIANCE_FILTER_SQUARE = 3;
	
	
	/**
	 * Filtering radius, filter threshold, algorithm to use and
	 * area which needs to be filtered.
	 */
	private RasterImage image, origImage;
	private RadioButtonPanelModel algorithmPanelModel = new RadioButtonPanelModel(4, FilterWindowModel.ALGORITHM_MEDIAN_FILTER_SQUARE);
	private ImagePanelModel imagePanelModel = new ImagePanelModel();
	private SpinnerNumberModel
		thresholdSpinnerModel = new SpinnerNumberModel(FilterWindowModel.DEFAULT_THRESHOLD, 0, 255, 1),
		radiusSpinnerModel = new SpinnerNumberModel(FilterWindowModel.DEFAULT_RADIUS, 0, 999, 1);
	
	
	public FilterWindowModel(RasterImage image)
	{
		this.origImage = new RasterImage(image);
		this.image = new RasterImage(image);
	}
	
	SpinnerNumberModel getThresholdSpinnerModel()
	{
		return this.thresholdSpinnerModel;
	}
	
	ImagePanelModel getImagePanelModel()
	{
		return this.imagePanelModel;
	}
	
	SpinnerNumberModel getRadiusSpinnerModel()
	{
		return this.radiusSpinnerModel;
	}
	
	RadioButtonPanelModel getAlgorithmPanelModel()
	{
		return this.algorithmPanelModel;
	}
	
	public RasterImage getImage()
	{
		return this.image;
	}
	
	void applyFilter()
	{
		if (this.algorithmPanelModel.getSelectedIndex() == FilterWindowModel.ALGORITHM_MEDIAN_FILTER_SQUARE)
		{
			MedianFilter.performMedianFiltering(
				this.image,
				this.image,
				(Integer) this.radiusSpinnerModel.getValue(),
				(Integer) this.thresholdSpinnerModel.getValue(),
				this.imagePanelModel.getRectangularSelection()
			);
		}
		else if (this.algorithmPanelModel.getSelectedIndex() == FilterWindowModel.ALGORITHM_MEDIAN_FILTER_BIDIRECTIONAL)
		{
			MedianFilter.performMedianFiltering_bidirectional(
				this.image,
				this.imagePanelModel.getRectangularSelection(),
				(Integer) this.radiusSpinnerModel.getValue(),
				(Integer) this.thresholdSpinnerModel.getValue()
			);
		}
		else if (this.algorithmPanelModel.getSelectedIndex() == FilterWindowModel.ALGORITHM_MEAN_FILTER_SQUARE)
		{
			MeanFilter.performMeanFiltering(
				this.image,
				this.image,
				(Integer) this.radiusSpinnerModel.getValue(),
				(Integer) this.thresholdSpinnerModel.getValue(),
				this.imagePanelModel.getRectangularSelection()
			);
		}
		else if (this.algorithmPanelModel.getSelectedIndex() == FilterWindowModel.ALGORITHM_VARIANCE_FILTER_SQUARE)
		{
			VarianceFilter.performVarianceFiltering(
				this.image,
				this.image,
				null,
				(Integer) this.radiusSpinnerModel.getValue(),
				(Integer) this.thresholdSpinnerModel.getValue(),
				this.imagePanelModel.getRectangularSelection()
			);
		}
		else
		{
			assert false;
		}
	}
	
	void loadOriginalImage()
	{
		this.image = new RasterImage(this.origImage);
	}
}
