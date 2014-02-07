package me.protocos.xteam.command.teamuser;

import me.protocos.xteam.XTeam;
import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.Requirements;
import me.protocos.xteam.command.TeamUserCommand;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeamPlayer;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.PatternBuilder;

public class TeamUserLeave extends TeamUserCommand
{
	public TeamUserLeave()
	{
		super();
	}

	@Override
	protected void performCommandAction(CommandContainer commandContainer)
	{
		team.removePlayer(teamPlayer.getName());
		if (team.size() == 0 && !team.isDefaultTeam())
			XTeam.getInstance().getTeamManager().disbandTeam(team.getName());
		Configuration.chatStatus.remove(teamPlayer.getName());
		for (String teammate : team.getPlayers())
		{
			ITeamPlayer mate = XTeam.getInstance().getPlayerManager().getPlayer(teammate);
			if (mate.isOnline())
				mate.sendMessage(teamPlayer.getName() + " " + ChatColorUtil.negativeMessage("left") + " your team");
		}
		teamPlayer.sendMessage("You " + ChatColorUtil.negativeMessage("left") + " " + team.getName());
	}

	@Override
	public void checkCommandRequirements(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerLeaderLeaving(teamPlayer);
	}

	@Override
	public String getPattern()
	{
		return new PatternBuilder()
				.oneOrMore("lea")
				.oneOrMore("ve")
				.whiteSpaceOptional()
				.toString();
	}

	@Override
	public String getPermissionNode()
	{
		return "xteam.core.user.leave";
	}

	@Override
	public String getUsage()
	{
		return "/team leave";
	}

	@Override
	public String getDescription()
	{
		return "leave your team";
	}
}
