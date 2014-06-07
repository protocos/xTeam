package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminSetLeader extends ServerAdminCommand
{
	private String teamName, playerName;

	public ServerAdminSetLeader(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeamPlayer playerSet = playerFactory.getPlayer(playerName);
		ITeam playerTeam = playerSet.getTeam();
		playerTeam.setLeader(playerName);
		if (playerSet.isOnline() && !playerSet.getName().equals(player.getName()))
			playerSet.sendMessage("You are now the " + MessageUtil.green("team leader"));
		ITeamPlayer previousLeader = playerFactory.getPlayer(playerTeam.getLeader());
		if (previousLeader.isOnline() && !previousLeader.getName().equals(player.getName()))
			previousLeader.sendMessage(playerName + " is now the " + MessageUtil.green("team leader"));
		player.sendMessage(playerName + " is now the " + MessageUtil.green("team leader") + " for " + playerTeam.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		ITeam desiredTeam = teamCoordinator.getTeam(teamName);
		ITeamPlayer playerSet = playerFactory.getPlayer(playerName);
		ITeam playerTeam = playerSet.getTeam();
		Requirements.checkPlayerHasPlayedBefore(playerSet);
		Requirements.checkTeamExists(teamCoordinator, teamName);
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
