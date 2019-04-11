
package pixelmaster.modules.transform.gui;


import pixelmaster.core.api.domain.*;
import pixelmaster.core.api.gui.*;
import pixelmaster.modules.transform.domain.rotation.*;

import javax.swing.*;


public final class RotationWindowModel
{
	// angle in degrees
	private SpinnerNumberModel deg__angleSpinnerModel = new SpinnerNumberModel(20, -44, 45, 1);
	
	private ImagePanelModel imagePanelModel = new ImagePanelModel();
	private RasterImage image, origImage;
	
	
	public RotationWindowModel(RasterImage image)
	{
		this.origImage = new RasterImage(image);
		this.image = new RasterImage(image);
		
	}
	
	SpinnerNumberModel deg__getAngleSpinnerModel()
	{
		return this.deg__angleSpinnerModel;
	}
	
	ImagePanelModel getImagePanelModel()
	{
		return this.imagePanelModel;
	}
	
	public RasterImage getImage()
	{
		return this.image;
	}
	
	void apply()
	{
		this.image = RotationByShear.rotate(this.image, (Integer) this.deg__angleSpinnerModel.getValue(), 0x00000000);
	}
	
	void loadOriginalImage()
	{
		this.image = new RasterImage(this.origImage);
	}
}
