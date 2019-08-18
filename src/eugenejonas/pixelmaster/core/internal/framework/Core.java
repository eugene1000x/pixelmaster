
package eugenejonas.pixelmaster.core.internal.framework;


import eugenejonas.pixelmaster.core.api.domain.*;
import eugenejonas.pixelmaster.core.api.framework.*;
import eugenejonas.pixelmaster.core.api.gui.*;

import java.awt.image.*;
import javax.swing.*;


/**
 * Core implements IModule interface for convenience, but actually it is not a module.
 */
public final class Core implements IModule
{
	private final class Action_open_file implements IToolbarButtonAction, IMenuItemAction
	{
		private final Icon smallIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/open_file_16.png"));
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/open_file_32.png"));
		private final String menuTitle = "File";
		private final String actionName = "Open";
		
		
		private Action_open_file()
		{
			//nothing
		}
		
		@Override
		public void onActionPerformed()
		{
			BufferedImage bufferedImage = OpenSaveDialogs.loadImage(null);
				
			if (bufferedImage == null)		// user pressed "Cancel"
			{
				return;
			}
			
			RasterImage rasterImage = new RasterImage(bufferedImage.getWidth(), bufferedImage.getHeight());
			ImageConverter.toRasterImage(bufferedImage, rasterImage);
			
			try
			{
				Core.this.workbench.onFileOpened(rasterImage);
			}
			catch (ImageSizeConstraintViolationException exc)
			{
				JOptionPane.showMessageDialog(null, exc.getMessage());
			}
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
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

	private final class Action_save_as implements IToolbarButtonAction, IMenuItemAction
	{
		private final Icon smallIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/save_as_16.png"));
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/save_as_32.png"));
		private final String menuTitle = "File";
		private final String actionName = "Save as";
		
		
		private Action_save_as()
		{
			//nothing
		}
		
		@Override
		public void onActionPerformed()
		{
			OpenSaveDialogs.saveImage(null, Core.this.workbench.getActiveImage());
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
		}
		
		@Override
		public boolean isEnabled()
		{
			return Core.this.workbench.isImageLoaded();
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
	
	private final class Action_close_file implements IToolbarButtonAction, IMenuItemAction
	{
		private final Icon smallIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/close_file_16.png"));
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/close_file_32.png"));
		private final String menuTitle = "File";
		private final String actionName = "Close";
		
		
		private Action_close_file()
		{
			//nothing
		}
		
		@Override
		public void onActionPerformed()
		{
			Object[] options = {"Yes", "No"};
			
			int selectedOption = JOptionPane.showOptionDialog(
				null,
				"Are you sure you want to close the file?",
				"Confirmation",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,						//don't use custom icon
				options,					//titles of buttons
				options[0]					//title of default button
			);
			
			if (selectedOption == 0)
			{
				Core.this.workbench.onFileClosed();
			}
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
		}
		
		@Override
		public boolean isEnabled()
		{
			return Core.this.workbench.isImageLoaded();
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
	
	private final class Action_undo_all implements IToolbarButtonAction, IMenuItemAction
	{
		private final Icon smallIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/undo_all_16.png"));
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/undo_all_32.png"));
		private final String menuTitle = "Edit";
		private final String actionName = "Undo all";
		
		
		private Action_undo_all()
		{
			//nothing
		}
		
		@Override
		public void onActionPerformed()
		{
			Core.this.workbench.undoAll();
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
		}
		
		@Override
		public boolean isEnabled()
		{
			return Core.this.workbench.mainWindowModel.getUndoRedoManager().canUndo();
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
	
	private final class Action_undo implements IToolbarButtonAction, IMenuItemAction
	{
		private final Icon smallIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/undo_16.png"));
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/undo_32.png"));
		private final String menuTitle = "Edit";
		private final String actionName = "Undo";
		
		
		private Action_undo()
		{
			//nothing
		}
		
		@Override
		public void onActionPerformed()
		{
			Core.this.workbench.undo();
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
		}
		
		@Override
		public boolean isEnabled()
		{
			return Core.this.workbench.mainWindowModel.getUndoRedoManager().canUndo();
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
	
	private final class Action_redo implements IToolbarButtonAction, IMenuItemAction
	{
		private final Icon smallIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/redo_16.png"));
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/redo_32.png"));
		private final String menuTitle = "Edit";
		private final String actionName = "Redo";
		
		
		private Action_redo()
		{
			//nothing
		}
		
		@Override
		public void onActionPerformed()
		{
			Core.this.workbench.redo();
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
		}
		
		@Override
		public boolean isEnabled()
		{
			return Core.this.workbench.mainWindowModel.getUndoRedoManager().canRedo();
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
	
	private final class Action_redo_all implements IToolbarButtonAction, IMenuItemAction
	{
		private final Icon smallIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/redo_all_16.png"));
		private final Icon largeIcon = new ImageIcon(this.getClass().getResource("/pixelmaster/core/internal/gui/redo_all_32.png"));
		private final String menuTitle = "Edit";
		private final String actionName = "Redo all";
		
		
		private Action_redo_all()
		{
			//nothing
		}
		
		@Override
		public void onActionPerformed()
		{
			Core.this.workbench.redoAll();
		}
		
		@Override
		public Icon getLargeIcon()
		{
			return this.largeIcon;
		}
		
		@Override
		public boolean isEnabled()
		{
			return Core.this.workbench.mainWindowModel.getUndoRedoManager().canRedo();
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
	
	
	private Action_open_file action_open_file = new Action_open_file();
	private Action_save_as action_save_as = new Action_save_as();
	private Action_close_file action_close_file = new Action_close_file();
	private Action_undo_all action_undo_all = new Action_undo_all();
	private Action_undo action_undo = new Action_undo();
	private Action_redo action_redo = new Action_redo();
	private Action_redo_all action_redo_all = new Action_redo_all();
	
	private Workbench workbench = null;
	
	
	Core()
	{
		//nothing
	}
	
	@Override
	public void init(IControlElementRegistry registry, IWorkbench workbench)
	{
		this.workbench = (Workbench) workbench;
		
		registry.registerMenuItem(this.action_open_file);
		registry.registerMenuItem(this.action_save_as);
		registry.registerMenuItem(this.action_close_file);
		registry.registerMenuItem(this.action_undo_all);
		registry.registerMenuItem(this.action_undo);
		registry.registerMenuItem(this.action_redo);
		registry.registerMenuItem(this.action_redo_all);
		
		registry.registerToolbarButton(this.action_open_file);
		registry.registerToolbarButton(this.action_save_as);
		registry.registerToolbarButton(this.action_close_file);
		registry.registerToolbarButton(this.action_undo_all);
		registry.registerToolbarButton(this.action_undo);
		registry.registerToolbarButton(this.action_redo);
		registry.registerToolbarButton(this.action_redo_all);
	}
}
