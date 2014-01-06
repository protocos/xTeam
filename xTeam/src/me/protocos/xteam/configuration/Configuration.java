package me.protocos.xteam.configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.entity.Team;
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
	//	public static boolean SEND_ANONYMOUS_ERROR_REPORTS;
	public static int MAX_PLAYERS;
	public static int HQ_INTERVAL;
	public static int TELE_RADIUS;
	public static int ENEMY_PROX;
	public static int TELE_DELAY;
	public static int CREATE_INTERVAL;
	public static int LAST_ATTACKED_DELAY;
	public static int TEAM_TAG_LENGTH;
	public static int MAX_NUM_LOCATIONS;
	public static int TELE_REFRESH_DELAY;
	public static int RALLY_DELAY;
	public static String COLOR_TAG;
	public static String COLOR_NAME;
	public static List<String> DEFAULT_TEAM_NAMES = new ArrayList<String>();
	public static List<String> DISABLED_WORLDS = new ArrayList<String>();

	private HashList<String, ConfigurationOption<?>> options;
	private FileReader fileReader;
	private FileWriter fileWriter;

	public Configuration(File file)
	{
		fileReader = new FileReader(file, false);
		try
		{
			fileWriter = new FileWriter(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		options = CommonUtil.emptyHashList();
	}

	public <T> void addAttribute(String name, T defaultValue, String description)
	{
		T value = parse(name, defaultValue);
		ConfigurationOption<T> option = new ConfigurationOption<T>(name, defaultValue, description, value);
		options.put(option.getKey(), option);
	}

	private <T> T parse(String name, T defaultValue)
	{
		return fileReader.get(name, defaultValue);
	}

	public void removeAttribute(String name)
	{
		options.remove(name);
	}

	private String getLineBreak()
	{
		String max = "";
		for (ConfigurationOption<?> option : options)
		{
			if (option.length() > max.length())
				max = option.getComment();
		}
		List<Permission> perms = xTeam.getInstance().getPermissions();
		for (Permission perm : perms)
		{
			if (this.formatPermission(perm).length() > max.length())
				max = this.formatPermission(perm);
		}
		return max.replaceAll(".", "#") + "\n";
	}

	public void load()
	{
		reload();
	}

	public void reload()
	{
		CAN_CHAT = CommonUtil.assignFromType(options.get("canteamchat").getValue(), Boolean.class);
		HQ_ON_DEATH = CommonUtil.assignFromType(options.get("hqondeath").getValue(), Boolean.class);
		TEAM_WOLVES = CommonUtil.assignFromType(options.get("teamwolves").getValue(), Boolean.class);
		RANDOM_TEAM = CommonUtil.assignFromType(options.get("randomjointeam").getValue(), Boolean.class);
		BALANCE_TEAMS = CommonUtil.assignFromType(options.get("balanceteams").getValue(), Boolean.class);
		DEFAULT_TEAM_ONLY = CommonUtil.assignFromType(options.get("onlyjoindefaultteam").getValue(), Boolean.class);
		DEFAULT_HQ_ON_JOIN = CommonUtil.assignFromType(options.get("defaulthqonjoin").getValue(), Boolean.class);
		TEAM_TAG_ENABLED = CommonUtil.assignFromType(options.get("teamtagenabled").getValue(), Boolean.class);
		TEAM_FRIENDLY_FIRE = CommonUtil.assignFromType(options.get("teamfriendlyfire").getValue(), Boolean.class);
		NO_PERMISSIONS = CommonUtil.assignFromType(options.get("nopermissions").getValue(), Boolean.class);
		ALPHA_NUM = CommonUtil.assignFromType(options.get("alphanumericnames").getValue(), Boolean.class);
		DISPLAY_COORDINATES = CommonUtil.assignFromType(options.get("displaycoordinates").getValue(), Boolean.class);
		//		SEND_ANONYMOUS_ERROR_REPORTS = CommonUtil.assignFromType(options.get("anonymouserrorreporting").getValue(), Boolean.class);
		MAX_PLAYERS = CommonUtil.assignFromType(options.get("playersonteam").getValue(), Integer.class);
		HQ_INTERVAL = CommonUtil.assignFromType(options.get("sethqinterval").getValue(), Integer.class);
		TELE_RADIUS = CommonUtil.assignFromType(options.get("teleportradius").getValue(), Integer.class);
		ENEMY_PROX = CommonUtil.assignFromType(options.get("enemyproximity").getValue(), Integer.class);
		TELE_DELAY = CommonUtil.assignFromType(options.get("teledelay").getValue(), Integer.class);
		CREATE_INTERVAL = CommonUtil.assignFromType(options.get("createteamdelay").getValue(), Integer.class);
		LAST_ATTACKED_DELAY = CommonUtil.assignFromType(options.get("lastattackeddelay").getValue(), Integer.class);
		TEAM_TAG_LENGTH = CommonUtil.assignFromType(options.get("teamtagmaxlength").getValue(), Integer.class);
		TELE_REFRESH_DELAY = CommonUtil.assignFromType(options.get("telerefreshdelay").getValue(), Integer.class);
		RALLY_DELAY = CommonUtil.assignFromType(options.get("rallydelay").getValue(), Integer.class);
		COLOR_TAG = CommonUtil.assignFromType(options.get("tagcolor").getValue(), String.class);
		COLOR_NAME = CommonUtil.assignFromType(options.get("chatnamecolor").getValue(), String.class);
		DEFAULT_TEAM_NAMES = CommonUtil.toList(CommonUtil.assignFromType(options.get("defaultteams").getValue(), String.class).replace(" ", "").split(","));
		DISABLED_WORLDS = CommonUtil.toList(CommonUtil.assignFromType(options.get("disabledworlds").getValue(), String.class).replace(" ", "").split(","));
		this.ensureDefaultTeams();
	}

	private void ensureDefaultTeams()
	{
		for (String name : Configuration.DEFAULT_TEAM_NAMES)
		{
			Team team = new Team.Builder(name).defaultTeam(true).openJoining(true).build();
			if (!CommonUtil.containsIgnoreCase(xTeam.getInstance().getTeamManager().getDefaultTeams().getOrder(), name))
				xTeam.getInstance().getTeamManager().createTeam(team);
		}
	}

	public void write()
	{
		try
		{
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
		List<Permission> perms = xTeam.getInstance().getPermissions();
		for (Permission perm : perms)
		{
			output += this.formatPermission(perm) + "\n";
		}
		output += getLineBreak();
		return output;
	}
}
