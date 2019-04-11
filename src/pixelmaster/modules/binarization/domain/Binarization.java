
package pixelmaster.modules.binarization.domain;


import pixelmaster.core.api.domain.*;
import pixelmaster.modules.filters.domain.*;

import java.util.*;


/**
 * Binarization algorithms.
 * 
 * @see <a href="http://www.philippovich.ru/Library/Books/ITS/wwwbook/ist4b/its4/fyodorov.htm">А. Федоров. Бинаризация</a>
 */
public final class Binarization
{
	/**
	 * Returns optimal threshold which divides pixels
	 * in two classes: [0 .. <t - 1>] and [t..255].
	 * 
	 * @param kr Coefficient of red channel (0..100).
	 * @param kg Coefficient of green channel (0..100).
	 * @param kb Coefficient of blue channel (0..100).
	 * @return Threshold in range [1..255].
	 * @see <a href="http://homepages.inf.ed.ac.uk/rbf/CVonline/LOCAL_COPIES/MORSE/threshold.pdf">Lecture notes on thresholding</a>
	 */
	public static int performOtsuThresholding(RasterImage inputImage, int kr, int kg, int kb)
	{
		assert inputImage != null && kr >= 0 && kr <= 100 && kg >= 0 && kg <= 100 && kb >= 0 && kb <= 100 && kr + kg + kb == 100;
		
		
		int
			width = inputImage.getWidth(),
			height = inputImage.getHeight(),
			size = height * width;
		
		int[] histogram = new int[256];
		
		//build histogram
		for (int i = 0; i < size; i++)
		{
			int intensity = RGB.getIntensity(inputImage.getRgb(i), kr, kg, kb);
			histogram[intensity]++;
		}
		
		
		//find min and max indexes
		
		int minIndex = 0;
		
		for ( ; minIndex < 256; minIndex++)
		{
			if (histogram[minIndex] != 0)
			{
				break;
			}
		}
		
		assert minIndex < 256;
		
		int maxIndex = 255;
		
		for ( ; maxIndex >= 0; maxIndex--)
		{
			if (histogram[maxIndex] != 0)
			{
				break;
			}
		}
		
		assert minIndex >= 0;
		
		if (maxIndex == minIndex)
		{
			return 1;		// impossible to divide in 2 classes
		}
			
		// number of elements in 2 classes
		long
			n0 = histogram[minIndex],
			n1 = size - n0;
		
		assert n1 > 0 && n0 > 0;
		
		
		// Calculate respective cluster means for threshold = minIndex + 1.
		// First cluster consists of pixels with intensity in range [0..minIndex],
		// second cluster consists of pixels with intensity in range [<minIndex + 1> .. 255].
		double u0 = (minIndex + 1) * histogram[minIndex] / n0;
		double u1 = 0;
		for (int i = minIndex + 1; i <= maxIndex; i++)
		{
			u1 += histogram[i] * (i + 1);
		}
		u1 /= n1;
		
		
		// find optimal threshold
		
		int threshold = minIndex + 1;
		double maxVariance = n0 * n1 * (u0 - u1) * (u0 - u1);
		
		for (int i = minIndex + 2; i <= maxIndex; i++)
		{
			if (histogram[i - 1] == 0)
			{
				continue;
			}
			
			n0 += histogram[i - 1];
			n1 -= histogram[i - 1];
			u0 = (u0 * (n0 - histogram[i - 1]) + histogram[i - 1] * (i)) / (n0);
			u1 = (u1 * (n1 + histogram[i - 1]) - histogram[i - 1] * (i)) / (n1);
			
			double variance = n0 * n1 * (u0 - u1) * (u0 - u1);
			
			if (variance > maxVariance)
			{
				maxVariance = variance;
				threshold = i;
			}
		}
		
		return threshold;
	}
	
	/**
	 * Creates binary image using default kr, kg and kb.
	 * Threshold will divide pixel intensity in two classes: [0 .. <t - 1>] and [t..255].
	 * inputImage and outputImage may point to the same objects.
	 *
	 * @param threshold Threshold parameter (must be in range [0..255]).
	 */
	public static void performGlobalBinarization(RasterImage inputImage, RasterImage outputImage, int threshold)
	{
		Binarization.performGlobalBinarization(inputImage, outputImage, threshold, RGB.DEFAULT_RED_COEFF, RGB.DEFAULT_GREEN_COEFF, RGB.DEFAULT_BLUE_COEFF);
	}
	
	/**
	 * Creates binary image using given threshold and kr, kg, kb.
	 * Threshold will divide pixel intensity in two classes: [0 .. <t - 1>] and [t .. 255].
	 * inputImage and outputImage may point to the same objects.
	 * 
	 * @param threshold Integer in range [0..255].
	 * @param kr Coefficient of red channel (0..100).
	 * @param kg Coefficient of green channel (0..100).
	 * @param kb Coefficient of blue channel (0..100).
	 */
	public static void performGlobalBinarization(RasterImage inputImage, RasterImage outputImage, int threshold, int kr, int kg, int kb)
	{
		assert
			inputImage != null && outputImage != null
			&& threshold >= 0 && threshold <= 255
			&& inputImage.getWidth() == outputImage.getWidth() && inputImage.getHeight() == outputImage.getHeight()
			&& kr >= 0 && kr <= 100 && kg >= 0 && kg <= 100 && kb >= 0 && kb <= 100
			&& kr + kg + kb == 100;
			
			
		int size = inputImage.getHeight() * inputImage.getWidth();
		
		for (int i = 0; i < size; i++)
		{
			int rgb = inputImage.getRgb(i);
		
			if (RGB.getIntensity(rgb, kr, kg, kb) < threshold)
			{
				outputImage.setRgb(i, 0);
			}
			else
			{
				outputImage.setRgb(i, 0x00ffffff);
			}
		}
	}
	
	/**
	 * Niblack algorithm. Does not process edges.
	 * inputImage and outputImage may point to the same objects.
	 *
	 * @param kr Coefficient of red channel (0..100).
	 * @param kg Coefficient of green channel (0..100).
	 * @param kb Coefficient of blue channel (0..100).
	 * @param k Coefficient in range [-infinity..infinity].
	 */
	public static void performNiblackBinarization(RasterImage inputImage, RasterImage outputImage, int kr, int kg, int kb, int radius, double k)
	{
		assert
			inputImage != null && outputImage != null
			&& inputImage.getWidth() == outputImage.getWidth() && inputImage.getHeight() == outputImage.getHeight()
			&& kr >= 0 && kr <= 100 && kg >= 0 && kg <= 100 && kb >= 0 && kb <= 100
			&& kr + kg + kb == 100
			&& radius >= 0;
		
		
		int
			width = inputImage.getWidth(),
			height = inputImage.getHeight();
		
		if (2 * radius + 1 > width || 2 * radius + 1 > height)
		{
			return;
		}
		
		if (inputImage == outputImage)
		{
			inputImage = new RasterImage(inputImage);
		}
		
		RasterImage grayImage = new RasterImage(inputImage.getWidth(), inputImage.getHeight());
		RasterImage meanFilteredImage = new RasterImage(inputImage.getWidth(), inputImage.getHeight());
		RasterImage variance = new RasterImage(inputImage.getWidth(), inputImage.getHeight());
		
		GrayScale.performGlobalGrayScaling(inputImage, grayImage, kr, kg, kb);
		MeanFilter.performMeanFiltering(grayImage, meanFilteredImage, radius, 0, null);
		VarianceFilter.performVarianceFiltering(grayImage, variance, meanFilteredImage, radius, radius, null);
		
		for (int i = radius; i < width - radius; i++)
		{
			for (int j = radius; j < height - radius; j++)
			{
				double threshold = RGB.getR(meanFilteredImage.getRgb(i, j)) + k * RGB.getR(variance.getRgb(i, j));
				int intensity = RGB.getR(grayImage.getRgb(i, j));
				
				if (intensity >= threshold)
				{
					outputImage.setRgb(i, j, 0x00ffffff);
				}
				else
				{
					outputImage.setRgb(i, j, 0);
				}
			}
		}
	}
	
	/**
	 * Bernsen algorithm. Does not process edges.
	 * If max_intensity - min_intensity >= threshold,
	 * pixel becomes black. inputImage and outputImage may point to the same objects.
	 * 
	 * @param kr Coefficient of red channel (0..100).
	 * @param kg Coefficient of green channel (0..100).
	 * @param kb Coefficient of blue channel (0..100).
	 * @param threshold Contrast threshold. Must be in range [0..255].
	 */
	public static void performBernsenBinarization(RasterImage inputImage, RasterImage outputImage, int kr, int kg, int kb, int radius, int threshold)
	{
		assert
			inputImage != null && outputImage != null
			&& inputImage.getWidth() == outputImage.getWidth() && inputImage.getHeight() == outputImage.getHeight()
			&& kr >= 0 && kr <= 100 && kg >= 0 && kg <= 100 && kb >= 0 && kb <= 100
			&& kr + kg + kb == 100
			&& radius >= 0;
			
			
		int
			width = inputImage.getWidth(),
			height = inputImage.getHeight();
		
		if (2 * radius + 1 > width || 2 * radius + 1 > height)
		{
			return;
		}
		if (inputImage == outputImage)
		{
			inputImage = new RasterImage(inputImage);
		}
		
		RasterImage grayImage = new RasterImage(inputImage.getWidth(), inputImage.getHeight());
		GrayScale.performGlobalGrayScaling(inputImage, grayImage, kr, kg, kb);
		
		int[] arr = new int[(radius * 2 + 1) * (radius * 2 + 1)];
		
		for (int i = radius; i < width - radius; i++)
		{
			for (int j = radius; j < height - radius; j++)
			{
				int count = 0;
				
				for (int r1 = -radius; r1 < radius + 1; r1++)
				{
					for (int r2 = -radius; r2 < radius + 1; r2++)
					{
						arr[count++] = RGB.getR(grayImage.getRgb(j * width + i + (r1 * width) + r2));
					}
				}
				
				Arrays.sort(arr);
				
				int
					min = arr[0],
					max = arr[arr.length - 1];
				
				int newValue = RGB.getRgb(
					RGB.getR(grayImage.getRgb(i, j)),
					min,
					max
				);
				
				grayImage.setRgb(i, j, newValue);
			}
		}
		
		for (int i = radius; i < width - radius; i++)
		{
			for (int j = radius; j < height - radius; j++)
			{
				int var = RGB.getB(grayImage.getRgb(i, j)) - RGB.getR(grayImage.getRgb(i, j));
				
				if (var < threshold)
				{
					outputImage.setRgb(i, j, 0x00ffffff);
				}
				else
				{
					outputImage.setRgb(i, j, 0);
				}
			}
		}
	}
}
