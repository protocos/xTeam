package me.protocos.xteam.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import me.protocos.xteam.TeamPlugin;
import me.protocos.xteam.collections.HashList;
import me.protocos.xteam.data.configuration.ConfigurationOption;
import me.protocos.xteam.data.configuration.FileReader;
import me.protocos.xteam.model.ILog;
import me.protocos.xteam.util.CommonUtil;
import me.protocos.xteam.util.PatternBuilder;
import org.bukkit.permissions.Permission;

public abstract class AbstractConfiguration
{
	protected TeamPlugin teamPlugin;
	private HashList<String, ConfigurationOption<?>> options;
	private ILog log;
	private FileReader fileReader;
	private FileWriter fileWriter;
	private String pluginName;

	public AbstractConfiguration(TeamPlugin teamPlugin, File file)
	{
		this.options = CommonUtil.emptyHashList();
		this.teamPlugin = teamPlugin;
		this.log = teamPlugin.getLog();
		try
		{
			this.fileReader = new FileReader(log, file, false);
			this.fileWriter = new FileWriter(file);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		this.pluginName = teamPlugin.getPluginName();
	}

	public abstract void load();

	private String getLineBreak()
	{
		String max = "";
		for (ConfigurationOption<?> option : options)
		{
			if (option.length() > max.length())
				max = option.getComment();
		}
		List<Permission> perms = teamPlugin.getPermissions();
		for (Permission perm : perms)
		{
			if (this.formatPermission(perm).length() > max.length())
				max = this.formatPermission(perm);
		}
		return max.replaceAll(".", "#") + "\n";
	}

	private void updateAliases(String newKey, String... oldKeys)
	{
		for (String oldKey : oldKeys)
		{
			fileReader.updateKey(oldKey, newKey);
		}
	}

	protected Boolean getAttribute(String key, Boolean defaultValue, String description, String... oldKeys)
	{
		updateAliases(key, oldKeys);
		Boolean value = fileReader.get(key, defaultValue);
		ConfigurationOption<Boolean> option = new ConfigurationOption<Boolean>(key, defaultValue, description, value);
		options.put(option.getKey(), option);
		return option.getValue();
	}

	protected Integer getAttribute(String key, Integer defaultValue, Integer lowerBound, Integer upperBound, String description, String... oldKeys)
	{
		updateAliases(key, oldKeys);
		Integer value = fileReader.get(key, defaultValue);
		if (!CommonUtil.insideRange(value, lowerBound, upperBound))
		{
			this.log.error(key + " = " + value + " is not inside range (" + lowerBound + " <= VALUE < " + upperBound + "), defaulting to " + key + " = " + defaultValue);
			value = defaultValue;
		}
		ConfigurationOption<Integer> option = new ConfigurationOption<Integer>(key, defaultValue, description, value);
		options.put(option.getKey(), option);
		return option.getValue();
	}

	protected String getAttribute(String key, String defaultValue, String description, String... oldKeys)
	{
		updateAliases(key, oldKeys);
		String value = fileReader.get(key, defaultValue);
		if (!new PatternBuilder().anyUnlimitedOptional(new PatternBuilder().alphaNumeric().append(":")).whiteSpaceOptional().matches(value))
		{
			this.log.error(key + " = '" + value + "' is not a valid pattern, defaulting to " + key + " = '" + defaultValue + "'");
			value = defaultValue;
		}
		ConfigurationOption<String> option = new ConfigurationOption<String>(key, defaultValue, description, value);
		options.put(option.getKey(), option);
		return option.getValue();
	}

	protected List<String> getAsList(String key, String defaultValue, String description, String... oldKeys)
	{
		updateAliases(key, oldKeys);
		String value = fileReader.get(key, defaultValue);
		if (!new PatternBuilder().anyUnlimitedOptional(new PatternBuilder().alphaNumeric().append(",")).whiteSpaceOptional().matches(value))
		{
			this.log.error(key + " = '" + value + "' is not a valid pattern, defaulting to " + key + " = '" + defaultValue + "'");
			value = defaultValue;
		}
		ConfigurationOption<String> option = new ConfigurationOption<String>(key, defaultValue, description, value);
		options.put(option.getKey(), option);
		return CommonUtil.toList(option.getValue().replace("\\s+", "").split(","));
	}

	private String formatPermission(Permission perm)
	{
		return "# " + perm.getName() + " - " + perm.getDescription();
	}

	public void write()
	{
		try
		{
			fileWriter.write(this.toString());
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String toString()
	{
		String output = getLineBreak() +
				"# \n" +
				"# " + this.pluginName + " Preferences\n" +
				"# \n" +
				getLineBreak();
		options.sort();
		for (ConfigurationOption<?> option : options)
		{
			output += option.getComment() + "\n";
		}
		output += getLineBreak();
		for (ConfigurationOption<?> option : options)
		{
			output += option.toString() + "\n";
		}
		output += getLineBreak() +
				"# \n" +
				"# Permissions\n" +
				"# \n" +
				getLineBreak();
		List<Permission> perms = teamPlugin.getPermissions();
		for (Permission perm : perms)
		{
			output += this.formatPermission(perm) + "\n";
		}
		output += getLineBreak();
		return output;
	}
}
