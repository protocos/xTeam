package me.protocos.xteam.command.console;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamAlreadyExistsException;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamNameNotAlphaException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ConsoleRename extends ConsoleCommand
{
	private String teamName, newName;

	public ConsoleRename()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		Team team = xTeam.getTeamManager().getTeam(teamName);
		xTeam.getTeamManager().removeTeam(teamName);
		team.setName(newName);
		xTeam.getTeamManager().addTeam(team);
		originalSender.sendMessage("You renamed the team to " + newName);
		team.sendMessage("The team has been renamed to " + ChatColor.AQUA + newName);
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		newName = parseCommand.get(2);
		Team desiredTeam = xTeam.getTeamManager().getTeam(teamName);
		if (desiredTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (xTeam.getTeamManager().contains(newName) && !desiredTeam.getName().equalsIgnoreCase(newName))
		{
			throw new TeamAlreadyExistsException();
		}
		if (Data.ALPHA_NUM && !newName.matches(ALPHA_NUMERIC))
		{
			throw new TeamNameNotAlphaException();
		}
	}
	@Override
	public String getPattern()
	{
		return "re" + patternOneOrMore("name") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getUsage()
	{
		return "/team rename [Team] [Name]";
	}
}
