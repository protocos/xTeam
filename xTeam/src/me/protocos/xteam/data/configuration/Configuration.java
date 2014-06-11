package me.protocos.xteam.data.configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import org.bukkit.permissions.Permission;

public class Configuration
{
	public static TreeSet<String> chatStatus = new TreeSet<String>();
	public static HashSet<String> spies = new HashSet<String>();
	public static HashMap<String, Long> lastCreated = new HashMap<String, Long>();
	public static boolean LOCATIONS_ENABLED;
	public static boolean CAN_CHAT;
	public static boolean HQ_ON_DEATH;
	public static boolean TEAM_WOLVES;
	public static boolean RANDOM_TEAM;
	public static boolean BALANCE_TEAMS;
	public static boolean DEFAULT_TEAM_ONLY;
	public static boolean DEFAULT_HQ_ON_JOIN;
	public static boolean TEAM_TAG_ENABLED;
	public static boolean TEAM_FRIENDLY_FIRE;
	public static boolean NO_PERMISSIONS;
	public static boolean ALPHA_NUM;
	public static boolean DISPLAY_COORDINATES;
	public static boolean DISPLAY_RELATIVE_COORDINATES;
	public static boolean SEND_ANONYMOUS_ERROR_REPORTS;
	public static int MAX_PLAYERS;
	public static int HQ_INTERVAL;
	public static int ENEMY_PROX;
	public static int TELE_DELAY;
	public static int CREATE_INTERVAL;
	public static int LAST_ATTACKED_DELAY;
	public static int TEAM_NAME_LENGTH;
	public static int MAX_NUM_LOCATIONS;
	public static int TELE_REFRESH_DELAY;
	public static int RALLY_DELAY;
	public static int SAVE_DATA_INTERVAL;
	public static String COLOR_TAG;
	public static String COLOR_NAME;
	public static String STORAGE_TYPE;
	public static List<String> DEFAULT_TEAM_NAMES = new ArrayList<String>();
	public static List<String> DISABLED_WORLDS = new ArrayList<String>();

	private HashList<String, ConfigurationOption<?>> options;
	private FileReader fileReader;
	private FileWriter fileWriter;
	private TeamPlugin teamPlugin;
	private ITeamCoordinator teamCoordinator;
	private ILog log;

	public Configuration(TeamPlugin teamPlugin, File file)
	{
		this.teamPlugin = teamPlugin;
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
		this.log = teamPlugin.getLog();
		this.fileReader = new FileReader(log, file, false);
		try
		{
			fileWriter = new FileWriter(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.options = CommonUtil.emptyHashList();
	}

	private <T> void addAttribute(String name, T defaultValue, String description)
	{
		T value = parse(name, defaultValue);
		ConfigurationOption<T> option = new ConfigurationOption<T>(name, defaultValue, description, value);
		options.put(option.getKey(), option);
	}

	private <T> T parse(String name, T defaultValue)
	{
		return fileReader.get(name, defaultValue);
	}

	private String getLineBreak()
	{
		String max = "";
		for (ConfigurationOption<?> option : options)
		{
			if (option.length() > max.length())
				max = option.getComment();
		}
		List<Permission> perms = teamPlugin.getPermissions();
		for (Permission perm : perms)
		{
			if (this.formatPermission(perm).length() > max.length())
				max = this.formatPermission(perm);
		}
		return max.replaceAll(".", "#") + "\n";
	}

	private <T> T getAs(String key, Class<T> clazz)
	{
		return CommonUtil.assignFromType(options.get(key).getValue(), clazz);
	}

	private List<String> getAsList(String key)
	{
		return CommonUtil.toList(getAs(key, String.class).replace("\\s+", "").split(","));
	}

	public void load()
	{
		this.write();
		CAN_CHAT = getAs("canteamchat", Boolean.class);
		HQ_ON_DEATH = getAs("hqondeath", Boolean.class);
		TEAM_WOLVES = getAs("teamwolves", Boolean.class);
		RANDOM_TEAM = getAs("randomjointeam", Boolean.class);
		BALANCE_TEAMS = getAs("balanceteams", Boolean.class);
		DEFAULT_TEAM_ONLY = getAs("onlyjoindefaultteam", Boolean.class);
		DEFAULT_HQ_ON_JOIN = getAs("defaulthqonjoin", Boolean.class);
		TEAM_TAG_ENABLED = getAs("teamtagenabled", Boolean.class);
		TEAM_FRIENDLY_FIRE = getAs("teamfriendlyfire", Boolean.class);
		NO_PERMISSIONS = getAs("nopermissions", Boolean.class);
		ALPHA_NUM = getAs("alphanumericnames", Boolean.class);
		DISPLAY_COORDINATES = getAs("displaycoordinates", Boolean.class);
		DISPLAY_RELATIVE_COORDINATES = getAs("displayrelativelocations", Boolean.class);
		SEND_ANONYMOUS_ERROR_REPORTS = getAs("anonymouserrorreporting", Boolean.class);
		MAX_PLAYERS = getAs("playersonteam", Integer.class);
		HQ_INTERVAL = getAs("sethqinterval", Integer.class);
		ENEMY_PROX = getAs("enemyproximity", Integer.class);
		TELE_DELAY = getAs("teledelay", Integer.class);
		CREATE_INTERVAL = getAs("createteamdelay", Integer.class);
		LAST_ATTACKED_DELAY = getAs("lastattackeddelay", Integer.class);
		TEAM_NAME_LENGTH = getAs("teamnamemaxlength", Integer.class);
		TELE_REFRESH_DELAY = getAs("telerefreshdelay", Integer.class);
		RALLY_DELAY = getAs("rallydelay", Integer.class);
		COLOR_TAG = getAs("tagcolor", String.class);
		COLOR_NAME = getAs("chatnamecolor", String.class);
		STORAGE_TYPE = getAs("storagetype", String.class);
		DEFAULT_TEAM_NAMES = getAsList("defaultteams");
		DISABLED_WORLDS = getAsList("disabledworlds");
		this.ensureDefaultTeams();
	}

	private void ensureDefaultTeams()
	{
		for (String name : Configuration.DEFAULT_TEAM_NAMES)
		{
			Team team = new Team.Builder(teamPlugin, name).defaultTeam(true).openJoining(true).build();
			if (!CommonUtil.containsIgnoreCase(teamCoordinator.getDefaultTeams().getOrder(), name))
				teamCoordinator.createTeam(team);
		}
	}

	private void write()
	{
		try
		{
			this.addAttribute("playersonteam", 10, "Amount of players that can be on a team");
			this.addAttribute("sethqinterval", 0, "Delay in hours between use of /team sethq");
			this.addAttribute("canteamchat", true, "Allows/Disallows the use of team chat function completely");
			this.addAttribute("hqondeath", true, "When a player dies, they are teleported to their headquarters when they respawn");
			this.addAttribute("enemyproximity", 16, "When teleporting, if enemies are within this radius of blocks, the teleport is delayed");
			this.addAttribute("teledelay", 7, "Delay in seconds for teleporting when enemies are near");
			this.addAttribute("telerefreshdelay", 0, "Delay in seconds for when you can use team teleporting. Does not include /team return");
			this.addAttribute("createteamdelay", 20, "Delay in minutes for creating teams");
			this.addAttribute("teamwolves", true, "Protects your wolfies from you and your teammates from damaging them");
			this.addAttribute("defaultteams", "", "Default list of teams for the server separated by commas  (e.g. defaultteams=red,green,blue,yellow)");
			this.addAttribute("randomjointeam", false, "Player randomly joins one of the default teams on joining");
			this.addAttribute("balanceteams", false, "Balance teams when someone randomly joins");
			this.addAttribute("onlyjoindefaultteam", false, "When true, players can only join one of the default teams listed above");
			this.addAttribute("defaulthqonjoin", false, "When true, players on default teams are teleported to their headquarters on join");
			this.addAttribute("anonymouserrorreporting", true, "When true, sends anonymous error reports for faster debugging");
			this.addAttribute("lastattackeddelay", 15, "How long a player has to wait after being attacked to teleport");
			this.addAttribute("teamtagenabled", true, "When true, players have their team tag displayed when in chat");
			this.addAttribute("teamnamemaxlength", 0, "Maximum length of a team name (0 = unlimited)");
			this.addAttribute("disabledworlds", "", "World names, separated by commas, that xTeam is disabled in (e.g. disabledworlds=world,world_nether,world_the_end)");
			this.addAttribute("nopermissions", false, "When true, xTeam will give all regular commands to players and admin commands to OPs");
			this.addAttribute("teamfriendlyfire", false, "When true, friendly fire will be enabled for all teams");
			this.addAttribute("alphanumericnames", true, "When true, players can only create teams with alphanumeric names and no symbols (e.g. TeamAwesome123)");
			this.addAttribute("displaycoordinates", true, "When true, players can see coordinates of other team mates in team info");
			this.addAttribute("displayrelativelocations", true, "When true, players see relative directions to team mates and team headquarters");
			this.addAttribute("tagcolor", "green", "Color representing the color of the tag in game (e.g. green, dark_red, light_purple)");
			this.addAttribute("chatnamecolor", "dark_green", "Color representing the color of player names in team chat (e.g. green, dark_red, light_purple)");
			this.addAttribute("rallydelay", 2, "Delay in minutes that a team rally stays active");
			this.addAttribute("newparam", 1, "Delay in minutes that a team rally stays active");
			this.addAttribute("storagetype", "file", "Method for storing data for the plugin (Options: file, sqlite, mysql:host:port:databasename:username:password)");
			fileWriter.write(toString());
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String formatPermission(Permission perm)
	{
		return "# " + perm.getName() + " - " + perm.getDescription();
	}

	public String toString()
	{
		String output = getLineBreak() +
				"# \n" +
				"# xTeam Preferences\n" +
				"# \n" +
				getLineBreak();
		options.sort();
		for (ConfigurationOption<?> option : options)
		{
			output += option.getComment() + "\n";
		}
		output += getLineBreak();
		for (ConfigurationOption<?> option : options)
		{
			output += option.toString() + "\n";
		}
		output += getLineBreak() +
				"# \n" +
				"# Permissions\n" +
				"# \n" +
				getLineBreak();
		List<Permission> perms = teamPlugin.getPermissions();
		for (Permission perm : perms)
		{
			output += this.formatPermission(perm) + "\n";
		}
		output += getLineBreak();
		return output;
	}
}
