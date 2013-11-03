package me.protocos.xteam.command.serveradmin;

import static me.protocos.xteam.util.StringUtil.*;
import java.io.InvalidClassException;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.TeamHeadquarters;
import me.protocos.xteam.core.exception.TeamDoesNotExistException;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public class AdminSetHeadquarters extends ServerAdminCommand
{
	private String teamName;
	private Team changeTeam;

	public AdminSetHeadquarters()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		changeTeam.setHQ(new TeamHeadquarters(teamPlayer.getLocation()));
		originalSender.sendMessage("You set the team headquarters for team " + teamName);
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
	}
	@Override
	public String getPattern()
	{
		return "set" + patternOneOrMore("hq") + WHITE_SPACE + ANY_CHARS + OPTIONAL_WHITE_SPACE;
	}
	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.sethq";
	}
	@Override
	public String getUsage()
	{
		return "/team sethq [Team]";
	}
}
