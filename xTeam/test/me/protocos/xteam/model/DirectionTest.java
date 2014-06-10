package me.protocos.xteam.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectionTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeFront()
	{
		//ASSEMBLE
		double direction = 0.0D;
		//ACT
		//ASSERT
		Assert.assertEquals(Direction.FRONT, Direction.fromAngle(direction));
	}

	@Test
	public void ShouldBeFrontLeft()
	{
		//ASSEMBLE
		double direction = 45.0D;
		//ACT
		//ASSERT
		Assert.assertEquals(Direction.FRONT_LEFT, Direction.fromAngle(direction));
	}

	@Test
	public void ShouldBeLeft()
	{
		//ASSEMBLE
		double direction = 90.0D;
		//ACT
		//ASSERT
		Assert.assertEquals(Direction.LEFT, Direction.fromAngle(direction));
	}

	@Test
	public void ShouldBeBackLeft()
	{
		//ASSEMBLE
		double direction = 135.0D;
		//ACT
		//ASSERT
		Assert.assertEquals(Direction.BACK_LEFT, Direction.fromAngle(direction));
	}

	@Test
	public void ShouldBeBack()
	{
		//ASSEMBLE
		double direction = 180.0D;
		//ACT
		//ASSERT
		Assert.assertEquals(Direction.BACK, Direction.fromAngle(direction));
	}

	@Test
	public void ShouldBeBackRight()
	{
		//ASSEMBLE
		double direction = 225.0D;
		//ACT
		//ASSERT
		Assert.assertEquals(Direction.BACK_RIGHT, Direction.fromAngle(direction));
	}

	@Test
	public void ShouldBeRight()
	{
		//ASSEMBLE
		double direction = 270.0D;
		//ACT
		//ASSERT
		Assert.assertEquals(Direction.RIGHT, Direction.fromAngle(direction));
	}

	@Test
	public void ShouldBeFrontRight()
	{
		//ASSEMBLE
		double direction = 315.0D;
		//ACT
		//ASSERT
		Assert.assertEquals(Direction.FRONT_RIGHT, Direction.fromAngle(direction));
	}

	@After
	public void takedown()
	{
	}
}