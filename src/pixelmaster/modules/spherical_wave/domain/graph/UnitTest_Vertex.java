
package pixelmaster.modules.spherical_wave.domain.graph;


import java.io.*;

import org.junit.*;


public class UnitTest_Vertex
{
	private UnorientedGraph graph;
	
	
	@Test
	public void test_equals_1()
	{
		Vertex v1 = new Vertex(9, 16), v2 = new Vertex(20, 10);
		boolean b = v1.equals(v2);
		Assert.assertFalse(b);
	}
	
	@Test
	public void test_equals_2()
	{
		Vertex v1 = new Vertex(9, 16);
		boolean b = v1.equals(new Object());
		Assert.assertFalse(b);
	}
	
	@Test
	public void test_isAdjacentTo() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph2.in"));
		Assert.assertTrue(this.graph.getVertexAt(3, 3).isAdjacentTo(this.graph.getVertexAt(5, 5)));
	}
	
	@Test
	public void test_getDegree() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph1.in"));
		Assert.assertEquals(1, this.graph.getVertexAt(1, 1).getDegree());
	}
	
	@Test
	public void test_setLocation() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph1.in"));
		boolean b = this.graph.getVertexAt(2, 2).setLocation(9, 9);
		Assert.assertTrue(b);
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph17.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_delete() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph3.in"));
		Assert.assertTrue(this.graph.getVertexAt(4, 4).delete());
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph18.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_merge_1() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph2.in"));
		Assert.assertTrue(this.graph.getVertexAt(3, 3).merge(this.graph.getVertexAt(5, 5), this.graph.getVertexAt(7, 7)));
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph16.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_merge_2() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph3.in"));
		Assert.assertTrue(this.graph.getVertexAt(0, 0).merge(this.graph.getVertexAt(4, 4), this.graph.getVertexAt(1, 1), this.graph.getVertexAt(3, 3)));
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph11.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_merge_3() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph3.in"));
		Assert.assertFalse(this.graph.getVertexAt(0, 0).merge(this.graph.getVertexAt(4, 4), this.graph.getVertexAt(1, 1), new Vertex(3, 3)));
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph3.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_merge_4() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph1.in"));
		Assert.assertTrue(this.graph.getVertexAt(4, 4).merge(this.graph.getVertexAt(4, 4), this.graph.getVertexAt(1, 1)));
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph12.in"));
		Assert.assertEquals(expected, this.graph);
	}
}
