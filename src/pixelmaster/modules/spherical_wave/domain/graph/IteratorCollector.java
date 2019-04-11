
package pixelmaster.modules.spherical_wave.domain.graph;


import java.util.*;


public final class IteratorCollector <TPL_IteratorCollector_T>
{
	private Set <TPL_IteratorCollector_T> elements = new HashSet <TPL_IteratorCollector_T> ();
	
	
	IteratorCollector(Iterator <TPL_IteratorCollector_T> it)
	{
		while (it.hasNext())
		{
			TPL_IteratorCollector_T element = it.next();
			this.elements.add(element);
		}
	}
	
	Set <TPL_IteratorCollector_T> getElements()
	{
		return this.elements;
	}
}
