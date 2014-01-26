package me.protocos.xteam.data;

import org.apache.commons.lang.builder.EqualsBuilder;
import me.protocos.xteam.api.collections.HashList;
import me.protocos.xteam.data.translators.IDataTranslator;

public class PropertyList
{
	private HashList<String, Property> properties;

	public PropertyList()
	{
		properties = new HashList<String, Property>();
	}

	public void put(String key, Object value)
	{
		properties.put(key, Property.fromObject(key, value));
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

	public static PropertyList fromString(String line)
	{
		PropertyList list = new PropertyList();
		String[] properties = line.split(" ");
		for (String property : properties)
		{
			list.put(Property.fromString(property));
		}
		return list;
	}

	public boolean contains(Property property)
	{
		return properties.containsKey(property.getKey());
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
		return new EqualsBuilder().append(this.properties, other.properties).isEquals();
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
}
