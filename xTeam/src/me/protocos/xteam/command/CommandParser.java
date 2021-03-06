package me.protocos.xteam.command;

import java.util.List;
import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.util.PatternBuilder;

public class CommandParser
{
	private final String baseCommand;
	private final List<String> parameters;

	public CommandParser(String command)
	{
		List<String> parsed = CommonUtil.split(command, new PatternBuilder().whiteSpace().toString());
		baseCommand = parsed.get(0);
		parameters = parsed.subList(1, parsed.size());
	}

	public String get(int index)
	{
		return parameters.get(index);
	}

	public String getBaseCommand()
	{
		return baseCommand;
	}

	public String getCommandWithoutID()
	{
		return CommonUtil.concatenate(parameters.toArray());
	}

	public int size()
	{
		return parameters.size();
	}

	public String toString()
	{
		String output = baseCommand;
		for (String param : parameters)
		{
			output += " " + param;
		}
		return output;
	}
}
