package me.protocos.xteam.command;

import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.exception.TeamInvalidCommandException;
import me.protocos.xteam.message.Message;
import me.protocos.xteam.message.MessageUtil;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.PatternBuilder;
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
			if (new PatternBuilder()
					.append("\\?+")
					.whiteSpaceOptional()
					.matches(commandContainer.getLastArgument()))
			{
				String help = manager.getHelp(commandContainer);
				if (help == null)
				{
					commandFailed(sender);
				}
				else
				{
					for (String line : help.split("\n"))
						new Message.Builder(line).addRecipients(sender).disableFormatting().send(log);
				}
			}
			else
			{
				BaseCommand command = manager.match(commandContainer);
				logCommand(commandContainer);
				if (command == null)
				{
					commandFailed(sender);
				}
				else if (command.execute(commandContainer) == true)
					teamPlugin.write();
			}
		}
		catch (Exception e)
		{
			new Message.Builder(MessageUtil.red("There was a server error executing command: /" + commandID + " " + CommonUtil.concatenate(args))).addRecipients(sender).send(log);
			log.exception(e);
		}
		return true;
	}

	private void commandFailed(CommandSender sender)
	{
		new Message.Builder(MessageUtil.red((new TeamInvalidCommandException()).getMessage())).addRecipients(sender).send(log);
	}

	private void logCommand(CommandContainer commandContainer)
	{
		log.debug(commandContainer.getSenderName() + " issued server command: " + commandContainer.getCommand());
	}
}
