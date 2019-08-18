
package eugenejonas.pixelmaster.modules.spherical_wave.domain.graph;


import java.io.*;
import java.util.*;

import org.junit.*;


public class UnitTest_VertexIterator
{
	private UnorientedGraph graph;
	
	
	@Test
	public void test1() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph1.in"));
		
		Set <Vertex> actual = new IteratorCollector <Vertex> (this.graph.getVertexIterator()).getElements();
		Set <Vertex> expected = new HashSet <Vertex> ();
		
		expected.add(new Vertex(1, 1));
		expected.add(new Vertex(2, 2));
		expected.add(new Vertex(3, 3));
		expected.add(new Vertex(4, 4));
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void test2() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph6.in"));
		
		Set <Vertex> actual = new IteratorCollector <Vertex> (this.graph.getVertexIterator()).getElements();
		
		Assert.assertTrue(actual.isEmpty());
	}
}
