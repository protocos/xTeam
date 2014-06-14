package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.core.InviteHandler;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserAccept extends TeamUserCommand
{
	private InviteHandler inviteHandler;

	public TeamUserAccept(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		this.inviteHandler = teamPlugin.getInviteHandler();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		inviteHandler.acceptInvite(teamUser);
		teamUser.sendMessageToTeam(teamUser.getName() + " " + MessageUtil.green("joined") + " your team");
		new Message.Builder("You " + MessageUtil.green("joined") + " " + teamUser.getTeam().getName()).addRecipients(teamUser).send(log);
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerDoesNotHaveTeam(teamUser);
		Requirements.checkPlayerDoesNotHaveInvite(inviteHandler, teamUser);
		ITeam inviteTeam = inviteHandler.getInviteTeam(teamUser.getName());
		Requirements.checkTeamPlayerMax(teamCoordinator, inviteTeam.getName());
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
