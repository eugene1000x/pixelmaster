
package eugenejonas.pixelmaster.core.api.gui;


import eugenejonas.pixelmaster.core.api.domain.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * Component that displays RasterImage objects.
 */
public final class ImagePanel extends JPanel
{
	private class ImageCanvas extends JPanel implements MouseListener, MouseMotionListener
	{
		private ImageCanvas()
		{
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}

		@Override
		public void paint(Graphics graphics)
		{
			super.paint(graphics);
			
			if (ImagePanel.this.image != null)
			{
				graphics.drawImage(ImagePanel.this.image, 0, 0, null);

				if (ImagePanel.this.imagePanelModel.getRectangularSelection() != null)
				{
					graphics.setColor(new Color(100, 100, 200));
					graphics.drawRect(
						ImagePanel.this.imagePanelModel.getRectangularSelection().x,
						ImagePanel.this.imagePanelModel.getRectangularSelection().y,
						ImagePanel.this.imagePanelModel.getRectangularSelection().width - 1,
						ImagePanel.this.imagePanelModel.getRectangularSelection().height - 1
					);
				}
			}
		}
		
		@Override
		public void mouseExited(MouseEvent event)
		{
			//nothing
		}
		
		@Override
		public void mouseMoved(MouseEvent event)
		{
			//nothing
		}
		
		@Override
		public void mouseEntered(MouseEvent event)
		{
			//nothing
		}
		
		@Override
		public void mouseClicked(MouseEvent event)
		{
			ImagePanel.this.canvas.repaint();
		}

		@Override
		public void mousePressed(MouseEvent event)
		{
			ImagePanel.this.imagePanelModel.setRectangularSelection(null);
			Point p = event.getPoint();
			
			if (ImagePanel.this.image == null || p.x < 0 || p.x >= ImagePanel.this.image.getWidth(null)
					|| p.y < 0 || p.y >= ImagePanel.this.image.getHeight(null))
			{
				ImagePanel.this.startPoint = null;
			}
			else
			{
				ImagePanel.this.startPoint = p;
			}
			
			ImagePanel.this.canvas.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent event)
		{
			ImagePanel.this.endPoint = event.getPoint();
			
			if (ImagePanel.this.image == null || ImagePanel.this.startPoint == null || ImagePanel.this.endPoint == null)
			{
				ImagePanel.this.imagePanelModel.setRectangularSelection(null);
				return;
			}
			
			if (ImagePanel.this.endPoint.x < 0)
			{
				ImagePanel.this.endPoint.x = 0;
			}
			else if (ImagePanel.this.endPoint.x >= ImagePanel.this.image.getWidth(null))
			{
				ImagePanel.this.endPoint.x = ImagePanel.this.image.getWidth(null) - 1;
			}
			
			if (ImagePanel.this.endPoint.y < 0)
			{
				ImagePanel.this.endPoint.y = 0;
			}
			else if (ImagePanel.this.endPoint.y >= ImagePanel.this.image.getHeight(null))
			{
				ImagePanel.this.endPoint.y = ImagePanel.this.image.getHeight(null) - 1;
			}
			
			int
				x1 = ImagePanel.this.endPoint.x,
				y1 = ImagePanel.this.endPoint.y,
				x2 = ImagePanel.this.startPoint.x,
				y2 = ImagePanel.this.startPoint.y;
			
			if (x1 > x2)
			{
				int tmp = x1;
				x1 = x2;
				x2 = tmp;
			}
			
			if (y1 > y2)
			{
				int tmp = y1;
				y1 = y2;
				y2 = tmp;
			}
			
			ImagePanel.this.imagePanelModel.setRectangularSelection(new Rectangle(x1, y1, x2 - x1 + 1, y2 - y1 + 1));
			ImagePanel.this.canvas.repaint();
		}

		@Override
		public void mouseDragged(MouseEvent event)
		{
			ImagePanel.this.endPoint = event.getPoint();
			
			if (ImagePanel.this.image == null || ImagePanel.this.startPoint == null || ImagePanel.this.endPoint == null)
			{
				ImagePanel.this.imagePanelModel.setRectangularSelection(null);
				return;
			}
			
			if (ImagePanel.this.endPoint.x < 0)
			{
				ImagePanel.this.endPoint.x = 0;
			}
			else if (ImagePanel.this.endPoint.x >= ImagePanel.this.image.getWidth(null))
			{
				ImagePanel.this.endPoint.x = ImagePanel.this.image.getWidth(null) - 1;
			}
			
			if (ImagePanel.this.endPoint.y < 0)
			{
				ImagePanel.this.endPoint.y = 0;
			}
			else if (ImagePanel.this.endPoint.y >= ImagePanel.this.image.getHeight(null))
			{
				ImagePanel.this.endPoint.y = ImagePanel.this.image.getHeight(null) - 1;
			}

			int
				x1 = ImagePanel.this.endPoint.x,
				y1 = ImagePanel.this.endPoint.y,
				x2 = ImagePanel.this.startPoint.x,
				y2 = ImagePanel.this.startPoint.y;
			
			if (x1 > x2)
			{
				int tmp = x1;
				x1 = x2;
				x2 = tmp;
			}
			
			if (y1 > y2)
			{
				int tmp = y1;
				y1 = y2;
				y2 = tmp;
			}
			
			ImagePanel.this.imagePanelModel.setRectangularSelection(new Rectangle(x1, y1, x2 - x1 + 1, y2 - y1 + 1));
			ImagePanel.this.canvas.repaint();
		}
	}
	
	
	private ImagePanelModel imagePanelModel;

	private Image image;
	private ImageCanvas canvas;
	private JScrollPane scrollPane;
	private Point startPoint, endPoint;


	/**
	 * Constructs image panel with default model.
	 *
	 * @param image Image to display. Can be null; ownership: {@link eugenejonas.pixelmaster.core.api.domain.ObjectOwnership#OWNERSHIP_CALLER}.
	 */
	public ImagePanel(RasterImage image)
	{
		this(image, new ImagePanelModel());
	}

	/**
	 * Constructs image panel with provided model.
	 *
	 * @param image Image to display. Can be null; ownership: {@link eugenejonas.pixelmaster.core.api.domain.ObjectOwnership#OWNERSHIP_CALLER}.
	 * @param imagePanelModel Underlying model.
	 */
	public ImagePanel(RasterImage image, ImagePanelModel imagePanelModel)
	{
		this.setLayout(new BorderLayout());
		//this.setBorder(BorderFactory.createRaisedBevelBorder());
		
		this.canvas = new ImageCanvas();
		this.canvas.setBackground(Color.darkGray);
		
		this.imagePanelModel = imagePanelModel;
		
		this.scrollPane = new JScrollPane(
			this.canvas,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
		);
		this.scrollPane.setBackground(Color.darkGray);
		
		this.add(this.scrollPane, BorderLayout.CENTER);
		this.setBackground(Color.darkGray);
		this.setImage(image);
	}

	/**
	 * Returns canvas where image is drawn. Can be used, for example, to register event listeners.
	 */
	public Component getCanvas()
	{
		return this.canvas;
	}

	/**
	 * Displays new image.
	 * 
	 * @param image New image. Can be null; ownership: {@link eugenejonas.pixelmaster.core.api.domain.ObjectOwnership#OWNERSHIP_CALLER}.
	 */
	public void setImage(RasterImage image)
	{
		this.imagePanelModel.setRectangularSelection(null);
		this.startPoint = null;
		this.endPoint = null;

		if (image != null)
		{
			this.image = this.createImage(image.getSource());
			this.canvas.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
			this.canvas.setSize(new Dimension(image.getWidth(), image.getHeight()));
		}
		else
		{
			this.image = null;
			this.canvas.setPreferredSize(new Dimension(0, 0));
			this.canvas.setSize(new Dimension(0, 0));
		}
		
		this.canvas.repaint();
	}
}
