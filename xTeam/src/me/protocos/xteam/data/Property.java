package me.protocos.xteam.data;

import me.protocos.xteam.data.translators.IDataTranslator;
import me.protocos.xteam.exception.InvalidFormatException;
import org.apache.commons.lang.builder.EqualsBuilder;

public class Property
{
	private final String key;
	private final String value;

	public Property(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	public String getKey()
	{
		return key;
	}

	public <T> T getValue(IDataTranslator<T> strategy)
	{
		return strategy.compile(value);
	}

	public String getValue()
	{
		return value;
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

	public static Property fromObject(String key, Object value)
	{
		if (value == null)
			return new Property(key, null);
		return new Property(key, value.toString());
	}

	public static <T> Property fromObject(String key, T value, IDataTranslator<T> strategy)
	{
		return new Property(key, strategy.decompile(value));
	}
}
