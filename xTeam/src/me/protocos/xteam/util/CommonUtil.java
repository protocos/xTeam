package me.protocos.xteam.util;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil
{
	public static final int INTEGER_ZERO = 0;
	public static final double DOUBLE_ZERO = 0.0D;
	public static final float FLOAT_ZERO = 0.0F;

	public static boolean isMac()
	{
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("mac") >= 0);
	}
	public static boolean isWindows()
	{
		String os = System.getProperty("os.name").toLowerCase();
		return (os.indexOf("win") >= 0);
	}
	public static List<Double> toList(double[] doubles)
	{
		List<Double> list = new ArrayList<Double>();
		for (double d : doubles)
			list.add(d);
		return list;
	}
	public static List<Integer> toList(int[] integers)
	{
		List<Integer> list = new ArrayList<Integer>();
		for (int i : integers)
			list.add(i);
		return list;
	}
	public static List<String> toList(String[] strings)
	{
		List<String> list = new ArrayList<String>();
		for (String s : strings)
			if (!s.equals(""))
				list.add(s);
		return list;
	}
	public static List<String> toList(String string, String delimiter)
	{
		String[] split = string.split(delimiter);
		return toList(split);
	}
	public static double toDouble(String s)
	{
		return Double.parseDouble(s);
	}
	public static int toInt(String s)
	{
		return Integer.parseInt(s);
	}
}
