package me.protocos.xteam.data.configuration;

public class ConfigurationOption<T>
{
	private final String key;
	private final T defaultValue;
	private String description;
	private T value;

	public ConfigurationOption(String key, T defaultValue, String description)
	{
		this(key, defaultValue, description, defaultValue);
	}

	public ConfigurationOption(String key, T defaultValue, String description, T value)
	{
		this.key = key;
		this.defaultValue = defaultValue;
		this.description = description;
		this.value = value;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setValue(T value)
	{
		this.value = value;
	}

	public String getKey()
	{
		return key;
	}

	public T getDefaultValue()
	{
		return defaultValue;
	}

	public String getDescription()
	{
		return description;
	}

	public T getValue()
	{
		return value;
	}

	public int length()
	{
		return this.getComment().length();
	}

	public String getComment()
	{
		return new StringBuilder().append("# ").append(this.getKey()).append(" - ").append(this.getDescription()).append(" (default = ").append(this.getDefaultValue()).append(")").toString();
	}

	public String toString()
	{
		return new StringBuilder().append(this.getKey()).append(" = ").append(this.getValue()).toString();
	}
}
