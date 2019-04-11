
package pixelmaster.modules.binarization.domain;


import pixelmaster.core.api.domain.*;


public final class GrayScale
{
	/**
	 * Creates gray-shaded image using default red, green and blue coefficients.
	 * inputImage and outputImage may point to the same objects.
	 */
	public static void performGlobalGrayScaling(RasterImage inputImage, RasterImage outputImage)
	{
		GrayScale.performGlobalGrayScaling(inputImage, outputImage, RGB.DEFAULT_RED_COEFF, RGB.DEFAULT_GREEN_COEFF, RGB.DEFAULT_BLUE_COEFF);
	}
	
	/**
	 * Creates gray-shaded image.
	 * inputImage and outputImage may point to the same objects.
	 * 
	 * @param kr Coefficient of red channel (0..100).
	 * @param kg Coefficient of green channel (0..100).
	 * @param kb Coefficient of blue channel (0..100).
	 */
	public static void performGlobalGrayScaling(RasterImage inputImage, RasterImage outputImage, int kr, int kg, int kb)
	{
		assert inputImage != null && outputImage != null;
		assert kr >= 0 && kr <= 100 && kg >= 0 && kg <= 100 && kb >= 0 && kb <= 100 && kr + kg + kb == 100;
		assert outputImage.getWidth() == inputImage.getWidth();
		assert outputImage.getHeight() == inputImage.getHeight();
		
		
		int size = inputImage.getHeight() * inputImage.getWidth();
		
		for (int i = 0; i < size; i++)
		{
			int gray = RGB.getIntensity(inputImage.getRgb(i), kr, kg, kb);
			outputImage.setRgb(i, RGB.getRgb(gray, gray, gray));
		}
	}
}
