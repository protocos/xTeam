package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminRename extends ServerAdminCommand
{
	private String teamName, newName;

	public AdminRename()
	{
	}
	public AdminRename(Player sender, CommandParser command)
	{
		super(sender, command);
	}
	@Override
	protected void act()
	{
		Team team = xTeam.tm.getTeam(teamName);
		xTeam.tm.removeTeam(teamName);
		team.setName(newName);
		xTeam.tm.addTeam(team);
		if (!team.containsPlayer(sender.getName()))
			sender.sendMessage("You renamed the team to " + ChatColor.AQUA + newName);
		team.sendMessage("The team has been renamed to " + ChatColor.AQUA + newName + ChatColor.RESET + " by an admin");
	}
	@Override
	public void checkRequirements() throws TeamException
	{
		if (!sender.hasPermission(getPermissionNode()))
		{
			throw new TeamPlayerPermissionException();
		}
		if (parseCommand.size() == 3)
		{
			teamName = parseCommand.get(1);
			newName = parseCommand.get(2);
		}
		else
		{
			throw new TeamInvalidCommandException();
		}
		Team desiredTeam = xTeam.tm.getTeam(teamName);
		if (desiredTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (xTeam.tm.contains(newName) && !desiredTeam.getName().equalsIgnoreCase(newName))
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
		return parseCommand.getBaseCommand() + " rename [Team] [Name]";
	}
}
