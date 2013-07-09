package me.protocos.xteam.command.testing;

import junit.framework.Assert;
import me.protocos.xteam.command.CommandParser;
import org.junit.Before;
import org.junit.Test;

public class CommandParserTest
{
	public final String command = "/command param1 param2 param3";
	public CommandParser parser;

	@Before
	public void setup()
	{
		parser = new CommandParser(command);
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
}
