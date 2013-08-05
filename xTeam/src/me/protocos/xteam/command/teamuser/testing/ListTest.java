package me.protocos.xteam.command.teamuser.testing;

import static me.protocos.xteam.testing.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.command.teamuser.UserList;
import me.protocos.xteam.command.CommandParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTeamUserListExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserList();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team list"));
		//ASSERT
		Assert.assertEquals("Teams: ONE, two, red, blue", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}
