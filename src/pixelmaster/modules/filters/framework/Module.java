
package pixelmaster.modules.filters.framework;


import pixelmaster.core.api.domain.*;
import pixelmaster.core.api.framework.*;
import pixelmaster.modules.filters.domain.*;
import pixelmaster.modules.filters.gui.*;

import java.awt.event.*;
import javax.swing.*;


public class Module implements IModule
{
	private final class Action_open_filter_window implements IMenuItemAction
	{
		private final class Action_copy_image_to_main_window extends AbstractAction
		{
			private Action_copy_image_to_main_window()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					Module.this.workbench.setActiveImage(Module.this.filterWindowModel.getImage(), ObjectOwnership.OWNERSHIP_CALLER);
				}
				catch (ImageSizeConstraintViolationException exc)
				{
					assert false;
				}
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "Copy to main window";
				}
				else
				{
					return super.getValue(key);
				}
			}
		}
		
		
		private final String menuTitle = "Tools";
		private final String actionName = "Filtering";
		private final Icon smallIcon = null;
		
		
		private Action_open_filter_window()
		{
			//nothing
		}
				
		@Override
		public void onActionPerformed()
		{
			/*
			 * Keeping track of window instances.
			 * Only one window may be opened at a time.
			 */
			if (Module.this.filterWindow == null)
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
						Module.this.filterWindow = null;
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
				
				Module.this.filterWindowModel = new FilterWindowModel(Module.this.workbench.getActiveImage());
				Module.this.filterWindow = new FilterWindow(Module.this.filterWindowModel, new Action_copy_image_to_main_window());
				
				/*
				 * This listener assigns null to the pointer when window
				 * closes so that garbage collector can free memory.
				 */
				Module.this.filterWindow.addWindowListener(new WindowListenerImpl());
			}
			else
			{
				Module.this.filterWindow.toFront();
			}
		}
		
		@Override
		public boolean isEnabled()
		{
			return Module.this.workbench.isImageLoaded();
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
	
	private final class Action_invert implements IMenuItemAction
	{
		private final String menuTitle = "Edit";
		private final String actionName = "Invert Colors";
		private final Icon smallIcon = null;
		
		
		private Action_invert()
		{
			//nothing
		}
		
		@Override
		public Icon getSmallIcon()
		{
			return this.smallIcon;
		}
		
		@Override
		public boolean isEnabled()
		{
			return Module.this.workbench.isImageLoaded();
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
		
		@Override
		public void onActionPerformed()
		{
			RasterImage invertedImage = new RasterImage(
				Module.this.workbench.getActiveImage().getWidth(),
				Module.this.workbench.getActiveImage().getHeight()
			);
			Inverting.invertColors(Module.this.workbench.getActiveImage(), invertedImage);
			
			try
			{
				Module.this.workbench.setActiveImage(invertedImage, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
	}
	
	
	// actions
	private Action_open_filter_window action_open_filter_window = new Action_open_filter_window();
	private Action_invert action_invert = new Action_invert();
	
	// workbench
	private IWorkbench workbench;
	
	// gui
	private FilterWindowModel filterWindowModel;
	private FilterWindow filterWindow = null;
	
	
	public Module()
	{
		//nothing
	}
	
	@Override
	public void init(IControlElementRegistry registry, IWorkbench workbench)
	{
		this.workbench = workbench;
		
		registry.registerMenuItem(this.action_open_filter_window);
		registry.registerMenuItem(this.action_invert);
	}
}
