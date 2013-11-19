package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.command.CommandSender;

public class ServerAdminSetLeader extends ServerAdminCommand
{
	private String teamName, playerName;

	public ServerAdminSetLeader()
	{
		super();
	}

	@Override
	protected void act(CommandSender originalSender, CommandParser parseCommand)
	{
		ITeamPlayer playerSet = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Team playerTeam = playerSet.getTeam();
		playerTeam.setLeader(playerName);
		if (playerSet.isOnline() && !playerSet.getName().equals(originalSender.getName()))
			playerSet.sendMessage("You are now the " + ChatColorUtil.positiveMessage("team leader"));
		ITeamPlayer previousLeader = xTeam.getInstance().getPlayerManager().getPlayer(playerTeam.getLeader());
		if (previousLeader.isOnline() && !previousLeader.getName().equals(originalSender.getName()))
			previousLeader.sendMessage(playerName + " is now the " + ChatColorUtil.positiveMessage("team leader"));
		originalSender.sendMessage(playerName + " is now the " + ChatColorUtil.positiveMessage("team leader") + " for " + playerTeam.getName());
	}

	@Override
	public void checkRequirements(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		teamName = parseCommand.get(1);
		playerName = parseCommand.get(2);
		Team desiredTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		ITeamPlayer playerSet = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Team playerTeam = playerSet.getTeam();
		Requirements.checkPlayerHasPlayedBefore(playerSet);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasTeam(playerSet);
		Requirements.checkPlayerOnTeam(playerSet, desiredTeam);
		Requirements.checkTeamIsDefault(playerTeam);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
				.oneOrMore("leader")
				.whiteSpace()
				.anyString()
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.serveradmin.core.setleader";
	}

	@Override
	public String getUsage()
	{
		return "/team setleader [Team] [Player]";
	}

	@Override
	public String getDescription()
	{
		return "set leader of team";
	}
}
