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

public class ServerAdminPromote extends ServerAdminCommand
{
	private String teamName, playerName;
	private Team changeTeam;

	public ServerAdminPromote()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		changeTeam.promote(playerName);
		if (!changeTeam.containsPlayer(player.getName()))
			player.sendMessage("You " + ChatColorUtil.positiveMessage("promoted") + " " + playerName);
		ITeamPlayer other = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		other.sendMessage("You've been " + ChatColorUtil.positiveMessage("promoted") + " by an admin");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		changeTeam = xTeam.getInstance().getTeamManager().getTeam(teamName);
		ITeamPlayer playerPromote = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(playerPromote);
		Requirements.checkTeamExists(teamName);
		Requirements.checkPlayerHasTeam(playerPromote);
		Requirements.checkPlayerOnTeam(playerPromote, changeTeam);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("promote")
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
		return "xteam.core.serveradmin.promote";
	}

	@Override
	public String getUsage()
	{
		return "/team promote [Team] [Player]";
	}

	@Override
	public String getDescription()
	{
		return "promote player to admin";
	}
}
