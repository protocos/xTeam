package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserJoin extends TeamUserCommand
{
	private String desiredName;
	private InviteHandler inviteHandler;

	public TeamUserJoin()
	{
		super();
		inviteHandler = InviteHandler.getInstance();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam foundTeam = XTeam.getInstance().getTeamManager().getTeam(desiredName);
		foundTeam.addPlayer(teamPlayer.getName());
		inviteHandler.removeInvite(teamPlayer.getName());
		teamPlayer.sendMessageToTeam(teamPlayer.getName() + " " + MessageUtil.positiveMessage("joined") + " your team");
		teamPlayer.sendMessage("You " + MessageUtil.positiveMessage("joined") + " " + foundTeam.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		ITeam desiredTeam = XTeam.getInstance().getTeamManager().getTeam(desiredName);
		Requirements.checkPlayerDoesNotHaveTeam(teamPlayer);
		Requirements.checkTeamOnlyJoinDefault(desiredName);
		Requirements.checkTeamExists(desiredName);
		Requirements.checkPlayerDoesNotHaveInviteFromTeam(teamPlayer, desiredTeam);
		Requirements.checkTeamPlayerMax(desiredName);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("join")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.join";
	}

	@Override
	public String getUsage()
	{
		return "/team join [Team]";
	}

	@Override
	public String getDescription()
	{
		return "join a team";
	}
}
