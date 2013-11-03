package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.core.exception.TeamNoHeadquartersException;
import org.bukkit.command.CommandSender;

public class AdminHeadquarters extends ServerAdminCommand
{
	private String teamName;
	private Team changeTeam;

	public AdminHeadquarters()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		teamPlayer.teleport(changeTeam.getHeadquarters());
		originalSender.sendMessage("You have been teleported to the headquarters of team " + teamName);
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
		if (!changeTeam.hasHeadquarters())
		{
			throw new TeamNoHeadquartersException();
		}
	}
	@Override
	public String getPattern()
	{
		return "hq" + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.hq";
	}
	@Override
	public String getUsage()
	{
		return "/team hq [Team]";
	}
}
