package me.protocos.xteam.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil
{
	public static final String WHITE_SPACE = "\\s+";
	public static final String OPTIONAL_WHITE_SPACE = "\\s*";
	public static final String NUMBERS = "\\d+";
	public static final String OPTIONAL_NUMBERS = "\\d*";
	public static final String ANY_CHARS = "\\S+";
	public static final String OPTIONAL_ANY_CHARS = "\\S*";
	public static final String ALPHA_NUMERIC = "\\w+";
	public static final String OPTIONAL_ALPHA_NUMERIC = "\\w*";

	public static String concatenate(Object[] o)
	{
		return concatenate(o, " ");
	}
	public static String concatenate(Object[] o, String glue)
	{
		String returnString = "";
		for (int x = 0; x < o.length; x++)
		{
			if (x != 0)
				returnString += glue;
			returnString += o[x].toString();
		}
		return returnString;
	}
	public static boolean matchesLowerCase(String str, String pattern)
	{
		return str.toLowerCase().matches(pattern);
	}
	public static boolean matchesUpperCase(String str, String pattern)
	{
		return str.toUpperCase().matches(pattern);
	}
	public static String patternOneOrMore(String str)
	{
		String pattern = "" + str.charAt(0);
		String closeParen = "";
		for (int x = 1; x < str.length(); x++)
		{
			if (x != 0)
			{
				pattern += "(";
				closeParen += ")";
			}
			pattern += str.charAt(x);
			if (x != 0)
			{
				pattern += "?";
			}
		}
		pattern += closeParen;
		return pattern;
	}
	public static String reverse(String s)
	{
		StringBuffer sb = new StringBuffer(s);
		return sb.reverse().toString();
	}
	public static List<String> toLowerCase(List<String> arraylist)
	{
		List<String> lowercase = new ArrayList<String>();
		for (String s : arraylist)
			lowercase.add(s.toLowerCase());
		return lowercase;
	}
	public static List<String> toUpperCase(List<String> arraylist)
	{
		List<String> uppercase = new ArrayList<String>();
		for (String s : arraylist)
			uppercase.add(s.toUpperCase());
		return uppercase;
	}
}
