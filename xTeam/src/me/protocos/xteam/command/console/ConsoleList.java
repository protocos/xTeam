package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleList extends BaseConsoleCommand
{
	public ConsoleList()
	{
		super();
	}
	public ConsoleList(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		List<String> teams = xTeam.tm.getAllTeamNames();
		String message = "Teams: " + teams.toString().replaceAll("[\\[\\]]", "");
		originalSender.sendMessage(message);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 1)
		{
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("list") + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " list";
	}
}
