
package pixelmaster.core.api.framework;


import javax.swing.*;


/**
 * This interface represents action associated with an item in menu of main window.
 * It will be invoked when user selects the menu item.
 */
public interface IMenuItemAction extends IAction
{
	/**
	 * Returns icon which will be visible in the menu item.
	 *
	 * @return If null, the menu item will have no icon.
	 */
	public Icon getSmallIcon();

	/**
	 * Returns name of this action which will be displayed as the menu item's title.
	 * E.g., if you want this action to be called by selecting menu item
	 * "Edit > My action", this method should return "My action".
	 *
	 * @return Non-null pointer.
	 */
	public String getName();

	/**
	 * Returns title of the top-level menu under which the menu item should be.
	 * E.g., if you want this action to be called by selecting menu item
	 * "Edit > My action", this method should return "Edit".
	 *
	 * @return Non-null pointer.
	 */
	public String getMenuTitle();
}
