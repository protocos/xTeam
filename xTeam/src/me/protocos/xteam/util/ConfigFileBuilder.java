package me.protocos.xteam.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.bukkit.permissions.Permission;
import me.protocos.xteam.xTeam;
import me.protocos.xteam.api.collections.HashList;

public class ConfigFileBuilder
{
	private HashList<String, String> descriptions;
	private HashList<String, Object> values;
	//	private FileReader fileReader;
	private FileWriter fileWriter;

	public ConfigFileBuilder(String fileName) throws IOException
	{
		File file = new File(fileName);
		if (!file.exists())
		{
			file.createNewFile();
		}
		//		fileReader = new FileReader(file, false);
		fileWriter = new FileWriter(file);
		descriptions = CommonUtil.emptyHashList();
		values = CommonUtil.emptyHashList();
	}

	public void add(String name, Object defaultValue, String description)
	{
		descriptions.put(name, description);
		values.put(name, defaultValue);
	}

	public String get(String name)
	{
		return new StringBuilder().append(name).append(" = ").append(values.get(name)).toString();
	}

	public String getComment(String name)
	{
		return new StringBuilder().append("# ").append(name).append(" - ").append(descriptions.get(name)).append(" (default = ").append(values.get(name)).append(")").toString();
	}

	public void remove(String name)
	{
		descriptions.remove(name);
		values.remove(name);
	}

	public String getLineBreak()
	{
		String max = "";
		for (String name : values)
		{
			if (this.getComment(name).length() > max.length())
				max = this.getComment(name);
		}
		for (String name : descriptions)
		{
			if (this.getComment(name).length() > max.length())
				max = this.getComment(name);
		}
		List<Permission> perms = xTeam.getSelf().getDescription().getPermissions();
		for (Permission perm : perms)
		{
			if (this.getPermission(perm).length() > max.length())
				max = this.getPermission(perm);
		}
		return max.replaceAll(".", "#") + "\n";
	}

	public void write() throws IOException
	{
		fileWriter.write(toString());
		fileWriter.close();
	}

	private String getPermission(Permission perm)
	{
		return "# " + perm.getName() + " - " + perm.getDescription();
	}

	public String toString()
	{
		String output = getLineBreak() +
				"# \n" +
				"# xTeam Preferences\n" +
				"# \n" +
				getLineBreak();
		descriptions.sort();
		for (String name : descriptions)
		{
			output += this.getComment(name) + "\n";
		}
		output += getLineBreak();
		values.sort();
		for (String name : values)
		{
			output += this.get(name) + "\n";
		}
		output += getLineBreak() +
				"# \n" +
				"# Permissions\n" +
				"# \n" +
				getLineBreak();
		List<Permission> perms = xTeam.getSelf().getDescription().getPermissions();
		for (Permission perm : perms)
		{
			output += this.getPermission(perm) + "\n";
		}
		output += getLineBreak();
		return output;
	}
}
