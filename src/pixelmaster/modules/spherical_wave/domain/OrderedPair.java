
package pixelmaster.modules.spherical_wave.domain;


/**
 * Class representing ordered pair of objects (can be null).
 * 
 * @param <TPL_OrderedPair_T1> Type of first element.
 * @param <TPL_OrderedPair_T2> Type of second element.
 */
public final class OrderedPair <TPL_OrderedPair_T1, TPL_OrderedPair_T2>
{
	public TPL_OrderedPair_T1 first;
	public TPL_OrderedPair_T2 second;
	
	
	/**
	 * Creates new pair where both elements are null.
	 */
	public OrderedPair()
	{
		//nothing
	}
	
	public OrderedPair(TPL_OrderedPair_T1 first, TPL_OrderedPair_T2 second)
	{
		this.first = first;
		this.second = second;
	}
	
	@Override
	public String toString()
	{
		return "{"+ (this.first == null ? "null" : this.first.toString()) +"; "+ (this.second == null ? "null" : this.second.toString()) +"}";
	}
	
	/**
	 * Two instances of OrderedPair are equal if their elements (pointers) point to the same objects.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof OrderedPair))
		{
			return super.equals(obj);
		}
		
		OrderedPair other = (OrderedPair) obj;
		
		return this.first == other.first && this.second == other.second;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 37 * hash + (this.first != null ? this.first.hashCode() : 0) + (this.second != null ? this.second.hashCode() : 0);
		return hash;
	}
}
