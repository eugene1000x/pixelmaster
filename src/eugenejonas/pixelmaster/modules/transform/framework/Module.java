
package eugenejonas.pixelmaster.modules.transform.framework;


import eugenejonas.pixelmaster.core.api.domain.*;
import eugenejonas.pixelmaster.core.api.framework.*;
import eugenejonas.pixelmaster.modules.transform.domain.*;
import eugenejonas.pixelmaster.modules.transform.domain.resizing.*;
import eugenejonas.pixelmaster.modules.transform.domain.rotation.*;
import eugenejonas.pixelmaster.modules.transform.gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Module implements IModule
{
	private final class Action_crop implements IMenuItemAction
	{
		private final String menuTitle = "Edit";
		private final String actionName = "Crop";
		private final Icon smallIcon = null;
		
		
		private Action_crop()
		{
			//nothing
		}
		
		@Override
		public void onActionPerformed()
		{
			Rectangle selection = Module.this.workbench.getRectangularSelection();
			
			if (selection == null)
			{
				JOptionPane.showMessageDialog(null, "No area selected.");
				return;
			}
			
			RasterImage tmp = Cropping.crop(Module.this.workbench.getActiveImage(), selection);
			
			try
			{
				Module.this.workbench.setActiveImage(tmp, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
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
	
	private final class Action_rotate_cw implements IToolbarButtonAction, IMenuItemAction
	{
		private final String menuTitle = "Edit";
		private final String actionName = "Rotate clockwise";
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/modules/transform/gui/rotate_cw_32.png"));
		private final Icon smallIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/modules/transform/gui/rotate_cw_16.png"));
		
		
		private Action_rotate_cw()
		{
			//nothing
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
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
			RasterImage rotated = new RasterImage(
				Module.this.workbench.getActiveImage().getWidth(),
				Module.this.workbench.getActiveImage().getHeight()
			);
			RotationBy90Degrees.rotateCw(Module.this.workbench.getActiveImage(), rotated);
			
			try
			{
				Module.this.workbench.setActiveImage(rotated, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
	}
	
	private final class Action_rotate_acw implements IToolbarButtonAction, IMenuItemAction
	{
		private final String menuTitle = "Edit";
		private final String actionName = "Rotate anti-clockwise";
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/modules/transform/gui/rotate_acw_32.png"));
		private final Icon smallIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/modules/transform/gui/rotate_acw_16.png"));
		
		
		private Action_rotate_acw()
		{
			//nothing
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
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
			RasterImage rotated = new RasterImage(
				Module.this.workbench.getActiveImage().getWidth(),
				Module.this.workbench.getActiveImage().getHeight()
			);
			RotationBy90Degrees.rotateAcw(Module.this.workbench.getActiveImage(), rotated);
			
			try
			{
				Module.this.workbench.setActiveImage(rotated, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
	}
	
	private final class Action_flip_horizontally implements IMenuItemAction
	{
		private final String menuTitle = "Edit";
		private final String actionName = "Flip horizontally";
		private final Icon smallIcon = null;
		
		
		private Action_flip_horizontally()
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
			RasterImage flipped = new RasterImage(
				Module.this.workbench.getActiveImage().getWidth(),
				Module.this.workbench.getActiveImage().getHeight()
			);
			Flipping.flipHorizontally(Module.this.workbench.getActiveImage(), flipped);
			
			try
			{
				Module.this.workbench.setActiveImage(flipped, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
	}
	
	private final class Action_flip_vertically implements IMenuItemAction
	{
		private final String menuTitle = "Edit";
		private final String actionName = "Flip vertically";
		private final Icon smallIcon = null;
		
		
		private Action_flip_vertically()
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
			RasterImage flipped = new RasterImage(
				Module.this.workbench.getActiveImage().getWidth(),
				Module.this.workbench.getActiveImage().getHeight()
			);
			Flipping.flipVertically(Module.this.workbench.getActiveImage(), flipped);
			
			try
			{
				Module.this.workbench.setActiveImage(flipped, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
	}
	
	private final class Action_stretch_horizontally implements IMenuItemAction
	{
		private final String menuTitle = "Edit";
		private final String actionName = "Stretch horizontally";
		private final Icon smallIcon = null;
		
		
		private Action_stretch_horizontally()
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
			return
				Module.this.workbench.isImageLoaded()
				&& Module.this.workbench.getActiveImage().getWidth() * 2 <= ImageSizeConstraints.MAX_IMAGE_WIDTH;
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
			RasterImage stretched = NearestNeighborResampling.performNearestNeighborResampling(Module.this.workbench.getActiveImage(), 2, 1);
			
			try
			{
				Module.this.workbench.setActiveImage(stretched, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
	}
	
	private final class Action_stretch_vertically implements IMenuItemAction
	{
		private final String menuTitle = "Edit";
		private final String actionName = "Stretch vertically";
		private final Icon smallIcon = null;
		
		
		private Action_stretch_vertically()
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
			return
				Module.this.workbench.isImageLoaded()
				&& Module.this.workbench.getActiveImage().getHeight() * 2 <= ImageSizeConstraints.MAX_IMAGE_HEIGHT;
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
			RasterImage stretched = NearestNeighborResampling.performNearestNeighborResampling(Module.this.workbench.getActiveImage(), 1, 2);
			
			try
			{
				Module.this.workbench.setActiveImage(stretched, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
	}
	
	private final class Action_zoom_in implements IToolbarButtonAction
	{
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/modules/transform/gui/zoom_in_32.png"));
		
		
		private Action_zoom_in()
		{
			//nothing
		}
		
		@Override
		public boolean isEnabled()
		{
			if (!Module.this.workbench.isImageLoaded())
			{
				return false;
			}
			
			int
				width = Module.this.workbench.getActiveImage().getWidth(),
				height = Module.this.workbench.getActiveImage().getHeight(),
				newWidth = (int) (width * 1.5),
				newHeight = (int) (height * 1.5);
			
			assert newWidth >= width;
			assert newHeight >= height;
			
			return newWidth <= ImageSizeConstraints.MAX_IMAGE_WIDTH && newHeight <= ImageSizeConstraints.MAX_IMAGE_HEIGHT && width > 1 && height > 1;
		}
		
		@Override
		public void onActionPerformed()
		{
			int
				width = Module.this.workbench.getActiveImage().getWidth(),
				height = Module.this.workbench.getActiveImage().getHeight(),
				newWidth = (int) (width * 1.5),
				newHeight = (int) (height * 1.5);
			
			RasterImage zoomedIn = SplineAlgorithm.resizeImage(Module.this.workbench.getActiveImage(), newWidth, newHeight);
			
			try
			{
				Module.this.workbench.setActiveImage(zoomedIn, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
		}
	}
	
	private final class Action_zoom_out implements IToolbarButtonAction
	{
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/modules/transform/gui/zoom_out_32.png"));
		
		
		private Action_zoom_out()
		{
			//nothing
		}
		
		@Override
		public boolean isEnabled()
		{
			if (!Module.this.workbench.isImageLoaded())
			{
				return false;
			}
			
			int
				width = Module.this.workbench.getActiveImage().getWidth(),
				height = Module.this.workbench.getActiveImage().getHeight(),
				newWidth = (int) (width * 0.67),
				newHeight = (int) (height * 0.67);
			
			assert newWidth <= width;
			assert newHeight <= height;
			
			return newWidth > 1 && newHeight > 1;
		}
		
		@Override
		public void onActionPerformed()
		{
			int
				width = Module.this.workbench.getActiveImage().getWidth(),
				height = Module.this.workbench.getActiveImage().getHeight(),
				newWidth = (int) (width * 0.67),
				newHeight = (int) (height * 0.67);
			
			RasterImage zoomedOut = SplineAlgorithm.resizeImage(Module.this.workbench.getActiveImage(), newWidth, newHeight);
			
			try
			{
				Module.this.workbench.setActiveImage(zoomedOut, ObjectOwnership.OWNERSHIP_CALLEE);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				assert false;
			}
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
		}
	}
	
	private final class Action_rotate implements IMenuItemAction
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
					Module.this.workbench.setActiveImage(Module.this.rotationWindowModel.getImage(), ObjectOwnership.OWNERSHIP_CALLER);
				}
				catch (ImageSizeConstraintViolationException exc)
				{
					JOptionPane.showMessageDialog(null, exc.getMessage());
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
		private final String actionName = "Rotate";
		private final Icon smallIcon = null;
		
		
		private Action_rotate()
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
			if (Module.this.rotationWindow == null)
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
						Module.this.rotationWindow = null;
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
				
				Module.this.rotationWindowModel = new RotationWindowModel(Module.this.workbench.getActiveImage());
				Module.this.rotationWindow = new RotationWindow(Module.this.rotationWindowModel, new Action_copy_image_to_main_window());
				
				/*
				 * This listener assigns null to the pointer when window
				 * closes so that garbage collector can free memory.
				 */
				Module.this.rotationWindow.addWindowListener(new WindowListenerImpl());
			}
			else
			{
				Module.this.rotationWindow.toFront();
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
	
	private final class Action_resize implements IMenuItemAction
	{
		private final class Action_apply_nearest_neighbor_resampling extends AbstractAction
		{
			private Action_apply_nearest_neighbor_resampling()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					RasterImage image = Module.this.resizingWindowModel.applyNearestNeighborResampling(Module.this.workbench.getActiveImage());
					Module.this.workbench.setActiveImage(image, ObjectOwnership.OWNERSHIP_CALLEE);
				}
				catch (ImageSizeConstraintViolationException exc)
				{
					JOptionPane.showMessageDialog(null, exc.getMessage());
				}
				catch (ResizingWindowModel.NotApplicableException exc)
				{
					JOptionPane.showMessageDialog(null, exc.getMessage());
				}
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "Apply";
				}
				else
				{
					return super.getValue(key);
				}
			}
		}
		
		private final class Action_apply_bilinear_resampling extends AbstractAction
		{
			private Action_apply_bilinear_resampling()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					RasterImage image = Module.this.resizingWindowModel.applyBilinearResampling(Module.this.workbench.getActiveImage());
					Module.this.workbench.setActiveImage(image, ObjectOwnership.OWNERSHIP_CALLEE);
				}
				catch (ImageSizeConstraintViolationException exc)
				{
					assert false;
				}
				catch (ResizingWindowModel.NotApplicableException exc)
				{
					JOptionPane.showMessageDialog(null, exc.getMessage());
				}
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "Apply";
				}
				else
				{
					return super.getValue(key);
				}
			}
		}
		
		
		private final String menuTitle = "Tools";
		private final String actionName = "Resize";
		private final Icon smallIcon = null;
		
		
		private Action_resize()
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
			if (Module.this.resizingWindow == null)
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
						Module.this.resizingWindow = null;
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
				
				Module.this.resizingWindowModel = new ResizingWindowModel();
				Module.this.resizingWindow = new ResizingWindow(
					Module.this.resizingWindowModel,
					new Action_apply_nearest_neighbor_resampling(),
					new Action_apply_bilinear_resampling(),
					Module.this.workbench.getActiveImage()
				);
				
				/*
				 * This listener assigns null to the pointer when window
				 * closes so that garbage collector can free memory.
				 */
				Module.this.resizingWindow.addWindowListener(new WindowListenerImpl());
			}
			else
			{
				Module.this.resizingWindow.toFront();
			}
		}
		
		@Override
		public boolean isEnabled()
		{
			return true;
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
	private Action_crop action_crop = new Action_crop();
	private Action_rotate_cw action_rotate_cw = new Action_rotate_cw();
	private Action_rotate_acw action_rotate_acw = new Action_rotate_acw();
	private Action_flip_horizontally action_flip_horizontally = new Action_flip_horizontally();
	private Action_flip_vertically action_flip_vertically = new Action_flip_vertically();
	private Action_stretch_horizontally action_stretch_horizontally = new Action_stretch_horizontally();
	private Action_stretch_vertically action_stretch_vertically = new Action_stretch_vertically();
	private Action_zoom_in action_zoom_in = new Action_zoom_in();
	private Action_zoom_out action_zoom_out = new Action_zoom_out();
	private Action_rotate action_rotate = new Action_rotate();
	private Action_resize action_resize = new Action_resize();
	
	// workbench
	private IWorkbench workbench;
	
	// gui
	private RotationWindowModel rotationWindowModel;
	private RotationWindow rotationWindow = null;
	private ResizingWindowModel resizingWindowModel;
	private ResizingWindow resizingWindow = null;
	
	
	public Module()
	{
		//nothing
	}
	
	@Override
	public void init(IControlElementRegistry registry, IWorkbench workbench)
	{
		this.workbench = workbench;
		
		final class ImageChangeListenerImpl implements IImageChangeListener
		{
			@Override
			public void onImageChanged()
			{
				if (Module.this.resizingWindow != null)
				{
					int
						newWidth = 0,
						newHeight = 0;
					
					if (workbench.getActiveImage() != null)
					{
						newWidth = workbench.getActiveImage().getWidth();
						newHeight = workbench.getActiveImage().getHeight();
					}
					
					Module.this.resizingWindow.updateImageSize(newWidth, newHeight);
				}
			}
		}
		
		workbench.registerImageChangeListener(new ImageChangeListenerImpl());
		
		registry.registerMenuItem(this.action_crop);
		registry.registerMenuItem(this.action_rotate_cw);
		registry.registerMenuItem(this.action_rotate_acw);
		registry.registerMenuItem(this.action_flip_horizontally);
		registry.registerMenuItem(this.action_flip_vertically);
		registry.registerMenuItem(this.action_stretch_horizontally);
		registry.registerMenuItem(this.action_stretch_vertically);
		registry.registerMenuItem(this.action_rotate);
		registry.registerMenuItem(this.action_resize);
		
		registry.registerToolbarButton(this.action_rotate_cw);
		registry.registerToolbarButton(this.action_rotate_acw);
		registry.registerToolbarButton(this.action_zoom_in);
		registry.registerToolbarButton(this.action_zoom_out);
	}
}
