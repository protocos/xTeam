package me.protocos.xteam.core;

import java.io.*;
import me.protocos.xteam.util.HashList;

public class ConfigFile implements IConfigFile
{
	private HashList<String, String> attributes;
	private BufferedReader reader;
	private BufferedWriter writer;
	private File file;

	public ConfigFile(File file)
	{
		attributes = new HashList<String, String>();
		this.file = file;
	}
	@Override
	public boolean read()
	{
		try
		{
			reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null)
			{
				//if there is only one '=' sign and the entire attribute does not contain a comment
				if (line.indexOf("=") == line.lastIndexOf("=") && (line.indexOf("#") < 0 || line.indexOf("#") > line.indexOf("=")))
				{
					String split[] = line.split("=");
					attributes.put(split[0], split[1]);
				}
			}
			reader.close();
			return true;
		}
		catch (FileNotFoundException e) // couldn't read the file
		{
		}
		catch (IOException e) // couldn't read the line
		{
		}
		return false;
	}
	@Override
	public boolean write()
	{
		try
		{
			writer = new BufferedWriter(new FileWriter(file));
			String line;
			for (int i = 0; i < attributes.size(); i++)
			{
				line = attributes.getKey(i) + "=" + attributes.get(i);
				writer.write(line);
			}
			writer.close();
			return true;
		}
		catch (FileNotFoundException e) // couldn't read the file
		{
		}
		catch (IOException e) // couldn't read the line
		{
		}
		return false;
	}

	@Override
	public File getFile()
	{
		return file;
	}

	@Override
	public void addAttribute(String attribute, String value)
	{
		attributes.put(attribute, value);
	}

	@Override
	public void removeAttribute(String attribute)
	{
		attributes.remove(attribute);
	}
}