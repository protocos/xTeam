package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserJoin extends TeamUserCommand
{
	private String desiredName;
	private InviteHandler inviteHandler;

	public TeamUserJoin(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		inviteHandler = teamPlugin.getInviteHandler();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam foundTeam = teamCoordinator.getTeam(desiredName);
		foundTeam.addPlayer(teamUser.getName());
		inviteHandler.removeInvite(teamUser.getName());
		new Message.Builder(teamUser.getName() + " joined your team").addRecipients(teamUser.getTeam()).excludeRecipients(teamUser).send(log);
		new Message.Builder("You joined " + teamUser.getTeam().getName()).addRecipients(teamUser).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		ITeam desiredTeam = teamCoordinator.getTeam(desiredName);
		Requirements.checkPlayerDoesNotHaveTeam(teamUser);
		Requirements.checkTeamOnlyJoinDefault(teamCoordinator, desiredName);
		Requirements.checkTeamExists(teamCoordinator, desiredName);
		Requirements.checkPlayerDoesNotHaveInviteFromTeam(inviteHandler, teamUser, desiredTeam);
		Requirements.checkTeamPlayerMax(teamCoordinator, desiredName);
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
