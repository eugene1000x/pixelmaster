
package eugenejonas.pixelmaster.modules.spherical_wave.domain.graph;


import java.io.*;
import java.util.*;

import org.junit.*;


public class UnitTest_GraphIo
{
	private UnorientedGraph graph;
	
	
	@Test
	public void test_readUnorientedGraph_positive() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph1.in"));
		
		Set <Vertex> actualVertices = this.graph.getVertices();
		Set <UnorientedEdge> actualEdges = this.graph.getEdges();
		Set <Vertex> expectedVertices = new HashSet <Vertex> ();
		
		expectedVertices.add(new Vertex(1, 1));
		expectedVertices.add(new Vertex(2, 2));
		expectedVertices.add(new Vertex(3, 3));
		expectedVertices.add(new Vertex(4, 4));
		
		Set <UnorientedEdge> expectedEdges = new HashSet <UnorientedEdge> ();
		
		expectedEdges.add(new UnorientedEdge(new Vertex(1, 1), new Vertex(2, 2)));
		expectedEdges.add(new UnorientedEdge(new Vertex(3, 3), new Vertex(2, 2)));
		expectedEdges.add(new UnorientedEdge(new Vertex(4, 4), new Vertex(2, 2)));
		expectedEdges.add(new UnorientedEdge(new Vertex(3, 3), new Vertex(4, 4)));
		
		Assert.assertEquals(expectedEdges, actualEdges);
		Assert.assertEquals(expectedVertices, actualVertices);
	}
	
	@Test
	public void test_readUnorientedGraph__negative__first_vertex_of_second_edge_does_not_exist() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/err1.in"));
		
		Assert.assertNull(this.graph);
	}
	
	@Test
	public void test_readUnorientedGraph__negative__negative_number_of_vertices_given_in_file() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/err2.in"));
		
		Assert.assertNull(this.graph);
	}
	
	@Test
	public void test_readUnorientedGraph__negative__4th_row_contains_only_one_coordinate_instead_of_two() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/err3.in"));
		
		Assert.assertNull(this.graph);
	}
	
	@Test
	public void test_readUnorientedGraph__negative__first_edge_is_the_same_as_second() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/err4.in"));
		
		Assert.assertNull(this.graph);
	}
	
	@Test
	public void test_writeUnorientedGraph_1() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph1.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_2() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph2.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_3() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph3.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_4() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph4.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_5() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph5.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_6() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph6.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_7() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph7.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_8() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph8.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_9() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph9.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_10() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph10.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_11() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph11.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_12() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph12.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_13() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph13.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_14() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph14.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_15() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph15.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_16() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph16.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_17() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph17.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
	
	@Test
	public void test_writeUnorientedGraph_18() throws IOException
	{
		UnorientedGraph expectedGraph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph18.in"));
		File tmp = File.createTempFile("test", null);
		GraphIo.writeUnorientedGraph(expectedGraph, tmp);
		UnorientedGraph actualGraph = GraphIo.readUnorientedGraph(tmp);
		
		Assert.assertEquals(expectedGraph, actualGraph);
	}
}
