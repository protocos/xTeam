package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.command.action.RenameTeamAction;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderRename extends TeamLeaderCommand
{
	private String desiredName;
	private RenameTeamAction renameTeamAction;

	public TeamLeaderRename(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		renameTeamAction = new RenameTeamAction(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		renameTeamAction.actOn(teamLeader, team.getName(), desiredName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		renameTeamAction.checkRequirementsOn(team.getName(), desiredName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("re")
				.oneOrMore("name")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.rename";
	}

	@Override
	public String getUsage()
	{
		return "/team rename [Name]";
	}

	@Override
	public String getDescription()
	{
		return "rename the team";
	}
}
