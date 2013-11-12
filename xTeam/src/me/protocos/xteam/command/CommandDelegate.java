package me.protocos.xteam.command;

import java.io.File;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.util.ChatColorUtil;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

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
		//		List<Permission> perms = pdf.getPermissions();
		//		for (Iterator iterator = perms.iterator(); iterator.hasNext();)
		//		{
		//			Permission permission = (Permission) iterator.next();
		//			//			System.out.println(permission.getName());
		//			if (sender instanceof Player)
		//				System.out.println(sender.getName() + ": " + PermissionUtil.hasPermission(sender, permission.getName()) + " - " + permission.getName());
		//		}
		//		if (Data.LOCATIONS_ENABLED)
		//		{
		//			// /////////////////////||||||||\\\\\\\\\\\\\\\\\\\\\
		//			// //////////////////              \\\\\\\\\\\\\\\\\\
		//			// ================     LOCATION     ================
		//			// \\\\\\\\\\\\\\\\\\              //////////////////
		//			// \\\\\\\\\\\\\\\\\\\\\||||||||/////////////////////
		//			boolean locationCmd = pm.getPlugin("xTeamLocations").onCommand(sender, cmd, commandID, args);
		//			if (locationCmd)
		//				return true;
		//		}
		try
		{
			String originalCommand = StringUtil.concatenate(args);
			BaseCommand command = null;
			CommandParser parseCommand = null;
			if (sender instanceof ConsoleCommandSender)
			{
				parseCommand = new CommandParser(commandID + " " + originalCommand);
				command = manager.matchConsole(parseCommand.getCommandWithoutID());
				xTeamPlugin.getInstance().getLog().info("console issued command: " + parseCommand.toString());
			}
			else if (sender instanceof Player)
			{
				parseCommand = new CommandParser("/" + commandID + " " + originalCommand);
				command = manager.matchPlayer(parseCommand.getCommandWithoutID());
				xTeamPlugin.getInstance().getLog().info(sender.getName() + " issued command: " + parseCommand.toString());
			}
			if (command == null)
			{
				sender.sendMessage(ChatColorUtil.negativeMessage((new TeamInvalidCommandException()).getMessage()));
				xTeamPlugin.getInstance().getLog().info("FAIL: " + (new TeamInvalidCommandException()).getMessage());
			}
			else if (command.execute(sender, parseCommand) == true)
				Functions.writeTeamData(new File("plugins/xTeamPlugin/teams.txt"));
		}
		catch (Exception e)
		{
			xTeamPlugin.getInstance().getLog().exception(e);
			xTeamPlugin.getInstance().getLog().info("[ERROR] Exception in xTeamPlugin onCommand() class [check logs]");
		}
		return true;
	}
}
