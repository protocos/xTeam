package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.command.action.RemoveAction;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderRemove extends TeamLeaderCommand
{
	private String teamName, playerName;
	private RemoveAction removeAction;

	public TeamLeaderRemove(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		this.removeAction = new RemoveAction(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		this.removeAction.actOn(teamPlayer, teamName, playerName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		playerName = commandContainer.getArgument(1);
		teamName = team.getName();
		this.removeAction.checkRequirements(teamName, playerName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("re")
				.oneOrMore("move")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.remove";
	}

	@Override
	public String getUsage()
	{
		return "/team remove [Player]";
	}

	@Override
	public String getDescription()
	{
		return "remove player from your team";
	}
}
