package me.protocos.xteam;

import java.io.File;
import java.io.IOException;
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
import me.protocos.xteam.core.*;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class xTeam extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");
	public static String VERSION;
	public static TeamServiceManager sm;
	public static TeamManager tm;
	public static ICommandManager cm;
	public static CommandExecutor exec;

	//	public static PluginManager pm;
	//	public static PluginDescriptionFile pdf;
	//	public static String VERSION;
	//	public static CommandExecutor exec;
	//	public static FileConfiguration config;

	public static void registerConsoleCommands(ICommandManager manager)
	{
		manager.registerCommand("console_delete", new ConsoleDelete());
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
		manager.registerCommand("serveradmin_delete", new AdminDelete());
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
		//		saveConfig();
	}
	@Override
	public void onEnable()
	{
		VERSION = getDescription().getVersion();
		Data.settings = new File(getDataFolder().getAbsolutePath() + "/xTeam.cfg");
		Data.load();
		log.info("[xTeam] Config loaded.");
		sm = new TeamServiceManager(this);
		tm = new TeamManager();
		cm = new CommandManager();
		Functions.readTeamData(new File("plugins/xTeam/teams.txt"));
		//delegate all commands to a CommandDelegate
		registerConsoleCommands(cm);
		registerServerAdminCommands(cm);
		registerAdminCommands(cm);
		registerLeaderCommands(cm);
		registerUserCommands(cm);
		exec = new CommandDelegate(cm);
		getCommand("team").setExecutor(exec);
		//		saveConfig();
	}
	@Override
	public void onLoad()
	{
		//		config = getConfig();
		//		defaultConfigData();
		Data.initFileSystem();
	}
	//	private void defaultConfigData()
	//	{
	//		String setting;
	//		if (!config.contains(setting = "team_chat_enabled"))
	//			config.set(setting, true);
	//		if (!config.contains(setting = "headquarters_on_death_enabled"))
	//			config.set(setting, true);
	//		if (!config.contains(setting = "team_wolves_enabled"))
	//			config.set(setting, true);
	//		if (!config.contains(setting = "team_tag_enabled"))
	//			config.set(setting, true);
	//		if (!config.contains(setting = "display_teammate_coordinates_enabled"))
	//			config.set(setting, true);
	//		if (!config.contains(setting = "team_friendly_fire_enabled"))
	//			config.set(setting, false);
	//		if (!config.contains(setting = "alphanumeric_team_names_enabled"))
	//			config.set(setting, false);
	//		if (!config.contains(setting = "only_default_teams_enabled"))
	//			config.set(setting, false);
	//		if (!config.contains(setting = "default_teams.random_join_team"))
	//			config.set(setting, false);
	//		if (!config.contains(setting = "default_teams.balance_join_team"))
	//			config.set(setting, false);
	//		if (!config.contains(setting = "default_teams.headquarters_on_join"))
	//			config.set(setting, false);
	//		if (!config.contains(setting = "no_permissions_plugin"))
	//			config.set(setting, false);
	//		if (!config.contains(setting = "players_on_team"))
	//			config.set(setting, 10);
	//		if (!config.contains(setting = "teleport_radius"))
	//			config.set(setting, 500);
	//		if (!config.contains(setting = "teleport_danger_delay"))
	//			config.set(setting, 10);
	//		if (!config.contains(setting = "teleport_refresh_delay"))
	//			config.set(setting, 60);
	//		if (!config.contains(setting = "enemy_proximity"))
	//			config.set(setting, 16);
	//		if (!config.contains(setting = "set_headquarters_interval"))
	//			config.set(setting, 0);
	//		if (!config.contains(setting = "create_team_interval"))
	//			config.set(setting, 0);
	//		if (!config.contains(setting = "last_attacked_delay"))
	//			config.set(setting, 15);
	//		if (!config.contains(setting = "team_tag_length"))
	//			config.set(setting, 0);
	//		if (!config.contains(setting = "default_team_names"))
	//			config.set(setting, "");
	//		if (!config.contains(setting = "default_worlds"))
	//			config.set(setting, "");
	//		//		if (!config.contains(setting = "locations.maximum_team_locations"))
	//		//			config.set(setting, 9);
	//		if (!config.contains(setting = "spout.hide_names"))
	//			config.set(setting, true);
	//		if (!config.contains(setting = "spout.name_reveal_time"))
	//			config.set(setting, 5);
	//
	//	}
}
