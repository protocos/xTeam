package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.api.command.TeamLeaderCommand;
import me.protocos.xteam.api.entity.ITeamPlayer;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderTag extends TeamLeaderCommand
{
	private String desiredTag;

	public TeamLeaderTag()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.setTag(desiredTag);
		teamPlayer.sendMessage("The team tag has been " + ChatColorUtil.positiveMessage("set") + " to " + desiredTag);
		for (ITeamPlayer mate : teamPlayer.getOnlineTeammates())
		{
			mate.sendMessage("The team tag has been " + ChatColorUtil.positiveMessage("set") + " to " + desiredTag);
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredTag = commandContainer.getArgument(1);
		Requirements.checkTeamNameTooLong(desiredTag);
		Requirements.checkTeamNameAlphaNumeric(desiredTag);
		Requirements.checkTeamNameAlreadyUsed(desiredTag, team);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("tag")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.leader.tag";
	}

	@Override
	public String getUsage()
	{
		return "/team tag [Tag]";
	}

	@Override
	public String getDescription()
	{
		return "set the team tag";
	}
}
