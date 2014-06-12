package me.protocos.xteam.legacy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import me.protocos.xteam.data.PropertyList;
import me.protocos.xteam.util.CommonUtil;

public class LegacyDataTranslator
{
	public static PropertyList fromLegacyData(String legacy)
	{
		PropertyList properties = new PropertyList();
		List<String> split = CommonUtil.split(legacy, " ");
		//Essentially if the first property in the line is a single word and not a mapping...
		if (!split.get(0).contains(":"))
		{
			String name = split.get(0);
			String tag = split.get(0);
			String timeHeadquartersLastSet = split.get(3);
			String headquarters = timeHeadquartersLastSet.equals("0") ? "" : CommonUtil.concatenate(split.get(2), split.get(5), split.get(6), split.get(7), split.get(8), split.get(9)).replaceAll(" ", ",");
			String leader = "";
			Set<String> admins = new HashSet<String>();
			Set<String> players = new HashSet<String>();
			for (int i = 10; i < split.size(); i++)
			{
				String string = split.get(i);
				if (string.endsWith("~~"))
					leader = string.replaceAll("~", "");
				else if (string.endsWith("~"))
				{
					admins.add(string.replaceAll("~", ""));
				}
				players.add(string.replaceAll("~", ""));
			}
			String openJoining = split.get(1).equals("none") ? "true" : "false";
			String defaultTeam = leader.equals("") ? "true" : "false";
			fillOutProperties(properties, name, tag, openJoining, defaultTeam, timeHeadquartersLastSet, headquarters, leader, CommonUtil.concatenate(admins, ","), CommonUtil.concatenate(players, ","));
		}
		else
		{
			properties = PropertyList.fromString(legacy);
			if ("".equals(properties.getAsString("name")))
				return null;
			if (!properties.containsKey("tag"))
				properties.put("tag", properties.getAsString("name"));
			if (properties.containsKey("hq"))
				properties.updateKey("hq", "headquarters");
			if (properties.containsKey("HQ"))
				properties.updateKey("HQ", "headquarters");
			if (properties.containsKey("world"))
			{
				String headquarters = properties.getAsString("world") + "," + properties.getAsString("headquarters");
				properties.remove("headquarters");
				properties.put("headquarters", headquarters);
				properties.remove("world");
			}
			if (properties.containsKey("timeLastSet"))
				properties.updateKey("timeLastSet", "timeHeadquartersLastSet");
			if (properties.containsKey("timeHeadquartersSet"))
				properties.updateKey("timeHeadquartersSet", "timeHeadquartersLastSet");
			if (properties.getAsString("timeHeadquartersLastSet").equals("0"))
			{
				properties.put("headquarters", "");
			}
			if (properties.containsKey("open"))
				properties.updateKey("open", "openJoining");
			if (properties.containsKey("default"))
				properties.updateKey("default", "defaultTeam");
			if (!properties.containsKey("defaultTeam"))
				properties.put("defaultTeam", "false");
			if ("".equals(properties.getAsString("leader")) || "default".equals(properties.getAsString("leader")))
			{
				properties.put("defaultTeam", "true");
				properties.put("openJoining", "true");
				properties.put("leader", "");
			}
			List<String> admins = CommonUtil.split(properties.getAsString("admins"), ",");
			admins.remove(properties.getAsString("leader"));
			properties.put("admins", CommonUtil.concatenate(admins, ","));

		}
		return properties;
	}

	private static void fillOutProperties(PropertyList properties, String name, String tag, String openJoining, String defaultTeam, String timeHeadquartersLastSet, String headquarters, String leader, String admins, String players)
	{
		properties.put("name", name);
		properties.put("tag", tag);
		properties.put("openJoining", openJoining);
		properties.put("defaultTeam", defaultTeam);
		properties.put("timeHeadquartersLastSet", timeHeadquartersLastSet);
		properties.put("headquarters", headquarters);
		properties.put("leader", leader);
		properties.put("admins", admins);
		properties.put("players", players);
	}
}
