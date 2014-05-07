package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.exception.TeamException;

public abstract class TeamUserCommand extends PlayerCommand
{
	public TeamUserCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		super.preInitialize(commandContainer);
	}
}
