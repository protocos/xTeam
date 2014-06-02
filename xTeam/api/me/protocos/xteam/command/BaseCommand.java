package me.protocos.xteam.command;

import java.io.InvalidClassException;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.IPlayerFactory;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.exception.TeamException;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.MessageUtil;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand
{
	protected TeamPlugin teamPlugin;
	protected ILog log;
	protected ITeamManager teamManager;
	protected IPlayerFactory playerFactory;

	public BaseCommand(TeamPlugin teamPlugin)
	{
		this.teamPlugin = teamPlugin;
		this.log = teamPlugin.getLog();
		this.teamManager = teamPlugin.getTeamManager();
		this.playerFactory = teamPlugin.getPlayerManager();
	}

	public abstract String getUsage();

	public abstract String getPattern();

	public abstract String getDescription();

	protected abstract void preInitialize(CommandContainer commandContainer) throws TeamException, InvalidClassException;

	protected void initializeData(@SuppressWarnings("unused") CommandContainer commandContainer)
	{
	}

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
			sender.sendMessage(MessageUtil.negativeMessage(e.getMessage()));
			log.debug("Command execute failed for reason: " + e.getMessage());
		}
		catch (InvalidClassException e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
