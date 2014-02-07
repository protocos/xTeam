package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminSetLeader extends ServerAdminCommand
{
	private String teamName, playerName;

	public ServerAdminSetLeader()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeamPlayer playerSet = XTeam.getInstance().getPlayerManager().getPlayer(playerName);
		ITeam playerTeam = playerSet.getTeam();
		playerTeam.setLeader(playerName);
		if (playerSet.isOnline() && !playerSet.getName().equals(player.getName()))
			playerSet.sendMessage("You are now the " + ChatColorUtil.positiveMessage("team leader"));
		ITeamPlayer previousLeader = XTeam.getInstance().getPlayerManager().getPlayer(playerTeam.getLeader());
		if (previousLeader.isOnline() && !previousLeader.getName().equals(player.getName()))
			previousLeader.sendMessage(playerName + " is now the " + ChatColorUtil.positiveMessage("team leader"));
		player.sendMessage(playerName + " is now the " + ChatColorUtil.positiveMessage("team leader") + " for " + playerTeam.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		ITeam desiredTeam = XTeam.getInstance().getTeamManager().getTeam(teamName);
		ITeamPlayer playerSet = XTeam.getInstance().getPlayerManager().getPlayer(playerName);
		ITeam playerTeam = playerSet.getTeam();
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
		return "xteam.core.serveradmin.setleader";
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
