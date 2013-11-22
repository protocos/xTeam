package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ServerAdminCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminDemote extends ServerAdminCommand
{
	private String teamName, playerName;
	private Team changeTeam;

	public ServerAdminDemote()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		changeTeam.demote(playerName);
		if (!changeTeam.containsPlayer(player.getName()))
			player.sendMessage("You " + ChatColorUtil.negativeMessage("demoted") + " " + playerName);
		ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		other.sendMessage("You have been " + ChatColorUtil.negativeMessage("demoted") + " by an admin");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		ITeamPlayer playerDemote = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(playerDemote);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasTeam(playerDemote);
		Requirements.checkPlayerIsTeamAdmin(playerDemote);
		Requirements.checkPlayerOnTeam(playerDemote, changeTeam);
		Requirements.checkPlayerLeaderDemote(playerDemote);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("demote")
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
		return "xteam.serveradmin.core.demote";
	}

	@Override
	public String getUsage()
	{
		return "/team demote [Team] [Player]";
	}

	@Override
	public String getDescription()
	{
		return "demote team admin";
	}
}
