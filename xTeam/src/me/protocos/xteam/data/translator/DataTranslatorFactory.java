package me.protocos.xteam.data.translator;

import java.util.List;
import java.util.Set;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.model.Headquarters;
import org.bukkit.Location;

public class DataTranslatorFactory
{
	@SuppressWarnings("unchecked")
	public static <T> IDataTranslator<T> getTranslator(T instance)
	{
		return (IDataTranslator<T>) getTranslator(instance.getClass());
	}

	@SuppressWarnings("unchecked")
	public static <T> IDataTranslator<T> getTranslator(Class<T> clazz)
	{
		if (clazz.equals(Boolean.class))
			return (IDataTranslator<T>) new BooleanDataTranslator();
		else if (clazz.equals(Byte.class))
			return (IDataTranslator<T>) new ByteDataTranslator();
		else if (clazz.equals(Character.class))
			return (IDataTranslator<T>) new CharacterDataTranslator();
		else if (clazz.equals(Double.class))
			return (IDataTranslator<T>) new DoubleDataTranslator();
		else if (clazz.equals(Float.class))
			return (IDataTranslator<T>) new FloatDataTranslator();
		else if (clazz.equals(Integer.class))
			return (IDataTranslator<T>) new IntegerDataTranslator();
		else if (clazz.equals(Long.class))
			return (IDataTranslator<T>) new LongDataTranslator();
		else if (clazz.equals(Short.class))
			return (IDataTranslator<T>) new ShortDataTranslator();
		else if (clazz.equals(String.class))
			return (IDataTranslator<T>) new StringDataTranslator();
		return (IDataTranslator<T>) new NullDataTranslator();
	}

	@SuppressWarnings("unchecked")
	public static <T> IDataTranslator<T> getTranslator(T instance, TeamPlugin teamPlugin)
	{
		return (IDataTranslator<T>) getTranslator(instance.getClass(), teamPlugin);
	}

	@SuppressWarnings("unchecked")
	public static <T> IDataTranslator<T> getTranslator(Class<T> clazz, TeamPlugin teamPlugin)
	{
		if (clazz.equals(Headquarters.class))
			return (IDataTranslator<T>) new HeadquartersDataTranslator(teamPlugin);
		else if (clazz.equals(Location.class))
			return (IDataTranslator<T>) new LocationDataTranslator(teamPlugin);
		return (IDataTranslator<T>) new NullDataTranslator();
	}

	public static IDataTranslator<Set<String>> getSetTranslator()
	{
		return new SetDataTranslator();
	}

	public static IDataTranslator<List<String>> getListTranslator()
	{
		return new ListDataTranslator();
	}
}
