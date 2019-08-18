
package eugenejonas.pixelmaster.core.internal.framework;


import eugenejonas.pixelmaster.core.api.framework.*;

import java.awt.event.*;
import javax.swing.*;


/**
 * Wraps actions provided by modules and adapts them to swing interface to be used for menu items.
 */
public final class MenuItemActionWrapper extends AbstractAction
{
	private IMenuItemAction wrappedAction;
	
	
	MenuItemActionWrapper(IMenuItemAction action)
	{
		this.wrappedAction = action;
		
		this.setEnabled(action.isEnabled());
		this.putValue(Action.SMALL_ICON, this.wrappedAction.getSmallIcon());
		this.putValue(Action.NAME, this.wrappedAction.getName());
	}
	
	@Override
	public boolean isEnabled()
	{
		return this.wrappedAction.isEnabled();
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		this.wrappedAction.onActionPerformed();
	}
}
