
package eugenejonas.pixelmaster.modules.spherical_wave.framework;


import eugenejonas.pixelmaster.core.api.framework.*;
import eugenejonas.pixelmaster.modules.spherical_wave.gui.*;

import java.awt.event.*;
import javax.swing.*;


public class Module implements IModule
{
	private final class Action_open_spw_window implements IMenuItemAction
	{
		private final String menuTitle = "Tools";
		private final String actionName = "Spherical wave";
		private final Icon smallIcon = null;
		
		
		private Action_open_spw_window()
		{
			//nothing
		}
		
		@Override
		public void onActionPerformed()
		{
			assert Module.this.workbench.getActiveImage().isBinary();
			
			
			/*
			 * Keeping track of window instances.
			 * Only one window may be opened at a time.
			 */
			if (Module.this.spwWindow == null)
			{
				final class WindowListenerImpl implements WindowListener
				{
					private WindowListenerImpl()
					{
						//nothing
					}
					
					@Override
					public void windowOpened(WindowEvent event)
					{
						//nothing
					}
					
					@Override
					public void windowClosing(WindowEvent event)
					{
						Module.this.spwWindow = null;
					}
					
					@Override
					public void windowClosed(WindowEvent event)
					{
						//nothing
					}
					
					@Override
					public void windowIconified(WindowEvent event)
					{
						//nothing
					}
					
					@Override
					public void windowDeiconified(WindowEvent event)
					{
						//nothing
					}
					
					@Override
					public void windowActivated(WindowEvent event)
					{
						//nothing
					}
					
					@Override
					public void windowDeactivated(WindowEvent event)
					{
						//nothing
					}
				}
				
				Module.this.spwWindowModel = new SphericalWaveWindowModel(Module.this.workbench.getActiveImage());
				Module.this.spwWindow = new SphericalWaveWindow(Module.this.spwWindowModel);
				
				/*
				 * This listener assigns null to the pointer when window
				 * closes so that garbage collector can free memory.
				 */
				Module.this.spwWindow.addWindowListener(new WindowListenerImpl());
			}
			else
			{
				Module.this.spwWindow.toFront();
			}
		}
		
		@Override
		public boolean isEnabled()
		{
			return Module.this.workbench.isImageLoaded() && Module.this.workbench.getActiveImage().isBinary();
		}
		
		@Override
		public Icon getSmallIcon()
		{
			return this.smallIcon;
		}
		
		@Override
		public String getName()
		{
			return this.actionName;
		}
		
		@Override
		public String getMenuTitle()
		{
			return this.menuTitle;
		}
	}
	
	
	// actions
	private Action_open_spw_window action_open_spw_window = new Action_open_spw_window();
	
	// workbench
	private IWorkbench workbench;
	
	// gui
	private SphericalWaveWindow spwWindow = null;
	private SphericalWaveWindowModel spwWindowModel = null;
	
	
	public Module()
	{
		//nothing
	}
	
	@Override
	public void init(IControlElementRegistry registry, IWorkbench workbench)
	{
		this.workbench = workbench;
		registry.registerMenuItem(this.action_open_spw_window);
	}
}
