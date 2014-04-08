package me.protocos.xteam.collections;

import java.util.HashMap;

public class MapBuilder<K, V>
{
	private HashList<K, V> map;

	public MapBuilder()
	{
		map = new HashList<K, V>();
	}

	public MapBuilder<K, V> addEntry(K key, V value)
	{
		map.put(key, value);
		return this;
	}

	public final HashMap<K, V> toHashMap()
	{
		return map.toHashMap();
	}

	public final HashList<K, V> build()
	{
		return map;
	}
}
