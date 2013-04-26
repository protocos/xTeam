package me.protocos.xteam.core;

import java.io.File;
import me.protocos.xteam.util.HashList;

public class ConfigFile implements IConfigFile
{
	private HashList<String, String> attributes;
	private File file;

	public ConfigFile(File file)
	{
		attributes = new HashList<String, String>();
		this.file = file;
	}
	@Override
	public boolean read()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean write()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public File getFile()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAttribute(String attribute, String description)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(String attribute)
	{
		// TODO Auto-generated method stub

	}
}