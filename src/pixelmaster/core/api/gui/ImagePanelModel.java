
package pixelmaster.core.api.gui;


import java.awt.*;


/**
 * This class represents model for {@link ImagePanel}.
 * Model's state is defined by currently selected rectangular area
 * (note that displayed image is not included in model, it needs to be handled separately).
 */
public final class ImagePanelModel
{
	private Rectangle rectangularSelection = null;
	
	
	/**
	 * Constructs new model with no rectangular area selected.
	 */
	public ImagePanelModel()
	{
		//nothing
	}

	/**
	 * Returns curretly selected rectangular area.
	 * 
	 * @return Selected area, or null if no rectangular area is selected.
	 * 		Ownership: {@link pixelmaster.core.api.domain.ObjectOwnership#OWNERSHIP_CALLER}.
	 */
	public Rectangle getRectangularSelection()
	{
		if (this.rectangularSelection == null)
		{
			return null;
		}
		else
		{
			return new Rectangle(this.rectangularSelection);
		}
	}

	void setRectangularSelection(Rectangle selection)
	{
		this.rectangularSelection = selection;
	}
}
