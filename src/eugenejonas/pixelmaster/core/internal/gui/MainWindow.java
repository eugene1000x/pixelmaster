
package eugenejonas.pixelmaster.core.internal.gui;


import eugenejonas.pixelmaster.core.api.domain.*;
import eugenejonas.pixelmaster.core.api.gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public final class MainWindow extends JFrame
{
	private static final String APP_NAME = "PixelMaster";
	
	
	private JPanel mainPanel;
	private JPanel toolbarPanel;
	private ImagePanel imagePanel;
	
	private MainWindowModel mainWindowModel;
	
	
	public MainWindow(MainWindowModel mainWindowModel)
	{
		this.mainWindowModel = mainWindowModel;
		
		this.setTitle(MainWindow.APP_NAME);
		//this.setBackground(Color.darkGray);
		this.setSize(700, 600);
		this.setLocation(200, 200);
		this.setMinimumSize(new Dimension(300, 200));
		
		this.mainPanel = new JPanel(new BorderLayout());
		//this.mainPanel.setBackground(Color.darkGray);
		this.add(this.mainPanel);
		
		this.toolbarPanel = new JPanel(new BorderLayout());
		this.mainPanel.add(this.toolbarPanel, BorderLayout.NORTH);
		
		this.imagePanel = new ImagePanel(
			mainWindowModel.getUndoRedoManager().getActiveImage(),
			mainWindowModel.getImagePanelModel()
		);
		this.mainPanel.add(this.imagePanel, BorderLayout.CENTER);
		this.mainPanel.setComponentZOrder(this.imagePanel, 0);
		
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent event)
			{
				System.exit(0);
			}
		});
		
		this.setVisible(false);
	}
	
	public void undo()
	{
		assert this.mainWindowModel.getUndoRedoManager().canUndo();
				
		this.mainWindowModel.getUndoRedoManager().undo();
		this.imagePanel.setImage(this.mainWindowModel.getUndoRedoManager().getActiveImage());
	}
	
	public void redo()
	{
		assert this.mainWindowModel.getUndoRedoManager().canRedo();
				
		this.mainWindowModel.getUndoRedoManager().redo();
		this.imagePanel.setImage(this.mainWindowModel.getUndoRedoManager().getActiveImage());
	}
	
	public void undoAll()
	{
		assert this.mainWindowModel.getUndoRedoManager().canUndo();
				
		this.mainWindowModel.getUndoRedoManager().undoAll();
		this.imagePanel.setImage(this.mainWindowModel.getUndoRedoManager().getActiveImage());
	}
	
	public void redoAll()
	{
		assert this.mainWindowModel.getUndoRedoManager().canRedo();
				
		this.mainWindowModel.getUndoRedoManager().redoAll();
		this.imagePanel.setImage(this.mainWindowModel.getUndoRedoManager().getActiveImage());
	}
	
	public void onFileOpened(RasterImage image)
	{
		assert image.getWidth() >= ImageSizeConstraints.MIN_IMAGE_WIDTH && image.getHeight() >= ImageSizeConstraints.MIN_IMAGE_HEIGHT;
		assert image.getWidth() <= ImageSizeConstraints.MAX_IMAGE_WIDTH && image.getHeight() <= ImageSizeConstraints.MAX_IMAGE_HEIGHT;
				
		this.mainWindowModel.getUndoRedoManager().clear();
		this.mainWindowModel.getUndoRedoManager().setActiveImage(image);
		this.imagePanel.setImage(this.mainWindowModel.getUndoRedoManager().getActiveImage());
	}
	
	public void onFileClosed()
	{
		assert this.mainWindowModel.getUndoRedoManager().getActiveImage() != null;
				
		this.mainWindowModel.getUndoRedoManager().clear();
		this.imagePanel.setImage(this.mainWindowModel.getUndoRedoManager().getActiveImage());
	}
	
	/**
	 * @param image Currently displayed image.
	 */
	public void setActiveImage(RasterImage image)
	{
		assert image != null;
		assert image.getWidth() >= ImageSizeConstraints.MIN_IMAGE_WIDTH && image.getHeight() >= ImageSizeConstraints.MIN_IMAGE_HEIGHT;
		assert image.getWidth() <= ImageSizeConstraints.MAX_IMAGE_WIDTH && image.getHeight() <= ImageSizeConstraints.MAX_IMAGE_HEIGHT;
				
		this.mainWindowModel.getUndoRedoManager().setActiveImage(image);
		this.imagePanel.setImage(this.mainWindowModel.getUndoRedoManager().getActiveImage());
	}
	
	public void setToolbar(JToolBar toolbar)
	{
		this.toolbarPanel.add(toolbar, BorderLayout.WEST);
	}
}
