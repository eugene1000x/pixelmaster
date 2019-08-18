
package eugenejonas.pixelmaster.modules.filters.domain;


import eugenejonas.pixelmaster.core.api.domain.*;

import java.awt.*;


public class MeanFilter
{
	/**
	 * inputImage and outputImage may be the same. If area
	 * specified by <code>selection</code> is closer to one of image sides
	 * than <code>radius</code>, area will be clipped.
	 * 
	 * @param radius Filtering radius.
	 * @param selection Area to filter. If null, whole image is filtered.
	 */
	public static void performMeanFiltering(RasterImage inputImage, RasterImage outputImage, int radius, int threshold, Rectangle selection)
	{
		if (selection == null)
		{
			selection = new Rectangle(0, 0, inputImage.getWidth(), inputImage.getHeight());
		}
		
		assert
			inputImage != null && outputImage != null
			&& radius >= 0
			&& inputImage.getWidth() == outputImage.getWidth()
			&& inputImage.getHeight() == outputImage.getHeight()
			&& threshold >= 0 && inputImage.isSelectionInBounds(selection);
		
		
		int
			width = inputImage.getWidth(),
			height = inputImage.getHeight();
		
		if (width < 2 * radius + 1 || height < 2 * radius + 1 /*|| r==0*/)
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
		
		MeanFilter.performMeanFiltering(inputImage, outputImage, selection, radius, threshold, 0);
		MeanFilter.performMeanFiltering(inputImage, outputImage, selection, radius, threshold, 8);
		MeanFilter.performMeanFiltering(inputImage, outputImage, selection, radius, threshold, 16);
	}
	
	/**
	 * Mean filter. Must be used separately for each RGB component, specified by bit offset.
	 * If area specified by <code>selection</code> is closer to one of the image sides than
	 * <code>radius</code>, area will be clipped.
	 * 
	 * @param selection Area to filter. If null, whole image is filtered.
	 * @param radius Filtering radius.
	 * @param offset 0 for blue, 8 for green, 16 for red.
	 */
	private static void performMeanFiltering(RasterImage inputImage, RasterImage outputImage, Rectangle selection, int radius, int threshold, int offset)
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
		
		
		int width = inputImage.getWidth(), height = inputImage.getHeight();
		int selX = selection.x, selY = selection.y, selWidth = selection.width, selHeight = selection.height;
		
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
		
		selection = null;
		
		int
			size = 2 * radius + 1,
			selSize = selWidth * selHeight,
			aptSize = size * size;
		
		int[] sum = new int[selWidth + (radius << 1)];
		
		
		
		//init first row
		
		
		//init sums of first (2 * radius + 1) elements for (selWidth + 2 * radius) columns
		for (int rowIndex = 0, rowStart = selX - radius + width * (selY - radius); rowIndex < 2 * radius + 1; rowIndex++, rowStart += width)
		{
			for (int sumIndex = 0, index = rowStart; sumIndex < sum.length; sumIndex++, index++)
			{
				int value = (inputImage.getRgb(index) >> offset) & 255;
				sum[sumIndex] += value;
			}
		}
		
		int curSum = 0;
		
		for (int i = 0; i < size; i++)
		{
			curSum += sum[i];
		}
		
		int curAvg = curSum / aptSize;
		int destPos = selY * width + selX;
		int oldValue = (outputImage.getRgb(destPos) >> offset) & 255;
		
		//init first column of first row
		if (Math.abs(curAvg - oldValue) > threshold)
		{
			outputImage.setRgb(destPos, outputImage.getRgb(destPos) & ~(255 << offset));
			outputImage.setRgb(destPos, outputImage.getRgb(destPos) | (curAvg << offset));
		}
		
		//init the rest of columns of first row
		
		destPos++;
		
		for (int j = selX + 1, subIndex = 0, addIndex = size; j < selX + selWidth; j++, subIndex++, addIndex++, destPos++)
		{
			curSum += sum[addIndex] - sum[subIndex];
			curAvg = curSum / aptSize;
			oldValue = (outputImage.getRgb(destPos) >> offset) & 255;
			
			if (Math.abs(curAvg - oldValue) > threshold)
			{
				outputImage.setRgb(destPos, outputImage.getRgb(destPos) & ~(255 << offset));
				outputImage.setRgb(destPos, outputImage.getRgb(destPos) | (curAvg << offset));
			}
		}
		
		
		
		
		
		
		//process remaining rows
		
		for (int i = selY + 1; i < selY + selHeight; i++)
		{
			//update sums of first (2 * radius + 1) elements for (selWidth + 2 * radius) columns
			for (
				int sumIndex = 0,
					subIndex = selX - radius + width * (i - radius - 1),
					addIndex = selX - radius + width * (i + radius);
				sumIndex < selWidth + 2 * radius;
				sumIndex++,
					subIndex++,
					addIndex++
			)
			{
				int subValue = (inputImage.getRgb(subIndex) >> offset) & 255;
				int addValue = (inputImage.getRgb(addIndex) >> offset) & 255;
				sum[sumIndex] += addValue - subValue;
			}
			
			
			
			curSum = 0;
			
			for (int k = 0; k < size; k++)
			{
				curSum += sum[k];
			}
			
			curAvg = curSum / aptSize;
			destPos = i * width + selX;
			oldValue = (outputImage.getRgb(destPos) >> offset) & 255;
			
			if (Math.abs(curAvg - oldValue) > threshold)
			{
				outputImage.setRgb(destPos, outputImage.getRgb(destPos) & ~(255 << offset));
				outputImage.setRgb(destPos, outputImage.getRgb(destPos) | (curAvg << offset));
			}
			
			destPos++;
			
			for (int j = selX + 1, subIndex = 0, addIndex = size; j < selX + selWidth; j++, subIndex++, addIndex++, destPos++)
			{
				curSum += sum[addIndex] - sum[subIndex];
				curAvg = curSum / aptSize;
				oldValue = (outputImage.getRgb(destPos) >> offset) & 255;
				
				if (Math.abs(curAvg - oldValue) > threshold)
				{
					outputImage.setRgb(destPos, outputImage.getRgb(destPos) & ~(255 << offset));
					outputImage.setRgb(destPos, outputImage.getRgb(destPos) | (curAvg << offset));
				}
			}
		}
	}
}
