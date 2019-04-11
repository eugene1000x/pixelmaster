
package pixelmaster.modules.spherical_wave.domain;


import pixelmaster.core.api.domain.*;
import pixelmaster.modules.spherical_wave.domain.graph.*;

import java.awt.*;


/**
 * Class representing coordinate vector with specified dimension.
 */
public final class CoordVector
{
	/**
	 * Vector elements.
	 */
	/*private*/ double[] elements;
	
	/**
	 * Cache of squares of vector elements.
	 */
	/*private*/ double[] squares;
	
	
	/**
	 * Creates vector with specified dimension.
	 */
	public CoordVector(int dim)
	{
		assert dim >= 0;
				
		this.elements = new double[dim];
		this.squares = new double[dim];
				
		assert this.invariant();
	}
	
	/**
	 * Creates vector with given coordinates in the same order as they are in <code>coords</code>.
	 * Dimension of the vector is determined by number of elements in given array.
	 */
	public CoordVector(double... coords)
	{
		int len = coords.length;
		
		this.elements = new double[len];
		this.squares = new double[len];
		
		int i = 0;
		
		for (double coord: coords)
		{
			this.elements[i] = coord;
			this.squares[i++] = coord * coord;
		}
		
		
		assert this.invariant();
	}
	
	/**
	 * Creates 2D vector from start point to end point.
	 */
	public CoordVector(Point start, Point end)
	{
		assert start != null && end != null;
		
		
		int len = 2;
		
		this.elements = new double[len];
		this.squares = new double[len];
		this.elements[0] = end.x - start.x;
		this.elements[1] = end.y - start.y;
		this.squares[0] = this.elements[0] * this.elements[0];
		this.squares[1] = this.elements[1] * this.elements[1];
		
		
		assert this.invariant();
	}
	
	/**
	 * Creates 2D vector from start point to end point.
	 */
	public CoordVector(Vertex start, Vertex end)
	{
		this(new Point(start.getX(), start.getY()), new Point(end.getX(), end.getY()));
	}
	
	private boolean invariant()
	{
		assert this.elements != null && this.squares != null;
		assert this.elements.length == this.squares.length;
				
		for (int i = 0; i < this.elements.length; i++)
		{
			assert CoordVector.areEqual(this.elements[i] * this.elements[i], this.squares[i], Misc.PRECISION);
		}
		
		return true;
	}
	
	public double getElement(int index)
	{
		return this.elements[index];
	}
	
	public void setElement(int index, double value)
	{
		assert index >= 0 && index < this.elements.length;
		assert this.invariant();
		
		
		this.elements[index] = value;
		this.squares[index] = value * value;
		
		
		assert this.invariant();
	}
	
	/**
	 * Returns dimension (number of elements) of this vector.
	 */
	public int getDimension()
	{
		return this.elements.length;
	}
	
	/**
	 * Returns length (magnitude) of this vector.
	 */
	public double getLength()
	{
		double sum = 0;
		
		for (int i = 0; i < this.elements.length; i++)
		{
			sum += this.squares[i];
		}
		
		return Math.sqrt(sum);
	}
	
	public static double calculateScalarProduct(CoordVector v1, CoordVector v2)
	{
		assert v1 != null && v2 != null && v1.elements.length == v2.elements.length;
		
		
		double sum = 0;
		int len = v1.elements.length;
		
		for (int i = 0; i < len; i++)
		{
			sum += v1.elements[i] * v2.elements[i];
		}
		
		return sum;
	}
	
	private static boolean areEqual(double a, double b, double eps)
	{
		return Math.abs(a - b) < eps;
	}
}
