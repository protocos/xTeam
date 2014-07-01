package me.protocos.xteam.data;

import me.protocos.xteam.data.translator.IDataTranslator;
import me.protocos.xteam.exception.InvalidFormatException;
import org.apache.commons.lang.builder.EqualsBuilder;

public class Property
{
	private String key, value;

	public Property(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	public <T> Property(String key, T value, IDataTranslator<T> dataTranslator)
	{
		this.key = key;
		this.value = dataTranslator.decompile(value);
	}

	public String getKey()
	{
		return key;
	}

	public <T> T getValueUsing(IDataTranslator<T> strategy)
	{
		try
		{
			return strategy.compile(value);
		}
		catch (Exception e)
		{
			value = "";
			return strategy.compile(value);
		}
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value.toString();
	}

	public <T> void setValue(T value, IDataTranslator<T> strategy)
	{
		this.value = strategy.decompile(value);
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

		Property other = (Property) obj;
		return new EqualsBuilder().append(this.key, other.key).append(this.value, other.value).isEquals();
	}

	@Override
	public String toString()
	{
		return new StringBuilder().append(this.getKey()).append(":").append(this.getValue()).toString();
	}

	public static Property fromString(String property)
	{
		String[] components = property.split(":");
		if (components.length == 1)
			return new Property(components[0], "");
		else if (components.length == 2)
			return new Property(components[0], components[1]);
		throw new InvalidFormatException(property, "key:value");
	}

	public static Property fromString(String key, Object value)
	{
		if (value == null)
			return new Property(key, null);
		return new Property(key, value.toString());
	}

	public static <T> Property fromObject(String key, T value, IDataTranslator<T> strategy)
	{
		return new Property(key, strategy.decompile(value));
	}

	public boolean updateKey(String newKey)
	{
		this.key = newKey;
		return true;
	}
}
