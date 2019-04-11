
package pixelmaster.modules.transform.domain;


import pixelmaster.core.api.domain.*;


public final class Flipping
{
	/**
	 * inputImage and outputImage may point to the same objects.
	 */
	public static void flipHorizontally(RasterImage inputImage, RasterImage outputImage)
	{
		assert inputImage != null && outputImage != null;
		assert inputImage.getWidth() == outputImage.getWidth() && inputImage.getHeight() == outputImage.getHeight();
		
		
		int
			width = inputImage.getWidth(),
			height = inputImage.getHeight();
		
		if (inputImage == outputImage)
		{
			for (int i = 0; i < height; i++)
			{
				for (int j = 0; j < width / 2; j++)
				{
					int index1 = i * width + j;
					int index2 = (i + 1) * width - j - 1;
					
					int rgb = inputImage.getRgb(index1);
			
					inputImage.setRgb(index1, inputImage.getRgb(index2));
					inputImage.setRgb(index2, rgb);
				}
			}
		}
		else
		{
			for (int i = 0; i < outputImage.getHeight(); i++)
			{
				for (int j = 0; j < outputImage.getWidth(); j++)
				{
					int index1 = i * width + j;
					int index2 = (i + 1) * width - j - 1;
					
					outputImage.setRgb(index1, inputImage.getRgb(index2));
				}
			}
		}
	}
	
	/**
	 * inputImage and outputImage may point to the same objects.
	 */
	public static void flipVertically(RasterImage inputImage, RasterImage outputImage)
	{
		assert inputImage != null && outputImage != null;
		assert inputImage.getWidth() == outputImage.getWidth() && inputImage.getHeight() == outputImage.getHeight();
		
		
		int
			width = inputImage.getWidth(),
			height = inputImage.getHeight();
		
		if (inputImage == outputImage)
		{
			int[] row = new int[width];
			
			for (int i = 0; i < height / 2; i++)
			{
				inputImage.copyTo(row, i * width, 0, width);
				outputImage.copyFrom(row, 0, (height - i - 1) * width, width);
				outputImage.copyTo(outputImage, (height - i - 1) * width, i * width, width);
			}
		}
		else
		{
			for (int i = 0; i < height; i++)
			{
				inputImage.copyTo(outputImage, i * width, (height - i - 1) * width, width);
			}
		}
	}
}
