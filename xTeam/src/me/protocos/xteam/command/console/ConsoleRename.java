package me.protocos.xteam.command.console;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.ConsoleCommand;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class ConsoleRename extends ConsoleCommand
{
	private String teamName, desiredName;

	public ConsoleRename(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam team = teamCoordinator.getTeam(teamName);
		teamCoordinator.renameTeam(team, desiredName);
		sender.sendMessage("You " + MessageUtil.green("renamed") + " the team to " + desiredName);
		team.sendMessage("The team has been " + MessageUtil.green("renamed") + " to " + desiredName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		teamName = commandContainer.getArgument(1);
		desiredName = commandContainer.getArgument(2);
		Requirements.checkTeamExists(teamCoordinator, teamName);
		Requirements.checkTeamAlreadyExists(teamCoordinator, desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("re")
				.oneOrMore("name")
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
		return "/team rename [Team] [Name]";
	}

	@Override
	public String getDescription()
	{
		return "rename a team";
	}
}
