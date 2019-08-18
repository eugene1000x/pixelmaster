
package eugenejonas.pixelmaster.modules.spherical_wave.domain.graph;


import java.io.*;
import java.util.*;

import org.junit.*;


public class UnitTest_UnorientedEdgeIterator
{
	private UnorientedGraph graph;
	
	
	@Test
	public void test1() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph1.in"));
		
		Set <UnorientedEdge> expected = new HashSet <UnorientedEdge> ();
		Set <UnorientedEdge> actual = new IteratorCollector <UnorientedEdge> (this.graph.getEdgeIterator()).getElements();
		
		expected.add(new UnorientedEdge(new Vertex(1, 1), new Vertex(2, 2)));
		expected.add(new UnorientedEdge(new Vertex(2, 2), new Vertex(3, 3)));
		expected.add(new UnorientedEdge(new Vertex(2, 2), new Vertex(4, 4)));
		expected.add(new UnorientedEdge(new Vertex(3, 3), new Vertex(4, 4)));
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void test2() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph4.in"));
		
		Set <UnorientedEdge> actual = new IteratorCollector <UnorientedEdge> (this.graph.getEdgeIterator()).getElements();
		
		Assert.assertTrue(actual.isEmpty());
	}
	
	@Test
	public void test3() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph6.in"));
		
		Set <UnorientedEdge> actual = new IteratorCollector <UnorientedEdge> (this.graph.getEdgeIterator()).getElements();
		
		Assert.assertTrue(actual.isEmpty());
	}
}
