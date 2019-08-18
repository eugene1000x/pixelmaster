
package eugenejonas.pixelmaster.core.api.framework;


import eugenejonas.pixelmaster.core.api.domain.*;

import java.awt.*;


/**
 * This interface provides modules access to user's workbench.
 * Workbench consists of main window with working image.
 * Implementation of this interface will be provided at {@link IModule#init initialization stage}.
 */
public interface IWorkbench
{
	/**
	 * Returns image that is currently displayed in main window.
	 *
	 * @return Currently displayed image, or null if no image is currently loaded.
	 * 		Ownership: {@link eugenejonas.pixelmaster.core.api.domain.ObjectOwnership#OWNERSHIP_CALLEE}.
	 */
	public RasterImage getActiveImage();

	/**
	 * Changes image that is currently displayed in main window.
	 *
	 * @param image New image (non-null object).
	 * @param ownership {@link eugenejonas.pixelmaster.core.api.domain.ObjectOwnership#OWNERSHIP_CALLER} or
	 * 		{@link eugenejonas.pixelmaster.core.api.domain.ObjectOwnership#OWNERSHIP_CALLEE}.
	 * @throws ImageSizeConstraintViolationException If image size violates contraints defined in
	 * 		{@link ImageSizeConstraints}.
	 */
	public void setActiveImage(RasterImage image, int ownership) throws ImageSizeConstraintViolationException;

	/**
	 * Checks if an image is currently displayed in main window.
	 *
	 * @return <code>true</code> if <code>getActiveImage() != null</code>.
	 */
	public boolean isImageLoaded();

	/**
	 * Returns current rectangular selection on image panel.
	 *
	 * @return Selected area, or null if no rectangular area is selected.
	 * 		Ownership: {@link eugenejonas.pixelmaster.core.api.domain.ObjectOwnership#OWNERSHIP_CALLER}.
	 */
	public Rectangle getRectangularSelection();

	/**
	 * Registers callback that will be invoked when image in main window changes.
	 */
	public void registerImageChangeListener(IImageChangeListener listener);
}
