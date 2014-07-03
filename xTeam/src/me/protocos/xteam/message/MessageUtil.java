package me.protocos.xteam.message;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.ChatColor;

public class MessageUtil
{
	private static Set<String> positiveWords = new HashSet<String>();
	private static Set<String> negativeWords = new HashSet<String>();
	static
	{
		//Positive Words
		positiveWords.add("added");
		positiveWords.add("renamed");
		positiveWords.add("created");
		positiveWords.add("teleported");
		positiveWords.add("refreshed");
		positiveWords.add("enabled");
		positiveWords.add("promoted");
		positiveWords.add("team leader");
		positiveWords.add("set");
		positiveWords.add("no longer");
		positiveWords.add("invited");
		positiveWords.add("joined");
		positiveWords.add("your team");
		positiveWords.add("Open");
		positiveWords.add("Set");
		//Negative Words
		negativeWords.add("expired");
		negativeWords.add("removed");
		negativeWords.add("disbanded");
		negativeWords.add("cancelled");
		negativeWords.add("demoted");
		negativeWords.add("disabled");
		negativeWords.add("spying");
		negativeWords.add("leave");
		negativeWords.add("everyone");
		negativeWords.add("left");
		negativeWords.add("no");
		negativeWords.add("Closed");
		negativeWords.add("None set");
	}

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
		return ChatColor.stripColor(string);
	}

	public static void addPositiveWord(String word)
	{
		positiveWords.add(word);
	}

	public static void addNegativeWord(String word)
	{
		negativeWords.add(word);
	}

	public static String formatMessage(String message)
	{
		String returnMessage = resetFormatting(message);
		//GREEN MESSAGES
		for (String word : positiveWords)
			returnMessage = replaceWithGreen(returnMessage, word);
		//RED MESSAGES
		for (String word : negativeWords)
			returnMessage = replaceWithRed(returnMessage, word);
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
