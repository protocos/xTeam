package me.protocos.xteam.command.console;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleDemote extends ConsoleCommand
{
	private String teamName, playerName;

	public ConsoleDemote()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam team = XTeam.getInstance().getTeamManager().getTeam(teamName);
		team.demote(playerName);
		sender.sendMessage("You" + ChatColorUtil.negativeMessage(" demoted ") + playerName);
		ITeamPlayer other = XTeam.getInstance().getPlayerManager().getPlayer(playerName);
		if (other.isOnline())
			other.sendMessage("You've been " + ChatColorUtil.negativeMessage("demoted"));
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		ITeamPlayer player = XTeam.getInstance().getPlayerManager().getPlayer(playerName);
		ITeam team = XTeam.getInstance().getTeamManager().getTeam(teamName);
		Requirements.checkTeamExists(teamName);
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
