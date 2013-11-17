package me.protocos.xteam.api.command;

import me.protocos.xteam.command.BaseCommand;
import me.protocos.xteam.command.CommandParser;
import me.protocos.xteam.command.action.Requirements;
import me.protocos.xteam.core.exception.TeamException;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public abstract class ConsoleCommand extends BaseCommand
{
	protected ConsoleCommandSender sender;

	public ConsoleCommand()
	{
		super();
	}

	@Override
	public void initData(CommandSender originalSender, CommandParser parseCommand) throws TeamException, IncompatibleClassChangeError
	{
		sender = CommonUtil.assignFromType(originalSender, ConsoleCommandSender.class);
		CommonUtil.assignFromType(originalSender, ConsoleCommandSender.class);
		Requirements.checkPlayerCommandIsValid(parseCommand, getPattern());
	}
}
