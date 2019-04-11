
package pixelmaster.core.internal.gui;


import pixelmaster.core.api.domain.*;
import pixelmaster.core.api.gui.*;

import java.util.*;


public final class MainWindowModel
{
	/**
	 * Manages undo/redo functionality.
	 */
	public final class UndoRedoManager
	{
		private RasterImage currentImage = null;
		private Stack <RasterImage>
				undoStack = new Stack <RasterImage> (),
				redoStack = new Stack <RasterImage> ();
		
		
		public RasterImage getActiveImage()
		{
			return this.currentImage;
		}
		
		void setActiveImage(RasterImage image)
		{
			assert image != null;
			assert image.getWidth() >= ImageSizeConstraints.MIN_IMAGE_WIDTH && image.getHeight() >= ImageSizeConstraints.MIN_IMAGE_HEIGHT;
			assert image.getWidth() <= ImageSizeConstraints.MAX_IMAGE_WIDTH && image.getHeight() <= ImageSizeConstraints.MAX_IMAGE_HEIGHT;

			
			this.redoStack.clear();
			
			if (this.currentImage != null)
			{
				this.undoStack.push(this.currentImage);
			}
			
			this.currentImage = image;
		}
		
		void clear()
		{
			this.undoStack.clear();
			this.redoStack.clear();
			this.currentImage = null;
		}
		
		public boolean canUndo()
		{
			return !this.undoStack.empty();
		}
		
		public boolean canRedo()
		{
			return !this.redoStack.empty();
		}
		
		void undo()
		{
			assert this.canUndo();
						
			this.redoStack.push(this.currentImage);
			this.currentImage = this.undoStack.pop();
		}
		
		void redo()
		{
			assert this.canRedo();
						
			this.undoStack.push(this.currentImage);
			this.currentImage = this.redoStack.pop();
		}
		
		void undoAll()
		{
			assert this.canUndo();
			
			
			this.redoStack.push(this.currentImage);
			
			while (this.undoStack.size() > 1)
			{
				this.redoStack.push(this.undoStack.pop());
			}
			
			this.currentImage = this.undoStack.pop();
		}
		
		void redoAll()
		{
			assert this.canRedo();
			
			
			this.undoStack.push(this.currentImage);
			
			while (this.redoStack.size() > 1)
			{
				this.undoStack.push(this.redoStack.pop());
			}
			
			this.currentImage = this.redoStack.pop();
		}
	}
	
	
	private UndoRedoManager undoRedoManager = new UndoRedoManager();
	private ImagePanelModel imagePanelModel = new ImagePanelModel();
	
	
	public MainWindowModel()
	{
		//nothing
	}
	
	public boolean isImageLoaded()
	{
		return this.undoRedoManager.getActiveImage() != null;
	}
	
	public UndoRedoManager getUndoRedoManager()
	{
		return this.undoRedoManager;
	}
	
	public ImagePanelModel getImagePanelModel()
	{
		return this.imagePanelModel;
	}
}
