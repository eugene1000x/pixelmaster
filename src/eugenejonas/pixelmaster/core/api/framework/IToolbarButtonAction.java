
package eugenejonas.pixelmaster.core.api.framework;


import javax.swing.*;


/**
 * This interface represents action associated with toolbar button of main window.
 * It will be invoked when user presses the toolbar button.
 */
public interface IToolbarButtonAction extends IAction
{
	/**
	 * Returns icon which will be visible on the button in toolbar.
	 *
	 * @return Non-null pointer.
	 */
	public Icon getLargeIcon();
}
