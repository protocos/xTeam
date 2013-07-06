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
				onConsoleCommand((ConsoleCommandSender) sender, commandID, originalCommand);
			}
			else if (sender instanceof Player)
			{
				onPlayerCommand((Player) sender, commandID, originalCommand);
			}
		}
		catch (Exception e)
		{
			xTeam.logger.exception(e);
			xTeam.log.info("[ERROR] Exception in xTeam onCommand() class [check logs]");
		}
		return true;
	}
	public boolean onConsoleCommand(ConsoleCommandSender sender, String commandID, String originalCommand)
	{
		xTeam.logger.info("console issued command: " + commandID + " " + originalCommand);
		BaseConsoleCommand command;
		// /////////////////////|||||||\\\\\\\\\\\\\\\\\\\\\
		// //////////////////             \\\\\\\\\\\\\\\\\\
		// ================     CONSOLE     ================
		// \\\\\\\\\\\\\\\\\\             //////////////////
		// \\\\\\\\\\\\\\\\\\\\\|||||||/////////////////////
		if (originalCommand.toLowerCase().matches(manager.getPattern("console_debug")))
			command = new ConsoleDebug(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_help")))
			command = new ConsoleHelp(sender, originalCommand, commandID);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_disband")))
			command = new ConsoleDisband(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_demote")))
			command = new ConsoleDemote(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_info")))
			command = new ConsoleInfo(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_list")))
			command = new ConsoleList(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_promote")))
			command = new ConsolePromote(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_reload")))
			command = new ConsoleReload(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_remove")))
			command = new ConsoleRemove(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_rename")))
			command = new ConsoleRename(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_tag")))
			command = new ConsoleTag(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_open")))
			command = new ConsoleOpen(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_set")))
			command = new ConsoleSet(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_setleader")))
			command = new ConsoleSetLeader(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("console_teleallhq")))
			command = new ConsoleTeleAllHQ(sender, originalCommand);
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
	public boolean onPlayerCommand(Player sender, String commandID, String originalCommand)
	{
		commandID = "/" + commandID;
		xTeam.logger.info(sender.getName() + " issued command: " + commandID + " " + originalCommand);
		BasePlayerCommand command;
		// /////////////////////|||||\\\\\\\\\\\\\\\\\\\\\
		// //////////////////           \\\\\\\\\\\\\\\\\\
		// ================     ADMIN     ================
		// \\\\\\\\\\\\\\\\\\           //////////////////
		// \\\\\\\\\\\\\\\\\\\\\|||||/////////////////////
		if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_admin")))
			command = new AdminHelp(sender, originalCommand, commandID);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_chatspy")))
			command = new AdminChatSpy(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_disband")))
			command = new AdminDisband(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_demote")))
			command = new AdminDemote(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_hq")))
			command = new AdminHeadquarters(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_promote")))
			command = new AdminPromote(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_reload")))
			command = new AdminReload(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_remove")))
			command = new AdminRemove(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_rename")))
			command = new AdminRename(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_tag")))
			command = new AdminTag(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_open")))
			command = new AdminOpen(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_set")))
			command = new AdminSet(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_sethq")))
			command = new AdminSetHeadquarters(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_setleader")))
			command = new AdminSetLeader(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_teleallhq")))
			command = new AdminTeleAllHQ(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_tpall")))
			command = new AdminTpAll(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("serveradmin_update")))
			command = new AdminUpdatePlayers(sender, originalCommand);
		// /////////////////////||||||\\\\\\\\\\\\\\\\\\\\\
		// //////////////////            \\\\\\\\\\\\\\\\\\
		// ================     PLAYER     ================
		// \\\\\\\\\\\\\\\\\\            //////////////////
		// \\\\\\\\\\\\\\\\\\\\\||||||/////////////////////
		else if (originalCommand.toLowerCase().matches(manager.getPattern("admin_invite")))
			command = new UserInvite(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("admin_promote")))
			command = new UserPromote(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("admin_sethq")))
			command = new UserSetHeadquarters(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("leader_demote")))
			command = new UserDemote(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("leader_disband")))
			command = new UserDisband(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("leader_open")))
			command = new UserOpen(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("leader_remove")))
			command = new UserRemove(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("leader_rename")))
			command = new UserRename(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("leader_setleader")))
			command = new UserSetLeader(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("leader_tag")))
			command = new UserTag(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_accept")))
			command = new UserAccept(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_chat")))
			command = new UserChat(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_create")))
			command = new UserCreate(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_hq")))
			command = new UserHeadquarters(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_help")))
			command = new UserHelp(sender, originalCommand, commandID);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_info")))
			command = new UserInfo(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_join")))
			command = new UserJoin(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_leave")))
			command = new UserLeave(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_list")))
			command = new UserList(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_mainhelp")))
			command = new UserMainHelp(sender, originalCommand, commandID);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_message")))
			command = new UserMessage(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_return")))
			command = new UserReturn(sender, originalCommand);
		else if (originalCommand.toLowerCase().matches(manager.getPattern("user_tele")))
			command = new UserTeleport(sender, originalCommand);
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
