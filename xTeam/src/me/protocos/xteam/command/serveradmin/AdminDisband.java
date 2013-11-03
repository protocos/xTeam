package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamIsDefaultException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminDisband extends ServerAdminCommand
{
	private String teamName;
	private Team changeTeam;

	public AdminDisband()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.sendMessage("Your team has been " + ChatColor.RED + "disbanded" + ChatColor.RESET + " by an admin");
		xTeam.getTeamManager().removeTeam(teamName);
		originalSender.sendMessage("You " + ChatColor.RED + "disbanded" + ChatColor.RESET + " " + teamName);
	}
	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, InvalidClassException
	{
		super.checkRequirements(originalSender, parseCommand);
		teamName = parseCommand.get(1);
		changeTeam = xTeam.getTeamManager().getTeam(teamName);
		if (changeTeam == null)
		{
			throw new TeamDoesNotExistException();
		}
		if (changeTeam.isDefaultTeam())
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
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.disband";
	}
	@Override
	public String getUsage()
	{
		return "/team disband [Team]";
	}
}
