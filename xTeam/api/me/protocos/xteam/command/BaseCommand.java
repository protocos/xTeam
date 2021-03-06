package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.model.ILog;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand
{
	protected TeamPlugin teamPlugin;
	protected ILog log;
	protected ITeamCoordinator teamCoordinator;
	protected IPlayerFactory playerFactory;

	public BaseCommand(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.log = teamPlugin.getLog();
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
		this.playerFactory = teamPlugin.getPlayerFactory();
	}

	public abstract String getUsage();

	public abstract String getPattern();

	public abstract String getDescription();

	protected abstract void preInitialize(CommandContainer commandContainer) throws TeamException;

	protected abstract void checkCommandRequirements(CommandContainer commandContainer) throws TeamException;

	protected abstract void performCommandAction(CommandContainer commandContainer);

	public final boolean execute(CommandContainer commandContainer)
	{
		CommandSender sender = commandContainer.getSender();
		try
		{
			preInitialize(commandContainer);
			checkCommandRequirements(commandContainer);
			performCommandAction(commandContainer);
			return true;
		}
		catch (TeamException e)
		{
			new Message.Builder(e.getMessage()).addRecipients(sender).disableFormatting().send(log);
		}
		return false;
	}
}
