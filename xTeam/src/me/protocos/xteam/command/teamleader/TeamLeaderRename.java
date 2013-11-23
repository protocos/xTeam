package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.core.ITeamPlayer;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderRename extends TeamLeaderCommand
{
	private String desiredName;

	public TeamLeaderRename()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		xTeam.getInstance().getTeamManager().removeTeam(team.getName());
		team.setName(desiredName);
		xTeam.getInstance().getTeamManager().addTeam(team);
		for (ITeamPlayer mate : teamPlayer.getOnlineTeammates())
		{
			mate.sendMessage("The team has been " + ChatColorUtil.positiveMessage("renamed") + " to " + desiredName);
		}
		teamPlayer.sendMessage("You " + ChatColorUtil.positiveMessage("renamed") + " the team to " + desiredName);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		Requirements.checkTeamNameTooLong(desiredName);
		Requirements.checkTeamNameAlphaNumeric(desiredName);
		Requirements.checkTeamNameAlreadyUsed(desiredName, team);
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
