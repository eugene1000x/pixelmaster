
package pixelmaster.modules.transform.gui;


import pixelmaster.core.api.domain.*;
import pixelmaster.core.api.gui.*;

import javax.swing.*;


public final class ResizingWindow extends JFrame
{
	private ResizingWindowModel resizingWindowModel;
	
	private Action
			action_apply_nn_resampling,
			action_apply_bilinear_resampling;
	
	private JPanel
			panel,
			currentWidthPanel,
			currentHeightPanel,
			nnResamplingPanel,
			bilinearResamplingPanel,
			newWidthPanel,
			newHeightPanel,
			coeffXPanel,
			coeffYPanel;
	private JTabbedPane tabbedPane;
	private JTextArea currentWidthTextarea, currentHeightTextarea;
	private JLabel
			newWidthLabel,
			newHeightLabel,
			currentWidthLabel,
			currentHeightLabel,
			coeffXLabel,
			coeffYLabel;
	private JSpinner coeffXSpinner, coeffYSpinner;
	private SliderWithSpinner newWidthSliderWithSpinner, newHeightSliderWithSpinner;
	private JButton
			button_nn_resampling,
			button_bilinear_resampling;
	
	
	public ResizingWindow(
		ResizingWindowModel resizingWindowModel,
		Action action_apply_nn_resampling,
		Action action_apply_bilinear_resampling,
		RasterImage image
	)
	{
		super("Resizing");
		
		assert resizingWindowModel != null && action_apply_nn_resampling != null && action_apply_bilinear_resampling != null;
		
		
		this.resizingWindowModel = resizingWindowModel;
		this.action_apply_nn_resampling = action_apply_nn_resampling;
		this.action_apply_bilinear_resampling = action_apply_bilinear_resampling;
		
		this.setVisible(false);
		this.setSize(250, 230);
		this.setLocation(100, 300);
		this.setResizable(false);
		this.createAndShowGui(ResizingWindow.getWidth(image), ResizingWindow.getHeight(image));
	}
	
	private static int getWidth(RasterImage image)
	{
		if (image == null)
		{
			return 0;
		}
		else
		{
			return image.getWidth();
		}
	}
	
	private static int getHeight(RasterImage image)
	{
		if (image == null)
		{
			return 0;
		}
		else
		{
			return image.getHeight();
		}
	}
	
	private void createAndShowGui(int imageWidth, int imageHeight)
	{
		this.createPanels();
		this.createComponents(imageWidth, imageHeight);
		this.addComponents();
		this.setVisible(true);
	}
	
	private void createPanels()
	{
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
		
		this.currentWidthPanel = new JPanel();
		this.currentWidthPanel.setLayout(new BoxLayout(this.currentWidthPanel, BoxLayout.LINE_AXIS));
		
		this.currentHeightPanel = new JPanel();
		this.currentHeightPanel.setLayout(new BoxLayout(this.currentHeightPanel, BoxLayout.LINE_AXIS));
		
		this.nnResamplingPanel = new JPanel();
		this.nnResamplingPanel.setLayout(new BoxLayout(this.nnResamplingPanel, BoxLayout.PAGE_AXIS));
		
		this.bilinearResamplingPanel = new JPanel();
		this.bilinearResamplingPanel.setLayout(new BoxLayout(this.bilinearResamplingPanel, BoxLayout.PAGE_AXIS));
		
		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.addTab("Nearest neighbor", this.nnResamplingPanel);
		this.tabbedPane.addTab("Bilinear", this.bilinearResamplingPanel);
		
		this.panel.add(this.currentWidthPanel);
		this.panel.add(this.currentHeightPanel);
		this.panel.add(this.tabbedPane);
		
		this.newWidthPanel = new JPanel();
		this.newWidthPanel.setLayout(new BoxLayout(this.newWidthPanel, BoxLayout.LINE_AXIS));
		
		this.newHeightPanel = new JPanel();
		this.newHeightPanel.setLayout(new BoxLayout(this.newHeightPanel, BoxLayout.LINE_AXIS));
		
		this.bilinearResamplingPanel.add(this.newWidthPanel);
		this.bilinearResamplingPanel.add(this.newHeightPanel);
		
		this.coeffXPanel = new JPanel();
		this.coeffXPanel.setLayout(new BoxLayout(this.coeffXPanel, BoxLayout.LINE_AXIS));
		
		this.coeffYPanel = new JPanel();
		this.coeffYPanel.setLayout(new BoxLayout(this.coeffYPanel, BoxLayout.LINE_AXIS));
		
		this.nnResamplingPanel.add(this.coeffXPanel);
		this.nnResamplingPanel.add(this.coeffYPanel);
		
		this.add(this.panel);
	}
	
	private void createComponents(int imageWidth, int imageHeight)
	{
		this.newWidthSliderWithSpinner = new SliderWithSpinner(this.resizingWindowModel.getNewWidthSliderWithSpinnerModel(), SwingConstants.HORIZONTAL, true);
		this.newHeightSliderWithSpinner = new SliderWithSpinner(this.resizingWindowModel.getNewHeightSliderWithSpinnerModel(), SwingConstants.HORIZONTAL, true);
		this.coeffXSpinner = new JSpinner(this.resizingWindowModel.getCoeffXSpinnerModel());
		this.coeffYSpinner = new JSpinner(this.resizingWindowModel.getCoeffYSpinnerModel());
		
		JFormattedTextField textField = ((JSpinner.DefaultEditor) this.coeffXSpinner.getEditor()).getTextField();
		textField.setEditable(false);
		
		textField = ((JSpinner.DefaultEditor) this.coeffYSpinner.getEditor()).getTextField();
		textField.setEditable(false);
		
		this.currentWidthTextarea = new JTextArea();
		this.currentWidthTextarea.setRows(1);
		this.currentWidthTextarea.setEditable(false);
		
		this.currentHeightTextarea = new JTextArea();
		this.currentHeightTextarea.setRows(1);
		this.currentHeightTextarea.setEditable(false);
		
		this.updateTextFields(imageWidth, imageHeight);
		
		this.newWidthLabel = new JLabel("new width:");
		this.newHeightLabel = new JLabel("new height:");
		this.coeffXLabel = new JLabel("stretch horizontally N times:");
		this.coeffYLabel = new JLabel("stretch vertically N times:");
		this.currentWidthLabel = new JLabel("current width:");
		this.currentHeightLabel = new JLabel("current height:");
		
		this.newWidthLabel.setLabelFor(this.newWidthSliderWithSpinner);
		this.newHeightLabel.setLabelFor(this.newHeightSliderWithSpinner);
		this.coeffXLabel.setLabelFor(this.coeffXSpinner);
		this.coeffYLabel.setLabelFor(this.coeffYSpinner);
		this.currentWidthLabel.setLabelFor(this.currentWidthTextarea);
		this.currentHeightLabel.setLabelFor(this.currentHeightTextarea);
		
		this.button_nn_resampling = new JButton(this.action_apply_nn_resampling);
		this.button_bilinear_resampling = new JButton(this.action_apply_bilinear_resampling);
	}
	
	private void addComponents()
	{
		this.currentWidthPanel.add(this.currentWidthLabel);
		this.currentWidthPanel.add(this.currentWidthTextarea);
		
		this.currentHeightPanel.add(this.currentHeightLabel);
		this.currentHeightPanel.add(this.currentHeightTextarea);
		
		this.newWidthPanel.add(this.newWidthLabel);
		this.newWidthPanel.add(this.newWidthSliderWithSpinner);
		this.newHeightPanel.add(this.newHeightLabel);
		this.newHeightPanel.add(this.newHeightSliderWithSpinner);
		this.bilinearResamplingPanel.add(this.button_bilinear_resampling);
		
		this.coeffXPanel.add(this.coeffXLabel);
		this.coeffXPanel.add(this.coeffXSpinner);
		this.coeffYPanel.add(this.coeffYLabel);
		this.coeffYPanel.add(this.coeffYSpinner);
		this.nnResamplingPanel.add(this.button_nn_resampling);
	}
	
	private void updateTextFields(int currentWidth, int currentHeight)
	{
		assert (currentWidth == 0) == (currentHeight == 0);
		
		
		if (currentWidth == 0)
		{
			this.currentWidthTextarea.setText("N/A");
			this.currentHeightTextarea.setText("N/A");
		}
		else
		{
			this.currentWidthTextarea.setText(currentWidth +"px");
			this.currentHeightTextarea.setText(currentHeight +"px");
		}
	}
	
	public void updateImageSize(int newWidth, int newHeight)
	{
		this.updateTextFields(newWidth, newHeight);
	}
}
