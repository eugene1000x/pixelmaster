
package eugenejonas.pixelmaster.modules.spherical_wave.gui;


import eugenejonas.pixelmaster.modules.spherical_wave.domain.graph.*;

import java.awt.*;
import java.io.*;
import javax.swing.*;


public class GraphOpenSaveDialogs
{
	private static JFileChooser openDialog = null, saveDialog = null;
	
	
	private static void createOpenDialog()
	{
		if (GraphOpenSaveDialogs.openDialog != null)
		{
			return;
		}
		
		GraphOpenSaveDialogs.openDialog = new JFileChooser();
		GraphOpenSaveDialogs.openDialog.setLocation(400, 300);
	}
	
	private static void createSaveDialog()
	{
		if (GraphOpenSaveDialogs.saveDialog != null)
		{
			return;
		}
		
		GraphOpenSaveDialogs.saveDialog = new JFileChooser();
		GraphOpenSaveDialogs.saveDialog.setLocation(400, 300);
	}
	
	/**
	 * Opens file dialog in load mode and loads graph from file.
	 *
	 * @param parentWindow Can be null.
	 */
	/*public static UnorientedGraph loadGraph(Frame parentWindow, int minX, int minY, int maxX, int maxY)
	{
		GraphOpenSaveDialogs.createOpenDialog();
		GraphOpenSaveDialogs.openDialog.setMultiSelectionEnabled(false);
		
		int returnVal = GraphOpenSaveDialogs.openDialog.showOpenDialog(parentWindow);
		
		try
		{
			if (returnVal != JFileChooser.APPROVE_OPTION)
			{
				return null;
			}
			
			File f = GraphOpenSaveDialogs.openDialog.getSelectedFile();
			
			assert f != null;
			
			UnorientedGraph graph = GraphIo.readUnorientedGraph(f, minX, minY, maxX, maxY);
			
			if (graph == null)
			{
				
			}
			else
			{
				return graph;
			}
		}
		catch (IOException exc)
		{
			JOptionPane.showMessageDialog(parentWindow, "Error occurred while reading from file.");
			return null;
		}
	}*/
	
	/**
	 * Opens file dialog in save mode and saves graph to file.
	 * 
	 * @param parentWindow Can be null.
	 */
	public static void saveGraph(Frame parentWindow, UnorientedGraph graph)
	{
		GraphOpenSaveDialogs.createSaveDialog();
		GraphOpenSaveDialogs.saveDialog.setMultiSelectionEnabled(false);
		
		int returnVal = GraphOpenSaveDialogs.saveDialog.showSaveDialog(parentWindow);
		
		if (returnVal != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		
		File f = GraphOpenSaveDialogs.saveDialog.getSelectedFile();
		
		assert f != null;
		
		try
		{
			GraphIo.writeUnorientedGraph(graph, f);
		}
		catch (IOException exc)
		{
			JOptionPane.showMessageDialog(parentWindow, "Error occurred while writing to file.");
		}
	}
}
