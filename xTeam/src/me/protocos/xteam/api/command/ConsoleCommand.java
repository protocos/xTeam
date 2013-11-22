package me.protocos.xteam.api.command;

import me.protocos.xteam.command.CommandContainer;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.ConsoleCommandSender;

public abstract class ConsoleCommand extends BaseCommand
{
	protected ConsoleCommandSender sender;

	public ConsoleCommand()
	{
		super();
	}

	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException, IncompatibleClassChangeError
	{
		sender = CommonUtil.assignFromType(commandContainer.getSender(), ConsoleCommandSender.class);
		Requirements.checkPlayerCommandIsValid(commandContainer.getCommandWithoutID(), getPattern());
	}
}
