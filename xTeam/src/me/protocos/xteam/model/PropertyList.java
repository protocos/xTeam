package me.protocos.xteam.model;

import java.util.Iterator;
import java.util.List;
import me.protocos.api.collection.OrderedHashMap;
import me.protocos.api.util.CommonUtil;
import me.protocos.xteam.data.translator.IDataTranslator;
import org.apache.commons.lang.builder.EqualsBuilder;

public class PropertyList implements Iterable<Property>
{
	private OrderedHashMap<String, Property> properties;

	public PropertyList()
	{
		properties = new OrderedHashMap<String, Property>();
	}

	public void put(String key, String value)
	{
		properties.put(key, Property.fromString(key, value));
	}

	public <T> void put(String key, T value, IDataTranslator<T> formatter)
	{
		properties.put(key, Property.fromObject(key, value, formatter));
	}

	public void put(Property property)
	{
		properties.put(property.getKey(), property);
	}

	public Property get(String propertyName)
	{
		return properties.get(propertyName);
	}

	public String getAsString(String propertyName)
	{
		return properties.get(propertyName).getValue();
	}

	public <T> T getAsType(String propertyName, IDataTranslator<T> strategy)
	{
		return properties.get(propertyName).getValueUsing(strategy);
	}

	public Property get(int index)
	{
		return properties.get(index);
	}

	public Property remove(String propertyName)
	{
		int index = 0;
		for (Property property : properties)
		{
			if (property.getKey().equalsIgnoreCase(propertyName))
				break;
			index++;
		}
		return properties.remove(index);
	}

	protected Property remove(int index)
	{
		return properties.remove(index);
	}

	public static PropertyList fromString(String line)
	{
		PropertyList list = new PropertyList();
		List<String> properties = CommonUtil.split(line, " ");
		//check all property strings for containing a colon
		//if no colon present, then it must be part of the previous property
		for (int x = 1; x < properties.size(); x++)
		{
			if (!properties.get(x).contains(":"))
			{
				properties.set(x - 1, properties.get(x - 1) + " " + properties.remove(x--));
			}
		}
		for (String property : properties)
		{
			list.put(Property.fromString(property));
		}
		return list;
	}

	public boolean contains(Property property)
	{
		return containsKey(property.getKey());
	}

	public boolean containsKey(String key)
	{
		return properties.containsKey(key);
	}

	public boolean updateKey(String oldkey, String newKey)
	{
		if (!this.containsKey(oldkey))
			return false;
		properties.get(oldkey).updateKey(newKey);
		return properties.updateKey(oldkey, newKey);
	}

	protected int size()
	{
		return properties.size();
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

		PropertyList other = (PropertyList) obj;
		return new EqualsBuilder().append(this.properties.toHashMap(), other.properties.toHashMap()).isEquals();
	}

	@Override
	public String toString()
	{
		String output = "";
		for (Property property : properties)
		{
			output += property.toString() + " ";
		}
		return output.trim();
	}

	@Override
	public Iterator<Property> iterator()
	{
		Iterator<Property> it = new Iterator<Property>()
		{
			private int index = 0;

			@Override
			public boolean hasNext()
			{
				return index < PropertyList.this.size() && PropertyList.this.get(index) != null;
			}

			@Override
			public Property next()
			{
				return PropertyList.this.get(index++);
			}

			@Override
			public void remove()
			{
				PropertyList.this.remove(index);
			}
		};
		return it;
	}
}
