package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.SetTeamAction;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleSet extends ConsoleCommand
{
	private String playerName, teamName;
	private SetTeamAction set;

	public ConsoleSet(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		set.actOn(playerName, teamName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		playerName = commandContainer.getArgument(1);
		teamName = commandContainer.getArgument(2);
		set = new SetTeamAction(teamPlugin, sender);
		set.checkRequirementsOn(playerName, teamName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("set")
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
		return "/team set [Player] [Team]";
	}

	@Override
	public String getDescription()
	{
		return "set team of player";
	}
}
