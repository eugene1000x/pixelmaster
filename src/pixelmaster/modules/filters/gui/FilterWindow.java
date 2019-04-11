
package pixelmaster.modules.filters.gui;


import pixelmaster.core.api.gui.*;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;


public final class FilterWindow extends JFrame
{
	private FilterWindowModel filterWindowModel;
	
	private Action action_copy_image_to_main_window;
	
	private ImagePanel imagePanel;
	private JPanel bottomPanel, rightPanel, rightTopPanel, settingsPanel, filterPanel;
	private JButton
			button_apply,
			button_load_orig_image,
			button_copy_image_to_main_window;
	private JSpinner radiusSpinner, thresholdSpinner;
	private JLabel radiusLabel, thresholdLabel;
	private RadioButtonPanel radioButtonPanel;
	
	
	public FilterWindow(FilterWindowModel filterWindowModel, Action action_copy_image_to_main_window)
	{
		super("Filtering");
		
		assert filterWindowModel != null;
		
		
		this.filterWindowModel = filterWindowModel;
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
		
		this.imagePanel = new ImagePanel(this.filterWindowModel.getImage(), this.filterWindowModel.getImagePanelModel());
		
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
		this.settingsPanel.setLayout(new GridLayout(2, 2, 5, 5));
		this.settingsPanel.setBorder(BorderFactory.createTitledBorder("settings"));
		this.rightTopPanel.add(this.settingsPanel);
		
		this.filterPanel = new JPanel();
		this.filterPanel.setLayout(new BoxLayout(this.filterPanel, BoxLayout.PAGE_AXIS));
		this.filterPanel.setBorder(BorderFactory.createTitledBorder("filter"));
		this.rightTopPanel.add(this.filterPanel);
		
		this.radioButtonPanel = new RadioButtonPanel(
			this.filterWindowModel.getAlgorithmPanelModel(),
			"Median filter",
			"Median bidirectional",
			"Mean filter",
			"Variance filter"
		);
		this.filterPanel.add(this.radioButtonPanel);
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
				FilterWindow.this.filterWindowModel.applyFilter();
				FilterWindow.this.imagePanel.setImage(FilterWindow.this.filterWindowModel.getImage());
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
				FilterWindow.this.filterWindowModel.loadOriginalImage();
				FilterWindow.this.imagePanel.setImage(FilterWindow.this.filterWindowModel.getImage());
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
		
		this.radiusLabel = new JLabel("radius:");
		this.thresholdLabel = new JLabel("threshold:");
		
		this.radiusSpinner = new JSpinner(this.filterWindowModel.getRadiusSpinnerModel());
		this.thresholdSpinner = new JSpinner(this.filterWindowModel.getThresholdSpinnerModel());
		
		JFormattedTextField textField = ((JSpinner.DefaultEditor) this.radiusSpinner.getEditor()).getTextField();
		textField.setColumns(3);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		DecimalFormat decimalFormat = new DecimalFormat("##0");
		decimalFormat.setMaximumIntegerDigits(3);
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
		
		Comparable min = ((SpinnerNumberModel) this.radiusSpinner.getModel()).getMinimum();
		Comparable max = ((SpinnerNumberModel) this.radiusSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
		
		textField = ((JSpinner.DefaultEditor) this.thresholdSpinner.getEditor()).getTextField();
		textField.setColumns(3);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		decimalFormat = new DecimalFormat("##0");
		decimalFormat.setMaximumIntegerDigits(3);
		decimalFormat.setMinimumIntegerDigits(1);
		decimalFormat.setParseBigDecimal(false);
		decimalFormat.setParseIntegerOnly(true);
		formatter = new NumberFormatter(decimalFormat);
		formatter.setValueClass(Integer.class);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setDefaultFormatter(formatter);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setDisplayFormatter(formatter);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setEditFormatter(formatter);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setNullFormatter(formatter);
		formatter.setAllowsInvalid(false);
		
		min = ((SpinnerNumberModel) this.thresholdSpinner.getModel()).getMinimum();
		max = ((SpinnerNumberModel) this.thresholdSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
	}
	
	protected void addComponents()
	{
		this.bottomPanel.add(this.button_copy_image_to_main_window);
		this.bottomPanel.add(this.button_load_orig_image);
		
		this.settingsPanel.add(this.radiusLabel);
		this.settingsPanel.add(this.radiusSpinner);
		this.settingsPanel.add(this.thresholdLabel);
		this.settingsPanel.add(this.thresholdSpinner);
		
		this.filterPanel.add(this.button_apply);
	}
}
