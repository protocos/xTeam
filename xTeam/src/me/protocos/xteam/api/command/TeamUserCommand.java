package me.protocos.xteam.api.command;

import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.core.exception.TeamException;

public abstract class TeamUserCommand extends PlayerCommand
{
	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		super.preInitialize(commandContainer);
	}
}
