
package pixelmaster.core.api.domain;


/**
 * Exception that should be used to signal that size constraints defined in {@link ImageSizeConstraints} are being violated.
 */
public class ImageSizeConstraintViolationException extends Exception
{
	public ImageSizeConstraintViolationException(String message)
	{
		super(message);
	}
}
