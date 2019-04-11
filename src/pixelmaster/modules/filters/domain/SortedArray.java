
package pixelmaster.modules.filters.domain;


import java.util.*;


/**
 * Class representing sorted array of constant size. Keeps track of the order in
 * which elements were added and allows to replace the oldest element in the
 * array with specified element.
 */
public final class SortedArray
{
	/**
	 * Array is sorted in ascending order.
	 */
	/*private*/ int[] arr;
	
	/**
	 * Elements in deque are queued in the same order as they were added to this array:
	 * top element in deque was added before all other elements.
	 */
	/*private*/ ArrayDeque <Integer> deque;
	
	/**
	 * Sum of elements in the array.
	 */
	private int sum;
	
	
	/**
	 * Constructs array with specified length filled with 0.
	 * 
	 * @param len Length of the array. Length cannot be changed after array is created.
	 */
	public SortedArray(int len)
	{
		assert len > 0;
		
		
		this.arr = new int[len];
		this.deque = new ArrayDeque <Integer> (len);
		
		for (int i = 0; i < len; i++)
		{
			this.deque.add(0);
		}
		
		this.sum = 0;
		
		
		assert this.invariant();
	}
	
	private boolean invariant()
	{
		assert this.arr != null && this.deque != null && this.arr.length > 0 && this.arr.length == this.deque.size();
		
		for (int i = 1; i < this.arr.length; i++)
		{
			assert this.arr[i] >= this.arr[i - 1];
		}
		
		for (int i = 0; i < this.arr.length; i++)
		{
			assert this.deque.contains(this.arr[i]);
		}
		
		for (Integer i: this.deque)
		{
			assert Arrays.binarySearch(this.arr, i.intValue()) >= 0;
		}
		
		int expectedSum = 0;
		
		for (int i = 0; i < this.arr.length; i++)
		{
			expectedSum += this.arr[i];
		}
		
		assert expectedSum == this.sum;
		
		return true;
	}
	
	public int getElement(int pos)
	{
		return this.arr[pos];
	}
	
	/**
	 * Returns median - element at index length / 2.
	 */
	public int getMedian()
	{
		return this.arr[this.arr.length >>> 1];
	}
	
	/**
	 * Returns average of elements.
	 */
	public double getAverage()
	{
		return ((double) this.sum) / this.arr.length;
	}
	
	public int getLength()
	{
		return this.arr.length;
	}
	
	/**
	 * Initializes the array with [width * height] elements from
	 * <code>buffer</code>, which represents a 2D array of pixels.
	 * Elements are taken from rectangular area, whose left top pixel has
	 * index <code>start</code>. Element values are shifted by
	 * <code>offset</code> bits to the right and then mask 0x000000ff is applied.
	 * Elements are added in following order:
	 *			1 2 3 4
	 *			5 6 7 8
	 *
	 * @param buffer Source buffer.
	 * @param start Index of left top pixel in buffer.
	 * @param stepSize Distance between one row of pixels and next row
	 *		in <code>buffer</code> (width of image).
	 * @param width Width of rectangular area of pixels.
	 * @param height Height of rectangular area of pixels.
	 * @param offset Specifies color channel (0, 8 or 16).
	 */
	public void init(int[] buffer, int start, int stepSize, int width, int height, int offset)
	{
		assert buffer != null && start >= 0 && width > 0 && height > 0;
		assert offset == 0 || offset == 8 || offset == 16;
		assert start + this.arr.length <= buffer.length;
		assert buffer.length >= start + height * stepSize;
		assert stepSize >= width;
		assert width * height == this.getLength();
		
		assert this.invariant();
		
		
		this.deque.clear();
		this.sum = 0;
		
		int arrIndex = 0;
		
		for (int i = 0; i < height; i++, start += stepSize)
		{
			for (int j = 0, pixelIndex = start; j < width; j++, pixelIndex++)
			{
				int value = (buffer[pixelIndex] >> offset) & 255;
				
				this.arr[arrIndex++] = value;
				this.deque.add(value);
				this.sum += value;
			}
		}
		
		assert this.arr.length == arrIndex;
		
		Arrays.sort(this.arr);
		
		
		assert this.invariant();
	}
	
	/**
	 * Replaces oldest element in the array with specified element.
	 */
	public void replaceOldestElement(int newElement)
	{
		assert this.invariant();
		
		
		int oldestElement = this.deque.remove().intValue();
		this.deque.add(newElement);
		this.sum += newElement - oldestElement;
		
		if (oldestElement == newElement)
		{
			return;
		}
		
		int oldestElementIndex = Arrays.binarySearch(this.arr, oldestElement);
		
		assert oldestElementIndex >= 0;		// Array must contain element oldestElement
		
		int newElementIndex = Arrays.binarySearch(this.arr, newElement);
		
		if (newElementIndex < 0)
		{
			newElementIndex = -newElementIndex - 1;
		}
		
		if (oldestElementIndex == newElementIndex || oldestElementIndex + 1 == newElementIndex)
		{
			this.arr[oldestElementIndex] = newElement;
		}
		else if (oldestElementIndex < newElementIndex)
		{
			System.arraycopy(this.arr, oldestElementIndex + 1, this.arr, oldestElementIndex, newElementIndex - oldestElementIndex - 1);
			this.arr[newElementIndex - 1] = newElement;
		}
		else
		{
			// oldestElementIndex > newElementIndex
			System.arraycopy(this.arr, newElementIndex, this.arr, newElementIndex + 1, oldestElementIndex - newElementIndex);
			this.arr[newElementIndex] = newElement;
		}
		
		
		assert this.invariant();
	}
}
