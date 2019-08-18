
package eugenejonas.pixelmaster.modules.spherical_wave.domain;


import eugenejonas.pixelmaster.core.api.domain.*;


/**
 * Bresenham's line algorithm.
 * 
 * @see <a href="http://algolist.manual.ru/graphics/painting/line.php">Генерация отрезка</a>
 */
public final class BresenhamAlgorithm
{
	/**
	 * Draws line from point (x1; y1) to (x2; y2).
	 * 
	 * @param color Color of the line in format 0x00rrggbb.
	 */
	public static void drawLine(RasterImage image, int x1, int y1, int x2, int y2, int color)
	{
		assert
			image != null
			&& image.getWidth() > x1 && x1 >= 0
			&& image.getWidth() > x2 && x2 >= 0
			&& image.getHeight() > y1 && y1 >= 0
			&& image.getHeight() > y2 && y2 >= 0
			&& (color & 0xff000000) == 0;
		
		
		int
			width = image.getWidth(),
			height = image.getHeight(),
			size = width * height;
		
		int dx, dy, s, sx, sy, kl, swap, incr1, incr2;
		
		
		
		//Вычисление приращений и шагов
		
		sx = 0;
		
		if ((dx = x2 - x1) < 0)
		{
			dx = -dx;
			--sx;
		}
		else if (dx > 0)
		{
			++sx;
		}
		
		sy = 0;
		
		if ((dy = y2 - y1) < 0)
		{
			dy = -dy;
			--sy;
		}
		else if (dy > 0)
		{
			++sy;
		}
		
		// Учёт наклона
		
		swap = 0;
		
		if ((kl = dx) < (s = dy))
		{
			dx = s;
			dy = kl;
			kl = s;
			++swap;
		}
		
		s = (incr1 = 2 * dy) - dx;		// incr1 - константа перевычисления
		
		//разности если текущее s < 0 и
		//s - начальное значение разности
		incr2 = 2 * dx;			// Константа для перевычисления
		// разности если текущее s >= 0
		
		image.setRgb(x1, y1, color);		//Первый пиксел вектора
		
		while (--kl >= 0)
		{
			if (s >= 0)
			{
				if (swap != 0)
				{
					x1 += sx;
				}
				else
				{
					y1 += sy;
				}
				
				s -= incr2;
			}
			
			if (swap != 0)
			{
				y1 += sy;
			}
			else
			{
				x1 += sx;
			}
			
			s += incr1;
			
			image.setRgb(x1, y1, color);		//Текущая точка вектора
		}
	}
}
