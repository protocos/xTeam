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
}
