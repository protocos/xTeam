package me.protocos.api.collection;

import java.util.*;
import org.apache.commons.lang.builder.EqualsBuilder;

public class OrderedHashMap<K, V> implements Map<K, V>, Iterable<V>
{
	private Map<K, V> values;
	private List<K> order;
	private boolean keepSorted;

	public static class Builder<K, V>
	{
		private OrderedHashMap<K, V> map;

		public Builder()
		{
			map = new OrderedHashMap<K, V>();
		}

		public Builder<K, V> addEntry(K key, V value)
		{
			map.put(key, value);
			return this;
		}

		public OrderedHashMap<K, V> build()
		{
			return map;
		}
	}

	public OrderedHashMap()
	{
		this.values = new HashMap<K, V>();
		this.order = new ArrayList<K>();
		this.keepSorted = false;
	}

	public OrderedHashMap(List<K> keys, List<V> values)
	{
		this.values = new HashMap<K, V>();
		this.order = new ArrayList<K>();
		this.addAll(keys, values);
		this.keepSorted = false;
	}

	public OrderedHashMap(Map<K, V> map)
	{
		this.values = new HashMap<K, V>();
		this.values.putAll(map);
		this.order = new ArrayList<K>();
		this.order.addAll(map.keySet());
		this.keepSorted = false;
	}

	public void clear()
	{
		this.values.clear();
		this.order.clear();
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
		order = new ArrayList<K>(new TreeSet<K>(order));
	}

	public int size()
	{
		return values.size();
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

	public void addAll(List<K> keys, @SuppressWarnings("hiding") List<V> values)
	{
		if (keys.size() != values.size())
			throw new IllegalArgumentException("Sizes of key and value list do not match");
		for (int x = 0; x < keys.size(); x++)
		{
			this.order.add(keys.get(x));
			this.values.put(keys.get(x), values.get(x));
		}
	}

	@Override
	public boolean isEmpty()
	{
		return this.values.isEmpty();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return this.values.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return this.values.containsValue(value);
	}

	@Override
	public V get(Object key)
	{
		return this.values.get(key);
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m)
	{
		this.values.putAll(m);
		this.order.addAll(m.keySet());
	}

	@Override
	public Set<K> keySet()
	{
		return this.values.keySet();
	}

	@Override
	public Collection<V> values()
	{
		return this.values.values();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet()
	{
		return this.values.entrySet();
	}

	public List<V> toList()
	{
		List<V> list = new ArrayList<V>();
		for (V value : this)
		{
			list.add(value);
		}
		return list;
	}

	public HashMap<K, V> toHashMap()
	{
		return new HashMap<K, V>(this);
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
		OrderedHashMap<K, V> other = (OrderedHashMap<K, V>) obj;
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
				return index < OrderedHashMap.this.size() && OrderedHashMap.this.get(index) != null;
			}

			@Override
			public V next()
			{
				return OrderedHashMap.this.get(index++);
			}

			@Override
			public void remove()
			{
				OrderedHashMap.this.remove(index);
			}
		};
		return it;
	}
}
