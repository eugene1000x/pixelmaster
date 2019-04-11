
package pixelmaster.modules.spherical_wave.domain.graph;


import java.util.*;


public final class VertexIterator implements Iterator <Vertex>
{
	/**
	 * Encapsulated iterator.
	 */
	private Iterator <Vertex> iterator;
	
	
	/**
	 * @param it Encapsulated iterator.
	 */
	VertexIterator(Iterator <Vertex> it)
	{
		assert it != null;
		
		this.iterator = it;
	}
	
	@Override
	public boolean hasNext()
	{
		return this.iterator.hasNext();
	}
	
	@Override
	public Vertex next()
	{
		if (!this.hasNext())
		{
			throw new NoSuchElementException();
		}
		else
		{
			return this.iterator.next();
		}
	}
}
