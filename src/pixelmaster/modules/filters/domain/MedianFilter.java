
package pixelmaster.modules.filters.domain;


import pixelmaster.core.api.domain.*;

import java.awt.*;


/**
 * @see <a href="http://www.shellandslate.com/download/fastmedian_5506.pdf">Fast Median and Bilateral Filtering (PDF)</a>
 * @see <a href="http://nomis80.org/ctmf.html">Median Filtering in Constant Time</a>
 */
public final class MedianFilter
{
	/**
	 * Calculates median of historgam (as described in article).
	 * 
	 * @param n Total number of elements in histogram
	 * 		(in median filter algorithm, n == (2 * radius + 1) ^ 2).
	 * @return Median (0..255).
	 */
	private static int calculateHistogramMedian(int[] histogram, int n)
	{
		assert histogram != null && histogram.length == 256 && n >= 1 && n % 2 == 1;
		
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			int sum = 0;
			for (int i = 0; i < 256; i++)
			{
				sum += histogram[i];
			}
			assert sum == n;
		}
		
		
		int i;
		int sum = 0;
		int k = n >>> 1;
		
		for (i = 0; i < 256; i++)
		{
			sum += histogram[i];
			
			if (sum > k)
			{
				break;
			}
		}
		
		assert i != 256;
		return i;
	}
	
	/**
	 * Returns sum of two histograms: histogram[m] and histogram[n] (as described in article).
	 * 
	 * @param m Index of first histogram.
	 * @param n Index of second histogram.
	 * @return Sum of two histograms (result has length 256).
	 */
	private static int[] calculateSumOfHistograms(int[][] histogram, int m, int n)
	{
		assert histogram != null && m >= 0 && m < histogram.length && n >= 0 && n < histogram.length;
		assert histogram[m] != null && histogram[n] != null && histogram[m].length == 256 && histogram[n].length == 256;
		
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			for (int i = 0; i < histogram.length; i++)
			{
				assert histogram[i] != null && histogram[i].length == 256;
			}
		}
		
		
		int[] sum = new int[256];
		
		for (int i = 0; i < 256; i++)
		{
			sum[i] = histogram[m][i] + histogram[n][i];
		}
		
		return sum;
	}
	
	/**
	 * Returns histogram H[n] (as described in article). If n != histogram.length / 2, histogram is calculated as sum of
	 * histograms histogram[histogram.length / 2] and histogram[n]. In this case new histogram is created.
	 * Otherwise histogram[n] is returned.
	 * 
	 * @param histogram Array of partial histograms.
	 * @param n Index of histogram.
	 * @return Histogram H[n] (result has length 256).
	 */
	private static int[] joinHistograms(int[][] histogram, int n)
	{
		assert histogram != null && n >= 0 && n < histogram.length;
		
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			for (int i = 0; i < histogram.length; i++)
			{
				assert histogram[i] != null && histogram[i].length == 256;
			}
		}
		
		
		if (n == histogram.length / 2)
		{
			return histogram[n];
		}
		
		int[] h = MedianFilter.calculateSumOfHistograms(histogram, n, histogram.length / 2);
		
		return h;
	}
	
	/**
	 * Substracts row from histogram (as described in article).
	 * Must be used separately for each RGB component, specified by bit offset.
	 * 
	 * <code>pixels</code> at indexes [start .. <start + width - 1>] are substracted
	 * from histogram.
	 * 
	 * @param pixels Array of pixels. Must have format same as in RasterImage.
	 * @param start Index of first element in row.
	 * @param width Width of row. (In median filtering algorithm, width must be equal to selWidth + 2 * radius.)
	 * @param offset Bit offset (0, 8 or 16).
	 */
	private static void substractRow(int[][] histogram, int[] pixels, int start, int radius, int width, int offset)
	{
		assert
			histogram != null
			&& histogram.length == width - 2 * radius
			&& pixels != null
			&& start >= 0
			&& radius >= 0
			&& width >= 2 * radius + 1
			&& pixels.length >= start + width
			&& (offset == 0 || offset == 8 || offset == 16);
		
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			for (int i = 0; i < histogram.length; i++)
			{
				assert histogram[i] != null && histogram[i].length == 256;
			}
		}
		
		
		int n = width - 2 * radius;
		int center = n / 2 + radius;
		int c1 = center - radius;
		int c2 = center + radius + 1;
		
		for (int i = 0; i < n / 2; i++)
		{
			int i1 = 2 * radius + i + 1;
			
			if (i1 > n / 2)
			{
				i1 = n / 2;
			}
			
			for (int j = i; j < i1; j++)
			{
				int value = ((pixels[j + start]) >> offset) & 255;
				histogram[i][value]--;
			}
			
			int c = i1 - i;		// how many elements were substracted
			
			for (int j = c2 - c; j < c2; j++)
			{
				int value = ((pixels[j + start]) >> offset) & 255;
				histogram[i][value]++;
			}
		}
		
		for (int j = c1; j < c2; j++)
		{
			int value = ((pixels[j + start]) >> offset) & 255;
			histogram[n / 2][value]--;
		}
		
		for (int i = n / 2 + 1; i < n; i++)
		{
			int i2 = c2;
			
			if (i > i2)
			{
				i2 = i;
			}
			
			for (int j = i2; j < i + 2 * radius + 1; j++)
			{
				int value = ((pixels[j + start]) >> offset) & 255;
				histogram[i][value]--;
			}
			
			int c = i + 2 * radius + 1 - i2;		// how many elements were added
			
			for (int j = c1; j < c1 + c; j++)
			{
				int value = ((pixels[j + start]) >> offset) & 255;
				histogram[i][value]++;
			}
		}
	}
	
	/**
	 * Adds row to histogram (as described in article).
	 * Must be used separately for each RGB component, specified by bit offset.
	 *
	 * <code>pixels</code> at indexes [start .. <start + width - 1>] are added
	 * to the histogram.
	 *
	 * @param pixels Array of pixels. Must have format same as in RasterImage.
	 * @param start Index of first element in row.
	 * @param width Width of row. (In median filtering algorithm, width must be equal to selWidth + 2 * radius.)
	 */
	private static void addRow(int[][] histogram, int[] pixels, int start, int radius, int width, int offset)
	{
		assert
			histogram != null
			&& histogram.length == width - 2 * radius
			&& pixels != null
			&& start >= 0
			&& radius >= 0
			&& width >= 2 * radius + 1
			&& pixels.length >= start + width
			&& (offset == 0 || offset == 8 || offset == 16);
		
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			for (int i = 0; i < histogram.length; i++)
			{
				assert histogram[i] != null && histogram[i].length == 256;
			}
		}
		
		
		int n = width - 2 * radius;
		int center = n / 2 + radius;
		int c1 = center - radius;
		int c2 = center + radius + 1;
		
		for (int i = 0; i < n / 2; i++)
		{
			int i1 = 2 * radius + i + 1;
			
			if (i1 > n / 2)
			{
				i1 = n / 2;
			}
			
			for (int j = i; j < i1; j++)
			{
				int value = ((pixels[j + start]) >> offset) & 255;
				histogram[i][value]++;
			}
			
			int c = i1 - i;		// how many elements were added
			
			for (int j = c2 - c; j < c2; j++)
			{
				int value = ((pixels[j + start]) >> offset) & 255;
				histogram[i][value]--;
			}
		}
		
		for (int j = c1; j < c2; j++)
		{
			int value = ((pixels[j + start]) >> offset) & 255;
			histogram[n / 2][value]++;
		}
		
		for (int i = n / 2 + 1; i < n; i++)
		{
			int i2 = c2;
			
			if (i > i2)
			{
				i2 = i;
			}
			
			for (int j = i2; j < i + 2 * radius + 1; j++)
			{
				int value = ((pixels[j + start]) >> offset) & 255;
				histogram[i][value]++;
			}
			
			int c = i + 2 * radius + 1 - i2;		// how many elements were substracted
			
			for (int j = c1; j < c1 + c; j++)
			{
				int value = ((pixels[j + start]) >> offset) & 255;
				histogram[i][value]--;
			}
		}
	}
	
	/**
	 * Median filter. Must be used separately for each RGB component, specified by bit offset.
	 * 
	 * @param selection Area to filter. If null, whole image is filtered.
	 */
	private static void performMedianFiltering(RasterImage inputImage, RasterImage outputImage, int radius, int offset, int threshold, Rectangle selection)
	{
		if (selection == null)
		{
			selection = new Rectangle(0, 0, inputImage.getWidth(), inputImage.getHeight());
		}
		
		assert
			inputImage != null && outputImage != null
			&& radius >= 0
			&& inputImage != outputImage
			&& (offset == 0 || offset == 8 || offset == 16)
			&& inputImage.getWidth() >= 2 * radius + 1 && inputImage.getHeight() >= 2 * radius + 1
			&& inputImage.getWidth() == outputImage.getWidth() && inputImage.getHeight() == outputImage.getHeight()
			&& threshold >= 0
			&& inputImage.isSelectionInBounds(selection);
	
		
		//if (radius == 0)
		//{
		//	return;
		//}
		
		int
			width = inputImage.getWidth(),
			height = inputImage.getHeight();
		
		int
			selX = selection.x,
			selY = selection.y,
			selWidth = selection.width,
			selHeight = selection.height;
		
		if (selX < radius)
		{
			selWidth -= radius - selX;
			selX = radius;
		}
		
		if (selX + selWidth > width - radius)
		{
			selWidth = width - radius - selX;
		}
		
		if (selY < radius)
		{
			selHeight -= radius - selY;
			selY = radius;
		}
		
		if (selY + selHeight > height - radius)
		{
			selHeight = height - radius - selY;
		}
		
		if (selHeight < 1 || selWidth < 1)
		{
			return;
		}
		
		int[][] histogram = new int[selWidth][256];
		
		//for (int i = 0; i < selWidth; i++)
		//{
		//	Arrays.fill(histogram[i], 0);
		//}
		
		for (int i = selY - radius; i < selY + radius + 1; i++)
		{
			MedianFilter.addRow(
				histogram,
				inputImage.getPixels(),
				i * width + selX - radius,
				radius,
				selWidth + 2 * radius,
				offset
			);
		}
		
		int total = (2 * radius + 1) * (2 * radius + 1);
		
		for (int j = selX; j < selX + selWidth; j++)
		{
			int[] h = MedianFilter.joinHistograms(histogram, j - selX);
			int m = MedianFilter.calculateHistogramMedian(h, total);
			int destPos = selY * width + j;
			int pixel = (outputImage.getRgb(destPos) >> offset) & 255;
			
			if (Math.abs(m - pixel) > threshold)
			{
				outputImage.setRgb(destPos, outputImage.getRgb(destPos) & ~(255 << offset));
				outputImage.setRgb(destPos, outputImage.getRgb(destPos) | (m << offset));
			}
		}
		
		for (int i = selY + 1; i < selY + selHeight; i++)
		{
			MedianFilter.addRow(
				histogram,
				inputImage.getPixels(),
				(i + radius) * width + selX - radius,
				radius,
				selWidth + 2 * radius,
				offset
			);
			
			MedianFilter.substractRow(
				histogram,
				inputImage.getPixels(),
				(i - radius - 1) * width + selX - radius,
				radius,
				selWidth + 2 * radius,
				offset
			);
			
			for (int j = selX; j < selX + selWidth; j++)
			{
				int[] h = MedianFilter.joinHistograms(histogram, j - selX);
				int m = MedianFilter.calculateHistogramMedian(h, total);
				int destPos = i * width + j;
				int pixel = (outputImage.getRgb(destPos) >> offset) & 255;
				
				if (Math.abs(m - pixel) > threshold)
				{
					outputImage.setRgb(destPos, outputImage.getRgb(destPos) & ~(255 << offset));
					outputImage.setRgb(destPos, outputImage.getRgb(destPos) | (m << offset));
				}
			}
		}
	}
	
	/**
	 * Median filter. inputImage and outputImage may be the same.
	 * 
	 * @param threshold Only those pixels are replaced with median, whose
	 * 		value differs from median value by more than threshold.
	 * @param selection Area to filter. If null, whole image is filtered.
	 */
	public static void performMedianFiltering(RasterImage inputImage, RasterImage outputImage, int radius, int threshold, Rectangle selection)
	{
		if (selection == null)
		{
			selection = new Rectangle(0, 0, inputImage.getWidth(), inputImage.getHeight());
		}
		
		assert
			inputImage != null && outputImage != null
			&& radius >= 0
			&& inputImage.getWidth() == outputImage.getWidth() && inputImage.getHeight() == outputImage.getHeight()
			&& threshold >= 0
			&& inputImage.isSelectionInBounds(selection);
		
		
		int
			width = inputImage.getWidth(),
			height = inputImage.getHeight();
		
		if (width < 2 * radius + 1 || height < 2 * radius + 1 /*|| radius == 0*/)
		{
			if (inputImage != outputImage)
			{
				inputImage.copyTo(outputImage);
			}
			
			return;
		}
		
		if (inputImage == outputImage)
		{
			inputImage = new RasterImage(inputImage);
		}
		
		MedianFilter.performMedianFiltering(inputImage, outputImage, radius, 0, threshold, selection);
		MedianFilter.performMedianFiltering(inputImage, outputImage, radius, 8, threshold, selection);
		MedianFilter.performMedianFiltering(inputImage, outputImage, radius, 16, threshold, selection);
	}
	
	/**
	 * Filters image using bidirectional median filter.
	 * 
	 * @param selection Area to filter. If null, whole image is filtered.
	 * @param radius Filtering radius.
	 * @param threshold Only those pixels are replaced with median, whose
	 * 		value differs from median value by more than threshold.
	 */
	public static void performMedianFiltering_bidirectional(RasterImage inputImage, Rectangle selection, int radius, int threshold)
	{
		if (selection == null)
		{
			selection = new Rectangle(0, 0, inputImage.getWidth(), inputImage.getHeight());
		}
		
		assert inputImage != null && inputImage.isSelectionInBounds(selection) && radius >= 0 && threshold >= 0;
		
		
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		int size = 2 * radius + 1;
				

		
		//horizontal pass
		
		
		int
			selX = selection.x,
			selY = selection.y,
			selWidth = selection.width,
			selHeight = selection.height;
		
		if (selX < radius)
		{
			selWidth -= radius - selX;
			selX = radius;
		}
		
		if (selX + selWidth > width - radius)
		{
			selWidth = width - radius - selX;
		}
		
		if (selWidth >= 1)
		{
			SortedArray
				redArray = new SortedArray(size),
				greenArray = new SortedArray(size),
				blueArray = new SortedArray(size);
			
			for (int i = 0, rowStart = selY * width + selX; i < selHeight; i++, rowStart += width)
			{
				redArray.init(inputImage.getPixels(), rowStart - radius, width, size, 1, 16);
				greenArray.init(inputImage.getPixels(), rowStart - radius, width, size, 1, 8);
				blueArray.init(inputImage.getPixels(), rowStart - radius, width, size, 1, 0);
				
				int oldR = RGB.getR(inputImage.getRgb(rowStart));
				int oldG = RGB.getG(inputImage.getRgb(rowStart));
				int oldB = RGB.getB(inputImage.getRgb(rowStart));
				
				int medianR = redArray.getMedian();
				int medianG = greenArray.getMedian();
				int medianB = blueArray.getMedian();
				
				int newR, newG, newB;
				
				if (Math.abs(medianR - oldR) > threshold)
				{
					newR = medianR;
				}
				else
				{
					newR = oldR;
				}
				
				if (Math.abs(medianG - oldG) > threshold)
				{
					newG = medianG;
				}
				else
				{
					newG = oldG;
				}
				
				if (Math.abs(medianB - oldB) > threshold)
				{
					newB = medianB;
				}
				else
				{
					newB = oldB;
				}
				
				inputImage.setRgb(rowStart, RGB.getRgb(newR, newG, newB));
				
				for (int j = 1, index = rowStart + 1; j < selWidth; j++, index++)
				{
					redArray.replaceOldestElement(RGB.getR(inputImage.getRgb(index + radius)));
					greenArray.replaceOldestElement(RGB.getG(inputImage.getRgb(index + radius)));
					blueArray.replaceOldestElement(RGB.getB(inputImage.getRgb(index + radius)));
					
					oldR = RGB.getR(inputImage.getRgb(index));
					oldG = RGB.getG(inputImage.getRgb(index));
					oldB = RGB.getB(inputImage.getRgb(index));
					
					medianR = redArray.getMedian();
					medianG = greenArray.getMedian();
					medianB = blueArray.getMedian();
					
					if (Math.abs(medianR - oldR) > threshold)
					{
						newR = medianR;
					}
					else
					{
						newR = oldR;
					}
					
					if (Math.abs(medianG - oldG) > threshold)
					{
						newG = medianG;
					}
					else
					{
						newG = oldG;
					}
					
					if (Math.abs(medianB - oldB) > threshold)
					{
						newB = medianB;
					}
					else
					{
						newB = oldB;
					}
					
					inputImage.setRgb(index, RGB.getRgb(newR, newG, newB));
				}
			}
		}
		
		
		
		//vertical pass
		
		
		selX = selection.x;
		selY = selection.y;
		selWidth = selection.width;
		selHeight = selection.height;
		
		if (selY < radius)
		{
			selHeight -= radius - selY;
			selY = radius;
		}
		
		if (selY + selHeight > height - radius)
		{
			selHeight = height - radius - selY;
		}
		
		if (selHeight >= 1)
		{
			SortedArray
				redArray = new SortedArray(size),
				greenArray = new SortedArray(size),
				blueArray = new SortedArray(size);
			
			for (int i = selX + selY * width; i < selX + selY * width + selWidth; i++)
			{
				redArray.init(inputImage.getPixels(), i - radius * width, width, 1, size, 16);
				greenArray.init(inputImage.getPixels(), i - radius * width, width, 1, size, 8);
				blueArray.init(inputImage.getPixels(), i - radius * width, width, 1, size, 0);
				
				int oldR = RGB.getR(inputImage.getRgb(i));
				int oldG = RGB.getG(inputImage.getRgb(i));
				int oldB = RGB.getB(inputImage.getRgb(i));
				
				int medianR = redArray.getMedian();
				int medianG = greenArray.getMedian();
				int medianB = blueArray.getMedian();
				
				int newR, newG, newB;
				
				if (Math.abs(medianR - oldR) > threshold)
				{
					newR = medianR;
				}
				else
				{
					newR = oldR;
				}
				
				if (Math.abs(medianG - oldG) > threshold)
				{
					newG = medianG;
				}
				else
				{
					newG = oldG;
				}
				
				if (Math.abs(medianB - oldB) > threshold)
				{
					newB = medianB;
				}
				else
				{
					newB = oldB;
				}
				
				inputImage.setRgb(i, RGB.getRgb(newR, newG, newB));
				
				for (int j = 1, index = i + width; j < selHeight; j++, index += width)
				{
					redArray.replaceOldestElement(RGB.getR(inputImage.getRgb(index + radius * width)));
					greenArray.replaceOldestElement(RGB.getG(inputImage.getRgb(index + radius * width)));
					blueArray.replaceOldestElement(RGB.getB(inputImage.getRgb(index + radius * width)));
					
					oldR = RGB.getR(inputImage.getRgb(index));
					oldG = RGB.getG(inputImage.getRgb(index));
					oldB = RGB.getB(inputImage.getRgb(index));
					
					medianR = redArray.getMedian();
					medianG = greenArray.getMedian();
					medianB = blueArray.getMedian();
					
					if (Math.abs(medianR - oldR) > threshold)
					{
						newR = medianR;
					}
					else
					{
						newR = oldR;
					}
					
					if (Math.abs(medianG - oldG) > threshold)
					{
						newG = medianG;
					}
					else
					{
						newG = oldG;
					}
					
					if (Math.abs(medianB - oldB) > threshold)
					{
						newB = medianB;
					}
					else
					{
						newB = oldB;
					}
					
					inputImage.setRgb(index, RGB.getRgb(newR, newG, newB));
				}
			}
		}
	}
}
