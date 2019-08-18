
package eugenejonas.pixelmaster.modules.binarization.gui;


import eugenejonas.pixelmaster.core.api.domain.*;
import eugenejonas.pixelmaster.core.api.gui.*;
import eugenejonas.pixelmaster.modules.binarization.domain.*;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;


public final class BinarizationWindow extends JFrame
{
	private static final class RgbPanel extends JPanel
	{
		private final BinarizationWindowModel.RgbPanelModel rgbPanelModel;
		
		private JButton button_default;
		private SliderWithSpinner redSliderWithSpinner, greenSliderWithSpinner, blueSliderWithSpinner;
		private JPanel panel = new JPanel(new GridLayout(3, 2));
		
		// listeners of the three sliders with spinners
		ChangeListener[] listeners = new ChangeListener[3];		// 0 - r; 1 - g; 2 - b
		
		
		private RgbPanel(BinarizationWindowModel.RgbPanelModel model)
		{
			this.rgbPanelModel = model;
			this.redSliderWithSpinner = new SliderWithSpinner(model.getRedSliderWithSpinnerModel(), SwingConstants.HORIZONTAL, false);
			this.greenSliderWithSpinner = new SliderWithSpinner(model.getGreenSliderWithSpinnerModel(), SwingConstants.HORIZONTAL, false);
			this.blueSliderWithSpinner = new SliderWithSpinner(model.getBlueSliderWithSpinnerModel(), SwingConstants.HORIZONTAL, true);
			
			this.listeners[0] = new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent event)
				{
					int green = 100 - model.getRed() - model.getBlue();
					
					if (green < 0)
					{
						green = 0;
						
						RgbPanel.this.blueSliderWithSpinner.removeChangeListener(RgbPanel.this.listeners[2]);
						RgbPanel.this.blueSliderWithSpinner.setValue(100 - model.getRed());
						RgbPanel.this.blueSliderWithSpinner.addChangeListener(RgbPanel.this.listeners[2]);
					}
					
					RgbPanel.this.greenSliderWithSpinner.removeChangeListener(RgbPanel.this.listeners[1]);
					RgbPanel.this.greenSliderWithSpinner.setValue(green);
					RgbPanel.this.greenSliderWithSpinner.addChangeListener(RgbPanel.this.listeners[1]);
				}
			};
			
			this.listeners[1] = new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent event)
				{
					int blue = 100 - model.getRed() - model.getGreen();
					
					if (blue < 0)
					{
						blue = 0;
						
						RgbPanel.this.redSliderWithSpinner.removeChangeListener(RgbPanel.this.listeners[0]);
						RgbPanel.this.redSliderWithSpinner.setValue(100 - model.getGreen());
						RgbPanel.this.redSliderWithSpinner.addChangeListener(RgbPanel.this.listeners[0]);
					}
					
					RgbPanel.this.blueSliderWithSpinner.removeChangeListener(RgbPanel.this.listeners[2]);
					RgbPanel.this.blueSliderWithSpinner.setValue(blue);
					RgbPanel.this.blueSliderWithSpinner.addChangeListener(RgbPanel.this.listeners[2]);
				}
			};
			
			this.listeners[2] = new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent event)
				{
					int green = 100 - model.getRed() - model.getBlue();
					
					if (green < 0)
					{
						green = 0;
						
						RgbPanel.this.redSliderWithSpinner.removeChangeListener(RgbPanel.this.listeners[0]);
						RgbPanel.this.redSliderWithSpinner.setValue(100 - model.getBlue());
						RgbPanel.this.redSliderWithSpinner.addChangeListener(RgbPanel.this.listeners[0]);
					}
					
					RgbPanel.this.greenSliderWithSpinner.removeChangeListener(RgbPanel.this.listeners[1]);
					RgbPanel.this.greenSliderWithSpinner.setValue(green);
					RgbPanel.this.greenSliderWithSpinner.addChangeListener(RgbPanel.this.listeners[1]);
				}
			};
			
			this.redSliderWithSpinner.addChangeListener(this.listeners[0]);
			this.greenSliderWithSpinner.addChangeListener(this.listeners[1]);
			this.blueSliderWithSpinner.addChangeListener(this.listeners[2]);
			
			this.panel.add(new JLabel("red:"));
			this.panel.add(this.redSliderWithSpinner);
			this.panel.add(new JLabel("green:"));
			this.panel.add(this.greenSliderWithSpinner);
			this.panel.add(new JLabel("blue:"));
			this.panel.add(this.blueSliderWithSpinner);
			
			final class Action_reset_to_default_rgb_coefficients extends AbstractAction
			{
				private Action_reset_to_default_rgb_coefficients()
				{
					//nothing
				}
				
				@Override
				public void actionPerformed(ActionEvent event)
				{
					RgbPanel.this.setDefaults();
				}
				
				@Override
				public Object getValue(String key)
				{
					if (key.equals(Action.NAME))
					{
						return "Default";
					}
					else
					{
						return super.getValue(key);
					}
				}
			}
			
			this.button_default = new JButton();
			this.button_default.setAction(new Action_reset_to_default_rgb_coefficients());
			
			this.add(this.button_default);
			this.add(this.panel);
		}
		
		private void setDefaults()
		{
			this.redSliderWithSpinner.removeChangeListener(this.listeners[0]);
			this.greenSliderWithSpinner.removeChangeListener(this.listeners[1]);
			this.blueSliderWithSpinner.removeChangeListener(this.listeners[2]);
			
			this.redSliderWithSpinner.setValue(RGB.DEFAULT_RED_COEFF);
			this.greenSliderWithSpinner.setValue(RGB.DEFAULT_GREEN_COEFF);
			this.blueSliderWithSpinner.setValue(RGB.DEFAULT_BLUE_COEFF);
			
			this.redSliderWithSpinner.addChangeListener(this.listeners[0]);
			this.greenSliderWithSpinner.addChangeListener(this.listeners[1]);
			this.blueSliderWithSpinner.addChangeListener(this.listeners[2]);
		}
	}
	
	
	private BinarizationWindowModel binarizationWindowModel;
	
	private Action action_copy_to_main_window;

	private JPanel rightPanel, bernsenTopPanel, niblackTopPanel, buttonPanel,
			globalThresholdingPanel, bernsenPanel, niblackPanel,
			globalBinarizationPanel, globalGrayScalingPanel;
	private JTabbedPane tabbedPane;
	private ImagePanel imagePanel;
	private JLabel niblackRadiusLabel, niblackCoeffLabel, bernsenRadiusLabel, bernsenThresholdLabel;
	private JSpinner bernsenRadiusSpinner, bernsenThresholdSpinner,
			niblackRadiusSpinner, niblackCoeffSpinner;
	private JButton
			button_Niblack,
			button_Bernsen,
			button_global_binarization,
			button_global_gray_scaling,
			button_Otsu,
			button_orig,
			button_copy;
	private SliderWithSpinner globalThresholdSliderWithSpinner;
	private RgbPanel rgbPanel;
	
	
	public BinarizationWindow(BinarizationWindowModel binarizationWindowModel, Action action_copy_to_main_window)
	{
		super("Binarization");
		
		assert binarizationWindowModel != null && action_copy_to_main_window != null;
		
		
		this.action_copy_to_main_window = action_copy_to_main_window;
		this.binarizationWindowModel = binarizationWindowModel;
		
		this.setVisible(false);
		this.setSize(700, 600);
		this.setMinimumSize(new Dimension(500, 450));
		this.setLocation(400, 100);
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
		
		this.imagePanel = new ImagePanel(this.binarizationWindowModel.getImage());
		this.add(this.imagePanel, BorderLayout.CENTER);
		
		this.rightPanel = new JPanel(new BorderLayout());
		this.rightPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.add(this.rightPanel, BorderLayout.EAST);
		
		this.rgbPanel = new RgbPanel(this.binarizationWindowModel.getRgbPanelModel());
		
		this.rightPanel.add(this.rgbPanel, BorderLayout.NORTH);
		
		this.globalThresholdingPanel = new JPanel();
		this.globalThresholdingPanel.setLayout(new BoxLayout(this.globalThresholdingPanel, BoxLayout.PAGE_AXIS));
		this.globalThresholdingPanel.setBorder(BorderFactory.createTitledBorder("threshold"));
		this.globalThresholdingPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		
		this.bernsenPanel = new JPanel(new BorderLayout());
		this.niblackPanel = new JPanel(new BorderLayout());
		this.globalBinarizationPanel = new JPanel();
		this.globalGrayScalingPanel = new JPanel();
		
		this.bernsenTopPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		this.niblackTopPanel = new JPanel(new GridLayout(3, 2, 5, 5));
		
		this.buttonPanel = new JPanel();
		this.buttonPanel.setBorder(BorderFactory.createEtchedBorder());
		this.add(this.buttonPanel, BorderLayout.SOUTH);
		
		this.globalBinarizationPanel.add(this.globalThresholdingPanel);
		
		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.addTab("Bernsen", this.bernsenPanel);
		this.tabbedPane.addTab("Niblack", this.niblackPanel);
		this.tabbedPane.addTab("Global binarization", this.globalBinarizationPanel);
		this.tabbedPane.addTab("Global gray-scaling", this.globalGrayScalingPanel);
		this.rightPanel.add(this.tabbedPane, BorderLayout.SOUTH);
	}
	
	protected void createComponents()
	{
		this.button_Niblack = new JButton();
		this.button_Bernsen = new JButton();
		this.button_global_binarization = new JButton();
		this.button_global_gray_scaling = new JButton();
		this.button_Otsu = new JButton();
		this.button_orig = new JButton();
		this.button_copy = new JButton();
		
		
		final class Action_apply_Niblack_algorithm extends AbstractAction
		{
			private Action_apply_Niblack_algorithm()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				BinarizationWindow.this.binarizationWindowModel.applyNiblackBinarization();
				BinarizationWindow.this.imagePanel.setImage(BinarizationWindow.this.binarizationWindowModel.getImage());
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
		
		final class Action_apply_Bernsen_algorithm extends AbstractAction
		{
			private Action_apply_Bernsen_algorithm()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				BinarizationWindow.this.binarizationWindowModel.applyBernsenBinarization();
				BinarizationWindow.this.imagePanel.setImage(BinarizationWindow.this.binarizationWindowModel.getImage());
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
		
		final class Action_apply_global_binarization extends AbstractAction
		{
			private Action_apply_global_binarization()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				BinarizationWindow.this.binarizationWindowModel.applyGlobalBinarization();
				BinarizationWindow.this.imagePanel.setImage(BinarizationWindow.this.binarizationWindowModel.getImage());
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
		
		final class Action_apply_global_gray_scaling extends AbstractAction
		{
			private Action_apply_global_gray_scaling()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				BinarizationWindow.this.binarizationWindowModel.applyGlobalGrayScaling();
				BinarizationWindow.this.imagePanel.setImage(BinarizationWindow.this.binarizationWindowModel.getImage());
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
		
		final class Action_calculate_threshold_using_Otsu_algorithm extends AbstractAction
		{
			private Action_calculate_threshold_using_Otsu_algorithm()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				BinarizationWindow.this.globalThresholdSliderWithSpinner.setValue(
					Binarization.performOtsuThresholding(
						BinarizationWindow.this.binarizationWindowModel.getImage(),
						BinarizationWindow.this.binarizationWindowModel.getRgbPanelModel().getRed(),
						BinarizationWindow.this.binarizationWindowModel.getRgbPanelModel().getGreen(),
						BinarizationWindow.this.binarizationWindowModel.getRgbPanelModel().getBlue()
					)
				);
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "Otsu";
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
				BinarizationWindow.this.binarizationWindowModel.loadOriginalImage();
				BinarizationWindow.this.imagePanel.setImage(BinarizationWindow.this.binarizationWindowModel.getImage());
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
		
		
		this.button_Niblack.setAction(new Action_apply_Niblack_algorithm());
		this.button_Bernsen.setAction(new Action_apply_Bernsen_algorithm());
		this.button_global_binarization.setAction(new Action_apply_global_binarization());
		this.button_global_gray_scaling.setAction(new Action_apply_global_gray_scaling());
		this.button_Otsu.setAction(new Action_calculate_threshold_using_Otsu_algorithm());
		this.button_orig.setAction(new Action_load_orig_image());
		this.button_copy.setAction(this.action_copy_to_main_window);
		
		this.niblackRadiusLabel = new JLabel("radius:");
		this.niblackCoeffLabel = new JLabel("coeff:");
		this.bernsenRadiusLabel = new JLabel("radius:");
		this.bernsenThresholdLabel = new JLabel("threshold:");
		
		this.globalThresholdSliderWithSpinner = new SliderWithSpinner(
			this.binarizationWindowModel.getGlobalThresholdSliderWithSpinnerModel(),
			SwingConstants.VERTICAL,
			true
		);
		
		JFormattedTextField textField;
		DecimalFormat decimalFormat = new DecimalFormat("##0");
		NumberFormatter formatter = new NumberFormatter(decimalFormat);
		
		this.bernsenRadiusSpinner = new JSpinner(this.binarizationWindowModel.getBernsenRadiusSpinnerModel());
		this.bernsenRadiusLabel.setLabelFor(this.bernsenRadiusSpinner);
		textField = ((JSpinner.DefaultEditor) this.bernsenRadiusSpinner.getEditor()).getTextField();
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
		
		Comparable min = ((SpinnerNumberModel) this.bernsenRadiusSpinner.getModel()).getMinimum();
		Comparable max = ((SpinnerNumberModel) this.bernsenRadiusSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
		
		this.bernsenThresholdSpinner = new JSpinner(this.binarizationWindowModel.getBernsenThresholdSpinnerModel());
		this.bernsenThresholdLabel.setLabelFor(this.bernsenThresholdSpinner);
		textField = ((JSpinner.DefaultEditor) this.bernsenThresholdSpinner.getEditor()).getTextField();
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
		
		min = ((SpinnerNumberModel) this.bernsenThresholdSpinner.getModel()).getMinimum();
		max = ((SpinnerNumberModel) this.bernsenThresholdSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
		
		this.niblackRadiusSpinner = new JSpinner(this.binarizationWindowModel.getNiblackRadiusSpinnerModel());
		this.niblackRadiusLabel.setLabelFor(this.niblackRadiusSpinner);
		textField = ((JSpinner.DefaultEditor) this.niblackRadiusSpinner.getEditor()).getTextField();
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
		
		min = ((SpinnerNumberModel) this.niblackRadiusSpinner.getModel()).getMinimum();
		max = ((SpinnerNumberModel) this.niblackRadiusSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
		
		this.niblackCoeffSpinner = new JSpinner(this.binarizationWindowModel.getNiblackCoeffSpinnerModel());
		this.niblackCoeffLabel.setLabelFor(this.niblackCoeffSpinner);
		textField = ((JSpinner.DefaultEditor) this.niblackCoeffSpinner.getEditor()).getTextField();
		textField.setColumns(5);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		decimalFormat = new DecimalFormat("##0.00");
		decimalFormat.setMinimumFractionDigits(2);
		decimalFormat.setMaximumFractionDigits(2);
		decimalFormat.setMaximumIntegerDigits(3);
		decimalFormat.setMinimumIntegerDigits(1);
		decimalFormat.setParseBigDecimal(false);
		decimalFormat.setParseIntegerOnly(false);
		formatter = new NumberFormatter(decimalFormat);
		formatter.setValueClass(Double.class);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setDefaultFormatter(formatter);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setDisplayFormatter(formatter);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setEditFormatter(formatter);
		((DefaultFormatterFactory) textField.getFormatterFactory()).setNullFormatter(formatter);
		formatter.setAllowsInvalid(false);
		
		min = ((SpinnerNumberModel) this.niblackCoeffSpinner.getModel()).getMinimum();
		max = ((SpinnerNumberModel) this.niblackCoeffSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
	}
	
	protected void addComponents()
	{
		this.globalThresholdingPanel.add(this.globalThresholdSliderWithSpinner);
		this.globalThresholdingPanel.add(this.button_Otsu);
		
		this.buttonPanel.add(this.button_copy);
		this.buttonPanel.add(this.button_orig);
		
		this.bernsenPanel.add(this.bernsenTopPanel, BorderLayout.NORTH);
		this.niblackPanel.add(this.niblackTopPanel, BorderLayout.NORTH);
		
		this.bernsenTopPanel.add(this.bernsenRadiusLabel, BorderLayout.SOUTH);
		this.bernsenTopPanel.add(this.bernsenRadiusSpinner);
		this.bernsenTopPanel.add(this.bernsenThresholdLabel);
		this.bernsenTopPanel.add(this.bernsenThresholdSpinner);
		this.bernsenTopPanel.add(this.button_Bernsen);
		
		this.niblackTopPanel.add(this.niblackRadiusLabel);
		this.niblackTopPanel.add(this.niblackRadiusSpinner);
		this.niblackTopPanel.add(this.niblackCoeffLabel);
		this.niblackTopPanel.add(this.niblackCoeffSpinner);
		this.niblackTopPanel.add(this.button_Niblack);
		
		this.globalBinarizationPanel.add(this.button_global_binarization);
		this.globalGrayScalingPanel.add(this.button_global_gray_scaling);
	}
}
