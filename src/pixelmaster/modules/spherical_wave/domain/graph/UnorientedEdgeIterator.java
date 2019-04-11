
package pixelmaster.modules.spherical_wave.domain.graph;


import java.util.*;


/**
 * Class representing iterator through edges of UnorientedGraph.
 * Since in unoriented graph edges [v1, v2] and [v2, v1] are equivalent,
 * iterator will return just one edge connecting every two adjacent vertices -
 * either [v1, v2] or [v2, v1], but not both.
 */
public final class UnorientedEdgeIterator implements Iterator <UnorientedEdge>
{
	/**
	 * Vertices that have been iterated through.
	 */
	private Set <Vertex> iterated;
	
	/**
	 * Iterator that points to the next vertex which has not been iterated through.
	 */
	private VertexIterator iterator;
	
	/**
	 * Iterator that points to the next neighbor of vertex1.
	 */
	private VertexIterator neighborIterator;
	
	private Vertex vertex1, vertex2;
	
	
	UnorientedEdgeIterator(UnorientedGraph graph)
	{
		this.iterated = new HashSet <Vertex> ();
		this.iterator = graph.getVertexIterator();
		
		if (!this.iterator.hasNext())
		{
			this.vertex1 = null;
			this.vertex2 = null;
			
			return;
		}
		
		this.vertex1 = this.iterator.next();
		this.neighborIterator = this.vertex1.getNeighborIterator();
		
		while (!this.neighborIterator.hasNext())
		{
			if (!this.iterator.hasNext())
			{
				this.vertex1 = null;
				this.vertex2 = null;
				
				return;
			}
			
			this.vertex1 = this.iterator.next();
			this.neighborIterator = this.vertex1.getNeighborIterator();
		}
		
		this.vertex2 = this.neighborIterator.next();
	}
	
	@Override
	public boolean hasNext()
	{
		return this.vertex1 != null && this.vertex2 != null;
	}
	
	@Override
	public UnorientedEdge next()
	{
		if (!this.hasNext())
		{
			throw new NoSuchElementException();
		}
		
		
		UnorientedEdge edge = new UnorientedEdge(this.vertex1, this.vertex2);
		
		do
		{
			while (!this.neighborIterator.hasNext())
			{
				this.iterated.add(this.vertex1);
				
				if (!this.iterator.hasNext())
				{
					this.vertex1 = this.vertex2 = null;
					return edge;
				}
				
				this.vertex1 = this.iterator.next();
				this.neighborIterator = this.vertex1.getNeighborIterator();
			}
			
			this.vertex2 = this.neighborIterator.next();
		}
		while (this.iterated.contains(this.vertex2));
		
		return edge;
	}
}
