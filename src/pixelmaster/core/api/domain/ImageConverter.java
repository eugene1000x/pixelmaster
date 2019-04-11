
package pixelmaster.core.api.domain;


import java.awt.image.*;


/**
 * This class contains methods for conversion between {@link RasterImage} and
 * {@link java.awt.image.BufferedImage}.
 */
public final class ImageConverter
{
	/**
	 * Converts BufferedImage to RasterImage of the same size. Alpha channel information will be discarded.
	 *
	 * @param inputImage Can have any size and type (see {@link java.awt.image.BufferedImage#getType()}).
	 * @param outputImage Can have any size, will be resized.
	 */
	public static void toRasterImage(BufferedImage inputImage, RasterImage outputImage)
	{
		assert inputImage != null && outputImage != null && inputImage.getWidth() >= 1 && inputImage.getHeight() >= 1;
		
		
		outputImage.resize(inputImage.getWidth(), inputImage.getHeight());

		/*
		 * Workaround for behaviour of BufferedImage.getRGB(): when image has type BufferedImage.TYPE_BYTE_GRAY,
		 * it returns result different from the color value initially set.
		 */
		if (inputImage.getType() == BufferedImage.TYPE_BYTE_GRAY)
		{
			WritableRaster raster = inputImage.getRaster();
			byte[] a__gray = (byte[]) raster.getDataElements(0, 0, outputImage.getWidth(), outputImage.getHeight(), null);
			
			for (int i = 0; i < outputImage.getSize(); i++)
			{
				int i__gray = a__gray[i] & 0x000000ff;				// a__gray[i] == -40 => i__gray == 216
				outputImage.setRgb(i, RGB.getRgb(i__gray, i__gray, i__gray));
			}
		}
		else
		{
			for (int x = 0; x < outputImage.getWidth(); x++)
			{
				for (int y = 0; y < outputImage.getHeight(); y++)
				{
					int argb = inputImage.getRGB(x, y);
					outputImage.setRgb(x, y, argb & 0x00ffffff);
				}
			}
		}
	}

	/**
	 * Converts RasterImage to BufferedImage of type {@link BufferedImage#TYPE_INT_RGB}.
	 * 
	 * @return BufferedImage of the same size.
	 */
	public static BufferedImage toBufferedImage(RasterImage image)
	{
		BufferedImage outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

		for (int y = 0, rowStart = 0; y < image.getHeight(); y++, rowStart += image.getWidth())
		{
			for (int index = rowStart, x = 0; x < image.getWidth(); x++, index++)
			{
				//loop invariant: index == y * width + x
				
				outputImage.setRGB(x, y, image.getRgb(index));
			}
		}

		return outputImage;
	}

	/**
	 * Converts RasterImage to BufferedImage of type BufferedImage.TYPE_BYTE_GRAY.
	 * Gray value for each pixel is calculated using default red, green and blue coefficients.
	 * 
	 * @return BufferedImage of the same size.
	 */
	public static BufferedImage toGrayScaleBufferedImage(RasterImage image)
	{
		BufferedImage outputImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster raster = outputImage.getRaster();
		
		for (int y = 0, rowStart = 0; y < image.getHeight(); y++, rowStart += image.getWidth())
		{
			for (int index = rowStart, x = 0; x < image.getWidth(); x++, index++)
			{
				//loop invariant: index == y * width + x
				
				int gray = RGB.getIntensity(image.getRgb(index));
				raster.setDataElements(x, y, new byte[] {(byte) gray});
				//outputImage.setRGB(x, y, RGB.getRgb(gray, gray, gray));
			}
		}
		
		return outputImage;
	}
}
