package me.protocos.xteam.data;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SQLiteDataManagerTest
{
	private TeamPlugin teamPlugin;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
	}

	@Test
	public void ShouldBeSomething()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals(true, true);
	}

	@After
	public void takedown()
	{
	}
}