package me.protocos.xteam.core;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamManagerTest
{
	private TeamPlugin teamPlugin;
	private ITeamManager teamManager;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		teamManager = teamPlugin.getTeamManager();
	}

	@Test
	public void ShouldBeClear()
	{
		//ASSEMBLE
		teamManager.createTeam(Team.createTeamWithLeader(teamPlugin, "test1", "protocos"));
		teamManager.createTeam(Team.createTeamWithLeader(teamPlugin, "test2", "kmlanglois"));
		//ACT
		teamManager.clear();
		//ASSERT
		Assert.assertTrue(teamManager.getTeams().size() == 0);
	}

	@Test
	public void ShouldBeCreateTeam()
	{
		//ASSEMBLE
		//ACT
		teamManager.createTeam(Team.createTeam(teamPlugin, "test"));
		//ASSERT
		Assert.assertTrue(teamManager.containsTeam("test"));
	}

	@Test
	public void ShouldBeCreateTeamWithLeader()
	{
		//ASSEMBLE
		//ACT
		teamManager.createTeam(Team.createTeamWithLeader(teamPlugin, "test", "protocos"));
		//ASSERT
		Assert.assertTrue(teamManager.containsTeam("test"));
		Assert.assertEquals("protocos", teamManager.getTeam("test").getLeader());
	}

	@Test
	public void ShouldBeGetAllTeams()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals("{ONE=name:ONE tag:TeamAwesome open:false default:false timeHeadquartersLastSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins: players:protocos,kmlanglois, " +
				"two=name:two tag:two open:false default:false timeHeadquartersLastSet:0 hq:none leader:mastermind admins: players:mastermind, " +
				"red=name:red tag:REDONE open:true default:true timeHeadquartersLastSet:0 hq:none leader: admins: players:teammate,strandedhelix, " +
				"blue=name:blue tag:blue open:true default:true timeHeadquartersLastSet:0 hq:none leader: admins: players:}", teamManager.getTeams().toString());
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		//ACT
		ITeam test = teamManager.getTeam("one");
		//ASSERT
		Assert.assertEquals("ONE", test.getName());
	}

	@Test
	public void ShouldBeSameNameConflict()
	{
		//ASSEMBLE
		Team team1 = new Team.Builder(teamPlugin, "ONE").build();
		//ACT
		teamManager.createTeam(team1);
		boolean exists = teamManager.containsTeam("one");
		//ASSERT
		Assert.assertTrue(exists);
	}

	@After
	public void takedown()
	{
		teamManager.clear();
	}
}