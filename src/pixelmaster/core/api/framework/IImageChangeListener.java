
package pixelmaster.core.api.framework;


import java.util.*;


/**
 * This interface allows modules to listen for image change events
 * (when image in main window changes - user presses undo/redo, modifies or closes image or opens another image).
 */
public interface IImageChangeListener extends EventListener
{
	/**
	 * Callback method.
	 */
	public void onImageChanged();
}
