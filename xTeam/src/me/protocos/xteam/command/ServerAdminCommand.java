package me.protocos.xteam.command;

import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import org.bukkit.command.CommandSender;

public abstract class ServerAdminCommand extends PlayerCommand
{
	public ServerAdminCommand()
	{
		super();
	}

	@Override
	public void initData(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		super.initData(originalSender, parseCommand);
		Requirements.checkPlayerHasPermission(originalSender, getPermissionNode());
		Requirements.checkPlayerCommandIsValid(parseCommand, getPattern());
	}
}
