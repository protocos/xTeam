package me.protocos.api.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import me.protocos.api.collection.OrderedHashMap;

public class ReflectionUtil
{
	public static <T> OrderedHashMap<String, Object> getPropertiesOf(T object)
	{
		OrderedHashMap<String, Object> fields = new OrderedHashMap<String, Object>();
		Class<?> clazz = object.getClass();
		for (Field field : clazz.getDeclaredFields())
		{
			field.setAccessible(true);
			try
			{
				fields.put(field.getName(), field.get(object));
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		return fields;
	}

	public static String decompose(Object object)
	{
		return decompose(object, 4);
	}

	public static String decompose(Object object, int recursionDepth)
	{
		return decompose(object, 0, recursionDepth);
	}

	private static String decompose(Object object, int level, int maxLevel)
	{
		if (object == null)
			return "null";
		level++;
		if (level == maxLevel)
			return "(...)";
		if (object instanceof Map)
		{
			String output = object.getClass().getSimpleName() + "{";
			@SuppressWarnings("unchecked")
			Map<Object, Object> objects = (Map<Object, Object>) object;
			boolean first = true;
			for (Object obj : objects.keySet())
			{
				if (!first)
					output += ", ";
				output += decompose(obj, level, maxLevel) + "=" + decompose(objects.get(obj), level, maxLevel);
				first = false;
			}
			return output + "}";
		}
		else if (object instanceof Collection)
		{
			String output = object.getClass().getSimpleName() + "{";
			@SuppressWarnings("unchecked")
			Collection<Object> objects = (Collection<Object>) object;
			boolean first = true;
			for (Object obj : objects)
			{
				if (!first)
					output += ", ";
				output += decompose(obj, level, maxLevel);
				first = false;
			}
			return output + "}";
		}
		else if (isKnownClass(object.getClass()))
		{
			return object.getClass().getSimpleName() + "{value=" + object.toString() + "}";
		}
		else
		{
			String output = object.getClass().getSimpleName() + "{";
			Field[] fields = object.getClass().getDeclaredFields();
			boolean first = true;
			for (Field field : fields)
			{
				if (!first)
					output += ", ";
				field.setAccessible(true);
				try
				{
					Object value = field.get(object);
					output += field.getName() + "=";
					if (value == null)
					{
						output += "null";
					}
					else
					{
						output += decompose(value, level, maxLevel);
					}
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}
				first = false;
			}
			return output + "}";
		}
	}

	private static boolean isKnownClass(Class<?> clazz)
	{
		return clazz.equals(byte.class) ||
				clazz.equals(boolean.class) ||
				clazz.equals(short.class) ||
				clazz.equals(int.class) ||
				clazz.equals(char.class) ||
				clazz.equals(long.class) ||
				clazz.equals(double.class) ||
				clazz.equals(float.class) ||
				clazz.equals(Byte.class) ||
				clazz.equals(Boolean.class) ||
				clazz.equals(Short.class) ||
				clazz.equals(Integer.class) ||
				clazz.equals(Character.class) ||
				clazz.equals(Long.class) ||
				clazz.equals(Double.class) ||
				clazz.equals(Float.class) ||
				clazz.equals(String.class);
	}
}
