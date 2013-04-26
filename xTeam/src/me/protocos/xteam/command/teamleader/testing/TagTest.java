package me.protocos.xteam.command.teamleader.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseUserCommand;
import me.protocos.xteam.command.teamleader.Tag;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.exception.*;
import me.protocos.xteam.testing.FakeLocation;
import me.protocos.xteam.testing.FakePlayerSender;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TagTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTagExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Tag(fakePlayerSender, "tag tag");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals("The team tag has been set to tag", fakePlayerSender.getLastMessage());
		Assert.assertEquals("tag", xTeam.tm.getTeam("one").getTag());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamTagExecuteAlreadyExists()
	{
		//ASSEMBLE
		Data.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Tag(fakePlayerSender, "tag two");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamNameConflictsWithTagException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.tm.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamTagExecuteNameNotAlpha()
	{
		//ASSEMBLE
		Data.ALPHA_NUM = true;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Tag(fakePlayerSender, "tag ú�");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamNameNotAlphaException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.tm.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamTagExecutenNmeTooLong()
	{
		//ASSEMBLE
		Data.TEAM_TAG_LENGTH = 10;
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		BaseUserCommand fakeCommand = new Tag(fakePlayerSender, "tag tagiswaytoolong");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamNameTooLongException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.tm.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamTagExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("lonely", new FakeLocation());
		BaseUserCommand fakeCommand = new Tag(fakePlayerSender, "tag tag");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamTagExecuteNotLeader()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("protocos", new FakeLocation());
		BaseUserCommand fakeCommand = new Tag(fakePlayerSender, "tag tag");
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute();
		//ASSERT
		Assert.assertEquals((new TeamPlayerNotLeaderException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertEquals("TeamAwesome", xTeam.tm.getTeam("one").getTag());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}