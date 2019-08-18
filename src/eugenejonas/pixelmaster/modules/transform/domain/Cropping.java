
package eugenejonas.pixelmaster.modules.transform.domain;


import eugenejonas.pixelmaster.core.api.domain.*;

import java.awt.*;


public final class Cropping
{
	public static RasterImage crop(RasterImage inputImage, Rectangle selection)
	{
		assert
			inputImage != null && selection != null
			&& selection.x >= 0 && selection.x < inputImage.getWidth()
			&& selection.y >= 0 && selection.y < inputImage.getHeight()
			&& selection.width >= 1 && selection.height >= 1
			&& selection.x + selection.width <= inputImage.getWidth() && selection.y + selection.height <= inputImage.getHeight();
		
		
		RasterImage cropped = new RasterImage(selection.width, selection.height);
		
		for (
			int i = 0,
				srcPos = selection.x + selection.y * inputImage.getWidth(),
				destPos = 0;
			i < selection.height;
			i++,
				srcPos += inputImage.getWidth(),
				destPos += cropped.getWidth()
		)
		{
			inputImage.copyTo(cropped, srcPos, destPos, cropped.getWidth());
		}
		
		return cropped;
	}
}
