package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.RemoveAction;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleRemove extends ConsoleCommand
{
	private String teamName, playerName;
	private RemoveAction removeAction;

	public ConsoleRemove(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		this.removeAction = new RemoveAction(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		this.removeAction.actOn(sender, teamName, playerName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		playerName = commandContainer.getArgument(1);
		teamName = commandContainer.getArgument(2);
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
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "/team remove [Player] [Team]";
	}

	@Override
	public String getDescription()
	{
		return "remove player from team";
	}
}
