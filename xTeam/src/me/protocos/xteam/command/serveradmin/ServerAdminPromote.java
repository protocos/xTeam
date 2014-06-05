package me.protocos.xteam.command.serveradmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.ServerAdminCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ServerAdminPromote extends ServerAdminCommand
{
	private String teamName, playerName;
	private ITeam changeTeam;

	public ServerAdminPromote(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		changeTeam.promote(playerName);
		if (!changeTeam.containsPlayer(player.getName()))
			player.sendMessage("You " + MessageUtil.green("promoted") + " " + playerName);
		ITeamPlayer other = playerFactory.getPlayer(playerName);
		other.sendMessage("You've been " + MessageUtil.green("promoted") + " by an admin");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		changeTeam = teamManager.getTeam(teamName);
		ITeamPlayer playerPromote = playerFactory.getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(playerPromote);
		Requirements.checkTeamExists(teamManager, teamName);
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
