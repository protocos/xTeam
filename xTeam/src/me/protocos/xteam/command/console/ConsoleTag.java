package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;
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
		ITeam changeTeam = teamCoordinator.getTeam(teamName);
		changeTeam.setTag(desiredTag);
		sender.sendMessage("The team tag has been " + MessageUtil.green("set") + " to " + desiredTag);
		changeTeam.sendMessage("The team tag has been " + MessageUtil.green("set") + " to " + desiredTag + " by an admin");
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		desiredTag = commandContainer.getArgument(2);
		ITeam changeTeam = teamCoordinator.getTeam(teamName);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		Requirements.checkTeamNameAlphaNumeric(desiredTag);
		Requirements.checkTeamRenameInUse(teamCoordinator, changeTeam, desiredTag);
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
		return "team tag [Team] [Tag]";
	}

	@Override
	public String getDescription()
	{
		return "set team tag";
	}
}
