package me.protocos.xteam.util;

import java.io.*;
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
	public boolean getBoolean(String key, boolean fallback)
	{
		if (this.keySet.containsKey(key))
		{
			boolean ret;
			try
			{
				ret = Boolean.parseBoolean(this.keySet.get(key));
			}
			catch (Exception e)
			{
				ret = fallback;
			}
			return ret;
		}
		return fallback;
	}
	public double getDouble(String key, double fallback)
	{
		if (this.keySet.containsKey(key))
		{
			double ret;
			try
			{
				ret = Double.parseDouble(this.keySet.get(key));
			}
			catch (Exception e)
			{
				ret = fallback;
			}
			return ret;
		}
		return fallback;
	}
	public float getFloat(String key, float fallback)
	{
		if (this.keySet.containsKey(key))
		{
			float ret;
			try
			{
				ret = Float.parseFloat(this.keySet.get(key));
			}
			catch (Exception e)
			{
				ret = fallback;
			}
			return ret;
		}
		return fallback;
	}
	public int getInteger(String key, int fallback)
	{
		if (this.keySet.containsKey(key))
		{
			int ret;
			try
			{
				ret = Integer.parseInt(this.keySet.get(key));
			}
			catch (Exception e)
			{
				ret = fallback;
			}
			return ret;
		}
		return fallback;
	}
	public long getLong(String key, long fallback)
	{
		if (this.keySet.containsKey(key))
		{
			long ret;
			try
			{
				ret = Long.parseLong(this.keySet.get(key));
			}
			catch (Exception e)
			{
				ret = fallback;
			}
			return ret;
		}
		return fallback;
	}
	public String getString(String key, String fallback)
	{
		if (this.keySet.containsKey(key))
		{
			return this.keySet.get(key);
		}
		return fallback;
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
						String[] splt = line.split("=");
						if (splt.length == 2)
						{
							String key = splt[0];
							String value = splt[1];
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
			xTeam.getLog().error("File " + this.file.getAbsoluteFile() + " not found.");
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
