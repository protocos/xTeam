package me.protocos.xteam;

import java.io.*;
import java.util.ArrayList;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.command.console.*;
import me.protocos.xteam.command.serveradmin.*;
import me.protocos.xteam.command.teamadmin.TeamAdminInvite;
import me.protocos.xteam.command.teamadmin.TeamAdminPromote;
import me.protocos.xteam.command.teamadmin.TeamAdminSetHeadquarters;
import me.protocos.xteam.command.teamleader.*;
import me.protocos.xteam.command.teamuser.*;
import me.protocos.xteam.data.PlayerDataStorageFactory;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.listener.TeamChatListener;
import me.protocos.xteam.listener.TeamPlayerListener;
import me.protocos.xteam.listener.TeamPvPEntityListener;
import me.protocos.xteam.util.SystemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

public final class XTeam extends TeamPlugin
{
	private String version;
	private Configuration configLoader;

	public XTeam()
	{
		super(Bukkit.getServer(), "plugins/xTeam");
	}

	public XTeam(Server server, String folder)
	{
		super(server, folder);
	}

	public String getVersion()
	{
		return version;
	}

	public void load()
	{
		this.commandManager.register(this);
		this.initFileSystem();
		this.playerManager.setDataManager(new PlayerDataStorageFactory(this).fromString(Configuration.STORAGE_TYPE));
	}

	private void initFileSystem()
	{
		SystemUtil.ensureFolder(this.getFolder());
		SystemUtil.ensureFile(this.getFolder() + "teams.txt");
		this.configLoader = new Configuration(this, SystemUtil.ensureFile(this.getFolder() + "xTeam.cfg"));
		this.configLoader.addAttribute("playersonteam", 10, "Amount of players that can be on a team");
		this.configLoader.addAttribute("sethqinterval", 0, "Delay in hours between use of /team sethq");
		this.configLoader.addAttribute("teleportradius", 500, "Maximum distance in blocks between team mates to teleport to one another");
		this.configLoader.addAttribute("canteamchat", true, "Allows/Disallows the use of team chat function completely");
		this.configLoader.addAttribute("hqondeath", true, "When a player dies, they are teleported to their headquarters when they respawn");
		this.configLoader.addAttribute("enemyproximity", 16, "When teleporting, if enemies are within this radius of blocks, the teleport is delayed");
		this.configLoader.addAttribute("teledelay", 10, "Delay in seconds for teleporting when enemies are near");
		this.configLoader.addAttribute("telerefreshdelay", 0, "Delay in seconds for when you can use team teleporting. Does not include /team return");
		this.configLoader.addAttribute("createteamdelay", 20, "Delay in minutes for creating teams");
		this.configLoader.addAttribute("teamwolves", true, "Protects your wolfies from you and your teammates from damaging them");
		this.configLoader.addAttribute("defaultteams", "", "Default list of teams for the server separated by commas  (e.g. defaultteams=red,green,blue,yellow)");
		this.configLoader.addAttribute("randomjointeam", false, "Player randomly joins one of the default teams on joining");
		this.configLoader.addAttribute("balanceteams", false, "Balance teams when someone randomly joins");
		this.configLoader.addAttribute("onlyjoindefaultteam", false, "When true, players can only join one of the default teams listed above");
		this.configLoader.addAttribute("defaulthqonjoin", false, "When true, players on default teams are teleported to their headquarters on join");
		this.configLoader.addAttribute("anonymouserrorreporting", true, "When true, sends anonymous error reports for faster debugging");
		this.configLoader.addAttribute("lastattackeddelay", 15, "How long a player has to wait after being attacked to teleport");
		this.configLoader.addAttribute("teamtagenabled", true, "When true, players have their team tag displayed when in chat");
		this.configLoader.addAttribute("teamnamemaxlength", 0, "Maximum length of a team name (0 = unlimited)");
		this.configLoader.addAttribute("disabledworlds", "", "World names, separated by commas, that xTeam is disabled in (e.g. disabledworlds=world,world_nether,world_the_end)");
		this.configLoader.addAttribute("nopermissions", false, "When true, xTeam will give all regular commands to players and admin commands to OPs");
		this.configLoader.addAttribute("teamfriendlyfire", false, "When true, friendly fire will be enabled for all teams");
		this.configLoader.addAttribute("alphanumericnames", true, "When true, players can only create teams with alphanumeric names and no symbols (e.g. TeamAwesome123)");
		this.configLoader.addAttribute("displaycoordinates", true, "When true, players can see coordinates of other team mates in team info");
		this.configLoader.addAttribute("tagcolor", "green", "Color representing the color of the tag in game (e.g. green, dark_red, light_purple)");
		this.configLoader.addAttribute("chatnamecolor", "dark_green", "Color representing the color of player names in team chat (e.g. green, dark_red, light_purple)");
		this.configLoader.addAttribute("rallydelay", 2, "Delay in minutes that a team rally stays active");
		this.configLoader.addAttribute("newparam", 1, "Delay in minutes that a team rally stays active");
		this.configLoader.addAttribute("storagetype", "file", "Method for storing data for the plugin (Options: file, SQLite, MySQL)");
		this.configLoader.addAttribute("savedatainterval", 10, "Frequency, in minutes, that the plugin will write data to disk if storagetype = file");
		this.configLoader.write();
		this.configLoader.load();
	}

	@Override
	public void readTeamData()
	{
		File f = new File("plugins/xTeam/teams.txt");
		try
		{
			String line;
			BufferedReader br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null)
			{
				Team team = Team.generateTeamFromProperties(this, line);
				this.getTeamManager().createTeam(team);
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void writeTeamData()
	{
		File f = new File("plugins/xTeam/teams.txt");
		ArrayList<String> data = new ArrayList<String>();
		try
		{
			HashList<String, ITeam> teams = this.getTeamManager().getTeams();
			for (ITeam team : teams)
				data.add(team.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (String line : data)
			{
				bw.write(line + "\n");
			}
			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onEnable()
	{
		this.enable();
	}

	@Override
	public void onDisable()
	{
		this.disable();
	}

	private void enable()
	{
		try
		{
			PluginManager pm = bukkitUtil.getPluginManager();
			pm.registerEvents(new TeamPvPEntityListener(this), this);
			pm.registerEvents(new TeamPlayerListener(this), this);
			pm.registerEvents(new TeamChatListener(this), this);
			this.readTeamData();
			this.getCommand("team").setExecutor(commandExecutor);
			this.getPlayerManager().open();
			this.getLog().debug("[" + this.getPluginName() + "] v" + this.getVersion() + " enabled");
		}
		catch (Exception e)
		{
			this.getLog().exception(e);
		}
	}

	private void disable()
	{
		try
		{
			this.writeTeamData();
			this.getPlayerManager().close();
			this.getLog().debug("[" + this.getPluginName() + "] v" + this.getVersion() + " disabled");
			this.getLog().close();
		}
		catch (Exception e)
		{
			this.getLog().exception(e);
		}
	}

	@Override
	public void registerCommands(ICommandManager manager)
	{
		this.registerConsoleCommands(manager);
		this.registerServerAdminCommands(manager);
		this.registerUserCommands(manager);
		this.registerAdminCommands(manager);
		this.registerLeaderCommands(manager);
	}

	public void registerConsoleCommands(ICommandManager manager)
	{
		manager.registerCommand(new ConsoleDebug(this));
		manager.registerCommand(new ConsoleDemote(this));
		manager.registerCommand(new ConsoleDisband(this));
		manager.registerCommand(new ConsoleHelp(this));
		manager.registerCommand(new ConsoleInfo(this));
		manager.registerCommand(new ConsoleList(this));
		manager.registerCommand(new ConsolePromote(this));
		manager.registerCommand(new ConsoleRemove(this));
		manager.registerCommand(new ConsoleRename(this));
		manager.registerCommand(new ConsoleTag(this));
		manager.registerCommand(new ConsoleOpen(this));
		manager.registerCommand(new ConsoleSet(this));
		manager.registerCommand(new ConsoleSetLeader(this));
		manager.registerCommand(new ConsoleTeleAllHQ(this));
	}

	public void registerServerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand(new ServerAdminChatSpy(this));
		manager.registerCommand(new ServerAdminDisband(this));
		manager.registerCommand(new ServerAdminDemote(this));
		manager.registerCommand(new ServerAdminHeadquarters(this));
		manager.registerCommand(new ServerAdminPromote(this));
		manager.registerCommand(new ServerAdminRemove(this));
		manager.registerCommand(new ServerAdminRename(this));
		manager.registerCommand(new ServerAdminTag(this));
		manager.registerCommand(new ServerAdminOpen(this));
		manager.registerCommand(new ServerAdminSet(this));
		manager.registerCommand(new ServerAdminSetHeadquarters(this));
		manager.registerCommand(new ServerAdminSetLeader(this));
		manager.registerCommand(new ServerAdminTeleAllHQ(this));
		manager.registerCommand(new ServerAdminTpAll(this));
	}

	public void registerLeaderCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamLeaderDemote(this));
		manager.registerCommand(new TeamLeaderDisband(this));
		manager.registerCommand(new TeamLeaderOpen(this));
		manager.registerCommand(new TeamLeaderRemove(this));
		manager.registerCommand(new TeamLeaderRename(this));
		manager.registerCommand(new TeamLeaderTag(this));
		manager.registerCommand(new TeamLeaderSetLeader(this));
		manager.registerCommand(new TeamLeaderSetRally(this));
	}

	public void registerAdminCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamAdminSetHeadquarters(this));
		manager.registerCommand(new TeamAdminInvite(this));
		manager.registerCommand(new TeamAdminPromote(this));
	}

	public void registerUserCommands(ICommandManager manager)
	{
		manager.registerCommand(new TeamUserMainHelp(this));
		manager.registerCommand(new TeamUserHelp(this));
		manager.registerCommand(new TeamUserInfo(this));
		manager.registerCommand(new TeamUserList(this));
		manager.registerCommand(new TeamUserCreate(this));
		manager.registerCommand(new TeamUserJoin(this));
		manager.registerCommand(new TeamUserLeave(this));
		manager.registerCommand(new TeamUserAccept(this));
		manager.registerCommand(new TeamUserHeadquarters(this));
		manager.registerCommand(new TeamUserTeleport(this));
		manager.registerCommand(new TeamUserReturn(this));
		manager.registerCommand(new TeamUserRally(this));
		manager.registerCommand(new TeamUserChat(this));
		manager.registerCommand(new TeamUserMessage(this));
	}
}
