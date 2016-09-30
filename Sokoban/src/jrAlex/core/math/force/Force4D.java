package jrAlex.core.math.force;

import jrAlex.core.math.vector.Vector2D;

public class Force4D extends Force
{
	public Force4D()
	{
		super(4);
	}

	public Force4D(double north, double east, double south, double west)
	{
		super(new double[]
		{
				north, east, south, west
		});
	}

	public void addForce(Direction dir, double value)
	{
		values[dir.index] += value;
	}

	public double getValueForDir(Direction dir)
	{
		return values[dir.index];
	}

	public Vector2D getVel()
	{
		Vector2D answer = new Vector2D(getValueForDir(Direction.NORTH), getValueForDir(Direction.EAST));
		answer.sub(new Vector2D(getValueForDir(Direction.SOUTH), getValueForDir(Direction.WEST)));
		return answer;
	}
}
