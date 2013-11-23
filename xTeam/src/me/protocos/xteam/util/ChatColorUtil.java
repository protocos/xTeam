package me.protocos.xteam.util;

import org.bukkit.ChatColor;

public class ChatColorUtil
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

	public static String positiveMessage(String string)
	{
		return ChatColor.GREEN + string + ChatColor.RESET;
	}

	public static String negativeMessage(String string)
	{
		return ChatColor.RED + string + ChatColor.RESET;
	}

	public static String resetFormatting(String string)
	{
		return string.replaceAll("¤.", "");
	}
}
