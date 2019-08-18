
package eugenejonas.pixelmaster.core.api.gui;


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


/**
 * This class represents panel with radio buttons where exactly one of them is selected.
 */
public class RadioButtonPanel extends JPanel
{
	private final class ActionListenerImpl implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event)
		{
			// updating index of the selected button
			
			int i = 0;
			Enumeration buttons = RadioButtonPanel.this.buttonGroup.getElements();
			
			while (buttons.hasMoreElements())
			{
				JRadioButton radioButton = (JRadioButton) buttons.nextElement();
				
				if (radioButton.isSelected())
				{
					RadioButtonPanel.this.radioButtonPanelModel.setSelectedIndex(i);
				}
				
				++i;
			}

			assert RadioButtonPanel.this.radioButtonPanelModel.getNumberOfButtons() == i;
		}
	}
	
	
	private RadioButtonPanelModel radioButtonPanelModel;
	private ButtonGroup buttonGroup;
	private ActionListenerImpl actionListener = new ActionListenerImpl();

	
	/**
	 * Creates new radio button panel which uses supplied model.
	 *
	 * @param labels Labels near radio buttons.
	 */
	public RadioButtonPanel(RadioButtonPanelModel radioButtonPanelModel, String... labels)
	{
		super(new GridLayout(0, 1));
		
		assert labels.length == radioButtonPanelModel.getNumberOfButtons();
		assert radioButtonPanelModel.getSelectedIndex() >= 0 && radioButtonPanelModel.getSelectedIndex() < labels.length;

		
		this.radioButtonPanelModel = radioButtonPanelModel;
		this.buttonGroup = new ButtonGroup();

		for (int i = 0; i < labels.length; ++i)
		{
			JRadioButton radioButton = new JRadioButton(labels[i], radioButtonPanelModel.getSelectedIndex() == i);
			radioButton.addActionListener(this.actionListener);
			this.buttonGroup.add(radioButton);
			this.add(radioButton);
		}
	}
}
