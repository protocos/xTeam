package me.protocos.xteam.command;

import me.protocos.xteam.FakeXTeam;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.fakeobjects.FakePlayerSender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommandParserTest
{
	public final String command = "/command param1 param2 param3";
	public CommandParser parser;
	private TeamPlugin teamPlugin;

	@Before
	public void setup()
	{
		parser = new CommandParser(command);
		teamPlugin = FakeXTeam.asTeamPlugin();
	}

	@Test
	public void ShouldBeBaseCommand()
	{
		//ASSEMBLE
		//ACT
		String base = parser.getBaseCommand();
		//ASSERT
		Assert.assertEquals("/command", base);
	}

	@Test
	public void ShouldBeParam1()
	{
		//ASSEMBLE
		//ACT
		String param1 = parser.get(0);
		//ASSERT
		Assert.assertEquals("param1", param1);
	}

	@Test
	public void ShouldBeCommandContainerBaseCommand()
	{
		//ASSEMBLE
		CommandContainer container = new CommandContainer(new FakePlayerSender(teamPlugin), "command", "".split(" "));
		//ACT
		String base = container.getCommand();
		//ASSERT
		Assert.assertEquals("/command", base);
	}

	@Test
	public void ShouldBeCommandContainerParam1()
	{
		//ASSEMBLE
		CommandContainer container = new CommandContainer(new FakePlayerSender(teamPlugin), "command", "param1 param2 param3".split(" "));
		//ACT
		String base = container.getArgument(0);
		//ASSERT
		Assert.assertEquals("param1", base);
	}
}
