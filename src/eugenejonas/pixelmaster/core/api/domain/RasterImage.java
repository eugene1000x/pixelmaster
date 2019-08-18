
package eugenejonas.pixelmaster.core.api.domain;


import java.awt.*;
import java.awt.image.*;
import java.util.*;


/**
 * Class representing raster image.
 */
public final class RasterImage implements Cloneable
{
	/**
	 * Needed to be able to draw image on the panel.
	 */
	private final class RasterImageProducer implements ImageProducer
	{
		/**
		 * Used by {@link java.awt.Component#createImage(java.awt.image.ImageProducer)}
		 * to create instance of {@link java.awt.Image}.
		 */
		@Override
		public void startProduction(ImageConsumer imageConsumer)
		{
			assert imageConsumer != null;
			
			imageConsumer.setDimensions(RasterImage.this.width, RasterImage.this.height);
			
			ColorModel colorModel = new DirectColorModel(
				32,
				0x00ff0000,		// Red
				0x0000ff00,		// Green
				0x000000ff,		// Blue
				0x00000000		// Alpha
			);
			
			imageConsumer.setColorModel(colorModel);
			imageConsumer.setHints(ImageConsumer.SINGLEPASS);
			imageConsumer.setPixels(0, 0, RasterImage.this.width, RasterImage.this.height, colorModel, RasterImage.this.pixels, 0, RasterImage.this.width);
			imageConsumer.imageComplete(ImageConsumer.STATICIMAGEDONE);
		}

		@Override
		public void addConsumer(ImageConsumer imageConsumer)
		{
			//nothing
		}
		
		@Override
		public boolean isConsumer(ImageConsumer imageConsumer)
		{
			return true;
		}
		
		@Override
		public void removeConsumer(ImageConsumer imageConsumer)
		{
			//nothing
		}
		
		@Override
		public void requestTopDownLeftRightResend(ImageConsumer imageConsumer)
		{
			//nothing
		}
	}

	/**
	 * Represents two-dimensional array of pixels. First dimension
	 * corresponds to horizontal axis x (from left to right) and can
	 * have values 0 .. getWidth() - 1. Second dimension corresponds to vertical
	 * axis y (from top to bottom) and can have values 0 .. getHeight() - 1.
	 *
	 * Pixel at coordinate [x, y] can be accessed as pixels[width * y + x].
	 * Two-dimensional array is modelled by one-dimensional array as follows:
	 *
	 * pixels[0 .. width - 1]:				M[0, 0] M[1, 0] ... M[width - 1, 0]
	 * pixels[width .. 2 * width - 1]:		M[0, 1] M[1, 1] ... M[width - 1, 1]
	 *								...
	 * 
	 *
	 * Pixel values must lie in range [0..0x00ffffff]. Pixel values have
	 * format 0x00rrggbb (alpha is not present, highest byte must be 0).
	 *
	 * Image is gray-scale if all RGB component values are equal:
	 * for example, 0x004f4f4f or 0x003a3a3a. Image is binary if all pixels have
	 * value 0x00000000 or 0x00ffffff.
	 */
	private int[] pixels;

	private int width, height;

	// needed to draw image on the panel
	private RasterImageProducer producer;
	
	
	/**
	 * Creates image with specified width and height.
	 * Initializes pixels with 0 (black).
	 * 
	 * @param width A positive integer.
	 * @param height A positive integer.
	 */
	public RasterImage(int width, int height)
	{
		assert width >= 1 && height >= 1;
		
		
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];		// initializes pixels with 0
		this.producer = new RasterImageProducer();
		
		
		assert this.invariant();
	}
	
	/**
	 * Creates copy of image.
	 * 
	 * @param image Image that must be copied.
	 */
	public RasterImage(RasterImage image)
	{
		assert image != null;
		
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.pixels = new int[this.width * this.height];
		this.producer = new RasterImageProducer();
		
		System.arraycopy(image.pixels, 0, this.pixels, 0, this.height * this.width);
		
		
		assert this.invariant();
	}

	private boolean invariant()
	{
		assert this.width >= 1 && this.height >= 1 && this.producer != null && this.pixels != null;
		
		int size = this.width * this.height;
		
		assert this.pixels.length == size;
		
		// highest byte must be 0
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			for (int i = 0; i < size; i++)
			{
				assert (this.pixels[i] & 0xff000000) == 0;
			}
		}
		
		return true;
	}

	/**
	 * @return Width.
	 */
	public int getWidth()
	{
		return this.width;
	}

	/**
	 * @return Height.
	 */
	public int getHeight()
	{
		return this.height;
	}

	/**
	 * @return Size (number of pixels).
	 */
	public int getSize()
	{
		return this.width * this.height;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clone()
	{
		try
		{
			RasterImage res = (RasterImage) super.clone();
			
			res.pixels = res.pixels.clone();
			res.producer = res.new RasterImageProducer();
			
			assert res.invariant();
			return res;
		}
		catch (CloneNotSupportedException exc)
		{
			// this shouldn't happen
			assert false;
			return null;
		}
	}

	/**
	 * Two instances of RasterImage are equal if they have the same width, height and pixel values.
	 *
	 * @param obj {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof RasterImage))
		{
			return super.equals(obj);
		}
		
		RasterImage other = (RasterImage) obj;
		
		if (this.getWidth() != other.getWidth() || this.getHeight() != other.getHeight())
		{
			return false;
		}
		
		return Arrays.equals(this.pixels, other.pixels);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		int hash = 7;
		
		hash = 11 * hash + this.getWidth();
		hash = 11 * hash + this.getHeight();
		
		return hash;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return String containing information about width and height of the image.
	 */
	@Override
	public String toString()
	{
		return "width: "+ this.width +", height: "+ this.height;
	}

	/**
	 * Gets pixel value.
	 * 
	 * @param x Horizontal coordinate in range [0 .. <getWidth() - 1>].
	 * @param y Vertical coordinate in range [0 .. <getHeight() - 1>].
	 * @return 0x00rrggbb
	 */
	public int getRgb(int x, int y)
	{
		assert x >= 0 && x < this.width && y >= 0 && y < this.height;
		
		return this.pixels[this.getWidth() * y + x];
	}

	/**
	 * Gets pixel value.
	 * 
	 * @param i Index in range [0 .. <getSize() - 1>].
	 * @return 0x00rrggbb
	 */
	public int getRgb(int i)
	{
		assert i >= 0 && i < this.getSize();
		
		return this.pixels[i];
	}

	/**
	 * Sets pixel value.
	 * 
	 * @param x Horizontal coordinate in range [0 .. <getWidth() - 1>].
	 * @param y Vertical coordinate in range [0 .. <getHeight() - 1>].
	 * @param value Pixel value in format 0x00rrggbb.
	 */
	public void setRgb(int x, int y, int value)
	{
		assert x >= 0 && x < this.width && y >= 0 && y < this.height && (value & 0xff000000) == 0;
		//assert this.invariant();
		
		this.setRgb(this.getWidth() * y + x, value);
	}

	/**
	 * Sets pixel value.
	 * 
	 * @param i Index in range [0 .. <getSize() - 1>].
	 * @param value Pixel value in format 0x00rrggbb.
	 */
	public void setRgb(int i, int value)
	{
		assert i >= 0 && i < this.getSize() && (value & 0xff000000) == 0;
		//assert this.invariant();
		
		this.pixels[i] = value;
		assert this.invariant();
	}

	public Color getColor(int x, int y)
	{
		int rgb = this.pixels[this.getPixelIndex(x, y)];

		return new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, 255);
	}

	public Color getColor(int index)
	{
		int value = this.pixels[index];

		return new Color(
			(value >>> 16) & 0xFF,
			(value >>> 8) & 0xFF,
			value & 0xFF,
			255
		);
	}

	public void setColor(int x, int y, Color color)
	{
		this.setColor(this.getPixelIndex(x, y), color);
	}

	public void setColor(int index, Color color)
	{
		this.pixels[index] =
			(color.getRed() << 16) & 0x00FF0000
			| (color.getGreen() << 8) & 0x0000FF00
			| color.getBlue() & 0x000000FF;
	}

	public int[] getPixels()
	{
		return this.pixels.clone();
	}

	/**
	 * Copies this image to another image.
	 * Output image will have the same size as this image.
	 *
	 * @param outputImage Output image. If outputImage == this image, call has no effect.
	 */
	public void copyTo(RasterImage outputImage)
	{
		assert outputImage != null;
		
		
		if (this == outputImage)
		{
			return;
		}
		
		int size = this.pixels.length;
		
		if (outputImage.pixels.length != size)
		{
			outputImage.pixels = new int[size];
		}
		
		outputImage.width = this.width;
		outputImage.height = this.height;
		System.arraycopy(this.pixels, 0, outputImage.pixels, 0, size);
		
		
		assert outputImage.invariant();
	}

	/**
	 * Copies pixels of this image to the specified image.
	 * 
	 * @param to May point to the same object.
	 * @param srcPos
	 * @param destPos
	 * @param length
	 * @see System#arraycopy(java.lang.Object, int, java.lang.Object, int, int)
	 */
	public void copyTo(RasterImage to, int srcPos, int destPos, int length)
	{
		System.arraycopy(this.pixels, srcPos, to.pixels, destPos, length);
	}

	/**
	 * Copies pixels of this image to the specified array.
	 * 
	 * @param to
	 * @param srcPos
	 * @param destPos
	 * @param length
	 * @see System#arraycopy(java.lang.Object, int, java.lang.Object, int, int)
	 */
	public void copyTo(int[] to, int srcPos, int destPos, int length)
	{
		System.arraycopy(this.pixels, srcPos, to, destPos, length);
	}

	/**
	 * Copies pixels from the specified array.
	 * 
	 * @param from
	 * @param srcPos
	 * @param destPos
	 * @param length
	 * @see System#arraycopy(java.lang.Object, int, java.lang.Object, int, int)
	 */
	public void copyFrom(int[] from, int srcPos, int destPos, int length)
	{
		System.arraycopy(from, srcPos, this.pixels, destPos, length);
		
		assert this.invariant();
	}

	private boolean areCoordsValid(int x, int y)
	{
		return y >= 0 && x >= 0 && y < this.height && x < this.width;
	}

	private int getPixelIndex(int x, int y)
	{
		assert this.areCoordsValid(x, y) : "Pixel at x == "+ x +", y == "+ y +" is out of image boundaries";
		
		return y * this.width + x;
	}

	/**
	 * Checks if this image is binary (pixel values are either 0 or 0x00ffffff).
	 * 
	 * @return true if this image is binary.
	 */
	public boolean isBinary()
	{
		int size = this.width * this.height;
		
		for (int i = 0; i < size; i++)
		{
			if (this.pixels[i] != 0x00ffffff && this.pixels[i] != 0)
			{
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Returns gray value (intensity) of pixel at specified coordinates using default
	 * red, green and blue coefficients.
	 *
	 * @param x Horizontal coordinate.
	 * @param y Vertical coordinate.
	 * @return Gray value (intensity).
	 */
	public int getGrayValueAt(int x, int y)
	{
		assert x >= 0 && x < this.width && y >= 0 && y < this.height;
		
		return this.getGrayValueAt(this.width * y + x);
	}

	/**
	 * Returns gray value (intensity) of the pixel at specified index.
	 *
	 * @param i Array index.
	 * @return Gray value (intensity) in range [0..255].
	 */
	public int getGrayValueAt(int i)
	{
		assert i >= 0 && i < this.width * this.height;
		
		return RGB.getIntensity(this.pixels[i]);
	}

	/**
	 * Checks if rectangular area (selection) is within bounds of this image.
	 * 
	 * @param selection Rectangular area (selection).
	 * @return true if it is in bounds.
	 */
	public boolean isSelectionInBounds(Rectangle selection)
	{
		return
			selection.width >= 0 && selection.height >= 0
			&& this.width > selection.x && selection.x >= 0
			&& this.height > selection.y && selection.y >= 0
			&& this.width >= selection.x + selection.width
			&& this.height >= selection.y + selection.height;
	}

	/**
	 * Image producer is used to draw image on the panel.
	 * 
	 * @return Image producer.
	 */
	public ImageProducer getSource()
	{
		return this.producer;
	}

	/**
	 * Recreates pixel buffer. Pixel values are undefined.
	 * 
	 * @param newWidth Positive integer.
	 * @param newHeight Positive integer.
	 */
	public void resize(int newWidth, int newHeight)
	{
		assert newWidth > 0 && newHeight > 0;
		assert this.invariant();
		
		this.width = newWidth;
		this.height = newHeight;
		
		int size = newWidth * newHeight;
		
		if (size != this.pixels.length)
		{
			this.pixels = new int[size];			// inits to zero
		}
		
		assert this.invariant();
	}

	/**
	 * Sets all pixels of this image to the specified color.
	 * 
	 * @param color Has format 0x00rrggbb.
	 */
	public void fill(int color)
	{
		assert (color & 0xff000000) == 0;
		assert this.invariant();
		
		Arrays.fill(this.pixels, color);
		
		assert this.invariant();
	}

	/**
	 * Sets specified pixels of this image to the specified color.
	 * 
	 * @param color Has format 0x00rrggbb.
	 * @param fromIndex Index of first pixel (inclusive) to be
	 * 		set to the specified color.
	 * @param toIndex Index of last pixel (exclusive) to be
	 * 		set to the specified color.
	 */
	public void fill(int color, int fromIndex, int toIndex)
	{
		assert (color & 0xff000000) == 0;
		assert this.invariant();
		
		Arrays.fill(this.pixels, fromIndex, toIndex, color);
		
		assert this.invariant();
	}
}
