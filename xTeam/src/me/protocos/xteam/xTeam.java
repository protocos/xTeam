package me.protocos.xteam;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.Logger;
import me.protocos.xteam.command.CommandDelegate;
import me.protocos.xteam.command.CommandManager;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.command.console.*;
import me.protocos.xteam.command.serveradmin.*;
import me.protocos.xteam.command.teamadmin.Invite;
import me.protocos.xteam.command.teamadmin.Promote;
import me.protocos.xteam.command.teamadmin.SetHeadquarters;
import me.protocos.xteam.command.teamleader.*;
import me.protocos.xteam.command.teamuser.*;
import me.protocos.xteam.core.Data;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.TeamManager;
import me.protocos.xteam.core.TeamServiceManager;
import me.protocos.xteam.util.ILog;
import me.protocos.xteam.util.Log;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class xTeam extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");
	public static ILog logger;
	public static String VERSION;
	public static TeamServiceManager sm;
	public static TeamManager tm;
	public static ICommandManager cm;
	public static CommandExecutor exec;

	public static void registerConsoleCommands(ICommandManager manager)
	{
		manager.registerCommand("console_disband", new ConsoleDisband());
		manager.registerCommand("console_demote", new ConsoleDemote());
		manager.registerCommand("console_help", new ConsoleHelp());
		manager.registerCommand("console_info", new ConsoleInfo());
		manager.registerCommand("console_list", new ConsoleList());
		manager.registerCommand("console_promote", new ConsolePromote());
		manager.registerCommand("console_reload", new ConsoleReload());
		manager.registerCommand("console_rename", new ConsoleRename());
		manager.registerCommand("console_tag", new ConsoleTag());
		manager.registerCommand("console_open", new ConsoleOpen());
		manager.registerCommand("console_set", new ConsoleSet());
		manager.registerCommand("console_setleader", new ConsoleSetLeader());
		manager.registerCommand("console_teleallhq", new ConsoleTeleAllHQ());
	}
	public static void registerServerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand("serveradmin_chatspy", new AdminChatSpy());
		manager.registerCommand("serveradmin_disband", new AdminDisband());
		manager.registerCommand("serveradmin_demote", new AdminDemote());
		manager.registerCommand("serveradmin_admin", new AdminHelp());
		manager.registerCommand("serveradmin_hq", new AdminHeadquarters());
		manager.registerCommand("serveradmin_promote", new AdminPromote());
		manager.registerCommand("serveradmin_reload", new AdminReload());
		manager.registerCommand("serveradmin_remove", new AdminRemove());
		manager.registerCommand("serveradmin_rename", new AdminRename());
		manager.registerCommand("serveradmin_tag", new AdminTag());
		manager.registerCommand("serveradmin_open", new AdminOpen());
		manager.registerCommand("serveradmin_set", new AdminSet());
		manager.registerCommand("serveradmin_sethq", new AdminSetHeadquarters());
		manager.registerCommand("serveradmin_setleader", new AdminSetLeader());
		manager.registerCommand("serveradmin_teleallhq", new AdminTeleAllHQ());
		manager.registerCommand("serveradmin_tpall", new AdminTpAll());
		manager.registerCommand("serveradmin_update", new AdminUpdatePlayers());
	}
	public static void registerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand("admin_invite", new Invite());
		manager.registerCommand("admin_promote", new Promote());
		manager.registerCommand("admin_sethq", new SetHeadquarters());
	}
	public static void registerLeaderCommands(ICommandManager manager)
	{
		manager.registerCommand("leader_demote", new Demote());
		manager.registerCommand("leader_disband", new Disband());
		manager.registerCommand("leader_open", new Open());
		manager.registerCommand("leader_remove", new Remove());
		manager.registerCommand("leader_rename", new Rename());
		manager.registerCommand("leader_setleader", new SetLeader());
		manager.registerCommand("leader_tag", new Tag());
	}
	public static void registerUserCommands(ICommandManager manager)
	{
		manager.registerCommand("user_accept", new Accept());
		manager.registerCommand("user_chat", new Chat());
		manager.registerCommand("user_create", new Create());
		manager.registerCommand("user_help", new Help());
		manager.registerCommand("user_hq", new Headquarters());
		manager.registerCommand("user_info", new Info());
		manager.registerCommand("user_join", new Join());
		manager.registerCommand("user_leave", new Leave());
		manager.registerCommand("user_list", new List());
		manager.registerCommand("user_mainhelp", new MainHelp());
		manager.registerCommand("user_message", new Message());
		manager.registerCommand("user_return", new Return());
		manager.registerCommand("user_tele", new Teleport());
	}
	public static boolean isConsoleSender(CommandSender sender)
	{
		return sender instanceof ConsoleCommandSender;
	}
	public static boolean isPlayerSender(CommandSender sender)
	{
		return sender instanceof Player;
	}
	@Override
	public void onDisable()
	{
		File f = new File("plugins/xTeam/");
		if (!f.exists())
		{
			f.mkdir();
		}
		f = new File("plugins/xTeam/teams.txt");
		if (!f.exists())
		{
			try
			{
				f.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		Functions.writeTeamData(new File("plugins/xTeam/teams.txt"));
		//		sm.saveConfig();
		logger.close();
	}
	@Override
	public void onEnable()
	{
		logger = new Log(this);
		Data.initFiles();
		VERSION = getDescription().getVersion();
		Data.settings = new File(getDataFolder().getAbsolutePath() + "/xTeam.cfg");
		Data.load();
		log.info("[xTeam] Config loaded.");
		sm = new TeamServiceManager(this);
		tm = new TeamManager();
		cm = new CommandManager();
		registerConsoleCommands(cm);
		registerServerAdminCommands(cm);
		registerAdminCommands(cm);
		registerLeaderCommands(cm);
		registerUserCommands(cm);
		exec = new CommandDelegate(cm);
		getCommand("team").setExecutor(exec);
		Functions.readTeamData(new File("plugins/xTeam/teams.txt"));
		Data.ensureDefaultTeams();
		//		sm.loadConfig();
		Timestamp t = new Timestamp(System.currentTimeMillis());
		logger.info("Server Started @ " + t.toString());
	}
	@Override
	public void onLoad()
	{
	}
}
