
package eugenejonas.pixelmaster.modules.filters.domain;


import eugenejonas.pixelmaster.core.api.domain.*;

import java.util.*;

import org.junit.*;


public class UnitTest_SortedArray
{
	private SortedArray arr;
	
	
	@Test
	public void test1()
	{
		this.arr = new SortedArray(7);
		int[] buf = {13, 18, 8, 29, 31, 2, 14};
		int[] expectedArray = {2, 8, 14, 14, 18, 29, 31};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(14);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(14);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(16.57142857142857142, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test2()
	{
		this.arr = new SortedArray(7);
		int[] buf = {13, 18, 8, 29, 31, 2, 14};
		int[] expectedArray = {2, 8, 13, 14, 18, 29, 31};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(13);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(13);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(16.42857142857142857, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test3()
	{
		this.arr = new SortedArray(7);
		int[] buf = {13, 18, 8, 29, 31, 2, 14};
		int[] expectedArray = {2, 8, 8, 14, 18, 29, 31};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(8);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(8);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(15.71428571428571428, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test4()
	{
		this.arr = new SortedArray(7);
		int[] buf = {13, 18, 8, 29, 31, 2, 14};
		int[] expectedArray = {2, 8, 14, 16, 18, 29, 31};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(16);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(16);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(16.85714285714285714, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test5()
	{
		this.arr = new SortedArray(6);
		int[] buf = {15, 8, 2, 15, 2, 2};
		int[] expectedArray = {2, 2, 2, 8, 8, 15};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(8);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(8);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(6.166666666666666666, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test6()
	{
		this.arr = new SortedArray(6);
		int[] buf = {15, 8, 2, 15, 2, 2};
		int[] expectedArray = {2, 2, 2, 8, 15, 15};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(15);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(15);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(7.333333333333333333, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test7()
	{
		this.arr = new SortedArray(6);
		int[] buf = {15, 8, 2, 15, 2, 2};
		int[] expectedArray = {2, 2, 2, 2, 8, 15};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(2);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(2);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(5.16666666666666666, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test8()
	{
		this.arr = new SortedArray(6);
		int[] buf = {15, 8, 2, 15, 2, 2};
		int[] expectedArray = {2, 2, 2, 8, 9, 15};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(9);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(9);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(6.33333333333333333, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test9()
	{
		this.arr = new SortedArray(1);
		int[] buf = {5};
		int[] expectedArray = {5};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(5);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(5);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(5.0, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test10()
	{
		this.arr = new SortedArray(1);
		int[] buf = {5};
		int[] expectedArray = {7};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(7);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(7);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(7.0, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test11()
	{
		this.arr = new SortedArray(1);
		int[] buf = {5};
		int[] expectedArray = {2};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(2);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(2);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(2.0, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test12()
	{
		this.arr = new SortedArray(7);
		int[] buf = {0, 2, 3, 2, 8, 0, 0};
		int[] expectedArray = {0, 0, 2, 2, 3, 8, 16};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(16);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(16);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(4.428571428571428, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test13()
	{
		this.arr = new SortedArray(4);
		int[] buf = {20, 8, 5, 15};
		int[] expectedArray = {5, 8, 15, 30};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(30);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(30);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(14.5, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test14()
	{
		this.arr = new SortedArray(4);
		int[] buf = {20, 8, 5, 15};
		int[] expectedArray = {5, 8, 15, 18};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(18);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(18);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(11.5, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test15()
	{
		this.arr = new SortedArray(4);
		int[] buf = {20, 8, 5, 15};
		int[] expectedArray = {3, 5, 8, 15};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(3);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(3);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(7.75, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test16()
	{
		this.arr = new SortedArray(4);
		int[] buf = {20, 8, 5, 15};
		int[] expectedArray = {5, 8, 13, 15};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(13);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(13);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(10.25, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test17()
	{
		this.arr = new SortedArray(4);
		int[] buf = {20, 8, 5, 15};
		int[] expectedArray = {5, 7, 8, 15};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(7);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(7);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(8.75, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
	
	@Test
	public void test18()
	{
		this.arr = new SortedArray(4);
		int[] buf = {20, 8, 5, 15};
		int[] expectedArray = {-30, 0, 15, 100};
		ArrayDeque <Integer> expectedDeque = new ArrayDeque <Integer> ();
		
		for (int i = 0; i < buf.length; i++)
		{
			expectedDeque.add(buf[i]);
		}
		
		expectedDeque.remove();
		expectedDeque.add(100);
		expectedDeque.remove();
		expectedDeque.add(0);
		expectedDeque.remove();
		expectedDeque.add(-30);
		
		this.arr.init(buf, 0, buf.length, buf.length, 1, 0);
		this.arr.replaceOldestElement(100);
		this.arr.replaceOldestElement(0);
		this.arr.replaceOldestElement(-30);
		
		Assert.assertArrayEquals(expectedArray, this.arr.arr);
		Assert.assertEquals(21.25, this.arr.getAverage(), Misc.PRECISION);
		Assert.assertArrayEquals(expectedDeque.toArray(), this.arr.deque.toArray());
	}
}
