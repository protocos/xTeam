package me.protocos.xteam;

import java.io.File;
import java.util.List;
import me.protocos.xteam.api.TeamPlugin;
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
import me.protocos.xteam.core.Configuration;
import me.protocos.xteam.core.Functions;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.TeamManager;
import me.protocos.xteam.listener.TeamChatListener;
import me.protocos.xteam.listener.TeamPlayerListener;
import me.protocos.xteam.listener.TeamPvPEntityListener;
import me.protocos.xteam.listener.TeamScoreListener;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.SystemUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

public class xTeam
{
	//	private static final Logger log = Logger.getLogger("Minecraft");

	private static xTeam xteam;
	private String pluginPath, pluginName, pluginVersion;
	private ILog logger;
	private ICommandManager commandManager;
	private CommandExecutor commandExecutor;
	private TeamManager teamManager;
	private PlayerManager playerManager;
	private Configuration configLoader;
	private List<Permission> permissions;

	private xTeam()
	{
		super();
		this.playerManager = new PlayerManager();
		this.teamManager = new TeamManager();
		this.commandManager = new CommandManager();
		this.commandExecutor = new CommandDelegate(commandManager);
		this.registerConsoleCommands(commandManager);
		this.registerServerAdminCommands(commandManager);
		this.registerUserCommands(commandManager);
		this.registerAdminCommands(commandManager);
		this.registerLeaderCommands(commandManager);
	}

	public static xTeam getInstance()
	{
		if (xteam == null)
		{
			xteam = new xTeam();
		}
		return xteam;
	}

	public String getPath()
	{
		return pluginPath;
	}

	public String getName()
	{
		return pluginName;
	}

	public String getVersion()
	{
		return pluginVersion;
	}

	public ILog getLog()
	{
		return logger;
	}

	public ICommandManager getCommandManager()
	{
		return commandManager;
	}

	public CommandExecutor getCommandExecutor()
	{
		return commandExecutor;
	}

	public TeamManager getTeamManager()
	{
		return teamManager;
	}

	public PlayerManager getPlayerManager()
	{
		return playerManager;
	}

	public Configuration getConfigLoader()
	{
		return configLoader;
	}

	public void registerConsoleCommands(ICommandManager manager)
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

	public void registerServerAdminCommands(ICommandManager manager)
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

	public void registerLeaderCommands(ICommandManager manager)
	{
		manager.registerCommand("leader_demote", new UserDemote());
		manager.registerCommand("leader_disband", new UserDisband());
		manager.registerCommand("leader_open", new UserOpen());
		manager.registerCommand("leader_remove", new UserRemove());
		manager.registerCommand("leader_rename", new UserRename());
		manager.registerCommand("leader_tag", new UserTag());
		manager.registerCommand("leader_setleader", new UserSetLeader());
		manager.registerCommand("leader_setrally", new UserSetRally());
	}

	public void registerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand("admin_sethq", new UserSetHeadquarters());
		manager.registerCommand("admin_invite", new UserInvite());
		manager.registerCommand("admin_promote", new UserPromote());
	}

	public void registerUserCommands(ICommandManager manager)
	{
		manager.registerCommand("user_mainhelp", new UserMainHelp());
		manager.registerCommand("user_help", new UserHelp());
		manager.registerCommand("user_info", new UserInfo());
		manager.registerCommand("user_list", new UserList());
		manager.registerCommand("user_create", new UserCreate());
		manager.registerCommand("user_join", new UserJoin());
		manager.registerCommand("user_leave", new UserLeave());
		manager.registerCommand("user_accept", new UserAccept());
		manager.registerCommand("user_hq", new UserHeadquarters());
		manager.registerCommand("user_tele", new UserTeleport());
		manager.registerCommand("user_return", new UserReturn());
		manager.registerCommand("user_rally", new UserRally());
		manager.registerCommand("user_chat", new UserChat());
		manager.registerCommand("user_message", new UserMessage());
	}

	public void load(TeamPlugin plugin)
	{
		try
		{
			this.pluginPath = plugin.getFolder();
			this.logger = plugin.getLog();
			this.pluginName = plugin.getPluginName();
			this.pluginVersion = plugin.getVersion();
			this.permissions = plugin.getPermissions();
			this.initFileSystem();
			this.logInfo("[" + this.pluginName + "] v" + this.pluginVersion + " loaded");
		}
		catch (Exception e)
		{
			this.logError(e);
		}
	}

	public void enable(TeamPlugin plugin)
	{
		try
		{
			PluginManager pm = BukkitUtil.getPluginManager();
			pm.registerEvents(new TeamPvPEntityListener(), plugin);
			pm.registerEvents(new TeamPlayerListener(), plugin);
			pm.registerEvents(new TeamScoreListener(), plugin);
			pm.registerEvents(new TeamChatListener(), plugin);
			Functions.readTeamData(new File(plugin.getDataFolder().getAbsolutePath() + "/teams.txt"));
			plugin.getCommand("team").setExecutor(commandExecutor);
			this.logInfo("[" + this.pluginName + "] v" + this.pluginVersion + " enabled");
		}
		catch (Exception e)
		{
			logError(e);
		}
	}

	public void disable(TeamPlugin plugin)
	{
		try
		{
			Functions.writeTeamData(new File(plugin.getDataFolder().getAbsolutePath() + "/teams.txt"));
			this.logInfo("[" + this.pluginName + "] v" + this.pluginVersion + " disabled");
			this.getLog().close();
		}
		catch (Exception e)
		{
			this.logError(e);
		}
	}

	private void initFileSystem()
	{
		try
		{
			SystemUtil.ensureFolder(this.pluginPath);
			SystemUtil.ensureFile(this.pluginPath + "/teams.txt");
			configLoader = new Configuration(SystemUtil.ensureFile(this.pluginPath + "/xTeam.cfg"));
			configLoader.addAttribute("playersonteam", 10, "Amount of players that can be on a team");
			configLoader.addAttribute("sethqinterval", 0, "Delay in hours between use of /team sethq");
			configLoader.addAttribute("teleportradius", 500, "Maximum distance in blocks between team mates to teleport to one another");
			configLoader.addAttribute("canteamchat", true, "Allows/Disallows the use of team chat function completely");
			configLoader.addAttribute("hqondeath", true, "When a player dies, they are teleported to their headquarters when they respawn");
			configLoader.addAttribute("enemyproximity", 16, "When teleporting, if enemies are within this radius of blocks, the teleport is delayed");
			configLoader.addAttribute("teledelay", 10, "Delay in seconds for teleporting when enemies are near");
			configLoader.addAttribute("telerefreshdelay", 60, "Delay in seconds for when you can use team teleporting. Does not include /team return");
			configLoader.addAttribute("createteamdelay", 20, "Delay in minutes for creating teams");
			configLoader.addAttribute("teamwolves", true, "Protects your wolfies from you and your teammates from damaging them");
			configLoader.addAttribute("defaultteams", "", "Default list of teams for the server separated by commas  (e.g. defaultteams=red,green,blue,yellow)");
			configLoader.addAttribute("randomjointeam", false, "Player randomly joins one of the default teams on joining");
			configLoader.addAttribute("balanceteams", false, "Balance teams when someone randomly joins");
			configLoader.addAttribute("onlyjoindefaultteam", false, "When true, players can only join one of the default teams listed above");
			configLoader.addAttribute("defaulthqonjoin", false, "When true, players on default teams are teleported to their headquarters on join");
			configLoader.addAttribute("anonymouserrorreporting", true, "When true, sends anonymous error reports for faster debugging");
			configLoader.addAttribute("lastattackeddelay", 15, "How long a player has to wait after being attacked to teleport");
			configLoader.addAttribute("teamtagenabled", true, "When true, players have their team tag displayed when in chat");
			configLoader.addAttribute("teamtagmaxlength", 0, "Maximum length of a team tag (0 = unlimited)");
			configLoader.addAttribute("disabledworlds", "", "World names, separated by commas, that xTeam is disabled in (e.g. disabledworlds=world,world_nether,world_the_end)");
			configLoader.addAttribute("nopermissions", false, "When true, xTeam will give all regular commands to players and admin commands to OPs");
			configLoader.addAttribute("teamfriendlyfire", false, "When true, friendly fire will be enabled for all teams");
			configLoader.addAttribute("alphanumericnames", true, "When true, players can only create teams with alphanumeric names and no symbols (e.g. TeamAwesome123)");
			configLoader.addAttribute("displaycoordinates", true, "When true, players can see coordinates of other team mates in team info");
			configLoader.addAttribute("tagcolor", "green", "Color representing the color of the tag in game (e.g. green, dark_red, light_purple)");
			configLoader.addAttribute("chatnamecolor", "dark_green", "Color representing the color of player names in team chat (e.g. green, dark_red, light_purple)");
			configLoader.addAttribute("rallydelay", 2, "Delay in minutes that a team rally stays active");
			configLoader.write();
			configLoader.load();
			SystemUtil.ensureFile(this.pluginPath + "/xTeam.log");
		}
		catch (Exception e)
		{
			this.logError(e);
		}
	}

	public List<Permission> getPermissions()
	{
		return permissions;
	}

	private void logInfo(String info)
	{
		//		xTeamPlugin.log.info(info);
		this.getLog().info(info);
	}

	private void logError(Exception e)
	{
		//		xTeamPlugin.log.severe("[ERROR] in '" + methodName + "' method, check the logs");
		this.getLog().exception(e);
	}
}
