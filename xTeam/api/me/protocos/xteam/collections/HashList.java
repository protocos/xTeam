package me.protocos.xteam.collections;

import java.util.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import me.protocos.xteam.util.CommonUtil;

public class HashList<K, V> implements Iterable<V>
{
	private HashMap<K, V> values;
	private List<K> order;
	private boolean keepSorted;

	public HashList()
	{
		values = CommonUtil.emptyHashMap();
		order = CommonUtil.emptyList();
		this.keepSorted = false;
	}

	public HashList(List<K> keys, List<V> values)
	{
		if (keys.size() == values.size())
		{
			for (int x = 0; x < keys.size(); x++)
			{
				order.add(keys.get(x));
				this.values.put(keys.get(x), values.get(x));
			}
		}
		this.keepSorted = false;
	}

	public HashList(HashMap<K, V> map)
	{
		values = new HashMap<K, V>(map);
		for (Map.Entry<K, V> entry : map.entrySet())
		{
			order.add(entry.getKey());
		}
		this.keepSorted = false;
	}

	public void clear()
	{
		values.clear();
		order.clear();
	}

	public boolean containsKey(K key)
	{
		return values.containsKey(key);
	}

	public List<K> getOrder()
	{
		return new ArrayList<K>(order);
	}

	public boolean isSorted()
	{
		return this.keepSorted;
	}

	public V put(K key, V value)
	{
		if (!order.contains(key))
			order.add(key);
		if (keepSorted)
			sort();
		return values.put(key, value);
	}

	public V put(K key, V value, int position)
	{
		if (!order.contains(key))
			order.add(position, key);
		if (keepSorted)
			sort();
		return values.put(key, value);
	}

	public void putAll(HashMap<K, V> map)
	{
		for (Map.Entry<K, V> entry : map.entrySet())
		{
			this.put(entry.getKey(), entry.getValue());
		}
	}

	public V get(int index)
	{
		return values.get(order.get(index));
	}

	public V get(K key)
	{
		return values.get(key);
	}

	public K getKey(int index)
	{
		return order.get(index);
	}

	public V remove(Object key)
	{
		order.remove(key);
		return values.remove(key);
	}

	public V remove(int index)
	{
		K key = order.remove(index);
		return values.remove(key);
	}

	public void setKeepSorted(boolean keepSorted)
	{
		this.keepSorted = keepSorted;
	}

	public boolean setOrder(List<K> newOrder)
	{
		for (K object : newOrder)
		{
			if (!order.contains(object))
				return false;
		}
		order = newOrder;
		return true;
	}

	public void sort()
	{
		Set<K> sorted = new TreeSet<K>(order);
		order = new ArrayList<K>(sorted);
	}

	public int size()
	{
		return values.size();
	}

	public List<V> asList()
	{
		List<V> list = CommonUtil.emptyList();
		for (V value : this)
		{
			list.add(value);
		}
		return list;
	}

	public boolean updateKey(K oldKey, K newKey)
	{
		if (oldKey == null || newKey == null)
			return false;
		if (!values.containsKey(oldKey))
			return false;
		int position = order.indexOf(oldKey);
		V value = remove(oldKey);
		put(newKey, value, position);
		return true;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (obj.getClass() != this.getClass())
			return false;

		@SuppressWarnings("unchecked")
		HashList<K, V> other = (HashList<K, V>) obj;
		return new EqualsBuilder().append(this.values, other.values).append(this.order, other.order).isEquals();

	}

	@Override
	public String toString()
	{
		String output = "{" + (values.size() > 0 ? order.get(0) + "=" + values.get(order.get(0)) : "");
		for (int x = 1; x < order.size(); x++)
			output += ", " + order.get(x) + "=" + values.get(order.get(x));
		output += "}";
		return output;
	}

	@Override
	public Iterator<V> iterator()
	{
		Iterator<V> it = new Iterator<V>()
		{
			private int index = 0;

			@Override
			public boolean hasNext()
			{
				return index < HashList.this.size() && HashList.this.get(index) != null;
			}

			@Override
			public V next()
			{
				return HashList.this.get(index++);
			}

			@Override
			public void remove()
			{
				HashList.this.remove(index);
			}
		};
		return it;
	}
}
