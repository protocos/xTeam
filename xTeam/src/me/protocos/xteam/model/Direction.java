package me.protocos.xteam.model;

import me.protocos.api.util.CommonUtil;

public enum Direction
{
	FRONT(345, 15, "front"),
	FRONT_LEFT(15, 45, "front-left"),
	LEFT_FRONT(45, 75, "left-front"),
	LEFT(75, 105, "left"),
	LEFT_BACK(105, 135, "left-back"),
	BACK_LEFT(135, 165, "back-left"),
	BACK(165, 195, "back"),
	BACK_RIGHT(195, 225, "back-right"),
	RIGHT_BACK(225, 255, "right-back"),
	RIGHT(255, 285, "right"),
	RIGHT_FRONT(285, 315, "right-front"),
	FRONT_RIGHT(315, 345, "front-right");

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
