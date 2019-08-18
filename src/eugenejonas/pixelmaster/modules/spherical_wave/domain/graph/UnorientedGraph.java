
package eugenejonas.pixelmaster.modules.spherical_wave.domain.graph;


import java.util.*;


public final class UnorientedGraph implements Cloneable
{
	int edgeCount;
	
	/**
	 * Using Hashtable instead of HashSet because HashSet does not provide
	 * a way to retrieve pointer to the exact object stored in the set by its value:
	
		Set <Vertex> set = new HashSet <Vertex> ();
		Vertex v = new Vertex(35, 12);
		set.add(v);
		
		// add 100 more objects
		
		v = null;
		
		// pointer to vertex at (35, 12) can be retrieved only by iterating through all elements
	
	 * With Hashtable the original object can easily be retrieved if it is stored as value of key/value pair:
	
		Hashtable <Vertex, Vertex> hashtable = new Hashtable <Vertex, Vertex> ();
		Vertex v = new Vertex(35, 12);
		hashtable.put(v, v);
		
		// put 100 more objects
		
		v = null;
		
		// get pointer to vertex at (35, 12):
		Vertex v2 = new Vertex(35, 12);
		v = hashtable.get(v2);
		assert v2 != v;
	 */
	Hashtable <Vertex, Vertex> vertices;
	
	
	public UnorientedGraph()
	{
		this.vertices = new Hashtable <Vertex, Vertex> ();
		this.edgeCount = 0;
	}
	
	private boolean invariant()
	{
		assert this.vertices != null;
		assert this.edgeCount == this.getEdges().size();
		
		for (Vertex v1: this.getVertices())
		{
			assert v1.owner == this;
			
			for (Vertex v2: this.getVertices())
			{
				if (v1 != v2)
				{
					assert v1.neighbors != v2.neighbors;
				}
			}
			
			// make sure that keys of hashtable are equal to values
			for (Map.Entry <Vertex, Vertex> entry: this.vertices.entrySet())
			{
				assert entry.getKey() == entry.getValue();
			}
		}
		
		return true;
	}
	
	/**
	 * Two instances of UnorientedGraph are equal if they contain the same vertices
	 * (at the same coordinates) and the same edges (connecting vertices with the same coordinates).
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof UnorientedGraph))
		{
			return super.equals(obj);
		}
		
		UnorientedGraph other = (UnorientedGraph) obj;
		
		return this.getVertices().equals(other.getVertices()) && this.getEdges().equals(other.getEdges());
	}
	
	@Override
	public int hashCode()
	{
		// Not an efficient implementation, so not quite suitable for storing graphs in data
		// structures like HashSet or Hashtable, but it adheres to the general contract of hashCode() and equals().
		// Currently, equals() is used only for direct comparison, so that is ok.
		return 5;
	}
	
	/**
	 * Creates deep copy of this graph.
	 */
	@Override
	public Object clone()
	{
		UnorientedGraph clonedGraph = new UnorientedGraph();
		VertexIterator it = this.getVertexIterator();
		
		while (it.hasNext())
		{
			Vertex v = it.next();
			clonedGraph.addVertex(v.getX(), v.getY());
		}
		
		UnorientedEdgeIterator edgeIterator = new UnorientedEdgeIterator(this);
		
		while (edgeIterator.hasNext())
		{
			UnorientedEdge edge = edgeIterator.next();
			
			clonedGraph.addEdge(
				edge.firstVertex.getX(),
				edge.firstVertex.getY(),
				edge.secondVertex.getX(),
				edge.secondVertex.getY()
			);
		}
		
		assert clonedGraph.invariant();
		return clonedGraph;
	}
	
	/**
	 * Deletes all vertices and edges.
	 */
	public void clear()
	{
		assert this.invariant();
		
		
		VertexIterator it = this.getVertexIterator();
		
		while (it.hasNext())
		{
			Vertex v = it.next();
			
			v.neighbors = null;
			v.owner = null;
		}
		
		this.vertices.clear();
		this.edgeCount = 0;
		
		
		assert this.invariant();
	}
	
	/**
	 * Adds new vertex (without neighbors) at specified coordinates.
	 * If graph already contains vertex at given coordinates, no changes will be made.
	 * 
	 * @return Pointer to the newly created Vertex object or null if graph already contains
	 * 		vertex at given coordinates.
	 */
	public Vertex addVertex(int x, int y)
	{
		assert this.invariant();
		
		
		Vertex v = new Vertex(x, y);
		
		if (this.vertices.containsKey(v))
		{
			return null;
		}
		
		v.neighbors = new ArrayList <Vertex> ();
		v.owner = this;
		
		this.vertices.put(v, v);
		
		assert this.invariant();
		return v;
	}
	
	/**
	 * @return Pointer to vertex or null if graph does not contain vertex at given coordinates.
	 */
	public Vertex getVertexAt(int x, int y)
	{
		return this.vertices.get(new Vertex(x, y));
	}
	
	/**
	 * Finds vertex at specified coordinates and returns its degree (number of edges that connect to it).
	 * 
	 * @return Degree of vertex or -1 if graph does not contain vertex at specified coordinates.
	 */
	public int getVertexDegree(int x, int y)
	{
		Vertex v = this.getVertexAt(x, y);
		
		if (v == null)
		{
			return -1;
		}
		else
		{
			return v.getDegree();
		}
	}
	
	/**
	 * Deletes given vertex from this graph. If this graph does not contain
	 * given vertex, no changes will be made.
	 * 
	 * @return true if graph changed as a result of the call.
	 */
	public boolean deleteVertex(Vertex v)
	{
		assert v != null;
		assert this.invariant();
		
		
		if (v.owner != this)
		{
			return false;
		}
		
		return v.delete();
	}
	
	/**
	 * Finds vertex at specified coordinates and deletes it from graph.
	 * If this graph does not contain specified vertex, no changes will be made.
	 *
	 * @return true if the graph changed as a result of the call.
	 */
	public boolean deleteVertex(int x, int y)
	{
		assert this.invariant();
		
		
		Vertex v = this.getVertexAt(x, y);
		
		if (v == null)
		{
			return false;
		}
		else
		{
			return v.delete();
		}
	}
	
	/**
	 * Finds two vertices at specified coordinates and
	 * adds edge connecting them. If this graph does not contain any
	 * of specified vertices or if they are adjacent, no changes will be made.
	 * 
	 * @return true if the graph changed as a result of the call.
	 */
	public boolean addEdge(int x1, int y1, int x2, int y2)
	{
		assert this.invariant();
		
		
		Vertex v = this.getVertexAt(x1, y1);
		
		if (v == null)
		{
			return false;
		}
		else
		{
			return v.addNeighbor(x2, y2);
		}
	}
	
	/**
	 * Finds two vertices at specified coordinates and checks if they are adjacent.
	 * 
	 * @return true if this graph contains both vertices and they are adjacent.
	 */
	public boolean areVerticesAdjacent(int x1, int y1, int x2, int y2)
	{
		Vertex v1 = this.getVertexAt(x1, y1);
		
		if (v1 == null)
		{
			return false;
		}
		
		Vertex v2 = this.getVertexAt(x2, y2);
		
		if (v2 == null)
		{
			return false;
		}
		else
		{
			return v1.isAdjacentTo(v2);
		}
	}
	
	/**
	 * Finds two vertices at specified coordinates and deletes edge connecting them.
	 * If this graph does not contain any of the vertices or if they are not adjacent, no changes will be made.
	 * 
	 * @return true if the graph changed as a result of the call.
	 */
	public boolean deleteEdge(int x1, int y1, int x2, int y2)
	{
		assert this.invariant();
		
		
		Vertex v = this.getVertexAt(x1, y1);
		
		if (v == null)
		{
			return false;
		}
		else
		{
			return v.deleteNeighbor(x2, y2);
		}
	}
	
	public int getVertexCount()
	{
		return this.vertices.size();
	}
	
	public int getEdgeCount()
	{
		return this.edgeCount;
	}
	
	/**
	 * Returns iterator through all vertices in this graph.
	 * 
	 * Iterator is invalidated and behaviour of its methods is undefined if after creation of iterator:
	 * 		* vertices are added to or deleted from graph
	 * 		* setLocation() is called on any vertex of graph
	 * Iterator is not invalidated if after creation of iterator:
	 * 		* edges are added to or deleted from graph
	 */
	public VertexIterator getVertexIterator()
	{
		return new VertexIterator(this.vertices.keySet().iterator());
	}
	
	/**
	 * Returns iterator through all edges of this graph.
	 * 
	 * Iterator is invalidated and behaviour of its methods is undefined if after creation of iterator:
	 * 		* vertices or edges are added to or deleted from graph
	 * 		* setLocation() is called on any vertex of graph
	 */
	public UnorientedEdgeIterator getEdgeIterator()
	{
		return new UnorientedEdgeIterator(this);
	}
	
	/**
	 * Finds vertex at specified coordinates and returns iterator through its neighbors.
	 * 
	 * Iterator is invalidated and behaviour of its methods is undefined if after creation of iterator:
	 * 		* vertices or edges are added to or deleted from graph
	 * Iterator is not invalidated if after creation of iterator:
	 * 		* setLocation() is called on any vertex of graph
	 *
	 * @return Iterator or null if vertex at given coordinates does not exist.
	 */
	public VertexIterator getNeighborIterator(int x, int y)
	{
		Vertex v = this.getVertexAt(x, y);
		
		if (v == null)
		{
			return null;
		}
		else
		{
			VertexIterator it = v.getNeighborIterator();
			assert it != null;
			return it;
		}
	}
	
	public Set <Vertex> getVertices()
	{
		VertexIterator it = this.getVertexIterator();
		Vertex vertex;
		Set <Vertex> vertices = new HashSet <Vertex> ();
		
		while (it.hasNext())
		{
			vertex = it.next();
			vertices.add(vertex);
		}
		
		return vertices;
	}
	
	public Set <UnorientedEdge> getEdges()
	{
		UnorientedEdgeIterator it = new UnorientedEdgeIterator(this);
		UnorientedEdge edge;
		Set <UnorientedEdge> edges = new HashSet <UnorientedEdge> ();
		
		while (it.hasNext())
		{
			edge = it.next();
			edges.add(edge);
		}
		
		return edges;
	}
}
