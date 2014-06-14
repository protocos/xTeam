package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleOpen extends ConsoleCommand
{
	String teamName;

	public ConsoleOpen(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam team = teamCoordinator.getTeam(teamName);
		team.setOpenJoining(!team.isOpenJoining());
		new Message.Builder("Open joining is now " + (team.isOpenJoining() ? "enabled" : "disabled") + " for " + team.getName()).addRecipients(sender).addRecipients(team).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
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
