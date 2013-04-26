package me.protocos.xteam.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HashList<K, V> extends HashMap<K, V>
{
	private static final long serialVersionUID = 4968800407845475984L;
	private ArrayList<K> order = new ArrayList<K>();

	public HashList()
	{
	}
	public HashList(ArrayList<K> keys, ArrayList<V> values)
	{
		if (keys.size() == values.size())
		{
			for (int x = 0; x < keys.size(); x++)
			{
				order.add(keys.get(x));
				super.put(keys.get(x), values.get(x));
			}
		}
	}
	public HashList(HashMap<K, V> map)
	{
		super(map);
		for (Map.Entry<K, V> entry : map.entrySet())
		{
			order.add(entry.getKey());
		}
	}
	public V get(int index)
	{
		return get(order.get(index));
	}
	public ArrayList<K> getOrder()
	{
		return new ArrayList<K>(order);
	}
	public V put(K key, V value)
	{
		if (!order.contains(key))
			order.add(key);
		return super.put(key, value);
	}
	public V put(K key, V value, int position)
	{
		if (!order.contains(key))
			order.add(position, key);
		return super.put(key, value);
	}
	public V remove(Object key)
	{
		order.remove(key);
		return super.remove(key);
	}
	public boolean setOrder(ArrayList<K> newOrder)
	{
		for (K object : newOrder)
		{
			if (!order.contains(object))
				return false;
		}
		order = newOrder;
		return true;
	}
	public void clear()
	{
		order.clear();
		super.clear();
	}
	public String toString()
	{
		String output = "{" + (size() > 0 ? order.get(0) + "=" + get(order.get(0)) : "");
		for (int x = 1; x < order.size(); x++)
			output += ", " + order.get(x) + "=" + get(order.get(x));
		output += "}";
		return output;
	}
}
