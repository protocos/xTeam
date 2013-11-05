package me.protocos.xteam.util;

import org.bukkit.ChatColor;

public class ChatColorUtil
{
	private static final ChatColor highlightColor = ChatColor.DARK_RED;

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
		string = string.replaceAll("\\{", highlightColor + "{" + mainColor);
		string = string.replaceAll("\\}", highlightColor + "}" + mainColor);
		string = string.replaceAll("\\[", highlightColor + "[" + mainColor);
		string = string.replaceAll("\\]", highlightColor + "]" + mainColor);
		string = string.replaceAll("/", highlightColor + "/" + mainColor);
		return string;
	}
}
