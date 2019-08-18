
package eugenejonas.pixelmaster.modules.binarization.framework;


import eugenejonas.pixelmaster.core.api.domain.*;
import eugenejonas.pixelmaster.core.api.framework.*;
import eugenejonas.pixelmaster.modules.binarization.domain.*;
import eugenejonas.pixelmaster.modules.binarization.gui.*;

import java.awt.event.*;
import javax.swing.*;


public class Module implements IModule
{
	private final class Action_open_binarization_window implements IMenuItemAction
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
					Module.this.workbench.setActiveImage(Module.this.binarizationWindowModel.getImage(), ObjectOwnership.OWNERSHIP_CALLER);
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
		private final String actionName = "Binarization";
		private final Icon smallIcon = null;
		
		
		private Action_open_binarization_window()
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
			if (Module.this.binarizationWindow == null)
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
						Module.this.binarizationWindow = null;
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
				
				Module.this.binarizationWindowModel = new BinarizationWindowModel(Module.this.workbench.getActiveImage());
				Module.this.binarizationWindow = new BinarizationWindow(Module.this.binarizationWindowModel, new Action_copy_image_to_main_window());
				
				/*
				 * This listener assigns null to the pointer when window
				 * closes so that garbage collector can free memory.
				 */
				Module.this.binarizationWindow.addWindowListener(new WindowListenerImpl());
			}
			else
			{
				Module.this.binarizationWindow.toFront();
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
	
	private final class Action_convert_to_gray_scale implements IMenuItemAction
	{
		private final String menuTitle = "Edit";
		private final String actionName = "Convert to gray-scale";
		private final Icon smallIcon = null;
		
		
		private Action_convert_to_gray_scale()
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
			RasterImage grayScaleImage = new RasterImage(Module.this.workbench.getActiveImage().getWidth(), Module.this.workbench.getActiveImage().getHeight());
			GrayScale.performGlobalGrayScaling(Module.this.workbench.getActiveImage(), grayScaleImage);
			
			try
			{
				Module.this.workbench.setActiveImage(grayScaleImage, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
	}
	
	
	// actions
	private Action_open_binarization_window action_open_binarization_window = new Action_open_binarization_window();
	private Action_convert_to_gray_scale action_convert_to_gray_scale = new Action_convert_to_gray_scale();
	
	// workbench
	private IWorkbench workbench;
	
	// gui
	private BinarizationWindowModel binarizationWindowModel;
	private BinarizationWindow binarizationWindow = null;
	
	
	public Module()
	{
		//nothing
	}
	
	@Override
	public void init(IControlElementRegistry registry, IWorkbench workbench)
	{
		this.workbench = workbench;
		
		registry.registerMenuItem(this.action_open_binarization_window);
		registry.registerMenuItem(this.action_convert_to_gray_scale);
	}
}
