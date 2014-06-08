package me.protocos.xteam.message;

import org.bukkit.ChatColor;

public class MessageUtil
{
	public static ChatColor getColor(String color)
	{
		try
		{
			return ChatColor.valueOf(color.toUpperCase());
		}
		catch (IllegalArgumentException e)
		{
			return ChatColor.RESET;
		}
	}

	public static String highlightString(ChatColor mainColor, String string)
	{
		final ChatColor highlightColor = ChatColor.DARK_RED;
		string = string.replaceAll("\\{", highlightColor + "{" + mainColor);
		string = string.replaceAll("\\}", highlightColor + "}" + mainColor);
		string = string.replaceAll("\\[", highlightColor + "[" + mainColor);
		string = string.replaceAll("\\]", highlightColor + "]" + mainColor);
		string = string.replaceAll("/", highlightColor + "/" + mainColor);
		return mainColor + string;
	}

	public static String formatForUser(String string)
	{
		return highlightString(ChatColor.GRAY, string);
	}

	public static String formatForAdmin(String string)
	{
		return highlightString(ChatColor.YELLOW, string);
	}

	public static String formatForLeader(String string)
	{
		return highlightString(ChatColor.LIGHT_PURPLE, string);
	}

	public static String formatForServerAdmin(String string)
	{
		return highlightString(ChatColor.DARK_AQUA, string);
	}

	public static String green(String string)
	{
		return ChatColor.GREEN + string + ChatColor.RESET;
	}

	public static String red(String string)
	{
		return ChatColor.RED + string + ChatColor.RESET;
	}

	public static String resetFormatting(String string)
	{
		return string.replaceAll("§.", "");
	}

	public static String formatMessage(String message)
	{
		String returnMessage = resetFormatting(message);
		//GREEN MESSAGES
		returnMessage = returnMessage.replaceAll("added", green("added"));
		returnMessage = returnMessage.replaceAll("renamed", green("renamed"));
		returnMessage = returnMessage.replaceAll("created", green("created"));
		returnMessage = returnMessage.replaceAll("teleported", green("teleported"));
		returnMessage = returnMessage.replaceAll("refreshed", green("refreshed"));
		returnMessage = returnMessage.replaceAll("enabled", green("enabled"));
		returnMessage = returnMessage.replaceAll("promoted", green("promoted"));
		returnMessage = returnMessage.replaceAll("team leader", green("team leader"));
		returnMessage = returnMessage.replaceAll("set", green("set"));
		returnMessage = returnMessage.replaceAll("no longer", green("no longer"));
		returnMessage = returnMessage.replaceAll("invited", green("invited"));
		returnMessage = returnMessage.replaceAll("joined", green("joined"));
		returnMessage = returnMessage.replaceAll("your team", green("your team"));
		returnMessage = returnMessage.replaceAll("Open", green("Open"));
		returnMessage = returnMessage.replaceAll("Set", green("Set"));
		//RED MESSAGES
		returnMessage = returnMessage.replaceAll("expired", red("expired"));
		returnMessage = returnMessage.replaceAll("removed", red("removed"));
		returnMessage = returnMessage.replaceAll("disbanded", red("disbanded"));
		returnMessage = returnMessage.replaceAll("cancelled", red("cancelled"));
		returnMessage = returnMessage.replaceAll("demoted", red("demoted"));
		returnMessage = returnMessage.replaceAll("disabled", red("disabled"));
		returnMessage = returnMessage.replaceAll("spying", red("spying"));
		returnMessage = returnMessage.replaceAll("leave", red("leave"));
		returnMessage = returnMessage.replaceAll("everyone", red("everyone"));
		returnMessage = returnMessage.replaceAll("left", red("left"));
		returnMessage = returnMessage.replaceAll("no", red("no"));
		returnMessage = returnMessage.replaceAll("Closed", red("Closed"));
		returnMessage = returnMessage.replaceAll("None set", red("None set"));
		return returnMessage;
	}
}
