package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.InfoAction;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleInfo extends ConsoleCommand
{
	private String other;
	private InfoAction infoAction;

	public ConsoleInfo(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		infoAction = new InfoAction(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		infoAction.actOn(sender, other);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		other = commandContainer.getArgument(1);
		infoAction.checkRequirements(other);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("info")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "team info [Player/Team]";
	}

	@Override
	public String getDescription()
	{
		return "get info on player/team";
	}
}
