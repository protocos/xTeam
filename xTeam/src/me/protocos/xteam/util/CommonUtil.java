package me.protocos.xteam.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonUtil
{
	public static final int INTEGER_ZERO = 0;
	public static final double DOUBLE_ZERO = 0.0D;
	public static final float FLOAT_ZERO = 0.0F;

	public static List<Boolean> toList(boolean... booleans)
	{
		List<Boolean> list = new ArrayList<Boolean>(booleans.length);
		for (boolean b : booleans)
			list.add(b);
		return list;
	}
	public static List<Byte> toList(byte... bytes)
	{
		List<Byte> list = new ArrayList<Byte>(bytes.length);
		for (byte b : bytes)
			list.add(b);
		return list;
	}
	public static List<Short> toList(short... shorts)
	{
		List<Short> list = new ArrayList<Short>(shorts.length);
		for (short s : shorts)
			list.add(s);
		return list;
	}
	public static List<Integer> toList(int... integers)
	{
		List<Integer> list = new ArrayList<Integer>(integers.length);
		for (int i : integers)
			list.add(i);
		return list;
	}
	public static List<Long> toList(long... longs)
	{
		List<Long> list = new ArrayList<Long>(longs.length);
		for (long l : longs)
			list.add(l);
		return list;
	}
	public static List<Character> toList(char... characters)
	{
		List<Character> list = new ArrayList<Character>(characters.length);
		for (char c : characters)
			list.add(c);
		return list;
	}
	public static List<Float> toList(float... floats)
	{
		List<Float> list = new ArrayList<Float>(floats.length);
		for (float f : floats)
			list.add(f);
		return list;
	}
	public static List<Double> toList(double... doubles)
	{
		List<Double> list = new ArrayList<Double>(doubles.length);
		for (double d : doubles)
			list.add(d);
		return list;
	}
	public static List<String> toList(String... strings)
	{
		return Arrays.asList(strings);
	}
	public static List<String> split(String string, String delimiter)
	{
		return toList(string.split(delimiter));
	}
	public static List<Object> toList(Object... objects)
	{
		return Arrays.asList(objects);
	}
	public static void print(boolean... booleans)
	{
		System.out.println(Arrays.toString(booleans));
	}
	public static void print(byte... bytes)
	{
		System.out.println(Arrays.toString(bytes));
	}
	public static void print(short... shorts)
	{
		System.out.println(Arrays.toString(shorts));
	}
	public static void print(int... integers)
	{
		System.out.println(Arrays.toString(integers));
	}
	public static void print(long... longs)
	{
		System.out.println(Arrays.toString(longs));
	}
	public static void print(char... characters)
	{
		System.out.println(Arrays.toString(characters));
	}
	public static void print(float... floats)
	{
		System.out.println(Arrays.toString(floats));
	}
	public static void print(double... doubles)
	{
		System.out.println(Arrays.toString(doubles));
	}
	public static void print(Object... objects)
	{
		System.out.println(Arrays.toString(objects));
	}
}
