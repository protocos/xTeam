package me.protocos.xteam.command.teamleader;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamLeaderCommand;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamLeaderTag extends TeamLeaderCommand
{
	private String desiredTag;

	public TeamLeaderTag(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.setTag(desiredTag);
		new Message.Builder("The team tag has been " + MessageUtil.green("set") + " to " + desiredTag).addRecipients(teamLeader).send(log);
		for (ITeamPlayer mate : teamLeader.getOnlineTeammates())
		{
			new Message.Builder("The team tag has been " + MessageUtil.green("set") + " to " + desiredTag).addRecipients(mate).send(log);
		}
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredTag = commandContainer.getArgument(1);
		Requirements.checkTeamNameTooLong(desiredTag);
		Requirements.checkTeamNameAlphaNumeric(desiredTag);
		Requirements.checkTeamRenameInUse(teamCoordinator, team, desiredTag);
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
