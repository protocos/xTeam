package me.protocos.xteam;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.command.CommandManager;
import me.protocos.xteam.command.ICommandManager;
import me.protocos.xteam.core.IPlayerManager;
import me.protocos.xteam.core.ITeamManager;
import me.protocos.xteam.core.PlayerManager;
import me.protocos.xteam.core.TeamManager;
import me.protocos.xteam.data.PlayerDataDB;
import me.protocos.xteam.data.PlayerDataFile;
import me.protocos.xteam.data.configuration.Configuration;
import me.protocos.xteam.entity.ITeam;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.event.EventDispatcher;
import me.protocos.xteam.event.IEventDispatcher;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.model.Log;
import me.protocos.xteam.util.BukkitUtil;
import me.protocos.xteam.util.SystemUtil;
import org.bukkit.permissions.Permission;

public final class XTeam
{
	private static XTeam xteam;
	private String version;
	private ILog logger;
	private IEventDispatcher eventDispatcher;
	private IPlayerManager playerManager;
	private ITeamManager teamManager;
	private ICommandManager commandManager;
	private List<Permission> permissions;
	private Configuration configLoader;

	private XTeam()
	{
		this.eventDispatcher = new EventDispatcher();
		this.commandManager = new CommandManager();
	}

	public static XTeam getInstance()
	{
		if (xteam == null)
		{
			xteam = new XTeam();
		}
		return xteam;
	}

	public ILog getLog()
	{
		return logger;
	}

	public ICommandManager getCommandManager()
	{
		return commandManager;
	}

	public IEventDispatcher getEventDispatcher()
	{
		return eventDispatcher;
	}

	public ITeamManager getTeamManager()
	{
		return teamManager;
	}

	public IPlayerManager getPlayerManager()
	{
		return playerManager;
	}

	public List<Permission> getPermissions()
	{
		return permissions;
	}

	public String getVersion()
	{
		return version;
	}

	public void load(TeamPlugin plugin)
	{
		if ("XTeamPlugin".equals(plugin.getPluginName()) || "FakeTeamPlugin".equals(plugin.getPluginName()))
		{
			this.version = plugin.getVersion();
			if (plugin.getLog() == null)
				this.logger = new Log(plugin.getFolder() + "xTeam.log");
			else
				this.logger = plugin.getLog();
			this.permissions = new ArrayList<Permission>(plugin.getPermissions());
			this.eventDispatcher = new EventDispatcher();
			this.initFileSystem(plugin);
			this.initDataStorage(plugin);
		}
		else
		{
			this.permissions.addAll(plugin.getPermissions());
		}
		this.commandManager.register(plugin);
	}

	private void initDataStorage(TeamPlugin plugin)
	{
		if (this.playerManager == null)
		{
			if ("SQLite".equals(Configuration.STORAGE_TYPE))
			{
				if (BukkitUtil.getPluginManager().getPlugin("SQLibrary") != null)
					this.playerManager = new PlayerManager(new PlayerDataDB(plugin));
				else
					this.getLog().error("Cannot use \"" + Configuration.STORAGE_TYPE + "\" for storage because plugin \"SQLibrary\" cannot be found!" +
							"\nSQLibrary can be found here: http://dev.bukkit.org/bukkit-plugins/sqlibrary/");
			}
			else if ("file".equals(Configuration.STORAGE_TYPE))
			{
				this.playerManager = new PlayerManager(new PlayerDataFile(plugin));
			}
			else
			{
				this.getLog().error("\"" + Configuration.STORAGE_TYPE + "\" is not a valid storage type");
			}
			if (this.playerManager == null)
			{
				this.getLog().info("Resorting to \"file\" storage type");
				this.playerManager = new PlayerManager(new PlayerDataFile(plugin));
			}
		}
		if (this.teamManager == null)
		{
			this.teamManager = new TeamManager(eventDispatcher);
		}
	}

	private void initFileSystem(TeamPlugin plugin)
	{
		SystemUtil.ensureFolder(plugin.getFolder());
		SystemUtil.ensureFile(plugin.getFolder() + "teams.txt");
		this.configLoader = new Configuration(SystemUtil.ensureFile(plugin.getFolder() + "xTeam.cfg"));
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

	public void readTeamData(File f)
	{
		try
		{
			String line;
			BufferedReader br = new BufferedReader(new FileReader(f));
			while ((line = br.readLine()) != null)
			{
				Team team = Team.generateTeamFromProperties(line);
				XTeam.getInstance().getTeamManager().createTeam(team);
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void writeTeamData(File f)
	{
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
}
