package jrAlex.core.math.vector;

/**
 * Created on 9/13/2016.
 */

public class Vector2D extends Vector
{
	public static final int X = 0, Y = 1;

	public Vector2D()
	{
		super(2);
	}

	public Vector2D(double x, double y)
	{
		super(new double[]
		{
				x, y
		});
	}
}
