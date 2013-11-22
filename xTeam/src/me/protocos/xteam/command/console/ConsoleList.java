package me.protocos.xteam.command.console;

import java.util.List;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.ConsoleCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleList extends ConsoleCommand
{
	public ConsoleList()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		List<String> teams = xTeam.getInstance().getTeamManager().getAllTeams().getOrder();
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
		return "/team list";
	}

	@Override
	public String getDescription()
	{
		return "list all teams on the server";
	}
}
