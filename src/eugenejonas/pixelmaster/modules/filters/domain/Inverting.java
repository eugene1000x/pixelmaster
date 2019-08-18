
package eugenejonas.pixelmaster.modules.filters.domain;


import eugenejonas.pixelmaster.core.api.domain.*;


public final class Inverting
{
	/**
	 * Converts image to its negative.
	 * inputImage and outputImage may point to the same objects.
	 */
	public static void invertColors(RasterImage inputImage, RasterImage outputImage)
	{
		assert outputImage.getWidth() == inputImage.getWidth();
		assert outputImage.getHeight() == inputImage.getHeight();
		
		
		int size = inputImage.getHeight() * inputImage.getWidth();
		
		for (int i = 0; i < size; i++)
		{
			outputImage.setRgb(i, 0x00ffffff - inputImage.getRgb(i));
		}
	}
}
