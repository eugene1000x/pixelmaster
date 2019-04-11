
package pixelmaster.core.internal.framework;


import pixelmaster.core.api.domain.*;
import pixelmaster.core.api.framework.*;
import pixelmaster.core.internal.gui.*;

import java.awt.*;
import java.util.*;
import javax.swing.*;


public final class Workbench implements IWorkbench
{
	MainWindow mainWindow;
	MainWindowModel mainWindowModel;
	
	private JMenuBar menubar;
	private JToolBar toolbar;
	
	private Set <IImageChangeListener> listeners = new HashSet <IImageChangeListener> ();
	
	
	Workbench()
	{
		this.mainWindowModel = new MainWindowModel();
	}
	
	void initControlElements(JMenuBar menubar, JToolBar toolbar)
	{
		this.menubar = menubar;
		this.toolbar = toolbar;
		
		this.mainWindow.setJMenuBar(menubar);
		this.mainWindow.setToolbar(toolbar);
		this.mainWindow.setVisible(true);
	}
	
	void loadMainGui()
	{
		this.mainWindow = new MainWindow(this.mainWindowModel);
	}

	void onFileOpened(RasterImage image) throws ImageSizeConstraintViolationException
	{
		assert image != null;
		
		
		if (image.getWidth() < ImageSizeConstraints.MIN_IMAGE_WIDTH || image.getHeight() < ImageSizeConstraints.MIN_IMAGE_HEIGHT)
		{
			throw new ImageSizeConstraintViolationException("Image too small");
		}
		
		if (image.getWidth() > ImageSizeConstraints.MAX_IMAGE_WIDTH || image.getHeight() > ImageSizeConstraints.MAX_IMAGE_HEIGHT)
		{
			throw new ImageSizeConstraintViolationException("Image too large");
		}
		
		this.mainWindow.onFileOpened(image);
		this.notifyAndUpdate();
	}
	
	void onFileClosed()
	{
		this.mainWindow.onFileClosed();
		this.notifyAndUpdate();
	}
	
	void undo()
	{
		this.mainWindow.undo();
		this.notifyAndUpdate();
	}
	
	void redo()
	{
		this.mainWindow.redo();
		this.notifyAndUpdate();
	}
	
	void undoAll()
	{
		this.mainWindow.undoAll();
		this.notifyAndUpdate();
	}
	
	void redoAll()
	{
		this.mainWindow.redoAll();
		this.notifyAndUpdate();
	}
	
	/**
	 * Notifies all listeners that image changed and updates enabled/disabled status of every
	 * control element in main window (core's and modules' control elements).
	 */
	private void notifyAndUpdate()
	{
		// notify listeners
		for (IImageChangeListener listener: this.listeners)
		{
			listener.onImageChanged();
		}
		
		// update enabled/disabled status of all menu items
		for (int i = 0; i < this.menubar.getMenuCount(); ++i)
		{
			JMenu menu = this.menubar.getMenu(i);
			
			for (int j = 0; j < menu.getItemCount(); ++j)
			{
				JMenuItem item = menu.getItem(j);
				Action action = item.getAction();
				action.setEnabled(action.isEnabled());
			}
		}
		
		//System.out.println("Component count in toolbar: "+ this.toolbar.getComponents().length);
		
		// update enabled/disabled status of all toolbar buttons
		for (Component c: this.toolbar.getComponents())
		{
			if (c instanceof JButton)
			{
				JButton button = (JButton) c;
				Action action = button.getAction();
				action.setEnabled(action.isEnabled());
			}
		}
	}
	
	@Override
	public RasterImage getActiveImage()
	{
		return this.mainWindowModel.getUndoRedoManager().getActiveImage();
	}
	
	@Override
	public void setActiveImage(RasterImage image, int ownership) throws ImageSizeConstraintViolationException
	{
		assert image != null;
		assert ownership == ObjectOwnership.OWNERSHIP_CALLEE || ownership == ObjectOwnership.OWNERSHIP_CALLER;
		
		
		if (ownership == ObjectOwnership.OWNERSHIP_CALLER)
		{
			image = (RasterImage) image.clone();
		}
		
		if (image.getWidth() < ImageSizeConstraints.MIN_IMAGE_WIDTH || image.getHeight() < ImageSizeConstraints.MIN_IMAGE_HEIGHT)
		{
			throw new ImageSizeConstraintViolationException("Image too small");
		}
		
		if (image.getWidth() > ImageSizeConstraints.MAX_IMAGE_WIDTH || image.getHeight() > ImageSizeConstraints.MAX_IMAGE_HEIGHT)
		{
			throw new ImageSizeConstraintViolationException("Image too large");
		}
		
		this.mainWindow.setActiveImage(image);
		
		this.notifyAndUpdate();
	}
	
	@Override
	public boolean isImageLoaded()
	{
		return this.mainWindowModel.isImageLoaded();
	}
	
	@Override
	public Rectangle getRectangularSelection()
	{
		Rectangle selection = this.mainWindowModel.getImagePanelModel().getRectangularSelection();
		
		if (selection == null)
		{
			return null;
		}
		else
		{
			return selection;
		}
	}
	
	@Override
	public void registerImageChangeListener(IImageChangeListener listener)
	{
		assert listener != null;
		
		this.listeners.add(listener);
	}
}
