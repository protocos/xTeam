package me.protocos.xteam.command;

import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.exception.TeamException;

public abstract class TeamUserCommand extends PlayerCommand
{
	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		super.preInitialize(commandContainer);
	}
}
