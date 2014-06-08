package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;
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
		foundTeam.addPlayer(teamPlayer.getName());
		inviteHandler.removeInvite(teamPlayer.getName());
		teamPlayer.sendMessageToTeam(teamPlayer.getName() + " " + MessageUtil.green("joined") + " your team");
		teamPlayer.sendMessage("You " + MessageUtil.green("joined") + " " + foundTeam.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		ITeam desiredTeam = teamCoordinator.getTeam(desiredName);
		Requirements.checkPlayerDoesNotHaveTeam(teamPlayer);
		Requirements.checkTeamOnlyJoinDefault(teamCoordinator, desiredName);
		Requirements.checkTeamExists(teamCoordinator, desiredName);
		Requirements.checkPlayerDoesNotHaveInviteFromTeam(inviteHandler, teamPlayer, desiredTeam);
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
