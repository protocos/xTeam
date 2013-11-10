package me.protocos.xteam;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import me.protocos.xteam.api.command.ICommandManager;
import me.protocos.xteam.api.util.ILog;
import me.protocos.xteam.command.CommandDelegate;
import me.protocos.xteam.command.CommandManager;
import me.protocos.xteam.command.console.*;
import me.protocos.xteam.command.serveradmin.*;
import me.protocos.xteam.command.teamadmin.UserInvite;
import me.protocos.xteam.command.teamadmin.UserPromote;
import me.protocos.xteam.command.teamadmin.UserSetHeadquarters;
import me.protocos.xteam.command.teamleader.*;
import me.protocos.xteam.command.teamuser.*;
import me.protocos.xteam.core.*;
import me.protocos.xteam.util.ConfigFileBuilder;
import me.protocos.xteam.util.LogUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class xTeam extends JavaPlugin
{
	public static final Logger log = Logger.getLogger("Minecraft");
	private static ILog logger;
	private static String VERSION;
	private static ServiceManager serviceManager;
	private static ICommandManager commandManager;
	private static CommandExecutor commandExecutor;
	private static TeamManager teamManager;
	private static PlayerManager playerManager;

	public static ILog getLog()
	{
		return logger;
	}

	public static String getVersion()
	{
		return VERSION;
	}

	public static ServiceManager getServiceManager()
	{
		return serviceManager;
	}

	public static ICommandManager getCommandManager()
	{
		return commandManager;
	}

	public static CommandExecutor getCommandExecutor()
	{
		return commandExecutor;
	}

	public static TeamManager getTeamManager()
	{
		return teamManager;
	}

	public static PlayerManager getPlayerManager()
	{
		return playerManager;
	}

	public static void registerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand("admin_invite", new UserInvite());
		manager.registerCommand("admin_promote", new UserPromote());
		manager.registerCommand("admin_sethq", new UserSetHeadquarters());
	}

	public static void registerConsoleCommands(ICommandManager manager)
	{
		manager.registerCommand("console_debug", new ConsoleDebug());
		manager.registerCommand("console_demote", new ConsoleDemote());
		manager.registerCommand("console_disband", new ConsoleDisband());
		manager.registerCommand("console_help", new ConsoleHelp());
		manager.registerCommand("console_info", new ConsoleInfo());
		manager.registerCommand("console_list", new ConsoleList());
		manager.registerCommand("console_promote", new ConsolePromote());
		manager.registerCommand("console_reload", new ConsoleReload());
		manager.registerCommand("console_remove", new ConsoleRemove());
		manager.registerCommand("console_rename", new ConsoleRename());
		manager.registerCommand("console_tag", new ConsoleTag());
		manager.registerCommand("console_open", new ConsoleOpen());
		manager.registerCommand("console_set", new ConsoleSet());
		manager.registerCommand("console_setleader", new ConsoleSetLeader());
		manager.registerCommand("console_teleallhq", new ConsoleTeleAllHQ());
	}

	public static void registerLeaderCommands(ICommandManager manager)
	{
		manager.registerCommand("leader_demote", new UserDemote());
		manager.registerCommand("leader_disband", new UserDisband());
		manager.registerCommand("leader_open", new UserOpen());
		manager.registerCommand("leader_remove", new UserRemove());
		manager.registerCommand("leader_rename", new UserRename());
		manager.registerCommand("leader_setleader", new UserSetLeader());
		manager.registerCommand("leader_setrally", new UserSetRally());
		manager.registerCommand("leader_tag", new UserTag());
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
	}

	public static void registerUserCommands(ICommandManager manager)
	{
		manager.registerCommand("user_accept", new UserAccept());
		manager.registerCommand("user_chat", new UserChat());
		manager.registerCommand("user_create", new UserCreate());
		manager.registerCommand("user_help", new UserHelp());
		manager.registerCommand("user_hq", new UserHeadquarters());
		manager.registerCommand("user_info", new UserInfo());
		manager.registerCommand("user_join", new UserJoin());
		manager.registerCommand("user_leave", new UserLeave());
		manager.registerCommand("user_list", new UserList());
		manager.registerCommand("user_mainhelp", new UserMainHelp());
		manager.registerCommand("user_message", new UserMessage());
		manager.registerCommand("user_return", new UserReturn());
		manager.registerCommand("user_tele", new UserTeleport());
	}

	public void initFileSystem()
	{
		File f = new File(getDataFolder().getAbsolutePath());
		if (!f.exists())
		{
			f.mkdir();
		}
		f = new File(getDataFolder().getAbsolutePath() + "/teams.txt");
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
		//		f = new File(getDataFolder().getAbsolutePath() + "/xTeam.cfg");
		//		if (!f.exists())
		//		{
		try
		{
			ConfigFileBuilder configBuilder = new ConfigFileBuilder(getDataFolder().getAbsolutePath() + "/xTeam.cfg");
			configBuilder.add("playersonteam", 10, "Amount of players that can be on a team");
			configBuilder.add("sethqinterval", 0, "Delay in hours between use of /team sethq");
			configBuilder.add("teleportradius", 500, "Maximum distance in blocks between team mates to teleport to one another");
			configBuilder.add("canteamchat", true, "Allows/Disallows the use of team chat function completely");
			configBuilder.add("enemyproximity", 16, "When teleporting, if enemies are within this radius of blocks, the teleport is delayed");
			configBuilder.add("teledelay", 10, "Delay in seconds for teleporting when enemies are near");
			configBuilder.add("telerefreshdelay", 60, "Delay in seconds for when you can use team teleporting. Does not include /team return");
			configBuilder.add("createteamdelay", 20, "Delay in minutes for creating teams");
			configBuilder.add("teamwolves", true, "Protects your wolfies from you and your teammates from damaging them");
			configBuilder.add("defaultteams", "", "Default list of teams for the server separated by commas  (e.g. defaultteams=red,green,blue,yellow)");
			configBuilder.add("randomjointeam", false, "Player randomly joins one of the default teams on joining");
			configBuilder.add("balanceteams", false, "Balance teams when someone randomly joins");
			configBuilder.add("onlyjoindefaultteam", false, "When true, players can only join one of the default teams listed above");
			configBuilder.add("defaulthqonjoin", false, "When true, players on default teams are teleported to their headquarters on join");
			configBuilder.add("anonymouserrorreporting", true, "When true, sends anonymous error reports for faster debugging");
			configBuilder.add("lastattackeddelay", 15, "How long a player has to wait after being attacked to teleport");
			configBuilder.add("teamtagenabled", true, "When true, players have their team tag displayed when in chat");
			configBuilder.add("teamtagmaxlength", 0, "Maximum length of a team tag (0 = unlimited)");
			configBuilder.add("disabledworlds", "", "World names, separated by commas, that xTeam is disabled in (e.g. disabledworlds=world,world_nether,world_the_end)");
			configBuilder.add("nopermissions", false, "When true, xTeam will give all regular commands to players and admin commands to OPs");
			configBuilder.add("alphanumericnames", true, "When true, players can only create teams with alphanumeric names and no symbols (e.g. TeamAwesome123)");
			configBuilder.add("displaycoordinates", true, "When true, players can see coordinates of other team mates in team info");
			configBuilder.add("tagcolor", "green", "Color representing the color of the tag in game (e.g. green, dark_red, light_purple)");
			configBuilder.add("chatnamecolor", "dark_green", "Color representing the color of player names in team chat (e.g. green, dark_red, light_purple)");
			configBuilder.add("rallydelay", 10, "Delay in minutes that a team rally stays active");
			configBuilder.write();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		//		}
		f = new File(getDataFolder().getAbsolutePath() + "/xTeam.log");
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
	}

	@Override
	public void onDisable()
	{
		try
		{
			Functions.writeTeamData(new File(getDataFolder().getAbsolutePath() + "/teams.txt"));
			//		serviceManager.saveConfig();
			logger.custom("[xTeam] v" + VERSION + " disabled");
			logger.close();
		}
		catch (Exception e)
		{
			logger.exception(e);
			xTeam.log.info("[ERROR] Exception in xTeam onDisable() class [check logs]");
		}
	}

	@Override
	public void onEnable()
	{
		try
		{
			initFileSystem();
			logger = new LogUtil(this);
			VERSION = getDescription().getVersion();
			Data.settings = new File(getDataFolder().getAbsolutePath() + "/xTeam.cfg");
			Data.load();
			log.info("[xTeam] Config loaded.");
			serviceManager = new ServiceManager(this);
			commandManager = new CommandManager();
			teamManager = new TeamManager();
			playerManager = new PlayerManager();
			registerConsoleCommands(commandManager);
			registerServerAdminCommands(commandManager);
			registerAdminCommands(commandManager);
			registerLeaderCommands(commandManager);
			registerUserCommands(commandManager);
			commandExecutor = new CommandDelegate(commandManager);
			getCommand("team").setExecutor(commandExecutor);
			Functions.readTeamData(new File(getDataFolder().getAbsolutePath() + "/teams.txt"));
			Data.ensureDefaultTeams();
			//		serviceManager.loadConfig();
			logger.custom("[xTeam] v" + VERSION + " enabled");
		}
		catch (Exception e)
		{
			logger.exception(e);
			xTeam.log.info("[ERROR] Exception in xTeam onEnable() class [check logs]");
		}
	}

	public static Plugin getSelf()
	{
		return Data.BUKKIT.getPluginManager().getPlugin("xTeam");
	}

	static void fakeData(ICommandManager fakeCommandManager, ServiceManager fakeServiceManager, TeamManager fakeTeamManager, PlayerManager fakePlayerManager, ILog fakeLogger, String fakeVersion)
	{
		commandManager = fakeCommandManager;
		serviceManager = fakeServiceManager;
		teamManager = fakeTeamManager;
		playerManager = fakePlayerManager;
		logger = fakeLogger;
		VERSION = fakeVersion;
	}
}
