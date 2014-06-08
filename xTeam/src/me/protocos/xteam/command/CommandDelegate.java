package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.exception.TeamInvalidCommandException;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandDelegate implements CommandExecutor
{
	private TeamPlugin teamPlugin;
	private ILog log;
	private ICommandManager manager;

	public CommandDelegate(TeamPlugin teamPlugin, ICommandManager manager)
	{
		this.teamPlugin = teamPlugin;
		this.log = teamPlugin.getLog();
		this.manager = manager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandID, String[] args)
	{
		try
		{
			CommandContainer commandContainer = new CommandContainer(sender, commandID, args);
			BaseCommand command = manager.match(commandContainer);
			logCommand(commandContainer);
			if (command == null)
			{
				sender.sendMessage(MessageUtil.red((new TeamInvalidCommandException()).getMessage()));
				log.debug("Command execute failed for reason: " + (new TeamInvalidCommandException()).getMessage());
			}
			else if (command.execute(commandContainer) == true)
				teamPlugin.write();
		}
		catch (Exception e)
		{
			sender.sendMessage(MessageUtil.red("There was a server error executing command: /" + commandID + " " + CommonUtil.concatenate(args)));
			log.exception(e);
		}
		return true;
	}

	private void logCommand(CommandContainer commandContainer)
	{
		log.debug(commandContainer.getSenderName() + " issued server command: " + commandContainer.getCommand());
	}
}
