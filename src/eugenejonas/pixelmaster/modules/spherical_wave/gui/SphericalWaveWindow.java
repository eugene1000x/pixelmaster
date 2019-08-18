
package eugenejonas.pixelmaster.modules.spherical_wave.gui;


import eugenejonas.pixelmaster.core.api.gui.*;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.text.*;


public final class SphericalWaveWindow extends JFrame
{
	private final class ActionListenerImpl implements ActionListener
	{
		private ActionListenerImpl()
		{
			//nothing
		}
		
		@Override
		public void actionPerformed(ActionEvent event)
		{
			if (event.getActionCommand().equals("Save as"))
			{
				GraphOpenSaveDialogs.saveGraph(null, SphericalWaveWindow.this.sphericalWaveWindowModel.getGraph());
			}
			else
			{
				assert false;
			}
		}
	}
	
	
	private SphericalWaveWindowModel sphericalWaveWindowModel;
	
	private ImagePanel imagePanel;
	private JLabel sizeLabel, coeffLabel, distanceLabel, varianceLabel, lengthLabel, modeLabel;
	private JPanel rightPanel, rightTopPanel, buttonPanel, paramPanel;
	private MenuBar menubar;
	private Menu menu_graph;
	private MenuItem menuItem_save_as;
	private JButton
		button_sva,
		button_primary_optimization,
		button_connect_edges,
		button_edge_optimization,
		button_cut_tails;
	private JSpinner
		sizeSpinner,
		coeffSpinner,
		distanceSpinner,
		varianceSpinner,
		lengthSpinner;
	private JComboBox modeCombobox;
	
	
	public SphericalWaveWindow(SphericalWaveWindowModel sphericalWaveWindowModel)
	{
		super("SPW");
		
		assert sphericalWaveWindowModel != null && sphericalWaveWindowModel.getImage().isBinary();
		
		
		this.sphericalWaveWindowModel = sphericalWaveWindowModel;
		
		this.setSize(600, 400);
		this.setMinimumSize(new Dimension(400, 250));
		this.setLocation(400, 300);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
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
		
		this.imagePanel = new ImagePanel(this.sphericalWaveWindowModel.getOrigImage());
		this.rightPanel = new JPanel(new BorderLayout());
		this.add(this.imagePanel, BorderLayout.CENTER);
		this.add(this.rightPanel, BorderLayout.EAST);
		
		this.rightTopPanel = new JPanel(new BorderLayout());
		this.rightTopPanel.setLayout(new BoxLayout(this.rightTopPanel, BoxLayout.LINE_AXIS));
		this.rightPanel.add(this.rightTopPanel, BorderLayout.NORTH);
		
		this.buttonPanel = new JPanel(new GridLayout(6, 1, 5, 5));
		this.buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.rightTopPanel.add(this.buttonPanel);
		
		this.paramPanel = new JPanel(new GridLayout(6, 2, 5, 5));
		this.paramPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.rightTopPanel.add(this.paramPanel, BorderLayout.EAST);
	}
	
	protected void createComponents()
	{
		this.menubar = new MenuBar();
		this.menu_graph = new Menu("Graph");
		this.menuItem_save_as = new MenuItem("Save as");
		this.menu_graph.add(this.menuItem_save_as);
		this.menuItem_save_as.addActionListener(new ActionListenerImpl());
		this.menubar.add(this.menu_graph);
		
		this.setMenuBar(this.menubar);
		
		final class Action_apply_sva extends AbstractAction
		{
			private Action_apply_sva()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				SphericalWaveWindow.this.sphericalWaveWindowModel.applySva();
				SphericalWaveWindow.this.imagePanel.setImage(SphericalWaveWindow.this.sphericalWaveWindowModel.getImage());
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "SVA";
				}
				else
				{
					return super.getValue(key);
				}
			}
		}
		
		final class Action_perform_primary_optimization extends AbstractAction
		{
			private Action_perform_primary_optimization()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				SphericalWaveWindow.this.sphericalWaveWindowModel.applyPrimaryOptimization();
				SphericalWaveWindow.this.imagePanel.setImage(SphericalWaveWindow.this.sphericalWaveWindowModel.getImage());
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "Primary optimization";
				}
				else
				{
					return super.getValue(key);
				}
			}
		}
		
		final class Action_connect_edges extends AbstractAction
		{
			private Action_connect_edges()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				SphericalWaveWindow.this.sphericalWaveWindowModel.connectEdges();
				SphericalWaveWindow.this.imagePanel.setImage(SphericalWaveWindow.this.sphericalWaveWindowModel.getImage());
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "Connect edges";
				}
				else
				{
					return super.getValue(key);
				}
			}
		}
		
		final class Action_optimize_edges extends AbstractAction
		{
			private Action_optimize_edges()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				SphericalWaveWindow.this.sphericalWaveWindowModel.applyEdgeOptimization();
				SphericalWaveWindow.this.imagePanel.setImage(SphericalWaveWindow.this.sphericalWaveWindowModel.getImage());
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "Edge optimization";
				}
				else
				{
					return super.getValue(key);
				}
			}
		}
		
		final class Action_cut_tails extends AbstractAction
		{
			private Action_cut_tails()
			{
				//nothing
			}
			
			@Override
			public void actionPerformed(ActionEvent event)
			{
				SphericalWaveWindow.this.sphericalWaveWindowModel.cutTails();
				SphericalWaveWindow.this.imagePanel.setImage(SphericalWaveWindow.this.sphericalWaveWindowModel.getImage());
			}
			
			@Override
			public Object getValue(String key)
			{
				if (key.equals(Action.NAME))
				{
					return "Cut tails";
				}
				else
				{
					return super.getValue(key);
				}
			}
		}
		
		this.button_sva = new JButton("SVA");
		this.button_sva.setAction(new Action_apply_sva());
		
		this.button_primary_optimization = new JButton("Primary opt.");
		this.button_primary_optimization.setAction(new Action_perform_primary_optimization());
		
		this.button_connect_edges = new JButton("Connect edges");
		this.button_connect_edges.setAction(new Action_connect_edges());
		
		this.button_edge_optimization = new JButton("Edge opt.");
		this.button_edge_optimization.setAction(new Action_optimize_edges());
		
		this.button_cut_tails = new JButton("Cut tails");
		this.button_cut_tails.setAction(new Action_cut_tails());
		
		this.sizeLabel = new JLabel("size:");
		this.coeffLabel = new JLabel("coeff:");
		this.distanceLabel = new JLabel("distance:");
		this.varianceLabel = new JLabel("variance:");
		this.lengthLabel = new JLabel("length:");
		this.modeLabel = new JLabel("connectivity mode:");
		
		this.modeCombobox = new JComboBox(this.sphericalWaveWindowModel.getModeComboboxModel());
		
		this.sizeSpinner = new JSpinner(this.sphericalWaveWindowModel.getSizeSpinnerModel());
		JFormattedTextField textField = ((JSpinner.DefaultEditor) this.sizeSpinner.getEditor()).getTextField();
		textField.setColumns(3);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setEditable(true);
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
		
		Comparable min = ((SpinnerNumberModel) this.sizeSpinner.getModel()).getMinimum();
		Comparable max = ((SpinnerNumberModel) this.sizeSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
		
		this.coeffSpinner = new JSpinner(this.sphericalWaveWindowModel.getCoeffSpinnerModel());
		textField = ((JSpinner.DefaultEditor) this.coeffSpinner.getEditor()).getTextField();
		textField.setColumns(3);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setEditable(true);
		decimalFormat = new DecimalFormat("0.000");
		decimalFormat.setMinimumFractionDigits(3);
		decimalFormat.setMaximumFractionDigits(3);
		decimalFormat.setMaximumIntegerDigits(5);
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
		
		min = ((SpinnerNumberModel) this.coeffSpinner.getModel()).getMinimum();
		max = ((SpinnerNumberModel) this.coeffSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
		
		this.distanceSpinner = new JSpinner(this.sphericalWaveWindowModel.getDistanceSpinnerModel());
		textField = ((JSpinner.DefaultEditor) this.distanceSpinner.getEditor()).getTextField();
		textField.setColumns(3);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setEditable(true);
		decimalFormat = new DecimalFormat("000.000");
		decimalFormat.setMinimumFractionDigits(3);
		decimalFormat.setMaximumFractionDigits(3);
		decimalFormat.setMaximumIntegerDigits(5);
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
		
		min = ((SpinnerNumberModel) this.distanceSpinner.getModel()).getMinimum();
		max = ((SpinnerNumberModel) this.distanceSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
		
		this.varianceSpinner = new JSpinner(this.sphericalWaveWindowModel.getVarianceSpinnerModel());
		textField = ((JSpinner.DefaultEditor) this.varianceSpinner.getEditor()).getTextField();
		textField.setColumns(3);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setEditable(true);
		decimalFormat = new DecimalFormat("000.000");
		decimalFormat.setMinimumFractionDigits(3);
		decimalFormat.setMaximumFractionDigits(3);
		decimalFormat.setMaximumIntegerDigits(5);
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
		
		min = ((SpinnerNumberModel) this.varianceSpinner.getModel()).getMinimum();
		max = ((SpinnerNumberModel) this.varianceSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
		
		this.lengthSpinner = new JSpinner(this.sphericalWaveWindowModel.getLengthSpinnerModel());
		textField = ((JSpinner.DefaultEditor) this.lengthSpinner.getEditor()).getTextField();
		textField.setColumns(3);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setEditable(true);
		decimalFormat = new DecimalFormat("000.000");
		decimalFormat.setMinimumFractionDigits(3);
		decimalFormat.setMaximumFractionDigits(3);
		decimalFormat.setMaximumIntegerDigits(5);
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
		
		min = ((SpinnerNumberModel) this.lengthSpinner.getModel()).getMinimum();
		max = ((SpinnerNumberModel) this.lengthSpinner.getModel()).getMaximum();
		formatter.setMinimum(min);
		formatter.setMaximum(max);
		
		this.sizeLabel.setLabelFor(this.sizeSpinner);
		this.coeffLabel.setLabelFor(this.coeffSpinner);
		this.distanceLabel.setLabelFor(this.distanceSpinner);
		this.varianceLabel.setLabelFor(this.varianceSpinner);
		this.lengthLabel.setLabelFor(this.lengthSpinner);
		this.modeLabel.setLabelFor(this.modeCombobox);
	}
	
	protected void addComponents()
	{
		this.buttonPanel.add(this.button_sva);
		this.buttonPanel.add(this.button_primary_optimization);
		this.buttonPanel.add(this.button_connect_edges);
		this.buttonPanel.add(this.button_edge_optimization);
		this.buttonPanel.add(this.button_cut_tails);
		this.paramPanel.add(this.sizeLabel);
		this.paramPanel.add(this.sizeSpinner);
		this.paramPanel.add(this.coeffLabel);
		this.paramPanel.add(this.coeffSpinner);
		this.paramPanel.add(this.distanceLabel);
		this.paramPanel.add(this.distanceSpinner);
		this.paramPanel.add(this.varianceLabel);
		this.paramPanel.add(this.varianceSpinner);
		this.paramPanel.add(this.lengthLabel);
		this.paramPanel.add(this.lengthSpinner);
		this.paramPanel.add(this.modeLabel);
		this.paramPanel.add(this.modeCombobox);
	}
}
