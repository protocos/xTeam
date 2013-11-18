package me.protocos.xteam.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.FileReader;
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
	public static boolean SEND_ANONYMOUS_ERROR_REPORTS;
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

	private HashList<String, String> descriptions;
	private HashList<String, Object> values;
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
		descriptions = CommonUtil.emptyHashList();
		values = CommonUtil.emptyHashList();
	}

	public void addAttribute(String name, Object defaultValue, String description)
	{
		descriptions.put(name, description);
		Object value = parse(name, defaultValue);
		values.put(name, value);
	}

	private Object parse(String name, Object defaultValue)
	{
		Object object = null;
		if (defaultValue instanceof Double)
			object = fileReader.getDouble(name, CommonUtil.assignFromType(defaultValue, Double.class));
		if (defaultValue instanceof Boolean)
			object = fileReader.getBoolean(name, CommonUtil.assignFromType(defaultValue, Boolean.class));
		if (defaultValue instanceof Integer)
			object = fileReader.getInteger(name, CommonUtil.assignFromType(defaultValue, Integer.class));
		if (defaultValue instanceof String)
			object = fileReader.getString(name, CommonUtil.assignFromType(defaultValue, String.class));
		return object;
	}

	private String getFormattedAttribute(String name)
	{
		return new StringBuilder().append(name).append(" = ").append(values.get(name)).toString();
	}

	private String getFormattedComment(String name)
	{
		return new StringBuilder().append("# ").append(name).append(" - ").append(descriptions.get(name)).append(" (default = ").append(values.get(name)).append(")").toString();
	}

	public void removeAttribute(String name)
	{
		descriptions.remove(name);
		values.remove(name);
	}

	private String getLineBreak()
	{
		String max = "";
		for (String name : values.getOrder())
		{
			if (this.getFormattedComment(name).length() > max.length())
				max = this.getFormattedComment(name);
		}
		for (String name : descriptions.getOrder())
		{
			if (this.getFormattedComment(name).length() > max.length())
				max = this.getFormattedComment(name);
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
		CAN_CHAT = CommonUtil.assignFromType(values.get("canteamchat"), Boolean.class);
		HQ_ON_DEATH = CommonUtil.assignFromType(values.get("hqondeath"), Boolean.class);
		TEAM_WOLVES = CommonUtil.assignFromType(values.get("teamwolves"), Boolean.class);
		RANDOM_TEAM = CommonUtil.assignFromType(values.get("randomjointeam"), Boolean.class);
		BALANCE_TEAMS = CommonUtil.assignFromType(values.get("balanceteams"), Boolean.class);
		DEFAULT_TEAM_ONLY = CommonUtil.assignFromType(values.get("onlyjoindefaultteam"), Boolean.class);
		DEFAULT_HQ_ON_JOIN = CommonUtil.assignFromType(values.get("defaulthqonjoin"), Boolean.class);
		TEAM_TAG_ENABLED = CommonUtil.assignFromType(values.get("teamtagenabled"), Boolean.class);
		TEAM_FRIENDLY_FIRE = CommonUtil.assignFromType(values.get("teamfriendlyfire"), Boolean.class);
		NO_PERMISSIONS = CommonUtil.assignFromType(values.get("nopermissions"), Boolean.class);
		ALPHA_NUM = CommonUtil.assignFromType(values.get("alphanumericnames"), Boolean.class);
		DISPLAY_COORDINATES = CommonUtil.assignFromType(values.get("displaycoordinates"), Boolean.class);
		SEND_ANONYMOUS_ERROR_REPORTS = CommonUtil.assignFromType(values.get("anonymouserrorreporting"), Boolean.class);
		MAX_PLAYERS = CommonUtil.assignFromType(values.get("playersonteam"), Integer.class);
		HQ_INTERVAL = CommonUtil.assignFromType(values.get("sethqinterval"), Integer.class);
		TELE_RADIUS = CommonUtil.assignFromType(values.get("teleportradius"), Integer.class);
		ENEMY_PROX = CommonUtil.assignFromType(values.get("enemyproximity"), Integer.class);
		TELE_DELAY = CommonUtil.assignFromType(values.get("teledelay"), Integer.class);
		CREATE_INTERVAL = CommonUtil.assignFromType(values.get("createteamdelay"), Integer.class);
		LAST_ATTACKED_DELAY = CommonUtil.assignFromType(values.get("lastattackeddelay"), Integer.class);
		TEAM_TAG_LENGTH = CommonUtil.assignFromType(values.get("teamtagmaxlength"), Integer.class);
		TELE_REFRESH_DELAY = CommonUtil.assignFromType(values.get("telerefreshdelay"), Integer.class);
		RALLY_DELAY = CommonUtil.assignFromType(values.get("rallydelay"), Integer.class);
		COLOR_TAG = CommonUtil.assignFromType(values.get("tagcolor"), String.class);
		COLOR_NAME = CommonUtil.assignFromType(values.get("chatnamecolor"), String.class);
		DEFAULT_TEAM_NAMES = CommonUtil.toList(CommonUtil.assignFromType(values.get("defaultteams"), String.class).replace(" ", "").split(","));
		DISABLED_WORLDS = CommonUtil.toList(CommonUtil.assignFromType(values.get("disabledworlds"), String.class).replace(" ", "").split(","));
		this.ensureDefaultTeams();
	}

	private void ensureDefaultTeams()
	{
		for (String name : Configuration.DEFAULT_TEAM_NAMES)
		{
			Team team = new Team.Builder(name).defaultTeam(true).openJoining(true).build();
			if (!CommonUtil.containsIgnoreCase(xTeam.getInstance().getTeamManager().getDefaultTeams().getOrder(), name))
				xTeam.getInstance().getTeamManager().addTeam(team);
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
		descriptions.sort();
		for (String name : descriptions.getOrder())
		{
			output += this.getFormattedComment(name) + "\n";
		}
		output += getLineBreak();
		values.sort();
		for (String name : values.getOrder())
		{
			output += this.getFormattedAttribute(name) + "\n";
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
