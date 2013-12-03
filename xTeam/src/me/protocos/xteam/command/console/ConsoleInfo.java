package me.protocos.xteam.command.console;

import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.InfoAction;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleInfo extends ConsoleCommand
{
	private String other;
	private InfoAction info;

	public ConsoleInfo()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		info.actOn(sender, other);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		other = commandContainer.getArgument(1);
		info = new InfoAction();
		info.checkRequirements(other);
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
		return "/team info [Player/Team]";
	}

	@Override
	public String getDescription()
	{
		return "get info on player/team";
	}
}
