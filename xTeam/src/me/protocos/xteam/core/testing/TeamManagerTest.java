package me.protocos.xteam.core.testing;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.core.Team;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamManagerTest
{
	@Before
	public void setup()
	{
	}
	@Test
	public void ShouldBeClear()
	{
		//ASSEMBLE
		xTeam.tm.createTeamWithLeader("test1", "protocos");
		xTeam.tm.createTeamWithLeader("test2", "kmlanglois");
		//ACT
		xTeam.tm.clear();
		//ASSERT
		Assert.assertTrue(xTeam.tm.getAllTeams().size() == 0);
	}
	@Test
	public void ShouldBeCreateTeam()
	{
		//ASSEMBLE
		//ACT
		xTeam.tm.createTeam("test");
		//ASSERT
		Assert.assertTrue(xTeam.tm.contains("test"));
	}
	@Test
	public void ShouldBeCreateTeamWithLeader()
	{
		//ASSEMBLE
		//ACT
		xTeam.tm.createTeamWithLeader("test", "protocos");
		//ASSERT
		Assert.assertTrue(xTeam.tm.contains("test"));
		Assert.assertEquals("protocos", xTeam.tm.getTeam("test").getLeader());
	}
	@Test
	public void ShouldBeGetAllTeams()
	{
		//ASSEMBLE
		xTeam.tm.createTeamWithLeader("test1", "protocos");
		xTeam.tm.createTeamWithLeader("test2", "kmlanglois");
		//ACT
		//ASSERT
		Assert.assertEquals("[name:test1 tag:test1 open:false default:false timeHeadquartersSet:0 hq: leader:protocos admins:protocos players:protocos, " +
				"name:test2 tag:test2 open:false default:false timeHeadquartersSet:0 hq: leader:kmlanglois admins:kmlanglois players:kmlanglois]", xTeam.tm.getAllTeams().toString());
	}
	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		xTeam.tm.createTeamWithLeader("one", "protocos");
		//ACT
		Team test = xTeam.tm.getTeam("one");
		//ASSERT
		Assert.assertEquals("one", test.getName());
	}
	@Test
	public void ShouldBeSameNameConflict()
	{
		//ASSEMBLE
		Team team1 = new Team.Builder("one").build();
		Team team2 = new Team.Builder("ONE").build();
		//ACT
		xTeam.tm.addTeam(team1);
		boolean exists = xTeam.tm.contains(team2.getName());
		//ASSERT
		Assert.assertTrue(exists);
	}
	@After
	public void takedown()
	{
		xTeam.tm.clear();
	}
}