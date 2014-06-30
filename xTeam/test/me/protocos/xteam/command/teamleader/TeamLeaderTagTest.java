package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.fakeobjects.FakePlayer;
import me.protocos.xteam.fakeobjects.FakeXTeam;
import me.protocos.xteam.util.CommandUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TeamLeaderTagTest
{
	private TeamPlugin teamPlugin;
	private TeamLeaderCommand fakeCommand;
	private ITeamCoordinator teamCoordinator;

	@Before
	public void setup()
	{
		teamPlugin = FakeXTeam.asTeamPlugin();
		fakeCommand = new TeamLeaderTag(teamPlugin);
		teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	@Test
	public void ShouldBeTeamLeaderTag()
	{
		Assert.assertTrue("tag TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tag TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tag TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("t TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertTrue("ta TEAM".matches(fakeCommand.getPattern()));
		Assert.assertTrue("tg TEAM ".matches(fakeCommand.getPattern()));
		Assert.assertFalse("tg TEAM sdfhkabkl".matches(fakeCommand.getPattern()));
		Assert.assertTrue(fakeCommand.getUsage().replaceAll("Page", "1").replaceAll("[\\[\\]\\{\\}]", "").matches("/team " + fakeCommand.getPattern()));
	}

	@Test
	public void ShouldBeTagExecute()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tag tag");
		//ASSERT
		Assert.assertEquals("The team tag has been set to tag", fakePlayerSender.getLastMessages());
		Assert.assertEquals("tag", teamCoordinator.getTeam("one").getTag());
		Assert.assertTrue(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteAlreadyExists()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tag two");
		//ASSERT
		Assert.assertEquals((new TeamNameAlreadyInUseException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteNameNotAlpha()
	{
		//ASSEMBLE
		Configuration.ALPHA_NUM = true;
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tag ��Eåm");
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecutenNmeTooLong()
	{
		//ASSEMBLE
		Configuration.TEAM_NAME_LENGTH = 10;
		FakePlayer fakePlayerSender = FakePlayer.get("kmlanglois");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tag tagiswaytoolong");
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("Lonely");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tag tag");
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@Test
	public void ShouldBeTeamTagExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayer fakePlayerSender = FakePlayer.get("protocos");
		//ACT
		boolean fakeExecuteResponse = CommandUtil.execute(fakePlayerSender, fakeCommand, "tag tag");
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessages());
		Assert.assertEquals("TeamAwesome", teamCoordinator.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}

	@After
	public void takedown()
	{
	}
}