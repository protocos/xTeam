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
		xTeam.getInstance().getTeamManager().addTeam(Team.createTeamWithLeader("test1", "protocos"));
		xTeam.getInstance().getTeamManager().addTeam(Team.createTeamWithLeader("test2", "kmlanglois"));
		//ACT
		xTeam.getInstance().getTeamManager().clear();
		//ASSERT
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getAllTeams().size() == 0);
	}
	@Test
	public void ShouldBeCreateTeam()
	{
		//ASSEMBLE
		//ACT
		xTeam.getInstance().getTeamManager().addTeam(Team.createTeam("test"));
		//ASSERT
		Assert.assertTrue(xTeam.getInstance().getTeamManager().contains("test"));
	}
	@Test
	public void ShouldBeCreateTeamWithLeader()
	{
		//ASSEMBLE
		//ACT
		xTeam.getInstance().getTeamManager().addTeam(Team.createTeamWithLeader("test", "protocos"));
		//ASSERT
		Assert.assertTrue(xTeam.getInstance().getTeamManager().contains("test"));
		Assert.assertEquals("protocos", xTeam.getInstance().getTeamManager().getTeam("test").getLeader());
	}
	@Test
	public void ShouldBeGetAllTeams()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().addTeam(Team.createTeamWithLeader("test1", "protocos"));
		xTeam.getInstance().getTeamManager().addTeam(Team.createTeamWithLeader("test2", "kmlanglois"));
		//ACT
		//ASSERT
		Assert.assertEquals("[name:test1 tag:test1 open:false default:false timeHeadquartersSet:0 hq: leader:protocos admins:protocos players:protocos, " +
				"name:test2 tag:test2 open:false default:false timeHeadquartersSet:0 hq: leader:kmlanglois admins:kmlanglois players:kmlanglois]", xTeam.getInstance().getTeamManager().getAllTeams().toString());
	}
	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		xTeam.getInstance().getTeamManager().addTeam(Team.createTeamWithLeader("one", "protocos"));
		//ACT
		Team test = xTeam.getInstance().getTeamManager().getTeam("one");
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
		xTeam.getInstance().getTeamManager().addTeam(team1);
		boolean exists = xTeam.getInstance().getTeamManager().contains(team2.getName());
		//ASSERT
		Assert.assertTrue(exists);
	}
	@After
	public void takedown()
	{
		xTeam.getInstance().getTeamManager().clear();
	}
}