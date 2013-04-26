package me.protocos.xteam.core.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import me.protocos.xteam.core.TeamHeadquarters;
import me.protocos.xteam.testing.FakeWorld;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamHeadquartersTest
{
	TeamHeadquarters hq;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTeamHeadquartersDefault()
	{
		//ASSEMBLE
		hq = new TeamHeadquarters();
		//ACT
		//ASSERT
		Assert.assertEquals("world", hq.getWorld().getName());
		Assert.assertEquals(0.0D, hq.getX(), 0);
		Assert.assertEquals(0.0D, hq.getY(), 0);
		Assert.assertEquals(0.0D, hq.getZ(), 0);
		Assert.assertEquals(0.0F, hq.getYaw(), 0);
		Assert.assertEquals(0.0F, hq.getPitch(), 0);
	}
	@Test
	public void ShouldBeTeamHeadquartersFakeData()
	{
		//ASSEMBLE
		hq = new TeamHeadquarters(new FakeWorld(), 0, 0, 0, 0, 0);
		//ACT
		//ASSERT
		Assert.assertEquals("world", hq.getWorld().getName());
		Assert.assertEquals(0.0D, hq.getX(), 0);
		Assert.assertEquals(0.0D, hq.getY(), 0);
		Assert.assertEquals(0.0D, hq.getZ(), 0);
		Assert.assertEquals(0.0F, hq.getYaw(), 0);
		Assert.assertEquals(0.0F, hq.getPitch(), 0);
	}
	@Test
	public void ShouldBeTeamHeadquartersExists()
	{
		//ASSEMBLE
		hq = new TeamHeadquarters(new FakeWorld(), 0, 0, 0, 0, 0);
		//ACT
		boolean exists = hq.exists();
		//ASSERT
		Assert.assertTrue(exists);
	}
	@Test
	public void ShouldBeTeamHeadquartersNotExists()
	{
		//ASSEMBLE
		hq = new TeamHeadquarters(null, 0, 0, 0, 0, 0);
		//ACT
		boolean notExists = hq.exists();
		//ASSERT
		Assert.assertFalse(notExists);
	}
	@After
	public void takedown()
	{
	}
}