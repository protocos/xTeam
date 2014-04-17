package me.protocos.xteam.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import me.protocos.xteam.collections.HashList;

public class CommonUtil
{
	public static final int INTEGER_ZERO = 0;
	public static final long LONG_ZERO = 0;
	public static final double DOUBLE_ZERO = 0.0D;
	public static final float FLOAT_ZERO = 0.0F;

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

	public static int round(double d)
	{
		return (int) Math.round(d);
	}

	public static List<String> split(String string, String delimiter)
	{
		return toList(string.split(delimiter));
	}

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

	public static List<Character> toList(char... characters)
	{
		List<Character> list = new ArrayList<Character>(characters.length);
		for (char c : characters)
			list.add(c);
		return list;
	}

	public static List<Double> toList(double... doubles)
	{
		List<Double> list = new ArrayList<Double>(doubles.length);
		for (double d : doubles)
			list.add(d);
		return list;
	}

	public static List<Float> toList(float... floats)
	{
		List<Float> list = new ArrayList<Float>(floats.length);
		for (float f : floats)
			list.add(f);
		return list;
	}

	public static List<Integer> toList(int... integers)
	{
		List<Integer> list = new ArrayList<Integer>(integers.length);
		for (int i : integers)
			list.add(i);
		return list;
	}

	public static List<Short> toList(short... shorts)
	{
		List<Short> list = new ArrayList<Short>(shorts.length);
		for (short s : shorts)
			list.add(s);
		return list;
	}

	public static List<Long> toList(long... longs)
	{
		List<Long> list = new ArrayList<Long>(longs.length);
		for (long l : longs)
			list.add(l);
		return list;
	}

	public static List<String> toList(String... strings)
	{
		if (strings.length == 1 && strings[0].equals(""))
			return new ArrayList<String>();
		return new ArrayList<String>(Arrays.asList(strings));
	}

	public static List<Object> toList(Object... objects)
	{
		return new ArrayList<Object>(Arrays.asList(objects));
	}

	public static boolean containsIgnoreCase(List<String> list, String string)
	{
		for (String s : list)
		{
			if (s.equalsIgnoreCase(string))
				return true;
		}
		return false;
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

	public static long getElapsedTimeSince(long time)
	{
		return (System.currentTimeMillis() - time) / 1000;
	}

	public static <T> List<T> emptyList()
	{
		return new ArrayList<T>();
	}

	public static <T> List<T> emptyList(int size)
	{
		return new ArrayList<T>(size);
	}

	public static <T> Set<T> emptySet()
	{
		return new HashSet<T>();
	}

	public static <K, V> HashMap<K, V> emptyHashMap()
	{
		return new HashMap<K, V>();
	}

	public static <K, V> HashList<K, V> emptyHashList()
	{
		return new HashList<K, V>();
	}

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
		DateFormat formatter = new SimpleDateFormat("MMM d @ h:mm a");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}

	@SuppressWarnings("unchecked")
	public static <SubType, SuperType> List<SubType> subListOfType(List<SuperType> list, Class<SubType> subType)
	{
		List<SubType> returnList = emptyList();
		for (SuperType superType : list)
		{
			if (subType.isAssignableFrom(superType.getClass()))
				returnList.add((SubType) superType);
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public static <SubType, SuperType> SubType assignFromType(SuperType instance, Class<SubType> subType) throws IncompatibleClassChangeError
	{
		if (!subType.isAssignableFrom(instance.getClass()))
		{
			throw new IncompatibleClassChangeError(instance.getClass().getSimpleName() + " '" + instance + "' cannot be assigned to an instance of " + subType.getSimpleName());
		}
		return (SubType) instance;
	}
}
