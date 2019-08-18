
package eugenejonas.pixelmaster.modules.spherical_wave.domain.graph;


import eugenejonas.pixelmaster.modules.spherical_wave.domain.*;

import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * Class representing vertex of unoriented graph. Vertex belongs to 0 or 1 graph.
 * After vertex is added to graph, it can be deleted, but it cannot be
 * added to another graph. After vertex is deleted from graph, it
 * cannot be added to any graph.
 */
public final class Vertex
{
	/**
	 * Coordinates.
	 */
	private int x, y;
	
	/**
	 * Pointer to graph that contains this vertex. null value means
	 * this vertex does not belong to a graph.
	 */
	UnorientedGraph owner;
	
	/**
	 * null value means this vertex does not belong to a graph.
	 *
	 * Using List instead of HashSet so that it is possible to change neighbor's coordinates
	 * without corrupting data structure or invalidating iterators (coordinates affect keys, used
	 * internally by HashSet).
	 */
	List <Vertex> neighbors;
	
	
	/**
	 * Creates vertex that does not belong to a graph.
	 */
	public Vertex(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.owner = null;
		this.neighbors = null;
		
		
		assert this.invariant();
	}
	
	private boolean invariant()
	{
		assert (this.owner == null) == (this.neighbors == null);
		
		if (this.neighbors != null)
		{
			assert !this.neighbors.contains(this);
			assert this.owner.vertices.contains(this);
			
			// uniqueness of elements in list
			for (int i = 0; i < this.neighbors.size(); ++i)
			{
				assert !this.neighbors.subList(0, i).contains(this.neighbors.get(i));
			}
			
			for (Vertex v: this.neighbors)
			{
				assert v.neighbors.contains(this);
				assert v.owner == this.owner;
			}
		}
		
		return true;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	@Override
	public int hashCode()
	{
		long bits = java.lang.Double.doubleToLongBits(this.x);
		bits ^= java.lang.Double.doubleToLongBits(this.y) * 31;
		return ((int) bits) ^ ((int) (bits >> 32));
	}
	
	/**
	 * Two instances of Vertex are equal if they have the same coordinates.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Vertex))
		{
			return super.equals(obj);
		}
		
		Vertex other = (Vertex) obj;
		
		return this.x == other.x && this.y == other.y;
	}
	
	@Override
	public String toString()
	{
		return new OrderedPair <Integer, Integer> (this.x, this.y).toString();
	}
	
	public Point getLocation()
	{
		return new Point(this.x, this.y);
	}
	
	/**
	 * Changes coordinates of this vertex. If graph already contains vertex at
	 * specified coordinates, no changes will be made. If this vertex does not belong to a
	 * graph, no changes will be made.
	 *
	 * @return true if this vertex changed as a result of the call.
	 */
	public boolean setLocation(int x, int y)
	{
		assert this.invariant();
		
		
		if (this.owner == null)
		{
			return false;
		}
		
		if (this.owner.getVertexAt(x, y) != null)
		{
			return false;
		}
		
		// Coordinates of vertex cannot change while vertex is in Hashtable,
		// because they are used to calculate key used internally by Hashtable.
		// If this key changes, it would corrupt data structure.
		this.owner.vertices.remove(this);
		this.x = x;
		this.y = y;
		this.owner.vertices.put(this, this);
		
		
		assert this.invariant();
		return true;
	}
	
	/**
	 * Returns degree of this vertex (number of edges that connect to it).
	 * 
	 * @return Degree of the vertex, or -1 if this vertex does not belong to a graph.
	 */
	public int getDegree()
	{
		if (this.owner == null)
		{
			return -1;
		}
		else
		{
			return this.neighbors.size();
		}
	}
	
	/**
	 * Adds neighbor to this vertex. If specified vertex belongs to
	 * another graph or it is already adjacent to this vertex, no changes will be made.
	 * If this vertex does not belong to a graph, no changes will be made.
	 * If trying to add vertex as neighbor to itself, no changes will be made.
	 * 
	 * @return true if neighbor list changed as a result of the call.
	 */
	public boolean addNeighbor(Vertex neighbor)
	{
		assert neighbor != null;
		assert this.invariant();
		
		
		if (this.owner == null)
		{
			return false;
		}
		
		if (neighbor == this)
		{
			return false;
		}
		
		if (neighbor.owner != this.owner)
		{
			return false;
		}
		
		if (this.neighbors.contains(neighbor))
		{
			return false;
		}
		
		this.neighbors.add(neighbor);
		
		neighbor.neighbors.add(this);
		
		this.owner.edgeCount++;
		
		
		assert invariant();
		return true;
	}
	
	/**
	 * Adds neighbor to this vertex. If graph does not contain vertex at
	 * specified coordinates or it is already adjacent to this vertex, no changes will
	 * be made. If this vertex does not belong to a graph, no changes will be made.
	 * If trying to add vertex as neighbor to itself, no changes will be made.
	 * 
	 * @return true if neighbor list changed as a result of the call.
	 */
	public boolean addNeighbor(int x, int y)
	{
		assert this.invariant();
		
		
		if (this.owner == null)
		{
			return false;
		}
		
		Vertex v = this.owner.getVertexAt(x, y);
		
		if (v == null)
		{
			return false;
		}
		
		
		assert this.invariant();
		return this.addNeighbor(v);
	}
	
	/**
	 * Checks if this vertex is adjacent to given vertex. If this vertex does not belong to a graph
	 * or if given vertex belongs to another or no graph, returns false.
	 */
	public boolean isAdjacentTo(Vertex v)
	{
		assert v != null;
		
		
		if (this.owner == null)
		{
			return false;
		}
		else
		{
			return v.owner == this.owner && this.neighbors.contains(v);
		}
	}
	
	/**
	 * Deletes edge connecting this vertex with given vertex. If specified vertex belongs to another or no
	 * graph or it is not adjacent to this vertex, no changes will be made.
	 * If this vertex does not belong to a graph, no changes will be made.
	 * 
	 * @return true if neighbor list changed as a result of the call.
	 */
	public boolean deleteNeighbor(Vertex neighbor)
	{
		assert neighbor != null;
		assert this.invariant();
		
		
		if (this.owner == null)
		{
			return false;
		}
		
		if (neighbor.owner != this.owner || !this.neighbors.remove(neighbor))
		{
			return false;
		}
		
		neighbor.neighbors.remove(this);
		this.owner.edgeCount--;
		
		
		assert this.invariant();
		return true;
	}
	
	/**
	 * Deletes edge connecting this vertex with given vertex. If graph does not contain specified
	 * vertex or it is not adjacent to this vertex, no changes will be made.
	 * If this vertex does not belong to a graph, no changes will be made.
	 *
	 * @return true if neighbor list changed as a result of the call.
	 */
	public boolean deleteNeighbor(int x, int y)
	{
		assert this.invariant();
		
		
		if (this.owner == null)
		{
			return false;
		}
		
		Vertex v = this.owner.getVertexAt(x, y);
		
		if (v == null)
		{
			return false;
		}
		else
		{
			return deleteNeighbor(v);
		}
	}
	
	/**
	 * Returns iterator through all neighbors of this vertex.
	 * 
	 * Iterator is invalidated and behaviour of its methods is undefined if after creation of iterator:
	 * 		* vertices or edges are added to or deleted from graph
	 * Iterator is not invalidated if after creation of iterator:
	 * 		* setLocation() is called on any vertex of graph
	 *
	 * @return Neighbor iterator or null if this vertex does not belong to a graph.
	 */
	public VertexIterator getNeighborIterator()
	{
		if (this.owner == null)
		{
			return null;
		}
		else
		{
			return new VertexIterator(this.neighbors.iterator());
		}
	}
	
	/**
	 * Deletes this vertex from graph. If this vertex does not belong to a
	 * graph, no changes will be made.
	 * 
	 * @return true if graph containing this vertex changed as a result of the call.
	 */
	public boolean delete()
	{
		assert this.invariant();
		
		
		if (this.owner == null)
		{
			return false;
		}
		
		Iterator <Vertex> it = this.neighbors.iterator();
		
		while (it.hasNext())
		{
			Vertex v = it.next();
			v.neighbors.remove(this);
			this.owner.edgeCount--;
		}
		
		this.owner.vertices.remove(this);
		this.owner = null;
		this.neighbors = null;
		
		
		assert this.invariant();
		return true;
	}
	
	/**
	 * Merges this vertex with given vertices.
	 * Note that <code>vertices</code> is allowed to contain pointer to this vertex.
	 * If this vertex does not belong to a graph, no changes will be made and false will be returned.
	 * 
	 * @return true if vertices were successfully merged.
	 */
	public boolean merge(Vertex... vertices)
	{
		assert vertices != null;
		assert this.invariant();
		
		
		if (this.owner == null)
		{
			return false;
		}
		
		Set <Vertex> allNeighbors = new HashSet <Vertex> ();
		
		for (Vertex v: vertices)
		{
			if (v.owner != this.owner)
			{
				return false;
			}
			
			allNeighbors.addAll(v.neighbors);
		}
		
		for (Vertex v: allNeighbors)
		{
			this.addNeighbor(v);
		}
		
		for (Vertex v: vertices)
		{
			if (v != this)
			{
				v.delete();
			}
		}
		
		
		assert this.invariant();
		return true;
	}
}
