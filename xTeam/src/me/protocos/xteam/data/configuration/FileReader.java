package me.protocos.xteam.data.configuration;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import me.protocos.xteam.xTeam;

public class FileReader
{
	private File file;
	private boolean caseSensitive;
	private HashMap<String, String> keySet = new HashMap<String, String>();

	public FileReader(File file, boolean caseSensitive)
	{
		this.file = file;
		this.caseSensitive = caseSensitive;
		reload();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key, T fallbackValue)
	{
		String configValue = this.keySet.get(key);
		T returnValue = fallbackValue;
		if (this.keySet.containsKey(key))
		{
			if (fallbackValue instanceof String)
				return (T) configValue;
			try
			{
				Method method = fallbackValue.getClass().getMethod("valueOf", String.class);
				returnValue = (T) method.invoke(fallbackValue, configValue);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	private boolean load()
	{
		if (this.file.exists())
		{
			try
			{
				FileInputStream input = new FileInputStream(this.file.getAbsoluteFile());
				InputStreamReader ir = new InputStreamReader(input);
				BufferedReader r = new BufferedReader(ir);
				while (true)
				{
					String line = r.readLine();
					if (line == null)
						break;
					if (!line.startsWith("#"))
					{
						String[] split = line.replaceAll("\\s+", "").split("=");
						if (split.length == 2)
						{
							String key = split[0];
							String value = split[1];
							if (!this.caseSensitive)
							{
								key = key.toLowerCase();
							}
							this.keySet.put(key, value);
						}
					}
				}
				r.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			xTeam.getInstance().getLog().error("File " + this.file.getAbsoluteFile() + " not found.");
			return false;
		}
		return true;
	}

	public void reload()
	{
		this.keySet.clear();
		load();
	}
}
