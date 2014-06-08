package me.protocos.xteam.command.console;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleList extends ConsoleCommand
{
	public ConsoleList(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		List<String> teams = teamCoordinator.getTeams().getOrder();
		String message = "Teams: " + teams.toString().replaceAll("\\[|\\]", "");
		if (teams.isEmpty())
			sender.sendMessage("There are no teams");
		else
			sender.sendMessage(message);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("list")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getUsage()
	{
		return "team list";
	}

	@Override
	public String getDescription()
	{
		return "list all teams on the server";
	}
}
