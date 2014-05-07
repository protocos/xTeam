package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.model.InviteRequest;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamAdminInvite extends TeamAdminCommand
{
	private InviteHandler inviteHandler;
	private String other;

	public TeamAdminInvite(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
		inviteHandler = teamPlugin.getInviteHandler();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeamPlayer otherPlayer = playerManager.getPlayer(other);
		InviteRequest request = new InviteRequest(teamPlayer, otherPlayer, System.currentTimeMillis());
		inviteHandler.addInvite(request);
		if (otherPlayer.isOnline())
			otherPlayer.sendMessage("You've been " + MessageUtil.positiveMessage("invited ") + "to join " + team.getName() + " (/team accept)");
		teamPlayer.sendMessage("You " + MessageUtil.positiveMessage("invited ") + otherPlayer.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		other = commandContainer.getArgument(1);
		ITeamPlayer otherPlayer = playerManager.getPlayer(other);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerInviteSelf(teamPlayer, otherPlayer);
		Requirements.checkPlayerHasPlayedBefore(otherPlayer);
		Requirements.checkPlayerHasInvite(inviteHandler, otherPlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.append("in")
				.oneOrMore("vite")
				.whiteSpace()
				.anyString()
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.admin.invite";
	}

	@Override
	public String getUsage()
	{
		return "/team invite [Player]";
	}

	@Override
	public String getDescription()
	{
		return "invite player to your team";
	}
}
