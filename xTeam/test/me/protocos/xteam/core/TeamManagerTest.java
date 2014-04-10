package me.protocos.xteam.core;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamManagerTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}

	@Test
	public void ShouldBeClear()
	{
		//ASSEMBLE
		XTeam.getInstance().getTeamManager().createTeam(Team.createTeamWithLeader("test1", "protocos"));
		XTeam.getInstance().getTeamManager().createTeam(Team.createTeamWithLeader("test2", "kmlanglois"));
		//ACT
		XTeam.getInstance().getTeamManager().clear();
		//ASSERT
		Assert.assertTrue(XTeam.getInstance().getTeamManager().getTeams().size() == 0);
	}

	@Test
	public void ShouldBeCreateTeam()
	{
		//ASSEMBLE
		//ACT
		XTeam.getInstance().getTeamManager().createTeam(Team.createTeam("test"));
		//ASSERT
		Assert.assertTrue(XTeam.getInstance().getTeamManager().containsTeam("test"));
	}

	@Test
	public void ShouldBeCreateTeamWithLeader()
	{
		//ASSEMBLE
		//ACT
		XTeam.getInstance().getTeamManager().createTeam(Team.createTeamWithLeader("test", "protocos"));
		//ASSERT
		Assert.assertTrue(XTeam.getInstance().getTeamManager().containsTeam("test"));
		Assert.assertEquals("protocos", XTeam.getInstance().getTeamManager().getTeam("test").getLeader());
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
				"blue=name:blue tag:blue open:true default:true timeHeadquartersLastSet:0 hq:none leader: admins: players:}", XTeam.getInstance().getTeamManager().getTeams().toString());
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		//ACT
		ITeam test = XTeam.getInstance().getTeamManager().getTeam("one");
		//ASSERT
		Assert.assertEquals("ONE", test.getName());
	}

	@Test
	public void ShouldBeSameNameConflict()
	{
		//ASSEMBLE
		Team team1 = new Team.Builder("ONE").build();
		//ACT
		XTeam.getInstance().getTeamManager().createTeam(team1);
		boolean exists = XTeam.getInstance().getTeamManager().containsTeam("one");
		//ASSERT
		Assert.assertTrue(exists);
	}

	@After
	public void takedown()
	{
		XTeam.getInstance().getTeamManager().clear();
	}
}