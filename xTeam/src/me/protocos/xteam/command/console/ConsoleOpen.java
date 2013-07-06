package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleOpen extends BaseConsoleCommand
{
	String teamName;

	public ConsoleOpen(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.setOpenJoining(!team.isOpenJoining());
		if (team.isOpenJoining())
			originalSender.sendMessage("UserOpen joining is now enabled for team " + teamName);
		else
			originalSender.sendMessage("UserOpen joining is now disabled for team " + teamName);
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (parseCommand.size() == 2)
		{
			teamName = parseCommand.get(1);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("open") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " open [Team]";
	}
}
