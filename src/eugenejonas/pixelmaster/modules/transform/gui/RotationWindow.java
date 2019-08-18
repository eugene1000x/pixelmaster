
package eugenejonas.pixelmaster.modules.transform.gui;


import eugenejonas.pixelmaster.core.api.gui.ImagePanel;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;


public final class RotationWindow extends JFrame
{
	private RotationWindowModel rotationWindowModel;
	
	private Action action_copy_image_to_main_window;
	
	private ImagePanel imagePanel;
	private JPanel
			bottomPanel,
			rightPanel,
			rightTopPanel,
			settingsPanel,
			rotateByShearPanel
			/*radioButtonPanel*/;
	private JButton
			button_apply,
			button_load_orig_image,
			button_copy_image_to_main_window;
	private JSpinner deg__angleSpinner;
	private JLabel deg__angleLabel;
	
	
	public RotationWindow(RotationWindowModel rotationWindowModel, Action action_copy_image_to_main_window)
	{
		super("Rotation");
		
		assert rotationWindowModel != null;
		
		
		this.rotationWindowModel = rotationWindowModel;
		this.action_copy_image_to_main_window = action_copy_image_to_main_window;
		
		this.setVisible(false);
		this.setSize(500, 400);
		this.setMinimumSize(new Dimension(300, 300));
		this.setLocation(600, 100);
		this.setResizable(true);
		
		this.createAndShowGui();
	}
	
	private void createAndShowGui()
	{
		this.createPanels();
		this.createComponents();
		this.addComponents();
		this.setVisible(true);
	}
	
	protected void createPanels()
	{
		this.setLayout(new BorderLayout());
		
		this.imagePanel = new ImagePanel(this.rotationWindowModel.getImage(), this.rotationWindowModel.getImagePanelModel());
		
		this.bottomPanel = new JPanel();
		this.rightPanel = new JPanel(new BorderLayout());
		this.add(this.imagePanel, BorderLayout.CENTER);
		this.add(this.bottomPanel, BorderLayout.SOUTH);
		this.add(this.rightPanel, BorderLayout.EAST);
		
		this.rightTopPanel = new JPanel();
		this.rightTopPanel.setLayout(new BoxLayout(this.rightTopPanel, BoxLayout.PAGE_AXIS));
		this.rightTopPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.rightPanel.add(this.rightTopPanel, BorderLayout.NORTH);
		
		this.settingsPanel = new JPanel();
		this.settingsPanel.setLayout(new GridLayout(1, 2, 5, 5));
		this.settingsPanel.setBorder(BorderFactory.createTitledBorder("settings"));
		this.rightTopPanel.add(this.settingsPanel);
		
		this.rotateByShearPanel = new JPanel();
		this.rotateByShearPanel.setLayout(new GridLayout(1, 1, 5, 5));
		this.rotateByShearPanel.setBorder(BorderFactory.createTitledBorder("rotate by shear"));
		this.rightTopPanel.add(this.rotateByShearPanel);
	}
	
	private void createComponents()
	{
		this.button_apply = new JButton();
		this.button_load_orig_image = new JButton();
		this.button_copy_image_to_main_window = new JButton();
		
		this.button_apply.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		final class Action_apply extends AbstractAction
		{
			private Action_apply()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				RotationWindow.this.rotationWindowModel.apply();
				RotationWindow.this.imagePanel.setImage(RotationWindow.this.rotationWindowModel.getImage());
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
		
		final class Action_load_orig_image extends AbstractAction
		{
			private Action_load_orig_image()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				RotationWindow.this.rotationWindowModel.loadOriginalImage();
				RotationWindow.this.imagePanel.setImage(RotationWindow.this.rotationWindowModel.getImage());
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "Original image";
				}
				else
				{
					return super.getValue(key);
				}
			}
		}
		
		this.button_apply.setAction(new Action_apply());
		this.button_load_orig_image.setAction(new Action_load_orig_image());
		this.button_copy_image_to_main_window.setAction(this.action_copy_image_to_main_window);
		
		this.deg__angleLabel = new JLabel("angle:");
		this.deg__angleSpinner = new JSpinner(this.rotationWindowModel.deg__getAngleSpinnerModel());
		
		JFormattedTextField textField = ((JSpinner.DefaultEditor) this.deg__angleSpinner.getEditor()).getTextField();
		textField.setColumns(3);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		DecimalFormat decimalFormat = new DecimalFormat("#0");
		decimalFormat.setMaximumIntegerDigits(2);
		decimalFormat.setMinimumIntegerDigits(1);
		decimalFormat.setParseBigDecimal(false);
		decimalFormat.setParseIntegerOnly(true);
		NumberFormatter formatter = new NumberFormatter(decimalFormat);
		formatter.setValueClass(Integer.class);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setDefaultFormatter(formatter);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setDisplayFormatter(formatter);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setEditFormatter(formatter);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setNullFormatter(formatter);
		formatter.setAllowsInvalid(false);
		
		Comparable min = ((SpinnerNumberModel) this.deg__angleSpinner.getModel()).getMinimum();
		Comparable max = ((SpinnerNumberModel) this.deg__angleSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
	}
	
	protected void addComponents()
	{
		this.bottomPanel.add(this.button_copy_image_to_main_window);
		this.bottomPanel.add(this.button_load_orig_image);
		
		this.settingsPanel.add(this.deg__angleLabel);
		this.settingsPanel.add(this.deg__angleSpinner);
		
		this.rotateByShearPanel.add(this.button_apply);
	}
}
