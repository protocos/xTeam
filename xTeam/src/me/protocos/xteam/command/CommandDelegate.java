package me.protocos.xteam.command;

import java.io.File;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.command.BaseCommand;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.exception.TeamInvalidCommandException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.StringUtil;
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
				sender.sendMessage(ChatColorUtil.negativeMessage((new TeamInvalidCommandException()).getMessage()));
				xTeam.getInstance().getLog().debug("Command execute failed for reason: " + (new TeamInvalidCommandException()).getMessage());
			}
			else if (command.execute(commandContainer) == true)
				xTeam.getInstance().writeTeamData(new File("plugins/xTeam/teams.txt"));
		}
		catch (Exception e)
		{
			sender.sendMessage(ChatColorUtil.negativeMessage("There was a server error executing command: /" + commandID + " " + StringUtil.concatenate(args)));
			xTeam.getInstance().getLog().exception(e);
		}
		return true;
	}

	private void logCommand(CommandContainer commandContainer)
	{
		xTeam.getInstance().getLog().debug(commandContainer.getSenderName() + " issued command: " + commandContainer.getCommand());
	}
}
