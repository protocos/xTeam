package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.core.exception.TeamIsDefaultException;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleDisband extends BaseConsoleCommand
{
	private String teamName;

	public ConsoleDisband(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.sendMessage("Your team has been disbanded by an admin");
		xTeam.tm.removeTeam(teamName);
		originalSender.sendMessage("You disbanded " + teamName);
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
		Team team = xTeam.tm.getTeam(teamName);
		if (team == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (team.isDefaultTeam())
		{
			throw new TeamIsDefaultException();
		}
	}
	@Override
	public String getPattern()
	{
		return "disband" + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " disband [Team]";
	}
}