
package eugenejonas.pixelmaster.core.api.gui;


/**
 * This class represents model for {@link RadioButtonPanel}.
 * Model's state is defined by number of radio buttons and currently selected radio button.
 */
public class RadioButtonPanelModel
{
	private int count, selectedIndex;
	
	
	/**
	 * Constructs new model with <code>count</code> radio buttons.
	 *
	 * @param count Number of radio buttons.
	 * @param selectedIndex Index of the selected radio button.
	 */
	public RadioButtonPanelModel(int count, int selectedIndex)
	{
		this.count = count;
		this.selectedIndex = selectedIndex;
		
		assert this.invariant();
	}

	private boolean invariant()
	{
		assert this.count > this.selectedIndex && this.selectedIndex >= 0;
		return true;
	}
	
	/**
	 * Returns number of radio buttons.
	 */
	public int getNumberOfButtons()
	{
		return this.count;
	}
	
	/**
	 * Returns index of currently selected radio button.
	 */
	public int getSelectedIndex()
	{
		return this.selectedIndex;
	}
	
	void setSelectedIndex(int selectedIndex)
	{
		this.selectedIndex = selectedIndex;
		
		assert this.invariant();
	}
}
