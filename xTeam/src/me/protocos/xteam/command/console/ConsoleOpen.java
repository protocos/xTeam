package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
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
		if (team.isOpenJoining())
			sender.sendMessage("Open joining is now " + MessageUtil.green("enabled") + " for team " + teamName);
		else
			sender.sendMessage("Open joining is now " + MessageUtil.red("disabled") + " for team " + teamName);
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
		return "/team open [Team]";
	}

	@Override
	public String getDescription()
	{
		return "open team to public joining";
	}
}
