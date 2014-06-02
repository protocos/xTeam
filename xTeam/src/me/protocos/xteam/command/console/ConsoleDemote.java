package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleDemote extends ConsoleCommand
{
	private String teamName, playerName;

	public ConsoleDemote(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam team = teamManager.getTeam(teamName);
		team.demote(playerName);
		sender.sendMessage("You" + MessageUtil.negativeMessage(" demoted ") + playerName);
		ITeamPlayer other = playerFactory.getPlayer(playerName);
		if (other.isOnline())
			other.sendMessage("You've been " + MessageUtil.negativeMessage("demoted"));
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		ITeamPlayer player = playerFactory.getPlayer(playerName);
		ITeam team = teamManager.getTeam(teamName);
		Requirements.checkTeamExists(teamManager, teamName);
		Requirements.checkPlayerHasPlayedBefore(player);
		Requirements.checkPlayerHasTeam(player);
		Requirements.checkPlayerIsTeamAdmin(player);
		Requirements.checkPlayerOnTeam(player, team);
		Requirements.checkPlayerLeaderDemote(player);
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
