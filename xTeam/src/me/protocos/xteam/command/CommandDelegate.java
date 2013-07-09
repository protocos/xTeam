package me.protocos.xteam.command;

import java.io.File;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.command.console.*;
import me.protocos.xteam.command.serveradmin.*;
import me.protocos.xteam.command.teamadmin.UserInvite;
import me.protocos.xteam.command.teamadmin.UserPromote;
import me.protocos.xteam.command.teamadmin.UserSetHeadquarters;
import me.protocos.xteam.command.teamleader.*;
import me.protocos.xteam.command.teamuser.*;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.exception.TeamInvalidCommandException;
import me.protocos.xteam.util.StringUtil;
import org.bukkit.ChatColor;
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
		//		UserList<Permission> perms = pdf.getPermissions();
		//		for (Iterator iterator = perms.iterator(); iterator.hasNext();)
		//		{
		//			Permission permission = (Permission) iterator.next();
		//			//			System.out.println(permission.getName());
		//			if (sender instanceof Player)
		//				System.out.println(sender.getName() + ": " + sender.hasPermission(permission.getName()) + " - " + permission.getName());
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
			if (sender instanceof ConsoleCommandSender)
			{
				onConsoleCommand((ConsoleCommandSender) sender, new CommandParser(commandID + " " + originalCommand));
			}
			else if (sender instanceof Player)
			{
				onPlayerCommand((Player) sender, new CommandParser("/" + commandID + " " + originalCommand));
			}
		}
		catch (Exception e)
		{
			xTeam.logger.exception(e);
			xTeam.log.info("[ERROR] Exception in xTeam onCommand() class [check logs]");
		}
		return true;
	}
	public boolean onConsoleCommand(ConsoleCommandSender sender, CommandParser parseCommand)
	{
		xTeam.logger.info("console issued command: " + parseCommand.toString());
		ConsoleCommand command;
		// /////////////////////|||||||\\\\\\\\\\\\\\\\\\\\\
		// //////////////////             \\\\\\\\\\\\\\\\\\
		// ================     CONSOLE     ================
		// \\\\\\\\\\\\\\\\\\             //////////////////
		// \\\\\\\\\\\\\\\\\\\\\|||||||/////////////////////

		if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_debug")))
			command = new ConsoleDebug(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_help")))
			command = new ConsoleHelp(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_disband")))
			command = new ConsoleDisband(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_demote")))
			command = new ConsoleDemote(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_info")))
			command = new ConsoleInfo(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_list")))
			command = new ConsoleList(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_promote")))
			command = new ConsolePromote(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_reload")))
			command = new ConsoleReload(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_remove")))
			command = new ConsoleRemove(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_rename")))
			command = new ConsoleRename(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_tag")))
			command = new ConsoleTag(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_open")))
			command = new ConsoleOpen(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_set")))
			command = new ConsoleSet(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_setleader")))
			command = new ConsoleSetLeader(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("console_teleallhq")))
			command = new ConsoleTeleAllHQ(sender, parseCommand);
		else
		{
			sender.sendMessage(ChatColor.RED + (new TeamInvalidCommandException()).getMessage());
			xTeam.logger.info("FAIL: " + (new TeamInvalidCommandException()).getMessage());
			return false;
		}
		if (command.execute() == true)
			Functions.writeTeamData(new File("plugins/xTeam/teams.txt"));
		return true;
	}
	public boolean onPlayerCommand(Player sender, CommandParser parseCommand)
	{
		xTeam.logger.info(sender.getName() + " issued command: " + parseCommand.toString());
		PlayerCommand command;
		// /////////////////////|||||\\\\\\\\\\\\\\\\\\\\\
		// //////////////////           \\\\\\\\\\\\\\\\\\
		// ================     ADMIN     ================
		// \\\\\\\\\\\\\\\\\\           //////////////////
		// \\\\\\\\\\\\\\\\\\\\\|||||/////////////////////
		if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_admin")))
			command = new AdminHelp(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_chatspy")))
			command = new AdminChatSpy(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_disband")))
			command = new AdminDisband(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_demote")))
			command = new AdminDemote(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_hq")))
			command = new AdminHeadquarters(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_promote")))
			command = new AdminPromote(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_reload")))
			command = new AdminReload(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_remove")))
			command = new AdminRemove(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_rename")))
			command = new AdminRename(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_tag")))
			command = new AdminTag(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_open")))
			command = new AdminOpen(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_set")))
			command = new AdminSet(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_sethq")))
			command = new AdminSetHeadquarters(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_setleader")))
			command = new AdminSetLeader(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_teleallhq")))
			command = new AdminTeleAllHQ(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_tpall")))
			command = new AdminTpAll(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("serveradmin_update")))
			command = new AdminUpdatePlayers(sender, parseCommand);
		// /////////////////////||||||\\\\\\\\\\\\\\\\\\\\\
		// //////////////////            \\\\\\\\\\\\\\\\\\
		// ================     PLAYER     ================
		// \\\\\\\\\\\\\\\\\\            //////////////////
		// \\\\\\\\\\\\\\\\\\\\\||||||/////////////////////
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("admin_invite")))
			command = new UserInvite(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("admin_promote")))
			command = new UserPromote(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("admin_sethq")))
			command = new UserSetHeadquarters(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("leader_demote")))
			command = new UserDemote(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("leader_disband")))
			command = new UserDisband(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("leader_open")))
			command = new UserOpen(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("leader_remove")))
			command = new UserRemove(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("leader_rename")))
			command = new UserRename(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("leader_setleader")))
			command = new UserSetLeader(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("leader_tag")))
			command = new UserTag(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_accept")))
			command = new UserAccept(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_chat")))
			command = new UserChat(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_create")))
			command = new UserCreate(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_hq")))
			command = new UserHeadquarters(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_help")))
			command = new UserHelp(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_info")))
			command = new UserInfo(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_join")))
			command = new UserJoin(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_leave")))
			command = new UserLeave(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_list")))
			command = new UserList(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_mainhelp")))
			command = new UserMainHelp(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_message")))
			command = new UserMessage(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_return")))
			command = new UserReturn(sender, parseCommand);
		else if (parseCommand.getCommandWithoutID().toLowerCase().matches(manager.getPattern("user_tele")))
			command = new UserTeleport(sender, parseCommand);
		else
		{
			sender.sendMessage(ChatColor.RED + (new TeamInvalidCommandException()).getMessage());
			xTeam.logger.info("FAIL: " + (new TeamInvalidCommandException()).getMessage());
			return false;
		}
		if (command.execute())
			Functions.writeTeamData(new File("plugins/xTeam/teams.txt"));
		return true;
	}
}
