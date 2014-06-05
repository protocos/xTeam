package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderRename extends TeamLeaderCommand
{
	private String desiredName;

	public TeamLeaderRename(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		teamManager.renameTeam(team, desiredName);
		for (ITeamPlayer mate : teamPlayer.getOnlineTeammates())
		{
			mate.sendMessage("The team has been " + MessageUtil.green("renamed") + " to " + desiredName);
		}
		teamPlayer.sendMessage("You " + MessageUtil.green("renamed") + " the team to " + desiredName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamNameAlreadyUsed(teamManager, desiredName, team);
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
