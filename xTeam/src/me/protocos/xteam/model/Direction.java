package me.protocos.xteam.model;

import me.protocos.xteam.util.CommonUtil;

public enum Direction
{
	FRONT_LEFT(22.5, 67.5, "front-left"),
	LEFT(67.5, 112.5, "left"),
	BACK_LEFT(112.5, 157.5, "back-left"),
	BACK(157.5, 202.5, "back"),
	BACK_RIGHT(202.5, 247.5, "back-right"),
	RIGHT(247.5, 292.5, "right"),
	FRONT_RIGHT(292.5, 337.5, "front-right"),
	FRONT(337.5, 22.5, "front");

	private final double lowerBound;
	private final double upperBound;
	private final String name;

	Direction(double lowerBound, double upperBound, String name)
	{
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.name = name;
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

	@Override
	public String toString()
	{
		return name;
	}
}
