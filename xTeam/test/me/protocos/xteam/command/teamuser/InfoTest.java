package me.protocos.xteam.command.teamuser;

import static me.protocos.xteam.StaticTestFunctions.mockData;
import junit.framework.Assert;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.fakeobjects.FakeLocation;
import me.protocos.xteam.api.fakeobjects.FakePlayerSender;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.UserCommand;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamPlayerHasNoTeamException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InfoTest
{
	@Before
	public void setup()
	{
		//MOCK data
		mockData();
	}
	@Test
	public void ShouldBeTeamUserInfoExecute()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team info"));
		//ASSERT
		Assert.assertEquals("Team Name - ONE\n" +
				"Team Tag - TeamAwesome\n" +
				"Team Leader - kmlanglois\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - X:170 Y:65 Z:209\n" +
				"Teammates online:\n" +
				"    kmlanglois Health: 100% Location: 0 64 0 in \"world\"\n" +
				"    protocos Health: 100% Location: 0 64 0 in \"world\"", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserInfoExecute2()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team info two"));
		//ASSERT
		Assert.assertEquals("Team Name - two\n" +
				"Team Leader - mastermind\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - None set\n" +
				"Teammates online:\n" +
				"    mastermind", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserInfoExecute3()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team info mastermind"));
		//ASSERT
		Assert.assertEquals("Team Name - two\n" +
				"Team Leader - mastermind\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - None set\n" +
				"Teammates online:\n" +
				"    mastermind", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserInfoExecute4()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team info red"));
		//ASSERT
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserInfoExecute5()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team info strandedhelix"));
		//ASSERT
		Assert.assertEquals("Team Name - red\n" +
				"Team Tag - RED\n" +
				"Team Joining - Open\n" +
				"Team Headquarters - None set\n" +
				"Teammates offline:\n" +
				"    strandedhelix", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserInfoExecute6()
	{
		//ASSEMBLE
		xTeam.getTeamManager().getTeam("red").addPlayer("Lonely");
		xTeam.getTeamManager().getTeam("red").promote("strandedhelix");
		xTeam.getTeamManager().getTeam("red").promote("Lonely");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("strandedhelix", new FakeLocation());
		UserCommand fakeCommand = new UserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team info"));
		//ASSERT
		Assert.assertEquals("Team Name - red\n" +
				"Team Tag - RED\n" +
				"Team Admins - strandedhelix, Lonely\n" +
				"Team Joining - Open\n" +
				"Team Headquarters - None set\n" +
				"Teammates online:\n" +
				"    Lonely Health: 100% Location: 0 64 0 in \"world\"\n" +
				"Teammates offline:\n" +
				"    strandedhelix was last online on Dec 31 @ 6:00 PM", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserInfoExecute7()
	{
		//ASSEMBLE
		xTeam.getTeamManager().getTeam("one").promote("protocos");
		FakePlayerSender fakePlayerSender = new FakePlayerSender("kmlanglois", new FakeLocation());
		UserCommand fakeCommand = new UserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team info"));
		//ASSERT
		Assert.assertEquals("Team Name - ONE\n" +
				"Team Tag - TeamAwesome\n" +
				"Team Leader - kmlanglois\n" +
				"Team Admins - protocos\n" +
				"Team Joining - Closed\n" +
				"Team Headquarters - X:170 Y:65 Z:209\n" +
				"Teammates online:\n" +
				"    kmlanglois Health: 100% Location: 0 64 0 in \"world\"\n" +
				"    protocos Health: 100% Location: 0 64 0 in \"world\"", fakePlayerSender.getLastMessage());
		Assert.assertTrue(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserInfoExecuteNoTeam()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team info"));
		//ASSERT
		Assert.assertEquals((new TeamPlayerHasNoTeamException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@Test
	public void ShouldBeTeamUserInfoExecuteTeamNotExists()
	{
		//ASSEMBLE
		FakePlayerSender fakePlayerSender = new FakePlayerSender("Lonely", new FakeLocation());
		UserCommand fakeCommand = new UserInfo();
		//ACT
		boolean fakeExecuteResponse = fakeCommand.execute(fakePlayerSender, new CommandParser("/team info three"));
		//ASSERT
		Assert.assertEquals((new TeamDoesNotExistException()).getMessage(), fakePlayerSender.getLastMessage());
		Assert.assertFalse(fakeExecuteResponse);
	}
	@After
	public void takedown()
	{
	}
}