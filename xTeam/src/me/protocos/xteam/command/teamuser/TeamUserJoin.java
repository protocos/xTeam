package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserJoin extends TeamUserCommand
{
	private String desiredName;

	public TeamUserJoin()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		Team foundTeam = xTeam.getInstance().getTeamManager().getTeam(desiredName);
		foundTeam.addPlayer(teamPlayer.getName());
		InviteHandler.removeInvite(teamPlayer.getName());
		teamPlayer.sendMessageToTeam(teamPlayer.getName() + " " + ChatColorUtil.positiveMessage("joined") + " your team");
		teamPlayer.sendMessage("You " + ChatColorUtil.positiveMessage("joined") + " " + foundTeam.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		desiredName = commandContainer.getArgument(1);
		Team desiredTeam = xTeam.getInstance().getTeamManager().getTeam(desiredName);
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
		return "xteam.core.player.join";
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
