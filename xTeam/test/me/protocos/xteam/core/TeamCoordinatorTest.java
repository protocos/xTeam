package me.protocos.xteam.core;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamCoordinatorTest
{
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeClear()
	{
		//ASSEMBLE
		teamCoordinator.createTeam(Team.createTeamWithLeader(teamPlugin, "test1", "protocos"));
		teamCoordinator.createTeam(Team.createTeamWithLeader(teamPlugin, "test2", "kmlanglois"));
		//ACT
		teamCoordinator.clear();
		//ASSERT
		Assert.assertTrue(teamCoordinator.getTeams().size() == 0);
	}

	@Test
	public void ShouldBeCreateTeam()
	{
		//ASSEMBLE
		//ACT
		teamCoordinator.createTeam(Team.createTeam(teamPlugin, "test"));
		//ASSERT
		Assert.assertTrue(teamCoordinator.containsTeam("test"));
	}

	@Test
	public void ShouldBeCreateTeamWithLeader()
	{
		//ASSEMBLE
		//ACT
		teamCoordinator.createTeam(Team.createTeamWithLeader(teamPlugin, "test", "protocos"));
		//ASSERT
		Assert.assertTrue(teamCoordinator.containsTeam("test"));
		Assert.assertEquals("protocos", teamCoordinator.getTeam("test").getLeader());
	}

	@Test
	public void ShouldBeGetAllTeams()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals("{ONE=name:ONE tag:TeamAwesome openJoining:false defaultTeam:false timeHeadquartersLastSet:1361318508899 headquarters:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins: players:protocos,kmlanglois, " +
				"two=name:two tag:two openJoining:false defaultTeam:false timeHeadquartersLastSet:0 headquarters: leader:mastermind admins: players:mastermind, " +
				"red=name:red tag:REDONE openJoining:true defaultTeam:true timeHeadquartersLastSet:0 headquarters: leader: admins: players:teammate,strandedhelix, " +
				"blue=name:blue tag:blue openJoining:true defaultTeam:true timeHeadquartersLastSet:0 headquarters: leader: admins: players:}", teamCoordinator.getTeams().toString());
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		//ACT
		ITeam test = teamCoordinator.getTeam("one");
		//ASSERT
		Assert.assertEquals("ONE", test.getName());
	}

	@Test
	public void ShouldBeSameNameConflict()
	{
		//ASSEMBLE
		Team team1 = new Team.Builder(teamPlugin, "ONE").build();
		//ACT
		teamCoordinator.createTeam(team1);
		boolean exists = teamCoordinator.containsTeam("one");
		//ASSERT
		Assert.assertTrue(exists);
	}

	@After
	public void takedown()
	{
		teamCoordinator.clear();
	}
}