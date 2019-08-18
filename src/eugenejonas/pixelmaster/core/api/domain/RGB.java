
package eugenejonas.pixelmaster.core.api.domain;


/**
 * This class contains methods for manipulation with RGB color model.
 */
public final class RGB
{
	/**
	 * Default red coefficient.
	 */
	public static final int DEFAULT_RED_COEFF = 30;

	/**
	 * Default green coefficient.
	 */
	public static final int DEFAULT_GREEN_COEFF = 59;

	/**
	 * Default blue coefficient.
	 */
	public static final int DEFAULT_BLUE_COEFF = 11;

	
	/**
	 * Gets red component of pixel color.
	 * 
	 * @param rgb Has format 0xaarrggbb or 0x00rrggbb.
	 * @return Red component (0..255).
	 */
	public static int getR(int rgb)
	{
		return (rgb >> 16) & 255;
	}

	/**
	 * Gets green component of pixel color.
	 * 
	 * @param rgb Has format 0xaarrggbb or 0x00rrggbb.
	 * @return Green component (0..255).
	 */
	public static int getG(int rgb)
	{
		return (rgb >> 8) & 255;
	}

	/**
	 * Gets blue component of pixel color.
	 * 
	 * @param rgb Has format 0xaarrggbb or 0x00rrggbb.
	 * @return Blue component (0..255).
	 */
	public static int getB(int rgb)
	{
		return rgb & 255;
	}

	/**
	 * Returns integer pixel in RGB color model BufferedImage.TYPE_INT_RGB
	 * with specified red, green and blue component values.
	 * 
	 * @param r Red component (0..255).
	 * @param g Green component (0..255).
	 * @param b Blue component (0..255).
	 * @return Pixel color in format 0x00rrggbb.
	 */
	public static int getRgb(int r, int g, int b)
	{
		return b + (g << 8) + (r << 16);
	}

	/**
	 * Calculates intensity of pixel using default red, green and blue coefficients.
	 * 
	 * @param rgb Pixel value (has format 0xaarrggbb or 0x00rrggbb).
	 * @return Intensity (lies in range [0..255]).
	 */
	public static int getIntensity(int rgb)
	{
		return RGB.getIntensity(rgb, DEFAULT_RED_COEFF, DEFAULT_GREEN_COEFF, DEFAULT_BLUE_COEFF);
	}

	/**
	 * Calculates intensity of pixel.
	 *
	 * @param rgb Pixel value (has format 0xaarrggbb or 0x00rrggbb).
	 * @param kr Coefficient of red channel (0..100).
	 * @param kg Coefficient of green channel (0..100).
	 * @param kb Coefficient of blue channel (0..100).
	 * @return Intensity (lies in range [0..255]).
	 */
	public static int getIntensity(int rgb, int kr, int kg, int kb)
	{
		assert kr >= 0 && kr <= 100 && kg >= 0 && kg <= 100 && kb >= 0 && kb <= 100 && kr + kg + kb == 100;
		
		int r = RGB.getR(rgb);
		int g = RGB.getG(rgb);
		int b = RGB.getB(rgb);
		
		int c = kr * r + kg * g + kb * b;
		
		return c / 100;
	}
}
