
package eugenejonas.pixelmaster.modules.spherical_wave.domain;


import eugenejonas.pixelmaster.modules.spherical_wave.domain.graph.*;

import java.io.*;

import org.junit.*;


public class UnitTest_Optimization
{
	private UnorientedGraph graph;
	
	
	@Test
	public void test_connectEdges() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph3.in"));
		Optimization.connectEdges(this.graph, 10);
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph13.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_cutTails() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph5.in"));
		Optimization.cutTails(this.graph, 2.9);
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph14.in"));
		Assert.assertEquals(expected, this.graph);
	}
	
	@Test
	public void test_performPrimaryOptimization() throws IOException
	{
		this.graph = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph7.in"));
		Optimization.performPrimaryOptimization(this.graph, 1.0);
		
		UnorientedGraph expected = GraphIo.readUnorientedGraph(new File("src/pixelmaster/modules/spherical_wave/domain/spherical_wave_test_data/graph15.in"));
		Assert.assertEquals(expected, this.graph);
	}
}
