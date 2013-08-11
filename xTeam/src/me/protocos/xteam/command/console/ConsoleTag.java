package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamNameConflictsWithTagException;
import me.protocos.xteam.core.exception.TeamNameNotAlphaException;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.command.CommandSender;

public class ConsoleTag extends ConsoleCommand
{
	private String teamName, newTag;

	public ConsoleTag()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeam.tm.getTeam(teamName);
		team.setTag(newTag);
		originalSender.sendMessage("The team tag has been set to " + newTag);
		team.sendMessage("The team tag has been set to " + newTag + " by an admin");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		newTag = parseCommand.get(2);
		Team desiredTeam = xTeam.tm.getTeam(teamName);
		if (desiredTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (!newTag.equalsIgnoreCase(desiredTeam.getName()) && StringUtil.toLowerCase(xTeam.tm.getAllTeamNames()).contains(newTag.toLowerCase()))
		{
			throw new TeamNameConflictsWithTagException();
		}
		if (Data.ALPHA_NUM && !newTag.matches(ALPHA_NUMERIC))
		{
			throw new TeamNameNotAlphaException();
		}
	}
	@Override
	public String getPattern()
	{
		return patternOneOrMore("tag") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return "/team tag [Team] [Tag]";
	}
}
