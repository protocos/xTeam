package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.BaseConsoleCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamPlayer;
import me.protocos.xteam.exception.TeamDoesNotExistException;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.exception.TeamInvalidCommandException;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleDelete extends BaseConsoleCommand
{
	private String teamName;

	public ConsoleDelete()
	{
		super();
	}

	public ConsoleDelete(ConsoleCommandSender sender, String command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		for (String p : team.getPlayers())
		{
			TeamPlayer player = new TeamPlayer(p);
			if (player.isOnline())
				player.sendMessage("Your team has been deleted by an admin");
		}
		xTeam.tm.removeTeam(teamName);
		originalSender.sendMessage("You have deleted team: " + teamName);
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
	}
	@Override
	public String getPattern()
	{
		return "de" + patternOneOrMore("lete") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return baseCommand + " delete [Team]";
	}
}