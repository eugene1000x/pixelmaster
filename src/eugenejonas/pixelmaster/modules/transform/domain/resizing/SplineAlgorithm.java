
package eugenejonas.pixelmaster.modules.transform.domain.resizing;


import eugenejonas.pixelmaster.core.api.domain.*;


/**
 * Bilinear spline interpolation algorithm for image resampling (resizing).
 * 
 * @see <a href="http://alglib.sources.ru/interpolation/spline2d.php">Двухмерная сплайн-интерполяция</a>
 */
public final class SplineAlgorithm
{
	public static RasterImage resizeImage(RasterImage inputImage, int newWidth, int newHeight)
	{
		assert inputImage != null && inputImage.getWidth() > 1 && inputImage.getHeight() > 1;
		assert newWidth > 1 && newHeight > 1;
		
		
		RasterImage outputImage = new RasterImage(newWidth, newHeight);
		
		SplineAlgorithm.performBilinearResamplingCartesian(inputImage, outputImage, 0);
		SplineAlgorithm.performBilinearResamplingCartesian(inputImage, outputImage, 8);
		SplineAlgorithm.performBilinearResamplingCartesian(inputImage, outputImage, 16);
		
		return outputImage;
	}
	
	/**
	 * Function must be used separately for each RGB component, specified by <code>offset</code>.
	 */
	private static void performBilinearResamplingCartesian(RasterImage inputImage, RasterImage outputImage, int offset)
	{
		assert offset == 0 || offset == 8 || offset == 16;
		assert inputImage != null && outputImage != null && inputImage != outputImage;
		assert inputImage.getHeight() > 1 && inputImage.getWidth() > 1 && outputImage.getHeight() > 1 && outputImage.getWidth() > 1;
		
		
		int i, j, l, c;
		float t, u;
		
		for (i = 0; i < outputImage.getHeight(); i++)
		{
			for (j = 0; j < outputImage.getWidth(); j++)
			{
				l = i * (inputImage.getHeight() - 1) / (outputImage.getHeight() - 1);
				
				if (l == inputImage.getHeight() - 1)
				{
					l = inputImage.getHeight() - 2;
				}
				
				u = (float) i / (float) (outputImage.getHeight() - 1) * (inputImage.getHeight() - 1) - l;
				
				c = j * (inputImage.getWidth() - 1) / (outputImage.getWidth() - 1);
				
				if (c == inputImage.getWidth() - 1)
				{
					c = inputImage.getWidth() - 2;
				}
				
				t = (float) (j * (inputImage.getWidth() - 1)) / (float) (outputImage.getWidth() - 1) - c;
				
				int v1 = (inputImage.getRgb(c, l) >> offset) & 255;
				int v2 = (inputImage.getRgb(c + 1, l) >> offset) & 255;
				int v3 = (inputImage.getRgb(c + 1, l + 1) >> offset) & 255;
				int v4 = (inputImage.getRgb(c, l + 1) >> offset) & 255;
				
				int value = (int) ((1 - t) * (1 - u) * v1 + t * (1 - u) * v2 + t * u * v3 + (1 - t) * u * v4);
				
				if (value > 255)
				{
					value = 255;
				}
				else if (value < 0)
				{
					value = 0;
				}
				
				// init byte to 0
				outputImage.setRgb(j, i, outputImage.getRgb(j, i) & ~(255 << offset));
				
				// assign byte value
				outputImage.setRgb(j, i, outputImage.getRgb(j, i) | (value << offset));
			}
		}
	}
}
