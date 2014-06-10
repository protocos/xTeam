package me.protocos.xteam.model;

import me.protocos.xteam.util.CommonUtil;

public enum Direction
{
	FRONT_LEFT(22.5, 67.5),
	LEFT(67.5, 112.5),
	BACK_LEFT(112.5, 157.5),
	BACK(157.5, 202.5),
	BACK_RIGHT(202.5, 247.5),
	RIGHT(247.5, 292.5),
	FRONT_RIGHT(292.5, 337.5),
	FRONT(337.5, 22.5);

	private double lowerBound;
	private double upperBound;

	Direction(double lowerBound, double upperBound)
	{
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	public boolean insideRange(double rangeCheck)
	{
		return CommonUtil.insideRange(rangeCheck, this.lowerBound, this.upperBound, 360.0D);
	}

	public static Direction fromAngle(double angle)
	{
		for (Direction direction : Direction.values())
			if (direction.insideRange(angle))
				return direction;
		return null;
	}
}
