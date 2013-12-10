package me.protocos.xteam.core;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.api.fakeobjects.FakeWorld;
import me.protocos.xteam.model.Headquarters;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamHeadquartersTest
{
	Headquarters hq;

	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	//	@Test
	//	public void ShouldBeTeamHeadquartersDefault()
	//	{
	//		//ASSEMBLE
	//		hq = new Headquarters();
	//		//ACT
	//		//ASSERT
	//		Assert.assertEquals("world", hq.getWorld().getName());
	//		Assert.assertEquals(0.0D, hq.getLocation().getX(), 0);
	//		Assert.assertEquals(0.0D, hq.getLocation().getY(), 0);
	//		Assert.assertEquals(0.0D, hq.getLocation().getZ(), 0);
	//		Assert.assertEquals(0.0F, hq.getLocation().getYaw(), 0);
	//		Assert.assertEquals(0.0F, hq.getLocation().getPitch(), 0);
	//	}

	@Test
	public void ShouldBeTeamHeadquartersExists()
	{
		//ASSEMBLE
		hq = new Headquarters(new FakeWorld(), 0, 0, 0, 0, 0);
		//ACT
		boolean exists = hq.exists();
		//ASSERT
		Assert.assertTrue(exists);
	}

	@Test
	public void ShouldBeTeamHeadquartersFakeData()
	{
		//ASSEMBLE
		hq = new Headquarters(new FakeWorld(), 0, 0, 0, 0, 0);
		//ACT
		//ASSERT
		Assert.assertEquals("world", hq.getWorld().getName());
		Assert.assertEquals(0.0D, hq.getLocation().getX(), 0);
		Assert.assertEquals(0.0D, hq.getLocation().getY(), 0);
		Assert.assertEquals(0.0D, hq.getLocation().getZ(), 0);
		Assert.assertEquals(0.0F, hq.getLocation().getYaw(), 0);
		Assert.assertEquals(0.0F, hq.getLocation().getPitch(), 0);
	}

	@Test
	public void ShouldBeTeamHeadquartersNotExists()
	{
		//ASSEMBLE
		hq = new Headquarters(null, 0, 0, 0, 0, 0);
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