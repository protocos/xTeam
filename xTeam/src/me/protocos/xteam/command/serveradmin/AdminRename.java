package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamAlreadyExistsException;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamNameNotAlphaException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminRename extends ServerAdminCommand
{
	private String teamName, newName;
	private Team changeTeam;

	public AdminRename()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		xTeam.tm.removeTeam(teamName);
		changeTeam.setName(newName);
		xTeam.tm.addTeam(changeTeam);
		if (!changeTeam.containsPlayer(originalSender.getName()))
			originalSender.sendMessage("You renamed the team to " + ChatColor.AQUA + newName);
		changeTeam.sendMessage("The team has been renamed to " + ChatColor.AQUA + newName + ChatColor.RESET + " by an admin");
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		newName = parseCommand.get(2);
		changeTeam = xTeam.tm.getTeam(teamName);
		if (changeTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (xTeam.tm.contains(newName) && !changeTeam.getName().equalsIgnoreCase(newName))
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
		return patternOneOrMore("re") + patternOneOrMore("name") + WHITE_SPACE + ANY_CHARS + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.rename";
	}
	@Override
	public String getUsage()
	{
		return "/team rename [Team] [Name]";
	}
}
