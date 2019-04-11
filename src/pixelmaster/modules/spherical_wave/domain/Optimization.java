
package pixelmaster.modules.spherical_wave.domain;


import pixelmaster.modules.spherical_wave.domain.graph.*;

import java.util.*;


/**
 * Image structure analysis and optimization functionality,
 * where image structure is represented as graph.
 */
public final class Optimization
{
	/**
	 * Connects ends of edges which are closer than distThreshold.
	 */
	public static void connectEdges(UnorientedGraph graph, double distThreshold)
	{
		assert graph != null && distThreshold >= 0;
		
		
		Set <Vertex> vertices = new HashSet <Vertex> ();
		VertexIterator it = graph.getVertexIterator();
		
		while (it.hasNext())
		{
			Vertex v = it.next();
			
			if (v.getDegree() == 1)
			{
				vertices.add(v);
			}
		}
		
		for (Vertex v1: vertices)
		{
			for (Vertex v2: vertices)
			{
				CoordVector a = new CoordVector(v1.getX() - v2.getX(), v1.getY() - v2.getY());
				
				if (a.getLength() < distThreshold)
				{
					v1.addNeighbor(v2);
				}
			}
		}
	}
	
	public static Set <Vertex> findKeyPoints(UnorientedGraph graph, double cosThreshold)
	{
		assert graph != null;
		assert -1 <= cosThreshold && cosThreshold <= 1;
		
		
		Set <Vertex> keyPoints = new HashSet <Vertex> ();
		VertexIterator it = graph.getVertexIterator();
		
		while (it.hasNext())
		{
			Vertex v = it.next();
			int degree = v.getDegree();
			
			if (degree == 1 || degree > 2)
			{
				keyPoints.add(v);
			}
			
			if (degree == 2)
			{
				VertexIterator neighbors = v.getNeighborIterator();
				
				Vertex p = neighbors.next();
				Vertex q = neighbors.next();
				
				double cos = Geometry.calculateCosOfAngleBetweenVectors(p, v, q);
				
				if (cos >= cosThreshold)
				{
					keyPoints.add(v);
				}
			}
		}
		
		return keyPoints;
	}
	
	public static void performPrimaryOptimization(UnorientedGraph graph, double cosThreshold)
	{
		assert graph != null;
		assert -1 <= cosThreshold && cosThreshold <= 1;
		
		
		Set <Vertex> degree3Vertices = Optimization.getVerticesOfDegree(graph, 3);
		Iterator <Vertex> it = degree3Vertices.iterator();
		
		while (it.hasNext())
		{
			Vertex v = it.next();
			
			VertexIterator neighborIterator = v.getNeighborIterator();
			
			Vertex n1 = neighborIterator.next();
			Vertex n2 = neighborIterator.next();
			Vertex n3 = neighborIterator.next();
			
			assert neighborIterator.hasNext() == false;
			
			double cos12 = Geometry.calculateCosOfAngleBetweenVectors(n1, v, n2);
			int f = 3;
			double minCos = cos12;
			double cos13 = Geometry.calculateCosOfAngleBetweenVectors(n1, v, n3);
			
			if (cos13 < minCos)
			{
				f = 2;
				minCos = cos13;
			}
			
			double cos23 = Geometry.calculateCosOfAngleBetweenVectors(n2, v, n3);
			
			if (cos23 < minCos)
			{
				f = 1;
				minCos = cos23;
			}
			
			if (minCos < cosThreshold)
			{
				if (f == 1)
				{
					Vertex newVertex = graph.addVertex((n2.getX() + n3.getX()) / 2, (n2.getY() + n3.getY()) / 2);
					
					if (newVertex != null)
					{
						v.delete();
						
						newVertex.addNeighbor(n3);
						newVertex.addNeighbor(n2);
						newVertex.addNeighbor(n1);
					}
					else
					{
						//replaced vertex is null (not bug)
					}
				}
				else if (f == 2)
				{
					Vertex newVertex = graph.addVertex((n1.getX() + n3.getX()) / 2, (n1.getY() + n3.getY()) / 2);
					
					if (newVertex != null)
					{
						v.delete();
						
						newVertex.addNeighbor(n3);
						newVertex.addNeighbor(n2);
						newVertex.addNeighbor(n1);
					}
					else
					{
						//replaced vertex is null (not bug)
					}
				}
				else if (f == 3)
				{
					Vertex newVertex = graph.addVertex((n2.getX() + n1.getX()) / 2, (n2.getY() + n1.getY()) / 2);
					
					if (newVertex != null)
					{
						v.delete();
						
						newVertex.addNeighbor(n3);
						newVertex.addNeighbor(n2);
						newVertex.addNeighbor(n1);
					}
					else
					{
						//replaced vertex is null (not bug)
					}
				}
				else
				{
					assert false;
				}
			}
			
			degree3Vertices.remove(v);
			it = degree3Vertices.iterator();
		}
	}
	
	/**
	 * @param varianceThreshold Smaller threshold value means less changes will be made.
	 */
	public static void optimizeEdges(UnorientedGraph graph, double varianceThreshold)
	{
		assert graph != null && varianceThreshold >= 0;
		
		
		Set <UnorientedEdge> edges = graph.getEdges();
		Iterator <UnorientedEdge> edgeIterator = edges.iterator();
		Vector <Vertex> vertices = new Vector <Vertex> ();
		
		while (edgeIterator.hasNext())
		{
			vertices.clear();
			
			UnorientedEdge edge = edgeIterator.next();
			
			vertices.add(edge.firstVertex);
			vertices.add(edge.secondVertex);
			
			do
			{
				boolean isAdded1 = false;
				boolean isAdded2 = false;
				
				if (vertices.lastElement().getDegree() == 2)
				{
					VertexIterator neighborIterator = vertices.lastElement().getNeighborIterator();
					
					Vertex neighbor1, neighbor2;
					
					neighbor1 = neighborIterator.next();
					neighbor2 = neighborIterator.next();
					
					if (!vertices.contains(neighbor1))
					{
						if (edges.contains(new UnorientedEdge(vertices.lastElement(), neighbor1)))
						{
							edges.remove(new UnorientedEdge(vertices.lastElement(), neighbor1));
							vertices.add(neighbor1);
							isAdded1 = true;
						}
					}
					else
					{
						if (!vertices.contains(neighbor2))
						{
							if (edges.contains(new UnorientedEdge(vertices.lastElement(), neighbor2)))
							{
								edges.remove(new UnorientedEdge(vertices.lastElement(), neighbor2));
								vertices.add(neighbor2);
								isAdded1 = true;
							}
						}
					}
					
					if (isAdded1)
					{
						double variance = Geometry.calculateVariance(vertices);
						
						if (variance > varianceThreshold)
						{
							vertices.remove(vertices.size() - 1);
							isAdded1 = false;
						}
					}
				}
				
				if (vertices.firstElement().getDegree() == 2)
				{
					VertexIterator neighborIterator = vertices.firstElement().getNeighborIterator();
					
					Vertex neighbor1, neighbor2;
					
					neighbor1 = neighborIterator.next();
					neighbor2 = neighborIterator.next();
					
					if (!vertices.contains(neighbor1))
					{
						if (edges.contains(new UnorientedEdge(vertices.firstElement(), neighbor1)))
						{
							edges.remove(new UnorientedEdge(vertices.firstElement(), neighbor1));
							vertices.add(0, neighbor1);
							isAdded2 = true;
						}
					}
					else
					{
						if (!vertices.contains(neighbor2))
						{
							if (edges.contains(new UnorientedEdge(vertices.firstElement(), neighbor2)))
							{
								edges.remove(new UnorientedEdge(vertices.firstElement(), neighbor2));
								vertices.add(0, neighbor2);
								isAdded2 = true;
							}
						}
					}
					
					if (isAdded2)
					{
						double variance = Geometry.calculateVariance(vertices);
						
						if (variance > varianceThreshold)
						{
							vertices.remove(0);
							isAdded2 = false;
						}
					}
				}
				
				if (!isAdded1 && !isAdded2)
				{
					//replace vertices with one edge
					
					Iterator <Vertex> removeIterator = vertices.iterator();
					
					removeIterator.next();
					
					Vertex v = removeIterator.next();
					
					//delete vertices at indexes 1 .. <size - 1> (index starts from 0)
					while (removeIterator.hasNext())
					{
						v.delete();
						v = removeIterator.next();
						assert v != null;
					}
					
					boolean b = vertices.firstElement().addNeighbor(vertices.lastElement());
					
					assert vertices.size() > 1;
					
					break;
				}
			}
			while (true);
			
			edges.remove(new UnorientedEdge(vertices.firstElement(), vertices.lastElement()));
			edgeIterator = edges.iterator();
		}
	}
	
	/**
	 * Cuts "tails" of graph, or edge sequences which are shorter than threshold.
	 */
	public static void cutTails(UnorientedGraph graph, double lengthThreshold)
	{
		assert graph != null && lengthThreshold >= 0;
		
		
		Set <Vertex> vertices = graph.getVertices();
		
		for (Vertex v: vertices)
		{
			if (v.getDegree() == 0)
			{
				v.delete();
			}
		}
			
		Set <Vertex> degree1Vertices = Optimization.getVerticesOfDegree(graph, 1);
		Iterator <Vertex> it = degree1Vertices.iterator();
		
		Vector <Vertex> verticesToDelete = new Vector <Vertex> ();
		
		while (it.hasNext())
		{
			Vertex v1 = it.next();
			
			if (v1.getDegree() != 1)
			{
				//vertex neighbor may have been deleted during iteration
				assert v1.getDegree() == 0;
				continue;
			}
			
			verticesToDelete.clear();
			verticesToDelete.add(v1);
			
			Vertex v2 = v1.getNeighborIterator().next();
			double len = Geometry.calculateDistance(v1, v2);
			
			while (len < lengthThreshold)
			{
				verticesToDelete.add(v2);
				
				if (v2.getDegree() != 2)
				{
					break;
				}
				
				VertexIterator v2NeighborIterator = v2.getNeighborIterator();
				Vertex v3 = v2NeighborIterator.next();
				
				if (v3.equals(verticesToDelete.elementAt(verticesToDelete.size() - 2)))
				{
					v3 = v2NeighborIterator.next();
				}
				
				len += Geometry.calculateDistance(v2, v3);
			}
			
			verticesToDelete.removeElementAt(verticesToDelete.size() - 1);
			
			for (Vertex tmp: verticesToDelete)
			{
				tmp.delete();
			}
		}
		
		vertices = graph.getVertices();
		
		for (Vertex v: vertices)
		{
			if (v.getDegree() == 0)
			{
				v.delete();
			}
		}
	}
	
	/**
	 * Returns vertices of given graph which have specified degree (number of edges that connect to vertex).
	 */
	public static Set <Vertex> getVerticesOfDegree(UnorientedGraph graph, int degree)
	{
		VertexIterator it = graph.getVertexIterator();
		Vertex v;
		Set <Vertex> vertices = new HashSet <Vertex> ();
		
		while (it.hasNext())
		{
			v = it.next();
			
			if (v.getDegree() == degree)
			{
				vertices.add(v);
			}
		}
		
		return vertices;
	}
}
