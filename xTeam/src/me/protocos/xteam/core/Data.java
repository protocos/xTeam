package me.protocos.xteam.core;

import java.io.File;
import java.util.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.FileReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class Data
{
	public static Server BUKKIT = Bukkit.getServer();
	public static boolean variable = false;
	public static File settings;
	public static TreeSet<String> chatStatus = new TreeSet<String>();
	public static HashSet<String> spies = new HashSet<String>();
	public static HashSet<String> damagedByPlayer = new HashSet<String>();
	public static HashMap<Player, Location> returnLocations = new HashMap<Player, Location>();
	public static HashMap<String, Integer> taskIDs = new HashMap<String, Integer>();
	public static HashMap<String, Integer> countWaitTime = new HashMap<String, Integer>();
	public static HashMap<String, Long> hasTeleported = new HashMap<String, Long>();
	public static HashMap<String, Long> lastAttacked = new HashMap<String, Long>();
	public static HashMap<String, Long> lastCreated = new HashMap<String, Long>();
	public static boolean SPOUT_ENABLED;
	public static boolean LOCATIONS_ENABLED;
	public static boolean CAN_CHAT;
	public static boolean HIDE_NAMES;
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
	public static int MAX_PLAYERS;
	public static int REVEAL_TIME;
	public static int HQ_INTERVAL;
	public static int TELE_RADIUS;
	public static int ENEMY_PROX;
	public static int TELE_DELAY;
	public static int CREATE_INTERVAL;
	public static int LAST_ATTACKED_DELAY;
	public static int TEAM_TAG_LENGTH;
	public static int MAX_NUM_LOCATIONS;
	public static int REFRESH_DELAY;
	public static String TAG_COLOR;
	public static String NAME_COLOR;
	public static List<String> DEFAULT_TEAM_NAMES = new ArrayList<String>();
	public static List<String> DISABLED_WORLDS = new ArrayList<String>();

	public static void ensureDefaultTeams()
	{
		for (String name : DEFAULT_TEAM_NAMES)
		{
			Team team = new Team.Builder(name).defaultTeam(true).openJoining(true).build();
			boolean contains = false;
			for (String teamName : xTeam.tm.getDefaultTeamNames())
			{
				if (teamName.equals(name))
					contains = true;
			}
			if (!contains)
				xTeam.tm.addTeam(team);
		}
	}

	public static void load()
	{
		readConfig(settings);
	}
	public static void readConfig(File file)
	{
		FileReader reader = new FileReader(file, false);
		SPOUT_ENABLED = Data.BUKKIT.getPluginManager().getPlugin("Spout") != null;
		LOCATIONS_ENABLED = Data.BUKKIT.getPluginManager().getPlugin("xTeamLocations") != null;
		CAN_CHAT = reader.getBoolean("canteamchat", true);
		HIDE_NAMES = reader.getBoolean("teamhidename", true);
		HQ_ON_DEATH = reader.getBoolean("hqondeath", true);
		TEAM_WOLVES = reader.getBoolean("teamwolves", true);
		RANDOM_TEAM = reader.getBoolean("randomjointeam", false);
		BALANCE_TEAMS = reader.getBoolean("balanceteams", false);
		DEFAULT_TEAM_ONLY = reader.getBoolean("onlyjoindefaultteam", false);
		DEFAULT_HQ_ON_JOIN = reader.getBoolean("defaulthqonjoin", false);
		TEAM_TAG_ENABLED = reader.getBoolean("teamtagenabled", true);
		TEAM_FRIENDLY_FIRE = reader.getBoolean("teamfriendlyfire", false);
		NO_PERMISSIONS = reader.getBoolean("nopermissions", false);
		ALPHA_NUM = reader.getBoolean("alphanumericnames", false);
		DISPLAY_COORDINATES = reader.getBoolean("displaycoordinates", true);
		MAX_PLAYERS = reader.getInteger("playersonteam", 10);
		REVEAL_TIME = reader.getInteger("namerevealtime", 5);
		HQ_INTERVAL = reader.getInteger("sethqinterval", 0);
		TELE_RADIUS = reader.getInteger("teleportradius", 500);
		ENEMY_PROX = reader.getInteger("enemyproximity", 16);
		TELE_DELAY = reader.getInteger("teledelay", 10);
		CREATE_INTERVAL = reader.getInteger("createteamdelay", 0);
		LAST_ATTACKED_DELAY = reader.getInteger("lastattackeddelay", 15);
		TEAM_TAG_LENGTH = reader.getInteger("teamtagmaxlength", 0);
		MAX_NUM_LOCATIONS = reader.getInteger("maxnumlocations", 9);
		REFRESH_DELAY = reader.getInteger("telerefreshdelay", 60);
		TAG_COLOR = reader.getString("tagcolor", "green");
		NAME_COLOR = reader.getString("chatnamecolor", "dark_green");
		DEFAULT_TEAM_NAMES = CommonUtil.toList(reader.getString("defaultteams", "").replace(" ", "").split(","));
		DISABLED_WORLDS = CommonUtil.toList(reader.getString("disabledworlds", "").replace(" ", "").split(","));
	}
	private Data()
	{
		//we don't want the default constructor to be called from within the class either...
		throw new AssertionError();
	}
}
