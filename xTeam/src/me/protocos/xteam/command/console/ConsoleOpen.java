package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.OpenAction;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleOpen extends ConsoleCommand
{
	private OpenAction action;
	private String teamName;
	private ITeam changeTeam;

	public ConsoleOpen(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		action = new OpenAction(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		action.actOn(sender, changeTeam);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		changeTeam = teamCoordinator.getTeam(teamName);
		action.checkRequirements(teamCoordinator, teamName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("open")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "team open [Team]";
	}

	@Override
	public String getDescription()
	{
		return "open team to public joining";
	}
}
