package me.protocos.xteam.entity;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import me.protocos.xteam.api.entity.ITeam;
import me.protocos.xteam.api.fakeobjects.FakeWorld;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.model.Headquarters;
import org.bukkit.World;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamTest
{
	ITeam team;

	@Before
	public void setup()
	{
		mockData();
		team = new Team.Builder("test").build();
	}

	@Test
	public void ShouldBeAddPlayer()
	{
		//ASSEMBLE
		//ACT
		team.addPlayer("protocos");
		//ASSERT
		Assert.assertTrue(team.getPlayers().contains("protocos"));
	}

	@Test
	public void ShouldBeContains()
	{
		//ASSEMBLE
		team.addPlayer("protocos");
		team.addPlayer("kmlanglois");
		//ACT
		boolean contains = team.containsPlayer("protocos");
		//ASSERT
		Assert.assertTrue(contains);
	}

	@Test
	public void ShouldBeDefaultTeam()
	{
		//ASSEMBLE
		//ACT
		team.setDefaultTeam(true);
		//ASSERT
		Assert.assertTrue(team.isDefaultTeam());
	}

	@Test
	public void ShouldBeDefaultTeamTag()
	{
		//ASSEMBLE
		//ACT
		String tag = team.getTag();
		//ASSERT
		Assert.assertEquals("test", tag);
	}

	@Test
	public void ShouldBeDemote()
	{
		//ASSEMBLE
		team.addPlayer("protocos");
		team.promote("protocos");
		//ACT
		team.demote("protocos");
		//ASSERT
		Assert.assertFalse(team.getAdmins().contains("protocos"));
	}

	@Test
	public void ShouldBeEquals()
	{
		//ASSEMBLE
		team.addPlayer("protocos");
		team.addPlayer("kmlanglois");
		//ACT
		Team newTeam = new Team.Builder("test").build();
		boolean equals = newTeam.equals(team);
		//ASSERT
		Assert.assertTrue(equals);
	}

	@Test
	public void ShouldBeHQ()
	{
		//ASSEMBLE
		World world = new FakeWorld();
		Headquarters hq = new Headquarters(world, 1.0D, 1.0D, 1.0D, 1.0F, 1.0F);
		//ACT
		team.setHeadquarters(hq);
		boolean hasHQ = team.hasHeadquarters();
		//ASSERT
		Assert.assertTrue(hasHQ);
	}

	@Test
	public void ShouldBeIsEmpty()
	{
		//ASSEMBLE
		//ACT
		boolean empty = team.isEmpty();
		//ASSERT
		Assert.assertTrue(empty);
	}

	@Test
	public void ShouldBeLastTime()
	{
		//ASSEMBLE
		long currentTime = System.currentTimeMillis();
		//ACT
		team.setTimeLastSet(currentTime);
		//ASSERT
		Assert.assertEquals(currentTime, team.getTimeLastSet());
	}

	@Test
	public void ShouldBeNotDemote()
	{
		//ASSEMBLE
		//ACT
		team.demote("protocos");
		//ASSERT
		Assert.assertFalse(team.getAdmins().contains("protocos"));
	}

	@Test
	public void ShouldBeNotHQ()
	{
		//ASSEMBLE
		//ACT
		boolean hasHQ = team.hasHeadquarters();
		//ASSERT
		Assert.assertFalse(hasHQ);
	}

	@Test
	public void ShouldBeNotPromote()
	{
		//ASSEMBLE
		//ACT
		team.promote("protocos");
		//ASSERT
		Assert.assertFalse(team.getAdmins().contains("protocos"));
	}

	@Test
	public void ShouldBeOpenJoining()
	{
		//ASSEMBLE
		//ACT
		team.setOpenJoining(true);
		//ASSERT
		Assert.assertTrue(team.isOpenJoining());
	}

	@Test
	public void ShouldBePromote()
	{
		//ASSEMBLE
		team.addPlayer("protocos");
		//ACT
		team.promote("protocos");
		//ASSERT
		Assert.assertTrue(team.getAdmins().contains("protocos"));
	}

	@Test
	public void ShouldBeRemovePlayer()
	{
		//ASSEMBLE
		team.addPlayer("protocos");
		//ACT
		team.removePlayer("protocos");
		//ASSERT
		Assert.assertTrue(team.isEmpty());
	}

	@Test
	public void ShouldBeSetLeader()
	{
		//ASSEMBLE
		//ACT
		team.setLeader("protocos");
		//ASSERT
		Assert.assertEquals("protocos", team.getLeader());
	}

	@Test
	public void ShouldBeSetTeamTag()
	{
		//ASSEMBLE
		String tag = "tag";
		//ACT
		team.setTag(tag);
		tag = team.getTag();
		//ASSERT
		Assert.assertEquals("tag", tag);
	}

	@Test
	public void ShouldBeSize()
	{
		//ASSEMBLE
		team.addPlayer("protocos");
		team.addPlayer("kmlanglois");
		//ACT
		int size = team.size();
		//ASSERT
		Assert.assertEquals(2, size);
	}

	@Test
	public void ShouldBeTeam()
	{
		//ASSEMBLE
		//ACT
		//ASSERT
		Assert.assertEquals("name:test tag:test open:false default:false timeHeadquartersSet:0 hq: leader: admins: players:", team.toString());
	}

	@Test
	public void ShouldBeTeamBlankProperties1()
	{
		//ASSEMBLE
		String properties = "name:blank tag:blank open:false default:false timeHeadquartersSet:0 hq: leader: admins: players:";
		//ACT
		Team t = Team.generateTeamFromProperties(properties);
		//ASSERT
		Assert.assertEquals("name:blank tag:blank open:false default:false timeHeadquartersSet:0 hq: leader: admins: players:", t.toString());
	}

	@Test
	public void ShouldBeTeamBlankProperties2()
	{
		//ASSEMBLE
		String properties = "name:blank tag:blank open:false world:world default:false timeHeadquartersSet:0 hq:0.0,0.0,0.0,0.0,0.0 leader: admins: players:";
		//ACT
		Team t = Team.generateTeamFromProperties(properties);
		//ASSERT
		Assert.assertEquals("name:blank tag:blank open:false default:false timeHeadquartersSet:0 hq: leader: admins: players:", t.toString());
	}

	@Test
	public void ShouldBeTeamBlankProperties3()
	{
		//ASSEMBLE
		String properties = "name:blank tag:tag open:false world:world default:false timeHeadquartersSet:0 hq:0.0,0.0,0.0,0.0,0.0 leader: admins: players:";
		//ACT
		Team t = Team.generateTeamFromProperties(properties);
		//ASSERT
		Assert.assertEquals("name:blank tag:tag open:false default:false timeHeadquartersSet:0 hq: leader: admins: players:", t.toString());
	}

	@Test
	public void ShouldBeTeamCurrentPropertiesFormatDefault1()
	{
		//ASSEMBLE
		String properties = "name:red tag:red open:false default:true timeHeadquartersSet:0 hq: leader: admins: players:protocos";
		//ACT
		Team t = Team.generateTeamFromProperties(properties);
		//ASSERT
		Assert.assertEquals("name:red tag:red open:false default:true timeHeadquartersSet:0 hq: leader: admins: players:protocos", t.toString());
	}

	@Test
	public void ShouldBeTeamFromPropertiesDefault1()
	{
		//ASSEMBLE
		String properties = "name:one tag:one world:world open:false leader:default timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois";
		//ACT
		Team t = Team.generateTeamFromProperties(properties);
		//ASSERT
		Assert.assertEquals("name:one tag:one open:false default:true timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader: admins:kmlanglois players:protocos,kmlanglois", t.toString());
	}

	@Test
	public void ShouldBeTeamFromPropertiesDefault2()
	{
		//ASSEMBLE
		String properties = "name:one tag:one world:world open:false leader:default timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois";
		//ACT
		Team t = Team.generateTeamFromProperties(properties);
		//ASSERT
		Assert.assertEquals("name:one tag:one open:false default:true timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader: admins:kmlanglois players:protocos,kmlanglois", t.toString());
	}

	@Test
	public void ShouldBeTeamFromPropertiesRegular1()
	{
		//ASSEMBLE
		String properties = "name:one tag:one world:world open:false leader:kmlanglois timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois";
		//ACT
		Team t = Team.generateTeamFromProperties(properties);
		//ASSERT
		Assert.assertEquals("name:one tag:one open:false default:false timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins:kmlanglois players:protocos,kmlanglois", t.toString());
	}

	@Test
	public void ShouldBeTeamFromPropertiesRegular2()
	{
		//ASSEMBLE
		String properties = "name:one tag:one world:world open:false leader:kmlanglois timeHeadquartersSet:1361318508899 Headquarters:169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 players:kmlanglois,protocos admins:kmlanglois";
		//ACT
		Team t = Team.generateTeamFromProperties(properties);
		//ASSERT
		Assert.assertEquals("name:one tag:one open:false default:false timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins:kmlanglois players:protocos,kmlanglois", t.toString());
	}

	@Test
	public void ShouldBeTeamInputEqualsOutput()
	{
		//ASSEMBLE
		String properties = "name:one tag:one open:false default:false timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins:kmlanglois players:kmlanglois,protocos";
		//ACT
		Team t = Team.generateTeamFromProperties(properties);
		//ASSERT
		Assert.assertEquals("name:one tag:one open:false default:false timeHeadquartersSet:1361318508899 hq:world,169.92906931820792,65.0,209.31066111932847,22.049545,36.14993 leader:kmlanglois admins:kmlanglois players:protocos,kmlanglois", t.toString());
	}

	@After
	public void takedown()
	{
	}
}