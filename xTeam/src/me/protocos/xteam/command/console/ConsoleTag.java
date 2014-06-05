package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleTag extends ConsoleCommand
{
	private String teamName, desiredTag;

	public ConsoleTag(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam team = teamManager.getTeam(teamName);
		team.setTag(desiredTag);
		sender.sendMessage("The team tag has been " + MessageUtil.green("set") + " to " + desiredTag);
		team.sendMessage("The team tag has been " + MessageUtil.green("set") + " to " + desiredTag + " by an admin");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		desiredTag = commandContainer.getArgument(2);
		ITeam team = teamManager.getTeam(teamName);
		Requirements.checkTeamExists(teamManager, teamName);
		Requirements.checkTeamNameAlreadyUsed(teamManager, desiredTag, team);
		Requirements.checkTeamNameAlphaNumeric(desiredTag);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("tag")
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
		return "/team tag [Team] [Tag]";
	}

	@Override
	public String getDescription()
	{
		return "set team tag";
	}
}
