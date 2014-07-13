package me.protocos.xteam.data.configuration;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.core.ITeamCoordinator;
import me.protocos.xteam.data.AbstractConfiguration;
import me.protocos.xteam.entity.Team;
import me.protocos.xteam.util.CommonUtil;

public class Configuration extends AbstractConfiguration
{
	private static final Integer MAX = new Integer(1000);
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
	public static List<String> DEFAULT_TEAM_NAMES = CommonUtil.emptyList();
	public static List<String> DISABLED_WORLDS = CommonUtil.emptyList();

	private ITeamCoordinator teamCoordinator;

	public Configuration(TeamPlugin teamPlugin, File file)
	{
		super(teamPlugin, file);
		this.teamCoordinator = teamPlugin.getTeamCoordinator();
	}

	public void load()
	{
		CAN_CHAT = this.getAttribute("teamchatenable", true, "Allows/Disallows the use of team chat function completely", "canteamchat");
		HQ_ON_DEATH = this.getAttribute("hqondeath", true, "When a player dies, they are teleported to their headquarters when they respawn");
		TEAM_WOLVES = this.getAttribute("teamwolves", true, "Protects your wolfies from you and your teammates from damaging them");
		RANDOM_TEAM = this.getAttribute("randomjointeam", false, "Player randomly joins one of the default teams on joining");
		BALANCE_TEAMS = this.getAttribute("balanceteams", false, "Balance teams when someone randomly joins");
		DEFAULT_TEAM_ONLY = this.getAttribute("onlyjoindefaultteam", false, "When true, players can only join one of the default teams listed above");
		DEFAULT_HQ_ON_JOIN = this.getAttribute("defaulthqonjoin", false, "When true, players on default teams are teleported to their headquarters on join");
		TEAM_TAG_ENABLED = this.getAttribute("teamtagenabled", true, "When true, players have their team tag displayed when in chat");
		TEAM_FRIENDLY_FIRE = this.getAttribute("friendlyfire", false, "When true, friendly fire will be enabled for all teams", "teamfriendlyfire");
		NO_PERMISSIONS = this.getAttribute("nopermissions", false, "When true, xTeam will give all regular commands to players and admin commands to OPs");
		ALPHA_NUM = this.getAttribute("alphanumericnames", true, "When true, players can only create teams with alphanumeric names and no symbols (e.g. TeamAwesome123)");
		DISPLAY_COORDINATES = this.getAttribute("displaycoordinates", true, "When true, players can see coordinates of other team mates in team info");
		DISPLAY_RELATIVE_COORDINATES = this.getAttribute("displayrelativelocations", true, "When true, players see relative directions to team mates and team headquarters");
		SEND_ANONYMOUS_ERROR_REPORTS = this.getAttribute("anonymouserrorreporting", true, "When true, sends anonymous error reports for faster debugging");
		MAX_PLAYERS = this.getAttribute("playersonteam", 10, 2, MAX, "Amount of players that can be on a team");
		HQ_INTERVAL = this.getAttribute("sethqinterval", 0, 0, MAX, "Delay in hours between use of /team sethq");
		ENEMY_PROX = this.getAttribute("enemyproximity", 16, 0, MAX, "When teleporting, if enemies are within this radius of blocks, the teleport is delayed");
		TELE_DELAY = this.getAttribute("teledelay", 7, 0, MAX, "Delay in seconds for teleporting when enemies are near");
		TELE_REFRESH_DELAY = this.getAttribute("telerefreshdelay", 0, 0, MAX, "Delay in seconds for when you can use team teleporting. Does not include /team return");
		CREATE_INTERVAL = this.getAttribute("createteamdelay", 20, 0, MAX, "Delay in minutes for creating teams");
		LAST_ATTACKED_DELAY = this.getAttribute("lastattackeddelay", 15, 0, MAX, "How long a player has to wait after being attacked to teleport");
		TEAM_NAME_LENGTH = this.getAttribute("teamnamemaxlength", 0, 0, MAX, "Maximum length of a team name (0 = unlimited)");
		RALLY_DELAY = this.getAttribute("rallydelay", 2, 0, MAX, "Delay in minutes that a team rally stays active");
		COLOR_TAG = this.getAttribute("tagcolor", "green", "Color representing the color of the tag in game (e.g. green, dark_red, light_purple)");
		COLOR_NAME = this.getAttribute("chatnamecolor", "dark_green", "Color representing the color of player names in team chat (e.g. green, dark_red, light_purple)");
		STORAGE_TYPE = this.getAttribute("storagetype", "file", "Method for storing data for the plugin (Options: file, sqlite, mysql:host:port:databasename:username:password)");
		DEFAULT_TEAM_NAMES = this.getAsList("defaultteams", "", "Default list of teams for the server separated by commas  (e.g. defaultteams=red,green,blue,yellow)");
		DISABLED_WORLDS = this.getAsList("disabledworlds", "", "World names, separated by commas, that xTeam is disabled in (e.g. disabledworlds=world,world_nether,world_the_end)");
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
}
