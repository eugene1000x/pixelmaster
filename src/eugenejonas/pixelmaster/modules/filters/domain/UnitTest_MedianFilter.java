
package eugenejonas.pixelmaster.modules.filters.domain;


import eugenejonas.pixelmaster.core.api.domain.*;

import java.io.*;
import javax.imageio.*;

import org.junit.*;


public class UnitTest_MedianFilter
{
	@Test
	public void test_performMedianFiltering_with_radius_1() throws IOException
	{
		RasterImage expected = new RasterImage(740, 515);
		ImageConverter.toRasterImage(ImageIO.read(new File("src/pixelmaster/modules/filters/domain/median_filter_test_data/gray_scale_1_filtered_3rd_party_lib.bmp")), expected);
		RasterImage actual = new RasterImage(740, 515);
		ImageConverter.toRasterImage(ImageIO.read(new File("src/pixelmaster/modules/filters/domain/median_filter_test_data/gray_scale_1_orig.bmp")), actual);
		MedianFilter.performMedianFiltering(actual, actual, 1, 0, null);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void test_performMedianFiltering_with_radius_5() throws IOException
	{
		RasterImage expected = new RasterImage(695, 488);
		ImageConverter.toRasterImage(ImageIO.read(new File("src/pixelmaster/modules/filters/domain/median_filter_test_data/gray_scale_2_filtered_3rd_party_lib.bmp")), expected);
		RasterImage actual = new RasterImage(695, 488);
		ImageConverter.toRasterImage(ImageIO.read(new File("src/pixelmaster/modules/filters/domain/median_filter_test_data/gray_scale_2_orig.bmp")), actual);
		MedianFilter.performMedianFiltering(actual, actual, 5, 0, null);
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void test_performMedianFiltering_with_radius_20() throws IOException
	{
		RasterImage expected = new RasterImage(402, 588);
		ImageConverter.toRasterImage(ImageIO.read(new File("src/pixelmaster/modules/filters/domain/median_filter_test_data/gray_scale_3_filtered_3rd_party_lib.bmp")), expected);
		RasterImage actual = new RasterImage(402, 588);
		ImageConverter.toRasterImage(ImageIO.read(new File("src/pixelmaster/modules/filters/domain/median_filter_test_data/gray_scale_3_orig.bmp")), actual);
		MedianFilter.performMedianFiltering(actual, actual, 20, 0, null);
		
		Assert.assertEquals(expected, actual);
	}
}
