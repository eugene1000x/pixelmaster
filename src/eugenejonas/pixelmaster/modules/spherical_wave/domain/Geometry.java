
package eugenejonas.pixelmaster.modules.spherical_wave.domain;


import eugenejonas.pixelmaster.modules.spherical_wave.domain.graph.*;

import java.awt.*;
import java.util.*;


/**
 * Analytical geometry functionality.
 */
public final class Geometry
{
	/**
	 * These functions return cosinus of angle between lines that are parallel to vectors (qp) and (qr).
	 * 
	 * @return Value in range [0..1] (since angle value lies in range [0..90 degrees]).
	 */
	public static double calculateCosOfAngleBetweenLines(Point p, Point q, Point r)
	{
		return Geometry.calculateCosOfAngleBetweenLines(p.x, p.y, q.x, q.y, r.x, r.y);
	}
	public static double calculateCosOfAngleBetweenLines(Vertex p, Vertex q, Vertex r)
	{
		return Geometry.calculateCosOfAngleBetweenLines(p.getX(), p.getY(), q.getX(), q.getY(), r.getX(), r.getY());
	}
	public static double calculateCosOfAngleBetweenLines(double px, double py, double qx, double qy, double rx, double ry)
	{
		return Math.abs(Geometry.calculateCosOfAngleBetweenVectors(px, py, qx, qy, rx, ry));
	}
	
	/**
	 * These functions return cosinus of angle between vectors (qp) and (qr).
	 * 
	 * @return Value in range [-1..1] (since angle value lies in range [0..180 degrees]).
	 */
	public static double calculateCosOfAngleBetweenVectors(Point p, Point q, Point r)
	{
		return Geometry.calculateCosOfAngleBetweenVectors(p.x, p.y, q.x, q.y, r.x, r.y);
	}
	public static double calculateCosOfAngleBetweenVectors(Vertex p, Vertex q, Vertex r)
	{
		return Geometry.calculateCosOfAngleBetweenVectors(p.getX(), p.getY(), q.getX(), q.getY(), r.getX(), r.getY());
	}
	public static double calculateCosOfAngleBetweenVectors(double px, double py, double qx, double qy, double rx, double ry)
	{
		CoordVector qp = new CoordVector(px - qx, py - qy);
		CoordVector qr = new CoordVector(rx - qx, ry - qy);
		
		double
			qpLen = qp.getLength(),
			qrLen = qr.getLength();
		
		assert qpLen != 0 && qrLen != 0;
		
		double product = CoordVector.calculateScalarProduct(qp, qr);
		
		return product / (qpLen * qrLen);
	}
	
	/**
	 * Returns distance from point a to line that passes through points b and c.
	 */
	public static double calculateDistanceFromLineToPoint(Vertex a, Vertex b, Vertex c)
	{
		assert b != null && c != null && !b.equals(c);
		
		
		double
			n1 = c.getY() - b.getY(),
			n2 = b.getX() - c.getX(),
			n3 = -b.getX() * c.getY() + b.getX() * b.getY() + b.getY() * c.getX() - b.getX() * b.getY();
		
		double sqrt = Math.hypot(n1, n2);
		
		return Math.abs(n1 * a.getX() + n2 * a.getY() + n3) / sqrt;
	}
	
	/**
	 * Returns variance which is defined as sum of squares of distances from line
	 * connecting first and last vertices in <code>vector</code> to every
	 * inner vertex of <code>vector</code>, divided by [number of elements in <code>vector</code> - 1].
	 */
	public static double calculateVariance(Vector <Vertex> vector)
	{
		assert vector != null && vector.size() > 1;
		
		
		double sum = 0;
		Vertex last = vector.lastElement();
		
		assert last != null;
		
		Iterator <Vertex> it = vector.iterator();
		
		Vertex first = it.next();
		
		assert first != null;
		assert !first.equals(last);
		
		while (it.hasNext())
		{
			Vertex v = it.next();
			
			assert v != null;
			
			if (!it.hasNext())
			{
				break;
			}
			
			double dist = Geometry.calculateDistanceFromLineToPoint(v, first, last);
			sum += dist * dist;
		}
		
		return sum / (vector.size() - 1);
	}
	
	public static double calculateDistance(Vertex v1, Vertex v2)
	{
		int
			dx = v1.getX() - v2.getX(),
			dy = v1.getY() - v2.getY();
		
		return Math.sqrt(dx * dx + dy * dy);
	}
}
