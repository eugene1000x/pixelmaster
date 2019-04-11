
package pixelmaster.modules.spherical_wave.gui;


import pixelmaster.core.api.domain.*;
import pixelmaster.modules.spherical_wave.domain.*;
import pixelmaster.modules.spherical_wave.domain.graph.*;

import javax.swing.*;


public final class SphericalWaveWindowModel
{
	static final String LABEL_CONNECTIVITY_MODE_16 = "16";
	static final String LABEL_CONNECTIVITY_MODE_4_8 = "4/8";
	
	
	private String[] labels = {
			SphericalWaveWindowModel.LABEL_CONNECTIVITY_MODE_16,
			SphericalWaveWindowModel.LABEL_CONNECTIVITY_MODE_4_8
	};
	
	private SpinnerNumberModel
			sizeSpinnerModel = new SpinnerNumberModel(1, 1, 999, 1),
			coeffSpinnerModel = new SpinnerNumberModel(-0.7, -1.0, 1.0, 0.05),
			distanceSpinnerModel = new SpinnerNumberModel(10.0, 0.0, 999.0, 0.5),
			varianceSpinnerModel = new SpinnerNumberModel(5.8, 0.0, 999.0, 0.05),
			lengthSpinnerModel = new SpinnerNumberModel(10.2, 0.0, 999.0, 0.05);
	private RasterImage image, waves, origImage;
	private UnorientedGraph graph;
	private boolean[] isBackground;
	private ComboBoxModel modeComboboxModel = new DefaultComboBoxModel(this.labels);
	
	
	public SphericalWaveWindowModel(RasterImage image)
	{
		this.origImage = new RasterImage(image);
		this.image = new RasterImage(image.getWidth(), image.getHeight());
		this.isBackground = new boolean[image.getSize()];
		
		for (int i = 0; i < this.isBackground.length; i++)
		{
			this.isBackground[i] = image.getRgb(i) != 0;
		}
		
		this.waves = new RasterImage(image.getWidth(), image.getHeight());
		this.graph = new UnorientedGraph();
	}
	
	SpinnerNumberModel getSizeSpinnerModel()
	{
		return this.sizeSpinnerModel;
	}
	
	SpinnerNumberModel getCoeffSpinnerModel()
	{
		return this.coeffSpinnerModel;
	}
	
	SpinnerNumberModel getDistanceSpinnerModel()
	{
		return this.distanceSpinnerModel;
	}
	
	SpinnerNumberModel getVarianceSpinnerModel()
	{
		return this.varianceSpinnerModel;
	}
	
	SpinnerNumberModel getLengthSpinnerModel()
	{
		return this.lengthSpinnerModel;
	}
	
	ComboBoxModel getModeComboboxModel()
	{
		return this.modeComboboxModel;
	}
	
	RasterImage getImage()
	{
		return this.image;
	}
	
	RasterImage getOrigImage()
	{
		return this.origImage;
	}
	
	UnorientedGraph getGraph()
	{
		return this.graph;
	}
	
	void applySva()
	{
		int mode;
		
		if (this.modeComboboxModel.getSelectedItem().equals(SphericalWaveWindowModel.LABEL_CONNECTIVITY_MODE_16))
		{
			mode = SphericalWave.CONNECTIVITY_MODE_16;
		}
		else if (this.modeComboboxModel.getSelectedItem().equals(SphericalWaveWindowModel.LABEL_CONNECTIVITY_MODE_4_8))
		{
			mode = SphericalWave.CONNECTIVITY_MODE_4_8;
		}
		else
		{
			assert false;
			mode = SphericalWave.CONNECTIVITY_MODE_16;
		}
		
		this.graph = new UnorientedGraph();
		
		SphericalWave.buildStructure(
			this.isBackground,
			this.origImage.getWidth(),
			this.origImage.getHeight(),
			this.waves,
			this.graph,
			(Integer) this.sizeSpinnerModel.getValue(),
			mode
		);
		
		this.origImage.copyTo(this.image);
		
		SphericalWave.drawStructure(this.image, this.graph);
	}
	
	void applyPrimaryOptimization()
	{
		Optimization.performPrimaryOptimization(this.graph, (Double) this.coeffSpinnerModel.getValue());
		this.origImage.copyTo(this.image);
		SphericalWave.drawStructure(this.image, this.graph);
	}
	
	void connectEdges()
	{
		Optimization.connectEdges(this.graph, (Double) this.distanceSpinnerModel.getValue());
		this.origImage.copyTo(this.image);
		SphericalWave.drawStructure(this.image, this.graph);
	}
	
	void applyEdgeOptimization()
	{
		Optimization.optimizeEdges(this.graph, (Double) this.varianceSpinnerModel.getValue());
		this.origImage.copyTo(this.image);
		SphericalWave.drawStructure(this.image, this.graph);
	}
	
	void cutTails()
	{
		Optimization.cutTails(this.graph, (Double) this.lengthSpinnerModel.getValue());
		this.origImage.copyTo(this.image);
		SphericalWave.drawStructure(this.image, this.graph);
	}
}
