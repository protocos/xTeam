package me.protocos.xteam.legacy;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.data.PropertyList;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.fakeobjects.FakeLocation;
import me.protocos.xteam.model.Headquarters;
import me.protocos.xteam.model.NullHeadquarters;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LegacyDataTranslatorTest
{
	private TeamPlugin teamPlugin;
	private PropertyList defaultTeamProperties, regularTeamProperties;
	private ITeam defaultTeam, regularTeam;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		defaultTeam = new Team.Builder(teamPlugin, "name")
				.tag("tag")
				.openJoining(true)
				.defaultTeam(true)
				.timeHeadquartersLastSet(1400000000000L)
				.headquarters(new Headquarters(teamPlugin, new FakeLocation(64.0D, 64.0D, 64.0D)))
				.admins("adminname")
				.players("adminname", "playername")
				.build();
		regularTeam = new Team.Builder(teamPlugin, "name")
				.tag("tag")
				.openJoining(false)
				.defaultTeam(false)
				.timeHeadquartersLastSet(1400000000000L)
				.headquarters(new Headquarters(teamPlugin, new FakeLocation(64.0D, 64.0D, 64.0D)))
				.leader("leadername")
				.admins("adminname")
				.players("leadername", "adminname", "playername")
				.build();
	}

	@Test
	public void ShouldBeVersion1_0Through1_6_3()
	{
		//ASSEMBLE
		defaultTeam.setTag("name");
		regularTeam.setTag("name");
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name none world 1400000000000 hq: 64.0 64.0 64.0 0.0 0.0 adminname~ playername");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name password world 1400000000000 hq: 64.0 64.0 64.0 0.0 0.0 leadername~~ adminname~ playername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBeVersion1_0Through1_6_3LeaderOnly()
	{
		//ASSEMBLE
		regularTeam.setTag("name");
		regularTeam.removePlayer("adminname");
		regularTeam.removePlayer("playername");
		//ACT
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name password world 1400000000000 hq: 64.0 64.0 64.0 0.0 0.0 leadername~~");
		//ASSERT
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBeVersion1_0Through1_6_3LeaderAndAdmin()
	{
		//ASSEMBLE
		regularTeam.setTag("name");
		regularTeam.removePlayer("playername");
		//ACT
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name password world 1400000000000 hq: 64.0 64.0 64.0 0.0 0.0 leadername~~ adminname~");
		//ASSERT
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBeVersion1_0Through1_6_3NoHeadquarters()
	{
		//ASSEMBLE
		defaultTeam.setTag("name");
		regularTeam.setTag("name");
		defaultTeam.setHeadquarters(new NullHeadquarters());
		regularTeam.setHeadquarters(new NullHeadquarters());
		defaultTeam.setTimeHeadquartersLastSet(0L);
		regularTeam.setTimeHeadquartersLastSet(0L);
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name none world 0 hq: 0.0 0.0 0.0 0.0 0.0 adminname~ playername");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name password world 0 hq: 0.0 0.0 0.0 0.0 0.0 leadername~~ adminname~ playername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBe1_6_4()
	{
		//ASSEMBLE
		defaultTeam.setOpenJoining(false);
		defaultTeam.setTag("name");
		regularTeam.setTag("name");
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name:name world:world open:false leader: timeLastSet:1400000000000 HQ:64.0,64.0,64.0,0.0,0.0 players:playername,adminname admins:adminname");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name world:world open:false leader:leadername timeLastSet:1400000000000 HQ:64.0,64.0,64.0,0.0,0.0 players:playername,adminname,leadername admins:adminname,leadername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBe1_6_4NoHeadquarters()
	{
		//ASSEMBLE
		defaultTeam.setOpenJoining(false);
		defaultTeam.setTag("name");
		regularTeam.setTag("name");
		defaultTeam.setHeadquarters(new NullHeadquarters());
		regularTeam.setHeadquarters(new NullHeadquarters());
		defaultTeam.setTimeHeadquartersLastSet(0L);
		regularTeam.setTimeHeadquartersLastSet(0L);
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name:name world:world open:false leader: timeLastSet:0 HQ:0.0,0.0,0.0,0.0,0.0 players:playername,adminname admins:adminname");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name world:world open:false leader:leadername timeLastSet:0 HQ:0.0,0.0,0.0,0.0,0.0 players:playername,adminname,leadername admins:adminname,leadername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBe1_6_5Through1_7_3()
	{
		//ASSEMBLE
		defaultTeam.setOpenJoining(false);
		defaultTeam.setTag("name");
		regularTeam.setTag("name");
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name:name world:world open:false leader:default timeLastSet:1400000000000 HQ:64.0,64.0,64.0,0.0,0.0 players:playername,adminname admins:adminname");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name world:world open:false leader:leadername timeLastSet:1400000000000 HQ:64.0,64.0,64.0,0.0,0.0 players:playername,adminname,leadername admins:adminname,leadername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBe1_7_4Through1_7_5()
	{
		//ASSEMBLE
		defaultTeam.setTag("name");
		regularTeam.setTag("name");
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name:name open:true default:true leader: timeLastSet:1400000000000 hq:world,64.0,64.0,64.0,0.0,0.0 players:playername,adminname admins:adminname");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name open:false default:false leader:leadername timeLastSet:1400000000000 hq:world,64.0,64.0,64.0,0.0,0.0 players:playername,adminname,leadername admins:adminname,leadername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBe1_7_4Through1_7_5NoHeadquarters()
	{
		//ASSEMBLE
		defaultTeam.setTag("name");
		regularTeam.setTag("name");
		defaultTeam.setHeadquarters(new NullHeadquarters());
		regularTeam.setHeadquarters(new NullHeadquarters());
		defaultTeam.setTimeHeadquartersLastSet(0L);
		regularTeam.setTimeHeadquartersLastSet(0L);
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name:name open:true default:true leader: timeLastSet:0 hq: players:playername,adminname admins:adminname");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name open:false default:false leader:leadername timeLastSet:0 hq: players:playername,adminname,leadername admins:adminname,leadername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBe1_7_6()
	{
		//ASSEMBLE
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag open:true default:true leader: timeLastSet:1400000000000 hq:world,64.0,64.0,64.0,0.0,0.0 players:playername,adminname admins:adminname");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag open:false default:false leader:leadername timeLastSet:1400000000000 hq:world,64.0,64.0,64.0,0.0,0.0 players:playername,adminname,leadername admins:adminname,leadername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBe1_7_7()
	{
		//name:name tag:name open:false default:false timeHeadquartersSet:1400000000000 hq:world,1.1,1.1,1.1,1.1,1.1 leader:leadername admins:leadername,adminname players:leadername,adminname,playername
		//ASSEMBLE
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name: tag: open:true default:true timeHeadquartersSet:0 hq: leader: admins: players:");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag open:false default:false leader:leadername timeLastSet:1400000000000 hq:world,64.0,64.0,64.0,0.0,0.0 players:playername,adminname,leadername admins:adminname,leadername");
		//ASSERT
		Assert.assertNull(defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBe1_8_0()
	{
		//ASSEMBLE
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag open:true default:true timeHeadquartersSet:1400000000000 hq:world,64.0,64.0,64.0,0.0,0.0 leader: players:playername,adminname admins:adminname");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag open:false default:false leader:leadername timeLastSet:1400000000000 hq:world,64.0,64.0,64.0,0.0,0.0 players:playername,adminname,leadername admins:adminname,leadername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBe1_8_1()
	{
		//name:name tag:tag openJoining:false defaultTeam:false timeHeadquartersLastSet:1402351827503 headquarters:world,-183.4474520173788,75.0,-350.69196682517924,241.19965,71.700005 leader:protocos admins: players:protocos,kmlanglois		//ASSEMBLE
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag openJoining:true defaultTeam:true timeHeadquartersSet:1400000000000 headquarters:world,64.0,64.0,64.0,0.0,0.0 leader: admins:adminname players:playername,adminname");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag openJoining:false defaultTeam:false timeLastSet:1400000000000 headquarters:world,64.0,64.0,64.0,0.0,0.0 leader:leadername admins:adminname,leadername players:playername,adminname,leadername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBeCapitalHeadquarters()
	{
		//ASSEMBLE
		//ACT
		defaultTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag open:true default:true timeHeadquartersSet:1400000000000 Headquarters:world,64.0,64.0,64.0,0.0,0.0 leader: players:playername,adminname admins:adminname");
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag open:false default:false leader:leadername timeLastSet:1400000000000 Headquarters:world,64.0,64.0,64.0,0.0,0.0 players:playername,adminname,leadername admins:adminname,leadername");
		//ASSERT
		Assert.assertEquals(fromString(defaultTeam), defaultTeamProperties);
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBeOnlyLeaderOnTeam()
	{
		//ASSEMBLE
		regularTeam.removePlayer("adminname");
		regularTeam.removePlayer("playername");
		//ACT
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag open:false default:false leader:leadername timeLastSet:1400000000000 Headquarters:world,64.0,64.0,64.0,0.0,0.0 players: admins:");
		//ASSERT
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@Test
	public void ShouldBeLeaderAndAdminOnTeam()
	{
		//ASSEMBLE
		regularTeam.removePlayer("playername");
		//ACT
		regularTeamProperties = LegacyDataTranslator.fromLegacyData("name:name tag:tag open:false default:false leader:leadername timeLastSet:1400000000000 Headquarters:world,64.0,64.0,64.0,0.0,0.0 players: admins:adminname");
		//ASSERT
		Assert.assertEquals(fromString(regularTeam), regularTeamProperties);
	}

	@After
	public void takedown()
	{
	}

	private PropertyList fromString(ITeam team)
	{
		return PropertyList.fromString(team.toString());
	}
}