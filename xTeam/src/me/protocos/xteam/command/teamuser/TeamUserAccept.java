package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.api.command.TeamUserCommand;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.core.Team;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserAccept extends TeamUserCommand
{
	public TeamUserAccept()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		Team inviteTeam = InviteHandler.getInviteTeam(teamPlayer.getName());
		inviteTeam.addPlayer(teamPlayer.getName());
		InviteHandler.removeInvite(teamPlayer.getName());
		teamPlayer.sendMessageToTeam(teamPlayer.getName() + " " + ChatColorUtil.positiveMessage("joined") + " your team");
		teamPlayer.sendMessage("You " + ChatColorUtil.positiveMessage("joined") + " " + inviteTeam.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerDoesNotHaveTeam(teamPlayer);
		Requirements.checkPlayerDoesNotHaveInvite(teamPlayer);
		Team inviteTeam = InviteHandler.getInviteTeam(teamPlayer.getName());
		Requirements.checkTeamPlayerMax(inviteTeam.getName());
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("accept")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.player.core.accept";
	}

	@Override
	public String getUsage()
	{
		return "/team accept";
	}

	@Override
	public String getDescription()
	{
		return "accept the most recent team invite";
	}
}
