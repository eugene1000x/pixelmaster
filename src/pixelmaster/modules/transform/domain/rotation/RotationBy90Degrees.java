
package pixelmaster.modules.transform.domain.rotation;


import pixelmaster.core.api.domain.*;


public final class RotationBy90Degrees
{
	/**
	 * Rotates image by 90 degrees anti-clockwise.
	 * inputImage and outputImage may point to the same objects.
	 */
	public static void rotateAcw(RasterImage inputImage, RasterImage outputImage)
	{
		assert inputImage != null && outputImage != null && outputImage.getSize() == inputImage.getSize();
		
		
		if (inputImage == outputImage)
		{
			inputImage = new RasterImage(inputImage);
		}
		
		outputImage.resize(inputImage.getHeight(), inputImage.getWidth());
		
		int newY;
		
		for (int i = 0; i < inputImage.getHeight(); i++)
		{
			for (int j = 0; j < inputImage.getWidth(); j++)
			{
				newY = inputImage.getWidth() - j - 1;
				outputImage.setRgb(i, newY, inputImage.getRgb(j, i));
			}
		}
	}
	
	/**
	 * Rotates image by 90 degrees clockwise.
	 * inputImage and outputImage may point to the same objects.
	 */
	public static void rotateCw(RasterImage inputImage, RasterImage outputImage)
	{
		assert outputImage.getSize() == inputImage.getSize();
		
		
		if (inputImage == outputImage)
		{
			inputImage = new RasterImage(inputImage);
		}
		
		outputImage.resize(inputImage.getHeight(), inputImage.getWidth());
		
		int newX;
		
		for (int i = 0; i < inputImage.getHeight(); i++)
		{
			newX = inputImage.getHeight() - i - 1;
			
			for (int j = 0; j < inputImage.getWidth(); j++)
			{
				outputImage.setRgb(newX, j, inputImage.getRgb(j, i));
			}
		}
	}
}
