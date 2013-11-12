package me.protocos.xteam.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import me.protocos.xteam.xTeamPlugin;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.core.Data;
import org.bukkit.permissions.Permission;

public class ConfigLoader
{
	private HashList<String, String> descriptions;
	private HashList<String, Object> values;
	private File file;
	private FileReader fileReader;
	private FileWriter fileWriter;

	public ConfigLoader(String fileName) throws IOException
	{
		file = new File(fileName);
		if (!file.exists())
		{
			file.createNewFile();
		}
		fileReader = new FileReader(file, false);
		fileWriter = new FileWriter(file);
		descriptions = CommonUtil.emptyHashList();
		values = CommonUtil.emptyHashList();
	}

	public void add(String name, Object defaultValue, String description)
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

	private String get(String name)
	{
		return new StringBuilder().append(name).append(" = ").append(values.get(name)).toString();
	}

	private String getComment(String name)
	{
		return new StringBuilder().append("# ").append(name).append(" - ").append(descriptions.get(name)).append(" (default = ").append(values.get(name)).append(")").toString();
	}

	public void remove(String name)
	{
		descriptions.remove(name);
		values.remove(name);
	}

	private String getLineBreak()
	{
		String max = "";
		for (String name : values)
		{
			if (this.getComment(name).length() > max.length())
				max = this.getComment(name);
		}
		for (String name : descriptions)
		{
			if (this.getComment(name).length() > max.length())
				max = this.getComment(name);
		}
		List<Permission> perms = xTeamPlugin.getInstance().getPermissions();
		for (Permission perm : perms)
		{
			if (this.getPermission(perm).length() > max.length())
				max = this.getPermission(perm);
		}
		return max.replaceAll(".", "#") + "\n";
	}

	public void load()
	{
		reload();
	}

	public void reload()
	{
		//		Data.LOCATIONS_ENABLED = BukkitUtil.getPlugin("xTeamLocations") != null;
		Data.CAN_CHAT = CommonUtil.assignFromType(values.get("canteamchat"), Boolean.class);
		Data.HQ_ON_DEATH = CommonUtil.assignFromType(values.get("hqondeath"), Boolean.class);
		Data.TEAM_WOLVES = CommonUtil.assignFromType(values.get("teamwolves"), Boolean.class);
		Data.RANDOM_TEAM = CommonUtil.assignFromType(values.get("randomjointeam"), Boolean.class);
		Data.BALANCE_TEAMS = CommonUtil.assignFromType(values.get("balanceteams"), Boolean.class);
		Data.DEFAULT_TEAM_ONLY = CommonUtil.assignFromType(values.get("onlyjoindefaultteam"), Boolean.class);
		Data.DEFAULT_HQ_ON_JOIN = CommonUtil.assignFromType(values.get("defaulthqonjoin"), Boolean.class);
		Data.TEAM_TAG_ENABLED = CommonUtil.assignFromType(values.get("teamtagenabled"), Boolean.class);
		Data.TEAM_FRIENDLY_FIRE = CommonUtil.assignFromType(values.get("teamfriendlyfire"), Boolean.class);
		Data.NO_PERMISSIONS = CommonUtil.assignFromType(values.get("nopermissions"), Boolean.class);
		Data.ALPHA_NUM = CommonUtil.assignFromType(values.get("alphanumericnames"), Boolean.class);
		Data.DISPLAY_COORDINATES = CommonUtil.assignFromType(values.get("displaycoordinates"), Boolean.class);
		Data.SEND_ANONYMOUS_ERROR_REPORTS = CommonUtil.assignFromType(values.get("anonymouserrorreporting"), Boolean.class);
		Data.MAX_PLAYERS = CommonUtil.assignFromType(values.get("playersonteam"), Integer.class);
		Data.HQ_INTERVAL = CommonUtil.assignFromType(values.get("sethqinterval"), Integer.class);
		Data.TELE_RADIUS = CommonUtil.assignFromType(values.get("teleportradius"), Integer.class);
		Data.ENEMY_PROX = CommonUtil.assignFromType(values.get("enemyproximity"), Integer.class);
		Data.TELE_DELAY = CommonUtil.assignFromType(values.get("teledelay"), Integer.class);
		Data.CREATE_INTERVAL = CommonUtil.assignFromType(values.get("createteamdelay"), Integer.class);
		Data.LAST_ATTACKED_DELAY = CommonUtil.assignFromType(values.get("lastattackeddelay"), Integer.class);
		Data.TEAM_TAG_LENGTH = CommonUtil.assignFromType(values.get("teamtagmaxlength"), Integer.class);
		//		Data.MAX_NUM_LOCATIONS = CommonUtil.assignFromType(values.get("maxnumlocations"), Integer.class);
		Data.TELE_REFRESH_DELAY = CommonUtil.assignFromType(values.get("telerefreshdelay"), Integer.class);
		Data.RALLY_DELAY = CommonUtil.assignFromType(values.get("rallydelay"), Integer.class);
		Data.TAG_COLOR = CommonUtil.assignFromType(values.get("tagcolor"), String.class);
		Data.NAME_COLOR = CommonUtil.assignFromType(values.get("chatnamecolor"), String.class);
		Data.DEFAULT_TEAM_NAMES = CommonUtil.toList(CommonUtil.assignFromType(values.get("defaultteams"), String.class).replace(" ", "").split(","));
		Data.DISABLED_WORLDS = CommonUtil.toList(CommonUtil.assignFromType(values.get("disabledworlds"), String.class).replace(" ", "").split(","));
	}

	public void write() throws IOException
	{
		fileWriter.write(toString());
		fileWriter.close();
	}

	private String getPermission(Permission perm)
	{
		return "# " + perm.getName() + " - " + perm.getDescription();
	}

	public String toString()
	{
		String output = getLineBreak() +
				"# \n" +
				"# xTeamPlugin Preferences\n" +
				"# \n" +
				getLineBreak();
		descriptions.sort();
		for (String name : descriptions)
		{
			output += this.getComment(name) + "\n";
		}
		output += getLineBreak();
		values.sort();
		for (String name : values)
		{
			output += this.get(name) + "\n";
		}
		output += getLineBreak() +
				"# \n" +
				"# Permissions\n" +
				"# \n" +
				getLineBreak();
		List<Permission> perms = xTeamPlugin.getInstance().getPermissions();
		for (Permission perm : perms)
		{
			output += this.getPermission(perm) + "\n";
		}
		output += getLineBreak();
		return output;
	}
}
