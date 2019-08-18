
package eugenejonas.pixelmaster.core.internal.framework;


import eugenejonas.pixelmaster.core.api.framework.*;

import java.awt.event.*;
import javax.swing.*;


/**
 * Wraps actions provided by modules and adapts them to swing interface to be used for toolbar buttons.
 */
public final class ToolbarButtonActionWrapper extends AbstractAction
{
	private IToolbarButtonAction wrappedAction;
	
	
	ToolbarButtonActionWrapper(IToolbarButtonAction action)
	{
		this.wrappedAction = action;
		
		this.setEnabled(action.isEnabled());
		this.putValue(Action.LARGE_ICON_KEY, this.wrappedAction.getLargeIcon());
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
