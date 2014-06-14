package me.protocos.xteam.util;

import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.message.MessageUtil;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocationUtilTest
{
	@Before
	public void setup()
	{
	}

	@Test
	public void ShouldBeNoAngleBetweenLocations()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation();
		Location location2 = new FakeLocation();
		//ACT
		double difference = LocationUtil.getAngleBetween(location1, location2);
		//ASSERT
		Assert.assertEquals(0.0D, difference, 0);
	}

	@Test
	public void ShouldBeActualYawTranslationEast()
	{
		//ASSEMBLE
		Location location = new FakeLocation(0, 0, 0, 270, 0);
		//ACT
		double degrees = LocationUtil.getYawTranslation(location);
		//ASSERT
		Assert.assertEquals(0.0D, degrees, 0);
	}

	@Test
	public void ShouldBeActualYawTranslationNorth()
	{
		//ASSEMBLE
		Location location = new FakeLocation(0, 0, 0, 180, 0);
		//ACT
		double degrees = LocationUtil.getYawTranslation(location);
		//ASSERT
		Assert.assertEquals(90.0D, degrees, 0);
	}

	@Test
	public void ShouldBeActualYawTranslationWest()
	{
		//ASSEMBLE
		Location location = new FakeLocation(0, 0, 0, 90, 0);
		//ACT
		double degrees = LocationUtil.getYawTranslation(location);
		//ASSERT
		Assert.assertEquals(180.0D, degrees, 0);
	}

	@Test
	public void ShouldBeActualYawTranslationSouth()
	{
		//ASSEMBLE
		Location location = new FakeLocation(0, 0, 0, 0, 0);
		//ACT
		double degrees = LocationUtil.getYawTranslation(location);
		//ASSERT
		Assert.assertEquals(270.0D, degrees, 0);
	}

	@Test
	public void ShouldBeAngleBetweenLocationsEast()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0);
		Location location2 = new FakeLocation(10, 0, 0);
		//ACT
		double difference = LocationUtil.getAngleBetween(location1, location2);
		//ASSERT
		Assert.assertEquals(0.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenLocationsNorth()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0);
		Location location2 = new FakeLocation(0, 0, -10);
		//ACT
		double difference = LocationUtil.getAngleBetween(location1, location2);
		//ASSERT
		Assert.assertEquals(90.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenLocationsWest()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0);
		Location location2 = new FakeLocation(-10, 0, 0);
		//ACT
		double difference = LocationUtil.getAngleBetween(location1, location2);
		//ASSERT
		Assert.assertEquals(180.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenLocationsSouth()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0);
		Location location2 = new FakeLocation(0, 0, 10);
		//ACT
		double difference = LocationUtil.getAngleBetween(location1, location2);
		//ASSERT
		Assert.assertEquals(270.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenYawAndLocationFront()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(0, 0, -10);
		//ACT
		double difference = LocationUtil.getYawAngleToLocation(location1, location2);
		//ASSERT
		Assert.assertEquals(0.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenYawAndLocationFrontLeft()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(-10, 0, -10);
		//ACT
		double difference = LocationUtil.getYawAngleToLocation(location1, location2);
		//ASSERT
		Assert.assertEquals(45.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenYawAndLocationLeft()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(-10, 0, 0);
		//ACT
		double difference = LocationUtil.getYawAngleToLocation(location1, location2);
		//ASSERT
		Assert.assertEquals(90.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenYawAndLocationBackLeft()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(-10, 0, 10);
		//ACT
		double difference = LocationUtil.getYawAngleToLocation(location1, location2);
		//ASSERT
		Assert.assertEquals(135.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenYawAndLocationBack()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(0, 0, 10);
		//ACT
		double difference = LocationUtil.getYawAngleToLocation(location1, location2);
		//ASSERT
		Assert.assertEquals(180.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenYawAndLocationBackRight()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(10, 0, 10);
		//ACT
		double difference = LocationUtil.getYawAngleToLocation(location1, location2);
		//ASSERT
		Assert.assertEquals(225.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenYawAndLocationRight()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(10, 0, 0);
		//ACT
		double difference = LocationUtil.getYawAngleToLocation(location1, location2);
		//ASSERT
		Assert.assertEquals(270.0D, difference, 0);
	}

	@Test
	public void ShouldBeAngleBetweenYawAndLocationFrontRight()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(10, 0, -10);
		//ACT
		double difference = LocationUtil.getYawAngleToLocation(location1, location2);
		//ASSERT
		Assert.assertEquals(315.0D, difference, 0);
	}

	@Test
	public void ShouldBeRelativePositionHere()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(0, 0, 0);
		//ACT
		//ASSERT
		Assert.assertEquals("Here", LocationUtil.getRelativePosition(location1, location2));
	}

	@Test
	public void ShouldBeRelativePositionPluralTest()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(0, 1, -1);
		//ACT
		//ASSERT
		Assert.assertEquals("@ 1 block to front, 1 block up", MessageUtil.resetFormatting(LocationUtil.getRelativePosition(location1, location2).trim()));
	}

	@Test
	public void ShouldBeRelativePositionFlatMeasurement()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(0, 0, 0, 180, 0);
		Location location2 = new FakeLocation(0, 10, -10);
		//ACT
		//ASSERT
		Assert.assertEquals("@ 10 blocks to front, 10 blocks up", MessageUtil.resetFormatting(LocationUtil.getRelativePosition(location1, location2).trim()));
	}

	@Test
	public void ShouldBeRelativePosition1()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(10, 0, 10, 180, 0);
		Location location2 = new FakeLocation(50, 0, -100);
		//ACT
		//ASSERT
		Assert.assertEquals("@ 117 blocks to front-right", MessageUtil.resetFormatting(LocationUtil.getRelativePosition(location1, location2).trim()));
	}

	@Test
	public void ShouldBeRelativePosition2()
	{
		//ASSEMBLE
		Location location1 = new FakeLocation(-256, 0, -172, 146, 0);
		Location location2 = new FakeLocation(11, 0, 1000);
		//ACT
		//ASSERT
		Assert.assertEquals("@ 1202 blocks to back-left", MessageUtil.resetFormatting(LocationUtil.getRelativePosition(location1, location2).trim()));
	}

	@After
	public void takedown()
	{
	}
}