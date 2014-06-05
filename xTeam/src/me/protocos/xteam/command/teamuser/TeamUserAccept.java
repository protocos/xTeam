package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserAccept extends TeamUserCommand
{
	private InviteHandler inviteHandler;

	public TeamUserAccept(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		inviteHandler = teamPlugin.getInviteHandler();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeam inviteTeam = inviteHandler.getInviteTeam(teamPlayer.getName());
		inviteTeam.addPlayer(teamPlayer.getName());
		inviteHandler.removeInvite(teamPlayer.getName());
		teamPlayer.sendMessageToTeam(teamPlayer.getName() + " " + MessageUtil.green("joined") + " your team");
		teamPlayer.sendMessage("You " + MessageUtil.green("joined") + " " + inviteTeam.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerDoesNotHaveTeam(teamPlayer);
		Requirements.checkPlayerDoesNotHaveInvite(inviteHandler, teamPlayer);
		ITeam inviteTeam = inviteHandler.getInviteTeam(teamPlayer.getName());
		Requirements.checkTeamPlayerMax(teamManager, inviteTeam.getName());
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
		return "xteam.core.user.accept";
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
