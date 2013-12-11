package me.protocos.xteam.core;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.model.ITeam;
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
		xTeam.getInstance().getTeamManager().createTeam(Team.createTeamWithLeader("test1", "protocos"));
		xTeam.getInstance().getTeamManager().createTeam(Team.createTeamWithLeader("test2", "kmlanglois"));
		//ACT
		xTeam.getInstance().getTeamManager().clear();
		//ASSERT
		Assert.assertTrue(xTeam.getInstance().getTeamManager().getTeams().size() == 0);
	}

	@Test
	public void ShouldBeCreateTeam()
	{
		//ASSEMBLE
		//ACT
		xTeam.getInstance().getTeamManager().createTeam(Team.createTeam("test"));
		//ASSERT
		Assert.assertTrue(xTeam.getInstance().getTeamManager().containsTeam("test"));
	}

	@Test
	public void ShouldBeCreateTeamWithLeader()
	{
		//ASSEMBLE
		//ACT
		xTeam.getInstance().getTeamManager().createTeam(Team.createTeamWithLeader("test", "protocos"));
		//ASSERT
		Assert.assertTrue(xTeam.getInstance().getTeamManager().containsTeam("test"));
		Assert.assertEquals("protocos", xTeam.getInstance().getTeamManager().getTeam("test").getLeader());
	}

	@Test
	public void ShouldBeGetAllTeams()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals("{ONE=name:ONE tag:TeamAwesome open:false default:false timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins:kmlanglois players:protocos,kmlanglois, " +
				"two=name:two tag:two open:false default:false timeHeadquartersSet:0 hq: leader:mastermind admins:mastermind players:mastermind, " +
				"red=name:red tag:REDONE open:true default:true timeHeadquartersSet:0 hq: leader: admins: players:teammate,strandedhelix, " +
				"blue=name:blue tag:blue open:true default:true timeHeadquartersSet:0 hq: leader: admins: players:}", xTeam.getInstance().getTeamManager().getTeams().toString());
	}

	@Test
	public void ShouldBeGetTeam()
	{
		//ASSEMBLE
		//ACT
		ITeam test = xTeam.getInstance().getTeamManager().getTeam("one");
		//ASSERT
		Assert.assertEquals("ONE", test.getName());
	}

	@Test
	public void ShouldBeSameNameConflict()
	{
		//ASSEMBLE
		Team team1 = new Team.Builder("ONE").build();
		//ACT
		xTeam.getInstance().getTeamManager().createTeam(team1);
		boolean exists = xTeam.getInstance().getTeamManager().containsTeam("one");
		//ASSERT
		Assert.assertTrue(exists);
	}

	@After
	public void takedown()
	{
		xTeam.getInstance().getTeamManager().clear();
	}
}