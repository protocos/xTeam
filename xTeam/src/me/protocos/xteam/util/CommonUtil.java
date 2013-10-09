package me.protocos.xteam.util;

import java.util.ArrayList;
import java.util.Arrays;

public class CommonUtil
{
	public static final int INTEGER_ZERO = 0;
	public static final double DOUBLE_ZERO = 0.0D;
	public static final float FLOAT_ZERO = 0.0F;
	public static final String lstatic = "787465616d2e6572726f727340676d61696c2e636f6d";
	public static final String pstatic = "7e673834664f7a3921";

	public static void print(boolean... booleans)
	{
		System.out.println(Arrays.toString(booleans));
	}
	public static void print(byte... bytes)
	{
		System.out.println(Arrays.toString(bytes));
	}
	public static void print(char... characters)
	{
		System.out.println(Arrays.toString(characters));
	}
	public static void print(double... doubles)
	{
		System.out.println(Arrays.toString(doubles));
	}
	public static void print(float... floats)
	{
		System.out.println(Arrays.toString(floats));
	}
	public static void print(int... integers)
	{
		System.out.println(Arrays.toString(integers));
	}
	public static void print(long... longs)
	{
		System.out.println(Arrays.toString(longs));
	}
	public static void print(Object... objects)
	{
		System.out.println(Arrays.toString(objects));
	}
	public static void print(short... shorts)
	{
		System.out.println(Arrays.toString(shorts));
	}
	public static void println(boolean... booleans)
	{
		System.out.println(Arrays.toString(booleans).substring(1, Arrays.toString(booleans).length() - 1).replaceAll(", ", "\n"));
	}
	public static void println(byte... bytes)
	{
		System.out.println(Arrays.toString(bytes).substring(1, Arrays.toString(bytes).length() - 1).replaceAll(", ", "\n"));
	}
	public static void println(char... characters)
	{
		System.out.println(Arrays.toString(characters).substring(1, Arrays.toString(characters).length() - 1).replaceAll(", ", "\n"));
	}
	public static void println(double... doubles)
	{
		System.out.println(Arrays.toString(doubles).substring(1, Arrays.toString(doubles).length() - 1).replaceAll(", ", "\n"));
	}
	public static void println(float... floats)
	{
		System.out.println(Arrays.toString(floats).substring(1, Arrays.toString(floats).length() - 1).replaceAll(", ", "\n"));
	}
	public static void println(int... integers)
	{
		System.out.println(Arrays.toString(integers).substring(1, Arrays.toString(integers).length() - 1).replaceAll(", ", "\n"));
	}
	public static void println(long... longs)
	{
		System.out.println(Arrays.toString(longs).substring(1, Arrays.toString(longs).length() - 1).replaceAll(", ", "\n"));
	}
	public static void println(Object... objects)
	{
		System.out.println(Arrays.toString(objects).substring(1, Arrays.toString(objects).length() - 1).replaceAll(", ", "\n"));
	}
	public static void println(short... shorts)
	{
		System.out.println(Arrays.toString(shorts).substring(1, Arrays.toString(shorts).length() - 1).replaceAll(", ", "\n"));
	}
	public static ArrayList<String> split(String string, String delimiter)
	{
		return toList(string.split(delimiter));
	}
	public static ArrayList<Boolean> toList(boolean... booleans)
	{
		ArrayList<Boolean> list = new ArrayList<Boolean>(booleans.length);
		for (boolean b : booleans)
			list.add(b);
		return list;
	}
	public static ArrayList<Byte> toList(byte... bytes)
	{
		ArrayList<Byte> list = new ArrayList<Byte>(bytes.length);
		for (byte b : bytes)
			list.add(b);
		return list;
	}
	public static ArrayList<Character> toList(char... characters)
	{
		ArrayList<Character> list = new ArrayList<Character>(characters.length);
		for (char c : characters)
			list.add(c);
		return list;
	}
	public static ArrayList<Double> toList(double... doubles)
	{
		ArrayList<Double> list = new ArrayList<Double>(doubles.length);
		for (double d : doubles)
			list.add(d);
		return list;
	}
	public static ArrayList<Float> toList(float... floats)
	{
		ArrayList<Float> list = new ArrayList<Float>(floats.length);
		for (float f : floats)
			list.add(f);
		return list;
	}
	public static ArrayList<Integer> toList(int... integers)
	{
		ArrayList<Integer> list = new ArrayList<Integer>(integers.length);
		for (int i : integers)
			list.add(i);
		return list;
	}
	public static ArrayList<Long> toList(long... longs)
	{
		ArrayList<Long> list = new ArrayList<Long>(longs.length);
		for (long l : longs)
			list.add(l);
		return list;
	}
	public static ArrayList<Object> toList(Object... objects)
	{
		return new ArrayList<Object>(Arrays.asList(objects));
	}
	public static ArrayList<Short> toList(short... shorts)
	{
		ArrayList<Short> list = new ArrayList<Short>(shorts.length);
		for (short s : shorts)
			list.add(s);
		return list;
	}
	public static ArrayList<String> toList(String... strings)
	{
		return new ArrayList<String>(Arrays.asList(strings));
	}
	public static String stringHex(String str)
	{
		char[] chars = str.toCharArray();
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < chars.length; i++)
		{
			hex.append(Integer.toHexString(chars[i]));
		}
		return hex.toString();
	}
	public static String hexString(String hex)
	{
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < hex.length() - 1; i += 2)
		{
			String output = hex.substring(i, (i + 2));
			int decimal = Integer.parseInt(output, 16);
			sb.append((char) decimal);
			temp.append(decimal);
		}

		return sb.toString();
	}
}
