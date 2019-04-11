
package pixelmaster.modules.spherical_wave.domain;


import pixelmaster.core.api.domain.*;
import pixelmaster.modules.spherical_wave.domain.graph.*;

import org.junit.*;


public class UnitTest_Geometry
{
	@Test
	public void test_calculateCosOfAngleBetweenLines_two_parallel_lines()
	{
		double actualCos = Geometry.calculateCosOfAngleBetweenLines(1, 1, 2, 2, 3, 3);
		Assert.assertEquals(1, actualCos, Misc.PRECISION);
	}
	
	@Test
	public void test_calculateCosOfAngleBetweenVectors_two_equal_vectors()
	{
		double actualCos = Geometry.calculateCosOfAngleBetweenVectors(1, 1, 2, 2, 1, 1);
		Assert.assertEquals(1, actualCos, Misc.PRECISION);
	}
	
	@Test
	public void test_calculateDistanceFromLineToPoint_1()
	{
		double actualDistance = Geometry.calculateDistanceFromLineToPoint(new Vertex(1, 3), new Vertex(0, 0), new Vertex(4, 4));
		Assert.assertEquals(Math.sqrt(2), actualDistance, Misc.PRECISION);
	}
	
	@Test
	public void test_calculateDistanceFromLineToPoint_2()
	{
		double actualDistance = Geometry.calculateDistanceFromLineToPoint(new Vertex(1, -1), new Vertex(-1, -2), new Vertex(4, -2));
		Assert.assertEquals(1, actualDistance, Misc.PRECISION);
	}
	
	@Test
	public void test_calculateDistanceFromLineToPoint_3()
	{
		double actualDistance = Geometry.calculateDistanceFromLineToPoint(new Vertex(0, 1), new Vertex(0, 2), new Vertex(0, -2));
		Assert.assertEquals(0, actualDistance, Misc.PRECISION);
	}
	
	@Test
	public void test_calculateDistanceFromLineToPoint_4()
	{
		double actualDistance = Geometry.calculateDistanceFromLineToPoint(new Vertex(-2, 3), new Vertex(-2, 3), new Vertex(-1, -2));
		Assert.assertEquals(0, actualDistance, Misc.PRECISION);
	}
}
