package me.protocos.xteam.command.console;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.api.entity.ITeamPlayer;
import me.protocos.xteam.api.model.ITeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleRemove extends ConsoleCommand
{
	private String teamName, playerName;
	private ITeamPlayer changePlayer;

	public ConsoleRemove()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam changeTeam = changePlayer.getTeam();
		changeTeam.removePlayer(playerName);
		sender.sendMessage("You" + ChatColorUtil.negativeMessage(" removed ") + playerName + " from " + teamName);
		if (changePlayer.isOnline())
			changePlayer.sendMessage("You've been " + ChatColorUtil.negativeMessage("removed") + " from " + changeTeam.getName());
		if (changeTeam.isEmpty())
		{
			sender.sendMessage(teamName + " has been " + ChatColorUtil.negativeMessage("disbanded"));
			xTeam.getInstance().getTeamManager().removeTeam(changeTeam.getName());
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		playerName = commandContainer.getArgument(2);
		changePlayer = xTeam.getInstance().getPlayerManager().getPlayer(playerName);
		Requirements.checkPlayerHasPlayedBefore(changePlayer);
		Requirements.checkPlayerHasTeam(changePlayer);
		Requirements.checkPlayerLeaderLeaving(changePlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("re")
				.oneOrMore("move")
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
		return "/team remove [Team] [Player]";
	}

	@Override
	public String getDescription()
	{
		return "remove player from team";
	}
}
