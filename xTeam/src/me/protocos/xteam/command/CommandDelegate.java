package me.protocos.xteam.command;

import java.io.File;
import me.protocos.xteam.XTeam;
import me.protocos.xteam.exception.TeamInvalidCommandException;
import me.protocos.xteam.util.MessageUtil;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandDelegate implements CommandExecutor
{
	private ICommandManager manager;

	public CommandDelegate(ICommandManager manager)
	{
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
				sender.sendMessage(MessageUtil.negativeMessage((new TeamInvalidCommandException()).getMessage()));
				XTeam.getInstance().getLog().debug("Command execute failed for reason: " + (new TeamInvalidCommandException()).getMessage());
			}
			else if (command.execute(commandContainer) == true)
				XTeam.getInstance().writeTeamData(new File("plugins/xTeam/teams.txt"));
		}
		catch (Exception e)
		{
			sender.sendMessage(MessageUtil.negativeMessage("There was a server error executing command: /" + commandID + " " + CommonUtil.concatenate(args)));
			XTeam.getInstance().getLog().exception(e);
		}
		return true;
	}

	private void logCommand(CommandContainer commandContainer)
	{
		XTeam.getInstance().getLog().debug(commandContainer.getSenderName() + " issued command: " + commandContainer.getCommand());
	}
}
