
package pixelmaster.core.api.domain;


public final class Misc
{
	/**
	 * Precision that can be used for real number comparison.
	 */
	public static final double PRECISION = 0.000000000001;

	
	/**
	 * Returns file extension
	 * (for example, "filename.abc.def" => ".def", "filename" => null, "filename." => ".", "filename.." => ".", ".ext" => ".ext").
	 * 
	 * @param filePath File path.
	 * @return File extension (null if filePath does not contain character '.').
	 */
	public static String getFileExtension(String filePath)
	{
		assert filePath != null;

		
		int dotIndex = filePath.lastIndexOf('.');
		
		if (dotIndex == -1)
		{
			return null;
		}
		else
		{
			return filePath.substring(dotIndex);
		}
	}
}
