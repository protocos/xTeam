package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ServerAdminHeadquarters extends ServerAdminCommand
{
	private String teamName;
	private Team changeTeam;

	public ServerAdminHeadquarters()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		teamPlayer.teleport(changeTeam.getHeadquarters().getLocation());
		originalSender.sendMessage("You have been " + ChatColorUtil.positiveMessage("teleported") + " to the headquarters of team " + teamName);
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		Requirements.checkTeamExists(teamName);
		Requirements.checkTeamHasHeadquarters(changeTeam);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("head")
				.oneOrMore("quarters")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
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

	@Override
	public String getDescription()
	{
		return "teleport to team headquarters for team";
	}
}
