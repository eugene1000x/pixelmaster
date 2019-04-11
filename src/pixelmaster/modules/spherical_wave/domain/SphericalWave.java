
package pixelmaster.modules.spherical_wave.domain;


import pixelmaster.core.api.domain.*;
import pixelmaster.modules.spherical_wave.domain.graph.*;

import java.awt.*;
import java.util.*;


/**
 * Implementation of spherical wave algorithm.
 * 
 * @see <a href="http://ocrai.narod.ru/vectory.html">Применение волнового алгоритма для нахождения скелета растрового изображения</a>
 */
public final class SphericalWave
{
	/**
	 * CONNECTIVITY_16 - using 16 neighbor pixels
	 * CONNECTIVITY_4 - using 4-connectivity
	 * CONNECTIVITY_8 - using 8-connectivity
	 */
	private static final int CONNECTIVITY_16 = 1;
	private static final int CONNECTIVITY_4 = 2;
	private static final int CONNECTIVITY_8 = 3;
	
	/**
	 * CONNECTIVITY_MODE_16 - using 16 neighbor pixels
	 * CONNECTIVITY_MODE_4_8 - combining 4-connectivity and 8-connectivity
	 */
	public static final int CONNECTIVITY_MODE_16 = 1;
	public static final int CONNECTIVITY_MODE_4_8 = 2;
	
	
	private static int color1 = 0x004f7fb5;
	private static int color2 = 0x000000;
	private static int color = SphericalWave.color1;
		
	/**
	 * Determines how waves will be generated. One of CONNECTIVITY_* constants.
	 */
	private static int connectivity;
	
	
	private static int getNextColor()
	{
		if (SphericalWave.color == SphericalWave.color2)
		{
			SphericalWave.color = SphericalWave.color1;
		}
		else
		{
			SphericalWave.color = SphericalWave.color2;
		}
		
		return SphericalWave.color;
	}
	
	/**
	 * Paints specified pixels on given image in specified color.
	 */
	public static void paintPixels(Set <Point> pixels, RasterImage image, int color)
	{
		assert pixels != null && image != null && (color & 0xff000000) == 0;
		
		
		int
			width = image.getWidth(),
			height = image.getHeight();
		
		Iterator <Point> it = pixels.iterator();
		
		while (it.hasNext())
		{
			Point p = it.next();
			
			assert p.x >= 0 && p.x < width && p.y >= 0 && p.y < height;
			
			image.setRgb(p.x, p.y, color);
		}
	}
	
	public static Point calculateAveragePoint(Set <Point> points)
	{
		assert points != null && !points.isEmpty();
		
		
		Iterator <Point> it = points.iterator();
		
		int
			sumX = 0,
			sumY = 0;
		
		while (it.hasNext())
		{
			Point i = it.next();
			
			sumX += i.x;
			sumY += i.y;
		}
		
		int
			size = points.size(),
			avgX = sumX / size,
			avgY = sumY / size;
		
		return new Point(avgX, avgY);
	}
	
	/**
	 * Paints vertices of given graph on given image.
	 */
	public static void drawVertices(RasterImage image, UnorientedGraph graph)
	{
		assert image != null && graph != null;
		
		
		VertexIterator it = graph.getVertexIterator();
		
		while (it.hasNext())
		{
			Vertex v = it.next();
		
			assert v.getX() >= 0 && v.getX() < image.getWidth() && v.getY() >= 0 && v.getY() < image.getHeight();
			
			image.setRgb(v.getX(), v.getY(), 0x00ff0000);
		}
	}
	
	/**
	 * Draws structure (represented by graph) on given image.
	 */
	public static void drawStructure(RasterImage image, UnorientedGraph graph)
	{
		assert image != null && graph != null;
		
		
		UnorientedEdgeIterator it = graph.getEdgeIterator();
		UnorientedEdge edge;
		
		while (it.hasNext())
		{
			edge = it.next();
			
			Vertex
				v1 = edge.firstVertex,
				v2 = edge.secondVertex;
		
			assert v1.getX() >= 0 && v1.getX() < image.getWidth() && v1.getY() >= 0 && v1.getY() < image.getHeight();
			assert v2.getX() >= 0 && v2.getX() < image.getWidth() && v2.getY() >= 0 && v2.getY() < image.getHeight();
			
			BresenhamAlgorithm.drawLine(image, v1.getX(), v1.getY(), v2.getX(), v2.getY(), 0x00ff0000);
		}
	}
	
	/**
	 * Finds connected component in given search area based on 8-connectivity.
	 */
	public static Set <Point> findConnectedComponent(Set <Point> searchArea, Point start)
	{
		assert searchArea != null && start != null && searchArea.contains(start);
		
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			for (Point p: searchArea)
			{
				assert p != null;
			}
		}
		
		
		Set <Point> connected = new HashSet <Point> ();
		Stack <Point> pointsTmp = new Stack <Point> ();
		
		pointsTmp.push(start);
		searchArea.remove(start);
		
		Point[] neighbors = new Point[8];
		
		for (int i = 0; i < 8; i++)
		{
			neighbors[i] = new Point();
		}
		
		while (!pointsTmp.empty())
		{
			Point v = pointsTmp.pop();
			connected.add(v);
			
			neighbors[0].x = v.x;
			neighbors[0].y = v.y - 1;
			neighbors[1].x = v.x + 1;
			neighbors[1].y = v.y - 1;
			neighbors[2].x = v.x + 1;
			neighbors[2].y = v.y;
			neighbors[3].x = v.x + 1;
			neighbors[3].y = v.y + 1;
			neighbors[4].x = v.x;
			neighbors[4].y = v.y + 1;
			neighbors[5].x = v.x - 1;
			neighbors[5].y = v.y + 1;
			neighbors[6].x = v.x - 1;
			neighbors[6].y = v.y;
			neighbors[7].x = v.x - 1;
			neighbors[7].y = v.y - 1;
			
			for (int i = 0; i < 8; i++)
			{
				if (searchArea.contains(neighbors[i]))
				{
					pointsTmp.push(neighbors[i]);
					searchArea.remove(neighbors[i]);
					neighbors[i] = new Point();
				}
			}
		}
		
		return connected;
	}
	
	/**
	 * Divides set of points into 8-connected components and puts them into stack.
	 */
	public static void divideWave(Stack <Set <Point> > waveGenerations, Set <Point> points)
	{
		assert waveGenerations != null && points != null;
		
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			for (Point p: points)
			{
				assert p != null;
			}
		}
		
		
		Iterator <Point> it = points.iterator();
		
		while (it.hasNext())
		{
			Point p = it.next();
			
			Set <Point> waveGeneration = SphericalWave.findConnectedComponent(points, p);
			//Set <Point> waveGeneration = SphericalWave.findConnectedComponent(points, new Point(p));		//for testing
			
			waveGenerations.add(waveGeneration);
			it = points.iterator();
		}
	}
	
	/**
	 * Generates spherical wave from every pixel in given set and returns
	 * wave generation as set of pixels. Note: result may not be 8-connected component.
	 */
	public static Set <Point> generateWave(Set <Point> pixels, boolean[] isBackground, boolean[] isVisited, int imageWidth, int imageHeight)
	{
		assert
			pixels != null && isBackground != null && isVisited != null
			&& isBackground != isVisited
			&& imageWidth >= 1 && imageHeight >= 1
			&& isBackground.length == imageWidth * imageHeight && isVisited.length == imageWidth * imageHeight;
		
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			for (int i = 0; i < isBackground.length; i++)
			{
				assert !(isVisited[i] && isBackground[i]);
			}
		}
			
		
		Set <Point> waveGeneration = new HashSet <Point> ();
		Iterator <Point> it = pixels.iterator();
		Point[] neighbors;
		
		if (SphericalWave.connectivity == SphericalWave.CONNECTIVITY_16)
		{
			neighbors = new Point[16];
			
			for (int i = 0; i < 16; i++)
			{
				neighbors[i] = new Point();
			}
			
			while (it.hasNext())
			{
				Point p = it.next();
				
				assert p.x >= 0 && p.x < imageWidth && p.y >= 0 && p.y < imageHeight && isVisited[p.y * imageWidth + p.x] && !isBackground[p.y * imageWidth + p.x];
				
				neighbors[0].x = p.x;
				neighbors[0].y = p.y - 3;
				neighbors[1].x = p.x + 1;
				neighbors[1].y = p.y - 3;
				neighbors[2].x = p.x + 2;
				neighbors[2].y = p.y - 2;
				neighbors[3].x = p.x + 3;
				neighbors[3].y = p.y - 1;
				neighbors[4].x = p.x + 3;
				neighbors[4].y = p.y;
				neighbors[5].x = p.x + 3;
				neighbors[5].y = p.y + 1;
				neighbors[6].x = p.x + 2;
				neighbors[6].y = p.y + 2;
				neighbors[7].x = p.x + 1;
				neighbors[7].y = p.y + 3;
				neighbors[8].x = p.x;
				neighbors[8].y = p.y + 3;
				neighbors[9].x = p.x - 1;
				neighbors[9].y = p.y + 3;
				neighbors[10].x = p.x - 2;
				neighbors[10].y = p.y + 2;
				neighbors[11].x = p.x - 3;
				neighbors[11].y = p.y + 1;
				neighbors[12].x = p.x - 3;
				neighbors[12].y = p.y;
				neighbors[13].x = p.x - 3;
				neighbors[13].y = p.y - 1;
				neighbors[14].x = p.x - 2;
				neighbors[14].y = p.y - 2;
				neighbors[15].x = p.x - 1;
				neighbors[15].y = p.y - 3;
			
				for (int i = 0; i < 16; i++)
				{
					int index = neighbors[i].y * imageWidth + neighbors[i].x;
					
					if (
						neighbors[i].x >= 0 && neighbors[i].x < imageWidth
						&& neighbors[i].y >= 0 && neighbors[i].y < imageHeight
						&& !isVisited[index]
						&& !isBackground[index]
						&& !waveGeneration.contains(neighbors[i])
					)
					{
						waveGeneration.add(neighbors[i]);
						isVisited[index] = true;
						neighbors[i] = new Point();
					}
				}
			}
		}
		else if (SphericalWave.connectivity == SphericalWave.CONNECTIVITY_4)
		{
			neighbors = new Point[4];
			
			for (int i = 0; i < 4; i++)
			{
				neighbors[i] = new Point();
			}
			
			while (it.hasNext())
			{
				Point p = it.next();
				
				assert p.x >= 0 && p.x < imageWidth && p.y >= 0 && p.y < imageHeight && isVisited[p.y * imageWidth + p.x] && !isBackground[p.y * imageWidth + p.x];
				
				neighbors[0].x = p.x;
				neighbors[0].y = p.y - 1;
				neighbors[1].x = p.x;
				neighbors[1].y = p.y + 1;
				neighbors[2].x = p.x - 1;
				neighbors[2].y = p.y;
				neighbors[3].x = p.x + 1;
				neighbors[3].y = p.y;
				
				for (int i = 0; i < 4; i++)
				{
					int index = neighbors[i].y * imageWidth + neighbors[i].x;
					
					if (
						neighbors[i].x >= 0 && neighbors[i].x < imageWidth
						&& neighbors[i].y >= 0 && neighbors[i].y < imageHeight
						&& !isVisited[index]
						&& !isBackground[index]
						&& !waveGeneration.contains(neighbors[i])
					)
					{
						waveGeneration.add(neighbors[i]);
						isVisited[index] = true;
						neighbors[i] = new Point();
					}
				}
			}
			
			SphericalWave.connectivity = SphericalWave.CONNECTIVITY_8;
		}
		else if (SphericalWave.connectivity == SphericalWave.CONNECTIVITY_8)
		{
			neighbors = new Point[8];
			
			for (int i = 0; i < 8; i++)
			{
				neighbors[i] = new Point();
			}
			
			while (it.hasNext())
			{
				Point p = it.next();
				
				assert p.x >= 0 && p.x < imageWidth && p.y >= 0 && p.y < imageHeight && isVisited[p.y * imageWidth + p.x] && !isBackground[p.y * imageWidth + p.x];
			
				neighbors[0].x = p.x;
				neighbors[0].y = p.y - 1;
				neighbors[1].x = p.x;
				neighbors[1].y = p.y + 1;
				neighbors[2].x = p.x - 1;
				neighbors[2].y = p.y;
				neighbors[3].x = p.x + 1;
				neighbors[3].y = p.y;
				neighbors[4].x = p.x + 1;
				neighbors[4].y = p.y + 1;
				neighbors[5].x = p.x - 1;
				neighbors[5].y = p.y + 1;
				neighbors[6].x = p.x + 1;
				neighbors[6].y = p.y - 1;
				neighbors[7].x = p.x - 1;
				neighbors[7].y = p.y - 1;
				
				for (int i = 0; i < 8; i++)
				{
					int index = neighbors[i].y * imageWidth + neighbors[i].x;
					
					if (
						neighbors[i].x >= 0 && neighbors[i].x < imageWidth
						&& neighbors[i].y >= 0 && neighbors[i].y < imageHeight
						&& !isVisited[index]
						&& !isBackground[index]
						&& !waveGeneration.contains(neighbors[i])
					)
					{
						waveGeneration.add(neighbors[i]);
						isVisited[index] = true;
						neighbors[i] = new Point();
					}
				}
			}
			
			SphericalWave.connectivity = SphericalWave.CONNECTIVITY_4;
		}
		else
		{
			assert false;
		}
		
		return waveGeneration;
	}
	
	/**
	 * Initiates propagation of spherical wave through binary image starting
	 * from specified pixel and adds information about structure of image to graph.
	 * If <code>waves</code> != null, paints all wave generations on <code>waves</code> image.
	 */
	public static void initiateWavePropagation(
		boolean[] isBackground,
		boolean[] isVisited,
		int imageWidth,
		int imageHeight,
		RasterImage waves,
		UnorientedGraph graph,
		Point start,
		int waveGenerationMinSize
	)
	{
		assert
			isBackground != null && isVisited != null && graph != null && start != null
			&& isBackground != isVisited
			&& imageWidth >= 1 && imageHeight >= 1
			&& isBackground.length == imageWidth * imageHeight
			&& isBackground.length == isVisited.length
			&& waveGenerationMinSize > 0
			&& start.x >= 0 && start.x < imageWidth
			&& start.y >= 0 && start.y < imageHeight
			&& !isBackground[start.y * imageWidth + start.x]
			&& !isVisited[start.y * imageWidth + start.x];
				
		assert waves == null || waves.getWidth() == imageWidth && waves.getHeight() == imageHeight;
		
		if (Config.ARE_FULL_ASSERTION_CHECKS_ENABLED)
		{
			for (int i = 0; i < isBackground.length; i++)
			{
				assert !(isVisited[i] && isBackground[i]);
			}
		}
			
		
		boolean doDrawWaves = waves != null;
		
		Stack <OrderedPair <Vertex, Set <Point> > > waveGenerationsWithAvgPoints = new Stack <OrderedPair <Vertex, Set <Point> > > ();
		OrderedPair <Vertex, Set <Point> > waveGenerationWithAvgPoint;
		Set <Point> waveGeneration = new HashSet <Point> ();
		Stack <Set <Point> > waveGenerations = new Stack <Set <Point> > ();
		
		isVisited[start.y * imageWidth + start.x] = true;
		waveGeneration.add(start);
		Vertex curVertex;
		
		if (waveGenerationMinSize <= 1)
		{
			curVertex = graph.getVertexAt(start.x, start.y);
			
			if (curVertex == null)
			{
				curVertex = graph.addVertex(start.x, start.y);
			}
		}
		else
		{
			curVertex = null;
		}
		
		waveGenerationsWithAvgPoints.push(new OrderedPair <Vertex, Set <Point> > (curVertex, waveGeneration));
		
		while (!waveGenerationsWithAvgPoints.empty())
		{
			waveGenerationWithAvgPoint = waveGenerationsWithAvgPoints.pop();
		
			if (doDrawWaves)
			{
				SphericalWave.paintPixels(waveGenerationWithAvgPoint.second, waves, SphericalWave.getNextColor());
			}
			
			waveGeneration = SphericalWave.generateWave(waveGenerationWithAvgPoint.second, isBackground, isVisited, imageWidth, imageHeight);

			SphericalWave.divideWave(waveGenerations, waveGeneration);
			
			while (!waveGenerations.isEmpty())
			{
				waveGeneration = waveGenerations.pop();
				
				if (waveGeneration.size() >= waveGenerationMinSize)
				{
					Point avg = SphericalWave.calculateAveragePoint(waveGeneration);
					curVertex = graph.getVertexAt(avg.x, avg.y);
			
					if (curVertex == null)
					{
						curVertex = graph.addVertex(avg.x, avg.y);
					}
					
					if (waveGenerationWithAvgPoint.first != null)
					{
						waveGenerationWithAvgPoint.first.addNeighbor(curVertex);
					}
					
					waveGenerationsWithAvgPoints.push(new OrderedPair <Vertex, Set <Point> > (curVertex, waveGeneration));
				}
				else
				{
					waveGenerationsWithAvgPoints.push(new OrderedPair <Vertex, Set <Point> > (null, waveGeneration));
				}
			}
		}
	}
	
	/**
	 * Generates spherical wave from every non-background pixel of binary image and builds structure of image.
	 * If <code>waves</code> != null, paints all wave generations on <code>waves</code> image.
	 * 
	 * @param connectivityMode One of SphericalWave.CONNECTIVITY_MODE_* constants.
	 */
	public static void buildStructure(
		boolean[] isBackground,
		int imageWidth,
		int imageHeight,
		RasterImage waves,
		UnorientedGraph graph,
		int waveGenerationMinSize,
		int connectivityMode
	)
	{
		assert
			isBackground != null
			&& imageWidth >= 1 && imageHeight >= 1
			&& isBackground.length == imageWidth * imageHeight
			&& graph != null
			&& waveGenerationMinSize > 0;
			
		assert waves == null || waves.getWidth() == imageWidth && waves.getHeight() == imageHeight;
		
		
		boolean doDrawWaves = waves != null;
				

		if (connectivityMode == SphericalWave.CONNECTIVITY_MODE_16)
		{
			SphericalWave.connectivity = SphericalWave.CONNECTIVITY_16;
		}
		else if (connectivityMode == SphericalWave.CONNECTIVITY_MODE_4_8)
		{
			SphericalWave.connectivity = SphericalWave.CONNECTIVITY_4;
		}
		else
		{
			assert false;
		}
				
		if (doDrawWaves)
		{
			waves.fill(0x00ffffff);
		}
		
		int size = isBackground.length;
		boolean[] isVisited = new boolean[size];
		
		//Arrays.fill(isVisited, false);
		
		graph.clear();
		
		Point p = new Point();
		
		for (int i = 0; i < size; i++)
		{
			if (!isBackground[i] && !isVisited[i])
			{
				p.x = i % imageWidth;
				p.y = i / imageWidth;
				
				SphericalWave.initiateWavePropagation(isBackground, isVisited, imageWidth, imageHeight, waves, graph, p, waveGenerationMinSize);
			}
		}
	}
}
