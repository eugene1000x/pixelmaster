
package eugenejonas.pixelmaster.modules.transform.domain.resizing;


import eugenejonas.pixelmaster.core.api.domain.*;


public final class NearestNeighborResampling
{
	/**
	 * Stretches image horizontally and vertically.
	 */
	public static RasterImage performNearestNeighborResampling(RasterImage inputImage, int coeffX, int coeffY)
	{
		assert inputImage != null && coeffX >= 1 && coeffY >= 1;
		
		
		int
			newWidth = coeffX * inputImage.getWidth(),
			newHeight = coeffY * inputImage.getHeight();
		
		RasterImage resizedImage = new RasterImage(newWidth, newHeight);
		
		for (
			int i = 0,
				inputRowStart = 0,
				destRowStart = 0;
			i < inputImage.getHeight();
			i++,
				inputRowStart += inputImage.getWidth(),
				destRowStart += coeffY * resizedImage.getWidth()
		)
		{
			for (
				int j = 0,
					inputIndex = inputRowStart,
					destIndex1 = destRowStart;
				j < inputImage.getWidth();
				j++,
					inputIndex++,
					destIndex1 += coeffX
			)
			{
				int rgb = inputImage.getRgb(inputIndex);
				
				for (int k = 0, destIndex2 = destIndex1; k < coeffY; k++, destIndex2 += resizedImage.getWidth())
				{
					resizedImage.fill(rgb, destIndex2, destIndex2 + coeffX);
				}
			}
		}
		
		return resizedImage;
	}
}
