package me.protocos.xteam.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StringUtil
{
	public static String concatenate(Object[] objects)
	{
		return concatenate(objects, " ");
	}

	public static String concatenate(Object[] objects, String glue)
	{
		StringBuilder returnString = new StringBuilder();
		for (Object obj : objects)
		{
			if (returnString.length() == 0)
				returnString.append(obj.toString());
			else
				returnString.append(glue).append(obj.toString());
		}
		return returnString.toString();
	}

	public static String concatenate(@SuppressWarnings("rawtypes") List objects)
	{
		return concatenate(objects, " ");
	}

	public static String concatenate(@SuppressWarnings("rawtypes") List objects, String glue)
	{
		StringBuilder returnString = new StringBuilder();
		for (Object obj : objects)
		{
			returnString.append(obj.toString()).append(glue);
		}
		return returnString.toString().trim();
	}

	public static boolean matchesLowerCase(String str, String pattern)
	{
		return str.toLowerCase().matches(pattern);
	}

	public static boolean matchesUpperCase(String str, String pattern)
	{
		return str.toUpperCase().matches(pattern);
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

	public static String formatDateToMonthDay(long milliSeconds)
	{
		DateFormat formatter = new SimpleDateFormat("MMM d");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		String month_day = formatter.format(calendar.getTime());
		formatter = new SimpleDateFormat("h:mm a");
		String hour_minute_am_pm = formatter.format(calendar.getTime());
		return month_day + " @ " + hour_minute_am_pm;
	}
}
