package me.protocos.xteam.command.console;

import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
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
		new Message.Builder(!teams.isEmpty() ? "Teams: " + teams.toString().replaceAll("\\[|\\]", "") : "There are no teams").addRecipients(sender).send(log);
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
