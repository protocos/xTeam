package me.protocos.xteam.util;

import me.protocos.xteam.fakeobjects.FakeLocation;
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

	@After
	public void takedown()
	{
	}
}