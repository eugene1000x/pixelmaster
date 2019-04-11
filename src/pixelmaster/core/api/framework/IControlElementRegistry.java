
package pixelmaster.core.api.framework;


/**
 * This interface lets modules add control elements to main window.
 * Control elements include toolbar buttons and menu items.
 * Implementation of this interface will be provided by core at
 * {@link IModule#init initialization stage}.
 */
public interface IControlElementRegistry
{
	/**
	 * Lets module register menu item and corresponding callback.
	 * Core will add this item to menu of main window.
	 */
	public void registerMenuItem(IMenuItemAction callback);

	/**
	 * Lets module register toolbar button and corresponding callback.
	 * Core will add this button to toolbar of main window.
	 */
	public void registerToolbarButton(IToolbarButtonAction callback);
}
