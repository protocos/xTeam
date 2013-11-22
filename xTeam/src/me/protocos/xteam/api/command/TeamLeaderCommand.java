package me.protocos.xteam.api.command;

import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;

public abstract class TeamLeaderCommand extends PlayerCommand
{
	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		super.preInitialize(commandContainer);
		Requirements.checkPlayerHasTeam(teamPlayer);
		Requirements.checkPlayerIsTeamLeader(teamPlayer);
	}
}
