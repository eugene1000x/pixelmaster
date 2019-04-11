
package pixelmaster.modules.spherical_wave.domain.graph;


import java.io.*;
import java.util.*;

import org.junit.*;


public class UnitTest_UnorientedGraph
{
	private UnorientedGraph graph;
	
	
	@Test
	public void test_clone() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph1.in"));
		
		UnorientedGraph actual = (UnorientedGraph) this.graph.clone();
		
		Assert.assertEquals(this.graph, actual);
	}
	
	@Test
	public void test_getVertexAt() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph1.in"));
		
		Assert.assertEquals(new Vertex(1, 1), this.graph.getVertexAt(1, 1));
	}
	
	@Test
	public void test_getNeighborIterator() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph2.in"));
		
		Set <Vertex> actual = new IteratorCollector <Vertex> (this.graph.getNeighborIterator(3, 3)).getElements();
		Set <Vertex> expected = new HashSet <Vertex> ();
		
		expected.add(new Vertex(5, 5));
		expected.add(new Vertex(2, 2));
		expected.add(new Vertex(6, 6));
		expected.add(new Vertex(7, 7));
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void test_getEdgeCount() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph2.in"));
		
		Assert.assertEquals(11, this.graph.getEdgeCount());
	}
	
	@Test
	public void test_getEdges() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph5.in"));
		
		Set <UnorientedEdge> expected = new HashSet <UnorientedEdge> ();
		
		Vertex v1 = new Vertex(1, 1);
		Vertex v2 = new Vertex(2, 2);
		Vertex v3 = new Vertex(3, 3);
		Vertex v4 = new Vertex(4, 4);
		
		expected.add(new UnorientedEdge(v1, v4));
		expected.add(new UnorientedEdge(v2, v4));
		expected.add(new UnorientedEdge(v3, v4));
		
		Assert.assertEquals(expected, this.graph.getEdges());
	}
	
	@Test
	public void test_getVertices() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph5.in"));
		
		Set <Vertex> expected = new HashSet <Vertex> ();
		
		Vertex v1 = new Vertex(1, 1);
		Vertex v2 = new Vertex(2, 2);
		Vertex v3 = new Vertex(3, 3);
		Vertex v4 = new Vertex(4, 4);
		
		expected.add(v1);
		expected.add(v2);
		expected.add(v3);
		expected.add(v4);
		
		Assert.assertEquals(expected, this.graph.getVertices());
	}
	
	@Test
	public void test_addEdge() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph2.in"));
		
		// There is no vertex at coordinates (1, 2).
		boolean res = this.graph.addEdge(1, 2, 1, 1);
		Assert.assertFalse(res);
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph2.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_deleteVertex() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph5.in"));
		
		boolean res = this.graph.deleteVertex(4, 4);
		Assert.assertTrue(res);
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph4.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_clear() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph3.in"));
		this.graph.clear();
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph6.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_addVertex() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph4.in"));
		//vertex already exists at these coordinates
		Assert.assertNull(this.graph.addVertex(1, 1));
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph4.in"));
		Assert.assertEquals(expected, this.graph);
	}
}
