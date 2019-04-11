
package pixelmaster.modules.spherical_wave.domain;


import pixelmaster.core.api.domain.*;

import org.junit.*;


public class UnitTest_CoordVector
{
	private CoordVector vector;
	
	
	@Test
	public void test_constructor_1()
	{
		this.vector = new CoordVector(3, 4);
		
		double[] expectedElements = {3, 4};
		double[] expectedSquares = {9, 16};
		
		Assert.assertArrayEquals(expectedElements, this.vector.elements, Misc.PRECISION);
		Assert.assertArrayEquals(expectedSquares, this.vector.squares, Misc.PRECISION);
	}
	
	@Test
	public void test_constructor_2()
	{
		this.vector = new CoordVector(3);
		
		double[] expectedElements = {0, 0, 0};
		double[] expectedSquares = {0, 0, 0};
		
		Assert.assertArrayEquals(expectedElements, this.vector.elements, Misc.PRECISION);
		Assert.assertArrayEquals(expectedSquares, this.vector.squares, Misc.PRECISION);
	}
	
	@Test
	public void test_constructor_3()
	{
		this.vector = new CoordVector(4, 0.5, -8, 5, 2, 0);
		
		double[] expectedElements = {4, 0.5, -8, 5, 2, 0};
		double[] expectedSquares = {16, 0.25, 64, 25, 4, 0};
		
		Assert.assertArrayEquals(expectedElements, this.vector.elements, Misc.PRECISION);
		Assert.assertArrayEquals(expectedSquares, this.vector.squares, Misc.PRECISION);
	}
	
	@Test
	public void test_setElement_getElement()
	{
		this.vector = new CoordVector(3);
		this.vector.setElement(2, 10.5);
		
		Assert.assertEquals(10.5, this.vector.getElement(2), Misc.PRECISION);
		Assert.assertEquals(110.25, this.vector.squares[2], Misc.PRECISION);
	}
	
	@Test
	public void test_calculateScalarProduct()
	{
		this.vector = new CoordVector(4, 5);
		
		CoordVector v = new CoordVector(-2, 3);
		
		Assert.assertEquals(7, CoordVector.calculateScalarProduct(this.vector, v), Misc.PRECISION);
	}
}
