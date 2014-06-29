package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.action.TeleAllHQAction;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleTeleAllHQ extends ConsoleCommand
{
	private TeleAllHQAction action;

	public ConsoleTeleAllHQ(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		this.action = new TeleAllHQAction(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		action.actOn(sender);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("teleport")
				.oneOrMore("all")
				.oneOrMore("head")
				.oneOrMore("quarters")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "team teleallhq";
	}

	@Override
	public String getDescription()
	{
		return "teleports everyone to their headquarters";
	}
}
