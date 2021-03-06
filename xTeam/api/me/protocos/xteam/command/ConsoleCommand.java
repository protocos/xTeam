package me.protocos.xteam.command;

import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.MessageUtil;
import org.bukkit.command.ConsoleCommandSender;

public abstract class ConsoleCommand extends BaseCommand
{
	protected ConsoleCommandSender sender;

	public ConsoleCommand(TeamPlugin teamPlugin)
	{
		super(teamPlugin);
	}

	@Override
	public final void preInitialize(CommandContainer commandContainer) throws TeamException
	{
		sender = CommonUtil.assignFromType(commandContainer.getSender(), ConsoleCommandSender.class);
		Requirements.checkCommandIsValid(commandContainer.getCommandWithoutID(), getPattern());
	}

	@Override
	public String toString()
	{
		return MessageUtil.formatForConsole(this.getUsage() + " - " + this.getDescription());
	}
}
