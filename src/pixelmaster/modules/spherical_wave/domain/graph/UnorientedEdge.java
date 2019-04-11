
package pixelmaster.modules.spherical_wave.domain.graph;


/**
 * Class representing edge of unoriented graph.
 */
public final class UnorientedEdge
{
	public Vertex firstVertex;
	public Vertex secondVertex;
	
	
	public UnorientedEdge(Vertex firstVertex, Vertex secondVertex)
	{
		this.firstVertex = firstVertex;
		this.secondVertex = secondVertex;
		
		assert this.invariant();
	}
	
	private boolean invariant()
	{
		assert this.firstVertex != null && this.secondVertex != null;
		return true;
	}
	
	/**
	 * Two instances of UnorientedEdge are equal if
	 * vertices they connect have the same locations.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof UnorientedEdge))
		{
			return super.equals(obj);
		}
		
		UnorientedEdge other = (UnorientedEdge) obj;
		
		return
			this.firstVertex.equals(other.firstVertex) && this.secondVertex.equals(other.secondVertex)
			|| this.firstVertex.equals(other.secondVertex) && this.secondVertex.equals(other.firstVertex);
	}
	
	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 37 * hash + this.firstVertex.hashCode() + this.secondVertex.hashCode();
		return hash;
	}
	
	@Override
	public String toString()
	{
		return "{"+ this.firstVertex.toString() +", "+ this.secondVertex.toString() +"}";
	}
}
