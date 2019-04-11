
package pixelmaster.core.api.gui;


import pixelmaster.core.api.domain.*;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.*;


/**
 * Class providing open/save dialog functionality.
 */
public final class OpenSaveDialogs
{
	private static JFileChooser openDialog = null, saveDialog = null;
	

	/**
	 * Returns file extension for specified file format.
	 * 
	 * @param desc Description of file format. One of:
	 *		"bmp", "gray-scale bmp", "jpg", "jpeg", "gif" or "png".
	 */
	private static String getExtByDesc(String desc)
	{
		if (desc.equals("bmp"))
		{
			return ".bmp";
		}
		else if (desc.equals("gray-scale bmp"))
		{
			return ".bmp";
		}
		else if (desc.equals("jpg"))
		{
			return ".jpg";
		}
		else if (desc.equals("jpeg"))
		{
			return ".jpeg";
		}
		else if (desc.equals("gif"))
		{
			return ".gif";
		}
		else if (desc.equals("png"))
		{
			return ".png";
		}
		else
		{
			assert false;
			return null;
		}
	}

	/**
	 * Checks if file extension is supported.
	 * 
	 * @param extension File extension.
	 * @return false if file extension is not supported or is null.
	 */
	/*public static boolean isFileExtensionSupported(String extension)
	{
		if (extension == null)
		{
			return false;
		}
		
		if (
			extension.equals(".gif")
			|| extension.equals(".jpeg")
			|| extension.equals(".jpg")
			|| extension.equals(".bmp")
			|| extension.equals(".png")
		)
		{
			return true;
		}
		else
		{
			return false;
		}
	}*/

	/**
	 * Creates file dialog in load mode.
	 */
	private static void createOpenDialog()
	{
		if (OpenSaveDialogs.openDialog != null)
		{
			return;
		}
		
		OpenSaveDialogs.openDialog = new JFileChooser();
		OpenSaveDialogs.openDialog.setLocation(400, 300);
		
		OpenSaveDialogs.openDialog.addChoosableFileFilter(new FileNameExtensionFilter("bmp", "bmp"));
		OpenSaveDialogs.openDialog.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "jpg", "jpeg"));
		OpenSaveDialogs.openDialog.addChoosableFileFilter(new FileNameExtensionFilter("png", "png"));
		OpenSaveDialogs.openDialog.addChoosableFileFilter(new FileNameExtensionFilter("gif", "gif"));
		OpenSaveDialogs.openDialog.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "bmp", "png", "gif"));
		
		//Locale locale = new Locale("eng");
		//OpenSaveDialogs.openDialog.setLocale(locale.ENGLISH);
	}

	/**
	 * Creates file dialog in save mode.
	 */
	private static void createSaveDialog()
	{
		if (OpenSaveDialogs.saveDialog != null)
		{
			return;
		}
		
		OpenSaveDialogs.saveDialog = new JFileChooser();
		OpenSaveDialogs.saveDialog.setLocation(400, 300);
		
		OpenSaveDialogs.saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("bmp", "bmp"));
		OpenSaveDialogs.saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("gray-scale bmp", "bmp"));
		OpenSaveDialogs.saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
		OpenSaveDialogs.saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("jpeg", "jpeg"));
		OpenSaveDialogs.saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("png", "png"));
		OpenSaveDialogs.saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("gif", "gif"));

		OpenSaveDialogs.saveDialog.removeChoosableFileFilter(OpenSaveDialogs.saveDialog.getAcceptAllFileFilter());
		
		//Locale locale = new Locale("eng");
		//OpenSaveDialogs.saveDialog.setLocale(locale.ENGLISH);
	}

	/**
	 * Opens file dialog in load mode and loads image from file.
	 * 
	 * @param parentWindow Parent window. Can be null.
	 * @return Pointer to BufferedImage object, or null if failed to load image.
	 */
	public static BufferedImage loadImage(Frame parentWindow)
	{
		OpenSaveDialogs.createOpenDialog();
		OpenSaveDialogs.openDialog.setMultiSelectionEnabled(false);
		
		int returnState = OpenSaveDialogs.openDialog.showOpenDialog(parentWindow);
		
		try
		{
			if (returnState != JFileChooser.APPROVE_OPTION)
			{
				return null;
			}
			
			File f = OpenSaveDialogs.openDialog.getSelectedFile();
			BufferedImage image = ImageIO.read(f);
			
			if (image == null)
			{
				JOptionPane.showMessageDialog(
					parentWindow,
					"Unknown file format."
				);
				return null;
			}
			else
			{
				return image;
			}
		}
		catch (IOException exc)
		{
			JOptionPane.showMessageDialog(
				parentWindow,
				"Error occurred while reading from file."
			);
			return null;
		}
	}

	/**
	 * Opens file dialog in load mode and allows user to load multiple images from files.
	 * 
	 * @return Array with pointers to BufferedImage objects. If failed to load one
	 * 		of the images, the corresponding array element will be null.
	 */
	public static BufferedImage[] loadMultipleImages()
	{
		OpenSaveDialogs.createOpenDialog();
		OpenSaveDialogs.openDialog.setMultiSelectionEnabled(true);
		
		int returnState = OpenSaveDialogs.openDialog.showOpenDialog(null);
		File[] f = OpenSaveDialogs.openDialog.getSelectedFiles();
		BufferedImage[] res = new BufferedImage[f.length];
		
		if (returnState != JFileChooser.APPROVE_OPTION)
		{
			assert f.length == 0;
		}
		
		for (int i = 0; i < f.length; i++)
		{
			try
			{
				res[i] = ImageIO.read(f[i]);
				
				if (res[i] == null)
				{
					//unknown format
				}
			}
			catch (IOException exc)
			{
				/*JOptionPane.showMessageDialog(
					null,
					"Error occurred while reading from file."
				);*/
				res[i] = null;
			}
		}
		
		return res;
	}

	/**
	 * Opens file dialog in save mode and saves image to file.
	 * 
	 * @param parentWindow Parent window.
	 * @param image Image to save.
	 */
	public static void saveImage(Frame parentWindow, RasterImage image)
	{
		OpenSaveDialogs.createSaveDialog();
		OpenSaveDialogs.saveDialog.setMultiSelectionEnabled(false);
		
		int returnState = OpenSaveDialogs.saveDialog.showSaveDialog(parentWindow);
		
		if (returnState != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		
		File f = OpenSaveDialogs.saveDialog.getSelectedFile();
		String path = f.getPath();
		String ext = Misc.getFileExtension(path);
		String desc = OpenSaveDialogs.saveDialog.getFileFilter().getDescription();

		if (ext == null)
		{
			path = path.concat(OpenSaveDialogs.getExtByDesc(desc));
		}
		else if (ext.equals("."))
		{
			path = path.concat(OpenSaveDialogs.getExtByDesc(desc));
		}
		else if (ext.equals(".bmp"))
		{
			if (!(desc.equals("bmp") || desc.equals("gray-scale bmp")))
			{
				path = path.substring(0, path.length() - 4);
				path = path.concat(OpenSaveDialogs.getExtByDesc(desc));
			}
		}
		else if (ext.equals(".jpg"))
		{
			if (!desc.equals("jpg"))
			{
				path = path.substring(0, path.length() - 4);
				path = path.concat(OpenSaveDialogs.getExtByDesc(desc));
			}
		}
		else if (ext.equals(".jpeg"))
		{
			if (!desc.equals("jpeg"))
			{
				path = path.substring(0, path.length() - 5);
				path = path.concat(OpenSaveDialogs.getExtByDesc(desc));
			}
		}
		else if (ext.equals(".png"))
		{
			if (!desc.equals("png"))
			{
				path = path.substring(0, path.length() - 4);
				path = path.concat(OpenSaveDialogs.getExtByDesc(desc));
			}
		}
		else if (ext.equals(".gif"))
		{
			if (!desc.equals("gif"))
			{
				path = path.substring(0, path.length() - 4);
				path = path.concat(OpenSaveDialogs.getExtByDesc(desc));
			}
		}
		else
		{
			path = path.concat(OpenSaveDialogs.getExtByDesc(desc));
		}
		
		f = new File(path);
		ext = Misc.getFileExtension(path);
		String formatName = ext.substring(1);
		
		try
		{
			BufferedImage tmpImage;
			
			if (OpenSaveDialogs.saveDialog.getFileFilter().getDescription().equals("gray-scale bmp"))
			{
				tmpImage = ImageConverter.toGrayScaleBufferedImage(image);
			}
			else
			{
				tmpImage = ImageConverter.toBufferedImage(image);
			}
			
			if (!ImageIO.write(tmpImage, formatName, f))
			{
				JOptionPane.showMessageDialog(parentWindow, "Unknown file format.");
			}
		}
		catch (IOException exc)
		{
			JOptionPane.showMessageDialog(parentWindow, "Error occurred while writing to file.");
		}
	}
}
