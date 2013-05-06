package me.protocos.xteam.util;

import java.util.*;

public class HashList<K, V> extends HashMap<K, V>
{
	private static final long serialVersionUID = 4968800407845475984L;
	private List<K> order = new ArrayList<K>();
	private boolean keepSorted;

	public HashList()
	{
		this.keepSorted = false;
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
		this.keepSorted = false;
	}
	public HashList(HashMap<K, V> map)
	{
		super(map);
		for (Map.Entry<K, V> entry : map.entrySet())
		{
			order.add(entry.getKey());
		}
		this.keepSorted = false;
	}
	public K getKey(int index)
	{
		return order.get(index);
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
		if (keepSorted)
			sort();
		return super.put(key, value);
	}
	public V put(K key, V value, int position)
	{
		if (!order.contains(key))
			order.add(position, key);
		if (keepSorted)
			sort();
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
	public void sort()
	{
		Set<K> sorted = new TreeSet<K>(order);
		order = new ArrayList<K>(sorted);
	}
	public void setKeepSorted(boolean keepSorted)
	{
		this.keepSorted = keepSorted;
	}
	public boolean isSorted()
	{
		return this.keepSorted;
	}
	//	public boolean containsKeyIgnoreCase(K key)
	//	{
	//		for (K k : this.keySet())
	//		{
	//			if (k instanceof String && key instanceof String && ((String) k).equalsIgnoreCase((String) key))
	//				return true;
	//			else if (k.equals(key))
	//				return true;
	//		}
	//		return false;
	//	}
	public boolean updateKey(K oldKey, K newKey)
	{
		if (!containsKey(oldKey))
			return false;
		int position = order.indexOf(oldKey);
		V value = remove(oldKey);
		put(newKey, value, position);
		return true;
	}
}
