package me.protocos.xteam.core;

import me.protocos.xteam.xTeam;
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
		xTeam.getTeamManager().addTeam(Team.createTeamWithLeader("test1", "protocos"));
		xTeam.getTeamManager().addTeam(Team.createTeamWithLeader("test2", "kmlanglois"));
		//ACT
		xTeam.getTeamManager().clear();
		//ASSERT
		Assert.assertTrue(xTeam.getTeamManager().getAllTeams().size() == 0);
	}
	@Test
	public void ShouldBeCreateTeam()
	{
		//ASSEMBLE
		//ACT
		xTeam.getTeamManager().addTeam(Team.createTeam("test"));
		//ASSERT
		Assert.assertTrue(xTeam.getTeamManager().contains("test"));
	}
	@Test
	public void ShouldBeCreateTeamWithLeader()
	{
		//ASSEMBLE
		//ACT
		xTeam.getTeamManager().addTeam(Team.createTeamWithLeader("test", "protocos"));
		//ASSERT
		Assert.assertTrue(xTeam.getTeamManager().contains("test"));
		Assert.assertEquals("protocos", xTeam.getTeamManager().getTeam("test").getLeader());
	}
	@Test
	public void ShouldBeGetAllTeams()
	{
		//ASSEMBLE
		xTeam.getTeamManager().addTeam(Team.createTeamWithLeader("test1", "protocos"));
		xTeam.getTeamManager().addTeam(Team.createTeamWithLeader("test2", "kmlanglois"));
		//ACT
		//ASSERT
		Assert.assertEquals("[name:test1 tag:test1 open:false default:false timeHeadquartersSet:0 hq: leader:protocos admins:protocos players:protocos, " +
				"name:test2 tag:test2 open:false default:false timeHeadquartersSet:0 hq: leader:kmlanglois admins:kmlanglois players:kmlanglois]", xTeam.getTeamManager().getAllTeams().toString());
	}
	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		xTeam.getTeamManager().addTeam(Team.createTeamWithLeader("one", "protocos"));
		//ACT
		Team test = xTeam.getTeamManager().getTeam("one");
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
		xTeam.getTeamManager().addTeam(team1);
		boolean exists = xTeam.getTeamManager().contains(team2.getName());
		//ASSERT
		Assert.assertTrue(exists);
	}
	@After
	public void takedown()
	{
		xTeam.getTeamManager().clear();
	}
}