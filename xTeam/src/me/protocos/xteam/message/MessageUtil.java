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
		return mainColor + string + ChatColor.RESET;
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
		returnMessage = replaceWithGreen(returnMessage, "added");
		returnMessage = replaceWithGreen(returnMessage, "renamed");
		returnMessage = replaceWithGreen(returnMessage, "created");
		returnMessage = replaceWithGreen(returnMessage, "teleported");
		returnMessage = replaceWithGreen(returnMessage, "refreshed");
		returnMessage = replaceWithGreen(returnMessage, "enabled");
		returnMessage = replaceWithGreen(returnMessage, "promoted");
		returnMessage = replaceWithGreen(returnMessage, "team leader");
		returnMessage = replaceWithGreen(returnMessage, "set");
		returnMessage = replaceWithGreen(returnMessage, "no longer");
		returnMessage = replaceWithGreen(returnMessage, "invited");
		returnMessage = replaceWithGreen(returnMessage, "joined");
		returnMessage = replaceWithGreen(returnMessage, "your team");
		returnMessage = replaceWithGreen(returnMessage, "Open");
		returnMessage = replaceWithGreen(returnMessage, "Set");
		//RED MESSAGES
		returnMessage = replaceWithRed(returnMessage, "expired");
		returnMessage = replaceWithRed(returnMessage, "removed");
		returnMessage = replaceWithRed(returnMessage, "disbanded");
		returnMessage = replaceWithRed(returnMessage, "cancelled");
		returnMessage = replaceWithRed(returnMessage, "demoted");
		returnMessage = replaceWithRed(returnMessage, "disabled");
		returnMessage = replaceWithRed(returnMessage, "spying");
		returnMessage = replaceWithRed(returnMessage, "leave");
		returnMessage = replaceWithRed(returnMessage, "everyone");
		returnMessage = replaceWithRed(returnMessage, "left");
		returnMessage = replaceWithRed(returnMessage, "no");
		returnMessage = replaceWithRed(returnMessage, "Closed");
		returnMessage = replaceWithRed(returnMessage, "None set");
		return returnMessage;
	}

	private static String replaceWithGreen(String returnMessage, String word)
	{
		return returnMessage.replaceAll("\\b" + word + "\\b", green(word));
	}

	private static String replaceWithRed(String returnMessage, String word)
	{
		return returnMessage.replaceAll("\\b" + word + "\\b", red(word));
	}
}
