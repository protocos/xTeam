package me.protocos.xteam.command.teamadmin;

import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamAdminCommand;
import me.protocos.xteam.command.action.InviteHandler;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.model.InviteRequest;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamAdminInvite extends TeamAdminCommand
{
	private String other;

	public TeamAdminInvite()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		ITeamPlayer otherPlayer = xTeam.getInstance().getPlayerManager().getPlayer(other);
		InviteRequest request = new InviteRequest(teamPlayer, otherPlayer, System.currentTimeMillis());
		InviteHandler.addInvite(request);
		if (otherPlayer.isOnline())
			otherPlayer.sendMessage("You've been " + ChatColorUtil.positiveMessage("invited ") + "to join " + team.getName() + " (/team accept)");
		teamPlayer.sendMessage("You " + ChatColorUtil.positiveMessage("invited ") + otherPlayer.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		other = commandContainer.getArgument(1);
		ITeamPlayer otherPlayer = xTeam.getInstance().getPlayerManager().getPlayer(other);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerInviteSelf(teamPlayer, otherPlayer);
		Requirements.checkPlayerHasPlayedBefore(otherPlayer);
		Requirements.checkPlayerHasInvite(otherPlayer);
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
