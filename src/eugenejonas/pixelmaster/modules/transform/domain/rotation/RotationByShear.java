
package eugenejonas.pixelmaster.modules.transform.domain.rotation;


import eugenejonas.pixelmaster.core.api.domain.*;


public final class RotationByShear
{
	public static RasterImage rotate(RasterImage inputImage, double deg__angle, int bgColor)
	{
		final double PI = 3.1415926535897932384626433832795;
		double rad__angle = deg__angle * PI / 180;		// Angle in radians
		double sin = Math.sin(rad__angle);
		double tan = Math.tan(rad__angle / 2);
		int inputWidth = inputImage.getWidth();
		int inputHeight = inputImage.getHeight();
		
		// Calc first shear (horizontal) output image dimensions
		int width1 = inputWidth + (int) (inputHeight * Math.abs(tan) + 0.5);
		int height1 = inputHeight;
		
		// Perform 1st shear (horizontal)
		// ----------------------------------------------------------------------
		
		// Allocate image for 1st shear
		RasterImage outputImage1 = new RasterImage(width1, height1);
		
		for (int i = 0; i < height1; i++)
		{
			double d__shear;
			
			if (tan >= 0)
			{
				// Positive angle
				d__shear = (i + 0.5) * tan;
			}
			else
			{
				// Negative angle
				d__shear = (i - height1 + 0.5) * tan;
			}
			
			int i__shear = (int) Math.floor(d__shear);
			RotationByShear.skewHorizontally(inputImage, outputImage1, i, i__shear, d__shear - i__shear, bgColor);
		}
		
		//return outputImage1;
		
		// Perform 2nd shear (vertical)
		// ----------------------------------------------------------------------
		
		// Calc 2nd shear (vertical) output image dimensions
		int width2 = width1;
		int height2 = (int) (inputWidth * Math.abs(sin) + (double) inputHeight * Math.cos(rad__angle) + 0.5) + 1;
		
		// Allocate image for 2nd shear
		RasterImage outputImage2 = new RasterImage(width2, height2);
		
		double d__offset;		// Variable skew offset
		
		if (sin > 0)
		{
			// Positive angle
			d__offset = (inputWidth - 1.0) * sin;
		}
		else
		{
			// Negative angle
			d__offset = -sin * (inputWidth - width2);
		}
		
		for (int i = 0; i < width2; i++, d__offset -= sin)
		{
			int i__shear = (int) Math.floor(d__offset);
			RotationByShear.skewVertically(outputImage1, outputImage2, i, i__shear, d__offset - i__shear, bgColor);
		}
		
		// Perform 3rd shear (horizontal)
		// ----------------------------------------------------------------------
		
		// Calc 3rd shear (horizontal) output image dimensions
		int width3 = (int) (inputHeight * Math.abs(sin) + inputWidth * Math.cos(rad__angle) + 0.5) + 1;
		int height3 = height2;
		
		// Allocate image for 3rd shear
		RasterImage outputImage3 = new RasterImage(width3, height3);
		
		if (sin >= 0)
		{
			// Positive angle
			d__offset = (inputWidth - 1.0) * sin * -tan;
		}
		else
		{
			// Negative angle
			d__offset = tan * ((inputWidth - 1.0) * -sin + (1.0 - height3));
		}
		
		for (int i = 0; i < height3; i++, d__offset += tan)
		{
			int i__shear = (int) (Math.floor(d__offset));
			RotationByShear.skewHorizontally(outputImage2, outputImage3, i, i__shear, d__offset - i__shear, bgColor);
		}
		
		// Return result of 3rd shear
		return outputImage3;
	}
	
	private static void skewHorizontally(RasterImage inputImage, RasterImage outputImage, int row, int offset, double weight, int bgColor)
	{
		int xPos;
		int inputWidth = inputImage.getWidth();
		int outputWidth = outputImage.getWidth();
		int pxlSrc, pxlLeft, pxlOldLeft;
		
		// fill gap left of skew with background
		for (int i = 0; i < offset; i++)
		{
			outputImage.setRgb(i, row, bgColor);
		}
		
		pxlOldLeft = bgColor;
		
		for (int i = 0; i < inputWidth; i++)
		{
			// loop through row pixels
			
			
			pxlSrc = inputImage.getRgb(i, row);
						
			// calculate weights
			
			pxlLeft = (int) ((bgColor & 255) + ((pxlSrc & 255) - (bgColor & 255)) * weight + 0.5);
			
			if (pxlLeft > 255)
			{
				pxlLeft = 255;
			}
			
			if (pxlLeft < 0)
			{
				pxlLeft = 0;
			}
			
			int g = (int) (((bgColor & 0xff00) >> 8) + (((pxlSrc & 0xff00) >> 8) - ((bgColor & 0xff00) >> 8)) * weight + 0.5);
			
			if (g > 255)
			{
				g = 255;
			}
			
			if (g < 0)
			{
				g = 0;
			}
			
			pxlLeft |= g << 8;
			
			int r = (int) (((bgColor & 0xff0000) >> 16) + (((pxlSrc & 0xff0000) >> 16) - ((bgColor & 0xff0000) >> 16)) * weight + 0.5);
			
			if (r > 255)
			{
				r = 255;
			}
			
			if (r < 0)
			{
				r = 0;
			}
			
			pxlLeft |= r << 16;
			
			// check boundaries
		
			xPos = i + offset;
			
			if (xPos >= 0 && xPos < outputWidth)
			{
				// update left over on input image
				
				int newPixel = (pxlSrc & 255) - ((pxlLeft & 255) - (pxlOldLeft & 255));
			
				if (newPixel > 255)
				{
					newPixel = 255;
				}
				
				if (newPixel < 0)
				{
					newPixel = 0;
				}
				
				g = ((pxlSrc & 0xff00) >> 8) - (((pxlLeft & 0xff00) >> 8) - ((pxlOldLeft & 0xff00) >> 8));
				
				if (g > 255)
				{
					g = 255;
				}
				
				if (g < 0)
				{
					g = 0;
				}
				
				newPixel |= g << 8;
				
				r = ((pxlSrc & 0xff0000) >> 16) - (((pxlLeft & 0xff0000) >> 16) - ((pxlOldLeft & 0xff0000) >> 16));
				
				if (r > 255)
				{
					r = 255;
				}
				
				if (r < 0)
				{
					r = 0;
				}
				
				newPixel |= r << 16;
				
				outputImage.setRgb(xPos, row, newPixel);
			}
			
			// save leftover for next pixel in scan
			pxlOldLeft = pxlLeft;
		}
		
		// go to rightmost point of skew
		xPos = inputWidth + offset;
		
		if (xPos >= 0 && xPos < outputWidth)
		{
			// If still in image bounds, put leftovers there
			outputImage.setRgb(xPos, row, pxlOldLeft);
			
			// clear to the right of the skewed line with background
			for (int i = 0; i < outputWidth - xPos - 1; i++)
			{
				outputImage.setRgb(i + xPos + 1, row, bgColor);
			}
		}
	}
	
	private static void skewVertically(RasterImage inputImage, RasterImage outputImage, int column, int offset, double weight, int bgColor)
	{
		int yPos;
		int inputHeight = inputImage.getHeight();
		int targetHeight = outputImage.getHeight();
		int pxlSrc, pxlLeft, pxlOldLeft;
		
		// fill gap above skew with background
		for (int i = 0; i < offset; i++)
		{
			outputImage.setRgb(column, i, bgColor);
		}
		
		pxlOldLeft = bgColor;
		
		for (int i = 0; i < inputHeight; i++)
		{
			// loop through column pixels
			
			
			pxlSrc = inputImage.getRgb(column, i);
			
			// calculate weights
			
			pxlLeft = (int) ((bgColor & 255) + ((pxlSrc & 255) - (bgColor & 255)) * weight + 0.5);
			
			if (pxlLeft > 255)
			{
				pxlLeft = 255;
			}
			
			if (pxlLeft < 0)
			{
				pxlLeft = 0;
			}
			
			int g = (int) (((bgColor & 0xff00) >> 8) + (((pxlSrc & 0xff00) >> 8) - ((bgColor & 0xff00) >> 8)) * weight + 0.5);
			
			if (g > 255)
			{
				g = 255;
			}
			
			if (g < 0)
			{
				g = 0;
			}
			
			pxlLeft |= g << 8;
			
			int r = (int) (((bgColor & 0xff0000) >> 16) + (((pxlSrc & 0xff0000) >> 16) - ((bgColor & 0xff0000) >> 16)) * weight + 0.5);
			
			if (r > 255)
			{
				r = 255;
			}
			
			if (r < 0)
			{
				r = 0;
			}
			
			pxlLeft |= r << 16;
			
			// check boundaries
			
			yPos = i + offset;
			
			if (yPos >= 0 && yPos < targetHeight)
			{
				// update left over on input image
				
				int newPixel = (pxlSrc & 255) - ((pxlLeft & 255) - (pxlOldLeft & 255));
				
				if (newPixel > 255)
				{
					newPixel = 255;
				}
				
				if (newPixel < 0)
				{
					newPixel = 0;
				}
				
				g = ((pxlSrc & 0xff00) >> 8) - (((pxlLeft & 0xff00) >> 8) - ((pxlOldLeft & 0xff00) >> 8));
				
				if (g > 255)
				{
					g = 255;
				}
				
				if (g < 0)
				{
					g = 0;
				}
				
				newPixel |= g << 8;
				
				r = ((pxlSrc & 0xff0000) >> 16) - (((pxlLeft & 0xff0000) >> 16) - ((pxlOldLeft & 0xff0000) >> 16));
				
				if (r > 255)
				{
					r = 255;
				}
				
				if (r < 0)
				{
					r = 0;
				}
				
				newPixel |= r << 16;
				
				outputImage.setRgb(column, yPos, newPixel);
			}
			
			// save leftover for next pixel in scan
			pxlOldLeft = pxlLeft;
		}
		
		// go to bottom point of skew
		yPos = inputHeight + offset;
		
		if (yPos >= 0 && yPos < targetHeight)
		{
			// If still in image bounds, put leftovers there
			outputImage.setRgb(column, yPos, pxlOldLeft);
			
			// clear to the right of the skewed line with background
			for (int i = 0; i < targetHeight - yPos - 1; i++)
			{
				outputImage.setRgb(column, i + yPos + 1, bgColor);
			}
		}
	}
}
